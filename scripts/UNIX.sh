#!/bin/bash
cd ..
mvn package
cd deliveries/jar
java -jar PS60.jar
PAUSE
