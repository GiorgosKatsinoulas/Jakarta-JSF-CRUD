package org.eclipse.jakarta.dao; 

import java.sql.*;

class MysqlCon 

{
//Testing db connection
	public static void main(String[] args) 

	{

		try{

			

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JakartaCRUD","root","password");

			Statement st = con.createStatement();

			String query = "select * from User";

			ResultSet rs = st.executeQuery(query);

			while(rs.next()){

				System.out.println(rs.getString(1)+" "+rs.getString(2));

			}

			con.close();

		}catch(SQLException e){

			System.out.println("Error");

		}catch(Exception e){



		}

	}

}