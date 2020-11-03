#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

rm inputText.txt outputHash.txt
javac $DIR/REPL.java
java REPL

node executeTest inputText.txt outputHash.txt