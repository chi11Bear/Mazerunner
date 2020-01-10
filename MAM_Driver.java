// 161 Lab 8
// Darren Joyner
// Driver to run the program

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.util.*;
import java.io.FileOutputStream;
import java.io.*; 
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class MAM_Driver {
	static int MapSizeX = 20;
	static int MapSizeY = 20;
	static int WallChance = 20;
	static int OppChance = 3;
	static int pBoundary = 0;
	static int boundary = 0;
   static Scanner sc = new Scanner(System.in);
   static String input;
   static Panel targetPanel = null;
 
   public static void main(String[] args) {
      Menu();
	}

	// Method to display main menu
	public static void Menu() {
		pBoundary = 0;
	   boundary = 0;
      String input;
      targetPanel = null;

		System.out.println("|-Welcome to Maze & Monsters-|");
		System.out.println("----------------------");
      System.out.println(">     MAIN MENU       ");
		System.out.println(">  Start Game {Y/N}   ");
      System.out.println(">   Load Game {L}     ");
      System.out.println("----------------------");
		
      System.out.print(">:");
		input = sc.nextLine();
		
      if (input.equalsIgnoreCase("y")) { // Start Game
			StartGame();
      }
      if (input.equalsIgnoreCase("l")) { // Load Game
			load();
		}
       else if (input.equalsIgnoreCase("n")) {
			System.out.println("> Quit then? (Y/N)");
			System.out.print(">:");

			input = sc.nextLine();
			if (input.equalsIgnoreCase("y")) { // Exit Game
				System.exit(0);
			} 
         else {
				Menu();
			}
		} 
      else {
			Menu();
		}
	}

	// Method to start game
	public static void StartGame() {

      //Scanner sc = new Scanner(System.in);
		Panel[][] map = new Panel[MapSizeY][MapSizeX]; // initialize the map
														// arrays
		ArrayList<Character> opponents = new ArrayList<Character>();
 
		System.out.println("> GENERATING MAP ...");
		GenerateMap(map, opponents);

		System.out.println("> MAP GENERATED");
		DisplayMap(map);

		System.out.println("> " + opponents.size() + " MONSTERS ARE IN THE MAP");

		while (true) { // Loop to re-generate map
			System.out.println("> RE-GENERATE THE MAP? (Y/N)");
			System.out.print(">:");

			String input = sc.nextLine();
			if (input.equalsIgnoreCase("y")) { // Re-generate the map in case it doesn't work
				System.out.println("> RESETTING PARAMETERS");
				map = new Panel[MapSizeY][MapSizeX];
				opponents = new ArrayList<Character>();

				System.out.println("> GENERATING MAP ...");
				GenerateMap(map, opponents);

				System.out.println("> MAP GENERATED");
				DisplayMap(map);

				System.out.println("> " + opponents.size() + " MONSTERS ARE IN THE MAP");
			} 
         
         else {
				break;
			}
		}
		
      // Generate character
		System.out.println("> GENERATING PLAYER CHARACTER");
		Character player = new Character("ty");
		player.AC = 15;
		player.X = 0;
		player.Y = 0;
		map[0][0].character = player;
		System.out.println(player);
      
		// Start the game
      System.out.println("> GAME START!");
      controlsInput(player, map);
      }
      
   // Method for player input during the game
   public static void controlsInput(Character player, Panel[][] map)  {
      while (true) {
         DisplayMap(map);
   		System.out.print("> Command: ");
   		input = sc.nextLine();
   	   //Check Player's HP using 'P'
   		if (input.equalsIgnoreCase("P")) {
   			System.out.println(player);
   		}
         else {
   			Move(input, player, map);
   		}
   	}
   }

	// Method handling ALL the movements
	public static void Move(String input, Character player, Panel[][] map)  {
		if (input.equalsIgnoreCase("d")) {
			pBoundary = player.X;
			boundary = MapSizeX - 1;
		} 
      else if (input.equalsIgnoreCase("w")) {
			pBoundary = player.Y;
			boundary = 0;
		} 
      else if (input.equalsIgnoreCase("a")) {
			pBoundary = player.X;
			boundary = 0;
		} 
      else if (input.equalsIgnoreCase("s")) {
			pBoundary = player.Y;
			boundary = MapSizeY - 1;
      } 
      else if (input.equalsIgnoreCase("m")) {
         options(input, player, map);
         boundary = -1;
		}
		if (pBoundary == boundary) {
			System.out.println("> ERROR: BOUNDARY");
		} 
      else {
			if (input.equalsIgnoreCase("d")) {
				targetPanel = map[player.Y][player.X + 1];
			} 
         else if (input.equalsIgnoreCase("w")) {
				targetPanel = map[player.Y - 1][player.X];
			} 
         else if (input.equalsIgnoreCase("a")) {
				targetPanel = map[player.Y][player.X - 1];
			} 
         else if (input.equalsIgnoreCase("s")) {
				targetPanel = map[player.Y + 1][player.X];
			}
			// Check wall
			if (targetPanel.panel_type == -1) {
				System.out.println("> ERROR: WALL");
			}
			// Able to walk
			else {
				// Check for opponent
				if (targetPanel.panel_type == 2) {
					System.out.println("> FOUND OPPONENT");
					System.out.println(targetPanel.character);
					if (Fight(player, targetPanel.character)) {

					} 
               else {

					}
				}
				// Check for Goal
				else if (targetPanel.panel_type == 3) {
					System.out.println("> CONGRATULATIONS! YOU WON!");
					Menu(); // Go back to main menu once you won?
				}
				// Erase your footstep
				map[player.Y][player.X].character = null;
				map[player.Y][player.X].panel_type = 0;
				// Emerge anew
				targetPanel.character = player;
				targetPanel.panel_type = 1;
				if (input.equalsIgnoreCase("d")) {
					player.X += 1;
				} 
            else if (input.equalsIgnoreCase("w")) {
					player.Y -= 1;
				} 
            else if (input.equalsIgnoreCase("a")) {
					player.X -= 1;
				} 
            else if (input.equalsIgnoreCase("s")) {
					player.Y += 1;
				}
			}
		}
       //System.out.println(pBoundary +" " + boundary);
	}

	// Method handle the fight
	public static boolean Fight(Character player, Character opponent) {
      playerClass a = new playerClass();
		opponentClass b = new opponentClass();

      System.out.println("> INITIALIZING COMBAT ");
		System.out.println(player.name + " VS " + opponent.name);
		// Determine who goes first
		Character first = null;
		Character second = null;
		if (player.DEX >= opponent.DEX) { // Player goes first
			first = player;
			second = opponent;
		} else { // Opponent goes first
			first = opponent;
			second = player;
		}
		while (true) { // Combat loop
			// Check if attack hit
			int roll = Roll(20);
			int oppAC = second.AC + b.Mod(second.DEX);
			System.out.println(first.name + " roll:" + roll + " to attack " + second.name + "\'s " + oppAC + "AC");
         if (roll >= oppAC) {
				System.out.println("It hits!");
				if (second == opponent) {
					System.out.println("K.O. Opponent is down!");
					second.HP = 0;
					return true;
				} 
            else {
					int damage = Roll(6) + a.Mod(first.STR);
					if (damage > 0) // No healing from attack!
						//second.HP -= damage;
                  for (int i = 0; i < damage; i++) {
                     first.TakeDamage(second.HP);
					   }
          
					System.out.println(second.name + " take " + damage + " damages!, " + second.HP + " HP left!");
					if (second.HP <= 0) {
						System.out.println(second.name + " died!, GAME OVER!");
						return false;
					}
				}
			} else {
				System.out.println("It missed!");
			}

			// swap attacks
			Character temp = first;
			first = second;
			second = temp;
		}
	}
   
	// Method to display the map
	public static void DisplayMap(Panel[][] map) {
      System.out.println("----------------------");
		for (int Y = 0; Y < MapSizeY; Y++) {
			System.out.print("|");
			for (int X = 0; X < MapSizeX; X++) {
				if (map[Y][X].panel_type == -1) {
					System.out.print("=");
				} 
            else if (map[Y][X].panel_type == 0) {
					System.out.print(" ");
				} 
            else if (map[Y][X].panel_type == 1) {
					System.out.print("P");
				} 
            else if (map[Y][X].panel_type == 2) {
					System.out.print("E");
				} 
            else if (map[Y][X].panel_type == 3) {
					System.out.print("O");
				}
			}
			System.out.println("|");
		}
		System.out.println("----------------------");
       
  	}

	// Method to generate a playable map with randomized wall, opponent and goal
	// location
	public static void GenerateMap(Panel[][] map, ArrayList<Character> opponents) {
		for (int Y = 0; Y < MapSizeY; Y++) {
			for (int X = 0; X < MapSizeX; X++) {
				map[Y][X] = new Panel();
				int panelRoll = Roll(100);
				if (X == 0 && Y == 0) { // Panel is a starting point
					map[0][0].panel_type = 1;
					map[0][0].character = null;
				} 
            else if (panelRoll < WallChance) { // Panel is a wall
					map[Y][X].panel_type = -1;
				} 
            else if (panelRoll < (WallChance + OppChance)) { // Panel is
																	// an
																	// opponent
					map[Y][X].panel_type = 2;
					Character opponent = new Character("Monster" + X + "-" + Y);
					opponents.add(opponent);
					map[Y][X].character = opponent;
				} 
            else { // Panel is a blank space
					map[Y][X].panel_type = 0;
				}
			}
		}
      
		// Generate Goal
		int RandomY = Roll(5) + MapSizeY - 6;
		int RandomX = Roll(MapSizeX) - 1;
		while (true) { // looking for non-opponent panel
			if (map[RandomY][RandomX].panel_type == 2) {
				RandomY = Roll(5) + MapSizeY - 6;
				RandomX = Roll(MapSizeX) - 1;
			} 
         else {
				map[RandomY][RandomX].panel_type = 3;
				break;
			}
		}
	}
  
   // pause menu
   public static void options(String input, Character player, Panel[][] map)  {
      Scanner pm = new Scanner(System.in);
      Scanner pm2 = new Scanner(System.in);
      String choice2;
     
         System.out.println(">    pause/options    ");
         System.out.println("--------------------- ");
         System.out.println("> Save {1}");
         System.out.println("> Load {2}");
         System.out.println("> Start Screen {3}"); 
         System.out.println("> Return to Game {4}");
         System.out.println("--------------------- ");
        
         System.out.print(">:");
         int choice = pm.nextInt();
                   
         switch (choice){
            case 1: 
               save(input,player,map);
               break; 
           case 2: 
               load();
               break; 
           case 3: 
               System.out.println("> All progress will be lost by returning to the start srceen");
               System.out.println("Continue? (Y/N)");
               System.out.print(">:");
               choice2 = pm2.nextLine();
               
               if (choice2.equalsIgnoreCase("y")) { // Start Game
			          Menu();
               }
               else if (choice2.equalsIgnoreCase("n")) {
                  options(input, player, map);
               }
               break; 
           case 4:   
               controlsInput(player, map);
               break;
           default: 
               System.out.println("> Invalid Input");
               options(input, player, map);
         }
   }
   
   //Method to save game
   public static void save(String input, Character player, Panel[][] map)  {
      int [] bounds = new int [] {pBoundary,boundary};
      
         try {
         FileOutputStream f = new FileOutputStream(new File("savefile.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);  
         
         o.writeObject(map);
         
         o.close();
			f.close();   
         
         FileOutputStream file = new FileOutputStream("savefile2.txt");
         for (int i = 0; i < bounds.length; i++)
            file.write(bounds[i]);
         file.close();         
     
         System.out.println("> Game Saved");
      }
      catch (IOException e) {
         System.out.println("> Couldn't Save");
         e.printStackTrace();
      } 
      options(input,player,map);      
   }
   
   //Method to load a save game
   public static void load() {
      InputStream is = null;
      byte[] buffer = new byte[2];
      int [] array = new int[42];
      int a;
      short y;
      Panel[][] savedMap = new Panel[20][20];
      Scanner fp = new Scanner(System.in);
      String fileInput;

      try {
         System.out.println("> Enter saved file name ");
         System.out.print(">:");
         fileInput = fp.nextLine();
        
         FileInputStream fi = new FileInputStream(new File(fileInput));
			ObjectInputStream oi = new ObjectInputStream(fi);
 
         savedMap = (Panel[][]) oi.readObject();
			
         oi.close();
			fi.close();

         is = new FileInputStream("savefile2.txt");
         
         System.out.println("Characters printed:");
         
         // read stream data into buffer
         is.read(buffer);
         
         // for each byte in the buffer
         for(byte b:buffer) {
         
            //convert byte to int
            for (int i = 0; i < buffer.length; i++) {
               //System.out.println(buffer[i]);
               y = buffer[i];
               a = y;
                  
               }
          System.out.print(buffer[0]);
         }        
         
         System.out.println("> Load Compelete");
         System.out.println("> GENERATING MAP ...");
         System.out.println("> Start!");
		} 
      catch (FileNotFoundException e) {
			System.out.println("File not found");
         Menu();
		} 
      catch (IOException e) {
			System.out.println("Error Initializing Stream");
         Menu();
		} 
      catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
      
      Character player = new Character("Player");
      controlsInput(player, savedMap);
   }     
   
	// Input a dice type, return a random roll
	public static int Roll(int max) {
		int dice = 0;
		Random rng = new Random();
		dice = rng.nextInt(max) + 1;
		return dice;
	}
   
   
   // generic method printArray
   public static < E > void printArray( E inputArray ) {
      // Display 
     
     
   }

}
