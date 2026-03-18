CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE teachers (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    password VARCHAR(255),
    semester INTEGER NOT NULL,
    student_uid VARCHAR(255) NOT NULL UNIQUE,
    department_id BIGINT,
    CONSTRAINT fk_students_department
        FOREIGN KEY (department_id)
        REFERENCES departments(id)
);

CREATE TABLE subjects (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(255),
    created_at TIMESTAMP,
    name VARCHAR(255),
    semester INTEGER NOT NULL,
    department_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    CONSTRAINT fk_subjects_department
        FOREIGN KEY (department_id)
        REFERENCES departments(id),
    CONSTRAINT fk_subjects_teacher
        FOREIGN KEY (teacher_id)
        REFERENCES teachers(id)
);

CREATE TABLE enrollment (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    student_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    CONSTRAINT fk_enrollment_student
        FOREIGN KEY (student_id)
        REFERENCES students(id),
    CONSTRAINT fk_enrollment_subject
        FOREIGN KEY (subject_id)
        REFERENCES subjects(id)
);

CREATE TABLE attendance (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    date DATE,
    status VARCHAR(255),
    student_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    CONSTRAINT fk_attendance_student
        FOREIGN KEY (student_id)
        REFERENCES students(id),
    CONSTRAINT fk_attendance_subject
        FOREIGN KEY (subject_id)
        REFERENCES subjects(id),
    CONSTRAINT chk_attendance_status
        CHECK (status IN ('PRESENT','ABSENT','LATE'))
);

CREATE TABLE attendance_tokens (
    token UUID PRIMARY KEY,
    expires_at TIMESTAMP NOT NULL,
    generated_at TIMESTAMP NOT NULL,
    subject_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    CONSTRAINT fk_token_subject
        FOREIGN KEY (subject_id)
        REFERENCES subjects(id),
    CONSTRAINT fk_token_teacher
        FOREIGN KEY (teacher_id)
        REFERENCES teachers(id)
);