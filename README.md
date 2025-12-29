# 📚 Library Management System

A web-based **Library Management System** developed using **JSP, Servlets, JDBC, and MySQL**.  
The system supports **role-based login** for **Admin** and **Student**, allowing efficient book management and issuing.

---

## 🔹 Features

### 👨‍💼 Admin
- Secure admin login
- Add new books
- View all books
- Role-based access control
- Logout functionality

### 🎓 Student
- Student login using institute email (`@kiet.edu`)
- Auto-registration if student does not exist
- View available books
- Issue books
- View issued books
- Automatic return date generation (7 days)
- Logout functionality

---

## 🔹 Technologies Used

- **Frontend**: JSP, HTML, CSS
- **Backend**: Java Servlets
- **Database**: MySQL
- **Connectivity**: JDBC
- **Server**: Apache Tomcat
- **Architecture**: MVC (Model–View–Controller)
- **Session Management**: HTTP Sessions

---

## 🔹 Project Architecture (MVC)

- **Model**
  - Database (MySQL)
  - `DBConnection.java`
- **View**
  - JSP Pages:
    - `login.jsp`
    - `dashboard.jsp`
    - `addBook.jsp`
    - `logout.jsp`
- **Controller**
  - Servlets:
    - `LoginServlet`
    - `AddBookServlet`
    - `IssueBookServlet`
    - `StudentViewBooksServlet`
    - `IssuedBooksServlet`
    - `ReturnBookServlet`

---

## 🔹 Database Tables

- `admin`
- `students`
- `books`
- `issued_books`

---

## 🔹 Login Rules

- **Admin**
  - Credentials verified from `admin` table
- **Student**
  - Only `@kiet.edu` email allowed
  - Auto-registers if student does not exist

---

## 🔹 How It Works

1. User logs in via `login.jsp`
2. Request goes to `LoginServlet`
3. Servlet verifies credentials using JDBC
4. Session is created based on role
5. User redirected to `dashboard.jsp`
6. Role-based access is enforced for all actions

---

## 🔹 Security Features

- Role-based authorization
- Session validation
- SQL Injection prevention using `PreparedStatement`
- Restricted page access without login

---

## 🔹 Setup Instructions

1. Install **Apache Tomcat**
2. Install **MySQL**
3. Create database:
   ```sql
   CREATE DATABASE library_system;
