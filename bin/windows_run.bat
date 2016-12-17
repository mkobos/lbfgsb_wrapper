REM Run the script in the root directory of the project.
REM The only argument of this script is the path to the JAR file.

PATH=%PATH%;./dist
java -jar %1
