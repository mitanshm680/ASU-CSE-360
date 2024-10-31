package com.example.project360;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The User class manages user data and functionality, including:
 * - User registration using an invitation system.
 * - Role management (Student, Instructor, Admin, etc.).
 * - Secure login via passwords and OTP (invitation codes).
 */

public class User {
    private String username; // The user's unique username
    private String password;  // The user's password, initially the invitation code
    private String email; // The user's email address
    private String firstName; // The user's first name
    private String middleName; // The user's middle name
    private String lastName; // The user's last name
    private String preferredFirstName; // The user's preferred first name (optional)
    private List<String> roles; // List of roles the user has (e.g., student, admin)
    private String oneTimePassword; // One-time password for first-time login (also the invitation code)
    private long oneTimePasswordExpiry; // Expiry time for the one-time password (in milliseconds)
    private String invitationCode; // Code used to invite the user to the system
    private long invitationCodeExpiry; // Expiry time for the invitation code (in milliseconds)
    public static List<User> userList = new ArrayList<>(); // Static list storing all users
    public static List<HelpArticle> articles = new ArrayList<>(); // List of HelpArticle objects

    // Constructor to create a new user with a username and an invitation code
    public User(String username, String invitationCode) {
        this.username = username;
        this.password = invitationCode; // Password is set to the invitation code initially
        this.invitationCode = invitationCode; // Store the invitation code
        this.invitationCodeExpiry = System.currentTimeMillis() + 3600000; // Invitation code valid for 1 hour
        this.roles = new ArrayList<>(); // Initialize the roles list
        addRole("Student"); // Assign the default role "Student" to the new user
        userList.add(this); // Add the new user to the user list
    }

    // Static method to delete a user by username
    public static boolean deleteUser(String username) {
        User userToRemove = findUser(username); // Find the user by username
        if (userToRemove != null) {
            userList.remove(userToRemove); // Remove the user if found
            return true; // Deletion successful
        }
        return false; // Return false if user not found
    }

    // Static method to find a user by username
    public static User findUser(String username) {
        for (User user : userList) {
            // Return the user if the username matches
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null; // Return null if the user is not found
    }

    // Getter and setter methods for user details

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredFirstName() {
        return preferredFirstName;
    }

    public void setPreferredFirstName(String preferredFirstName) {
        this.preferredFirstName = preferredFirstName;
    }

    public String getUsername() {
        return username;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    // Method to add a role to the user, ensuring no duplicates
    public void addRole(String role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }

    // Method to check if the user has a specific role
    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    // Method to remove a role from the user
    public void removeRole(String role) {
        roles.remove(role);
    }

    // Method to set a one-time password for the user, with an expiry time
    public void setOneTimePassword(String password, long expiry) {
        this.oneTimePassword = password;
        this.oneTimePasswordExpiry = expiry; // Set the expiration time for the one-time password
    }

    // Method to validate if the one-time password is still valid
    public boolean isOneTimePasswordValid(String password) {
        return oneTimePassword != null && oneTimePassword.equals(password) &&
                System.currentTimeMillis() <= oneTimePasswordExpiry; // Ensure OTP hasn't expired
    }

    // Method to set the user's account details, such as names and email
    public void setAccountDetails(String email, String firstName, String middleName, String lastName, String preferredFirstName) {
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.preferredFirstName = preferredFirstName; // Set preferred name
    }

    // Method to update the user's password
    public void setPassword(String newPassword) {
        this.password = newPassword; // Set the new password
    }

    // Method to check if the invitation code is still valid
    public boolean isInvitationCodeValid() {
        return invitationCode != null && System.currentTimeMillis() <= invitationCodeExpiry; // Ensure the code hasn't expired
    }

    // Method to set a new invitation code with a 1-hour expiry time
    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
        this.invitationCodeExpiry = System.currentTimeMillis() + 3600000; // One time password valid for 1 hour
    }

    // Method to add a help article
    public static void addHelpArticle() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter article title: ");
        String title = scanner.nextLine();

        System.out.print("Enter short description: ");
        String shortDescription = scanner.nextLine();

        System.out.print("Enter article body: ");
        String body = scanner.nextLine();

        System.out.print("Enter keywords (comma-separated): ");
        List<String> keywords = List.of(scanner.nextLine().split(","));

        System.out.print("Enter references (comma-separated): ");
        List<String> references = List.of(scanner.nextLine().split(","));

        System.out.print("Enter article level (e.g., Beginner, Intermediate): ");
        String level = scanner.nextLine();

        System.out.print("Enter groups (comma-separated): ");
        List<String> groups = List.of(scanner.nextLine().split(","));

        System.out.print("Enter sensitive description (optional): ");
        String sensitiveDescription = scanner.nextLine();

        HelpArticle article = new HelpArticle(
                System.currentTimeMillis(), title, shortDescription, body, keywords, references, level, groups, sensitiveDescription
        );

        articles.add(article); // Add the article to the list
        System.out.println("Help article added successfully!");
    }

    // Method to delete a help article
    public static void deleteHelpArticle() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the article to delete: ");
        long articleId = scanner.nextLong();

        boolean removed = articles.removeIf(article -> article.getId() == articleId);
        System.out.println(removed ? "Help article deleted." : "Article not found.");
    }

    // Method to list all help articles
    public static void listHelpArticles() {
        System.out.println("Listing all help articles:");
        for (HelpArticle article : articles) {
            System.out.println("ID: " + article.getId() + " | Title: " + article.getTitle());
        }
    }

    // Method for users to view help articles
    public void viewHelpArticles() {
        System.out.println("Available Help Articles:");
        for (HelpArticle article : articles) {
            System.out.println("ID: " + article.getId() + " - Title: " + article.getTitle());
        }
    }

    // Method for users to view the details of a specific help article
    public void viewArticleDetails(long articleId) {
        for (HelpArticle article : articles) {
            if (article.getId() == articleId) {
                System.out.println("Title: " + article.getTitle());
                System.out.println("Description: " + article.getShortDescription());
                System.out.println("Body: " + article.getBody());
                System.out.println("Keywords: " + String.join(", ", article.getKeywords()));
                System.out.println("References: " + String.join(", ", article.getReferences()));
                System.out.println("Level: " + article.getLevel());
                System.out.println("Groups: " + String.join(", ", article.getGroups()));
                return;
            }
        }
        System.out.println("Article not found."); // If the article doesn't exist
    }
}
