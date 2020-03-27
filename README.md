Akka Http Medical
-----------------
>This project exports an Akka Http REST service that selects diet data from a medical database based on
>patient and encounter id values.

SSL
---
>See:
1. https://blog.knoldus.com/create-a-self-signed-ssl-certificate-using-openssl/
2. https://blog.knoldus.com/how-to-create-a-keystore-in-pkcs12-format/

SSL Passwords
-------------
1. Server Key Passphrase: test
2. Challenge Password: test
3. Server PEM: test
4. Export Password: test

SSL Artifacts
-------------
>Located in src/main/resources
1. server.crt
2. server.csr
3. server.key
4. server.pem
5. keystore.pkcs12

Https
-----
>See ( https://doc.akka.io/docs/akka-http/current/server-side/server-https-support.html#using-https ) for details.

Test
----
1. sbt clean test

Run
---
>Run app locally, with optional host and port args, which default to: host = 127.0.0.1, port = 7676
1. sbt "run localhost 7676" **or** sbt run

Package
-------
>Using https://sbt-native-packager.readthedocs.io/en/stable/
1. sbt universal:packageZipTarball | windows:packageBin
