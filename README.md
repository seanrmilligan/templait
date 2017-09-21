# Templait

## Description
Templait is a simple moustache templating engine.

Templait searches for the pattern {{[A-Z][A-Z_]\*}} in each file in the template/ directory. Templait uses the includes.json and substitutions.json file to build the template and then write the completed file to the build/ directory.

Two distinct passes are made. The first pass replaces any matched keys in the includes.json file with the corresponding content mapped to in the includes/ directory. This is useful for merging large chunks of text into a template. The second pass replaces any keys in the substitutions.json file with the value mapped to in that json file. Any keys not matched in either pass are simply written back out as they were found.

The two pass system means it is possible to use substitution keys in files found in the includes/ directory. This is because the 

## Dependencies
  - JRE 1.8

## Usage
```
java -jar Templait.jar --build ~/path/to/project/
```

## Structure
```
Structure:
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
        +-substitutions.json    The map of template keys to inline substitutions.
```