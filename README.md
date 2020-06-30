# 2020 SOFTWARE ENGINEERING PROJECT - SANTORINI

This is an implementation of the board game "Santorini" created by group #60 of the software engineering course (year 2020) of the "Politecnico di Milano"

## What we have achieved

1.	4 UML are been written (not generated) with plantUML
		*	A complete UML with fully written methods
		*	A high-level simplified UML for each of model, view and controller

2.	The complete rules are been implemented

3.	The GUI of the client-side are been implemented with Swing

4.	Two advanced functionality are been implemented
		1.	5 advanced god are available:
				1.	Zeus
				2.	Triton
				3.	Chronus
				4.	Hestia
				5.	Poseidon
		2.	Persistence are been implemented.
		
5.  The tests are been done only for the game logic as suggested

## How to build and start the game

In order to create the jar file you simply have to package the project with maven using 
```shell script
mvn package
```
The jar file will be created in the directory of the deliveries in a subfolder called jar.
In order to run the jar once created simply open a terminal and run the following commands:
```shell script
cd deliveries/jar
java -jar PS60.jar
```
Maven and java is requested in order to build and run this application

## How to play

In order to start to play you have to run a server and 2 or 3 clients.
As you run the exported jar file press 0 to run server and 1 to run one client.
If you have chosen to run the client you have to select between CLI (press 1) and GUI (press 0).
We suggest playing it in GUI. As the game starts you will enter your nickname and birthday.
You will not have the chance to do an illegal move. 
You only have to select your move that you want to do from a range of possible moves calculated by the server.
A wrong input will only involve in a new try to enter a right input.

## Built with

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Simone Ghidoli** - [Simone-Ghidoli](https://github.com/Simone-Ghidoli)
* **Nicolò Giannini** - [Nicog97](https://github.com/Nicog97)
* **Vincenzo Greco** - [Buba98](https://github.com/Buba98)