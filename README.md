# CampusSync Backend

Welcome to the backend repository for CampusSync, a comprehensive application designed to streamline campus operations, particularly focusing on student and teacher data management, attendance tracking, and session management.

---

## Features

This backend is built with **Spring Boot** and offers a robust set of features:

* **Student and Teacher Management**: Stores and manages data for students and teachers, including their departments, enrollment details, and subjects.
* **Attendance Tracking**: Facilitates student attendance marking through a dynamic token generation system.
* **Secure Authentication**: Implements **JWT (JSON Web Tokens)** for secure session management and API authentication.
* **RESTful API Design**: Exposes well-structured and intuitive RESTful endpoints for seamless integration with the frontend.
* **Data Security**: Utilizes **Data Transfer Objects (DTOs)** to prevent exposure of crucial information.
* **Database Management**: Leverages **PostgreSQL** for persistent data storage with **JDBC** for database connectivity.

---

## Technologies Used

* **Java 17+**
* **Spring Boot**
* **Spring Security**
* **PostgreSQL**
* **JDBC**
* **JWT (JSON Web Tokens)**
* **Maven**

---

## Getting Started

To get the CampusSync backend up and running on your local machine, follow these steps:

### Prerequisites

* **Java Development Kit (JDK) 17 or higher**: Make sure you have JDK 17 or a newer version installed.
* **Maven**: Install Maven for building and managing project dependencies.
* **PostgreSQL**: Set up a PostgreSQL database instance.

### Database Setup

1.  **Create a PostgreSQL Database**:
    ```sql
    CREATE DATABASE campussync_db;
    ```
2.  **Configure `application.properties`**:
    Update `src/main/resources/application.properties` with your PostgreSQL database credentials:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/campussync_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    ```
    (Replace `your_username` and `your_password` with your PostgreSQL credentials.)

### Running the Application

1.  **Clone the Repository**:
    ```bash
    git clone <repository_url>
    cd CampusSync-Backend
    ```
2.  **Build the Project**:
    ```bash
    mvn clean install
    ```
3.  **Run the Application**:
    ```bash
    mvn spring-boot:run
    ```
    The application will start on `http://localhost:8080`.

---

## API Endpoints

The following are some of the key API endpoints available in the CampusSync backend. All endpoints are prefixed with `http://localhost:8080`.

### Attendance

* **GET /attendance/all**: Retrieve all attendance records.

    ```json
    [
        {
            "id": 1,
            "student": {
                "id": 1,
                "name": "John Doe",
                "student_uid": "S001",
                "email": "john.doe@univ.edu",
                "jwt_token": " ",
                "semester": 1
            },
            "subject": {
                "id": 1,
                "name": "Introduction to Programming",
                "code": "CS101",
                "semester": "1",
                "department": {
                    "id": 1,
                    "name": "Computer Science"
                },
                "teacher": {
                    "id": 1,
                    "name": "Dr. Alice Smith",
                    "email": "alice.smith@univ.edu",
                    "jwtToken": null,
                    "departmentName": "Computer Science"
                }
            },
            "date": "2025-07-06",
            "status": null
        }
    ]
    ```

### Enrollment

* **GET /enrollment/all**: Retrieve all enrollment records.

    ```json
    [
        {
            "id": 1,
            "student": {
                "id": 1,
                "name": "John Doe",
                "student_uid": "S001",
                "email": "john.doe@univ.edu",
                "jwt_token": " ",
                "semester": 1
            },
            "subject": {
                "id": 1,
                "name": "Introduction to Programming",
                "code": "CS101",
                "semester": "1",
                "department": {
                    "id": 1,
                    "name": "Computer Science"
                },
                "teacher": {
                    "id": 1,
                    "name": "Dr. Alice Smith",
                    "email": "alice.smith@univ.edu",
                    "jwtToken": null,
                    "departmentName": "Computer Science"
                }
            }
        }
    ]
    ```

### Subject

* **GET /subject/all**: Retrieve all subject records.

    ```json
    {
        "id": 1,
        "name": "Introduction to Programming",
        "code": "CS101",
        "semester": "1",
        "department": {
            "id": 1,
            "name": "Computer Science"
        },
        "teacher": {
            "id": 1,
            "name": "Dr. Alice Smith",
            "email": "alice.smith@univ.edu",
            "jwtToken": null,
            "departmentName": "Computer Science"
        }
    }
    ```

### Teacher

* **GET /teacher/all**: Retrieve all teacher records.

    ```json
    {
        "id": 1,
        "name": "Dr. Alice Smith",
        "email": "alice.smith@univ.edu",
        "jwtToken": null,
        "departmentName": "Computer Science"
    }
    ```

### Student

* **GET /student/all**: Retrieve all student records.

    ```json
    {
        "id": 1,
        "name": "John Doe",
        "student_uid": "S001",
        "email": "john.doe@univ.edu",
        "jwt_token": " ",
        "semester": 1
    }
    ```

---

## Contributing

Contributions are welcome! If you have suggestions for improvements or new features, feel free to open an issue or submit a pull request.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
