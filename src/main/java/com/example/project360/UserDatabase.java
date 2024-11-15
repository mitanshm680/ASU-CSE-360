package com.example.project360;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserDatabase {
	private List<User> users; // List of users that the admin can manage
	
	public UserDatabase() {
		users = new ArrayList<>();
	}
	
	/**
     * Adds a user to the database
     * 
     * @param user - user to check for permission
     */
	public void add(User user) {
		users.add(user);
	}
	
	/**
     * Returns a list of all admin users in the database
     * 
     */
	public List<User> getAdmins() {
		List<User> admins = new ArrayList<>();
		for (User user: users) {
			if (user.isAdmin()) {
				admins.add(user);
			}
		}
		
		return admins;
	}
	
	/**
     * Returns a list of all users in the database
     * 
     */
	public List<String> getUsers() {
		List<String> userList = new ArrayList<>();
		for (User user: users) {
			userList.add(user.getUsername());
		}
		
		return userList;
	}
	
	/**
     * Returns a user based on the specified username
     * 
     * @param username - the username for the user to find
     */
	public User getUser(String username) {
		for (User user: users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	/**
     * Lists all users managed by the admin, displaying their username, full name,
     * and roles.
     */
    public boolean listUsersToConsole(User user) {
    	if (!user.isAdmin()) {
    		return false;
    	}
        System.out.println("Listing users:");
        // Loop through each user in the users list and display their details
        for (User usr : users) {
            System.out.println(usr.getUsername() + " - " + usr.getFirstName() + " " + usr.getLastName() + " Roles: " + usr.getRoles());
        }
        
        return true;
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
     * @param username           - The new user's username
     * @param role               - The role assigned to the new user (e.g., Student, Instructor)
     * @param firstName          - The new user's first name
     * @param lastName           - The new user's last name
     * @param email              - The new user's email address
     * @param preferredFirstName - The new user's preferred first name
     */
    public boolean inviteUser(String username, List<String> roles, String firstName, String lastName, String email, String preferredFirstName, User user) {
        if (!user.isAdmin()) { return false; }
    	
    	// Generate an invitation code for the new user
        String invitationCode = generateInvitationCode();

        // Create a new User object with the provided username and invitation code
        User invitedUser;
        invitedUser = new User(username, invitationCode);
        if (roles.contains("admin")) {
        	invitedUser.addRole("admin");
        }
        if (roles.contains("instructor")) {
        	invitedUser.addRole("instructor");
        }
        if (roles.contains("student")) {
        	invitedUser.addRole("student");
        }

        // Set the account details for the new user
        invitedUser.setAccountDetails(email, firstName, null, lastName, preferredFirstName);

        // Assign the specified roles to the invited user
        for (String role: roles) {
        	invitedUser.addRole(role);
        }

        // Add the invited user to the list of users managed by the admin
        users.add(invitedUser);
        
        System.out.println("Invited " + username + " with password " + invitationCode);
        
        return true;
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
    public boolean resetUserAccount(User userToReset, User user) {
    	if (!user.isAdmin()) { return false; }
    	
        // Generate a new one-time password
        String oneTimePassword = generateOneTimePassword();

        // Set the expiry time for the one-time password to 1 hour from now
        long expiry = System.currentTimeMillis() + 3600000; // 1 hour in milliseconds

        // Set the user's one-time password and its expiry
        user.setOneTimePassword(oneTimePassword, expiry);
        
        return true;
    }
    
    
    /**
     * Deletes a user by their username. This is a static method that calls the
     * User class's static deleteUser method.
     *
     * @param username - The username of the user to be deleted
     * @return True if the user was successfully deleted, false if the user was not found
     */
    public boolean deleteUser(String articleName, User user) {
    	for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(articleName)) {
            	users.remove(i);
                return true;
            }
        }
        return false; // User not found
    }
}