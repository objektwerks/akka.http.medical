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
>Run app default host ( 127.0.0.1 ) and port ( 7979 ). Or pass in host and port.
1. copy / paste ./bh.rest.cerner/target/universal/bh-rest-cerner-0.1.tgz to target directory
2. unpack bh-rest-cerner-0.1.tgz
3. cd to app root directory
4. chmod +x ./bin/bh-rest-cerner
5. ./bin/bh-rest-cerner & | ./bin/bh-rest-cerner myhost 7272 &
6. note pid
7. read log at ./bh.rest.cerner.log.${timeStamp}.txt
8. kill pid

Curl
----
1. curl http://127.0.0.1:7979/api/v1/diet/19106271/74798395
2. view json