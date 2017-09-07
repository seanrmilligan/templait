package com.seanrmilligan.sitebuilder.file;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sean on 6/19/17.
 */
public class TemplateBuilder {
	private static Pattern keyPattern = Pattern.compile("\\{\\{[A-Z][A-Z_]*}}");

	public static void build(File in, File out, Charset encoding, HashMap<String, String> substitutions) throws IOException {
		StringBuilder document = new StringBuilder();
		String template = new String(Files.readAllBytes(Paths.get(in.getPath())), encoding);
		int writtenIndex = -1;
		
		Matcher keyFinder = keyPattern.matcher(template);

		while (keyFinder.find()) {
			String key = keyFinder.group().substring(2, keyFinder.group().length() - 2);
			document.append(between(writtenIndex, keyFinder.start(), template));
			document.append(substitutions.get(key));
			writtenIndex = keyFinder.end();
		}
		
		if (writtenIndex < template.length()) {
			document.append(between(writtenIndex, template.length(), template));
		}
		
		Files.write(Paths.get(out.getPath()), document.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
	}
	
	private static String between(int startInclusive, int endExclusive, String s) {
		if (startInclusive < 0) {
			return s.substring(0, endExclusive);
		} else {
			return s.substring(startInclusive, endExclusive);
		}
	}
}
