#!/bin/bash
## Run the script in the root directory of the project.

export LD_LIBRARY_PATH="$LD_LIBRARY_PATH:./dist"
java -jar ./dist/*.jar
