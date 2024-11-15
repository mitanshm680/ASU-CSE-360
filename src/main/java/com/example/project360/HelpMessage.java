package com.example.project360;

// Class to hold basic information about help messages created by users
public class HelpMessage {
    	private String type;
    	private String email;
    	private String title;
    	private String body;
    	
    	public HelpMessage(String type, String email, String title, String body) {
    		this.type = type;
    		this.email = email;
    		this.title = title;
    		this.body = body;
    	}
    	
    	public String getType() { return type; }
    	public void setType(String type) { this.type = type; }
    	public String getEmail() { return email; }
    	public void setEmail(String email) { this.email = email; }
    	public String getTitle() { return title; }
    	public void setTitle(String title) { this.title = title; }
    	public String getBody() { return body; }
    	public void setBody(String body) { this.body = body; }
}