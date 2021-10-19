# Vesalius

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

The purpose of the application is to show how client-server architecture works, and  how server applications can interact with each other using Java based technologies. This project enables developer to get familiar with:  
REST, SOAP, Secure Sockets, RMI, 5 ways of serialization (GSON, Java serialization, KRYO, Text, XML), JavaFX and In-Memory database (Redis).
Architecture is shown on image bellow:  
![Architecture](/Architecture.png)  
## Some features
- Central register:
    - used to track infected/potentially infected individuals and to notify 
potential contacts of infected person (a person is considered potentially infected if it has spent
n minutes with infected person on distance less than N meters , where n,N are configurable paramteres).
- User application:
  - Registration and login with token using SOAP
  - Unregister and logout from Central register
  - Mark current location on map
  - Send message to medical staff
  - Send medical documents to medical staff
  - See activity in application (login/logout time)
  - Get notification about possible infection with option to see map where contact happened
  - View previous visited location on specific date
-  Medical staff:
   - If available, respond to user's message
   - Send message to medical staff (group)
   - Download user's documents
   - Change status of user's infection
   - Block user
- Chat:
  - Enable unicast and multicast communication with active medical staff
- File server:
  - Enable upload/dowload files with RMI

## Building and running application
In order to run application, Java 8 must be installed, and some Web server (like Tomcat 9) must be configured.