## Sample API framework with Java, cucumber and RestAssured

This project uses Java with Gradle. Before running it, please verify that you have java installed on your computer, same with gradle.
To verify it you can run in your command console:

    java --version
    gradle -v

Note that this project uses version 7.3.3 of gradle, which is compatible with java 17. If you need to install gradle, you can go to:

    https://gradle.org/install/

and follow the installation instructions.


## Structure

This project is using Cucumber, a library that defines the scenarios to test the steps to be executed. You will find the features files in the path:

    src/test/resources/features

The steps where the steps are defined can be found in:

    src/test/java/Steps


## Running this project

To run this project, please go to the directory:

    src/test/java/Runner/Runner.java

In the Runner class you can run the project and see the results by console.