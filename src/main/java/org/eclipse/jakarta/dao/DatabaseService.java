package org.eclipse.jakarta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.eclipse.jakarta.model.User;

public class DatabaseService {
	
    private static final String JNDI_NAME = "jdbc/jakarta"; 
    
    private static DatabaseService instance;
    private DataSource dataSource;

    // Private constructor to prevent instantiation
    private DatabaseService() throws Exception {
        dataSource = getDataSource();
    }

    // Singleton getInstance method
    public static synchronized DatabaseService getInstance() throws Exception {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    // Method to get DataSource from JNDI
    private DataSource getDataSource() throws NamingException {
        InitialContext context = new InitialContext();
        return (DataSource) context.lookup(JNDI_NAME);
    }

    // Method to get a database connection from the DataSource
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Method to retrieve all users from the database
    public List<User> getUsers() throws Exception {
        List<User> users = new ArrayList<>();
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        try {
            // Get connection from the pool
            myConn = getConnection();

            // Define the SQL query to fetch all users
            String sql = "SELECT * FROM User ORDER BY surname";

            // Create a statement and execute the query
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(sql);

            // Process the result set and populate the users list
            while (myRs.next()) {
                int id = myRs.getInt("id");
                String name = myRs.getString("name");
                String surname = myRs.getString("surname");
                String email = myRs.getString("email");
                String major = myRs.getString("major");
                String phone = myRs.getString("phone");
                int age = myRs.getInt("age");

                // Create a new User object and add it to the list
                User tempUser = new User(id, name, surname, email, major, phone, age);
                users.add(tempUser);
            }

            return users;
        } finally {
            // Close resources to avoid leaks
            close(myConn, myStmt, myRs);
        }
    }
    
    public void addUser(User theUser) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            // SQL query to insert a new user into the database
            String sql = "INSERT INTO user (name, surname, email, major, phone, age) VALUES (?, ?, ?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // Set parameters
            myStmt.setString(1, theUser.getName());
            myStmt.setString(2, theUser.getSurname());
            myStmt.setString(3, theUser.getEmail());
            myStmt.setString(4, theUser.getMajor());
            myStmt.setString(5, theUser.getPhone());
            myStmt.setInt(6, theUser.getAge());

            myStmt.execute();			
        } finally {
            close(myConn, myStmt);
        }
    }

    
    
 // Method to retrieve a specific user by ID
    public User getUser(int userId) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            // SQL query for retrieving user by ID
            String sql = "SELECT * FROM user WHERE id=?";

            myStmt = myConn.prepareStatement(sql);

            // Set parameters
            myStmt.setInt(1, userId);

            myRs = myStmt.executeQuery();

            User theUser = null;

            // Retrieve data from the result set
            if (myRs.next()) {
                int id = myRs.getInt("id");
                String name = myRs.getString("name");
                String surname = myRs.getString("surname");
                String email = myRs.getString("email");
                String major = myRs.getString("major");
                String phone = myRs.getString("phone");
                int age = myRs.getInt("age");

                theUser = new User(id, name, surname, email, major, phone, age);
            } else {
                throw new Exception("Could not find user with ID: " + userId);
            }

            return theUser;
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    // Method to update user information in the database
    public void updateUser(User theUser) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            // SQL query to update user data
            String sql = "UPDATE user SET name=?, surname=?, email=?, major=?, phone=?, age=? WHERE id=?";

            myStmt = myConn.prepareStatement(sql);

            // Set parameters
            myStmt.setString(1, theUser.getName());
            myStmt.setString(2, theUser.getSurname());
            myStmt.setString(3, theUser.getEmail());
            myStmt.setString(4, theUser.getMajor());
            myStmt.setString(5, theUser.getPhone());
            myStmt.setInt(6, theUser.getAge());
            myStmt.setInt(7, theUser.getId());

            myStmt.execute();
        } finally {
            close(myConn, myStmt);
        }
    }

    // Method to delete a user by ID
    public void deleteUser(int userId) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            // SQL query to delete user by ID
            String sql = "DELETE FROM user WHERE id=?";

            myStmt = myConn.prepareStatement(sql);

            // Set parameters
            myStmt.setInt(1, userId);

            myStmt.execute();
        } finally {
            close(myConn, myStmt);
        }
    }
    
    public List<User> searchUsers(String theSearchName)  throws Exception {

		List<User> users = new ArrayList<>();
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int studentId;
		
		try {
			
			// get connection to database
			myConn = dataSource.getConnection();
			
	        //
	        // only search by name if theSearchName is not empty
	        //
			if (theSearchName != null && theSearchName.trim().length() > 0) {

				// create sql to search for students by name
				String sql = "SELECT * FROM user WHERE LOWER(name) LIKE ? or LOWER(surname) LIKE ?";

				// create prepared statement
				myStmt = myConn.prepareStatement(sql);

				// set params
				String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
				myStmt.setString(1, theSearchNameLike);
				myStmt.setString(2, theSearchNameLike);
				
			} else {
				// create sql to get all students
				String sql = "SELECT * FROM user ORDER BY surname";

				// create prepared statement
				myStmt = myConn.prepareStatement(sql);
			}
	        
			// execute statement
			myRs = myStmt.executeQuery();
			
			// retrieve data from result set row
			while (myRs.next()) {
				
				// retrieve data from result set row
                int id = myRs.getInt("id");
                String name = myRs.getString("name");
                String surname = myRs.getString("surname");
                String email = myRs.getString("email");
                String major = myRs.getString("major");
                String phone = myRs.getString("phone");
                int age = myRs.getInt("age");

                User tempUser = new User(id, name, surname, email, major, phone, age);
				
				// add it to the list of students
				users.add(tempUser);			
			}
			
			return users;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
    private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}


    // Method to close database resources
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try {
            if (myRs != null) {
                myRs.close();
            }
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close();
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

}

