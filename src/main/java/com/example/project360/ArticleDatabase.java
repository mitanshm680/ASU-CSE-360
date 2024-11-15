package com.example.project360;

/*import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;*/
import java.util.ArrayList;
import java.util.List;

public class ArticleDatabase {
	private List<HelpArticle> helpArticles; // List of help articles managed by the admin
	
	private List<ArticleGroup> helpArticleGroups;
	
	public ArticleDatabase() {
		this.helpArticles = new ArrayList<>();
		this.helpArticleGroups = new ArrayList<>();
	}
	
	/**
     * Adds a help article to the admin's managed list.
     *
     * @param article - The HelpArticle to be added
     * @param user - User to check permissions
     */
    public boolean addArticle(HelpArticle article, User user) {
    	for (HelpArticle existingArticle: helpArticles) {
    		if (existingArticle.getTitle().equals(article.getTitle())) {
    			return false;
    		}
    	}
        helpArticles.add(article);
        updateArticleGroups(article.getGroups(), article, user);
        
        return true;
    }
    /**
     * Returns a help article based on the inputed name
     *
     * @param articleName - The HelpArticle to be found
     */
    public HelpArticle getArticle(String articleName) {
    	for (HelpArticle article: helpArticles) {
    		if (article.getTitle().equals(articleName)) {
    			return article;
    		}
    	}
    		
    	return null;
    }
    
    /**
     * Updates the article lists of the groups that an article is being added and removed to
     *
     * @param article - The HelpArticle to be added
     * @param groups - the new group list of the article
     * @param user - user to check permission
     */
    public void updateArticleGroups(List<String> groups, HelpArticle article, User user) {    	
    	List<String> originalGroups = article.getGroups();
    	for (String group: originalGroups) {
        	if (!groups.contains(group)) {
        		getGroup(group).removeArticle(article.getTitle(), user);
        	}
        }
    	
    	for (String group: groups) {
    		ArticleGroup foundGroup = getGroup(group);
        	if (foundGroup == null) {
        		foundGroup = createGroup(group, user);
        	}
        	foundGroup.addArticle(article.getTitle(), user);
        }
    	ArrayList<String> returnGroups = new ArrayList<String>();
    	returnGroups.addAll(groups);
    	article.setGroups(returnGroups, user);
    }
    
    /**
     * Creates a group with the specified name
     *
     * @param groupName - name of the group to be created
     * @param user - user to check permission
     */
    public ArticleGroup createGroup(String groupName, User user) {
    	if (!user.isAdmin() && !user.isInstructor()) { return null; }
    	ArticleGroup newGroup = new ArticleGroup(groupName, user);
    	
    	helpArticleGroups.add(newGroup);
    	
    	return newGroup;
    }
    
    /**
     * Creates a special group with the specified name
     *
     * @param groupName - name of the special group to be created
     * @param user - user to check permission
     */
    public SpecialAccessGroup createSpecialGroup(String groupName, User user) {
    	if (!user.isAdmin() && !user.isInstructor()) { return null; }
    	SpecialAccessGroup newGroup = new SpecialAccessGroup(groupName, user);
    	
    	helpArticleGroups.add(newGroup);
    	
    	return newGroup;
    }
    
    /**
     * Returns a group from the specified name
     * Returns null otherwise
     *
     * @param groupName - name of the special group to be created
     */
    public ArticleGroup getGroup(String groupName) {
    	for (ArticleGroup group: helpArticleGroups) {
    		if (group.getName().equals(groupName)) {
    			return group;
    		}
    	}
    	
    	return null;
    }
    /**
     * Returns a special group from the specified name
     * Returns null otherwise
     *
     * @param groupName - name of the special group to be created
     */
    public SpecialAccessGroup getSpecialGroup(String groupName) {
    	ArticleGroup foundGroup = getGroup(groupName);
    	if (foundGroup.isSpecialAccess()) {
    		return (SpecialAccessGroup) foundGroup;
    	}
    	
    	return null;
    }
    
    /**
     * Deletes a help article by its ID.
     *
     * @param articleId - The ID of the article to delete
     * @param user - user to check for permission
     * @return true if the article was found and deleted, false otherwise
     */
    public boolean deleteArticle(long articleId, User user) {
    	for (int i = 0; i < helpArticles.size(); i++) {
            if (helpArticles.get(i).getId() == articleId) {
            	helpArticles.remove(i);
                return true;
            }
        }
        return false; // Article not found
    }
    /**
     * Deletes a help article by its name
     *
     * @param articleName - The name of the article to delete
     * @param user - user to check for permission
     * @return true if the article was found and deleted, false otherwise
     */
    public boolean deleteArticle(String articleName, User user) {
    	for (int i = 0; i < helpArticles.size(); i++) {
            if (helpArticles.get(i).getTitle().equals(articleName)) {
            	helpArticles.remove(i);
                return true;
            }
        }
        return false; // Article not found
    }
    
    /**
     * Lists all article IDs, titles, and bodies to the console
     *
     */
    public List<HelpArticle> listArticlesToConsole() {
        System.out.println("Listing help articles:");
        for (HelpArticle article : helpArticles) {
            System.out.println("ID: " + article.getId() + " - Title: " + article.getTitle());
            System.out.println("      Body: " + article.getBody());
        }
        return new ArrayList<>(helpArticles); // Return a copy of the list
    }
    
    /**
     * Returns a list of all articles
     */
    public List<HelpArticle> getArticles() {
    	return new ArrayList<>(helpArticles); // Return a copy of the list
    }
    
    
    /**
     * Returns a list of names of all groups, except special ones where the user doesn't have access
     * 
     * @param user - user to check for permission
     */
    public List<String> getArticleGroupNames(User user) {
    	List<String> allGroupNames = new ArrayList<>();
    	for (ArticleGroup group: helpArticleGroups) {
    		if (group.isSpecialAccess()) {
    			SpecialAccessGroup saGroup = (SpecialAccessGroup) group;
    			if (saGroup.hasEncryptedViewAccess(user)) {
    				allGroupNames.add(group.getName());
    			}
    		}
    		else { allGroupNames.add(group.getName()); }
    	}
    	
    	return allGroupNames;
    }
    
    
    /**
     * Returns if a user has edit permissions to a certain article
     * 
     * @param user - user to check for permission
     * @param article - the article to check for permission to
     */
    public boolean hasEditAccess(User user, HelpArticle article) {
    	List<String> articleGroups = article.getGroups();
    	
    	for (String group: articleGroups) {
    		ArticleGroup foundGroup = getGroup(group);
    		if (foundGroup.isSpecialAccess()) {
    			if (foundGroup.getAdminNames().contains(user.getUsername())) {
    				return true;
    			} else {
    				return false;
    			}
    		}
    	}
    	
    	if (user.isInstructor()) {
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * Returns if a user has admin permissions to a certain article
     * 
     * @param user - user to check for permission
     * @param article - the article to check for permission to
     */
    public boolean hasAdminAccess(User user, HelpArticle article) {
    	List<String> articleGroups = article.getGroups();
    	
    	for (String group: articleGroups) {
    		ArticleGroup foundGroup = getGroup(group);
    		
    		if (foundGroup.getAdminNames().contains(user.getUsername())) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    

    
    /*public void baclupArticles(String filename, User user) {
    	backupArticles(filename, "", user);
    }
    public void backupArticles(String filename, String groupname, User user) {
        try (Writer writer = new BufferedWriter(new FileWriter(filename))) {
            for (HelpArticle article : helpArticles) {
            	if (groupname.equals("") || article.getGroups().contains(groupname)) {
            		writer.write(article.getId() + "," + article.getTitle() + "," +
                            String.join(";", article.getAuthors()) + "," + article.getShortDescription() + "," +
            				article.getBody() + "," + String.join(";", article.getKeywords()) + "," +
                            String.join(";", article.getReferences()) + ","+ article.getLevel() + "," +
                            String.join(";", article.getGroups()) + "," + article.getSensitiveDescription(null)+ "," +
                            article.isEncrypted() + "\n");
            	}
            }
            writer.close();
            System.out.println("Backup successful.");
        } catch (IOException e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }

    public void restoreArticles(String filename, boolean removeExisting) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            if (removeExisting) {
                helpArticles.clear(); // Remove all existing articles
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1); // Split by comma

                // Ensure we have the correct number of parts
                if (parts.length < 9) {
                    System.out.println("Invalid line format: " + line);
                    continue; // Skip this line if it's invalid
                }

                long id = Long.parseLong(parts[0].trim());
                String title = parts[1].trim();
                List<String> authors = new ArrayList<>(Arrays.asList(parts[2].trim().split(";")));
                String shortDescription = parts[3].trim();
                String body = parts[4].trim();

                // Split keywords and references by semicolon
                List<String> keywords = new ArrayList<>(Arrays.asList(parts[5].trim().split(";")));
                List<String> references = new ArrayList<>(Arrays.asList(parts[6].trim().split(";")));
                String level = parts[7].trim();
                List<String> groups = new ArrayList<>(Arrays.asList(parts[8].trim().split(";")));
                String sensitiveDescription = parts[9].trim();
                Boolean encrypted;
                if (parts[10].trim().equals("true")) {
                	encrypted = true;
                } else {
                	encrypted = false;
                }
                
                

                HelpArticle backupArticle = new HelpArticle(id, title, authors, shortDescription, body,
                        keywords, references, level, groups, sensitiveDescription, this, encrypted, null);

                // Check if the ID already exists before adding
                if (!helpArticles.stream().anyMatch(article -> article.getId() == backupArticle.getId())) {
                    helpArticles.add(backupArticle); // Add only if the ID is unique
                }
            }
            System.out.println("Restore successful.");
        } catch (IOException e) {
            System.out.println("Restore failed: " + e.getMessage());
        }

    }*/
    
}