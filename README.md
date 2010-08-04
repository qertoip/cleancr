# cleancr

This is initial version of the software and should not be used for production purposes.

Finds and optionally removes __CR__ (carriage return) characters from your project text files.

Carriage return characters also known as __\r__ or __^M__ are evil and should never happen to exist in your source code files.

However, when software is being developed on multiple operating systems, editors and IDEs, it is easy for unwanted CR characters to sneak into your project.

Use cleancr tool to monitor for CR characters and get rid of them from the whole project with one command.

## Usage

java -jar cleancr e:\project

java -jar cleancr e:\project\docs.txt

## Installation

Simply download and invoke (no installation necessary).

## License

Copyright (C) 2010 Piotr 'Qertoip' Włodarek. Distributed under the MIT License.