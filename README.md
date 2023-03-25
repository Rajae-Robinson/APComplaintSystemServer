# UTECH Complaint System Server

This is the server application for UTECH Complaint and Query System

**Recommended IDE**: Eclipse

## Project Dependecies
Ensure that the following is in your classpath:
- Hibernate (5.6.5): https://sourceforge.net/projects/hibernate/files/hibernate-orm/5.6.5.Final/
- MySQL ConnectorJ: https://dev.mysql.com/downloads/connector/j/
- Apache Log4j

### Server Actions
| Action          | Description                                                       |
|-----------------|-------------------------------------------------------------------|
| authenticate    | Authenticate user login credentials                               |
| getStudents     | Retrieve all student records from the database                    |
| getAdvisors     | Retrieve all advisor records from the database                    |
| getSupervisors  | Retrieve all supervisor records from the database                 |
| getComplaints   | Retrieve all complaint records from the database                  |
| createComplaint | Create a new complaint record in the database                      |
| findComplaint   | Retrieve a specific complaint record from the database by ID      |
| respondComplaint| Update the status of a specific complaint record in the database  |
| deleteComplaint | Delete a specific complaint record from the database by ID        |
| getQueries      | Retrieve all query records from the database                       |
| createQuery     | Create a new query record in the database                           |
| findQuery       | Retrieve a specific query record from the database by ID           |
| respondQuery    | Update the status of a specific query record in the database       |
| deleteQuery     | Delete a specific query record from the database by ID             |

