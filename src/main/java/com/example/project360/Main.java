package com.example.project360;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List; // Import List interface
import java.util.ArrayList;

/**
 * The Main class is the entry point for the JavaFX application.
 * It manages the login system and user interactions.
 */
public class Main extends Application {
    private final List<User> users = new ArrayList<>(); // List to store all users
    private Admin admin; // Reference to the admin user
    private Stage primaryStage; // Primary stage for managing the UI scenes
    private Scene loginScene, setupScene, homeScene, inviteUserScene; // Different scenes for navigation

    /**
     * Main method that launches the JavaFX application.
     *
     * @param args - Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    /**
     * This method is called when the application starts.
     * It initializes the primary stage and displays the login scene.
     *
     * @param primaryStage - The main stage (window) for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Store the reference to the primary stage
        primaryStage.setTitle("User Login System"); // Set the title of the window

        createLoginScene(); // Create the login scene layout
        primaryStage.setScene(loginScene); // Set the initial scene to login
        primaryStage.show(); // Show the stage (window)
    }

    /**
     * Creates the login scene layout, including fields for username and password,
     * and buttons for login and account creation.
     */
    private void createLoginScene() {
        VBox layout = new VBox(10); // Vertical layout with 10px spacing between elements
        TextField usernameField = new TextField(); // Input field for username
        PasswordField passwordField = new PasswordField(); // Input field for password
        Button loginButton = new Button("Login"); // Button to trigger login
        Button createAccountButton = new Button("Create Account"); // Button to navigate to account creation

        // Add all UI components (labels, fields, buttons) to the layout
        layout.getChildren().addAll(
                new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                loginButton, createAccountButton
        );

        // Set the action for the login button (handle login when clicked)
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        // Set the action for the create account button (navigate to account creation page)
        createAccountButton.setOnAction(e -> showCreateAccountPage());

        // Create a new Scene with the layout and set its size to 300x200 pixels
        loginScene = new Scene(layout, 300, 200);
    }

    /**
     * Displays the "Create Account" page where a new user can enter a username
     * and password to create an account.
     */
    private void showCreateAccountPage() {
        VBox layout = new VBox(10); // Vertical layout with 10px spacing between elements
        TextField usernameField = new TextField(); // Input field for the new username
        PasswordField passwordField = new PasswordField(); // Input field for the password
        PasswordField confirmPasswordField = new PasswordField(); // Input field to confirm the password
        Button createButton = new Button("Create Account"); // Button to create the account
        Button backButton = new Button("Back"); // Button to return to the login page

        // Add all UI components (labels, text fields, buttons) to the layout
        layout.getChildren().addAll(
                new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                new Label("Confirm Password:"), confirmPasswordField,
                createButton, backButton
        );

        // Set the action for the create account button (handle account creation)
        createButton.setOnAction(e -> handleCreateAccount(
                usernameField.getText(), passwordField.getText(), confirmPasswordField.getText())
        );

        // Set the action for the back button (return to the login scene)
        backButton.setOnAction(e -> primaryStage.setScene(loginScene));

        // Create a new Scene for the account creation page with a size of 300x200 pixels
        Scene createAccountScene = new Scene(layout, 300, 200);
        primaryStage.setScene(createAccountScene); // Switch to the account creation scene
    }

    /**
     * Adds a user to the list of users.
     *
     * @param user - The user to be added to the system
     */
    public void addUser(User user) {
        users.add(user); // Add the user to the list of users
    }

    /**
     * Handles the login logic. Checks if the user exists and if the password or invitation code is valid.
     * If it's the first login (admin setup), the user becomes an admin. Otherwise, the home page is shown.
     *
     * @param username - The username entered by the user
     * @param password - The password entered by the user
     */
    private void handleLogin(String username, String password) {
        User user = findUser(username); // Find the user based on the entered username

        // Check if the user exists and if the password or invitation code matches
        if (user != null && (user.getPassword().equals(password) || user.getInvitationCode().equals(password))) {

            // If this is the first login and admin has not been set up yet
            if (users.size() == 1 && admin == null) {
                admin = new Admin(username, password, users); // Set the first user as admin
                users.add(admin); // Add the admin to the user list
                showSetupPage(); // Navigate to the admin setup page
            } else {
                // If the user exists, log in and navigate to the home page
                showHomePage(user); // Navigate to the home page based on user role
            }
        } else {
            // Show an alert if the login fails due to invalid username or password
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    /**
     * Displays the page for adding additional user details like email, names, and preferred name.
     * Once the details are submitted, the system saves the information and navigates to the home page.
     *
     * @param user - The user object to which the details will be added
     */
    private void showAddUserDetailsPage(User user) {
        VBox layout = new VBox(10); // Vertical layout with 10px spacing between elements

        // Input fields for user details
        TextField emailField = new TextField(); // Field for entering email
        TextField firstNameField = new TextField(); // Field for entering first name
        TextField middleNameField = new TextField(); // Field for entering middle name
        TextField lastNameField = new TextField(); // Field for entering last name
        TextField preferredNameField = new TextField(); // Field for entering preferred first name
        Button finishButton = new Button("Finish"); // Button to submit the details and finish account setup

        // Add all UI components (labels and text fields) to the layout
        layout.getChildren().addAll(
                new Label("Email:"), emailField,
                new Label("First Name:"), firstNameField,
                new Label("Middle Name:"), middleNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Preferred First Name:"), preferredNameField,
                finishButton
        );

        // Set the action for the finish button (submit user details and go to home page)
        finishButton.setOnAction(e -> {
            // Set the user's account details based on the input fields
            user.setAccountDetails(
                    emailField.getText(), firstNameField.getText(),
                    middleNameField.getText(), lastNameField.getText(),
                    preferredNameField.getText()
            );
            showHomePage(user); // Navigate to the home page after setting the details
        });

        // Create a new Scene for the user details page with a size of 400x300 pixels
        Scene userDetailsScene = new Scene(layout, 400, 300);
        primaryStage.setScene(userDetailsScene); // Switch to the user details scene
    }

    /**
     * Handles the process of creating a new account. It validates the input, checks if the passwords match,
     * and either sets up an admin account (if this is the first account) or creates a regular user account.
     *
     * @param username - The username entered by the user
     * @param password - The password entered by the user
     * @param confirmPassword - The confirmation of the password to ensure they match
     */
    private void handleCreateAccount(String username, String password, String confirmPassword) {
        // Check if username or password fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and Password cannot be empty."); // Show error if fields are empty
            return;
        }

        // Check if the password and confirm password fields match
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match."); // Show error if passwords do not match
            return;
        }

        // If this is the first account creation, set up the admin account
        if (admin == null) {
            // Create the admin account with the provided username and password
            admin = new Admin(username, password, users); // Pass the users list to the admin constructor
            users.add(admin); // Add the admin to the list of users
            showSetupPage(); // Navigate to the setup page for the admin
        } else {
            // If admin already exists, create a regular user account
            User newUser = new User(username, password); // Create a new user with the given username and password
            newUser.addRole("Student"); // Assign the default role of "Student" to the new user
            users.add(newUser); // Add the new user to the list of users
            showAddUserDetailsPage(newUser); // Navigate to the user details page for further input
        }
    }

    /**
     * Displays the page where a user can change their password. It asks for the old password
     * and validates that the new password and the confirmation match. If valid, the password
     * is updated; otherwise, an error message is shown.
     *
     * @param user - The user whose password is being changed
     */
    private void showChangePasswordPage(User user) {
        VBox layout = new VBox(10); // Vertical layout with 10px spacing

        // Input fields for old password, new password, and password confirmation
        PasswordField oldPasswordField = new PasswordField(); // Field for entering the old password
        PasswordField newPasswordField = new PasswordField(); // Field for entering the new password
        PasswordField confirmNewPasswordField = new PasswordField(); // Field for confirming the new password
        Button changePasswordButton = new Button("Change Password"); // Button to submit the password change
        Button backButton = new Button("Back"); // Button to go back to the home page

        // Add all UI components to the layout
        layout.getChildren().addAll(
                new Label("Old Password:"), oldPasswordField,
                new Label("New Password:"), newPasswordField,
                new Label("Confirm New Password:"), confirmNewPasswordField,
                changePasswordButton, backButton
        );

        // Set the action for the "Change Password" button
        changePasswordButton.setOnAction(e -> {
            // Check if the old password is correct and the new passwords match
            if (user.getPassword().equals(oldPasswordField.getText()) &&
                    newPasswordField.getText().equals(confirmNewPasswordField.getText())) {

                user.setPassword(newPasswordField.getText()); // Set the new password
                showAlert("Success", "Password changed successfully."); // Show success message
            } else {
                showAlert("Error", "Password change failed. Please check your input."); // Show error message
            }
        });

        // Set the action for the "Back" button to return to the home page
        backButton.setOnAction(e -> showHomePage(user)); // Navigate back to the home page

        // Create a new Scene for the change password page with a size of 300x300 pixels
        Scene changePasswordScene = new Scene(layout, 300, 300);
        primaryStage.setScene(changePasswordScene); // Switch to the change password scene
    }

    /**
     * Displays the setup page for the admin account. This is where the admin enters
     * additional details such as email, names, and preferred first name. Once the setup
     * is complete, the admin is redirected to the home page.
     */
    private void showSetupPage() {
        VBox layout = new VBox(10); // Vertical layout with 10px spacing

        // Input fields for the admin's email and name details
        TextField emailField = new TextField(); // Field for entering email
        TextField firstNameField = new TextField(); // Field for entering first name
        TextField middleNameField = new TextField(); // Field for entering middle name
        TextField lastNameField = new TextField(); // Field for entering last name
        TextField preferredNameField = new TextField(); // Field for entering preferred first name
        Button finishButton = new Button("Finish Setup"); // Button to finish the setup

        // Add all UI components to the layout
        layout.getChildren().addAll(
                new Label("Email:"), emailField,
                new Label("First Name:"), firstNameField,
                new Label("Middle Name:"), middleNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Preferred First Name:"), preferredNameField,
                finishButton
        );

        // Set the action for the "Finish Setup" button
        finishButton.setOnAction(e -> {
            // Set the admin's account details based on the input fields
            admin.setAccountDetails(
                    emailField.getText(), firstNameField.getText(),
                    middleNameField.getText(), lastNameField.getText(),
                    preferredNameField.getText()
            );
            showHomePage(admin); // Navigate to the home page after setup
        });

        // Create a new Scene for the setup page with a size of 400x300 pixels
        setupScene = new Scene(layout, 400, 300);
        primaryStage.setScene(setupScene); // Switch to the setup scene
    }

    /**
     * Displays the home page after a successful login, personalized for the logged-in user.
     * The page contains options for changing password, inviting users, updating details,
     * listing users, and logging out. Admins will also see the option to delete users.
     *
     * @param user - The logged-in user for whom the home page is being displayed
     */

    private void showHomePage(User user) {
        VBox layout = new VBox(10); // Vertical layout with 10px spacing

        // Welcome label displaying the user's first name
        Label welcomeLabel = new Label("Welcome, " + user.getFirstName() + "!");

        // Buttons for different actions on the home page
        Button changePasswordButton = new Button("Change Password"); // Button to change password
        Button logoutButton = new Button("Logout"); // Button to log out
        Button inviteUserButton = new Button("Invite User"); // Button to invite a new user
        Button listUsersButton = new Button("List Users"); // Button to list all users
        Button updateDetailsButton = new Button("Update Personal Details"); // Button to update personal details
        Button deleteUserButton = new Button("Delete User"); // Button to delete users (visible to admin only)
        Button helpSystemButton = new Button("Manage Help Articles"); // Button to manage help articles

        // Add all UI components to the layout
        layout.getChildren().addAll(
                welcomeLabel, changePasswordButton, inviteUserButton,
                updateDetailsButton, listUsersButton, deleteUserButton,
                helpSystemButton, logoutButton
        );

        // Create a new Scene for the home page with a size of 300x250 pixels
        Scene homeScene = new Scene(layout, 300, 250);

        // Set actions for each button

        // Change password action - Opens the change password page
        changePasswordButton.setOnAction(e -> showChangePasswordPage(user));

        // Invite user action - Opens the invite user page
        inviteUserButton.setOnAction(e -> showInviteUserPage(user));

        // List users action - Displays a list of all users
        listUsersButton.setOnAction(e -> showUserList(user));

        // Delete user action - Opens the delete user page (visible only to admin)
        deleteUserButton.setOnAction(e -> showDeleteUserPage(user)); // Action for delete user

        // Help system action - Opens the help system management page if user is admin
        if (user.hasRole("Admin")) { // Check if the user has admin privileges
            helpSystemButton.setOnAction(e -> showHelpSystemPage((Admin) user)); // Cast user to Admin
        } else {
            helpSystemButton.setDisable(true); // Disable the button for non-admin users
        }

        // Logout action - Returns to the login page
        logoutButton.setOnAction(e -> primaryStage.setScene(loginScene));

        // Update personal details action - Opens the page to update personal information
        updateDetailsButton.setOnAction(e -> showUpdateUserDetailsPage(user));

        // Set the scene to the home page
        primaryStage.setScene(homeScene);
    }

    /**
     * Displays the delete user page where the admin can enter a username and delete
     * the specified user. Only admins are allowed to delete users.
     *
     * @param user - The logged-in user, who must be an admin to access this page
     */
    private void showDeleteUserPage(User user) {
        // Check if the logged-in user is an admin
        if (user instanceof Admin) {
            VBox layout = new VBox(10); // Vertical layout with 10px spacing

            // Input field to enter the username of the user to delete
            TextField usernameField = new TextField();
            Button deleteButton = new Button("Delete"); // Button to confirm deletion
            Button backButton = new Button("Back"); // Button to go back to the home page

            // Add all UI components to the layout
            layout.getChildren().addAll(new Label("Enter Username to Delete:"), usernameField, deleteButton, backButton);

            // Set action for the delete button
            deleteButton.setOnAction(e -> {
                String username = usernameField.getText(); // Get the entered username
                boolean isDeleted = Admin.deleteUser(username); // Call the static deleteUser method from Admin

                // Display success or error message based on the result of the deletion
                if (isDeleted) {
                    showAlert("Success", username + " has been deleted.");
                } else {
                    showAlert("Error", "User not found.");
                }
            });

            // Set action for the back button to return to the home page
            backButton.setOnAction(e -> showHomePage(user));

            // Create a new Scene for the delete user page with a size of 300x200 pixels
            Scene deleteUserScene = new Scene(layout, 300, 200);
            primaryStage.setScene(deleteUserScene); // Switch to the delete user scene
        } else {
            // Show an error alert if the user is not an admin
            showAlert("Access Denied", "Only admins can delete users.");
        }
    }

    /**
     * Displays the list of users if the logged-in user is an admin.
     * If not an admin, access to the user list is denied.
     *
     * @param user - The logged-in user who may attempt to view the list
     */
    private void showUserList(User user) {
        if (user instanceof Admin) {
            ((Admin) user).listUsers(); // Admin lists users via Admin class
            showAlert("User List", "Check console for the list of users."); // Notify that list is displayed in console
        } else {
            showAlert("Access Denied", "Only admins can view the user list."); // Deny access to non-admins
        }
    }

    /**
     * Displays the page for updating user details, pre-filling fields with the current information.
     *
     * @param user - The user whose details are being updated
     */
    private void showUpdateUserDetailsPage(User user) {
        VBox layout = new VBox(10); // Vertical layout with 10px spacing

        // Pre-fill the text fields with the user's existing details
        TextField emailField = new TextField(user.getEmail());
        TextField firstNameField = new TextField(user.getFirstName());
        TextField middleNameField = new TextField(user.getMiddleName());
        TextField lastNameField = new TextField(user.getLastName());
        TextField preferredNameField = new TextField(user.getPreferredFirstName());
        Button updateButton = new Button("Update"); // Button to confirm updates

        // Add all UI components to the layout
        layout.getChildren().addAll(
                new Label("Email:"), emailField,
                new Label("First Name:"), firstNameField,
                new Label("Middle Name:"), middleNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Preferred First Name:"), preferredNameField,
                updateButton
        );

        // Set action for the update button
        updateButton.setOnAction(e -> {
            // Update user details based on input fields
            user.setAccountDetails(emailField.getText(), firstNameField.getText(),
                    middleNameField.getText(), lastNameField.getText(),
                    preferredNameField.getText());
            showAlert("Success", "Your details have been updated."); // Notify user of success
            showHomePage(user); // Return to the home page
        });

        // Create a new Scene for updating user details with a size of 400x300 pixels
        Scene updateDetailsScene = new Scene(layout, 400, 300);
        primaryStage.setScene(updateDetailsScene); // Switch to the update details scene
    }

    /**
     * Displays the invite user page, allowing the admin to invite a new user by
     * entering their username, first name, last name, email, and preferred first name.
     *
     * @param user - The logged-in user, who must be an admin to invite new users
     */
    private void showInviteUserPage(User user) {
        VBox layout = new VBox(10); // Vertical layout with 10px spacing

        // Input fields for the new user's information
        TextField inviteUsernameField = new TextField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField(); // Input for email
        TextField preferredFirstNameField = new TextField(); // Input for preferred first name
        Button inviteButton = new Button("Invite"); // Button to send the invitation
        Button backButton = new Button("Back"); // Button to go back to the home page

        // Add all UI components to the layout
        layout.getChildren().addAll(
                new Label("Enter Username to Invite:"), inviteUsernameField,
                new Label("First Name:"), firstNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Email:"), emailField, // Email label and input
                new Label("Preferred First Name:"), preferredFirstNameField, // Preferred first name label and input
                inviteButton, backButton
        );

        // Set action for the invite button
        inviteButton.setOnAction(e -> {
            // Gather the input from the fields
            String username = inviteUsernameField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText(); // Get email
            String preferredFirstName = preferredFirstNameField.getText(); // Get preferred first name
            String role = "Student"; // Default role for invited users is Student

            // Call the inviteUser method from the Admin class
            admin.inviteUser(username, role, firstName, lastName, email, preferredFirstName);
            showAlert("Invitation Sent", "An invitation has been sent to " + username); // Notify admin of success
        });

        // Set action for the back button to return to the home page
        backButton.setOnAction(e -> showHomePage(user));

        // Create a new Scene for inviting users with a size of 400x300 pixels
        Scene inviteScene = new Scene(layout, 400, 300); // Adjust height for new fields
        primaryStage.setScene(inviteScene); // Switch to the invite user scene
    }

    /**
     * Searches the list of users by their username.
     *
     * @param username - The username to search for
     * @return the User object if found, null if not found
     */
    private User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user; // Return the user if the username matches
            }
        }
        return null; // Return null if the user is not found
    }

    /**
     * Displays an alert dialog with the given title and message.
     *
     * @param title - The title of the alert dialog
     * @param message - The message content of the alert dialog
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Create an information alert
        alert.setTitle(title); // Set the title
        alert.setHeaderText(null); // No header
        alert.setContentText(message); // Set the content of the alert
        alert.showAndWait(); // Display the alert and wait for user interaction
    }

    private void showHelpSystemPage(Admin admin) {
        VBox layout = new VBox(10); // Vertical layout with 10px spacing

        // UI elements for managing help articles
        TextField titleField = new TextField(); // Title field
        TextField shortDescriptionField = new TextField(); // Short description field
        TextField keywordsField = new TextField(); // Keywords field
        TextArea bodyField = new TextArea(); // Body field
        Button createButton = new Button("Create Article");
        Button deleteButton = new Button("Delete Article");
        Button listButton = new Button("List Articles");

        // Backup and Restore buttons
        Button backupButton = new Button("Backup Articles");
        Button restoreButton = new Button("Restore Articles");
        TextField backupFileField = new TextField(); // Field for backup file name

        // ComboBox for selecting an article to view
        ComboBox<HelpArticle> articleComboBox = new ComboBox<>();
        Button viewButton = new Button("View Article Content");

        // Back button
        Button backButton = new Button("Back");

        // Populate ComboBox with articles
        articleComboBox.getItems().addAll(admin.listArticles());

        // Add UI components to the layout
        layout.getChildren().addAll(
                new Label("Title:"), titleField,
                new Label("Short Description:"), shortDescriptionField,
                new Label("Keywords (comma-separated):"), keywordsField,
                new Label("Body:"), bodyField,
                createButton, deleteButton, listButton,
                new Label("Select an Article:"), articleComboBox,
                viewButton, backupFileField, backupButton, restoreButton,
                backButton // Add back button
        );

        // Create article action
        createButton.setOnAction(e -> {
            long articleId = System.currentTimeMillis(); // Generate a unique ID using the current timestamp
            HelpArticle newArticle = new HelpArticle(
                    articleId,
                    titleField.getText(),
                    shortDescriptionField.getText(),
                    bodyField.getText(),
                    List.of(keywordsField.getText().split(",")), // Split keywords by comma
                    List.of(), // References can be set as needed
                    "General", // Level can be set as required
                    List.of(), // Groups can be set as needed
                    null // Sensitive description if any
            );

            admin.addArticle(newArticle); // Call the method to add the article
            articleComboBox.getItems().add(newArticle); // Update ComboBox with the new article
            showAlert("Success", "Article created successfully.");
        });

        // Delete article action
        deleteButton.setOnAction(e -> {
            // Assuming the titleField contains the ID for simplicity
            long articleId = Long.parseLong(titleField.getText());
            if (admin.deleteArticle(articleId)) {
                articleComboBox.getItems().removeIf(article -> article.getId() == articleId); // Remove from ComboBox
                showAlert("Success", "Article deleted successfully.");
            } else {
                showAlert("Error", "Article not found for deletion.");
            }
        });

        // List articles action
        listButton.setOnAction(e -> {
            admin.listArticles(); // Lists articles in the console
            showAlert("Article List", "Check console for the list of help articles.");
        });

        // View article content action
        viewButton.setOnAction(e -> {
            HelpArticle selectedArticle = articleComboBox.getValue();
            if (selectedArticle != null) {
                showArticleContent(selectedArticle); // Show the content of the selected article
            } else {
                showAlert("Error", "Please select an article to view.");
            }
        });

        // Backup articles action
        backupButton.setOnAction(e -> {
            String filename = backupFileField.getText();
            if (!filename.isEmpty()) {
                admin.backupArticles(filename); // Method to backup articles to the specified file
                showAlert("Success", "Articles backed up to " + filename);
            } else {
                showAlert("Error", "Please enter a valid filename for backup.");
            }
        });

        // Restore articles action
        restoreButton.setOnAction(e -> {
            String filename = backupFileField.getText();
            if (!filename.isEmpty()) {
                boolean removeExisting = confirmRestore(); // Method to confirm if the user wants to remove existing articles
                admin.restoreArticles(filename, removeExisting); // Method to restore articles from the specified file
                showAlert("Success", "Articles restored from " + filename);
            } else {
                showAlert("Error", "Please enter a valid filename for restore.");
            }
        });

        // Back button action
        backButton.setOnAction(e -> showHomePage(admin)); // Go back to the home page

        // Create a new Scene for the help system management with a size of 400x400 pixels
        Scene helpSystemScene = new Scene(layout, 400, 400);
        primaryStage.setScene(helpSystemScene); // Switch to the help system management scene
    }

    private void showArticleContent(HelpArticle article) {
        VBox layout = new VBox(10); // Vertical layout with 10px spacing

        // Add article details to the layout
        layout.getChildren().add(new Label("Title: " + article.getTitle()));
        layout.getChildren().add(new Label("Short Description: " + article.getShortDescription()));
        layout.getChildren().add(new Label("Body:"));

        // TextArea for the body of the article
        TextArea bodyTextArea = new TextArea(article.getBody());
        bodyTextArea.setEditable(false); // Make the body field non-editable
        layout.getChildren().add(bodyTextArea); // Add the body text area to the layout

        // Create the Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showHelpSystemPage(admin)); // Go back to the help system page
        layout.getChildren().add(backButton); // Add the back button to the layout

        // Create a Scene for viewing the article content
        Scene articleContentScene = new Scene(layout, 400, 300);
        primaryStage.setScene(articleContentScene); // Switch to the article content scene
    }

    private boolean confirmRestore() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Restore Confirmation");
        alert.setHeaderText("Do you want to remove existing articles before restoring?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeOne = new ButtonType("Remove Existing Articles");
        ButtonType buttonTypeTwo = new ButtonType("Merge with Existing Articles");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        // Show the alert and wait for a response
        alert.showAndWait();

        // Check which button was clicked
        if (alert.getResult() == buttonTypeOne) {
            return true; // User chose to remove existing articles
        } else {
            return false; // User chose to merge or cancel
        }
    }

}
