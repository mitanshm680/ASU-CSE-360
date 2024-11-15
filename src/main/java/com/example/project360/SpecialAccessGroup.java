package com.example.project360;

import java.util.ArrayList;
import java.util.List;

public class SpecialAccessGroup extends ArticleGroup {
	private List<String> viewers;
	
	
	public SpecialAccessGroup(String groupName, User admin) {
		super(groupName, admin);
		viewers = new ArrayList<>();
	}
	
	/**
     * Returns the encrypted version of the inputed decrypted data
     * 
     * @param decryptedData - the data that is to be encrypted
     * @param user - user to check for permission
     */
	public String encryptData(String decryptedData, User user) {
		if (decryptedData == null || user == null) { return ""; }
		if (!hasEncryptedViewAccess(user)) { return ""; }
		
		String encrypted = "";
		for (int i = 0; i < decryptedData.length(); i++) {
			encrypted += (char) (((int)decryptedData.charAt(i)) + 1);
		}
		return encrypted;
	}
	
	/**
     * Returns the decrypted version of the inputed encrypted data
     * 
     * @param encryptedData - the data that is to be decrypted
     * @param user - user to check for permission
     */
	public String decryptData(String encryptedData, User user) {
		if (encryptedData == null || user == null) { return "Decrypted Access Denied2"; }
		if (!hasEncryptedViewAccess(user)) { return "Decrypted Access Denied"; }
		
		String decrypted = "";
		for (int i = 0; i < encryptedData.length(); i++) {
			decrypted += (char) (((int)encryptedData.charAt(i)) - 1);
		}
		return decrypted;
	}
	
	/**
     * Give a user viewing permissions of this group
     * 
     * @param userName - name of user to give permission to
     * @param user - user to check for permission
     */
	public boolean addViewer(String userName, User user) {
		if (!hasAdminGroupAccess(user)) { return false; }
		viewers.add(userName);
		
		return true;
	}
	
	/**
     * Remove a users viewing permissions of this group
     * 
     * @param userName - name of user to take permission from
     * @param user - user to check for permission
     */
	public boolean removeViewer(String userName, User user) {
		if (!hasAdminGroupAccess(user)) { return false; }
		viewers.remove(userName);
		
		return true;
	}
	
	/**
     * Returns a list of all with viewing permissions of this group
     * 
     */
	public List<String> getViewerNames() {
		return List.copyOf(viewers);
	}
	
	/**
     * Checks if a user has viewing access to this group from viewing permissions or admin access
     * 
     * @param user - user to check the permissions of
     */
	public boolean hasEncryptedViewAccess(User user) {
		if (getAdminNames().contains(user.getUsername())) { return true; }
		else if (viewers.contains(user.getUsername())) { return true; }
		
		return false;
	}
	
	
	public boolean isSpecialAccess() { return true; }
}