Akka Http Medical
-----------------
>This project exports an Akka Http REST service that selects diet data from a medical database based on
>patient and encounter id values.

Https
-----
>For details see:
1. https://doc.akka.io/docs/akka-http/current/server-side/server-https-support.html
2. https://lightbend.github.io/ssl-config/CertificateGeneration.html
>Also see x509 text file and directory.

Test
----
1. sbt clean test

Run
---
>Run app locally, with optional host and port args, which default to: host = 127.0.0.1, port = 7676
1. sbt "run localhost 7676" **or** sbt run

Curl
----
1. curl http://localhost:7676/api/v1/now

Package
-------
>Using https://sbt-native-packager.readthedocs.io/en/stable/
1. sbt universal:packageZipTarball | windows:packageBin