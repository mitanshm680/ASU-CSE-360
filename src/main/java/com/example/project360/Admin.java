package com.example.project360;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Admin extends User {
    private List<User> users;


    public Admin(String username, String password, List<User> users) {
        super(username, password);
        this.users = users;
        addRole("Admin");
    }

    public String generateInvitationCode() {
        return String.valueOf(System.currentTimeMillis()); // Simple unique code for demonstration
    }

    public void inviteUser(String username, String role, String firstName, String lastName, String email, String preferredFirstName) {
        String invitationCode = generateInvitationCode();
        User invitedUser = new User(username, invitationCode);
        invitedUser.setAccountDetails(email, firstName, null, lastName, preferredFirstName); // Set names and email
        invitedUser.addRole(role); // Add the role to the invited user
        System.out.println("Invited user: " + username + " with role: " + role + " and invitation code: " + invitationCode);

        users.add(invitedUser); // Add the invited user to the user list
    }

    public void listUsers(List<User> users) {
        System.out.println("Listing users:");
        for (User user : users) {
            System.out.println(user.getUsername() + " - " + user.getFirstName() + " " + user.getLastName() + " Roles: " + user.getRoles());
        }
    }


    public String generateOneTimePassword() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    public void resetUserAccount(User user) {
        String oneTimePassword = generateOneTimePassword();
        long expiry = System.currentTimeMillis() + 3600000; // 1 hour from now
        user.setOneTimePassword(oneTimePassword, expiry);
        System.out.println("Reset password for user: " + user.getUsername() + " with one-time password: " + oneTimePassword);
    }

    public void deleteUser(User user) {
        // Logic to delete user (not implemented in this example)
        System.out.println("Deleted user: " + user.getUsername());
    }

    public void addRoleToUser(User user, String role) {
        user.addRole(role);
        System.out.println("Added role " + role + " to user: " + user.getUsername());
    }

    public void removeRoleFromUser(User user, String role) {
        user.removeRole(role);
        System.out.println("Removed role " + role + " from user: " + user.getUsername());
    }
}
