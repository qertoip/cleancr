# cleancr

Finds and optionally removes __carriage return characters__ from your project text files (also known as Ctrl-M, ^M, \r).

Carriage return characters are evil and should never happen to exist in your source code files.

However, when software is being developed on multiple operating systems, editors and IDEs, it is easy for unwanted CR characters to sneak into your project.

Use cleancr to hunt for CR characters and get rid of them from the whole project with one command.

Text files are recognized automatically. Cleancr should never try to modify your binary files. The [istext](https://github.com/qertoip/istext) is used for that purpose.

The tool is a recursive in nature. It is designed to clean up the whole directories. You can specify a file extensions to limit its appetite.

## Usage

    java -jar cleancr /some/project/docs.txt    # remove CR from single file

    java -jar cleancr /some/project/*.java      # remove CR recursively from all .java files

    java -jar cleancr /some/project             # remove CR recursively from all text files

Please note:

*   dotdirs and dotfiles are skipped (i.e. .svn, .gitignore)

*   full wildcards are __not__ supported. It works only in some/dir/*.extension scenario.

## Installation

Simply download and invoke (no installation necessary).

## Development

cleancr is built with Leiningen:

    lein deps
    lein test
    lein uberjar

## License

Copyright (C) 2010 Piotr 'Qertoip' Włodarek. Distributed under the MIT License.