<div align="center">

# 👨‍💼 Employee Management System

### 🚀 Java | Spring Boot | Spring MVC | JSP | Hibernate | MySQL

A web-based Employee Management System built using Spring Boot and JSP, featuring employee registration, login functionality and employee record management.

<br/>

![Java](https://img.shields.io/badge/Java-8-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7.14-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-ORM-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

<br/>

<img src="https://skillicons.dev/icons?i=java,spring,mysql,hibernate,maven,html,git,github,eclipse" />

</div>

---

## 📌 Project Overview

The Employee Management System is a Java-based web application developed using Spring Boot, Spring MVC, Hibernate and MySQL.

It provides a simple interface to register employees, perform login operations and display employee information through a dashboard.

The application follows a layered architecture to separate controller, service, repository and entity responsibilities.

## ✨ Features

- 🏠 **Home Page:** Navigation to registration and login pages.
- 📝 **Employee Registration:** Register employees with personal and login details.
- 🔐 **Login Functionality:** Login using username and password.
- 📊 **Employee Dashboard:** Display employee records retrieved from MySQL.
- 📧 **Duplicate Email Check:** Service-layer logic checks whether an email already exists.
- 💾 **Database Integration:** Persist employee records using Hibernate.
- 🌐 **JSP-based UI:** Dynamic web pages using JSP and JSTL.
- 🚀 **Automatic Browser Launch:** Opens the application in Chrome after startup.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 8 | Programming Language |
| Spring Boot 2.7.14 | Application Framework |
| Spring MVC | Web Layer |
| JSP | Frontend |
| JSTL | JSP Tag Library |
| Hibernate | ORM |
| Spring Data JPA | Persistence Dependency |
| MySQL 8 | Database |
| Maven | Build Management |
| Lombok | Boilerplate Reduction |
| Spring Boot Actuator | Application Monitoring |
| Spring Boot DevTools | Development Support |

## 🏗️ Project Architecture

```text
             Employee Management System
                         |
                         |
                  EmployeeController
                         |
                         |
                  EmployeeService
                         |
                         |
                   EmployeeRepo
                         |
                         |
                   Hibernate
                         |
                         |
                      MySQL
```

### 📂 Package Structure

```text
src/main/java/com/example
│
├── Application.java
│
├── config
│   └── BrowserLauncher.java
│
├── controller
│   └── EmployeeController.java
│
├── entity
│   └── Employee.java
│
├── repository
│   ├── EmployeeRepo.java
│   └── EmployeeRepoImpl.java
│
└── service
    ├── EmployeeService.java
    └── EmployeeServiceImpl.java
```

### 🎨 JSP Pages

```text
src/main/webapp
│
├── index.jsp
├── register.jsp
├── login.jsp
├── dashboard.jsp
└── success.jsp
```

## 🔄 Application Workflow

1. User opens the application home page.
2. User selects Registration or Login.
3. Registration form collects employee information.
4. Employee details are processed by the Controller and Service layers.
5. Hibernate saves employee information into MySQL.
6. Login credentials are checked by the service layer.
7. On successful login, the dashboard displays employee records.

## 🌐 Application Endpoints

| HTTP Method | Endpoint | Description |
|---|---|---|
| GET | `/` | Home Page |
| GET | `/reg` | Employee Registration |
| GET | `/log` | Login Processing |
| GET | `/dashboard` | Employee Dashboard |

## 🗄️ Database Configuration

**Database:** MySQL

**Database Name:** `springboot`

Create the database:

```sql
CREATE DATABASE springboot;
```

Configure your local `application.properties`:

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.url=jdbc:mysql://localhost:3306/springboot

spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

spring.jpa.hibernate.ddl-auto=update

spring.mvc.view.suffix=.jsp
```

Set your database credentials using environment variables before running the application.

## ⚙️ How to Run the Project

### Prerequisites

- JDK 8
- MySQL Server
- Eclipse / Spring Tool Suite
- Maven

### Steps

**1. Clone the repository**

```bash
git clone https://github.com/YogeshwarKadam4393/SpringBoot.git
```

**2. Navigate to the project**

```bash
cd SpringBoot/01_EmployeeManagementProject
```

**3. Create the database**

```sql
CREATE DATABASE springboot;
```

**4. Configure database credentials**

Update `src/main/resources/application.properties` with your local database configuration.

**5. Run the application**

On Windows:

```cmd
mvnw.cmd spring-boot:run
```

**6. Open in browser**

```text
http://localhost:8080
```

## 🔒 Security Notes

- The current learning implementation uses hardcoded `admin/admin` login credentials.
- Passwords are currently stored and displayed as plain text.
- This implementation is intended for learning purposes and requires security improvements before production use.

## 👨‍💻 Author

**Yogeshwar Ashok Kadam**

Java Developer | Spring Boot | Hibernate | SQL

[![GitHub](https://img.shields.io/badge/GitHub-YogeshwarKadam4393-181717?style=for-the-badge&logo=github)](https://github.com/YogeshwarKadam4393)

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-0A66C2?style=for-the-badge&logo=linkedin)](https://linkedin.com/in/yogeshwar-kadam-94b649258)

---

<div align="center">

### ⭐ Learning by Building, Growing by Improving!

**Made with ❤️ by Yogeshwar Kadam**

</div>
