# Project Setup

The Project setup involves setting up 4 components

1. NodeJs Server and dependencies.
2. Stress-tester Java program Setup
3. MongoDb
4. Python Viz setup

--------------------------------------------------------------------------------------------------------------------------------------------

## NodeJs Server and dependencies

1. Install npm 8.5.0 (https://www.npmjs.com/package/npm/v/8.5.0)
2. Install Nodejs v16.14.2 (https://nodejs.org/en/blog/release/v16.14.2/)

After installing npm and Nodejs. Go into the web-server and execute the following commands.

npm install express
npm install morgan
npm install image-js
npm install flamebearer

After installing all these depencies run node server.js and check if the server startups.
If the server startup up the setup is good.

--------------------------------------------------------------------------------------------------------------------------------------------

## Stress-tester Java program Setup

Install Java 14 or higher for running the stress-tester tool.

The setup for this part is simple.

Install https://www.jetbrains.com/idea/download/#section=windows for windows / mac / Linux and import the folder
stress-tester into intellij as a gradle project. (This is a gradle project and intellij has inbuilt gradle integration)

After importing the gradle project run gradle build if the gradle build is successful then this step is complete.

Reference: https://www.jetbrains.com/help/idea/gradle.html#gradle_jvm

--------------------------------------------------------------------------------------------------------------------------------------------

## Mongodb setup

Install mongodb v5.0.6

https://www.mongodb.com/download-center/community/releases

--------------------------------------------------------------------------------------------------------------------------------------------

## Python Viz setup

1. Install Python3 
2. Install pip3

After completing the above steps go into visualizer folder and execute the folling command

pip3 install requirements.txt