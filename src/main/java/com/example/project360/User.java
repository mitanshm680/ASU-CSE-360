package com.example.project360;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;  // Regular password
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredFirstName;
    private List<String> roles;
    private String oneTimePassword; // Can be used as invitation code
    private long oneTimePasswordExpiry; // Expiry time for OTP
    private String invitationCode; // To store the invitation code
    private long invitationCodeExpiry;
    public static List<User> userList = new ArrayList<>();


    public User(String username, String invitationCode) {
        this.username = username;
        this.password = invitationCode; // Password is the invitation code
        this.invitationCode = invitationCode; // Store the invitation code
        this.invitationCodeExpiry = System.currentTimeMillis() + 3600000; // Valid for 1 hour
        this.roles = new ArrayList<>();
        addRole("Student"); // Default role
        userList.add(this);
    }

    public static boolean login(String username, String password) {
        for (User user : userList) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return true; // Successful login
            }
        }
        return false; // Failed login
    }

    public static boolean deleteUser(String username) {
        User userToRemove = findUser(username);
        if (userToRemove != null) {
            userList.remove(userToRemove);
            return true; // Deletion successful
        }
        return false; // User not found
    }


    public static User findUser(String username) {
        for (User user : userList) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null; // User not found
    }



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

    public void addRole(String role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }

    public void removeRole(String role) {
        roles.remove(role);
    }

    public void setOneTimePassword(String password, long expiry) {
        this.oneTimePassword = password;
        this.oneTimePasswordExpiry = expiry;
    }

    public boolean isOneTimePasswordValid(String password) {
        return oneTimePassword != null && oneTimePassword.equals(password) &&
                System.currentTimeMillis() <= oneTimePasswordExpiry;
    }

    public void setAccountDetails(String email, String firstName, String middleName, String lastName, String preferredFirstName) {
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.preferredFirstName = preferredFirstName;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public boolean isInvitationCodeValid() {
        return invitationCode != null && System.currentTimeMillis() <= invitationCodeExpiry;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
        this.invitationCodeExpiry = System.currentTimeMillis() + 3600000; // Valid for 1 hour
    }

}
