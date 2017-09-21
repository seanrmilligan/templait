package com.seanrmilligan.template.file;

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
	
	/**
	 *
	 * @param file The template file to scan
	 * @param encoding The encoding of the template file
	 * @param substitutions The map of template keys to values
	 * @return true if every key in the template is in the substitution map's key set, false otherwise
	 * @throws IOException
	 */
	public static boolean scan(File file, Charset encoding, HashMap<String, String> substitutions) throws IOException {
		String template = new String(Files.readAllBytes(Paths.get(file.getPath())), encoding);
		Matcher keyFinder = keyPattern.matcher(template);
		boolean missingKey;

		for (missingKey = false; keyFinder.find() && !missingKey; missingKey = !substitutions.containsKey(getKey(keyFinder.group())));

		return !missingKey;
	}
	
	public static void merge(File in, File out, File includes, Charset encoding, HashMap<String, String> substitutions) throws IOException {
		StringBuilder document = new StringBuilder();
		String template = new String(Files.readAllBytes(Paths.get(in.getPath())), encoding);
		int writtenIndex = 0;
		
		Matcher keyFinder = keyPattern.matcher(template);
		
		while (keyFinder.find()) {
			String key = getKey(keyFinder.group());
			document.append(template.substring(writtenIndex, keyFinder.start()));
			
			if (substitutions.containsKey(key)) {
				document.append(
					new String(Files.readAllBytes(
						Paths.get(includes.getAbsolutePath() + File.separator + substitutions.get(key))
					), encoding)
				);
			} else {
				document.append(keyFinder.group());
			}
			
			writtenIndex = keyFinder.end();
		}
		
		if (writtenIndex < template.length()) {
			document.append(template.substring(writtenIndex, template.length()));
		}
		
		Files.write(Paths.get(out.getPath()), document.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
	}
	
	/**
	 *
	 * @param in The template file
	 * @param out The completed file
	 * @param encoding The encoding of the template file
	 * @param substitutions The map of template keys to values
	 * @throws IOException
	 */
	public static void build(File in, File out, Charset encoding, HashMap<String, String> substitutions) throws IOException {
		StringBuilder document = new StringBuilder();
		String template = new String(Files.readAllBytes(Paths.get(in.getPath())), encoding);
		int writtenIndex = 0;
		
		Matcher keyFinder = keyPattern.matcher(template);

		while (keyFinder.find()) {
			String key = getKey(keyFinder.group());
			document.append(template.substring(writtenIndex, keyFinder.start()));
			
			if (substitutions.containsKey(key)) {
				document.append(substitutions.get(key));
			} else {
				document.append(keyFinder.group());
			}
			
			writtenIndex = keyFinder.end();
		}
		
		if (writtenIndex < template.length()) {
			document.append(template.substring(writtenIndex, template.length()));
		}
		
		Files.write(Paths.get(out.getPath()), document.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
	}
	
	private static String getKey(String group) {
		return group.substring(2, group.length() - 2);
	}
}
