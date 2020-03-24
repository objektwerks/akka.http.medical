Akka Http Medical
-----------------
>This project exports an Akka Http REST service that selects diet data from a medical database based on
>patient and encounter id values.

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
