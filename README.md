BH Rest Cerner
--------------
>This project exports an Akka Http REST service that selects diet nutrition data from the Cerner database based on
>patient and encouter id values.

Test
----
1. sbt clean test

Run
---
1. sbt run

Package
-------
>Using https://sbt-native-packager.readthedocs.io/en/stable/
1. sbt universal:packageZipTarball | windows:packageBin

Deploy
------
1. unpack bh-rest-cerner-0.1-SNAPSHOT.tgz
2. ./bin/bh-rest-cerner
3. read log at ./bin/target/*.txt
4. shutdown with ctrl-C