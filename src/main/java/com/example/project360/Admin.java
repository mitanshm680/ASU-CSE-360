package com.example.project360;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Admin class extends the User class, adding special functionalities
 * only available to admin users. This includes inviting users, listing
 * users, resetting passwords, and managing user roles.
 */
public class Admin extends User {
    private List<User> users; // List of users that the admin can manage

    /**
     * Constructor for Admin class. Calls the superclass constructor (User)
     * and assigns the admin role.
     *
     * @param username - Admin's username
     * @param password - Admin's password
     * @param users - List of users the admin manages
     */
    public Admin(String username, String password, List<User> users) {
        super(username, password); // Call to the superclass User constructor
        this.users = users; // Initialize the user list
        addRole("Admin"); // Automatically add the "Admin" role to this user
    }

    /**
     * Generates a unique invitation code for a new user.
     *
     * @return Invitation code as a String, using the current timestamp for uniqueness.
     */
    public String generateInvitationCode() {
        return String.valueOf(System.currentTimeMillis()); // Simple, unique code generation using time
    }

    /**
     * Invites a new user by creating a User object and assigning a role.
     *
     * @param username - The new user's username
     * @param role - The role assigned to the new user (e.g., Student, Instructor)
     * @param firstName - The new user's first name
     * @param lastName - The new user's last name
     * @param email - The new user's email address
     * @param preferredFirstName - The new user's preferred first name
     */
    public void inviteUser(String username, String role, String firstName, String lastName, String email, String preferredFirstName) {
        // Generate an invitation code for the new user
        String invitationCode = generateInvitationCode();

        // Create a new User object with the provided username and invitation code
        User invitedUser = new User(username, invitationCode);

        // Set the account details for the new user
        invitedUser.setAccountDetails(email, firstName, null, lastName, preferredFirstName);

        // Assign the specified role to the invited user
        invitedUser.addRole(role);

        // Display information about the invited user and the invitation code
        System.out.println("Invited user: " + username + " with role: " + role + " and invitation code: " + invitationCode);

        // Add the invited user to the list of users managed by the admin
        users.add(invitedUser);
    }

    /**
     * Lists all users managed by the admin, displaying their username, full name,
     * and roles.
     */
    public void listUsers() {
        System.out.println("Listing users:");
        // Loop through each user in the users list and display their details
        for (User user : users) {
            System.out.println(user.getUsername() + " - " + user.getFirstName() + " " + user.getLastName() + " Roles: " + user.getRoles());
        }
    }

    /**
     * Deletes a user by their username. This is a static method that calls the
     * User class's static deleteUser method.
     *
     * @param username - The username of the user to be deleted
     * @return True if the user was successfully deleted, false if the user was not found
     */
    public static boolean deleteUser(String username) {
        // Call the static deleteUser method from the User class
        boolean isDeleted = User.deleteUser(username);

        // Output the result of the deletion attempt
        if (isDeleted) {
            System.out.println("User " + username + " has been deleted.");
        } else {
            System.out.println("User " + username + " not found.");
        }
        return isDeleted; // Return the result of the deletion attempt
    }

    /**
     * Generates a one-time password (OTP) consisting of 6 random digits.
     *
     * @return A six-digit OTP as a String
     */
    public String generateOneTimePassword() {
        Random random = new Random(); // Initialize random number generator
        return String.format("%06d", random.nextInt(1000000)); // Generate a 6-digit OTP
    }

    /**
     * Resets the user account by setting a new one-time password (OTP) that expires
     * in one hour.
     *
     * @param user - The user whose account is being reset
     */
    public void resetUserAccount(User user) {
        // Generate a new one-time password
        String oneTimePassword = generateOneTimePassword();

        // Set the expiry time for the one-time password to 1 hour from now
        long expiry = System.currentTimeMillis() + 3600000; // 1 hour in milliseconds

        // Set the user's one-time password and its expiry
        user.setOneTimePassword(oneTimePassword, expiry);

        // Output the reset details
        System.out.println("Reset password for user: " + user.getUsername() + " with one-time password: " + oneTimePassword);
    }

    /**
     * Adds a role to the specified user.
     *
     * @param user - The user to whom the role will be added
     * @param role - The role to be added (e.g., Instructor, Admin)
     */
    public void addRoleToUser(User user, String role) {
        user.addRole(role); // Call the addRole method in User to add the role
        System.out.println("Added role " + role + " to user: " + user.getUsername());
    }

    /**
     * Removes a role from the specified user.
     *
     * @param user - The user from whom the role will be removed
     * @param role - The role to be removed
     */
    public void removeRoleFromUser(User user, String role) {
        user.removeRole(role); // Call the removeRole method in User to remove the role
        System.out.println("Removed role " + role + " from user: " + user.getUsername());
    }
}
