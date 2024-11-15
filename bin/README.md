# ASU CSE 360 Project Phase 2

This is a JavaFX-based Help System for students and instructors at Arizona State University (ASU). The system allows an admin to invite users, manage their roles, and reset their passwords. Students can log in, update their personal details, and interact with the system. Instructors could add, delete, edit, and search for help articles.

## Features

- Admin can invite users and assign roles (`Student`, `Instructor`).
- Users can reset their passwords using a one-time invitation code.
- Admin can delete users and manage their roles.
- Users can update their personal information like email and preferred name after logging in.
- Instructors and Admin can add, delete, edit, and search for help articles form the help system.

## Technologies Used

- **Java 22.0.2**
- **JavaFX 22.0.2**
- **Maven** for dependency management
- **FXML** for UI layout

## Getting Started

### Prerequisites

To run this project, you will need to have the following installed:

- **Java JDK 22** or higher
- **Maven** (for dependency management)
- **JavaFX SDK** (make sure it matches the version used in the project)

### Setup Instructions

1. Clone this repository to your local machine:

    ```bash
    git clone <repository-url>
    cd project360
    ```

2. Make sure to have JavaFX installed and set up. Add JavaFX as a module by configuring VM Options in your IDE:

    ```bash
    --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
    ```

3. Use Maven to resolve dependencies and build the project:

    ```bash
    mvn clean install
    ```

4. Run the project from your IDE or from the command line using the Main class.

### Running the Application

- Upon starting the application, you will be presented with a login screen.
- If you're the admin, create an account with admin privileges.
- The admin can invite new users, assign roles, reset passwords, and delete users.
- Users can update their personal details after logging in.
- If you're assigned the instructor role, you will have added permissions relating to the help articles. 

### Project Structure

```bash
project360/
│
├── src/
│   ├── main/
│   │   ├── java/com/example/project360/
│   │   │   ├── Main.java           # Entry point for the JavaFX application
│   │   │   ├── User.java           # User class with roles and methods
│   │   │   ├── Admin.java          # Admin-specific functionalities
│   │   │   ├── Instructor.java     # Instructor-specific functionalities
│   │   │   ├── HelpArticle.java    # Class for the help article system
│   │   │   └── other-classes       # Other supporting classes
│   │   ├── resources/
│   │       └── fxml/               # FXML files for UI
│   │           └── main-page.fxml   # FXML layout for the main page
│   └── test/                       # Test cases (if applicable)
├── pom.xml                         # Maven configuration file
└── README.md                       # Project documentation
```

## How to Use the Admin Panel
- Create an Admin Account: When you start the application, create an admin account by filling in the required details.

- Invite Users: As an admin, you can invite users by entering their details (username, role, and email).

- Manage Users: The admin can view all users, delete users, and modify their roles (e.g., promote a user to Instructor).

- Reset User Passwords: The admin can reset any user’s password using a one-time password system.
