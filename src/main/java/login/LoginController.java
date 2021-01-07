package login;

import entity.users.*;
import entity.interfaces.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/** 
 * This class is the logic controller for Login Interface on {@link StartMenu} class
 * <p>
 * The class implements {@link ConnectionManager} interface where the details like host, database name
 * is found which is required by the JDBC Connector.
*/
public class LoginController implements ConnectionManager {

    /**
     * Implements the Login Logic for StartMenu
     * <p>
     * Given username input from StartMenu, it will query the database for user. If user is not found,
     * it will prompt the user to key in the Login credentials again.
     * <p>
     * else, it will create a User Object and pass it as a argument into HomeMenu Constructor
     * 
     */
    public void login() {
        String dbUsername = "root";
        String dbPassword = "";

        String sql = "SELECT * FROM users WHERE username = ?";

        Boolean carryOn = true;
        Scanner sc = new Scanner(System.in);

        String fullnameInDb;
        String usernameInDb;
        String passInDb;

        while(carryOn == true){

            try{
                System.out.println("== Social Magnet :: Login ==");

                System.out.print("Enter your username > ");
                String givenUsername = sc.nextLine();

                System.out.print("Enter your password > ");
                String givenPassword = sc.nextLine();

                // step 1: Loads the JDBC driver
                Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

                // step 2: Gets a connection to the database
                Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

                // step 3: Prepare the SQL to be sent to the database
                PreparedStatement stmt = conn.prepareStatement(sql);

                // bind the parameters
                stmt.setString(1, givenUsername);

                // step 4: executes the query
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    fullnameInDb = rs.getString("full_name");
                    usernameInDb = rs.getString("username");
                    passInDb = rs.getString("pwd");

                    if(usernameInDb.equals(givenUsername) && passInDb.equals(givenPassword)){
                        carryOn = false;
                        User user = new User(usernameInDb,fullnameInDb);
                        HomeMenu homeMenu = new HomeMenu(user);
                        homeMenu.start();
                        break;
                    }

                }
                System.out.println("Invalid username/password!");
                System.out.println();

            } catch(Exception e) {
                System.out.println("Invalid username/password!");
                e.printStackTrace();
            }
        }

    }

    /**
     * Register New Users based on credentials given.
     * The method will prompt users to reconfirm their password again before calling UserDAO to add
     * them into the Database
     */
    public void register(){
        // Remove the whole section of codes that dealt with direct access to SQL and moved it into UserDAO
        // Felt that it should not belong here but rather be a function call from userDAO instead
        // Following Single Responsiblity Principle - Louis <13 March 2020 20:33>

        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        System.out.println("== Social Magnet :: Registration ==");

        System.out.print("Enter your Full Name > ");
        String givenFullname = sc.nextLine();

        System.out.print("Enter your username > ");
        String givenUsername = sc.nextLine();

        System.out.print("Enter your password > ");
        String givenPassword = sc.nextLine();

        System.out.print("Confirm your password > ");
        String confirmPassword = sc.nextLine();

        while(!givenPassword.equals(confirmPassword)){
            System.out.println("Passwords dont match! Please try again.");
            System.out.print("Confirm your password > ");
            confirmPassword = sc.nextLine();
        }

        if (userDAO.add(givenUsername, givenPassword, givenFullname)) {
            System.out.println(givenUsername + ", your account is successfully created!");
            System.out.println();

        } else {
            System.out.println("Sorry, Something went wrong!");
        
        }
    }
}