# Templait

## Description
Templait is a simple moustache templating engine.

Templait searches for the pattern {{[A-Z][A-Z_]\*}} (all caps and underscores) in each file in the template/ directory. Templait uses the includes.json and substitutions.json file to build the template and then write the completed file to the build/ directory.

Templait only attempts to build files with one of the filetypes listed in extensions.json. If extensions.json is empty, Templait attempts to build all files. Files which do not have a filetype that appears in extensions.json are copied as-is.

Two distinct passes are made in the building process. The first pass replaces any matched keys in the includes.json file with the corresponding file content mapped to in the includes/ directory. This is useful for merging large chunks of text into a template. The second pass replaces any keys in the substitutions.json file with the value mapped to in that json file. Any keys not matched in either pass are simply written to the final build as they were found.

The two pass system means it is possible to use substitution keys in files found in the includes/ directory. This is because the contents of the includes/ files, including any moustache keys, are written to the build/ file before the second pass is done. Templait does not recursively resolve keys, therefore this feature has its limits.


## Dependencies
  - JRE 1.8

## Usage
Making a new project:
```
java -jar Templait.jar --new ~/new/project/
```

Building an existing project:
```
java -jar Templait.jar --build ~/path/to/project/
```

## Structure
```
base-directory/             The argument DIR to this program.
    |
    +-build/                The directory of the completed template files.
    |
    +-includes/             The base directory prepended to the values in the includes.json file.
    |
    +-template/             The base directory for the template files.
    |
    +-extensions.json       The whitelist of extensions to operate on. Defaults to all if empty.
    |
    +-includes.json         The map of template keys to files in the includes/ directory.
    |
    +-substitutions.json    The map of template keys to short, inline substitutions.
```
