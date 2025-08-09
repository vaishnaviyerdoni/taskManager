
# Task Manager


### Description

Task Manager is personal productivity application that lets users manage tasks and attach notes for their tasks.
It is built using Spring Boot for Backend, SQL Server for Database and HTML, CSS and JavaScript for Frotend UI.
## Table of Contents
|Sr. No.| Content                    | 
| ----- | ---------------------------| 
| 1     | Introduction               | 
| 2     |  Features                  | 
| 3     | Tech Stack                 |
| 4     |Installation Instructions   |
| 5     |Project File structure      |
| 6     |     Usage                  |
| 7     |API Endpoints               |





## Introduction
**Task Manager** is a personal productivity web Application that allows users to manage their tasks. Users can also add notes to any of their tasks for additional information.

The application is built in RESTful backend using *Java Programming Language* and *Springboot*, *SQL Server Database* was used for data storage and Frontend is built using HTML, CSS and JavaScript.

The Application focuses on enabling users to manage their daily tasks. Users can optionally add notes to tasks for additional information.
## Tech Stack

- **Backend:** Java Programming Language, Spring-Boot (RESTfful API)
- **Database:** SQL Server
- **Frontend:** HTML, CSS, JavaScript
- **Tools:** Postman(API testing), Maven(Build Tool)
## Database Schema

The database structure is defined in the `Database/taskManager.sql` file.  
It includes three tables:
- `users`: Stores user information.
- `tasks`: Stores tasks created by users.
- `notes`: Stores notes related to tasks.

You can execute the script in SQL Server to initialize the database.

## Features

- User Registration and User Authentication
- User Dashboard
- Add, view, update and delete tasks
- Add, view, update and delete notes associated with any task
- Password, email update methodology
- User deletion
- Persistant data storage using SQL Server
- RESTful API using SpringBoot
- User Friendly and visually pleasing user interface



## File Structure
--src/main/java/com/taskManager
- /service           ---Business Logic
- /controller        ---RESTful API endpoints
- /repository        ---CRUD operations
- /Exceptions        ---Custom Written Exceptions
- /model             ---POJO Classes


--src/main/resources/static
- /Image             ---Background Image for Webpage
- favicon            ---favicon for webpage
- style.css          ---stylesheet
- /user              ---HTML and JavaScript

--/Database            ---taskManager.sql (DB Schema)

## Installation Instructions

1. Clone the repository.
2. Import the project to your preferred IDE, I used VSCode, but you can use whichever IDE you're comfortable with.
3. Set up the database in SQL server using the taskManager.sql from the Database folder.
4. Run the TaskManagerApplication.java from your preferred IDE.
5. Open the User Registration page in your browser
```bash
http://localhost:8080/user/Register.html
```
6 Once successfully registered, you'll be redirected to login page and you can login to begin using the taskManager!
```bash
http://localhost:8080/user/Login.html
```
7. You're ready to use the Task Manager!

    
## Usage
Visit the following link to register:
```bash
http://localhost:8080/user/Register.html
```
Once registered successfully, you can login for using the taskManager
Make sure to note and remember your userId which you can get from alert message after successful registration.

You can navigate the dashboard using the button and each buttons will take you to your desired page,.
You can add tasks, Once tasks are added you can view them or delete and update them.
If you want to add notes to your tasks for extra information or for any other purpose, you can add notes when viewing your notes
You can also view, add, delete or update the notes associated with any of the tasks.


## API Endpoints

## 1. User

|`Method`|     `Endpoints`          |    `Description`    |
|:-------|:-------------------------|:--------------------|
|`GET`   |`/api/user/{userId}`      | `Get all Users`     |
|`POST`  |`/api/user/register`      |`User Registration`  |
|`POST`  |`/api/user/login`         |`User authentication`|
|`PUT`   |`/api/user{userId}/email`   |`Update Email`     |
|`PUT`   |`/api/user/{userId}/password`|`Update password` |  
|`DELETE`|`/api/user/{userId}`      |`Delete user account`|
 
## 2. Task

|`Method`|     `Endpoints`          |    `Description`    |
|:-------|:-------------------------|:--------------------|
|`GET`   |`/api/task/{userId}`      |  `Get all Tasks`    |
|`POST`  |`/api/task/addtask`       |   `Adding task`     |
|`PUT`   |`/api/task/{taskId}`      |   `Update Email`    |
|`DELETE`|`/api/task/{taskId}`      |   `Delete task`     |
 
## 3. Note

|`Method`|     `Endpoints`          |    `Description`    |
|:-------|:-------------------------|:--------------------|
|`GET`   |`/api/note/{taskId}`      |  `Get all Notes`    |
|`POST`  |`/api/note`               |   `Adding Note`     |
|`PUT`   |`/api/note/{notesId}`     |   `Update Note`     |
|`DELETE`|`/api/note/{notesId}`     |   `Delete Note`     |
 
## References

#### 1.) Favicon

The favicon for the project was generated using the following favicon generated.

```http
    https://favicon.io/favicon-generator/
```

#### 2.) Webpage Background

The background image for the Webpage was downloaded from Unsplash.
Unsplash is a website that provides a vast collection of free and high resolution images

You can download the image from
```http
    https://unsplash.com/photos/black-laptop-computer-on-brown-wooden-table-rLkZVjaqW2o
```