// 161 Lab 8
// Darren Joyner
// Panel stores archetype for each of the  location in the 2d array we used for map

import java.io.Serializable;


public class Panel implements Serializable {

   transient Character character;
	int panel_type;

	// Constructor for panel
	public Panel() {
		character = null;

	}


      
  //Method to convert panel_type to string
  // @Override
  // public String toString() {
  //   return  "" + panel_type + "" ; 
  //}
}  




