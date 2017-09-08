package com.seanrmilligan.sitebuilder.test;

/**
 * Created by sean on 6/23/17.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.seanrmilligan.sitebuilder.file.MapManager;
import com.seanrmilligan.sitebuilder.file.TemplateBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class TemplateBuilderTest {
	@Test
	public void basic() {
		try {
			File jsonFile = new File("src/test/resources/TemplateBuilder/basic/keys.json");
			File templateFile = new File("src/test/resources/TemplateBuilder/basic/template.html");
			File expectedFile = new File("src/test/resources/TemplateBuilder/basic/expected.html");
			File actualFile = new File("src/test/resources/TemplateBuilder/basic/out.html");
			
			HashMap<String, String> map = MapManager.loadMap(jsonFile, StandardCharsets.UTF_8);
			
			TemplateBuilder.build(templateFile, actualFile, StandardCharsets.UTF_8, map);
			
			assertFileEquals(expectedFile, actualFile);
		} catch (IOException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void scanNonexistentKeyInDoc() {
		try {
			File jsonFile = new File("src/test/resources/TemplateBuilder/nonexistent-key-in-doc/keys.json");
			File templateFile = new File("src/test/resources/TemplateBuilder/nonexistent-key-in-doc/template.html");
			
			HashMap<String, String> map = MapManager.loadMap(jsonFile, StandardCharsets.UTF_8);
			
			boolean missingKey = !TemplateBuilder.scan(templateFile, StandardCharsets.UTF_8, map);
			
			assertEquals(true, missingKey);
		} catch (IOException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void buildNonexistentKeyInDoc() {
		try {
			File jsonFile = new File("src/test/resources/TemplateBuilder/nonexistent-key-in-doc/keys.json");
			File templateFile = new File("src/test/resources/TemplateBuilder/nonexistent-key-in-doc/template.html");
			File expectedFile = new File("src/test/resources/TemplateBuilder/nonexistent-key-in-doc/expected.html");
			File actualFile = new File("src/test/resources/TemplateBuilder/nonexistent-key-in-doc/out.html");
			
			HashMap<String, String> map = MapManager.loadMap(jsonFile, StandardCharsets.UTF_8);
			
			TemplateBuilder.build(templateFile, actualFile, StandardCharsets.UTF_8, map);
			
			assertFileEquals(expectedFile, actualFile);
		} catch (IOException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void scanEmptyDoc() {
		try {
			File jsonFile = new File("src/test/resources/TemplateBuilder/empty/keys.json");
			File templateFile = new File("src/test/resources/TemplateBuilder/empty/template.html");
			
			HashMap<String, String> map = MapManager.loadMap(jsonFile, StandardCharsets.UTF_8);
			
			boolean missingKey = !TemplateBuilder.scan(templateFile, StandardCharsets.UTF_8, map);
			
			assertEquals(false, missingKey);
		} catch (IOException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void buildEmptyDoc() {
		try {
			File jsonFile = new File("src/test/resources/TemplateBuilder/empty/keys.json");
			File templateFile = new File("src/test/resources/TemplateBuilder/empty/template.html");
			File expectedFile = new File("src/test/resources/TemplateBuilder/empty/expected.html");
			File actualFile = new File("src/test/resources/TemplateBuilder/empty/out.html");
			
			HashMap<String, String> map = MapManager.loadMap(jsonFile, StandardCharsets.UTF_8);
			
			TemplateBuilder.build(templateFile, actualFile, StandardCharsets.UTF_8, map);
			
			assertFileEquals(expectedFile, actualFile);
		} catch (IOException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void buildDocWithKeysAtBounds() {
		try {
			File jsonFile = new File("src/test/resources/TemplateBuilder/keys-at-file-bounds/keys.json");
			File templateFile = new File("src/test/resources/TemplateBuilder/keys-at-file-bounds/template.html");
			File expectedFile = new File("src/test/resources/TemplateBuilder/keys-at-file-bounds/expected.html");
			File actualFile = new File("src/test/resources/TemplateBuilder/keys-at-file-bounds/out.html");
			
			HashMap<String, String> map = MapManager.loadMap(jsonFile, StandardCharsets.UTF_8);
			
			TemplateBuilder.build(templateFile, actualFile, StandardCharsets.UTF_8, map);
			
			assertFileEquals(expectedFile, actualFile);
		} catch (IOException e) {
			fail(e.toString());
		}
	}
	
	private static void assertFileEquals(File expectedFile, File actualFile) throws IOException {
		List<String> expectedLines = FileUtils.readLines(expectedFile, StandardCharsets.UTF_8);
		List<String> actualLines = FileUtils.readLines(actualFile, StandardCharsets.UTF_8);
		
		assertEquals(expectedLines.size(), actualLines.size());
		
		for (int i=0; i<expectedLines.size(); i++) {
			assertEquals(expectedLines.get(i), actualLines.get(i));
		}
	}
}
