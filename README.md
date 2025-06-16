
# 📘 BookReview_RestAPI


A secure, RESTful Spring Boot API that powers a multi-user **book review** platform. It supports features like user registration, book management, review posting, rating, and role-based access control.

---


## 🚀 Project Setup Instructions

* This project is a Spring Boot REST API built using **Spring Tool Suite (STS)** and tested using **Postman**.
* It includes **Basic Authentication**, user roles, and CRUD operations for books and reviews.

---

### 🧰 Prerequisites

Make sure you have the following installed:

* **Java JDK 17** (or 11)
* **Maven** (bundled with STS)
* **Spring Tool Suite (STS)** or **IntelliJ / Eclipse**
* **Postman** (for API testing)
* **Git** (to clone the repository)
* **MySQL/PostgreSQL** (or the database your app uses)

---

### 📁 Clone the Project

```bash
git clone https://github.com/shrugi/BookReview_RestAPI.git
cd BookReview_RestAPI

```

---

### ⚙️ Import Project into STS

1. Open **Spring Tool Suite**.
2. Go to `File > Import > Maven > Existing Maven Projects`.
3. Browse to the cloned project folder.
4. Select the project and click **Finish**.

---

### ⚙️ Configure Database (if needed)

1. Open `application.properties` or `application.yml`.
2. Update the following properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Make sure the database exists in MySQL/PostgreSQL.

---

### ▶️ Run the Application

* In STS: Right-click the main class (with `@SpringBootApplication`) > `Run As` > `Spring Boot App`.
* OR via terminal:

```bash
./mvnw spring-boot:run
```

---

### 🔍 Testing the API with Postman

You can test the API endpoints using **Postman**.

#### Example Requests:

* **Register User**

```http
POST http://localhost:8080/public/register
Content-Type: application/json

{
    "username": "Shruti",
    "email": "shruti@gmail.com",
    "password": "2428aab",
    "roles": [
        {
            "name": "ROLE_USER"
        },
        {
            "name": "ROLE_ADMIN"
        }
    ]
}
```


### 🛠️ Common Commands

* **Clean & Build Project**

```bash
./mvnw clean install
```

* **Run Tests**

```bash
./mvnw test
```






## 📬 API Reference

### 🎯 Core Features

**Base URL:** `http://localhost:8080`

#### 🔹 Register a New User

```http
POST /public/register
```

**Request Body:**

```json
{
  "username": "shruti",
  "email": "shruti@gmail.com",
  "password": "2428aab",
  "roles": [{ "name": "ROLE_USER" }]
}
```

---

#### 🔹 Add a New Book

```http
POST /books/users/{userId}
```

Example: `/books/users/153`

---

#### 🔹 Get All Books with Filters

```http
GET /books?gener=motivational
```

Optional filters: `author`, `title`, `genre`. Supports pagination.

---

#### 🔹 Get Book Details by ID

```http
GET /books/302
```

Returns book details with average rating and reviews (with pagination).

---

#### 🔹 Submit a Review (Authenticated users only, 1 review per book)

```http
POST /reviews/{userId}/{bookId}
```

Example: `/reviews/202/255`

---

#### 🔹 Update Your Own Review

```http
PUT /reviews/{reviewId}
```

Example: `/reviews/3`

---

#### 🔹 Delete Your Own Review

```http
DELETE /admin/user/{userId}/book/{bookId}
```

Example: `/admin/user/52/book/302`

---

### 🔎 Additional Feature: Search Books

```http
GET /books?gener=motivational
```

Supports **partial** and **case-insensitive** search by title or author.

---

---

## 🧠 Design Decisions & Assumptions

### ✅ Design Decisions

* Spring Boot with layered MVC architecture.
* JPA + Hibernate for ORM mapping.
* Basic Authentication for secure access.
* Centralized exception handling using `@ControllerAdvice`.
* Input validation using `@Valid`, `@NotBlank`, `@Email`.
* Status Codes: 200, 201, 400, 401, 403, 404.
* Tested with Postman.

---

### 📌 Assumptions

* Predefined roles: `ROLE_USER`, `ROLE_ADMIN`.
* Unique username and email for each user.
* Runs at `http://localhost:8080`.
* Admin has authority to delete any review or manage users.

---


## 🗃️ Database Schema

### 👤 `users`

| Column             | Type         | Description                                    |
|--------------------|--------------|------------------------------------------------|
| id                 | int (PK)     | Primary key                                    |
| username           | String       | Required, 4-30 characters                      |
| email              | String       | Required, unique, valid email format           |
| password           | String       | Encrypted password, not returned in response   |
| createDate         | LocalDateTime | Auto-generated on creation                    |
| lastModifiedDate   | LocalDateTime | Auto-updated on change                        |
| roles              | Set<Role>    | Many-to-Many relationship with `roles` table   |

---

### 🛡️ `roles`

| Column | Type   | Description                       |
|--------|--------|-----------------------------------|
| id     | int (PK) | Primary key                    |
| name   | String | `ROLE_USER` / `ROLE_ADMIN`, unique |

---

### 📚 `books`

| Column           | Type           | Description                                 |
|------------------|----------------|---------------------------------------------|
| id               | int (PK)       | Primary key                                 |
| title            | String         | Book title                                  |
| author           | String         | Author name                                 |
| gener            | String         | Genre (e.g., motivational, fiction)         |
| createDate       | LocalDateTime  | Auto-generated on creation                  |
| LastModifiedDate | LocalDateTime  | Auto-updated on change                      |
| user_id          | int (FK)       | Many-to-One relationship with `users`       |
| reviews          | List<Review>   | One-to-Many relationship with `reviews`     |

---

### ✍️ `reviews`

| Column   | Type           | Description                                   |
|----------|----------------|-----------------------------------------------|
| id       | int (PK)       | Primary key                                   |
| comment  | String         | Review comment                                |
| rating   | int            | Rating (e.g., 1 to 5)                          |
| book_id  | int (FK)       | Many-to-One relationship with `books`         |
| user_id  | int (FK)       | Many-to-One relationship with `users`         |

---

### 🔗 Relationships

- One **User** → Many **Books**
- One **User** → Many **Reviews**
- One **Book** → Many **Reviews**
- One **User** ↔ Many **Roles** (via `user_roles` join table)
- One **Review** → One **User** and One **Book**

---

### ⚠️ Rules & Constraints

- 🧑 **POST /books/users/{userId}** – Only authenticated users can add books.
- ✍️ **POST /reviews/{userId}/{bookId}** – Only one review per user per book.
- 🔐 Passwords are write-only and encrypted.
- 📅 Timestamps (`createDate`, `lastModifiedDate`) are handled automatically.


## License

[MIT](https://choosealicense.com/licenses/mit/)

