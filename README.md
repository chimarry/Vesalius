# Vesalius

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

The purpose of the application is to show how client-server architecture works, and how server applications can interact with each other using Java-based technologies. This project enables a developer to get familiar with:  
REST, SOAP, Secure Sockets, RMI, 5 ways of serialization (GSON, Java serialization, KRYO, Text, XML), JavaFX and In-Memory database (Redis).
Architecture is shown on the image bellow:  
![Architecture](/Architecture.png)  
## Some features
- Central register:
    - used to track infected/potentially infected individuals and to notify 
potential contacts of the infected person (a person is considered potentially infected if it has spent
n minutes with the infected person on distance less than N meters, where n, N are configurable parameters).
- User application:
  - Registration and login with a token using SOAP
  - Unregister and logout from the Central register
  - Mark current location on a map
  - Send a message to medical staff
  - Send medical documents to medical staff
  - See activity in the application (login/logout time)
  - Get notification about possible infection with the option to see the map where contact happened
  - View previously visited location on a specific date
-  Medical staff:
   - If available, respond to the user's message
   - Send a message to medical staff (group)
   - Download user's documents
   - Change the status of user's infection
   - Block user
- Chat:
  - Enable unicast and multicast communication with active medical staff
- File server:
  - Enable upload/download files with RMI

## Building and running application
To run the application, Java 8 must be installed, and some Web server (like Tomcat 9) must be configured.