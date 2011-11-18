@echo off
set M2_REPO=C:\dev\apache\maven\3.0.3\repository
set COMMONS_CLI_DIR=%M2_REPO%\commons-cli\commons-cli\
set COMMONS_CLI_VER=1.2
set COMMONS_CLI_JAR=%COMMONS_CLI_DIR%\%COMMONS_CLI_VER%\commons-cli-%COMMONS_CLI_VER%.jar
set COMMONS_LANG_DIR=%M2_REPO%\commons-lang\commons-lang\
set COMMONS_LANG_VER=2.6
set COMMONS_LANG_JAR=%COMMONS_LANG_DIR%\%COMMONS_LANG_VER%\commons-lang-%COMMONS_LANG_VER%.jar
set COMMONS_COLL_DIR=%M2_REPO%\commons-collections\commons-collections\
set COMMONS_COLL_VER=3.2.1
set COMMONS_COLL_JAR=%COMMONS_COLL_DIR%\%COMMONS_COLL_VER%\commons-collections-%COMMONS_COLL_VER%.jar

java -cp %COMMONS_CLI_JAR%;%COMMONS_LANG_JAR%;%COMMONS_COLL_JAR%;./target/classes org.rip.regex.grep %*
