#  Openclassrooms JAVA Path
## Project 7: develop the new IT system of a public librairies network


### **Presentation**

This application is the 7th project of the [Openclassrooms JAVA learning path](https://openclassrooms.com/en/paths/88-developpeur-dapplication-java).
This project is about developing the new IT system of a public librairies network. 
It includes 1/a webapp for the users to perform book research and extend loans and 2/a batch which automates the sending of reminder emails for overdue books.

The web app is also planned to be later released as a mobile version (ios and android). Further, another app will enable the librairy staff to perfom the loan and user management. 

The architecture choosen for this system is a Service Orientated Architecture: the business logic and the data layer are performed by a SOAP web service and all above mentionned apps consume or will consume data from this webservice. 

**Warning: this repo contains only the webservice!**
[Click here to move to the repo of the webapp and the batch](https://github.com/JulienDeBerlin/p7-librairyManagement-webapp)


### **Features**

**Book research (managed by wsdl "books") :**
* resarch of book based on criteria such as author's name, book title (or part of it) and/or keyword. At least one of this criteria is required to perform
the research. The criteria can also be combined. 
* indication of the amount of books available with location

**Loan and customer management (managed by wsdl "customersAndLoans") :**
* login a user
* refresh a user
* register a new loan
* register a return
* extend a loan
* loan monitoring: get a list of all open loans
* loan monitoring: get a list of overdue open loans
* loan monitoring: get a list of open loans still within the deadline
* loan monitoring: get a list of the open loans that have already been extended at least once

For more details on the input and output of each service, please refere to the WSDL files. 


### **Return values for loan management**
Please note that for some requests a code (int) will be generated as response and transmit to the client. 
Though the meaning of these codes can easily be found in the javadoc, here below a short summary: 

**extendLoanResponse**

1 = success (loan has been extended),
0 = failure (membership expired),
-1 = failure (max amount of extensions reached),
-2 = failure (loanId not correct)

**registerLoanResponse**

1 = success (loan is possible and registered),
0 = failure (membership expired),
-1 = failure (book Id not correct),
-2 = failure (customer Id not correct),
-3 = failure (book not available)

**registerBookReturnResponse**

1 = success (book return has been validated),
0 = failure (no loan active with for this book id)
-1 = failure (bookId is not a valid book id)

**loginCustomerResponse and refreshCustomerResponse**

returns a Customer object with a nested collection of Loans. Please note that the collection included ALL the loans made by the customer (open loans AND returned loans). By all open loans, dateBack = 1900-01-01. 

### **Configuration and stack**
* This application is a multi-module **Maven Spring Boot 2** project.
* It embarks a **Tomcat server 9.0.17**
* The database used in developement is **PostgreSQL 9.6.** 
* the data layer is managed with **Spring Data / Hibernate**
* the creation of the webservice uses **Spring WS and the jaxb maven plugin**. 

**Warning: this webservice has been developped with and for Java 8. As Jaxb is not fully compatible with more recent version, you might experience troubles if
you try to run the JAR with Java 9 or more. It is also greatly recommended to run it with Java 8. For more infos on this issue, [click here.](https://www.jesperdj.com/2018/09/30/jaxb-on-java-9-10-11-and-beyond/)**


### **Install and run the webservice on your PC**

1/ clone this repository on your PC

2/ create a database on PostgreSQL and use the dump of the demo-database available in this repo to import all the data in your database

3/ build the project with Maven ( "mvn package" in the parent maven module). A runnable jar will be created in the target folder of the module "p7-webservice-app-webservice". 

4/ in the same target folder, in the subfolder "classes", you will find the application.properties file. Open this file and declare your database setting by overriding the values. 

5/ you can now run the jar in the terminal. Don't forget to point to the updated properties file in the command. For instance: 

```
Admins-MBP:~ admin$ /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java -jar /Users/admin/Documents/PROGRAMMING/OPENCLASSROOMS/P7/Apps_P7/WebserviceApp/p7-webservice-app-webservice/target/p7-webservice-app-webservice-1.0-SNAPSHOT-spring-boot.jar --spring.config.location=file:///Users/admin/Documents/PROGRAMMING/OPENCLASSROOMS/P7/Apps_P7/WebserviceApp/p7-webservice-app-webservice/target/classes/application.properties 
```

6/ Enjoy!


### **Functional configuration**

In the application.properties file you have access to following settings:
* max amount of loan extensions (aka. how many times can a loan be extended)
* duration of a loan in days
* duration of a loan extension in days
