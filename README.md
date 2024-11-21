

# ASU CSE 360 Project Phase 3

This is a JavaFX-based Help System for students and instructors at Arizona State University (ASU). The system allows an admin to invite users, manage their roles, and reset their passwords. Students can log in, update their personal details, and interact with the system. Instructors could add, delete, edit, and search for help articles.

## Core Features

### 1. **User Management**
The system offers a comprehensive suite of user management features:
- **Roles**: Users can hold multiple roles (`Student`, `Instructor`, `Admin`), with corresponding permissions.
- **Secure Authentication**:
   - Password-based and OTP-based account setup/reset.
   - Invitation-based user registration with expiring invitation codes.
- **User Profile Management**:
   - Store and update personal details (name, email, etc.).
   - Track active roles for multi-role users.
- **Administrative Functions**:
   - Manage user roles and permissions.
   - Invite or delete users.
   - Generate and manage invitation codes.

### 2. **Article Group Management**
The system allows structured organization of help articles into groups:
- **Group Creation and Management**:
   - Standard groups for general article organization.
   - Special Access Groups for secure, encrypted article storage.
- **Admin Control**:
   - Add or remove articles from groups.
   - Assign multiple admins to manage groups.
- **Access Control**:
   - Group-level and article-level permissions for secure access.

### 3. **Help Articles**
Help articles provide detailed guidance, categorized by access level and group:
- **Article Details**:
   - Includes title, body, keywords, authors, and short descriptions.
- **Access Levels**:
   - Articles can be assigned access levels: `Beginner`, `Intermediate`, `Advanced`, `Expert`.
- **Encryption**:
   - Special Access Groups enable encryption for sensitive articles.
- **Search and Management**:
   - Admins and instructors can create, edit, and organize articles.

### 4. **Help Messaging System**
Users can submit help requests containing:
- **Request Details**: Type, email, title, and body.
- Admins and instructors can review and respond to help requests.

## Key Components

### Classes for User Management
#### `User`
- **Purpose**: Manages user account details and roles.
- **Features**:
   - Role management: `addRole()`, `removeRole()`, `hasRole()`.
   - Secure setup with invitation codes and OTPs.
   - Tracks active roles for multi-role users.

#### `UserDatabase`
- **Purpose**: Handles user storage and operations.
- **Features**:
   - User registration and invitation management.
   - Role and account management for admins.
   - Key methods: `inviteUser()`, `resetUserAccount()`, `generateInvitationCode()`.

### Classes for Article Management
#### `HelpArticle`
- **Purpose**: Represents individual help articles.
- **Features**:
   - Stores metadata: title, authors, keywords, and access levels.
   - Encryption support for sensitive articles in special groups.
   - Group management integration.

#### `ArticleDatabase`
- **Purpose**: Centralizes management of articles and groups.
- **Features**:
   - Add, remove, and organize articles into groups.
   - Manage special access permissions for secure articles.
   - Key methods: `addArticle()`, `deleteArticle()`, `listArticlesToConsole()`.

#### `SpecialAccessGroup`
- **Purpose**: Provides encrypted storage and controlled access for sensitive articles.
- **Features**:
   - Encryption and decryption of article content.
   - Viewer and admin permission management.

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
