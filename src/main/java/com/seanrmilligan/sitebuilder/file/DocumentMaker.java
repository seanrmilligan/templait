package com.seanrmilligan.sitebuilder.file;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sean on 6/19/17.
 */
public class DocumentMaker {
	Pattern keyPattern = Pattern.compile("\\{\\{[A-Z]+}}");

	public void makeDoc(File in, File out, Charset encoding, HashMap<String, String> substitutions) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(in.getPath()));
		String document = new String(bytes, encoding);

		Matcher keyFinder = keyPattern.matcher(document);

		while (keyFinder.find()) {
			System.out.println("substr: " + document.substring(keyFinder.start(), keyFinder.end()));
			System.out.println("group:  " + keyFinder.group());
		}
	}
}
