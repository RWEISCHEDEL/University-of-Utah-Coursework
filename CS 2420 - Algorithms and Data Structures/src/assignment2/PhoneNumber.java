package assignment2;

/**
 * Class representation of a phone number. This class was written by Dr. Meyer. We only used this class to 
 * create phone number objects to pass into the LibraryBookGeneric as and alternative to the String holder. 
 * @author Dr. Meyer
 */
public class PhoneNumber {

  private String areaCode;

  private String trunk;

  private String rest;

  public PhoneNumber(String phoneNum) {
    phoneNum = phoneNum.replaceAll("-|\\s|\\.|\\(|\\)", "");

    boolean isValid = true;
    if (phoneNum.length() != 10)
      isValid = false;
    for (int i = 0; isValid && i < 10; i++)
      if (!Character.isDigit(phoneNum.charAt(i)))
        isValid = false;

    if (isValid) {
      areaCode = phoneNum.substring(0, 3);
      trunk = phoneNum.substring(3, 6);
      rest = phoneNum.substring(6, 10);
    } else {
      areaCode = "000";
      trunk = "000";
      rest = "000";
      System.err.println("Phone number \"" + phoneNum
          + "\" is not formatted correctly, initializing as " + toString()
          + ".");
    }
  }
  
  public boolean equals(Object other) {	    
    if (!(other instanceof PhoneNumber))
      return false;

    PhoneNumber rhs = (PhoneNumber) other;
    PhoneNumber lhs = this;

    return lhs.areaCode.equals(rhs.areaCode) && lhs.trunk.equals(rhs.trunk)
      && lhs.rest.equals(rhs.rest);
  }

  public String toString() {
    return "(" + areaCode + ") " + trunk + "-" + rest;
  }
}