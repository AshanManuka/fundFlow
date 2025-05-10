# FundFlow Application (BackEnd)

A Loan Management System backend developed with Spring Boot and Log4j2 for logging.

##  Setup Instructions



1. Download the MySql dump by Clicking here:
2. Open Mysql CLI

   ```bash
   mysql -u root -p -e "CREATE DATABASE fundflow_db;"
   mysql -u root -p fundflow_db < fundflow_dump.sql



3. Clone the repository:
   ```bash
   git clone https://github.com/AshanManuka/fundFlow.git
   cd fundFlow

4. Clone the repository:
   ```bash
   mvn clean install
   mvn spring-boot:run

Postman API Documentation : https://documenter.getpostman.com/view/25541524/2sB2j999XE


