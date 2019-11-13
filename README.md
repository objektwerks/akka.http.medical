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
1. sbt universal:packageZipTarball | windows:packageBin