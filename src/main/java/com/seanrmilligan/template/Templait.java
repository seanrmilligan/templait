package com.seanrmilligan.template;

import com.seanrmilligan.template.file.FileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;

public class Templait {
	/* flags */
	private static final String FLAG_BUILD = "--build";
	private static final String FLAG_HELP = "--help";
	private static final String FLAG_NEW = "--new";

	/* exit codes */
	public static final int EXIT_OK = 0;
	public static final int EXIT_BAD_ARGS = 1;

	/* messages */
	public static final String STATUS = "Status";
	public static final String STARTED = " started.";
	public static final String COMPLETED = " completed.";
	public static final String FAILED = " failed.";
	public static final String BAD_ARGS = "Bad Arguments";
	public static final String SEE_HELP = "See --help for more information.";


	private static final String[] MENU = {
		"Templait - moustache based templating engine",
		"    java -jar Templait.jar ACT [DIR]",
		"        ACT     The action to perform on the provided directory",
		"        DIR     The directory to operate on",
		"Actions:",
		"    --build",
		"        Builds the files in the template/ directory, merging the contents with",
		"        those in the includes/ directory. Mapping is performed by way of a",
		"        lookup in the substitutions.json and includes.json files. Only files",
		"        with an extension listed in extensions.json will be built, unless",
		"        extensions.json is empty.",
		"    --help",
		"        Prints out this menu.",
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
	
	public static void main(String[] args) {
		if (args.length == 1 && args[0].equals(FLAG_HELP)) {
			help();
		} else if (args.length == 2) {
			String processName = "Process";
			FileManager fileManager;
			File dir = new File(args[1]);
			String flag = args[0];

			try {
				if (!dir.isDirectory()) throw new NotDirectoryException(dir.getName());
				fileManager = new FileManager(dir);

				if (flag.equals(FLAG_BUILD)) {
					processName = "Build";
					showInfo(STATUS, processName + STARTED);
					fileManager.buildAll();
				} else if (flag.equals(FLAG_NEW)) {
					processName = "Project creation";
					showInfo(STATUS, processName + STARTED);
					fileManager.newTemplate();
				} else {
					badArgs();
				}

				showInfo(STATUS, processName + COMPLETED);
			} catch (IOException | IllegalArgumentException e) {
				showError(e.getClass().getSimpleName(), e.getMessage());
				showInfo(STATUS, processName + FAILED);
			}
		} else {
			badArgs();
		}

		System.exit(EXIT_OK);
	}
	
	private static void help() {
		for (String line : Templait.MENU) {
			System.out.println(line);
		}
	}

	private static void badArgs() {
		showError(BAD_ARGS, SEE_HELP);
		System.exit(EXIT_BAD_ARGS);
	}
	
	private static void showInfo(String title, String message) {
		System.out.println(title + ": " + message);
	}
	
	private static void showError(String title, String message) {
		System.err.println(title + ": " + message);
	}
}