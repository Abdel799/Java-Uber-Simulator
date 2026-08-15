import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
//import java.util.InvalidDriverNameException;

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

//NAME: Abdelrahman Abdelaal
//ID: 501 227 765

public class TMUberUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 

    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      String action = scanner.nextLine();

      if (action == null || action.equals("")) 
      {
        System.out.print("\n>");
        continue;
      }
      // Quit the App
      else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
        return;
      // Print all the registered drivers
      else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
      {
        tmuber.listAllDrivers(); 
      }
      // Print all the registered users
      else if (action.equalsIgnoreCase("USERS"))  // List all users
      {
        tmuber.listAllUsers(); 
      }
      // Print all current ride requests or delivery requests
      else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
      {
        tmuber.listAllServiceRequests(); 
      }
      // Register a new driver
      else if (action.equalsIgnoreCase("REGDRIVER")) 
      {
        String name = "";
        System.out.print("Name: ");           // gets name
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String carModel = "";
        System.out.print("Car Model: ");      // gets model
        if (scanner.hasNextLine())
        {
          carModel = scanner.nextLine();
        }
        String license = "";
        System.out.print("Car License: ");      // gets license
        if (scanner.hasNextLine())
        {
          license = scanner.nextLine();
        }

        String address = "";
        System.out.print("Address: ");        // gets address
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        
        try
        {
          tmuber.registerNewDriver(name, carModel, license, address);     // calls register driver method
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s", name, carModel, license);      // output
        }
          
        catch (InvalidDriverNameException e)        // if driver name invalid
        {
          System.out.println(e.getMessage());
        }
        catch(InvalidCarModelException e)           // if model invalid
        {
          System.out.println(e.getMessage());
        }
        catch(InvalidPlateException e)              // if plate invalid
        {
          System.out.println(e.getMessage());
        }
        catch(DriverExistsException e)              // if driver already exists
        {
          
          System.out.println(e.getMessage());
        }
        catch(InvalidAddressException e)            // if address invalid
        {
          System.out.println(e.getMessage());
        }
      }
      // Register a new user
      else if (action.equalsIgnoreCase("REGUSER")) 
      {
        String name = "";
        System.out.print("Name: ");           // gets name
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");        // gets address
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        double wallet = 0.0;
        System.out.print("Wallet: ");         // gets wallet
        if (scanner.hasNextDouble())
        {
          wallet = scanner.nextDouble();
          scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
        }
      

        try
        {
          tmuber.registerNewUser(name, address, wallet);      // calls method
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);     // output
        }
        catch(InvalidUserNameException e)           // if name invalid
        {
          System.out.println(e.getMessage());
        }
        catch(InvalidAddressException e)            // if address invalid
        {
          System.out.println(e.getMessage());
        }
        catch(InvalidMoneyAmountException e)        // if invalid money
        {
          System.out.println(e.getMessage());
        }
        catch(UserExistsException e)                // if user already exists
        {
          System.out.println(e.getMessage());
        }


      }
      // Request a ride
      else if (action.equalsIgnoreCase("REQRIDE")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestRide() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)
        Scanner myObj = new Scanner(System.in);

        String Id1 = "";
        System.out.print("User Account Id: ");    // gets ID
        if (myObj.hasNextLine())
        {
          Id1 = myObj.nextLine();
        }

        String from1 = "";
        System.out.println("From Address: ");     // gets from address
        if (myObj.hasNextLine())
        {
          from1 = myObj.nextLine();
        }

        String to1 = "";
        System.out.println("To Address: ");       // gets to address
        if (myObj.hasNextLine())
        {
          to1 = myObj.nextLine();
        }
        

       try
       {
          tmuber.requestRide(Id1, from1, to1);      // calls method
          User user1 = tmuber.getUser(Id1);         // gets user
          System.out.println("RIDE for: " + user1.getName() + " From: " + from1 + "   To: " + to1);     // outputs result
       }

       catch(InvalidAddressException e)             // invalid address
       {
        System.out.println(e.getMessage());
       }
       catch(InvalidDistException e)                // invalid distance
       {
        System.out.println(e.getMessage());
       }
       catch(DriverNotFoundException e)             // driver not found
       {
        System.out.println(e.getMessage());
       }
       catch(InsufficientFundsException e)          // insufficient funds
       {
        System.out.println(e.getMessage());
       }
       catch(ReqExistsException e)                  // request already exists
       {
        System.out.println(e.getMessage());
       }
       catch(UserNotFoundException e)               // user not found
       {
        System.out.println(e.getMessage());
       }
       

      }
      // Request a food delivery
      else if (action.equalsIgnoreCase("REQDLVY")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestDelivery() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)
        // "Restaurant: "           (string)
        // "Food Order #: "         (string)
        Scanner myObj2 = new Scanner(System.in);          // gets ID
        System.out.println("User Account Id: ");
        String Id1 = myObj2.nextLine();

        System.out.println("From Address: ");           // gets from address
        String from1 = myObj2.nextLine();

        System.out.println("To Address: ");             // gets to address
        String to1 = myObj2.nextLine();

        System.out.println("Restaurant: ");             // gets restaurant name
        String res = myObj2.nextLine();

        System.out.println("Food Order #: ");           // gets food order number
        String order = myObj2.nextLine();

       

        try
        {
          tmuber.requestDelivery(Id1, from1, to1, res, order);    // calls method
          User user1 = tmuber.getUser(Id1);     // gets user from ID
          System.out.println("DELIVERY for: " + user1.getName() + " From: " + from1 + "    To: " + to1);      // outputs the result
        }

        catch(InvalidAddressException e)          // invalid address
       {
        System.out.println(e.getMessage());
       }
       catch(InvalidDistException e)              // invalid distance
       {
        System.out.println(e.getMessage());
       }
       catch(DriverNotFoundException e)           // driver not found
       {
        System.out.println(e.getMessage());
       }
       catch(InsufficientFundsException e)        // insufficient funds
       {
        System.out.println(e.getMessage());
       }
       catch(ReqExistsException e)                // request already exists
       {
        System.out.println(e.getMessage());
       }
       catch(UserNotFoundException e)             // user not found
       {
        System.out.println(e.getMessage());
       }

       
      }

      else if (action.equalsIgnoreCase("PICKUP"))
      {
        Scanner scanner2 = new Scanner(System.in);

        System.out.println("Driver Id: ");        // gets ID
        String id = scanner2.nextLine();
        

        try
        {
          Driver d = tmuber.getDriver(id);        // finds driver using ID
          tmuber.pickUp(id);                      // calls method
          int zone = d.getZone();                 // gets zone
          System.out.println("Driver " + id + " Picking Up in Zone " + zone);       // output
        }

        catch(DriverNotFoundException e)            // driver not found
        {
          System.out.println(e.getMessage());
        }

        catch(ReqNotFoundException e)               // request not found
        {
          System.out.println(e.getMessage());
        }
      }

      else if (action.equalsIgnoreCase("LOADDRIVERS"))
      {
       
        Scanner s = new Scanner(System.in);
        System.out.println("Filename: ");         // gets filename
        String name = s.nextLine();
        try
        {
          tmuber.setDrivers(TMUberRegistered.loadPreregisteredDrivers(name));         // calls method
          System.out.println("Drivers loaded");     // output
        }
        catch(FileNotFoundException e)            // if file not found
        {
          System.out.println(name + " Not Found");
        }

      }

      else if (action.equalsIgnoreCase("LOADUSERS"))
      {
        Scanner scan = new Scanner(System.in);
        System.out.println("Filename: ");         // gets filename
        String name = scan.nextLine();
        try
        {
          tmuber.setUsers(TMUberRegistered.loadPreregisteredUsers(name));       // calls method
          System.out.println("Users loaded");     // output
        }
        catch(FileNotFoundException e)              // if file not found
        {
          System.out.println(name + " Not Found");
        }
      }

      else if (action.equalsIgnoreCase("driveto"))
      {
        Scanner s2 = new Scanner (System.in);
        System.out.println("Driver Id: ");          // gets ID
        String id = s2.nextLine();
        
        System.out.println("Address: ");            // gets address
        String address = s2.nextLine();

        int zone = CityMap.getCityZone(address);      // calls zone

        try
        {
          tmuber.driveTo(id, address);        // calls method
          System.out.println("Driver " + id + " Now in Zone " + zone);        // output
        }

        catch(DriverBusyException e)                // if driver is busy
        {
          System.out.println(e.getMessage());
        }
        catch(DriverNotFoundException e)            // if driver not found
        {
          System.out.println(e.getMessage());
        }
        catch(InvalidAddressException e)            // if invalid address
        {
          System.out.println(e.getMessage());
        }
      }

  
      // Sort users by name
      else if (action.equalsIgnoreCase("SORTBYNAME")) 
      {
        tmuber.sortByUserName();
      }
      // Sort users by number of ride they have had
      else if (action.equalsIgnoreCase("SORTBYWALLET")) 
      {
        tmuber.sortByWallet();
      }
     
      // Cancel a current service (ride or delivery) request
       else if (action.equalsIgnoreCase("CANCELREQ")) 
      {
        int request = -1;
        int zone = 0;
        System.out.print("Request #: ");
        if (scanner.hasNextInt())
        {
          request = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }

        System.out.print("Zone: ");
        if (scanner.hasNextInt())
        {
          zone = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }

        
          try
          {
            tmuber.cancelServiceRequest(request, zone);     // calls method
            System.out.println("Service request #" + request + " cancelled");     // output
          }
          catch(InvalidRequestException e)        // if invalid request
          {
            System.out.println(e.getMessage());
          }
      }
      // Drop-off the user or the food delivery to the destination address
      else if (action.equalsIgnoreCase("DROPOFF")) 
      {
        boolean done = false;
        String id = "";
        System.out.print("Driver ID: ");        // gets ID
        if (scanner.hasNextInt())
        {
          id = scanner.next();
          scanner.nextLine(); // consume nl
        }
        
        try
        {
          tmuber.dropOff(id);     // calls method
          System.out.println("Driver " + id + " Dropping Off");       // output
        }
        catch(DriverNotFoundException e)            // if driver not found
        {
          System.out.println(e.getMessage());
        }
        catch(ReqNotFoundException e)               // if request not found
        {
          System.out.println(e.getMessage());
        }
      }

        
      // Get the Current Total Revenues
      else if (action.equalsIgnoreCase("REVENUES")) 
      {
        System.out.println("Total Revenue: " + tmuber.totalRevenue);
      }
      // Unit Test of Valid City Address 
      else if (action.equalsIgnoreCase("ADDR")) 
      {
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        System.out.print(address);

          try
          {
            CityMap.validAddress(address);                // calls method
            System.out.println("\nGood Address");       // good address
          }
          catch(InvalidAddressException e)
          {
            System.out.println("\nBad Address");        // bad address
          }
      }
      // Unit Test of CityMap Distance Method
      else if (action.equalsIgnoreCase("DIST")) 
      {
        String from = "";
        System.out.print("From: ");       // gets from address
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To: ");         // gets to address
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        System.out.print("\nFrom: " + from + " To: " + to);
        System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");      // output
      }
      
      System.out.print("\n>");
    }
  }
}

