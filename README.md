BH Rest Cerner
--------------
>This project exports an Akka Http REST service that selects diet nutrition data from the Cerner database based on
>patient and encouter id values.

Test
----
1. sbt clean test

Run
---
>Run app locally, with optional host and port args, which default to: host = 127.0.0.1, port = 7979
1. sbt "run localhost 7676" | sbt run

Package
-------
>Using https://sbt-native-packager.readthedocs.io/en/stable/
1. sbt universal:packageZipTarball | windows:packageBin

Deploy
------
1. unpack bh-rest-cerner-0.1-SNAPSHOT.tgz
2. chmod +x ./bin/bh-rest-cerner
3. ./bin/bh-rest-cerner &
4. read log at ./bin/target/*.txt
5. kill pid