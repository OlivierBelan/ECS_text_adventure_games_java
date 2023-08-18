#! /bin/sh
# cp ../src/*.txt .
# find ../src/ -name "*.java" > sources.txt
# /usr/lib/jvm/jdk-11.0.8+10/bin/javac -d . -Xlint:unchecked @sources.txt
# java Main

cp -r ../src/map .
find ../src/ -name "*.java" > sources.txt
../dependencies/jdk-11.0.8+10/bin/javac -d . -Xlint:unchecked @sources.txt --module-path ../dependencies/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.graphics,javafx.controls,javafx.base,javafx.media,javafx.swing,javafx.fxml
java --module-path ../dependencies/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.graphics,javafx.controls,javafx.base,javafx.media,javafx.swing,javafx.fxml Main
