#  Openclassrooms JAVA Path
## Project 7: develop the new IT system of a public librairies network


### **Presentation**

This application is the 7th project of the [Openclassrooms JAVA learning path](https://openclassrooms.com/en/paths/88-developpeur-dapplication-java).
This project is about developing the new IT system of a public librairies network. 
It includes 1/a webapp for the users to perform book research and extend loans and 
2/a batch which automates the sending of reminder emails for overdue books.

The web app is also planned to be later released as a mobile version (ios and android). 
Further, another app will enable the librairy staff to perfom the loan and user management. 

The architecture choosen for this system is a Service Orientated Architecture: 
the business logic and the data layer are performed by a SOAP web service and 
all above mentionned apps consume or will consume data from this webservice. 

**Warning: this repo contains only the webapp and the batch!**
[Click here to move to the repo of the webservice](https://github.com/JulienDeBerlin/p7-librairyManagement-webservice)


### **Features**

**Webapp:**
* resarch of book based on criteria such as author's name, book title (or part of it) and/or keyword. 
At least one of this criteria is required to perform
the research. The criteria can also be combined. 
* indication of the amount of books available with location
* the users can login in to the member area and display the list of their current loans
* the users can extend their loan


**Batch:**
* The batch automates the sending of a reminder email to all users with overdue loans. 
* The content of the reminder email can be customized based on the overdue date. 
3 levels of emails are possible, for instance very friendly reminder for recent overdue date 
up to more assertive email for older overdue date. 


### **Configuration and stack**
* This application is a multi-module **Maven Spring Boot 2** project. 
* The webapp module and the batch module share the same maven parent module.
* It embarks a **Tomcat server 9.0.17**
* the webservice client uses **Spring WS and the jaxb maven plugin**. 
* The view layer based on **Spring MVC and Thymeleaf**

**Warning: this app/batch have been developped with and for Java 8. 
As Jaxb is not fully compatible with more recent version, you might experience troubles if
you try to run the JAR with Java 9 or more. It is also greatly recommended to run it with Java 8. 
For more infos on this issue, [click here.](https://www.jesperdj.com/2018/09/30/jaxb-on-java-9-10-11-and-beyond/)**


### **Run the webapp on your PC**

1/ clone this repository on your PC

2/ build the project with Maven ( "mvn package" in the parent maven module). 
A runnable jar will be created in the target folder of the module "p7-webapp-presentation". 

3/ in the same target folder, in the subfolder classes, you will find the application.properties file. 
If you wish, you can here change the port and the application context. 
Actual values are port=8081 and application context=/p7 

4/ Make sure the webservice that provides the data to the webapp has been started on http://localhost:8080

5/ you can now run the jar in the terminal. 

For instance: 
```
Admins-MBP:~ admin$ /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java -jar /Users/admin/Documents/PROGRAMMING/OPENCLASSROOMS/P7/Apps_P7/Webapp/p7-webapp-presentation/target/p7-webapp-presentation.jar
```

If you have modified the properties file, don't forget to point to the updated properties file in the command:
```
Admins-MBP:~ admin$ /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java -jar /Users/admin/Documents/PROGRAMMING/OPENCLASSROOMS/P7/Apps_P7/Webapp/p7-webapp-presentation/target/p7-webapp-presentation.jar --spring.config.location=file:///Users/admin/Documents/PROGRAMMING/OPENCLASSROOMS/P7/Apps_P7/Webapp/p7-webapp-presentation/target/classes/application.properties
```

6/ Based on the current settings of the application.properties file, 
you can access the webapp on following url: http://localhost:8081/p7/

7/ Enjoy!


### **Launch the batch from your PC**

1/ clone this repository on your PC

2/ build the project with Maven ( "mvn package" in the parent maven module). 
A runnable jar will be created in the target folder of the module "p7-batch". 

3/ in the same target folder, in the subfolder classes, you will find 2 properties files: 
* application.properties where you can set up among other things the 3 reminder levels or the email address used to send the reminder emails
* messages.properties where you can set up the emails content.
You can override all these values as you wish. 

**Warning: with Java8, the default encoding for properties.file is ISO-8859-1. 
In order to have caracters like é è ê à and so on displayed properly, you need to escape ALL OF THEM! 
You can do that easly by using this [converting tool](https://www.mobilefish.com/services/unicode_escape_sequence_converter/unicode_escape_sequence_converter.php).**

4/ Make sure the webservice that provides the data to the webapp has been started on http://localhost:8080

5/ you can now launch the batch by running the jar in the terminal. Don't forget to point to the updated properties file in the command. For instance: 

```
Admins-MBP:~ admin$ /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java -jar /Users/admin/Documents/PROGRAMMING/OPENCLASSROOMS/P7/Apps_P7/Webapp/p7-batch/target/p7-batch-1.0-SNAPSHOT.jar --spring.config.location=file:///Users/admin/Documents/PROGRAMMING/OPENCLASSROOMS/P7/Apps_P7/Webapp/p7-batch/target/classes/messages.properties
```

6/ That's it!


