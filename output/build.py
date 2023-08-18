#!/usr/bin/python3

import sys
import getopt
import os


LINUX = False
WINDOWS = False


def usage():
    print("Usage : build.py [OPTIONS]")
    print("\t-h, --help\tDisplay this message and quit")
    print("\t-l, --linux\tLaunch in Linux mode")
    print("\t-w, --windows\tLaunch in Windows mode")


def get_opt(argv):
    try:
        # opts is a list of (option, value) pairs
        # args is the list of program arguments left after the option list was stripped
        opts, args = getopt.getopt(
            argv[0:], "hlw", ["help", "linux", "windows"])
        pass
    except getopt.GetoptError as err:
        print(str(err))
        usage()
        sys.exit(2)
        pass
    for option, value in opts:
        if option in ("-h", "--help"):
            usage()
            sys.exit(0)
        if option in ("-l", "--linux"):
            global LINUX
            LINUX = True
            continue
        if option in ("-w", "--windows"):
            global WINDOWS
            WINDOWS = True
            continue
        assert False, "unhandled option"
    return args


def main(argv):
    args = get_opt(argv)
    if (LINUX):
        os.system("cp -r ../src/map .")
        os.system("find ../src/ -name ""*.java"" > sources.txt")
        os.system("../dependencies/jdk-11.0.8+10/bin/javac -d . -Xlint:unchecked @sources.txt --module-path ../dependencies/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.graphics,javafx.controls,javafx.base,javafx.media,javafx.swing,javafx.fxml")
        os.system("../dependencies/jdk-11.0.8+10/bin/java --module-path ../dependencies/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.graphics,javafx.controls,javafx.base,javafx.media,javafx.swing,javafx.fxml Main")
    if (WINDOWS):
        os.system("del sources.txt")
        os.system("copy .\..\src\map .")
        os.system("where /r .\..\src\ *.java >> sources.txt")
        path= r".\..\dependencies\jdk-11.0.9_windows\bin\javac.exe -d . -Xlint:unchecked @sources.txt --module-path ..\dependencies\javafx-sdk-11.0.2_windows\lib --add-modules javafx.controls,javafx.graphics,javafx.controls,javafx.base,javafx.media,javafx.swing,javafx.fxml"
        os.system(path)
        path = r".\..\dependencies\jdk-11.0.9_windows\bin\java.exe --module-path ..\dependencies\javafx-sdk-11.0.2_windows\lib --add-modules javafx.controls,javafx.graphics,javafx.controls,javafx.base,javafx.media,javafx.swing,javafx.fxml Main"
        os.system(path)
    else:
        usage()


if __name__ == "__main__":
    main(sys.argv[1:])
