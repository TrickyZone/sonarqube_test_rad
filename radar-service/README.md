# radar-service

## Introduction

* Author: Knoldus
* Swagger: [here](http://localhost:8080/swagger-ui/)

## How Stuff Works

## Requirements

| Framework                                                                          | Version |
|------------------------------------------------------------------------------------|---------|
| Gradle Wrapper                                                                     | 6.8.1   |
| Java                                                                               | 11      |
| Spring Boot                                                                        | 2.6.3   |
 
## Databases and Local Projections

This service maintains its own database `my_example_service`

## How to create and start the local database
1. Go inside the bin directory.
2. Make sure port 5432 is free.
3. Run `start-test-db.sh` by using command `./start-test-db.sh`

This will create the local instance of the database inside the docker container `radar-service-db` 
and will run the sql scripts which are present in the sql directory which will create the required test data.
## Project structure

    .
    ├── bin                         : Shell scripts for starting and creating local database.
    ├── docker                      : Docker-related files for testing, building and deploying the service.
    │   └── test                    : Test Docker files.
    ├── README.md                   : This file you are reading right now ;).
    ├── sql                         : General database scripts for starting a test database.
    └── src                         : Sources.
        ├── main                    : Main sources.
        └── test                    : Test sources.

## Note
To include the api tests in build, make sure first your database is created successfully and is running locally 
and change the pattern from `**/*UTest` to `**/*Test` in the configuration of maven surefire plugin in `pom.xml`