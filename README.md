# CampusSync Backend

Welcome to the backend repository for CampusSync, an advanced, highly-structured educational management system built with Spring Boot. It manages students, teachers, departments, subjects, enrollments, course offerings, lecture sessions, dynamic attendance tokens, attendance tracking, and JWT-based secure session management.

---

## 🚀 Features & Tech Stack

- **Framework**: Spring Boot 3.x with Java 17+
- **Database**: PostgreSQL with Spring Data JPA (Hibernate)
- **Security**: Spring Security with stateless JWT (JSON Web Tokens)
- **Session Management**: Persistent Refresh Tokens mapping
- **API Architecture**: Controller-Service-Repository pattern utilizing strict DTO separation for inbound (InputDTO) and outbound (ResponseDTO) payloads.

---

## 🔧 Getting Started

### Prerequisites
- JDK 17+
- Maven 3.8+
- PostgreSQL 14+

### Database Configuration
1. Create a database: `CREATE DATABASE campussync_db;`
2. Update `src/main/resources/application.properties` with your PostgreSQL credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/campussync_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

### Execution
```bash
mvn clean install
mvn spring-boot:run
```

The application defaults to `http://localhost:8080`.

---

## 📚 API Reference Documentation

The CampusSync API strictly enforces decoupled data structures. Create and Update requests rely on dedicated `InputDTO` objects that omit sensitive fields like `id` or `createdAt` to prevent unintended database manipulation. Unique entity identifiers for updates or deletions are exclusively handled via URL Query Parameters (`?moduleId={id}`).

Below is the exhaustive structural documentation of each module's endpoints, valid payloads, expected responses, and potential error states.

---

### 1. Authentication API (`/api/auth`)

Handles system-wide stateless authentication validation, token fetching, and secure logouts.

* **POST `/api/auth/validate-token`**
  * **Headers**: `Authorization: Bearer <jwt_token>`
  * **Response (200 OK)**:
    ```json
    { "success": true, "message": "Token is valid." }
    ```
  * **Response (401 UNAUTHORIZED / 400 BAD REQUEST)**:
    ```json
    { "success": false, "message": "Invalid Authorization header..." }
    ```

* **POST `/api/auth/refreshtoken`**
  * **Request Body**:
    ```json
    { "refreshToken": "uuid-string-of-refresh-token" }
    ```
  * **Response (200 OK)**:
    ```json
    { "accessToken": "new-jwt-token", "refreshToken": "uuid-string-of-refresh-token", "tokenType": "Bearer" }
    ```
  * **Response (403 FORBIDDEN)**: Returned if the refresh token is expired, revoked, or missing.

* **POST `/api/auth/logout`**
  * **Request Body**:
    ```json
    { "refreshToken": "uuid-string-of-refresh-token" }
    ```
  * **Response (200 OK)**: Token revoked successfully.
  * **Response (400 BAD REQUEST)**: Logout failed.

---

### 2. Student API (`/student`)

Manages student enrollment, profile creation, and authentication.

* **POST `/student/register`**
  * **Request Body (StudentRequestDTO)**:
    ```json
    { "name": "Jane Doe", "email": "jane@univ.edu", "password": "secure123", "student_uid": "S002", "semester": 2 }
    ```
  * **Response (201 CREATED)**: 
    ```json
    { "id": 1, "name": "Jane Doe", "email": "jane@univ.edu", "student_uid": "S002", "jwt_token": null, "semester": 2 }
    ```
  * **Errors**: `409 CONFLICT` if email or UID already exists.

* **POST `/student/login`**
  * **Request Body (StudentLoginDTO)**:
    ```json
    { "email": "jane@univ.edu", "password": "secure123" }
    ```
  * **Response (200 OK)**: Returns `StudentResponseDTO` containing a fresh `jwt_token`.
  * **Errors**: `401 UNAUTHORIZED` if credentials mismatch.

* **GET `/student/all`**
  * **Response (200 OK)**: Array of `StudentResponseDTO`.

* **GET `/student?studentId={id}`**
  * **Response (200 OK)**: Single `StudentResponseDTO`.
  * **Errors**: `404 NOT FOUND`

* **PUT `/student?studentId={id}`**
  * **Request Body**: `StudentRequestDTO`
  * **Response (200 OK)**: Updated `StudentResponseDTO`.
  * **Errors**: `404 NOT FOUND`

* **DELETE `/student?studentId={id}`**
  * **Response (204 NO CONTENT)**: Successful deletion.

---

### 3. Teacher API (`/teacher`)

Manages teacher accounts, assigned departments, and authentication.

* **POST `/teacher/register`**
  * **Request Body (TeacherRequestDTO)**:
    ```json
    { "name": "Dr. Smith", "email": "smith@univ.edu", "password": "pwd", "departmentId": 1 }
    ```
  * **Response (201 CREATED)**: `TeacherResponseDTO`

* **POST `/teacher/login`**
  * **Request Body (TeacherLoginDTO)**: `{ "email": "...", "password": "..." }`
  * **Response (200 OK)**: `TeacherResponseDTO` populated with `.jwtToken`.

* **GET `/teacher/all`** / **GET `/teacher?teacherId={id}`** / **PUT `/teacher?teacherId={id}`** / **DELETE `/teacher?teacherId={id}`**
  * Follows standard standard CRUD structure. `PUT` requires `TeacherRequestDTO`. Returns `TeacherResponseDTO`.

---

### 4. Subject API (`/subject`)

Subjects denote specific courses bound to departments and taught by teachers.

* **POST `/subject`**
  * **Request Body (SubjectInputDTO)**:
    ```json
    { "name": "Data Structures", "code": "CS201", "credits": 4, "departmentId": 1 }
    ```
  * **Response (201 CREATED)**:
    ```json
    { "id": 1, "name": "Data Structures", "code": "CS201", "credits": 4 }
    ```

* **GET `/subject/all`**
  * **Response (200 OK)**: Array of `SubjectDTO`.

* **GET `/subject?subjectId={id}`**
  * **Response (200 OK)**: Single `SubjectDTO`.

* **GET `/subject/details/{id}`**
  * **Response (200 OK)**: Returns `SubjectDetailsDTO` including expanded `DepartmentDetailsDTO` & `TeacherResponseDTO` objects inside it.

* **PUT `/subject?subjectId={id}`**
  * **Request Body**: `SubjectInputDTO`
  * **Response (200 OK)**: Updated `SubjectDTO`.
  * **Errors**: `404 NOT FOUND`.

* **DELETE `/subject?subjectId={id}`**

---

### 5. Course Offerings API (`/course-offerings`)

Connects a Subject with a Teacher instance for a specific academic semester and section.

* **POST `/course-offerings`**
  * **Request Body (CourseOfferingsInputDTO)**:
    ```json
    { "subjectId": 1, "teacherId": 1, "academicYear": 2025, "semester": 5, "section": "A" }
    ```
  * **Response (201 CREATED)**: 
    ```json
    { "id": 1, "subjectId": 1, "teacherId": 1, "academicYear": 2025, "semester": 5, "section": "A" }
    ```

* **GET `/course-offerings/all`** | **GET `/course-offerings?courseOfferingId={id}`**
  * **Response (200 OK)**: `CourseOfferingsDTO` (Flat relationship IDs).

* **GET `/course-offerings/details/{id}`**
  * **Response (200 OK)**: `CourseOfferingsDetailsDTO` (Nested Subject and Teacher objects).

* **PUT `/course-offerings?courseOfferingId={id}`**
  * **Request Body**: `CourseOfferingsInputDTO`

* **DELETE `/course-offerings?courseOfferingId={id}`**

---

### 6. Enrollment API (`/enrollment`)

Tracks which Students are actively enrolled in specific Course Offerings.

* **POST `/enrollment`**
  * **Request Body (EnrollmentInputDTO)**:
    ```json
    { "studentId": 1, "courseOfferingId": 1 }
    ```
  * **Response (201 CREATED)**:
    ```json
    { "id": 1, "studentId": 1, "courseOfferingId": 1, "enrollmentDate": "2025-07-06T12:00:00" }
    ```

* **GET `/enrollment/all`** | **GET `/enrollment?enrollmentId={id}`**
  * **Response (200 OK)**: `EnrollmentDTO`

* **GET `/enrollment/details/{id}`**
  * **Response (200 OK)**: `EnrollmentDetailsDTO` containing loaded Student and CourseOffering entities.

* **PUT `/enrollment?enrollmentId={id}`**
  * **Request Body**: `EnrollmentInputDTO`
  * **Response (200 OK)**: `EnrollmentDTO`

* **DELETE `/enrollment?enrollmentId={id}`**

---

### 7. Lecture Sessions API (`/lecturesessions`)

Defines discrete classes (time slots) belonging to a Course Offering.

* **POST `/lecturesessions`**
  * **Request Body (LectureSessionsInputDTO)**:
    ```json
    { "courseOfferingId": 1, "sessionDate": "2025-10-14", "startTime": "2025-10-14T09:00:00", "endTime": "2025-10-14T10:30:00", "room": "Room 204", "topic": "Introduction to Trees" }
    ```
  * *Note*: `startTime`, `endTime`, `room`, and `topic` are optional fields and may be omitted (sent as `null`).
  * **Response (201 CREATED)**: `LectureSessionsDTO`

* **GET `/lecturesessions/all`** | **GET `/lecturesessions?sessionId={id}`**
  * **Response (200 OK)**: `LectureSessionsDTO`

* **GET `/lecturesessions/details/{id}`**
  * **Response (200 OK)**: `LectureSessionsDetailsDTO`

* **PUT `/lecturesessions?sessionId={id}`**
  * **Request Body**: `LectureSessionsInputDTO`
  * *Note*: Passing `null` for optional fields during an update retains their existing database values.

* **DELETE `/lecturesessions?sessionId={id}`**

---

### 8. Attendance Token API (`/attendance_token`)

Handles dynamic attendance QR tokens generated by teachers for an active session.

* **POST `/attendance_token/generate`**
  * **Request Parameters**: `?subjectId=1`
  * **Response (201 CREATED)**:
    ```json
    { "token": "uuid-v4-string", "generatedAt": "...", "expiresAt": "...", "subject": { ... } }
    ```

* **GET `/attendance_token/all`**
  * **Response (200 OK)**: Array of generated token objects.

* **GET `/attendance_token/{subjectId}`**
  * **Response (200 OK)**: Array of tokens specifically mapped to the parsed Subject.

* **GET `/attendance_token/validate/{token}`**
  * **Response (200 OK)**: `true` (if token exists and current timestamp < expiresAt) or `false` otherwise.

---

### 9. Attendance API (`/attendance`)

Registers and modifies student attendance mapping to specific lecture sessions and validated tokens.

* **POST `/attendance`**
  * **Request Body (AttendanceInputDTO)**:
    ```json
    { "enrollmentId": 1, "lectureSessionId": 1, "tokenId": "uuid-v4", "status": "PRESENT" }
    ```
  * **Response (201 CREATED)**: `AttendanceDTO`

* **POST `/attendance/bulk`**
  * **Request Body**: Array of `AttendanceInputDTO`
  * **Response (201 CREATED)**: Array of `AttendanceDTO` objects processed.

* **GET `/attendance/all`** | **GET `/attendance?attendanceId={id}`**
  * **Response (200 OK)**: `AttendanceDTO` 

* **GET `/attendance/details/{id}`**
  * **Response (200 OK)**: `AttendanceDetailsDTO` containing deep nested records of the Lecture Session and Enrollment.

* **GET `/attendance/lecture-session/{id}`** | **GET `/attendance/enrollment/{id}`**
  * Filter endpoints returning arrays of `AttendanceDTO`.

* **PUT `/attendance?attendanceId={id}`**
  * **Request Body**: `AttendanceInputDTO`
  * **Response (200 OK)**: `AttendanceDTO`

* **DELETE `/attendance?attendanceId={id}`**

---

### 10. User & Role API (`/users`)
Generic platform user structure used by administrative and background contexts.

* **POST `/users`**
  * **Request Body (UserInputDTO)**:
    ```json
    { "name": "Admin", "email": "admin@sync.edu", "password": "pass", "role": "ADMIN" }
    ```

* **GET `/users/all`** | **GET `/users?userId={id}`**
  * **Response (200 OK)**: `UserDTO`

* **PUT `/users`**
  * **Request Body**: `UserInputDTO`

* **PATCH `/users/{id}/status?status=ACTIVE`**
  * Updates system suspension/activation statuses. Use `ACTIVE` or `INACTIVE`.

* **DELETE `/users?userId={id}`**

---

### 11. Refresh Token API (`/refreshtokens`)
Provides administrative view over registered persistent device sessions.

* **GET `/refreshtokens/all`**
  * **Response (200 OK)**: Array of `RefreshTokenDTO`.
  * *Note*: Hides User Profile payload, mapping strictly down to `userId` integer pointers.

* **GET `/refreshtokens/{id}`**
  * **Response (200 OK)**: Look up single Token by PK.

* **GET `/refreshtokens/user/{userId}`**
  * **Response (200 OK)**: Fetch all issued tokens currently bound to a specific user id.

---

### 12. Department API (`/department`)
Manages academic sectors grouping Subjects and Teachers.

* **POST `/department`**
  * **Request Body (DepartmentDTO)**:
    ```json
    { "name": "Computer Science" }
    ```
  * **Response (201 CREATED)**: `DepartmentDTO`

* **GET `/department/all`** | **GET `/department/{id}`**
  * **Response (200 OK)**: `DepartmentDTO`. (Use ID via parameter path)

* **PUT `/department/{id}`**
  * **Request Body**: `DepartmentDTO`

* **DELETE `/department/{id}`**

---

### ⚠️ Global Exception Handling

Throughout the CampusSync API, failing requests will respond with appropriate standardized Status Codes mapping back to global controller advice (if set):

* **`400 BAD REQUEST`**: Emitted prominently if DTO validations fail, an invalid format string is passed to an enumeration mapping, or related foreign dependencies (e.g., `teacherId`, `enrollmentId`) evaluate to `null` prior to Database insertion.
* **`404 NOT FOUND`**: Returned cleanly when requesting a `GET`, `PUT`, or `DELETE` across an ID that does not exist in the relational database. Handled via standard `NoSuchElementException` maps.
* **`401 UNAUTHORIZED` / `403 FORBIDDEN`**: Fired upon supplying invalid Bearer tokens, bad credentials, revoked refresh tokens, or attempting to access secured modules without appropriate scopes.
* **`409 CONFLICT`**: Fired if unique constraints are broken (e.g. duplicating unique student UIDs or emails).
