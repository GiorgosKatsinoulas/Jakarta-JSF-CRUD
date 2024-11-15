# DEMO Jakarta JSF CRUD web app

## Overview  
This project is a **DEMO Jakarta JSF CRUD web app** developed using **Jakarta Faces (JSF)** and deployed on the **GlassFish server**. It integrates a **MySQL database** to perform CRUD operations and utilizes **PrimeFaces** for enhanced UI/UX design. Additionally, **jQuery** with **AJAX** is incorporated to explore its integration process.  

The project is designed as a learning exercise to build proficiency in Jakarta EE and related frameworks, aligning with organizational requirements. Future plans include expanding the functionality using **Spring Boot APIs**.  

## Features  
### Implemented Functionality  
1. **Create User**  
   - Add new users to the MySQL database through a form interface.  
2. **Delete User**  
   - Remove specific users from the database with confirmation prompts.  
3. **Update User**  
   - Modify existing user details with a form pre-populated with current data.  
4. **List Users**  
   - Display all users in a paginated table for easy navigation.  
5. **Search Users**  
   - Search for users dynamically using a search bar.  
6. **Exploring Interactivity with jQuery and AJAX**  
   - A simple test was implemented to understand the process of using AJAX.

### Upcoming Features  
- **Integration with Spring Boot API**  
  - Develop an API in Spring Boot to handle backend logic and data exchange.  
  - Utilize this API in the JSF application to retrieve data and perform necessary processing for display in the web application.  

## Technologies Used  
- **Jakarta EE 9** (JSF Framework)  
- **GlassFish Server**  
  - Connection pool and JDBC resource configured to connect to MySQL.  
- **MySQL Database**  
  - Users table with CRUD operations.  
- **PrimeFaces Framework**  
  - Responsive components and design elements for a modern user interface.  
- **jQuery with AJAX**  
  - A simple test for dynamic interactivity.  

## Prerequisites  
1. **Environment Setup**  
   - Install Java JDK 17 or higher.  
   - Install GlassFish Server 6.2 or higher.  
   - Install MySQL 8.0 or higher.  

2. **Database Configuration**  
   - Create a MySQL database and users table with the following schema:  
     ```sql
     USE jakartaee_crud;

     CREATE TABLE users (
         id INT AUTO_INCREMENT PRIMARY KEY,      -- Unique identifier, auto-incremented
         name VARCHAR(100) NOT NULL,             -- Name of the user
         surname VARCHAR(100) NOT NULL,          -- Surname of the user
         date_of_birth DATE NOT NULL,            -- Date of birth (YYYY-MM-DD format)
         major VARCHAR(100),                     -- Major (optional)
         email VARCHAR(255) NOT NULL UNIQUE,     -- Email, must be unique
         phone VARCHAR(20)                       -- Phone number (optional)
     );
     ```

3. **GlassFish Configuration**  
   - Create a connection pool and JDBC resource:  
     - Navigate to **GlassFish Admin Console**.  
     - Configure a new connection pool pointing to the MySQL database.  
     - Create a JDBC resource linked to the pool.  

4. **Project Setup**  
   - Clone the repository:  
     ```bash
     git clone https://github.com/GiorgosKatsinoulas/Jakarta-JSF-CRUD.git
     cd Jakarta-JSF-CRUD
     ```  
   - Deploy the application to the GlassFish server.  

## Installation and Execution  
1. **Setup the Database**  
   - Ensure the MySQL database is running with the required schema.  

2. **Deploy to GlassFish**  
   - Deploy the `.war` file or directly run the project from your IDE.  

3. **Access the Application**  
   - Open your browser and navigate to:  
     ```  
     http://localhost:8080/Jakarta-JSF-CRUD
     ```  

## Next Steps  
- Develop and deploy a Spring Boot API for data retrieval.  
- Connect the JSF application to the API and perform necessary processing to display the data effectively.  
- Further enhance UI with advanced PrimeFaces components.  
- Add user authentication features to restrict access.  

## Folder Structure  
src/main/ │ ├── webapp/
│ ├── index.xhtml # Landing page
│ ├── pages/ # Folder containing all additional pages
│ ├── dashboard.xhtml # User management dashboard
│ ├── addUser.xhtml # Form to add users
│ ├── listUser.xhtml # Paginated user list
│ └── resources/
│ ├── css/ # Custom styles
├── java/
│ ├── controller/ # Managed beans for business logic
│ ├── dao/ # Database access layer
│ ├── model/ # Entity classes
│ └── services/ # Spring Boot service layer (future)

## Purpose  
This project demonstrates my ability to rapidly acquire and implement new technologies to create practical and effective solutions. It was developed in response to a requirement from my company to adopt Jakarta EE for an upcoming project. Additionally, it reflects my dedication to mastering modern frameworks and tools, showcasing my adaptability and professionalism in a dynamic work environment.  

## Author  
**Georgios Katsinoulas**  
- Email: giorgos.gkats@gmail.com
- LinkedIn: https://www.linkedin.com/in/giorgos-gkats/
- GitHub: https://github.com/GiorgosKatsinoulas
