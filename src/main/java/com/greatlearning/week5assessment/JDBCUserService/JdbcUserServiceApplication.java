package com.greatlearning.week5assessment.JDBCUserService;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.greatlearning.week5assessment.JDBCUserService.dao.UserDAO;
import com.greatlearning.week5assessment.JDBCUserService.pojo.User;
import com.greatlearning.week5assessment.JDBCUserService.util.SmsApi;

/*
 * Script used to create the table is in "src/main/resources/sqlScript"
 * Database name used is "dbName" and table is "user"
 */

public class JdbcUserServiceApplication {

	public static void main(String[] args) {

		try (// Creating a connection
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbname", "root",
						"<Use_your_own_password>");
				// Access a statement
				Statement stmt = conn.createStatement();

				Scanner menu = new Scanner(System.in)) {

			int operationNumberToPerform;
			UserDAO userDao = new UserDAO();
			do {
				System.out.println("!!!!! Welcome to the User CRUD services !!!!!");
				System.out.println("1. Registration");
				System.out.println("2. Update");
				System.out.println("3. Display Data");
				System.out.println("4. Delete");
				System.out.println("5. Exit");

				operationNumberToPerform = menu.nextInt();
				menu.nextLine();
				switch (operationNumberToPerform) {

				case 1:
					System.out.println("Enter the registered mobile number ");
					String number = menu.nextLine();

					SecureRandom random = new SecureRandom();
					int num = random.nextInt(100000);
					String OTP = String.format("%04d", num);

					// Displaying the sent OTP in case the SMS API is down or any other issue
					System.out.println("Sent OPT is = " + OTP);

					// Sending SMS to mobile via FAST2SMS API
					SmsApi.sendSms(OTP, number);

					System.out.println("Enter the OTP received");
					String OTPreceived = menu.nextLine();

					if (OTPreceived.equalsIgnoreCase(OTP)) {
						System.out.println("OTP verification Successful!!! :-)");
						System.out.println("Enter first Name: ");
						String firstName = menu.nextLine();
						System.out.println("Enter Last Name: ");
						String lastName = menu.nextLine();
						System.out.println("Enter Email: ");
						String emailId = menu.nextLine();
						User user = new User(firstName, lastName, emailId);
						try {
							userDao.insertData(conn, user);
							System.out.println("ROW INSERTED SUCCESSFULLY " + user);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else
						System.out.println("Wrong OTP entered Try Again!!! :-(");
					break;

				case 2:
					System.out.println("Enter email id of the user you want to update");
					String email = menu.nextLine();
					System.out.println("Enter Updated first Name: ");
					String new_firstName = menu.nextLine();
					System.out.println("Enter Updated Last Name: ");
					String new_lastName = menu.nextLine();
					System.out.println("Enter Updated Email: ");
					String new_emailId = menu.nextLine();
					userDao.updateData(conn, new_firstName, new_lastName, new_emailId, email);
					break;

				case 3:
					try {
						userDao.getAllUsers(stmt);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					break;

				case 4:
					System.out.println("Enter email id of the user you want to DELETE");
					String emailToDelete = menu.nextLine();
					userDao.deleteUser(conn, emailToDelete);
					break;
				default:
					break;
				}
			} while (operationNumberToPerform != 5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
