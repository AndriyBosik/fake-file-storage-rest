#Fake File Storage REST Service

This project doesn't save files actually.
Instead, it saves only file name and file size.

Elasticsearch uses as data source.

#How to run
In order to run this application you should have running Elasticsearch instance on your machine.
Specify `spring.elasticsearch` parameters in `application.properties` file and that's all.
You can now run project.