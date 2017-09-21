package com.seanrmilligan.template.file;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

/**
 * Created by sean on 6/19/17.
 */
public class ArrayManager {
	public static HashSet<String> loadSet(File file, Charset encoding) throws IOException {
		HashSet<String> set = new HashSet<>();
		JSONArray json = new JSONArray(new String(Files.readAllBytes(Paths.get(file.getPath())), encoding));
		
		for (int i=0; i<json.length(); set.add((json.getString(i++))));
		
		return set;
	}
	
	public static void saveSet (File file, HashSet<String> set) throws IOException {
		FileOutputStream stream = new FileOutputStream(file);
		JSONArray json = new JSONArray();
		
		set.stream().map(element -> json.put(element));
		
		stream.write(json.toString(4).getBytes());
		
		stream.close();
	}
}
