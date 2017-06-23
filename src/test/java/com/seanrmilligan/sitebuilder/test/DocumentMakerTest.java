package com.seanrmilligan.sitebuilder.test;

/**
 * Created by sean on 6/23/17.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.seanrmilligan.sitebuilder.file.DocumentMaker;
import com.seanrmilligan.sitebuilder.file.MapMaker;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class DocumentMakerTest {
	@Test
	public void testDoc() {
		try {
			File jsonFile = new File("src/test/resources/keys.json");
			File templateFile = new File("src/test/resources/template.html");
			File expectedFile = new File("src/test/resources/expected.html");
			File actualFile = new File("src/test/resources/out.html");
			
			HashMap<String, String> map = MapMaker.buildMap(jsonFile);
			
			DocumentMaker.makeDoc(templateFile, actualFile, StandardCharsets.UTF_8, map);
			
			List<String> expectedLines = FileUtils.readLines(expectedFile, StandardCharsets.UTF_8);
			List<String> actualLines = FileUtils.readLines(actualFile, StandardCharsets.UTF_8);
			
			assertEquals(expectedLines.size(), actualLines.size());
			
			for (int i=0; i<expectedLines.size(); i++) {
				assertEquals(expectedLines.get(i), actualLines.get(i));
			}
		} catch (IOException e) {
			fail(e.toString());
		}
	}
}
