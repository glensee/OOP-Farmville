package entity.interfaces;


/**
 * WebHeader Interface stores all the Strings to be printed and allows ease of editing what shows on the 
 * display for Menu
 */
public interface WebHeader {

    // For Login Page
    String loginOptions = "1. Register \r\n2. Login \r\n3. Exit";

    // For Welcome Menu
    String welcomeMenuHeader = "== Social Magnet :: Welcome ==";

    String welcomeMenuOptions = "1. News Feed \r\n2. My Wall \r\n3. My Friends \r\n4. City Farmer \r\n5. Logout";

    String selectChoice = "Enter your choice > ";

    // For my Wall
    String wallMenuHeader = "== Social Magnet :: My Wall ==";

    String wallMenuOptions = "[M]ain | [T]hread | [A]ccept Gift | [P]ost > ";

    // For NewsFeed
    String newsFeedWall = "== Social Magnet :: News Feed ==";

    String newsFeedOption = "[M]ain | [T]hread > ";

    // For Friends
    String friendsWallHeader = "== Social Magnet :: My Friends ==";

    String friendsWallChoice = "[M]ain | [U]nfriend | re[Q]uest | [A]ccept | [R]eject | [V]iew > ";
}