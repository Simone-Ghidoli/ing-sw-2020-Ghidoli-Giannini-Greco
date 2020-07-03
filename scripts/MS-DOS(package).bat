@echo off
title Santorini
cd ..
mvn package
cd deliveries/jar
java -jar PS60.jar
pause
