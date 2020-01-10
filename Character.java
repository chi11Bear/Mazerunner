// 161 Lab 8
// Darren Joyner
// Character object file generate randomized character

import java.util.Random;

 public class Character {
   
	String name;
	int STR, DEX, CON, INT, WIS, CHA, HP, AC, X, Y;

	// Blank Constructor
	public Character()  {

      name = ""; // Leave name blank
		STR = StatGen();
		DEX = StatGen();
		CON = StatGen();
		INT = StatGen();
		WIS = StatGen();
		CHA = StatGen();
		HP = 10 + Mod(CON);
      AC = 10;
	}
	
	// Non-Blank Constructor
	public Character(String name) {
         
      this.name = name;
	   STR = StatGen();
		DEX = StatGen();
		CON = StatGen();
		INT = StatGen();
		WIS = StatGen();
		CHA = StatGen();
		HP = 10 + Mod(CON);
      AC = 10;
   }
   public int Mod(int stat) {
         int mod = (stat-15)/2;  
                return mod;
   }

	// Generate stats by roll 4 dices and combines 3 highest rolls
	public static int StatGen() {
		int total = 0;
		int[] dices = { Roll(6), Roll(6), Roll(6), Roll(6) };
		int lowest = 6;
		for (int i = 0; i < dices.length; i++) { // Find lowest
			total += dices[i];
			if (lowest >= dices[i])
				lowest = dices[i];
		}
		return total-lowest;
	}

	// Input a dice type, return a random roll
	public static int Roll(int max) {
		int dice = 0;
		Random rng = new Random();
		dice = rng.nextInt(max) + 1;
		return dice;
	}
   
	public String toString(){
		String string = "My name is: "+name+" and here are my stats,\n";
		string += "["+STR+", "+DEX+", "+CON+", "+INT+", "+WIS+", "+CHA+"]\n";
		if(HP <= 0){
			string += "I'm currently dead\n";
		}else{
			string += "I'm still alive with "+HP+" HP left\n";
		}
		return string;
	}
   
   public int TakeDamage(int health) {
   --health; 
   return health;
   }
}

   //Input the desire stat, return a modifier for player
   
   class playerClass extends Character {
      public int Mod (int stat) {
        //Character a = new  playerClass();
         int mod = (stat-15)/2;
       
	   return mod;
      }
   }
  
   // Input the desire stat, return a modifier for opponent
    class opponentClass extends Character {
      public int Mod (int stat) {
         //Character b = new opponentClass();
         int mod = (stat-10)/2;
		   return mod;
      }
   }

