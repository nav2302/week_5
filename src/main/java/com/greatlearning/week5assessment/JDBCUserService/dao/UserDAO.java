package com.greatlearning.week5assessment.JDBCUserService.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.greatlearning.week5assessment.JDBCUserService.pojo.User;

public class UserDAO {

	public void getAllUsers(Statement stmt) throws SQLException {
		String selectSqlQuery = "select firstName, lastName, email from user";
		ResultSet rs = stmt.executeQuery(selectSqlQuery);
		while (rs.next()) {
			System.out.print("First Name: " + rs.getString("firstName"));
			System.out.print(", LastName: " + rs.getString("lastName"));
			System.out.println(", Email: " + rs.getString("email"));
		}
		System.out.println();
		rs.close();
	}

	public void insertData(Connection conn, User user) throws SQLException {
		String insertSqlQuery = "INSERT INTO user (" + " firstName," + " lastName," + " email ) VALUES (" + "?, ?, ?)";

		// set all the preparedstatement parameters
		PreparedStatement st = conn.prepareStatement(insertSqlQuery);
		st.setString(1, user.getFirstName());
		st.setString(2, user.getLastName());
		st.setString(3, user.getEmail());

		// execute the preparedstatement insert
		st.executeUpdate();
		st.close();
	}

	public void updateData(Connection conn, String firstName, String lastName, String new_email, String old_email) {
		String sql = "UPDATE user SET firstName=?, lastName=?, email=? WHERE email=?";
		try (PreparedStatement statement = conn.prepareStatement(sql);) {
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, new_email);
			statement.setString(4, old_email);

			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("An existing user was updated successfully!");
			}
			else
				System.out.println("UPDATE FAILED:: User with given email does not exists");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void deleteUser(Connection conn, String email) {
		String sql = "DELETE FROM user WHERE email=?";
		 
		try (PreparedStatement statement = conn.prepareStatement(sql);){
			
			statement.setString(1, email);
			 
			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
			    System.out.println("A user was deleted successfully!");
			}
			else
				System.out.println("DELETE FAILED:: User with given email does not exist");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
