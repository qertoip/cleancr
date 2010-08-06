# cleancr

Finds and optionally removes __carriage return characters__ from your project text files (also known as Ctrl-M, ^M, \r).

Carriage return characters are evil and should never happen to exist in your source code files.

However, when software is being developed on multiple operating systems, editors and IDEs, it is easy for unwanted CR characters to sneak into your project.

Use cleancr tool to monitor for CR characters and get rid of them from the whole project with one command.

## Usage

    java -jar cleancr e:\project\docs.txt    # remove CR from single file

    java -jar cleancr e:\project\*.java      # remove CR recursively from all .java files

    java -jar cleancr e:\project             # remove CR recursively from all text files

Please note:
 * dotdirs and dotfiles are skipped (i.e. .svn, .gitignore)
 * full wildcards are __not__ supported. It works only in some/dir/*.extension scenario.

## Installation

Simply download and invoke (no installation necessary).

## License

Copyright (C) 2010 Piotr 'Qertoip' Włodarek. Distributed under the MIT License.