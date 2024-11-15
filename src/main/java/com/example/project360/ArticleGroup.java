package com.example.project360;

import java.util.ArrayList;
import java.util.List;

/*public class SpecialAccessGroup extends ArticleGroup {
	
	
	public SpecialAccessGroup(String groupName) {
		super(groupName);
	}
}*/

public class ArticleGroup{
	private String groupName;
	
	private List<String> helpArticles;
	
	private List<String> groupAdmins;
	
	public ArticleGroup(String groupName, User admin) {
		this.groupName = groupName;
		helpArticles = new ArrayList<>();
		
		groupAdmins = new ArrayList<>();
		
		groupAdmins.add(admin.getUsername());
	}
	
	
	/**
     * checks if the specified user has admin permissions to this group
     * 
     * @param user - user to check the permissions of
     */
	public boolean hasAdminGroupAccess(User user) {
		if (groupAdmins.contains(user.getUsername())) { return true; }
		return false;
	}
	
	/**
     * adds specified article to the group
     * 
     * @param articleName - name of article to add
     * @param user - user to check for permission
     */
	public boolean addArticle(String articleName, User user) {
		if (!hasAdminGroupAccess(user)) { return false; }
		
		if (!helpArticles.contains(articleName)) {
			helpArticles.add(articleName);
		}
		
		return true;
    }
	
	/**
     * removes specified article from the group
     * 
     * @param articleName - name of article to remove
     * @param user - user to check for permission
     */
	public boolean removeArticle(String articleName, User user) {
		if (!hasAdminGroupAccess(user)) { return false; }
		
		for (int i = 0; i < helpArticles.size(); i++) {
			if (helpArticles.get(i).equals(articleName)) {
				helpArticles.remove(i);
				return true;
			}
		}
		
		return true;
	}
	
	/**
     * returns a list of all articles in the group
     * 
     */
	public List<String> getArticleNames() {
		return List.copyOf(helpArticles);
	}

	
	
	/**
     * returns the name of this group
     * 
     */
	public String getName() {
		return groupName;
	}
	
	/**
     * set the name for his group
     * 
     * @param groupName - name to set groupName to
     * @param user - user to check for permission
     */
	public boolean setName(String groupName, User user) {
		if (!hasAdminGroupAccess(user)) { return false; }
		
		this.groupName = groupName;
		
		return true;
	}
	
	
	
	/**
     * Give a user admin permissions of this group
     * 
     * @param adminName - name of user to give permission to
     * @param user - user to check for permission
     */
	public boolean addAdmin(String adminName, User user) {
		if (!hasAdminGroupAccess(user)) { return false; }
		if (!user.isAdmin() && !user.isInstructor()) { return false; }
		
		if (!groupAdmins.contains(adminName)) {
			groupAdmins.add(adminName);
		}
		
		return true;
	}
	public List<String> getAdminNames() {
		return List.copyOf(groupAdmins);
	}
	
	/**
     * Remove a users admin permissions of this group
     * 
     * @param adminName - name of user to remove permission from
     * @param user - user to check for permission
     */
	public boolean removeAdmin(String adminName, User user) {
		if (!hasAdminGroupAccess(user)) { return false; }
		
		if (groupAdmins.size() < 2) { return false; }
		//if (adminName.equals(user.getUsername())) { return false;}
		
		if (groupAdmins.contains(adminName)) {
			groupAdmins.remove(adminName);
		}
		
		return true;
	}
	
	

	
	public boolean isSpecialAccess() { return false; }
	
}