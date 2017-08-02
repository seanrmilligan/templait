package com.seanrmilligan.sitebuilder.file;

import org.json.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by sean on 6/19/17.
 */
public class MapManager {
	public static HashMap<String, String> buildMap(File keyfile) throws IOException {
		HashMap<String, String> map = new HashMap<>();
		FileInputStream stream = new FileInputStream(keyfile);
		String jsonText = IOUtils.toString(stream, "UTF-8");
		JSONObject jsonObject = new JSONObject(jsonText);
		Iterator<String> keyIter = jsonObject.keys();
		
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			map.put(key, jsonObject.getString(key));
		}

		return map;
	}
}
