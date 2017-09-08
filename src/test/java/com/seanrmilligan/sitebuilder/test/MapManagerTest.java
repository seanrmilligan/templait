package com.seanrmilligan.sitebuilder.test;

/**
 * Created by sean on 6/19/17.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.seanrmilligan.sitebuilder.file.MapManager;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class MapManagerTest {
	@Test
	public void testMap() {
		try {
			File jsonFile = new File("src/test/resources/MapManager/keys.json");
			
			HashMap<String, String> actualMap = MapManager.loadMap(jsonFile, StandardCharsets.UTF_8);
			HashMap<String, String> expectedMap = new HashMap<>();
			
			expectedMap.put("NAVIGATION", "<nav><a href=\"http://seanrmilligan.com\">Sean Milligan</a></nav>");
			expectedMap.put("SITE_TITLE", "Sean Milligan");
			
			assertEquals(expectedMap, actualMap);
		} catch (IOException e) {
			fail(e.toString());
		}
	}
}
