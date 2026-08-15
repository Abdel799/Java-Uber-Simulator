import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.io.FileNotFoundException;

/*
 * NAME: Abdelrahman Abdelaal
 * ID: 501 227 765
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private Map <String, User> userMap;             // map of all users
  private ArrayList<Driver> drivers;              // list of all drivers

  //private ArrayList<TMUberService> serviceRequests;     // list of all requests

  private Queue <TMUberService>[] zones;        // list of queues for zones

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    userMap = new TreeMap <String, User> ();    // initalizes map of users
    drivers = new ArrayList<Driver>();          // initalizes arraylist of drivers
    
    zones = new Queue[4];                       // initalizes list of queues

    for (int i = 0; i < 4; i++)                 // initalizes each queue in list of queues
    {
      zones[i] = new LinkedList <TMUberService>();
    }
      
    totalRevenue = 0;
  }

  public void setDrivers(ArrayList<Driver> drivers2)    // sets arraylist of drivers
  {
    drivers = drivers2;
  }

  public void setUsers(ArrayList<User> users2)        // sets map of users
  {
    for (int i = 0; i < users2.size(); i++)
    {
      userMap.put(users2.get(i).getAccountId(), users2.get(i));     // puts account id, user object in map
    }
  }

  
  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId)
  {
    // Fill in the code
    Collection<User> valuesCollection = userMap.values();               // collects all values in map
    ArrayList<User> users = new ArrayList <User> (valuesCollection);    // converts collection of values to arraylist
    
    for (int i = 0; i < users.size(); i++)                      // iterates through the users arraylist until it finds a user with the same ID
    {
      if (users.get(i).getAccountId().equals(accountId))        // gets ID from user in the list and compares it to given ID
      {
        return users.get(i);
      }
    }

    return null;          // return null is user not found
  }

  public Driver getDriver(String accountId)
  {
    for (int i = 0; i < drivers.size(); i++)                      // iterates through the driver arraylist until it finds a driver with the same ID
    {
      if (drivers.get(i).getId().equals(accountId))        // gets ID from driver in the list and compares it to given ID
      {
        return drivers.get(i);
      }
    }

    return null;
  }
  
  // Check for duplicate user
  private void userExists(User user)
  {
    // Fill in the code
    Collection<User> valuesCollection = userMap.values();                 // collects all values in map
    ArrayList<User> users = new ArrayList <User> (valuesCollection);      // converts collection of values to arraylist
    
    boolean found = false;
    for (int i = 0; i < users.size(); i++)        // iterates through the users arraylist and compares each user object to the given one
    {
      if (users.get(i).equals(user))      // checks is users are equal
      {
        found = true;
        break;
      }
    }
    
    if (found)
    {
      throw new UserExistsException("User Already Exists in System");
    }
  }
  
 // Check for duplicate driver
 private void driverExists(Driver driver)
 {
   // Fill in the code
  Boolean found = false;
   for (int i = 0; i < drivers.size(); i++)     // iterates through the drivers arraylist and compares each driver object to the given one
   {
      if (drivers.get(i).equals(driver))        // checks if drivers are equal
      {
        found = true;
        break;
      }
   }
   if (found)
   {
    throw new DriverExistsException("Driver Already Exists in System");
   }
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private void existingRequest(TMUberService req)
  {
    // Fill in the code

    String address = req.getFrom();     // gets from address
    Queue <TMUberService> serviceRequests = new LinkedList<>();     // creates a queue to check for req

    if (CityMap.getCityZone(address) == 0)
    {
      serviceRequests = zones[0];       // sets new queue to zone 0
    }

    if (CityMap.getCityZone(address) == 1)
    {
      serviceRequests = zones[1];       // sets new queue to zone 1
    }

    if (CityMap.getCityZone(address) == 2)
    {
      serviceRequests = zones[2];       // sets new queue to zone 2
    }

    if (CityMap.getCityZone(address) == 3)
    {
      serviceRequests = zones[3];       // sets new queue to zone 3
    }
    
    if (serviceRequests.contains(req))    // uses .contains method to check if request is in arraylist service requests
    {
      if(req.getServiceType().equals("DELIVERY"))     // if request type is delivery
      {
        //errMsg = "User Already Has Delivery Request at Resturant with this Food Order";
        throw new ReqExistsException("User Already Has Delivery Request at Resturant with this Food Order");
      }

      else{     // since type is not delivery it has to be ride
        //errMsg = "User Already Has Ride Request";
        throw new ReqExistsException("User Already Has Ride Request");
      }
      //return true;    // returns true if req is in service requests
    }
    //return false;     // returns false if req is not in service requests
  }

  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  // Return null if no available driver
  private Driver getAvailableDriver()
  {
    // Fill in the code

    for (int i = 0; i < drivers.size(); i++)    // iterates through drivers arraylist until it finds a driver who has status AVAILABLE
    {
      if (drivers.get(i).getStatus().equals(Driver.Status.AVAILABLE))   // checks if driver status is available
      {
        return drivers.get(i);    // once a driver is found the method returns the driver object
      }
    }
    
    return null;    // if no available drivers return null
  }

  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    System.out.println();

    Collection<User> valuesCollection = userMap.values();                 // gets values of map
    ArrayList<User> users = new ArrayList <User> (valuesCollection);      // converts collection of values to arraylist

    Collections.sort(users, new IDComparator());      // id comparator is explained below with other comparators
    
    for (int i = 0; i < users.size(); i++)    // iterates through users list and uses printinfo() method
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      users.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    // Fill in the code
    System.out.println();

    for (int i = 0; i < drivers.size(); i++)    // iterates through drivers list and uses printinfo() method
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println();
    }
    
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    // Fill in the code
    System.out.println();

    Queue <TMUberService> zone0 = new LinkedList <TMUberService>();     // creates queue for zone 0
    zone0 = zones[0];     // stores zone 0
    Iterator <TMUberService> iter0 = zone0.iterator();      // creates iterator for zone 0
    int index = 0;

    System.out.println("ZONE 0");
    System.out.println("------");
    System.out.println("------");
    System.out.println();

    while (iter0.hasNext())
    {
      index += 1;
      System.out.printf("%-2s. ", index);
      iter0.next().printInfo();
      System.out.println();
    }
    System.out.println();
    Queue <TMUberService> zone1 = new LinkedList <TMUberService>();     // creates queue for zone 1
    zone1 = zones[1];     // stores zone 1
    Iterator <TMUberService> iter1 = zone1.iterator();      // creates iterator for zone 1
    int index1 = 0;
    
    System.out.println("ZONE 1");
    System.out.println("------");
    System.out.println("------");
    System.out.println();

    while (iter1.hasNext())
    {
      index1 += 1;
      System.out.printf("%-2s. ", index1);
      iter1.next().printInfo();
      System.out.println();
    }
    System.out.println();
    Queue <TMUberService> zone2 = new LinkedList <TMUberService>();     // creates queue for zone 2
    zone2 = zones[2];     // stores zone 2
    Iterator <TMUberService> iter2 = zone2.iterator();      // creates iterator for zone 2
    int index2 = 0;
    
    System.out.println("ZONE 2");
    System.out.println("------");
    System.out.println("------");
    System.out.println();

    while (iter2.hasNext())
    {
      index2 += 1;
      System.out.printf("%-2s. ", index2);
      iter2.next().printInfo();
      System.out.println();
    }
    System.out.println();
    Queue <TMUberService> zone3 = new LinkedList <TMUberService>();     // creates queue for zone 3
    zone3 = zones[3];     // stores zone 3
    Iterator <TMUberService> iter3 = zone3.iterator();      // creates iterator for zone 3
    int index3 = 0;
    
    System.out.println("ZONE 3");
    System.out.println("------");
    System.out.println("------");
    System.out.println();

    while (iter3.hasNext())
    {
      index3 += 1;
      System.out.printf("%-2s. ", index3);
      iter3.next().printInfo();
      System.out.println();
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet)
  {
    // Fill in the code. Before creating a new user, check paramters for validity
    // See the assignment document for list of possible erros that might apply
    // Write the code like (for example):
    // if (address is *not* valid)
    // {
    //    set errMsg string variable to "Invalid Address "
    //    return false
    // }
    // If all parameter checks pass then create and add new user to array list users
    // Make sure you check if this user doesn't already exist!

    Collection<User> valuesCollection = userMap.values();               // gets values of map
    ArrayList<User> users = new ArrayList <User> (valuesCollection);    // converts collection of values to arraylist

    if (name.equals(null) || name.equals(""))       // checks is name is empty or null
    {
      throw new InvalidUserNameException("Invalid User Name");
    }

    try
    {
      CityMap.validAddress(address);      // checks if address is valid
    }

    catch(InvalidAddressException e)
    {
      throw new InvalidAddressException("Invalid Address");     // if address is not valid throw exception
    }

    if (wallet < 0)       // cannot have negative funds
    {
      throw new InvalidMoneyAmountException("Invalid Money in Wallet");   // if wallet is not valid throw exception
    }
    
    User user = new User (TMUberRegistered.generateUserAccountId(users), name, address, wallet);      // creates a new user object, and generates new ID
    
    try
    {
      userExists(user);
      userMap.put(user.getAccountId(), user);       // if user does not already exist, add new user to map of users
    }

    catch(UserExistsException e)
    {
      System.out.println(e.getMessage());         // if user exists throw exception
    }
    
    
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address)
  {
    // Fill in the code - see the assignment document for error conditions
    // that might apply. See comments above in registerNewUser

    if (name.equals(null) || name.equals(""))     // checks if name is empty or null
    {
      //errMsg = "Invalid Driver Name";       // sets error message and returns false
      throw new InvalidDriverNameException("Invalid Driver Name");
      //return false;
    }

    if (carModel.equals(null) || carModel.equals(""))     // checks if car model is empty or null
    {
      //errMsg = "Invalid Car Model";         // sets error message and returns false
      throw new InvalidCarModelException("Invalid Car Model");
     //return false;
    }

    if (carLicencePlate.equals(null) || carLicencePlate.equals(""))     // checks if license plate is empty or null
    {
      //errMsg = "Invalid Car Licence Plate";       // sets error message and returns false
      throw new InvalidPlateException("Invalid Car Licence Plate");
      //return false;
    }

    try
    {
      CityMap.validAddress(address);      // checks if address is valid
    }

    catch(InvalidAddressException e)
    {
      throw new InvalidAddressException("Invalid Address");     // if address is not valid throw exception
    }

    Driver drive = new Driver (TMUberRegistered.generateDriverId(drivers), name, carModel, carLicencePlate, address);      // creates a new driver object and generates ID

    try 
    {
      driverExists(drive);
      drivers.add(drive);       // if driver does not already exist, add new driver to list of drivers
    }

    catch(DriverExistsException e)
    {
      throw new DriverExistsException("Driver Already Exists in System");
    }

    

    //return true;
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to)
  {
    // Check for valid parameters
	// Use the account id to find the user object in the list of users
    // Get the distance for this ride
    // Note: distance must be > 1 city block!
    // Find an available driver
    // Create the TMUberRide object
    // Check if existing ride request for this user - only one ride request per user at a time!
    // Change driver status
    // Add the ride request to the list of requests
    // Increment the number of rides for this user
    // check for valid address?
    
    /*if (!CityMap.validAddress(from) || !CityMap.validAddress(to))     // checks if from and to addresses are valid
    {
      errMsg = "Invalid Address";     // sets error message and returns false
      return false;
    }*/

    if (getUser(accountId) == null)
    {
      throw new UserNotFoundException("User Not Found");    // throws exception if id is invalid
    }
    
    try
    {
      CityMap.validAddress(from);     // checks if from is a valid address
      CityMap.validAddress(to);       // checks if to is a valid address
    }

    catch(InvalidAddressException e)
    {
      throw new InvalidAddressException("Invalid Address");   // throws exception if catches invalid address
    }
    
    int distance = CityMap.getDistance(from, to);     // calculates distance between from and to

    if (distance <= 1)      // distance cannot be less than or equal to 1
    {
      throw new InvalidDistException("Insufficient Travel Distance");   // throws exception if dist is invalid
    }

    if (getAvailableDriver() == null)     // checks for available driver
    {
      //errMsg = "No Drivers Available";
      throw new DriverNotFoundException("No Drivers Available");    // throws exception if no driver available
    }
    
    Driver driver1 = getAvailableDriver();      // gets available driver
    User user1 = getUser(accountId);            // gets user 
    double cost1 = getRideCost(distance);       // gets cost

    TMUberRide ride1 = new TMUberRide(from, to, user1, distance, cost1);       // creates a ride object

    if (user1.getWallet() < cost1)      // if user does not have enough money to pay 
    {
      throw new InsufficientFundsException("Insufficicent Funds");    // throws exception if user can't pay for cost
    }
  
    try
    {
      existingRequest(ride1);                         // checks for existing req
      //driver1.setStatus(Driver.Status.DRIVING);       // sets chosen driver status to DRIVING
      user1.addRide();                                // adds ride to user

      // adds request depending on which zone the from address is in
      
      if (CityMap.getCityZone(from) == 0)
      {
        zones[0].add(ride1);
      }

      if (CityMap.getCityZone(from) == 1)
      {
        zones[1].add(ride1);
      }

      if (CityMap.getCityZone(from) == 2)
      {
        zones[2].add(ride1);
      }

      if (CityMap.getCityZone(from) == 3)
      {
        zones[3].add(ride1);
      }
    
    }

    catch(ReqExistsException e)
    {
      throw new ReqExistsException("User Already Has Ride Request");
    }
    driver1.setService(ride1);
    
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {
    // See the comments above and use them as a guide
    // For deliveries, an existing delivery has the same user, restaurant and food order id
    // Increment the number of deliveries the user has had

    if (getUser(accountId) == null)
    {
      throw new UserNotFoundException("User Not Found");      // if user is null, throws exception
    }
    
    try
    {
      CityMap.validAddress(from);
      CityMap.validAddress(to);
    }

    catch(InvalidAddressException e)
    {
      throw new InvalidAddressException("Invalid Address");     // if address is invalid, throws exception
    }

    int distance = CityMap.getDistance(from, to);     // gets distance 

    if (distance <= 1)         // distances cannot be less than or equal to 1
    {
      //errMsg = "Insufficient Travel Distance";    // sets error message and returns false
      throw new InvalidDistException("Insufficient Travel Distance");
    }

    if (getAvailableDriver() == null)     // checks for available driver
    {
      //errMsg = "No Drivers Available";    // sets error message and returns false
      throw new DriverNotFoundException("No Drivers Available");
    }
   
    Driver driver1 = getAvailableDriver();        // gets driver
    User user1 = getUser(accountId);              // gets user
    double cost1 = getDeliveryCost(distance);     // gets cost
    
    TMUberDelivery ride1 = new TMUberDelivery(from, to, user1, distance, cost1, restaurant, foodOrderId);    // creates delivery object

    if (user1.getWallet() < cost1)      // if user cannot pay for cost
    {
      throw new InsufficientFundsException("Insufficicent Funds");      // throws exception
    }
    
    try
    {
      existingRequest(ride1);

      driver1.setStatus(Driver.Status.DRIVING);       // changes status to DRIVING
      
      user1.addDelivery();                            // adds delivery 
      
  
      // adds ride to zone depending on which zone the from address is in
      
      if (CityMap.getCityZone(from) == 0)
      {
        zones[0].add(ride1);
      }
  
      if (CityMap.getCityZone(from) == 1)
      {
        zones[1].add(ride1);
      }
  
      if (CityMap.getCityZone(from) == 2)
      {
        zones[2].add(ride1);
      }
  
      if (CityMap.getCityZone(from) == 3)
      {
        zones[3].add(ride1);
      }
    }

    catch (ReqExistsException e)      // if request already exists, throws exception
    {
      throw new ReqExistsException("User Already Has Delivery Request at Resturant with this Food Order");
    }
   
    driver1.setService(ride1);        // sets service of driver
    
  }

  public void pickUp(String driverId)
  {
    if (getDriver(driverId) == null)      // if driver is null throw exception
    {
      throw new DriverNotFoundException("Driver Not Found");
    }
    
    Driver drive = getDriver(driverId);         // gets driver
    int zone = drive.getZone();                 // gets zone

    if (zones[zone].isEmpty())
    {
      if (zone == 0){throw new ReqNotFoundException("No Service Request in Zone 0" );}
      else if (zone == 1){throw new ReqNotFoundException("No Service Request in Zone 1" );}
      else if (zone == 2){throw new ReqNotFoundException("No Service Request in Zone 2" );}
      else if (zone == 3){throw new ReqNotFoundException("No Service Request in Zone 3" );}
    }

    TMUberService req = zones[zone].peek();     // gets request from top of queue
    drive.setService(req);
    zones[zone].remove();                       // removes request
    drive.setStatus(Driver.Status.DRIVING);     // sets driver status
    drive.setAddress(req.getFrom());            // sets new address
  }

  public void driveTo(String driverId, String address)
  {
    try
    {
      CityMap.validAddress(address);          // checks for valid address
      Driver drive = getDriver(driverId);     // gets driver

      if (getDriver(driverId) == null)        // if driver is null throws exception driver not found
      {
      throw new DriverNotFoundException("Driver Not Found");
      }

      if (drive.getStatus() == Driver.Status.DRIVING)       // checks if driver is busy
      {
        throw new DriverBusyException("Driver is Busy");
      }

      drive.setAddress(address);      // sets address
      drive.setZone(address);         // sets zone
    }

    catch(InvalidAddressException e)      // if address invalid catches exception
    {
      throw new InvalidAddressException("Invalid Address");
    }
    
  }


  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list

  /*For cancel request use iterator to iterate through queue */
  public void cancelServiceRequest(int request, int zone)
  {
    // Check if valid request #
    // Remove request from list
    // Also decrement number of rides or number of deliveries for this user
    // since this ride/delivery wasn't completed

    // add an invalid zone # 
                                
    Iterator <TMUberService> iter = zones[zone].iterator();       // iterator for specified queue
    int count = 0;
    boolean found = false;

    while(iter.hasNext())
    {
      count++;
      TMUberService req = iter.next();

      if (count == request)      // if found, check what type of request it is (ride or delivery), and decrement request from user
      {
        found = true;   // sets found to true
        if(req.getServiceType().equals("RIDE"))
        { 
          req.getUser().decrementRide();      // decrements ride
        }

        else
        {
          req.getUser().decrementDelivery();    // decrements delivery
        }   
        Iterator <TMUberService> iter2 = zones[zone].iterator();
        Queue<TMUberService> temp = new LinkedList<TMUberService>();

        while(iter2.hasNext())        // removes the request from queue by adding every request that is not the specified request into the temporary queue, then setting actual queue to the temporary
        {
          TMUberService req2 = iter2.next();

          if (req2 != req)
          {
            temp.add(req2);
          }
        }
        zones[zone] = temp;
        break;
      }
        
    }

    if (found == false)     // if req is not found, throws exception
    {
      throw new InvalidRequestException("Invalid Request #");
    }
    
  }
    
    
  
  
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public void dropOff(String driverId)
  {
    // See above method for guidance
    // Get the cost for the service and add to total revenues
    // Pay the driver
    // Deduct driver fee from total revenues
    // Change driver status
    // Deduct cost of service from user

    if (getDriver(driverId) == null)
    {
      throw new DriverNotFoundException("Driver Not Found in System");      // if driver is null throws exception
    }
    
    Driver drive = getDriver(driverId);

    if (drive.getService() == null)
    {
      throw new ReqNotFoundException("Request not found");      // if req not found throws exception
    }
    TMUberService req = drive.getService();
    
    
    double cost1 = req.getCost();            // gets cost
    req.getUser().payForService(cost1);      // user pays for request
    double driver_pay = cost1 * PAYRATE;      // calculates driver pay
    totalRevenue += (cost1 - driver_pay);     // subtracts revenue from driver pay

    double wallet1 = drive.getWallet();    // gets driver wallet
    drive.setWallet(driver_pay + wallet1);   // sets driver wallet to driver pay + current wallet

    drive.setStatus(Driver.Status.AVAILABLE);    // changes driver status to available
    drive.setService(null);
    
  }


  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {
    Collection<User> valuesCollection = userMap.values();
    ArrayList<User> users = new ArrayList <User> (valuesCollection);

    Collections.sort(users, new NameComparator());      // sorts list of users based off the method below

    System.out.println();
    
    for (int i = 0; i < users.size(); i++)    // iterates through users list and uses printinfo() method
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      users.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator <User>
  {
    public int compare (User a, User b)
    {
      char letter1 = a.getName().charAt(0);     // gets first letter of name of first user
      char letter2 = b.getName().charAt(0);     // gets first letter of name of second user

      letter1 = Character.toLowerCase(letter1);
      letter2 = Character.toLowerCase(letter2);
      
      int value1 = (int) letter1;     // gets the ascii value of the letter
      int value2 = (int) letter2;

      if (value1 > value2)      // compares the values of each users first letter
      {
        return 1;
      }

      if(value1 == value2)
      {
        return 0;
      }

      else{
        return -1;
      }
    }
  }

  
  // The reason why I made an ID Comparator was because whenever I added a new user to the user map, it wouldn't list it based on increasing ID, so I made this comparator to take care of that.
  
  private class IDComparator implements Comparator <User>
  {
    public int compare (User a, User b)
    {
      int value1 = Integer.parseInt(a.getAccountId());
      int value2 = Integer.parseInt(b.getAccountId());

      if (value1 > value2)      // compares the values of each users first letter
      {
        return 1;
      }

      if(value1 == value2)
      {
        return 0;
      }

      else{
        return -1;
      }
    }
  }

  // Sort users by number amount in wallet
  // Then ist all users
  public void sortByWallet()
  {
    
    Collection<User> valuesCollection = userMap.values();
    ArrayList<User> users = new ArrayList <User> (valuesCollection);
    
    Collections.sort(users, new UserWalletComparator());      // sorts users based off the method below

    System.out.println();

    for (int i = 0; i < users.size(); i++)    // iterates through users list and uses printinfo() method
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      users.get(i).printInfo();
      System.out.println(); 
    }
  
  }
  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator <User>
  {
    public int compare(User a, User b)
    {
      if (a.getWallet() > b.getWallet())      // compares wallet of each user. Determines who has more money, or if users have same amount
      {
        return 1;
      }

      if (a.getWallet() == b.getWallet())
      {
        return 0;
      }

      else
      {
        return -1;
      }
    }
  }

  

}


// ALL MY CUSTOM EXCEPTIONS

class DriverNotFoundException extends RuntimeException
{
    public DriverNotFoundException(){}

    public DriverNotFoundException(String message)
    {
      super(message);
    }
}

class UserNotFoundException extends RuntimeException
  {
    public UserNotFoundException(){}

    public UserNotFoundException(String message)
    {
      super(message);
    }
  }

class ReqNotFoundException extends RuntimeException
  {
    public ReqNotFoundException(){}

    public ReqNotFoundException(String message)
    {
      super(message);
    }
  }

class InvalidRequestException extends RuntimeException
  {
    public InvalidRequestException(){}

    public InvalidRequestException(String message)
    {
      super(message);
    }
  }

class InsufficientFundsException extends RuntimeException
  {
    public InsufficientFundsException(){}

    public InsufficientFundsException(String message)
    {
      super(message);
    }
  }

class UserExistsException extends RuntimeException
  {
    public UserExistsException(){}

    public UserExistsException(String message)
    {
      super(message);
    }
  }


class DriverExistsException extends RuntimeException
  {
    public DriverExistsException(){}

    public DriverExistsException(String message)
    {
      super(message);
    }
  }


class ReqExistsException extends RuntimeException
  {
    public ReqExistsException(){}

    public ReqExistsException(String message)
    {
      super(message);
    }
  }


class InvalidAddressException extends RuntimeException
  {
    public InvalidAddressException(){}

    public InvalidAddressException(String message)
    {
      super(message);
    }
  }


class InvalidDistException extends RuntimeException
  {
    public InvalidDistException(){}

    public InvalidDistException(String message)
    {
      super(message);
    }
  }


class InvalidDriverNameException extends RuntimeException
  {
    public InvalidDriverNameException(){}

    public InvalidDriverNameException(String message)
    {
      super(message);
    }
  }


class InvalidUserNameException extends RuntimeException
  {
    public InvalidUserNameException(){}

    public InvalidUserNameException(String message)
    {
      super(message);
    }
  }


class InvalidPlateException extends RuntimeException
  {
    public InvalidPlateException(){}

    public InvalidPlateException(String message)
    {
      super(message);
    }
  }


class InvalidMoneyAmountException extends RuntimeException
  {
    public InvalidMoneyAmountException(){}

    public InvalidMoneyAmountException(String message)
    {
      super(message);
    }
  }


class InvalidCarModelException extends RuntimeException
  {
    public InvalidCarModelException(){}

    public InvalidCarModelException(String message)
    {
      super(message);
    }
  }

  class DriverBusyException extends RuntimeException
  {
    public DriverBusyException(){}

    public DriverBusyException(String message)
    {
      super(message);
    }
  }

