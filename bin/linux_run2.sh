#!/bin/bash
## Run the script in the root directory of the project.
## The only argument of this script is the path to the JAR file.

JAR_FILE=$1

export LD_LIBRARY_PATH="$$LD_LIBRARY_PATH:./dist"
java -jar $JAR_FILE
