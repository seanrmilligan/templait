package com.seanrmilligan.template;

import com.seanrmilligan.template.file.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Templait {
	private static final String FLAG_BUILD = "--build";
	private static final String FLAG_HELP = "--help";
	private static final String FLAG_NEW = "--new";
	
	public static void main(String[] args) {
		FileManager fileManager = null;
		File dir;
		String flag;
		
		for (String arg : args) {
			if (arg.equals(FLAG_HELP)) {
				help();
				System.exit(0);
			}
		}
		
		if (args.length == 2) {
			flag = args[0];
			dir = new File(args[1]);
			
			if (!dir.isDirectory()) {
				System.out.println(args[0] + " is not a directory.");
			}
			
			try {
				fileManager = new FileManager(dir);
			} catch (IOException e) {
				showError("IOException", e.toString());
			}
			
			
			if (flag.equals(FLAG_BUILD)) {
				showInfo("Status", "Build started.");
				
				try {
					fileManager.buildAll();
					showInfo("Status", "Build complete.");
				} catch (IOException e) {
					showError("IOException", Arrays.toString(e.getStackTrace()));
					showInfo("Status", "Build interrupted.");
				}
			} else if (flag.equals(FLAG_NEW)) {
				showInfo("Status", "Structure creation started.");
				
				try {
					fileManager.newTemplate();
					showInfo("Status", "Structure creation complete.");
				} catch (IOException e) {
					showError("IOException", e.toString());
					showInfo("Status", "Structure creation interrupted.");
				}
			} else {
				System.out.println("Unrecognized flag. See --help for more information.");
			}
		} else {
			System.out.println("Invalid number of arguments. See --help for more information.");
		}
	}
	
	private static void help() {
		String[] menu = {
				"Templait - moustache based templating engine",
				"    java -jar Templait.jar ACT DIR",
				"        ACT     The action to perform on the provided directory",
				"        DIR     The directory to operate on",
				"Actions:",
				"    --build",
				"        Builds the files in the template/ directory, merging the contents with those in the includes/ directory.",
				"        Mapping is performed by way of a lookup in the substitutions.json and includes.json files.",
				"        Only files with an extension listed in extensions.json will be built, unless extensions.json is empty.",
				"    --new",
				"        Creates the required files and folders for a new templated project. See 'Structure' for more.",
				"Structure:",
				"    base-directory/             The argument DIR to this program.",
				"        |",
				"        +-build/                The directory of the completed template files.",
				"        |",
				"        +-includes/             The base directory prepended to the values in the includes.json file.",
				"        |",
				"        +-template/             The base directory for the template files.",
				"        |",
				"        +-extensions.json       The whitelist of extensions to operate on. Defaults to all if empty.",
				"        |",
				"        +-includes.json         The map of template keys to files in the includes/ directory.",
				"        |",
				"        +-substitutions.json    The map of template keys to inline substitutions.",
		};
		
		for (String line : menu) {
			System.out.println(line);
		}
	}
	
	private static void showInfo(String title, String message) {
		System.out.println(title + ": " + message);
	}
	
	private static void showError(String title, String message) {
		System.err.println(title + ": " + message);
	}
}
