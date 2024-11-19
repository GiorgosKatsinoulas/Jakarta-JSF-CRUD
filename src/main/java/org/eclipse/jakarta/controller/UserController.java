package org.eclipse.jakarta.controller;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jakarta.model.User;
import org.eclipse.jakarta.dao.DatabaseService;

@Named("userController") // Use @Named instead of @ManagedBean and specify the bean name
@SessionScoped
public class UserController implements Serializable { // Implement Serializable

    private static final long serialVersionUID = 1L; // Add a serialVersionUID
    private List<User> users;  // List to hold users
    private DatabaseService userDatabaseService;  // Service to interact with database
    private Logger logger = Logger.getLogger(getClass().getName());  // Logger for logging messages
 // Assuming you don't have a setter for the user, add one:
    private User user; // The user object in the session
	private String theSearchName;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Clear the user object (reset all fields)
    public void clearUser() {
        this.user = new User();  // Create a new instance so the form is empty
    }
    // Constructor
    public UserController() throws Exception {
        users = new ArrayList<>();
        userDatabaseService = DatabaseService.getInstance();  // Get the instance of DatabaseService
        clearUser();// Initialize the user object to prevent 'null' errors in JSF
    }

    // Getter for users list
    public List<User> getUsers() {
        return users;
    }
   
  
    // Method to load users from the database
    public void loadUsers() {
        logger.info("Loading users from the database...");

    	logger.info("theSearchName = " + theSearchName);
        // Clear current users list before loading new data
        users.clear();
 
        
        try {
            // Fetch users from the DatabaseService
            users = userDatabaseService.getUsers();
            logger.info("Users loaded successfully.");
        	
			if (theSearchName != null && theSearchName.trim().length() > 0) {
				// search for students by name
				users = userDatabaseService.searchUsers(theSearchName);				
			}
			else {
				// get all students from database
				users = userDatabaseService.getUsers();
			}
        } catch (Exception exc) {
            // Log the error at SEVERE level
            logger.log(Level.SEVERE, "Error loading users from the database", exc);
            // Show an error message to the user
            addErrorMessage(exc);
        }finally {
			// reset the search info
			theSearchName = null;
		}
    }
    

    
    public String addUser(User theUser) {
    	logger.info("Adding user: " + theUser);
    	
    	try {
    		userDatabaseService.addUser(theUser);
    		
    	} catch(Exception exc) {
    		// send this to server logs
    		logger.log(Level.SEVERE, "Error adding users", exc);
    					
    		// add error message for JSF page
    		addErrorMessage(exc);

    		return null;
    	}
    	return "listUser?faces-redirect=true";
    }
    
    public String loadUser(int userId) {
        logger.info("loading user: " + userId);
        
     
        try {
            // get user from the database
            User theUser = userDatabaseService.getUser(userId);
             
            this.setUser(theUser); // Assuming you have a `setUser` method or directly set the user object 
            logger.info("loading user new: " + theUser);
            
        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading user id:" + userId, exc);
            
            // add error message for JSF page
            addErrorMessage(exc);
            
            return null;
        }
        
        return "updateUser.xhtml";
    }

    public String updateUser(User theUser) {
        logger.info("updating user: " + theUser);
        
        
        try {
            // update user in the database
        	userDatabaseService.updateUser(theUser);
            
        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error updating user: " + theUser, exc);
            
            // add error message for JSF page
            addErrorMessage(exc);
            
            return null;
        }
        
        return "listUser?faces-redirect=true";        
    }

    public String deleteUser(int userId) {
        logger.info("Deleting user id: " + userId);
        
        try {
            // delete the user from the database
        	userDatabaseService.deleteUser(userId);
            
        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error deleting user id: " + userId, exc);
            
            // add error message for JSF page
            addErrorMessage(exc);
            
            return null;
        }
        
        return "listUser";    
    }


	public String getTheSearchName() {
		return theSearchName;
	}

	public void setTheSearchName(String theSearchName) {
		this.theSearchName = theSearchName;
	}

    // Method to display error message on the JSF page
    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}

