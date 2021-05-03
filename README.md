# delombok-example
Example of delombok Errors with Modules

The Lombok plugin and delombok are not working correctly with modules. I've created
a sample project at https://github.com/bradleylarrick/delombok-example.git.

I'm using Maven 3.8.1, Lombok 1.18.20 and JDK 11 (the same behavior exhibits with JDK 16).

Without the module-info.java file the delombok process works correctly.  When I add
the module-info.java file the lombok plugin errors saying that the modules referenced in
module-info.java can't be found:

src\main\java\module-info.java:24: error: module not found: com.fasterxml.jackson.annotation
src\main\java\module-info.java:25: error: module not found: com.fasterxml.jackson.core
 .
 .
 .

I get the same errors when I try running the delombok from the command line using the same arguments
the plugin uses.

I can get the command line delombok to work if I change the sourcepath to point to the lombok-enabled
code (src/main/lombok) instead if the standard code (src/main/java). This presents the problem of
requiring all classes the lombok-enabled code reference to be in the src/main/lombok path.

I tried changing the command line script to add the jar files to the module-path instead of the classpath,
but then I get the following errors on the lombok-enabled classes:

C:\projects\delombok-example\src\main\lombok\org\larrick\datagen\data\Address.java:7: error: file should be on source path, or on patch path for module
C:\projects\delombok-example\src\main\lombok\org\larrick\datagen\data\Ethnicity.java:7: error: file should be on source path, or on patch path for module
 .
 .
 .

Please advise.
