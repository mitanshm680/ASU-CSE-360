package com.example.project360;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private final List<User> users = new ArrayList<>();
    private Admin admin;
    private Stage primaryStage;
    private Scene loginScene, setupScene, homeScene, inviteUserScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("User Login System");
        createLoginScene();
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void createLoginScene() {
        VBox layout = new VBox(10);
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create Account");

        layout.getChildren().addAll(new Label("Username:"), usernameField,
                new Label("Password:"), passwordField, loginButton, createAccountButton);

        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
        createAccountButton.setOnAction(e -> showCreateAccountPage());

        loginScene = new Scene(layout, 300, 200);
    }

    private void showCreateAccountPage() {
        VBox layout = new VBox(10);
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        Button createButton = new Button("Create Account");
        Button backButton = new Button("Back");

        layout.getChildren().addAll(new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                new Label("Confirm Password:"), confirmPasswordField, createButton, backButton);

        createButton.setOnAction(e -> handleCreateAccount(usernameField.getText(), passwordField.getText(), confirmPasswordField.getText()));
        backButton.setOnAction(e -> primaryStage.setScene(loginScene));

        Scene createAccountScene = new Scene(layout, 300, 200);
        primaryStage.setScene(createAccountScene);
    }

    public  void addUser(User user) {
        users.add(user);
    }

    private void handleLogin(String username, String password) {
        User user = findUser(username);
        if (user != null && (user.getPassword().equals(password) || user.getInvitationCode().equals(password))) {
            if (users.size() == 1 && admin == null) { // First login, setup admin
                admin = new Admin(username, password, users);
                users.add(admin);
                showSetupPage();
            } else {
                // Logic for selecting roles or going to home page
                showHomePage(user);
            }
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private void showAddUserDetailsPage(User user) {
        VBox layout = new VBox(10);
        TextField emailField = new TextField();
        TextField firstNameField = new TextField();
        TextField middleNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField preferredNameField = new TextField();
        Button finishButton = new Button("Finish");

        layout.getChildren().addAll(
                new Label("Email:"), emailField,
                new Label("First Name:"), firstNameField,
                new Label("Middle Name:"), middleNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Preferred First Name:"), preferredNameField,
                finishButton
        );

        finishButton.setOnAction(e -> {
            user.setAccountDetails(emailField.getText(), firstNameField.getText(),
                    middleNameField.getText(), lastNameField.getText(),
                    preferredNameField.getText());
            showHomePage(user);
        });

        Scene userDetailsScene = new Scene(layout, 400, 300);
        primaryStage.setScene(userDetailsScene);
    }

    private void handleCreateAccount(String username, String password, String confirmPassword) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and Password cannot be empty.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }

        if (admin == null) {
            // First account creation - set up admin account
            admin = new Admin(username, password, users); // Pass the users list to the admin
            users.add(admin);
            showSetupPage(); // Direct to the setup page for the admin
        } else {
            // Creating a new user account
            User newUser = new User(username, password);
            newUser.addRole("Student"); // Default role is Student
            users.add(newUser);
            showAddUserDetailsPage(newUser);
        }
    }

    private void showChangePasswordPage(User user) {
        VBox layout = new VBox(10);
        PasswordField oldPasswordField = new PasswordField();
        PasswordField newPasswordField = new PasswordField();
        PasswordField confirmNewPasswordField = new PasswordField();
        Button changePasswordButton = new Button("Change Password");
        Button backButton = new Button("Back");

        layout.getChildren().addAll(new Label("Old Password:"), oldPasswordField,
                new Label("New Password:"), newPasswordField,
                new Label("Confirm New Password:"), confirmNewPasswordField,
                changePasswordButton, backButton);

        changePasswordButton.setOnAction(e -> {
            if (user.getPassword().equals(oldPasswordField.getText()) && newPasswordField.getText().equals(confirmNewPasswordField.getText())) {
                user.setPassword(newPasswordField.getText());
                showAlert("Success", "Password changed successfully.");
            } else {
                showAlert("Error", "Password change failed. Please check your input.");
            }
        });

        backButton.setOnAction(e -> showHomePage(user));

        Scene changePasswordScene = new Scene(layout, 300, 300);
        primaryStage.setScene(changePasswordScene);
    }


    private void showSetupPage() {
        VBox layout = new VBox(10);
        TextField emailField = new TextField();
        TextField firstNameField = new TextField();
        TextField middleNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField preferredNameField = new TextField();
        Button finishButton = new Button("Finish Setup");

        layout.getChildren().addAll(new Label("Email:"), emailField,
                new Label("First Name:"), firstNameField,
                new Label("Middle Name:"), middleNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Preferred First Name:"), preferredNameField,
                finishButton);

        finishButton.setOnAction(e -> {
            admin.setAccountDetails(emailField.getText(), firstNameField.getText(),
                    middleNameField.getText(), lastNameField.getText(),
                    preferredNameField.getText());
            showHomePage(admin);
        });

        setupScene = new Scene(layout, 400, 300);
        primaryStage.setScene(setupScene);
    }

    private void showHomePage(User user) {
        VBox layout = new VBox(10);
        Label welcomeLabel = new Label("Welcome, " + user.getFirstName() + "!");
        Button changePasswordButton = new Button("Change Password");
        Button logoutButton = new Button("Logout");
        Button inviteUserButton = new Button("Invite User");
        Button listUsersButton = new Button("List Users");
        Button updateDetailsButton = new Button("Update Personal Details");
        Button deleteUserButton = new Button("Delete User"); // New delete user button

        layout.getChildren().addAll(welcomeLabel, changePasswordButton, inviteUserButton,
                updateDetailsButton, listUsersButton, deleteUserButton, logoutButton);
        Scene homeScene = new Scene(layout, 300, 250);

        changePasswordButton.setOnAction(e -> showChangePasswordPage(user));
        inviteUserButton.setOnAction(e -> showInviteUserPage(user));
        listUsersButton.setOnAction(e -> showUserList(user));
        deleteUserButton.setOnAction(e -> showDeleteUserPage(user)); // Add action for delete user
        logoutButton.setOnAction(e -> primaryStage.setScene(loginScene));
        updateDetailsButton.setOnAction(e -> showUpdateUserDetailsPage(user));

        primaryStage.setScene(homeScene);
    }

    private void showDeleteUserPage(User user) {
        if (user instanceof Admin) {
            VBox layout = new VBox(10);
            TextField usernameField = new TextField();
            Button deleteButton = new Button("Delete");
            Button backButton = new Button("Back");

            layout.getChildren().addAll(new Label("Enter Username to Delete:"), usernameField, deleteButton, backButton);

            deleteButton.setOnAction(e -> {
                String username = usernameField.getText();
                boolean isDeleted = Admin.deleteUser(username); // Call the static deleteUser method
                if (isDeleted) {
                    showAlert("Success", username + " has been deleted.");
                } else {
                    showAlert("Error", "User not found.");
                }
            });

            backButton.setOnAction(e -> showHomePage(user));

            Scene deleteUserScene = new Scene(layout, 300, 200);
            primaryStage.setScene(deleteUserScene);
        } else {
            showAlert("Access Denied", "Only admins can delete users.");
        }
    }



    private void showUserList(User user) {
        if (user instanceof Admin) {
            ((Admin) user).listUsers(); // Admin lists users
            showAlert("User List", "Check console for the list of users."); // Notify user
        } else {
            showAlert("Access Denied", "Only admins can view the user list.");
        }
    }

    private void showUpdateUserDetailsPage(User user) {
        VBox layout = new VBox(10);
        TextField emailField = new TextField(user.getEmail()); // Pre-fill with existing details
        TextField firstNameField = new TextField(user.getFirstName());
        TextField middleNameField = new TextField(user.getMiddleName());
        TextField lastNameField = new TextField(user.getLastName());
        TextField preferredNameField = new TextField(user.getPreferredFirstName());
        Button updateButton = new Button("Update");

        layout.getChildren().addAll(
                new Label("Email:"), emailField,
                new Label("First Name:"), firstNameField,
                new Label("Middle Name:"), middleNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Preferred First Name:"), preferredNameField,
                updateButton
        );

        updateButton.setOnAction(e -> {
            user.setAccountDetails(emailField.getText(), firstNameField.getText(),
                    middleNameField.getText(), lastNameField.getText(),
                    preferredNameField.getText());
            showAlert("Success", "Your details have been updated.");
            showHomePage(user);
        });

        Scene updateDetailsScene = new Scene(layout, 400, 300);
        primaryStage.setScene(updateDetailsScene);
    }

    private void showInviteUserPage(User user) {
        VBox layout = new VBox(10);
        TextField inviteUsernameField = new TextField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField(); // Add email field
        TextField preferredFirstNameField = new TextField(); // Add preferred first name field
        Button inviteButton = new Button("Invite");
        Button backButton = new Button("Back");

        layout.getChildren().addAll(
                new Label("Enter Username to Invite:"), inviteUsernameField,
                new Label("First Name:"), firstNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Email:"), emailField, // Add label for email
                new Label("Preferred First Name:"), preferredFirstNameField, // Add label for preferred first name
                inviteButton, backButton
        );

        inviteButton.setOnAction(e -> {
            String username = inviteUsernameField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText(); // Get the email input
            String preferredFirstName = preferredFirstNameField.getText(); // Get the preferred first name
            String role = "Student"; // Default role

            // Pass all required arguments to the inviteUser method
            admin.inviteUser(username, role, firstName, lastName, email, preferredFirstName);
            showAlert("Invitation Sent", "An invitation has been sent to " + username);
        });

        backButton.setOnAction(e -> showHomePage(user));

        Scene inviteScene = new Scene(layout, 400, 300); // Adjust height for new fields
        primaryStage.setScene(inviteScene);
    }

    private User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
