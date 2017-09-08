package com.seanrmilligan.sitebuilder.file;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by sean on 6/19/17.
 */
public class MapManager {
	public static HashMap<String, String> loadMap(File file, Charset encoding) throws IOException {
		HashMap<String, String> map = new HashMap<>();
		JSONObject json = new JSONObject(new String(Files.readAllBytes(Paths.get(file.getPath())), encoding));
		
		for (String key : json.keySet()) {
			map.put(key, json.getString(key));
		}

		return map;
	}
	
	public static void saveMap (File file, HashMap<String, String> map) throws IOException {
		FileOutputStream stream = new FileOutputStream(file);
		JSONObject json = new JSONObject();
		
		for (String key : map.keySet()) {
			json.put(key, map.get(key));
		}
		
		stream.write(json.toString().getBytes());
		
		stream.close();
	}
}
