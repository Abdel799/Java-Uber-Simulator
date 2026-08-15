import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TMUberRegistered
{
    // NAME: Abdelrahman Abdelaal
    //ID: 501 227 765
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    // The test scripts and test outputs included with the skeleton code use these
    // users and drivers below. You may want to work with these to test your code (i.e. check your output with the
    // sample output provided). 
    public static ArrayList<User> loadPreregisteredUsers(String name1) throws FileNotFoundException
    {
        

        ArrayList <User> users = new ArrayList<User>();         // creates array list to hold users

        
        File in = new File(name1);              // finds file

        Scanner s = new Scanner(in);            // creates scanner for file

        while (s.hasNextLine())                 // while scanner has next
        {
            String name = s.nextLine();             // gets name
            String address = s.nextLine();          // gets address
            double wallet = s.nextDouble();         // gets wallet
            
            if (s.hasNextLine()){s.nextLine();}

            String id = generateUserAccountId(users);                   // generates ID
            User user1 = new User(id, name, address, wallet);           // creates user
            users.add(user1);           // adds user to array list
        }
            

        return users;

    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    public static ArrayList<Driver> loadPreregisteredDrivers(String name1) throws FileNotFoundException
    {
        
        ArrayList <Driver> drivers = new ArrayList<Driver>();           // creates array list of drivers

        
        File in = new File(name1);              // finds file

        Scanner s = new Scanner(in);            // creates scanner for file

        while (s.hasNextLine())             // while scanner has next
        {
            String name = s.nextLine();         // gets name
            String model = s.nextLine();        // gets model
            String plate = s.nextLine();        // gets plate
            String address = s.nextLine();      // gets address

            String id = generateDriverId(drivers);                                      // generates ID
            Driver drive1 = new Driver(id, name, model, plate, address);                // creates driver
            drivers.add(drive1);            // adds driver to array list
        }
            

        return drivers;

    }
}

