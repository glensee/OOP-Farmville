package login;

import java.util.*;
import entity.interfaces.WebHeader;


/** 
 * This class represents menu interface with menu interactions for the Users to input to.
*/
public class StartMenu implements WebHeader {
    private LoginController loginCtrl;

    /** 
         * Constructor for StartMenu
         * <p> 
         * Initialise the StartMenu with new LoginController Object as Attribute 
    */
    public StartMenu(){
        this.loginCtrl = new LoginController();
    }

    /** 
     * Displays and Prints out the Login Page of Social Magnet. 
     * <p>
     * The display is robust, showing a greeting suited to the time of the system the 
     * application is started on.
    */ 
    public void display() {
   
        System.out.println(welcomeMenuHeader);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            System.out.println("Good morning, anonymous!");

        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            System.out.println("Good afternoon, anonymous!");

        } else {
            System.out.println("Good evening, anonymous!");
        }

        System.out.println(loginOptions);
        System.out.print(selectChoice);
    }


    /** 
     * Starts the Application Menu and calls the {@link display()} to display Login Menu
    */   
    public void start() {
        Scanner sc = new Scanner(System.in);
        int choice;

        try {
            display();
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    register();
                    break;

                case 2:
                    login();
                    break;

                case 3:
                    System.out.println("Goodbye");
                    break;

                default:
                    System.out.println("Enter a choice between 1 & 3!");
                    start();
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid choice!");
            start();
        }
    }

    /**
     * Executes Login logic with given input from the interface
     */
    public void login() {
        loginCtrl.login();

    }

    /**
     * Executes Register logic with given input from the interface
     */
    public void register() {
        loginCtrl.register();
        start();
    }

}