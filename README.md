# Board

### Introduction

This program is a sample of a wide verity of Object-Oriented Design Patterns including
* Observer
* Builder
* Command
* Factory
* Abstract Factory
* Strategy
* Lazy Loading
* Iterator
* Singleton
* Null Object


### How to run

First step is to build the application with Maven, this will create two jar files, _Board-1.0-1-jar-with-dependencies.jar_ will have all 
required dependencies.  

`mvn clean package`  

Then save commands in a text file and run the application by passing full path of the file as program arguments. For example  

`java -jar ./target/Board-1.0-1-jar-with-dependencies.jar ./src/main/resources/board1.txt`

By default, board has 5 columns and rows, in order to change dimensions of the board, set `number_of_columns` and `number_of_rows` in environment variable
