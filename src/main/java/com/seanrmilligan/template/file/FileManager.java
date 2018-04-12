package com.seanrmilligan.template.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;

import static com.seanrmilligan.template.file.FileUtils.getExtension;

/**
 * Created by sean on 6/25/17.
 */
public class FileManager {
	private static final String DIR_ALREADY_EXISTS = "Directory already exists: ";
	private static final String DIR_CREATION_ERROR = "Cannot create directory: ";
	private static final String DIR_NAME_COLLISION = "File of the same name already exists: ";
	private static final String FILE_CREATION_ERROR = "Cannot create file: ";
	
	private File projectDir;
	private File buildDir;
	private File includesDir;
	private File templateDir;
	private File extensionsFile;
	private File includesFile;
	private File substitutionsFile;
	
	public FileManager(File projDir) throws IOException {
		String projPath = projDir.getCanonicalPath();
		
		this.projectDir = projDir;
		this.buildDir = FileUtils.constructFileFromPath(projPath, FileNames.BUILD_DIRECTORY);
		this.includesDir = FileUtils.constructFileFromPath(projPath, FileNames.INCLUDES_DIRECTORY);
		this.templateDir = FileUtils.constructFileFromPath(projPath, FileNames.TEMPLATE_DIRECTORY);
		this.extensionsFile = FileUtils.constructFileFromPath(projPath, FileNames.EXTENSIONS);
		this.includesFile = FileUtils.constructFileFromPath(projPath, FileNames.INCLUDES);
		this.substitutionsFile = FileUtils.constructFileFromPath(projPath, FileNames.SUBSTITUTIONS);
	}
	
	public void buildAll() throws IOException {
		HashSet<String> extensions = ArrayManager.loadSet(this.extensionsFile, StandardCharsets.UTF_8);
		HashMap<String, String> includes = MapManager.loadMap(this.includesFile, StandardCharsets.UTF_8);
		HashMap<String, String> substitutions = MapManager.loadMap(this.substitutionsFile, StandardCharsets.UTF_8);

		if (!buildDir.exists()) makeDir(buildDir);

		org.apache.commons.io.FileUtils.cleanDirectory(buildDir);
		
		buildAll(templateDir, templateDir, includesDir, buildDir, extensions, includes, substitutions);
	}

	/**
	 * Makes a new project in the @projectDir this class was initialized with.
	 * @throws IOException If any of the directories or files fail to create.
	 */
	public void newTemplate() throws IOException {
		makeDir(this.buildDir);
		makeDir(this.includesDir);
		makeDir(this.templateDir);
		makeFile(this.extensionsFile, "[]");
		makeFile(this.includesFile, "{}");
		makeFile(this.substitutionsFile, "{}");
	}
	
	public void showIncludes() throws IOException {
		HashMap<String, String> includes = MapManager.loadMap(this.includesFile, StandardCharsets.UTF_8);
		this.showMap("Includes", includes, this.includesFile.getCanonicalPath());
	}
	
	public void showSubstitutions() throws IOException {
		HashMap<String, String> substitutions = MapManager.loadMap(this.substitutionsFile, StandardCharsets.UTF_8);
		this.showMap("Substitutions", substitutions, this.substitutionsFile.getCanonicalPath());
	}
	
	private static void build(File template, File templateDir, File includesDir, File buildDir, HashSet<String> extensions,
							  HashMap<String, String> includes, HashMap<String, String> substitutions) throws IOException {
		
		String templateDirPath = templateDir.getCanonicalPath();
		String buildDirPath = buildDir.getCanonicalPath();
		String outPath = template.getCanonicalPath().replaceFirst(templateDirPath, buildDirPath);
		File out = new File(outPath);
		
		if (extensions.contains(getExtension(template))) {
			System.out.println("Building " + template.getCanonicalPath());
			TemplateBuilder.merge(template, out, includesDir, StandardCharsets.UTF_8, includes);
			TemplateBuilder.build(out, out, StandardCharsets.UTF_8, substitutions);
		} else {
			System.out.println("Skipping " + template.getCanonicalPath());
			Files.copy(template.toPath(), out.toPath());
		}
	}
	
	private static void buildAll(File dir, File templateDir, File includesDir, File buildDir, HashSet<String> extensions,
								 HashMap<String, String> includes, HashMap<String, String> substitutions) throws IOException {
		
		for (File item : dir.listFiles()) {
			if (item.isFile()) {
				build(item, templateDir, includesDir, buildDir, extensions, includes, substitutions);
			} else if (item.isDirectory()) {
				String templateDirPath = templateDir.getCanonicalPath();
				String buildDirPath = buildDir.getCanonicalPath();
				String itemTemplatePath = item.getCanonicalPath();
				String itemBuildPath = itemTemplatePath.replaceFirst(templateDirPath, buildDirPath);
				(new File(itemBuildPath)).mkdirs();
				
				buildAll(item, templateDir, includesDir, buildDir, extensions, includes, substitutions);
			}
		}
	}

	/**
	 *
	 * @param dir The directory to create
	 * @throws IOException If the directory already exists, the name of the directory collides with a non-directory
	 *     file, or the method fails to make the directory
	 */
	
	private static void makeDir(File dir) throws IOException {
		if (dir.exists()) {
			if (dir.isDirectory()) {
				throw new FileAlreadyExistsException(DIR_ALREADY_EXISTS + dir.getCanonicalPath());
			} else {
				throw new FileAlreadyExistsException(DIR_NAME_COLLISION + dir.getCanonicalPath());
			}
		}
		
		if (!dir.mkdir()) {
			throw new FileNotFoundException(DIR_CREATION_ERROR + dir.getCanonicalPath());
		}
	}

	/**
	 *
	 * @param file The file (which should not already exist) to save the @content to
	 * @param content The content to write out to @file
	 * @throws IOException If this method fails to make the file
	 */
	private static void makeFile(File file, String content) throws IOException {
		if (!file.createNewFile()) {
			throw new FileNotFoundException(FILE_CREATION_ERROR + file.getCanonicalPath());
		}
		
		FileOutputStream stream = new FileOutputStream(file);
		stream.write(content.getBytes());
		stream.close();
	}
	
	private void showMap(String title, HashMap<String, String> map, String filename) {
		System.out.println(title);
		System.out.println(filename);
		
		map.keySet().stream().forEach((element) -> System.out.println(element + " -- " + map.get(element)));
	}
}
