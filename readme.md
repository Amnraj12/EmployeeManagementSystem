# Employee Management System

---

## 🌟 Introduction

**Employee Management System** is a modern, full-stack web application designed to streamline HR operations, task management, and employee engagement for organizations of all sizes. Built with Java, Spring Boot, Thymeleaf, and MySQL, it offers a robust, role-based platform for both administrators and employees to manage tasks, leaves, and profiles efficiently.

---

## 🏗️ Technologies & Architecture

- **Java 8+**: Core programming language for backend logic.
- **Spring Boot**: Rapid development of RESTful web applications and MVC architecture.
- **Spring Data JPA**: ORM for seamless database integration and entity management.
- **Thymeleaf**: Server-side HTML templating for dynamic, responsive UI.
- **MySQL**: Relational database for persistent data storage.
- **Lombok**: Reduces boilerplate code in Java classes.
- **Maven**: Dependency management and build automation.

---

## ⚡ Features

- **Role-Based Access**: Separate dashboards and functionalities for Admins and Employees.
- **Task Management**: Assign, track, and review tasks with priority and progress indicators.
- **Leave Management**: Employees can apply for leaves; admins can approve or reject requests.
- **Employee Onboarding & Profile Management**: Registration, profile editing, and approval workflows.
- **Performance Tracking**: Points-based ranking and activity feeds for employee performance.
- **Responsive UI**: Clean, modern interface with intuitive navigation.

---

## 🗂️ Project Structure

```
src/
├── main/
│   ├── java/com/company/employeemanagement/
│   │   ├── controller/      # Handles web requests and routing
│   │   ├── model/           # Entity classes for DB tables
│   │   ├── repository/      # JPA repositories for data access
│   │   └── service/         # Business logic and service layer
│   ├── resources/
│   │   ├── application.properties  # Configuration (DB, server, etc.)
│   │   ├── static/css/styles.css   # Global styles
│   │   └── templates/              # Thymeleaf HTML templates
│   │       ├── index.html
│   │       ├── login.html
│   │       ├── register.html
│   │       ├── admin/              # Admin panel pages
│   │       ├── employee/           # Employee panel pages
│   │       └── fragments/          # Reusable UI components
```

---

## 🧑‍💼 Admin Panel Pages

- **Dashboard**: Overview of employee stats, recent activities, and performance rankings.
- **Manage Employees**: View, edit, and approve pending employee registrations.
- **Manage Tasks**: Create, assign, edit, and review tasks for employees.
- **Leave Requests**: Approve or reject employee leave applications.
- **Pending Employees**: Approve or reject new employee registrations.

> Admins have full control over employee data, task assignments, and leave approvals.

---

## 👨‍🎓 Employee Panel Pages

- **Dashboard**: Personalized overview of assigned tasks, recent activities, and leave status.
- **Available Tasks**: Browse and accept new tasks assigned by admin.
- **My Tasks**: Track progress, update status, and view details of assigned tasks.
- **Apply for Leave**: Submit leave requests with category and reason.
- **Leave Requests**: View status of submitted leave applications.
- **Profile**: Edit personal details, upload profile picture, and manage account settings.

> Employees can manage their tasks, apply for leaves, and update their profiles.

---

## 🛠️ Prerequisites

- **Java 8 or higher**
- **Maven**
- **MySQL Server**

---

## 🚀 Installation & Setup

1. **Clone the Repository**

   ```sh
   git clone https://github.com/yourusername/employeemanagement.git
   cd employeemanagement
   ```
2. **Create the Database**

   - Open MySQL and run:
     ```sql
     CREATE DATABASE employeemanagement;
     ```
3. **Configure Database Credentials**

   - Edit `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/employeemanagement
     spring.datasource.username=YOUR_DB_USERNAME
     spring.datasource.password=YOUR_DB_PASSWORD
     spring.jpa.hibernate.ddl-auto=update
     ```
4. **Run the Application**

   ```sh
   mvn spring-boot:run
   ```
5. **Access the Application**

   - Open [http://localhost:8080](http://localhost:8080) in your browser.

---

## 🔑 Demo Credentials

- **Admin**
  - Email: `admin@company.com`
  - Password: `admin`
- **Employee**
  - Register a new account and approve it after logging into admin account.

---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---

## 📄 License

This project is licensed under the MIT License.

---

## 👤 Author & Contact

Developed and maintained by Aman Raj.
For questions, collaborations, or professional inquiries, reach out via [amnraj125@gmail.com](mailto:amnraj125@gmail.com) or connect on [LinkedIn](https://www.linkedin.com/in/aman-raj-3a3ab02b2/).

---
