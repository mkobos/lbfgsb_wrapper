#!/bin/bash

export LD_LIBRARY_PATH="$$LD_LIBRARY_PATH:./"
java -jar @TARGET_FILE_NAME@
