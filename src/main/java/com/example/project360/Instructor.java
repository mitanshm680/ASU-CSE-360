package com.example.project360;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends User {
    private List<HelpArticle> helpArticles; // List of help articles managed by the instructor

    /**
     * Constructor for Instructor class. Calls the superclass constructor (User)
     * and assigns the instructor role.
     *
     * @param username - Instructor's username
     * @param password - Instructor's password
     * @param helpArticles - List of help articles managed by the instructor
     */
    public Instructor(String username, String password, List<HelpArticle> helpArticles) {
        super(username, password);
        this.helpArticles = helpArticles != null ? helpArticles : new ArrayList<>(); // Initialize with empty list if null
        addRole("Instructor");
    }

    /**
     * Adds a help article to the instructor's managed list.
     *
     * @param article - The HelpArticle to be added
     */
    public void addArticle(HelpArticle article) {
        if (article != null) {
            helpArticles.add(article);
            System.out.println("Added article: " + article.getTitle());
        } else {
            System.out.println("Cannot add a null article.");
        }
    }

    /**
     * Deletes a help article by its ID.
     *
     * @param articleId - The ID of the article to delete
     * @return true if the article was found and deleted, false otherwise
     */
    public boolean deleteArticle(long articleId) {
        for (HelpArticle article : helpArticles) {
            if (article.getId() == articleId) {
                helpArticles.remove(article);
                System.out.println("Deleted article: " + article.getTitle());
                return true; // Article deleted
            }
        }
        System.out.println("Article with ID " + articleId + " not found.");
        return false; // Article not found
    }

    /**
     * Lists all help articles managed by the instructor.
     */
    public void listArticles() {
        if (helpArticles.isEmpty()) {
            System.out.println("No articles found.");
            return;
        }
        System.out.println("Listing help articles:");
        for (HelpArticle article : helpArticles) {
            System.out.printf("ID: %d - Title: %s%n", article.getId(), article.getTitle());
        }
    }
}
