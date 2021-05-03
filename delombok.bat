@echo off

set REPO=C:\Users\bradley\.m2\repository

set LOMBOK=%REPO%\org\projectlombok\lombok\1.18.20\lombok-1.18.20.jar

set cp=%LOMBOK%
set cp=%cp%;%REPO%\com\fasterxml\jackson\core\jackson-annotations\2.12.3\jackson-annotations-2.12.3.jar
set cp=%cp%;%REPO%\com\fasterxml\jackson\core\jackson-core\2.12.3\jackson-core-2.12.3.jar
set cp=%cp%;%REPO%\com\fasterxml\jackson\core\jackson-databind\2.12.3\jackson-databind-2.12.3.jar
set cp=%cp%;%REPO%\com\fasterxml\jackson\dataformat\jackson-dataformat-csv\2.12.3\jackson-dataformat-csv-2.12.3.jar
set cp=%cp%;%REPO%\com\fasterxml\jackson\dataformat\jackson-dataformat-xml\2.12.3\jackson-dataformat-xml-2.12.3.jar
set cp=%cp%;%REPO%\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.12.3\jackson-datatype-jsr310-2.12.3.jar
set cp=%cp%;%REPO%\com\fasterxml\jackson\module\jackson-module-jaxb-annotations\2.12.3\jackson-module-jaxb-annotations-2.12.3.jar
set cp=%cp%;%REPO%\jakarta\xml\bind\jakarta.xml.bind-api\2.3.2\jakarta.xml.bind-api-2.3.2.jar
set cp=%cp%;%REPO%\jakarta\activation\jakarta.activation-api\1.2.1\jakarta.activation-api-1.2.1.jar

java -jar %LOMBOK% delombok src\main\lombok -d target\generated-sources\delombok --classpath %cp% --sourcepath src\main\lombok --verbose
