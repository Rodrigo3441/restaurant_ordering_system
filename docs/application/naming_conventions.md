# **Naming Conventions**

This document outlines the naming conventions used for packages, entities, attributes, methods, and other objects in the java project.

## **Table of Contents**

1. [General Principles](#general-principles)
2. [Package Naming Conventions](#package-naming-conventions)
3. [Class Naming Conventions](#class-naming-conventions)
4. [Interface Naming Conventions](#interface-naming-conventions)
5. [Method Naming Conventions](#method-naming-conventions)
6. [Variable Naming Conventions](#variable-naming-conventions)
7. [Constant Naming Conventions](#constant-naming-conventions)
8. [Layer Naming Conventions](#layer-naming-conventions)
---

## **General Principles**

- **Language**: Use English for all identifiers.
- **Naming Style**: Follow standard Java naming conventions.
- **Meaningful Names**: Prefer descriptive names over abbreviations.
- **Consistency**: Similar concepts should follow the same naming pattern.
- **Avoid Reserved Words**: Do not use Java reserved keywords as identifiers.

---

# **Package Naming Conventions**

- Package names must use lowercase letters only.
- Separate words using dots (`.`).
- Organize packages according to the project architecture.

### Pattern

```text
<project>.<layer>
```

### Examples

```text
com.university.library
com.university.library.model
com.university.library.dao
com.university.library.service
com.university.library.controller
com.university.library.util
```

---

# **Class Naming Conventions**

- Class names must use **PascalCase**.
- Use nouns that describe the represented object.

### Pattern

```text
<Entity>
```

### Examples

```text
Student
Book
Employee
Loan
DatabaseConnection
```

---

# **Interface Naming Conventions**

- Interface names must use **PascalCase**.
- Prefer descriptive names without prefixes such as `I`.

### Pattern

```text
<Entity>Service
<Entity>DAO
```

### Examples

```text
StudentService
AuthenticationService
BookDAO
```

---

# **Method Naming Conventions**

- Method names must use **camelCase**.
- Begin with a verb describing the performed action.

### Pattern

```text
<verb><Object>
```

### Examples

```text
findStudent()
saveBook()
updateEmployee()
deleteLoan()
calculateAverage()
validateLogin()
```

---

# **Variable Naming Conventions**

- Variable names must use **camelCase**.
- Choose descriptive names over abbreviations.

### Examples

```text
student
studentName
studentList
averageGrade
databaseConnection
```

### Boolean Variables

Boolean variables should start with:

- `is`
- `has`
- `can`
- `should`

### Examples

```text
isApproved
hasPermission
canDelete
shouldSave
```

---

# **Constant Naming Conventions**

- Constants must use uppercase letters separated by underscores.

### Pattern

```text
CONSTANT_NAME
```

### Examples

```text
MAX_STUDENTS
DEFAULT_TIMEOUT
DATABASE_URL
PI
```

---

# **Layer Naming Conventions**

Each application layer must follow a consistent naming pattern.

| Layer | Pattern | Example |
|-------|---------|---------|
| Model | `<Entity>` | `Student` |
| DAO | `<Entity>DAO` | `StudentDAO` |
| Service | `<Entity>Service` | `StudentService` |
| Controller | `<Entity>Controller` | `StudentController` |
| DTO | `<Entity>DTO` | `StudentDTO` |
| Utility | `<Purpose>Util` | `DateUtil` |
| Exception | `<Reason>Exception` | `DatabaseException` |

---
