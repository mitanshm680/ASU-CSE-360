package com.example.project360;

import java.util.ArrayList;
import java.util.List;

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
    
    private String currentRole;

    // Constructor to create a new user with a username and an invitation code
    public User(String username, String invitationCode) {
        this.username = username;
        this.password = invitationCode; // Password is set to the invitation code initially
        this.invitationCode = invitationCode; // Store the invitation code
        this.invitationCodeExpiry = System.currentTimeMillis() + 3600000; // Invitation code valid for 1 hour
        this.roles = new ArrayList<>(); // Initialize the roles list
        this.currentRole = "";
        addRole("student"); // Assign the default role "Student" to the new user
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
    	if (preferredFirstName == null) {
    		return firstName;
    	}
    	if (preferredFirstName.isBlank()) {
    		return firstName;
    	}
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
    
    public void setCurrentRole(String role) {
    	currentRole = role;
    }
    public String getCurrentRole() {
    	return currentRole;
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
    
    public boolean isInstructor() { return currentRole.equals("instructor"); }
    public boolean isAdmin() { return currentRole.equals("admin"); }
}
