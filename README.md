# 📚 Library Management System using Java & JDBC

## 📌 Overview
This project is a **console-based Library Management System** developed using **Java and JDBC**.  
It demonstrates how Java applications interact with a **relational database** to perform real-world operations such as managing books, users, and borrowing transactions.

The project focuses on **CRUD operations**, **database connectivity**, and **transaction handling**.

---

## 🧠 Project Objective
The main goal of this project is to:
- Understand **JDBC (Java Database Connectivity)**
- Perform database operations using SQL through Java
- Simulate real-world library operations like borrowing and returning books

---

## ✨ Features Implemented

### 📖 Book Management
- Add new books to the library
- View available books
- Update book details
- Delete books

### 👤 User Management
- Add library users
- View users
- Update user details
- Delete users

### 🔄 Transactions
- Borrow a book
- Return a book
- Track borrowed and returned books
- Prevent borrowing when a book is not available

---

## 🗄️ Database Design

### Tables Used

#### 📘 Books Table
- `book_id` (Primary Key)
- `title`
- `author`
- `quantity`

#### 👥 Users Table
- `user_id` (Primary Key)
- `name`
- `email`

#### 🔁 Transactions Table
- `transaction_id` (Primary Key)
- `book_id` (Foreign Key)
- `user_id` (Foreign Key)
- `borrow_date`
- `return_date`

---

## 🔗 JDBC Concepts Used
- JDBC Driver
- `Connection`
- `PreparedStatement`
- `ResultSet`
- SQL Queries
- Exception Handling
- Transaction control

---
