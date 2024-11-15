package com.example.project360;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List; // Import List interface
import java.util.ArrayList;

/**
 * The Main class is the entry point for the JavaFX application.
 * It manages the login system and user interactions.
 */
public class Main extends Application {
    //private final List<User> users = new ArrayList<>(); // List to store all users
    private Stage primaryStage; // Primary stage for managing the UI scenes
    private Scene loginScene; // Different scenes for navigation
    
    private ArticleDatabase articleDatabase;
    private UserDatabase userDatabase;
    private List<HelpMessage> helpRequests;

    private int windowX = 1200;
    private int windowY = 800;
    
    private int titleTextSize = 50;
    
    private String defaultFont = "Helvetica";
    
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
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
    	articleDatabase = new ArticleDatabase();
    	userDatabase = new UserDatabase();
    	helpRequests = new ArrayList<>();
    	
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
        Text title = new Text("TH56 Help System"); // Title for the initial screen
        title.setFont(Font.font(defaultFont, titleTextSize));
        
        TextField usernameField = new TextField(); // Input field for username
        PasswordField passwordField = new PasswordField(); // Input field for password
        Button loginButton = new Button("Login"); // Button to trigger login
        Button createAccountButton = new Button("Create Account"); // Button to navigate to account creation
     
        
        // Add all UI components (labels, fields, buttons) to the layout
        layout.getChildren().addAll(
        		title,
                new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                loginButton, createAccountButton
        );
        
        
        

        // Debugging Tools to speed up testing by initializing data into system
        
        Button skipUserLogin = new Button("Skip- User");
        Button skipInstructorLogin = new Button("Skip- Instructor");
        Button skipAdminLogin = new Button("Skip- Admin");
        
        User testUser = new User("TestUser", "pw1");
        User testInstructor = new User("TestInstructor", "pw2");
        User testAdmin = new User("AdminAccount_", "Password**");
        
        Button skipButton = new Button("Init Test Data");
        skipButton.setOnAction(e -> {
            
            testUser.setAccountDetails(
            		"user1Email@email.com", "u1FN",
            		"u1MN", "u1LN",
            		"u1FN"
            );
            testUser.addRole("student");
            
            testInstructor.setAccountDetails(
            		"user2Email@email.com", "u2FN",
            		"u2MN", "u2LN",
            		"u2FN"
            );
            testInstructor.addRole("instructor");
            testInstructor.addRole("student");
            
            testAdmin.setAccountDetails(
                    "adminemail@email.com", "adminFN",
                    "adminMN", "adminLN",
                    "adminFN"
            );
            testAdmin.addRole("admin");
            testAdmin.addRole("instructor");
            testAdmin.setCurrentRole("admin");
            
            userDatabase.add(testUser);
            userDatabase.add(testInstructor);
            userDatabase.add(testAdmin);
            
            articleDatabase.addArticle(new HelpArticle(
            		1,
                    "Title1 Hello",
                    List.of("Jerry"),
                    "TestArticleee 9938 Blue",
                    "The First Test article",
                    List.of(), // Split keywords by comma
                    List.of(), // References can be set as needed
                    "Beginner", // Level can be set as required
                    List.of("Group1", "Group2", "Group3"), // Groups can be set as needed
                    null, // Sensitive description if any
                    articleDatabase, false, testAdmin)
            		, testAdmin);
            articleDatabase.addArticle(new HelpArticle(
            		2,
                    "Title2 Goodbye",
                    List.of("Jerry", "Sandra"),
                    "TestArticleee 9938 Green",
                    "The Second Test article",
                    List.of(), // Split keywords by comma
                    List.of(), // References can be set as needed
                    "Advanced", // Level can be set as required
                    List.of("Group4", "Group3"), // Groups can be set as needed
                    null,// Sensitive description if any
                    articleDatabase, false, testAdmin)
            		, testAdmin);
            articleDatabase.addArticle(new HelpArticle(
            		3,
                    "Title3 Sun Hello",
                    List.of("Sandra"),
                    "TestArticleee 9939 Blue RedBlue",
                    "The Third Test article",
                    List.of(), // Split keywords by comma
                    List.of(), // References can be set as needed
                    "Beginner", // Level can be set as required
                    List.of(), // Groups can be set as needed
                    null, // Sensitive description if any
                    articleDatabase, false, testAdmin)
            		, testAdmin);
            articleDatabase.addArticle(new HelpArticle(
            		3,
                    "Title4 Moon",
                    List.of("Tom"),
                    "TestArticleee 1000 White",
                    "The Forth Test article",
                    List.of(), // Split keywords by comma
                    List.of(), // References can be set as needed
                    "Intermediate", // Level can be set as required
                    List.of("Group3"), // Groups can be set as needed
                    null,			// Sensitive description if any
                    articleDatabase, false, testAdmin)
            		, testAdmin);
            
                    
            articleDatabase.createSpecialGroup("SpecialGroup1", testAdmin);
            articleDatabase.addArticle(new HelpArticle(
            		3,
                    "Title5 encrypt",
                    List.of("Henry"),
                    "TestArticleee 12 Gray",
                    "The Fifth Test article, and it's encrypted",
                    List.of(), // Split keywords by comma
                    List.of(), // References can be set as needed
                    "Expert", // Level can be set as required
                    List.of("SpecialGroup1"), // Groups can be set as needed
                    null,			// Sensitive description if any
                    articleDatabase, true, testAdmin)
            		, testAdmin);
            articleDatabase.addArticle(new HelpArticle(
            		3,
                    "Title6 decrypt",
                    List.of("Henry"),
                    "TestArticleee 13 Gray",
                    "The sixth Test article, and it's also encrypted",
                    List.of(), // Split keywords by comma
                    List.of(), // References can be set as needed
                    "Advanced", // Level can be set as required
                    List.of("SpecialGroup1"), // Groups can be set as needed
                    "This is an encrypted descrption",			// Sensitive description if any
                    articleDatabase, true, testAdmin)
            		, testAdmin);
        });
        
        skipUserLogin.setOnAction(e -> {
        	showSelectRolePage(testUser);
        });
        skipInstructorLogin.setOnAction(e -> {
        	showSelectRolePage(testInstructor);
        });
        skipAdminLogin.setOnAction(e -> {
        	showSelectRolePage(testAdmin);
        });
        
        layout.getChildren().addAll(
        		new Label("Debugging Tools:"),
        		skipButton,
                skipUserLogin, skipInstructorLogin, skipAdminLogin
        );
        
        
        
        
        
        
        


        // Set the action for the login button (handle login when clicked)
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        // Set the action for the create account button (navigate to account creation page)
        createAccountButton.setOnAction(e -> showCreateAccountPage());

        // Create a new Scene with the layout and set its size to 300x200 pixels
        loginScene = new Scene(layout, windowX, windowY);
    }

    /**
     * Displays the "Create Account" page where a new user can enter a username
     * and password to create an account.
     */
    private void showCreateAccountPage() {
        VBox layout = new VBox(10); // Vertical layout with 10px spacing between elements
        Text title = new Text("Create Your Account"); // Title for the initial screen
        title.setFont(Font.font(defaultFont, titleTextSize));
        TextField usernameField = new TextField(); // Input field for the new username
        PasswordField passwordField = new PasswordField(); // Input field for the password
        PasswordField confirmPasswordField = new PasswordField(); // Input field to confirm the password
        Button createButton = new Button("Create Account"); // Button to create the account
        Button backButton = new Button("Back"); // Button to return to the login page

        // Add all UI components (labels, text fields, buttons) to the layout
        layout.getChildren().addAll(
        		title,
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
        Scene createAccountScene = new Scene(layout, windowX, windowY);
        primaryStage.setScene(createAccountScene); // Switch to the account creation scene
    }

    /**
     * Adds a user to the list of users.
     *
     * @param user - The user to be added to the system
     */
    public void addUser(User user) {
    	userDatabase.add(user); // Add the user to the list of users
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
            if (userDatabase.getUsers().size() == 1 && userDatabase.getAdmins().isEmpty()) {
                User newAdmin = new User(username, password); // Set the first user as admin
                user.addRole("admin");
                userDatabase.add(newAdmin); // Add the admin to the user list
     
                showAddUserDetailsPage(newAdmin); // Navigate to the admin setup page
            } else {
                // If the user exists, log in and navigate to the home page
            	// If the user has more than 1 role, have it pick a role
            	// Else, default to its 1 role or a student if it has no roles
            	if (user.getRoles().size() > 1) {
            		showSelectRolePage(user);
            	} else if (user.getRoles().isEmpty()) {
            		user.addRole("student");
            		user.setCurrentRole("student");
            		showHomePage(user);
            	} else {
            		user.setCurrentRole(user.getRoles().get(0));
                    showHomePage(user);
            	}
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

        // Create a new Scene for the user details page with a pre-set size
        Scene userDetailsScene = new Scene(layout, windowX, windowY);
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
        if (userDatabase.getAdmins().isEmpty()) {
            // Create the admin account with the provided username and password
            User newAdmin = new User(username, password); // Pass the users list to the admin constructor
            newAdmin.addRole("admin");
            newAdmin.setCurrentRole("admin");
            userDatabase.add(newAdmin); // Add the admin to the list of users
            showAddUserDetailsPage(newAdmin); // Navigate to the setup page for the admin
        } else {
            // If admin already exists, create a regular user account
            User newUser = new User(username, password); // Create a new user with the given username and password
            newUser.addRole("student"); // Assign the default role of "Student" to the new user
            newUser.setCurrentRole("student");
            userDatabase.add(newUser); // Add the new user to the list of users
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
        backButton.setOnAction(e -> showUpdateUserDetailsPage(user)); // Navigate back to the home page

        // Create a new Scene for the change password page with a size of 300x300 pixels
        Scene changePasswordScene = new Scene(layout, windowX, windowY);
        primaryStage.setScene(changePasswordScene); // Switch to the change password scene
    }
    
    /**
     * Displays the page for selecting a role for the user to act as in their current session if
     * their user has multiple available roles
     *
     * @param user - The logged-in user
     */
    private void showSelectRolePage(User user) {
    	VBox layout = new VBox(10); // Vertical layout with 10px spacing
    	
    	ComboBox<String> availableRoles = new ComboBox<>();
    	availableRoles.getItems().addAll(user.getRoles());
    	Button selectButton = new Button("Select");
    	
    	
    	
    	
    	layout.getChildren().addAll(
    		new Label("Select active role:"),
    		availableRoles,
    		selectButton
    	);
    	
    	selectButton.setOnAction(e -> {
    		String selectedRole = availableRoles.getSelectionModel().getSelectedItem();
    		if (selectedRole == null) {
    			showAlert("Error", "Error- Must select a role");
    			return;
    		}
    		
    		user.setCurrentRole(selectedRole);
    		showHomePage(user);
    	});
    	
    	
    	
        // Create a new Scene for the user details page with a pre-set size
        Scene selectRoleScene = new Scene(layout, windowX, windowY);
        primaryStage.setScene(selectRoleScene); // Switch to the role select scene
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
        Label welcomeLabel = new Label("Welcome, " + user.getPreferredFirstName() + "!");
        welcomeLabel.setFont(Font.font(defaultFont, titleTextSize));

        // Buttons for different actions on the home page
        Button logoutButton = new Button("Logout"); // Button to log out
        Button updateDetailsButton = new Button("Update Personal Details"); // Button to update personal details
        Button helpArticleButton = new Button("Help Article Search"); // Button to search help articles
        Button manageArticlesButton = new Button("Manage Articles & Groups"); // Button to manage help articles
        Button manageUsersButton = new Button("Manage Users"); // Button to manage users
        Button helpMessageButton = new Button("Request Help"); // Button to access the help request system

        // Add all UI components to the layout
        layout.getChildren().addAll(
                welcomeLabel, updateDetailsButton,
                helpArticleButton, manageUsersButton,
                manageArticlesButton,
                helpMessageButton, logoutButton
        );

        // Create a new Scene for the home page with a size of 300x250 pixels
        Scene homeScene = new Scene(layout, windowX, windowY);

        // Set actions for each button
        helpArticleButton.setOnAction(e -> showArticleSearchPage(user, false));

        // Help system action - Opens the help system management page if user is admin or instructor
        if (user.isAdmin() || user.isInstructor()) { // Check if the user has admin privileges
        	manageArticlesButton.setOnAction(e -> showManageArticlesPage(user)); // Cast user to Admin
        } else {
        	manageArticlesButton.setDisable(true); // Disable the button for non-admin users
        }
        
        helpMessageButton.setOnAction(e -> showHelpMessagePage(user));
        
        // Allows the user to enter the manage users page if they are an admin
        if (user.isAdmin()) {
        	manageUsersButton.setOnAction(e -> showManageUsersPage(user)); // Cast user to Admin
        } else {
            manageUsersButton.setDisable(true); // Disable the button for non-admin users
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
        if (user.isAdmin()) {
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
                
                boolean isDeleted = userDatabase.deleteUser(username, user);

                // Display success or error message based on the result of the deletion
                if (isDeleted) {
                    showAlert("Success", username + " has been deleted.");
                } else {
                    showAlert("Error", "User not found.");
                }
            });

            // Set action for the back button to return to the home page
            backButton.setOnAction(e -> showManageUsersPage(user));

            // Create a new Scene for the delete user page with a size of 300x200 pixels
            Scene deleteUserScene = new Scene(layout, windowX, windowY);
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
    	if (userDatabase.listUsersToConsole(user)) {
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
        Button changePasswordButton = new Button("Change Password"); // Button to change password
        Button backButton = new Button("Back");

        // Add all UI components to the layout
        layout.getChildren().addAll(
                new Label("Email:"), emailField,
                new Label("First Name:"), firstNameField,
                new Label("Middle Name:"), middleNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Preferred First Name:"), preferredNameField,
                updateButton,
                changePasswordButton,
                backButton
        );
        
        // Change password action - Opens the change password page
        changePasswordButton.setOnAction(e -> showChangePasswordPage(user));
        
        backButton.setOnAction(e -> showHomePage(user));

        // Set action for the update button
        updateButton.setOnAction(e -> {
            // Update user details based on input fields
            user.setAccountDetails(emailField.getText(), firstNameField.getText(),
                    middleNameField.getText(), lastNameField.getText(),
                    preferredNameField.getText());
            showAlert("Success", "Your details have been updated."); // Notify user of success
            showUpdateUserDetailsPage(user); // Return to the home page
        });

        // Create a new Scene with a pre-set size
        Scene updateDetailsScene = new Scene(layout, windowX, windowY);
        primaryStage.setScene(updateDetailsScene); // Switch to the update details scene
    }
    
    /**
     * Displays the page for showing a variety of functions to manage help articles and article groups
     *
     * @param user - The logged-in user
     */
    @SuppressWarnings("unused")
	private void showManageArticlesPage(User user) {
    	VBox layout = new VBox(10); // Vertical layout with 10px spacing
    	
    	// List to display the groups available for the user to view
    	ListView<String> groupList = new ListView<>();
    	groupList.setPrefHeight(windowY/8);
    	ObservableList<String> groupListData = FXCollections.observableArrayList();
    	groupListData.addAll(articleDatabase.getArticleGroupNames(user));
        groupList.setItems(groupListData);
        
        // Buttons to choose what to do with the the group selected from the group list
        Button managePeopleButton = new Button("Manage selected group's users");
        Button manageGroupArticlesButton = new Button("Manage selected group's articles");
        
        
        // Objects to create a new group with a name and an optional special group setting
        TextField newGroupNameField = new TextField();
        CheckBox isSpecialGroupToggle = new CheckBox("Special Access Group");
        newGroupNameField.setPromptText("Name a new group");
        Button createGroupButton = new Button("Create");
        
        // Button that leads to a hub page to create new articles, or edit and delete existing ones
        Button editArticleButton = new Button("Create/Edit/Delete articles");
        
        Button backButton = new Button("Back");
        
        // add UI objects to scene
        layout.getChildren().addAll(
        		new Label("Articles:"),
        		editArticleButton,
        		new Label("Groups:"),
        		groupList,
        		managePeopleButton, manageGroupArticlesButton,
        		newGroupNameField, isSpecialGroupToggle, createGroupButton,
        		backButton
        		
        );
        
        
        managePeopleButton.setOnAction(e -> {
        	// Ensures a group is selected
        	if (groupList.getSelectionModel().getSelectedIndex() < 0) {
        		showAlert("Failed", "You must select a group");
        		return;
        	}
        	
        	ArticleGroup selectedGroup = articleDatabase.getGroup(groupList.getSelectionModel().getSelectedItem());
        	
        	// Ensures the user has the permission to edit the group
        	if (!selectedGroup.hasAdminGroupAccess(user)) {
        		showAlert("Error", "Error- Current user does not have admin rights to this group");
        		return;
        	}
        	
        	// Ensures a group is properly loaded before editing
        	if (selectedGroup != null) {
        		showGroupUsersPage(user, selectedGroup);
        	} else {
        		showAlert("Failed", "Error- Could not find group");
        	}
        });
        
        manageGroupArticlesButton.setOnAction(e -> {
        	// Ensures a group is selected
        	if (groupList.getSelectionModel().getSelectedIndex() < 0) {
        		showAlert("Failed", "You must select a group");
        		return;
        	}
        	
        	
        	ArticleGroup selectedGroup = articleDatabase.getGroup(groupList.getSelectionModel().getSelectedItem());
        	
        	// Ensures the user has the permission to edit the group
        	if (!selectedGroup.hasAdminGroupAccess(user)) {
        		showAlert("Error", "Error- Current user does not have admin rights to this group");
        		return;
        	}
        	
        	// Ensures a group is properly loaded before editing
        	if (selectedGroup != null) {
        		showGroupArticlesPage(user, selectedGroup);
        	} else {
        		showAlert("Failed", "Error- Could not find group");
        	}
        });
        
        createGroupButton.setOnAction(e -> {
        	
        	if (!newGroupNameField.getText().equals("")) { // ensures the user has entered a group name
        		// creates a normal group or a special group based on user input from the checkbox
        		if (isSpecialGroupToggle.isSelected()) {
        			articleDatabase.createSpecialGroup(newGroupNameField.getText(), user);
        		} else {
        			articleDatabase.createGroup(newGroupNameField.getText(), user);
        		}
        		
        		showAlert("Success", "Article Group Created");
        		showManageArticlesPage(user);
        	} else {
        		showAlert("Failed", "Must Enter Group Name");
        	}
        });
        
        // Opens the article search page with editing features enabled
        editArticleButton.setOnAction(e -> showArticleSearchPage(user, true));
        
        
        backButton.setOnAction(e -> showHomePage(user));
    	
    	
        // Create a new Scene with a pre-set size
        Scene manageArticlesScene = new Scene(layout, windowX, windowY); // Adjust height for new fields
        primaryStage.setScene(manageArticlesScene); // Switch to the article management scene
    }
    
    /**
     * Displays the page for managing the user permissions of a group
     *
     * @param user - The logged-in user
     * @param group - The group to edit
     */
    private void showGroupUsersPage(User user, ArticleGroup group) {
    	VBox layout = new VBox(10); // Vertical layout with 10px spacing
    	
    	// Objects to enter a username to either give viewing rights or admin rights to for the group
    	TextField addUserTextField = new TextField();
    	addUserTextField.setPromptText("Username");
    	Button giveViewingRightsButton = new Button("Give Viewing Rights");
    	Button giveAdminRightsButton = new Button("Give Admin Rights");
    	
    	// Selection boxes that allow the users with viewing/admin rights to be selected and stripped of rights
    	ComboBox<String> removeViewerSelection = new ComboBox<>();
    	Button removeViewingRightsButton = new Button("Remove Viewing Rights");
    	ComboBox<String> removeAdminSelection = new ComboBox<>();
    	removeAdminSelection.getItems().addAll(group.getAdminNames());
    	Button removeAdminRightsButton = new Button("Remove Admin Rights");
    	
    	
    	Button backButton = new Button("Back");
    	
    	// add ui objects to screen
    	layout.getChildren().addAll(
    			new Label("Give Rights"),
    			addUserTextField
    	);
    	
    	// Includes the viewing permission management objects if the group is a special access group
    	if (group.isSpecialAccess()) {
    		SpecialAccessGroup saGroup = (SpecialAccessGroup) group;
    		removeViewerSelection.getItems().addAll(saGroup.getViewerNames());
    		
    		// add ui objects to screen
    		layout.getChildren().addAll(
    			giveViewingRightsButton,
    			giveAdminRightsButton,
    			new Label("Remove Rights"),
    			removeViewerSelection,
    			removeViewingRightsButton,
    			removeAdminSelection,
    			removeAdminRightsButton,
    			backButton
    		);
    	} else {
    		// add ui objects to screen
    		layout.getChildren().addAll(
    			giveAdminRightsButton,
    			new Label("Remove Rights"),
    			removeAdminSelection,
    			removeAdminRightsButton,
    			backButton
    		);
    	}
    	
    	
    	giveViewingRightsButton.setOnAction(e -> {
    		SpecialAccessGroup saGroup = (SpecialAccessGroup) group;
    		// finds the user to give rights to
    		for (String usr: userDatabase.getUsers()) {
    			if (usr.equals(addUserTextField.getText())) {
    				// ensures the current user has edit access to this group
    				if (!saGroup.addViewer(addUserTextField.getText(), user)) {
    					showAlert("Error", "Error- Current user does not have permissions to this group");
    					return;
    				}
    				showGroupUsersPage(user, group);
    				return;
    			}
    		}
    		
    		showAlert("Failed", "Error- User " + addUserTextField.getText() + " does not exist");
    	});
    	
    	removeViewingRightsButton.setOnAction(e -> {
    		SpecialAccessGroup saGroup = (SpecialAccessGroup) group;
    		// Ensures that there is a user selected
    		if (removeViewerSelection.getSelectionModel().getSelectedItem() == null) {
    			showAlert("Failed", "Must select a student");
    			return;
    		}
    		// ensures the current user has edit access to this group
    		if (!saGroup.removeViewer(removeViewerSelection.getSelectionModel().getSelectedItem(), user)) {
    			showAlert("Error", "Error- Current user does not have permissions to this group");
				return;
    		}
    		showGroupUsersPage(user, group);
    	});
    	
    	
    	giveAdminRightsButton.setOnAction(e -> {
    		// finds the user to give rights to
    		for (String usr: userDatabase.getUsers()) {
    			if (usr.equals(addUserTextField.getText())) {
    				// ensures the current user has edit access to this group
    				if (!group.addAdmin(addUserTextField.getText(), user)) {
    					showAlert("Error", "Error- Current user does not have permissions to this group");
    					return;
    				}
    				showGroupUsersPage(user, group);
    				return;
    			}
    		}
    		
    		showAlert("Failed", "Error- User " + addUserTextField.getText() + " does not exist");
    	});
    	
    	removeAdminRightsButton.setOnAction(e -> {
    		// Ensures that there is a user selected
    		if (removeAdminSelection.getSelectionModel().getSelectedItem() == null) {
    			showAlert("Failed", "Must select an admin");
    			return;
    		}
    		// Ensures that there remains at least 1 admin per group
    		if (group.getAdminNames().size() < 2) {
    			showAlert("Failed", "Cannot remove the last admin of a group");
    			return;
    		}
    		// ensures the current user has edit access to this group
    		if (!group.removeAdmin(removeAdminSelection.getSelectionModel().getSelectedItem(), user)) {
    			showAlert("Error", "Error- Current user does not have permissions to this group");
				return;
    		}
    		showGroupUsersPage(user, group);
    	});
    	
    	
    	
    	
    	backButton.setOnAction(e -> showManageArticlesPage(user));
    	
        // Create a new Scene with a pre-set size
        Scene showGroupUsersPage = new Scene(layout, windowX, windowY); // Adjust height for new fields
        primaryStage.setScene(showGroupUsersPage); // Switch to the group user management scene
    }
    
    /**
     * Displays the group article management page where a group admin can add or remove articles from a group
     *
     * @param user - The logged-in user, who must be an admin to invite new users
     * @param group - the group to edit
     */
    private void showGroupArticlesPage(User user, ArticleGroup group) {
    	VBox layout = new VBox(10); // Vertical layout with 10px spacing
    	
    	// Objects to add a new article to a group or delete one from a group
    	TextField addArticleField = new TextField();
    	addArticleField.setPromptText("Article to add name or ID");
    	Button addArticleButton = new Button("Add Article To Group");
    	ComboBox<String> groupArticleList = new ComboBox<>();
    	groupArticleList.getItems().addAll(group.getArticleNames());
    	Button deleteArticleButton = new Button("Delete Article From Group");
    	
    	// Buttons to backup and restore the articles in the current group
    	Button backupButton = new Button("Backup Articles");
    	Button restoreButton = new Button("Restore Articles");
    	
    	Button backButton = new Button("Back");
    	
    	
    	// add ui objects to screen
    	layout.getChildren().addAll(
    			addArticleField, addArticleButton,
    			groupArticleList, deleteArticleButton,
    			backupButton, restoreButton,
    			backButton
    	);
    	
    	
    	addArticleButton.setOnAction(e -> {
    		for (HelpArticle article : articleDatabase.getArticles()) {
    			// Either the article title or id can be used to select an article to add
    			if (article.getTitle().equals(addArticleField.getText())
    					|| (article.getId() + "") == addArticleField.getText()) {
    				
    				ArrayList<String> updatedList = article.getGroups();
    				updatedList.add(group.getName());
    				articleDatabase.updateArticleGroups(article.getGroups(), article, user);
    				showAlert("Success", "Added article to group");
    				showGroupArticlesPage(user, group);
    				return;
    			}
    		}
    		
    		showAlert("Failed", "Error- Article " +addArticleField.getText() + " does not exist");
    	});
    	
    	deleteArticleButton.setOnAction(e -> {
    		HelpArticle selectedArticle = articleDatabase.getArticle(groupArticleList.getSelectionModel().getSelectedItem());
    		List<String> alteredGroupList = new ArrayList<>();
    		for (String groupName: selectedArticle.getGroups()) {
    			if (groupName != group.getName()) {
    				alteredGroupList.add(groupName);
    			}
    		}
    		articleDatabase.updateArticleGroups(alteredGroupList, selectedArticle, user);
    		showGroupArticlesPage(user, group);
    	});
    	
    	
    	
    	// Backup articles action
        /*backupButton.setOnAction(e -> {
            String filename = backupFileField.getText();
            if (!filename.isEmpty()) {
            	//articleDatabase.backupArticles(filename); // Method to backup articles to the specified file
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
                //articleDatabase.restoreArticles(filename, removeExisting); // Method to restore articles from the specified file
                showAlert("Success", "Articles restored from " + filename);
            } else {
                showAlert("Error", "Please enter a valid filename for restore.");
            }
        });*/
    	
    	
    	backButton.setOnAction(e -> showManageArticlesPage(user));
    	
        // Create a new Scene with a pre-set size
        Scene showGroupArticlesPage = new Scene(layout, windowX, windowY); // Adjust height for new fields
        primaryStage.setScene(showGroupArticlesPage); // Switch to the group article management scene
    }
    
    
    
    
    
    /**
     * Displays the manage users page which leads an admin towards a variety of different
     * pages where they can manage new or existing users
     * 
     * @param user - The logged-in user, who must be an admin to invite new users
     */
    private void showManageUsersPage(User user) {
    	VBox layout = new VBox(10); // Vertical layout with 10px spacing
    	
    	Button manageRolesButton = new Button("Manage User Roles"); // Button to edit the roles of users
    	Button inviteUserButton = new Button("Invite User"); // Button to invite a new user
        Button listUsersButton = new Button("List Users"); // Button to list all users
        Button deleteUserButton = new Button("Delete User"); // Button to delete users (visible to admin only)
        Button backButton = new Button("Back");
    	
    	
        // add ui objects to screen
    	layout.getChildren().addAll(
    			manageRolesButton,
    			inviteUserButton,
    			listUsersButton,
    			deleteUserButton,
    			backButton
    	);
    	
    	manageRolesButton.setOnAction(e -> showManageRolesPage(user));
    	
    	// Invite user action - Opens the invite user page
        inviteUserButton.setOnAction(e -> showInviteUserPage(user));

        // List users action - Displays a list of all users
        listUsersButton.setOnAction(e -> showUserList(user));

        // Delete user action - Opens the delete user page (visible only to admin)
        deleteUserButton.setOnAction(e -> showDeleteUserPage(user)); // Action for delete user
        
        backButton.setOnAction(e -> showHomePage(user));
    	
    	
    	
    	
        // Create a new Scene with a pre-set size
        Scene manageUsersScene = new Scene(layout, windowX, windowY); // Adjust height for new fields
        primaryStage.setScene(manageUsersScene); // Switch to the user management scene
    }
    
    /**
     * Displays the manage roles page where an admin can add or remove roles from themself or other users
     *
     * @param user - The logged-in user
     */
    private void showManageRolesPage(User user) {
    	VBox layout = new VBox(10); // Vertical layout with 10px spacing
    	
    	// Constants for coding ease
    	String studentRole = "student";
    	String instructorRole = "instructor";
    	String adminRole = "admin";
    	
    	// Box to select which user's roles to edit
    	ComboBox<String> userList = new ComboBox<>();
    	userList.getItems().addAll(userDatabase.getUsers());
    	
    	// Checkboxes to select each possible role
    	CheckBox studentToggle = new CheckBox(studentRole);
    	CheckBox instructorToggle = new CheckBox(instructorRole);
    	CheckBox adminToggle = new CheckBox(adminRole);
    	
    	Button saveChangesButton = new Button("Save Changes");
    	
    	Button backButton = new Button("Back");
    	
    	// add ui objects to screen
    	layout.getChildren().addAll(
    			userList,
    			studentToggle, instructorToggle, adminToggle,
    			saveChangesButton,
    			backButton
    	);
    	
    	// Checkboxes will automatically populate when a user is selected
    	userList.setOnAction(e -> {
    		User foundUser = userDatabase.getUser(userList.getSelectionModel().getSelectedItem());
    		if (foundUser == null) { return; }
    		
    		studentToggle.setSelected(foundUser.hasRole("student"));
    		instructorToggle.setSelected(foundUser.hasRole("instructor"));
    		adminToggle.setSelected(foundUser.hasRole("admin"));
    		
    	});
    	
    	
    	saveChangesButton.setOnAction(e -> {
    		// Ensures the user is an admin
    		if (!user.isAdmin()) {
    			showAlert("Error", "Error- Must be an admin to complete this action");
    			return;
    		}
    		
    		// Ensures the user isn't removing their own admin status
    		if (user.getUsername().equals(userList.getSelectionModel().getSelectedItem())) {
    			if (!adminToggle.isSelected()) {
    				showAlert("Error", "Error- cannot remove yourself as an admin");
    				return;
    			}
    		}
    		
    		
    		User foundUser = userDatabase.getUser(userList.getSelectionModel().getSelectedItem());
    		// adds or deletes the student role based on the student selection
    		if (foundUser == null ) {return;}
    		if (studentToggle.isSelected() && !foundUser.hasRole(studentRole)) {
    			foundUser.addRole(studentRole);
    		} else if (!studentToggle.isSelected() && foundUser.hasRole(studentRole)) {
    			foundUser.removeRole(studentRole);
    		}
    		
    		// adds or deletes the instructor role based on the instructor selection
    		if (instructorToggle.isSelected() && !foundUser.hasRole(instructorRole)) {
    			foundUser.addRole(instructorRole);
    		} else if (!instructorToggle.isSelected() && foundUser.hasRole(instructorRole)) {
    			foundUser.removeRole(instructorRole);
    		}
    		
    		// adds or deletes the admin role based on the admin selection
    		if (adminToggle.isSelected() && !foundUser.hasRole(adminRole)) {
    			foundUser.addRole(adminRole);
    		} else if (!adminToggle.isSelected() && foundUser.hasRole(adminRole)) {
    			foundUser.removeRole(adminRole);
    		}
    		
    		showAlert("Success", "Successfully updated user roles");
    		showManageRolesPage(user);
    	});
    	
    	backButton.setOnAction(e -> showManageUsersPage(user));
    	
        // Create a new Scene with a pre-set size
        Scene manageRolesScene = new Scene(layout, windowX, windowY); // Adjust height for new fields
        primaryStage.setScene(manageRolesScene); // Switch to the role management scene
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
        CheckBox instructorToggle = new CheckBox("Instructor");
        CheckBox adminToggle = new CheckBox("Admin");
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
            List<String> roles = new ArrayList<>();
            if (adminToggle.isSelected()) { roles.add("admin"); }
            if (instructorToggle.isSelected()) { roles.add("instructor"); }
            if (roles.size() == 0) { roles.add("student"); }

            // Call the inviteUser method from the Admin class
            if (userDatabase.inviteUser(username, roles, firstName, lastName, email, preferredFirstName, user)) {
            	showAlert("Invitation Sent", "An invitation has been sent to " + username); // Notify admin of success
            } else {
            	showAlert("Error", "You must be an admin to send an invitation");
            }
        });

        // Set action for the back button to return to the home page
        backButton.setOnAction(e -> showManageUsersPage(user));

        // Create a new Scene with a pre-set size
        Scene inviteScene = new Scene(layout, windowX, windowY); // Adjust height for new fields
        primaryStage.setScene(inviteScene); // Switch to the invite user scene
    }

    /**
     * Searches the list of users by their username.
     *
     * @param username - The username to search for
     * @return the User object if found, null if not found
     */
    private User findUser(String username) {
        for (String user : userDatabase.getUsers()) {
            if (user.equals(username)) {
                return userDatabase.getUser(user); // Return the user if the username matches
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

    /**
     * Displays the create/edit article page where it will show blank fields to create a new article,
     * or fill the editable boxes to allow for editing
     *
     * @param user - The logged-in user, who must be an admin to invite new users
     * @param editing - if the page is editing an existing article rather than creating a new one
     * @param article - the article to edit if it is editing
     */
    private void showCreateEditArticlePage(User user, boolean editing, HelpArticle article) {
    	VBox layout = new VBox(10);
    	
    	// UI elements for managing help articles
        TextField titleField = new TextField(); // Title field
        TextField shortDescriptionField = new TextField(); // Short description field
        TextField keywordsField = new TextField(); // Keywords field
        TextArea bodyField = new TextArea(); // Body field
        
        // Allows the selection of the level for the article
        ComboBox<String> articleLevelSelector = new ComboBox<>();
        articleLevelSelector.getItems().addAll(HelpArticle.getAvailableLevels());
        
        Button createButton = new Button("Create");
        
        Button backButton = new Button("Back");
        
        // Objects to allow new and existing groups to be added to the article
        TextField newGroupField = new TextField();
        newGroupField.setPromptText("Enter a new group");
        ComboBox<String> groupChoices = new ComboBox<>();
        groupChoices.getItems().addAll(articleDatabase.getArticleGroupNames(user));
        ListView<String> addedGroupList = new ListView<>();
        addedGroupList.setPrefHeight(windowY/10);
        ObservableList<String> groupListData = FXCollections.observableArrayList();
        addedGroupList.setItems(groupListData);
        
        // A checkbox that turns on encryption for the article
        Label encryptNotice = new Label("Encrypted articles must only be added to a single, existing, special access group");
        CheckBox encryptToggle = new CheckBox("Encrypt");
        
        // Button that allows a group to be added to the list
        Button addGroupButton = new Button("Add Group");
        addGroupButton.setOnAction(e -> {
        	String choice = "";
        	// prioritizes an existing selected group over a new one
        	if (groupChoices.getValue() != null) {
        		choice = groupChoices.getValue();
        	} else {
        		choice = newGroupField.getText();
        	}
        	groupChoices.setValue(null);
        	newGroupField.clear();
        	
        	if (!groupListData.contains(choice) && choice.length() != 0) {
        		groupListData.add(choice);
        	}
        });
        // Button that deletes the selected group from the list
        Button removeGroupButton = new Button("Remove Selected Group");
        removeGroupButton.setOnAction(e -> {
        	// ensures a group is selected
        	if (addedGroupList.getSelectionModel().getSelectedIndex() >= 0) {
        		groupListData.remove(addedGroupList.getSelectionModel().getSelectedIndex());
        	}
        });
        
        
        
        
        // add ui objects to screen
        layout.getChildren().addAll(
                new Label("Title:"), titleField,
                new Label("Short Description:"), shortDescriptionField,
                new Label("Keywords (comma-separated):"), keywordsField,
                new Label("Body:"), bodyField,
                new Label("Article Level:"), articleLevelSelector,
                encryptNotice, encryptToggle,
                new Label("Article Groups- Enter or select a group"), 
                newGroupField, groupChoices, addGroupButton,
                addedGroupList, removeGroupButton,
                createButton, backButton
        );
        
        ArrayList<String> originalGroups = new ArrayList<>();
        // Populates the fields with the current articles data if in editing mode
        if (editing) {
        	titleField.setText(article.getTitle());
        	titleField.setEditable(false);
        	shortDescriptionField.setText(article.getShortDescription());
        	keywordsField.setText(article.getKeywordsString());
        	bodyField.setText(article.getBody());
        
        	articleLevelSelector.setValue(article.getLevel());
        	encryptToggle.setSelected(article.isEncrypted());
        	encryptToggle.setDisable(true);
        	originalGroups.addAll(article.getGroups());
        	groupListData.addAll(originalGroups);
        	
        	// Makes sure data is decrypted and group can't be changed if in encrypted mode
        	if (article.isEncrypted()) {
        		bodyField.setText(article.getBody(user));
        		shortDescriptionField.setText(article.getSensitiveDescription(user));
        		addGroupButton.setDisable(true);
        		removeGroupButton.setDisable(true);
        		shortDescriptionField.setText(article.getSensitiveDescription(user));
        	}
        	
        	createButton.setText("Edit");
        }
        
        
        // Create article action
        createButton.setOnAction(e -> {
            long articleId = System.currentTimeMillis(); // Generate a unique ID using the current timestamp
            // ensures an article name has been provided
            if (titleField.getText().length() == 0) {
            	showAlert("Error", "Must Enter Article Name");
            	return;
            }
            // ensures an article level has been provided
            if (articleLevelSelector.getValue() == "") {
            	showAlert("Error", "Must Add Article Level");
            	return;
            }
            
            // Adds the current user to the list of authors for the article
            List<String> authors = new ArrayList<String>();
            if (editing) {
            	authors.addAll(article.getAuthors());
            }
            if (!authors.contains(user.getFirstName())) {
            	authors.add(user.getFirstName());
            }
            
            // Ensures that admins can't add body text
            if (user.isAdmin()) {
        		if (!bodyField.getText().isBlank()) {
        			showAlert("Error", "Error- You cannot add a body to a help article while an admin");
        			return;
        		}
        	}
           
            
            if (editing) {
            	// Ensures the current user is allowed to edit the article
            	if (!articleDatabase.hasEditAccess(user, article)) {
            		showAlert("Error", "Current user does not have edit permission");
            		return;
            	}

            	// updates the current article
            	article.setAuthors(authors, user);
            	article.setShortDescription(shortDescriptionField.getText(), user);
            	article.setBody(bodyField.getText(), user);
            	article.setKeywords(List.of(keywordsField.getText().split(",")), user);
            	article.setLevel(articleLevelSelector.getValue(), user);
            	articleDatabase.updateArticleGroups(groupListData, article, user);
            	
            	showAlert("Success", "Article edited successfully.");
                showArticleSearchPage(user, true);
            } else {
            	// Ensures only 1 existing special group is added if they are trying to make an encrypted article
            	if (encryptToggle.isSelected()) {
            		if (groupListData.size() != 1) {
            			showAlert("Error", "Error- Can only add 1 group to an encrypted article");
            			return;
            		} else if (!articleDatabase.getGroup(groupListData.get(0)).isSpecialAccess()) {
            			showAlert("Error", "Error- Encrypted article can only be added to a special accesss group");
            		}
            
            	}
            	
            
            	// Creates a new article from fields
            	HelpArticle newArticle = new HelpArticle(
                        articleId,
                        titleField.getText(),
                        authors,
                        shortDescriptionField.getText(),
                        bodyField.getText(),
                        List.of(keywordsField.getText().split(",")), // Split keywords by comma
                        List.of(), // References can be set as needed
                        articleLevelSelector.getValue(), // Level can be set as required
                        List.copyOf(groupListData), // Groups can be set as needed
                        null, // Sensitive description if any
                        articleDatabase,
                        encryptToggle.isSelected(),
                        user
                );
                

            	// Ensures the article has a unique title
                if (!articleDatabase.addArticle(newArticle, user)) {
                	showAlert("Error", "Article title already taken");
                	return;
                }
                showAlert("Success", "Article created successfully.");
                showArticleSearchPage(user, true);
            }
            
        });
       
        backButton.setOnAction(e -> showArticleSearchPage(user, true));
        
        // Create a new Scene for the help system management with a size of 400x400 pixels
        Scene createArticleScene = new Scene(layout, windowX, windowY);
        primaryStage.setScene(createArticleScene); // Switch to the help system management scene
    }
    
    
    
    
    
	@SuppressWarnings("unchecked")
	/**
     * Displays the invite user page, allowing the admin to invite a new user by
     * entering their username, first name, last name, email, and preferred first name.
     *
     * @param user - The logged-in user, who must be an admin to invite new users
     * @param editing - Specifies if the page is in editing mode, allowing for articles to be created, edited,
     * 					or deleted
     */
	private void showArticleSearchPage(User user, boolean editing) {
        VBox layout = new VBox(10); // Vertical layout with 10px spacing


        // A table with auto-populated columns that stores the articles returned from searches
        TableView<HelpArticle> articleList = new TableView<HelpArticle>();
        articleList.setEditable(false);
        articleList.getSelectionModel().setCellSelectionEnabled(false);
        
        TableColumn<HelpArticle, Number> numberColumn = new TableColumn<HelpArticle, Number>("#");
        numberColumn.setCellValueFactory(
        		column-> new ReadOnlyObjectWrapper<Number>(articleList.getItems().indexOf(column.getValue()) + 1));
        
        TableColumn<HelpArticle, String> titleColumn = new TableColumn<HelpArticle, String>("Title");
        titleColumn.setCellValueFactory(
        		new PropertyValueFactory<>("title"));
        
        TableColumn<HelpArticle, String> authorsColumn = new TableColumn<HelpArticle, String>("Author(s)");
        authorsColumn.setCellValueFactory(
        		new PropertyValueFactory<>("authorsString"));
        
        TableColumn<HelpArticle, String> descriptionColumn = new TableColumn<HelpArticle, String>("Description");
        descriptionColumn.setCellValueFactory(
        		new PropertyValueFactory<>("shortDescription"));
        
        articleList.getColumns().addAll(numberColumn, titleColumn, authorsColumn, descriptionColumn);
        ObservableList<HelpArticle> articleListData = FXCollections.observableArrayList();
        articleList.setItems(articleListData);
        
        
        // Holds the articles returned from searches, before they are filtered
        List<HelpArticle> foundArticles = new ArrayList<>();
        foundArticles.addAll(searchArticlesFor("", user));
        articleListData.addAll(foundArticles);
        
        
        // Objects to allow searching
        TextField searchField = new TextField();
        searchField.setPromptText("Search for an article title, author, or abstract");
        Button searchButton = new Button("Search");
        
        Label foundArticleCountLabel = new Label(articleListData.size() + " articles found");
        
        // Constraint selectors to filter results to a single group or a single level
        ComboBox<String> articleGroupSelector = new ComboBox<String>();
        ComboBox<String> levelConstraintSelector = new ComboBox<String>();
        
        articleGroupSelector.getItems().add("All");
        articleGroupSelector.getItems().addAll(articleDatabase.getArticleGroupNames(user));
        articleGroupSelector.setValue("All");
        articleGroupSelector.setOnAction(e -> {
        	articleListData.clear();
        	articleListData.addAll(filterArticlesFromSelections(foundArticles,
        			levelConstraintSelector.getValue(), articleGroupSelector.getValue()));
        	foundArticleCountLabel.setText(articleListData.size() + " articles found");
        });
        
        levelConstraintSelector.getItems().add("All");
        levelConstraintSelector.getItems().addAll(HelpArticle.getAvailableLevels());
        levelConstraintSelector.setValue("All");
        levelConstraintSelector.setOnAction(e -> {
        	articleListData.clear();
        	articleListData.addAll(filterArticlesFromSelections(foundArticles,
        			levelConstraintSelector.getValue(), articleGroupSelector.getValue()));
        	foundArticleCountLabel.setText(articleListData.size() + " articles found");
        });
        
        
     
        
        

        Button viewButton = new Button("View Article Content");
        
        // Buttons enabled in editing mode
        Button editButton = new Button("Edit Article");
        Button deleteButton = new Button("Delete Article");
        Button createButton = new Button("Create Article");

        // Back button
        Button backButton = new Button("Back");


        // Add UI components to the layout
        layout.getChildren().addAll(
                searchField, searchButton,
                new Label("Article Level"), levelConstraintSelector,
                new Label("Article Group"), articleGroupSelector,
                new Label("Select an Article:"), articleList,
                foundArticleCountLabel,
                viewButton
        );
        
        // adds buttons special to editing mode
        if (editing) {
        	layout.getChildren().add(editButton);
        	layout.getChildren().add(deleteButton);
        	layout.getChildren().add(createButton);
        } else {
        	editButton.setDisable(true);
        }
        
        layout.getChildren().addAll(
        		backButton
        );
        
        
        searchButton.setOnAction(e ->{
        	// collects all articles that contain the search term
        	foundArticles.clear();
        	foundArticles.addAll(searchArticlesFor(searchField.getText(), user));
        	
        	// than filters them based on selections
        	articleListData.clear();
        	articleListData.addAll(filterArticlesFromSelections(foundArticles,
        			levelConstraintSelector.getValue(), articleGroupSelector.getValue()));
        	foundArticleCountLabel.setText(articleListData.size() + " articles found");
        });
        

        // View article content action
        viewButton.setOnAction(e -> {
            HelpArticle selectedArticle = articleList.getSelectionModel().getSelectedItem();
            // ensures an article is selected
            if (selectedArticle != null) {
                showArticleContent(user, selectedArticle, editing); // Show the content of the selected article
            } else {
                showAlert("Error", "Please select an article to view.");
            }
        });
        
        
        editButton.setOnAction(e -> {
        	HelpArticle selectedArticle = articleList.getSelectionModel().getSelectedItem();
        	// ensures an article is selected
            if (selectedArticle != null) {
            	// ensures the current user is an instructor
            	if (user.getCurrentRole() == "instructor") {
            		showCreateEditArticlePage(user, true, selectedArticle);
            		return;
            	} else {
            		showAlert("Error", "You must be active as an instructor to edit");
            		return;
            	}
            	
            } else {
                showAlert("Error", "Please select an article to edit/delete.");
                return;
            }
        });
        
        deleteButton.setOnAction(e -> {
        	// ensures the article was successfully deleted
        	if (!articleDatabase.deleteArticle(articleList.getSelectionModel().getSelectedItem().getTitle(), user)) {
        		showAlert("Error", "Could not delete article");
        		return;
        	}
        	
        	showAlert("Success", "Article deleted successfully.");
        	showArticleSearchPage(user, true);
        });
        
        createButton.setOnAction(e -> showCreateEditArticlePage(user, false, null));

        // Back button action
        backButton.setOnAction(e -> {
        	if (editing) {
        		showManageArticlesPage(user);
        	} else {
        		showHomePage(user);
        	}
        }); // Go back to the home page

        // Create a new Scene for the help system management with a size of 400x400 pixels
        Scene articleSearchScene = new Scene(layout, windowX, windowY);
        primaryStage.setScene(articleSearchScene); // Switch to the article search scene
    }
	
	/**
     * Displays the help message page where a user can send help messages to the system
     * to be viewed by admins
     *
     * @param user - The logged-in user
     */
	private void showHelpMessagePage(User user) {
		VBox layout = new VBox(10); // Vertical layout with 10px spacing
		
		Text title = new Text("Request Help"); // Title for the initial screen
        title.setFont(Font.font(defaultFont, titleTextSize));
        
        // allows the selection of the type of help request
        ComboBox<String> requestTypeSelect = new ComboBox<>();
        requestTypeSelect.getItems().addAll(
        		"Generic Request- Help using this software application",
        		"Specific Request- Request a new help article to be added"
        );
        
        TextField titleField = new TextField();
        titleField.setPromptText("Message title");
        
        TextField bodyField = new TextField();
        bodyField.setPromptText("Message body");
        
        Button sendButton = new Button("Send");
        
		Button viewMessagesButton = new Button("ViewMessages");
		if (!user.isAdmin()) {
			viewMessagesButton.setDisable(true);;
		}
		
		// Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showHomePage(user)); // Go back to the home page
       


        // Add UI components to the layout
        layout.getChildren().addAll(
        		title,
        		new Label("Request type:"), requestTypeSelect,
        		titleField, bodyField, sendButton
        );
        // enables viewing of articles if the user is an admin
        if (user.isAdmin()) {
        	viewMessagesButton.setOnAction(e -> {
        		showViewMessagesPage(user);
        	});
			layout.getChildren().add(viewMessagesButton);
		}
        layout.getChildren().add(
                backButton
        );
        
        
        sendButton.setOnAction(e -> {
        	// ensures the user has specified a type, a title, and a message body
        	if (requestTypeSelect.getSelectionModel().getSelectedItem() == null) {
        		showAlert("Error", "Must select message type");
        		return;
        	}
        	if (titleField.getText().isBlank()) {
        		showAlert("Error", "Must add message title");
        		return;
        	}
        	if (bodyField.getText().isBlank()) {
        		showAlert("Error", "Must add message body");
        		return;
        	}
        	
        	String storedType;
        	if (requestTypeSelect.getSelectionModel().getSelectedItem().charAt(0) == 'G') {
        		storedType = "Generic";
        	} else { 
        		storedType = "Specific"; 
        	}
        	HelpMessage message = new HelpMessage (storedType,
        						user.getEmail(),
        						titleField.getText(),
        						bodyField.getText());
        	
        	helpRequests.add(message);
        	showAlert("Success", "Message successfully sent");
        	showHelpMessagePage(user);
        });
		
		
		// Create a new Scene for the help system management with a size of 400x400 pixels
        Scene helpMessageScene = new Scene(layout, windowX, windowY);
        primaryStage.setScene(helpMessageScene); // Switch to the help message sending scene
	}
	
	/**
     * Displays the view help message page where admins can view and delte help requests from users
     *
     * @param user - The logged-in user
     */
	@SuppressWarnings("unchecked")
	private void showViewMessagesPage(User user) {
		VBox layout = new VBox(10); // Vertical layout with 10px spacing
		
		// Table with auto-populating columns that displays Help Messages in the system
        TableView<HelpMessage> messageList = new TableView<HelpMessage>();
        messageList.setEditable(false);
        messageList.getSelectionModel().setCellSelectionEnabled(false);
        
        TableColumn<HelpMessage, Number> numberColumn = new TableColumn<HelpMessage, Number>("#");
        numberColumn.setCellValueFactory(
        		column-> new ReadOnlyObjectWrapper<Number>(messageList.getItems().indexOf(column.getValue()) + 1));
        
        TableColumn<HelpMessage, String> typeColumn = new TableColumn<HelpMessage, String>("Type");
        typeColumn.setCellValueFactory(
        		new PropertyValueFactory<>("type"));
        
        TableColumn<HelpMessage, String> titleColumn = new TableColumn<HelpMessage, String>("Title");
        titleColumn.setCellValueFactory(
        		new PropertyValueFactory<>("title"));
        
        TableColumn<HelpMessage, String> emailColumn = new TableColumn<HelpMessage, String>("Email");
        emailColumn.setCellValueFactory(
        		new PropertyValueFactory<>("email"));
        
        TableColumn<HelpMessage, String> bodyColumn = new TableColumn<HelpMessage, String>("Message");
        bodyColumn.setCellValueFactory(
        		new PropertyValueFactory<>("body"));
        
        messageList.getColumns().addAll(numberColumn, typeColumn, titleColumn, emailColumn, bodyColumn);
        ObservableList<HelpMessage> messageListData = FXCollections.observableArrayList();
        messageList.setItems(messageListData);
        
        messageListData.addAll(helpRequests);
		
        
        Button removeButton = new Button("Delete selected message");
		// Create the Back button
        Button backButton = new Button("Back");
        
        // add ui objects to screen
        layout.getChildren().addAll(
        		messageList,
        		removeButton,
        		backButton
        );
        
        
        removeButton.setOnAction(e -> {
        	helpRequests.remove(messageList.getSelectionModel().getSelectedItem());
        	showViewMessagesPage(user);
        });
        
        backButton.setOnAction(e -> showHelpMessagePage(user)); // Go back to the help system page
		
		// Create a Scene for viewing the article content
        Scene viewMessagesScene = new Scene(layout, windowX, windowY);
        primaryStage.setScene(viewMessagesScene); // Switch to the message viewing scene
	}
	
	
	/**
     * Displays the invite user page, allowing the admin to invite a new user by
     * entering their username, first name, last name, email, and preferred first name.
     *
     * @param user - The logged-in user, who must be an admin to invite new users
     * @param article - the article for the shown content
     * @param editing - stored variable for if the system is in editing mode
     */
    private void showArticleContent(User user, HelpArticle article, boolean editing) {
    	// ensures that admins can't view help articles
    	if (user.isAdmin()) {
    		showAlert("Error", "Error- You cannot view HelpArticles while active as an admin");
    		showArticleSearchPage(user, editing);
    		return;
    	}
    	
        VBox layout = new VBox(10); // Vertical layout with 10px spacing

        // Add article details to the layout
        layout.getChildren().add(new Label("Title: " + article.getTitle()));
        String description = article.getShortDescription();
        if (article.isEncrypted()) {
        	description = article.getSensitiveDescription(user);
        }
        layout.getChildren().add(new Label("Short Description: " + description));
        layout.getChildren().add(new Label("Body:"));

        // TextArea for the body of the article
        TextArea bodyTextArea = new TextArea(article.getBody(user));
        
        bodyTextArea.setEditable(false); // Make the body field non-editable
        layout.getChildren().add(bodyTextArea); // Add the body text area to the layout

        // Create the Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showArticleSearchPage(user, editing)); // Go back to the help system page
        layout.getChildren().add(backButton); // Add the back button to the layout

        // Create a Scene for viewing the article content
        Scene articleContentScene = new Scene(layout, windowX, windowY);
        primaryStage.setScene(articleContentScene); // Switch to the article content scene
    }

    /*private boolean confirmRestore() {
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
    }*/

    
    
    
    
    
    // Returns all articles with the search term in the title, authors list, or description
    // also filters out non-allowed encrypted articles
    private List<HelpArticle> searchArticlesFor(String searchText, User user) {
    	List<HelpArticle> availableArticles = articleDatabase.getArticles();
    	List<HelpArticle> foundArticles = new ArrayList<HelpArticle>();
    	
    	for (HelpArticle article: availableArticles) {
    		if (article.isEncrypted()) {
    			// Checks if the user has access to an article if its encrypted
    			if (articleDatabase.getSpecialGroup(article.getSpecialGroup()).hasEncryptedViewAccess(user)) {
    				foundArticles.add(article);
    			}
    		} else if (searchText == "") { foundArticles.add(article); }	// returns all articles if the search term is blank
    		else if (article.getTitle().contains(searchText)) { foundArticles.add(article); }
    		else if (article.getAuthorsString().contains(searchText)) { foundArticles.add(article); }
    		else if (article.getShortDescription().contains(searchText)) { foundArticles.add(article); } 
    	}
    	return foundArticles;
    }
    
    
    
    // Filters out articles from the level and group constraints before they are displayed to the user
    private List<HelpArticle> filterArticlesFromSelections(List<HelpArticle> originalArticles,
    		String levelConstraint, String groupConstraint) {
    	
    	// Filters out articles based on level
    	List<HelpArticle> filteredArticles1 = new ArrayList<HelpArticle>();
    	if (levelConstraint != "All") {
    		for (HelpArticle article : originalArticles) {
    			if (article.getLevel() == levelConstraint) {
    				filteredArticles1.add(article);
    			}
    		}
    	} else {
    		filteredArticles1 = List.copyOf(originalArticles);
    	}
    	
    	// Filters out articles based on group
    	List<HelpArticle> filteredArticles2 = new ArrayList<HelpArticle>();
    	if (groupConstraint != "All") {
    		for (HelpArticle article : filteredArticles1) {
    			if (article.getGroups().contains(groupConstraint)) {
    				filteredArticles2.add(article);
    			}
    		}
    	} else {
    		return filteredArticles1;
    	}
    	
    	
    	return filteredArticles2;
    }
    
    
    
    
    
    
}
