package com.example.project360;

import java.util.ArrayList;
import java.util.List;

public class HelpArticle {
    private long id;
    private String title;
    private List<String> authors;
    private String authorsString;
    private String shortDescription;
    private String body;
    private List<String> keywords = new ArrayList<>();
    private List<String> references;
    private String level;
    private ArrayList<String> groups;
    private String sensitiveDescription;
    
    private boolean encrypted;
    private ArticleDatabase articleDatabase;

    public HelpArticle(long id, String title, List<String> authors, String shortDescription, String body,
                       List<String> keywords, List<String> references,
                       String level, List<String> groups, String sensitiveDescription,
                       ArticleDatabase articleDatabase, boolean encrypted, User user) {
        this.id = id; // Set the ID directly
        this.title = title;
        this.authors = authors;
        this.authorsString = "";
        this.shortDescription = shortDescription;
        this.keywords = keywords;
        this.references = references;
        this.level = level;
        ArrayList<String> copyGroupList = new ArrayList<>();
        copyGroupList.addAll(groups);
        this.groups = copyGroupList;
        
        // creates a formatted string of all authors for easy access
        for (String author: authors) {
        	if (authorsString.length() != 0) {
        		authorsString +=  ", ";
        	}
        	authorsString += author;
        }
        
        // encrypts the body and description if this is an encrypted article
        if (encrypted && user != null) {
        	this.body =
        		articleDatabase.getSpecialGroup(groups.get(0)).encryptData(body, user);
        	this.sensitiveDescription = 
        		articleDatabase.getSpecialGroup(groups.get(0)).encryptData(sensitiveDescription, user);
        	this.shortDescription = "";
        } else {
        	this.body = body;
        	this.sensitiveDescription = "";
        	this.shortDescription = shortDescription;
        }
        
        
        this.articleDatabase = articleDatabase;
        this.encrypted = encrypted;
    }

    // Getters and setters...
    // setters require permissions
   
    public long getId() { return id; }
    public boolean setId(long id, User user) {
    	if (articleDatabase.hasEditAccess(user, this)) { setId(id); return true; }
    	return false;
    }
    private void setId(long id) { this.id = id; }

    
    public String getTitle() { return title; }
    public boolean setTitle(String title, User user) {
    	if (articleDatabase.hasEditAccess(user, this)) { setTitle(title); return true; }
    	return false;
    }
    private void setTitle(String title) { this.title = title; }
    
    
    public List<String> getAuthors() { return authors; }
    public boolean setAuthors(List<String> authors, User user) {
    	if (articleDatabase.hasEditAccess(user, this)) { setAuthors(authors); return true; }
    	return false;
    }
    private void setAuthors(List<String> authors) { 
    	this.authors = authors; 

    	for (String author: authors) {
        	if (authorsString.length() != 0) {
        		authorsString +=  ", ";
        	}
        	authorsString += author;
        }
    }
    public String getAuthorsString() { return authorsString; }

    
    public String getShortDescription() { return shortDescription; }
    public boolean setShortDescription(String shortDescription, User user) {
    	if (articleDatabase.hasEditAccess(user, this)) { setShortDescription(shortDescription); return true; }
    	return false;
    }
    private void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getBody(User user) {
    	if (encrypted) {
    		if (user == null) { return body; }
    		return articleDatabase.getSpecialGroup(groups.get(0)).decryptData(body, user);
    	}
    	
    	return body;
    }
    public String getBody() {
    	return getBody(null);
    }
    public boolean setBody(String body, User user) {
    	if (articleDatabase.hasEditAccess(user, this)) { setBody(body); return true; }
    	return false;
    }
    private void setBody(String body) { this.body = body; }

    
    public List<String> getKeywords() { return keywords; }
    public String getKeywordsString() {
    	String keywordString = "";
    	for (String keyword: keywords) {
    		if (keywordString.length() != 0) {
    			keywordString += ", ";
    		}
    		keywordString += keyword;
    	}
    	
    	return keywordString;
    }
    public boolean setKeywords(List<String> keywords, User user) {
    	if (articleDatabase.hasEditAccess(user, this)) { setKeywords(keywords); return true; }
    	return false;
    }
    private void setKeywords(List<String> keywords) { this.keywords = keywords; }

    
    public List<String> getReferences() { return references; }
    public boolean setReferences(List<String> references, User user) {
    	if (articleDatabase.hasEditAccess(user, this)) { setReferences(references); return true; }
    	return false;
    }
    private void setReferences(List<String> references) { this.references = references; }

    
    public String getLevel() { return level; }
    public boolean setLevel(String level, User user) {
    	if (articleDatabase.hasEditAccess(user, this)) { setLevel(level); return true; }
    	return false;
    }
    private void setLevel(String level) { this.level = level; }

    
    public ArrayList<String> getGroups() { return groups; }
    public String getSpecialGroup() {
    	if (groups.size() > 0) {
    		return groups.get(0);
    	}
    	return "";
    }
    //public void addGroup(String group) { this.groups.add(group); }
    public boolean setGroups(ArrayList<String> groups, User user) {
    	if (articleDatabase.hasAdminAccess(user, this) || articleDatabase.hasEditAccess(user, this)) { setGroups(groups); return true; }
    	return false;
    }
    private void setGroups(ArrayList<String> groups) { this.groups = groups; }

    public String getSensitiveDescription() {
    	return getSensitiveDescription(null);
    }
    public String getSensitiveDescription(User user) {
    	if (encrypted && user != null) {
    		return articleDatabase.getSpecialGroup(groups.get(0)).decryptData(sensitiveDescription, user);
    	}
    	
    	return sensitiveDescription;
    }
    public boolean setSensitiveDescription(String sensitiveDescription, User user) {
    	if (articleDatabase.hasEditAccess(user, this)) { setSensitiveDescription(sensitiveDescription); }
    	return false;
    }
    private void setSensitiveDescription(String sensitiveDescription) { this.sensitiveDescription = sensitiveDescription; }
    
    public boolean isEncrypted() { return encrypted; }
    
    
    
    // Returns all of the available article levels for easy access
    public static List<String> getAvailableLevels() {
    	List<String> levelList = new ArrayList<String>();
    	levelList.add("Beginner");
    	levelList.add("Intermediate");
    	levelList.add("Advanced");
    	levelList.add("Expert");
    	return levelList;
    }
}
