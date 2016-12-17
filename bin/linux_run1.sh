#!/bin/bash
## Run the script in the root directory of the project.
## The only argument of this script is the path to the JAR file.

JAR_FILE=$1

java -Djava.library.path=./dist -jar $JAR_FILE
