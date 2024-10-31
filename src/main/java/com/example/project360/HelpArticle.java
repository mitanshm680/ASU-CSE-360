package com.example.project360;

import java.util.List;

public class HelpArticle {
    private long id;
    private String title;
    private String shortDescription;
    private String body;
    private List<String> keywords;
    private List<String> references;
    private String level;
    private List<String> groups;
    private String sensitiveDescription;

    public HelpArticle(long id, String title, String shortDescription, String body,
                       List<String> keywords, List<String> references,
                       String level, List<String> groups, String sensitiveDescription) {
        this.id = id; // Set the ID directly
        this.title = title;
        this.shortDescription = shortDescription;
        this.body = body;
        this.keywords = keywords;
        this.references = references;
        this.level = level;
        this.groups = groups;
        this.sensitiveDescription = sensitiveDescription;
    }

    // Getters and setters...

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }

    public List<String> getReferences() { return references; }
    public void setReferences(List<String> references) { this.references = references; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public List<String> getGroups() { return groups; }
    public void setGroups(List<String> groups) { this.groups = groups; }

    public String getSensitiveDescription() { return sensitiveDescription; }
    public void setSensitiveDescription(String sensitiveDescription) { this.sensitiveDescription = sensitiveDescription; }
}
