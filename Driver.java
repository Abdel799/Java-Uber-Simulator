/*
 * NAME: Abdelrahman Abdelaal
 * ID: 501 227 765
 * This class simulates a car driver in a simple uber app 
 * 
 * Everything has been done for you except the equals() method
 */
public class Driver
{
  private String id;
  private String name;
  private String carModel;
  private String licensePlate;
  private double wallet;
  private String type;
  private TMUberService service;
  private int zone = 0;
  private String address;
  
  public static enum Status {AVAILABLE, DRIVING};
  private Status status;
    
  
  public Driver(String id, String name, String carModel, String licensePlate, String address)
  {
    this.id = id;
    this.name = name;
    this.carModel = carModel;
    this.licensePlate = licensePlate;
    this.status = Status.AVAILABLE;
    this.wallet = 0;
    this.type = "";
    this.address = address;
    this.zone = CityMap.getCityZone(address);
    this.service = null;
  }
  // Print Information about a driver
  public void printInfo()
  {
    System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f Status: %-10s Address: %-10s Zone: %-10s", 
                      id, name, carModel, licensePlate, wallet, status, address, zone);
  }
  // Getters and Setters

  

  public int getZone()
  {
    return zone;
  }

  public void setZone(String address)
  {
    zone = CityMap.getCityZone(address);
  }

  public void setService(TMUberService s)
  {
    this.service = s;
  }

  public TMUberService getService()
  {
    return service;
  }

  public void setAddress(String a)
  {
    this.address = a;
  }

  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getCarModel()
  {
    return carModel;
  }
  public void setCarModel(String carModel)
  {
    this.carModel = carModel;
  }
  public String getLicensePlate()
  {
    return licensePlate;
  }
  public void setLicensePlate(String licensePlate)
  {
    this.licensePlate = licensePlate;
  }
  public Status getStatus()
  {
    return status;
  }
  public void setStatus(Status status)
  {
    this.status = status;
  }
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(double wallet)
  {
    this.wallet = wallet;
  }
  /*
   * Two drivers are equal if they have the same name and license plates.
   * This method is overriding the inherited method in superclass Object
   * 
   * Fill in the code 
   */
  public boolean equals(Object other)
  {
    
    if (other == null)      // checks if other is null
    {
      return false;
    }
    
    Driver other2 = (Driver) other;     // typecasts other to class Driver

    if (!(this.name.equals(other2.getName())))    // checks if names are not equal
    {
      return false;
    }

    if (!(this.licensePlate.equals(other2.getLicensePlate())))      // checks if license plates are not equal
    {
      return false;
    }
    return true;
  }
  
  // A driver earns a fee for every ride or delivery
  public void pay(double fee)
  {
    wallet += fee;
  }
}
