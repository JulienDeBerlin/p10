#  Openclassrooms JAVA Path

## Project 10: update an existing project

### **Presentation**
This application is the 10th project of the [Openclassrooms JAVA learning path](https://openclassrooms.com/en/paths/88-developpeur-dapplication-java).
The goal of this project is to add new features to the librairy management system developed at project 7. 
See below the description of project 7. 

### **New features**
The new features include :
* a reservation systeme : the users have the possibility to make a reservation for a book which is currently not available. 
**Warning: feature only available on branch develop as of 09/09/19**

### **Bug fix**
One functionnal bug has been fixed: loan extension is now only possible if the loan is NOT already overdue.  


---

### **Description of the initial project (project 7)**

This project was about to develop the new IT system of a public librairies network. 

It includes **two clients applications**: 
* 1/ **a webapp** for the users to perform book research and extend loans
* 2/ **a batch** which automates the sending of reminder emails for overdue books.

and **one webservice**: 
The architecture choosen for this system is a Service Orientated Architecture: the business logic and the data layer are performed by a SOAP web service and all above mentionned apps consume or will consume data from this webservice. 

Project 7 was spread over two git repos: [one for the webservice](https://github.com/JulienDeBerlin/p7-librairyManagement-webservice)
and [one for the webapp and the batch](https://github.com/JulienDeBerlin/p7-librairyManagement-webapp). For purpose of project 10, both repos has been merged in this single repo. 

---

### **Configuration and stack**
* This application is a multi-module **Maven Spring Boot 2** project.
* It embarks a **Tomcat server 9.0.17**
* The database used in developement is **PostgreSQL 9.6.** 
* the data layer is managed with **Spring Data / Hibernate**
* the creation of the webservice uses **Spring WS and the jaxb maven plugin**. 

**Warning: this webservice has been developped with and for Java 8. As Jaxb is not fully compatible with more recent version, you might experience troubles if
you try to run the JAR with Java 9 or more. It is also greatly recommended to run it with Java 8. For more infos on this issue, [click here.](https://www.jesperdj.com/2018/09/30/jaxb-on-java-9-10-11-and-beyond/)**


---

### **Run the webservice on your PC**

1/ clone this repository on your PC

2/ the webservice can be run with 2 maven profiles: "dev" for test and demonstration purpose, "prod" for prodution. 

* dev profile (activated by default)
If your intention is only to test the appl, use the dev profile. It's more convenient because it uses an embedded H2-Database, so you don't have to worry with DB configuration

* prod profile
If you want to connect the app to a local database, use the prod profile. In this case, you will have to create a database on PostgreSQL. You can use as template the dump of the demo-database available in this repo to import all the data in your database

3/ build the project with Maven ( "mvn package" in the parent maven module with -P prod if you want to activate the prod profile). A runnable jar will be created in the target folder of the module "p7-webservice-app-webservice". 

4/ in the same target folder, in the subfolder "classes", you will find the application-prod.properties file. If you want to connect the app to your own DB, open this file and declare your database setting by overriding the values. 

5/ you can now run the jar in the terminal. Don't forget to point to the updated properties file in the command. 

6/ Enjoy!

---


### **Run the webapp on your PC**

1/ clone this repository on your PC

2/ build the project with Maven ( "mvn package" in the parent maven module). 
A runnable jar will be created in the target folder of the module "p7-webapp-presentation". 

3/ in the same target folder, in the subfolder classes, you will find the application.properties file. 
If you wish, you can here change the port and the application context. 
Actual values are port=8088 and application context=/p10

4/ Make sure the webservice that provides the data to the webapp has been started on http://localhost:8080

5/ you can now run the jar in the terminal. If you have modified the properties file, don't forget to point to the updated properties file in the command line.

6/ Based on the current settings of the application.properties file, 
you can access the webapp on following url: http://localhost:8088/p10/

7/ Enjoy!


