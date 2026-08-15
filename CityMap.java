import java.util.Arrays;
import java.util.Scanner;

// NAME: Abdelrahman Abdelaal
// ID: 501 227 765
// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap
{
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()
  // Other solutions are possible - you may replace this code if you wish
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static void validAddress(String address)
  {
    // Fill in the code
    // Make use of the helper methods above if you wish
    // There are quite a few error conditions to check for 
    // e.g. number of parts != 3

    
    if (address.equals(""))
    {
      throw new InvalidAddressException("Invalid Address");
    }
    
    String [] parts2 = getParts(address);     // seperates address into three different parts
    
    for (int i = 0; i < parts2.length; i++)   // iterates through list of parts
    {
      String word = parts2[i];                // sets current word to whatever index (i) in parts list
      

      if (i == 0)                             // if i is 0, it checks for the first part of the address (the number).
      {
        if (word.length() != 2)               // number should be of length 2
        {
          throw new InvalidAddressException("Invalid Address");       // throw exception if address invalid
        }

        if (allDigits(word) == false)         // checks if the first part of address is a number
        {
          //return false;
          throw new InvalidAddressException("Invalid Address");
        }

        if (Integer.parseInt(word) < 10 || Integer.parseInt(word) > 99)     // checks if number is between 99 and 10
        {
          
          throw new InvalidAddressException("Invalid Address");     // throw exception if address invalid
        }
      }

      if (i == 1)       // if i is 1, checks second number and prefix
      {
        
        if (word.length() != 3)     // length should be 3, for example, "2nd" has length 3
        {
          
          throw new InvalidAddressException("Invalid Address");       // throw exception if address invalid
        }

        String end = word.substring(1);               // gets prefix
        String num = word.substring(0,1);     // gets number

        if (!(Character.isDigit(word.charAt(0))))           // checks if there is a number
        {
          
          throw new InvalidAddressException("Invalid Address");       // throw exception if address invalid
        }

        if (!(end.equals("th") || end.equals("nd") || end.equals("rd") || end.equals("st")))      // checks if prefix is one of these options
        {
          
          throw new InvalidAddressException("Invalid Address");         // throw exception if address invalid
        }

        int realNum = Integer.parseInt(num);        // parses string containing the number into an integer

        if (realNum > 9 || realNum < 1)             // checks if number is in single digits
        {
          
          throw new InvalidAddressException("Invalid Address");     // throw exception if address invalid
        }

        if (realNum == 1)                           // if number is 1, prefix should end in st, ex. 1st
        {
          if (!(end.equals("st")))
          {
            
            throw new InvalidAddressException("Invalid Address");     // throw exception if address invalid
          }
        }

        if (realNum == 2)                           // if number is 2, prefix should end in nd, ex. 2nd
        {
          if (!(end.equals("nd")))
          {
            
            throw new InvalidAddressException("Invalid Address");         // throw exception if address invalid
          }
        }

        if (realNum == 3)                           // if number is 3, prefix should end in rd, ex. 3rd
        {
          if (!(end.equals("rd")))
          {
            
            throw new InvalidAddressException("Invalid Address");       // throw exception if address invalid
          }
        }

        if (realNum >= 4 && realNum <= 9)           // if number is 4 - 9, prefix should be th, ex. 9th
        {
          if (!(end.equals("th")))
          {
            
            throw new InvalidAddressException("Invalid Address");       // throw exception if address invalid
          }
        }
        
      }

      if (i == 2)     // checks last part of string
      {
        if (!(word.equalsIgnoreCase("avenue") || word.equalsIgnoreCase("street")))      // string should be avenue or street
        {
          
          throw new InvalidAddressException("Invalid Address");         // throw exception if address invalid
        }
      }
    }
    
    
     
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address)
  {
    int[] block = {-1, -1};

    // Fill in the code
    String parts[] = getParts(address);       // splits address into a list

    for (int i = 0; i < parts.length - 1; i++)    // iterates throug parts
    {
      String word = parts[i];     // sets current word to the word in idex i of the parts list

      if (i == 0)     // gets the first number from the double digit number (54 is 5)
      {
        if (parts[2].equalsIgnoreCase("street"))
        {
          block[0] = Integer.parseInt(word.substring(0, 1));
        }
        
        else{
          block[1] = Integer.parseInt(word.substring(0, 1));
        }
        
      }

      if (i == 1)   // gets the number from the string, example 3rd is 3
      {
        if (parts[2].equalsIgnoreCase("street"))
        {
          block[1] = Integer.parseInt(word.substring(0, 1));
        }
        
        else{
          block[0] = Integer.parseInt(word.substring(0,1));
        }
        
        
      }
    }
    return block;
  }
  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  // Hint: be careful not to generate negative distances
  
  // This skeleton version generates a random distance
  // If you do not want to attempt this method, you may use this default code

  public static int getDistance(String from, String to)     
  {
    // Fill in the code or use this default code below. If you use
    // the default code then you are not eligible for any marks for this part
    
    int[] block1 = getCityBlock(from);    // gets city blocks of each address using method above
    int [] block2 = getCityBlock(to);

    return Math.abs((block2[0] - block1[0])) + Math.abs((block2[1] - block1[1]));     // calculates and returns distance
  }

  public static int getCityZone(String address)
  {
    
    

    try{validAddress(address);}           // checks if address is valid, if not throws exception
    catch(InvalidAddressException e)
    {
      return -1;
    }
    
    int block[] = getCityBlock(address);      // gets city block

    // compares values to see which zone the address is in

    if (block[0] >= 1 && block[0] <= 5 && block[1] >= 6 && block[1] <= 9)
    {
      return 0;
    }
    
    else if (block[0] >= 6 && block[0] <= 9 && block[1] >= 6 && block[1] <= 9)
    {
      return 1;
    }

    else if (block[0] >= 6 && block[0] <= 9 && block[1] >= 1 && block[1] <= 5)
    {
      return 2;
    }

    else if (block[0] >= 1 && block[0] <= 5 && block[1] >= 1 && block[1] <= 5)
    {
      return 3;
    }

    return -1;
  }

}
