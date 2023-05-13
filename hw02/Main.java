package hw02;
import java.util.Scanner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		runner();
	}
	
	
	/** This method runs a menu for the user, if the user enter 0, the program
	 * ends. */
	public static void runner() {
		PPMImage p = null;
		/**This {@code Scanner} is a scanner to get user input. */
		Scanner input = new Scanner(System.in);
		String userChoices;
		
		/** do while loops repeats if user does not enter 0*/
		do {
			System.out.println("Enter"
					+ "\n  0 to quit"
					+ "\n  1 to choose an image"
					+ "\n  2 to name the new file"
					+ "\n  3 to sepia()"
					+ "\n  4 to greyscale()"
					+ "\n  5 to negative()" );
			
			userChoices = input.nextLine();
			
		
			/** Used a switch to do what the user needs, choose an image,
			 * invoke negative() and the two more. */
			switch (Integer.valueOf(userChoices)) {
			case 0:
				System.out.println("Process finshed>");
				break;
			case 1:
				String[] ppmNames = {"blue_bird.ppm", "bridge.ppm", "cat.ppm", "dog.ppm",
						"drink.ppm", "macaw.ppm", "monalisa.ppm", "rainbow_flower.ppm"
				};
				for(int index=0;index<ppmNames.length;index++) {
					System.out.println((index+1)+": " + ppmNames[index]);
				}
				System.out.println("Enter index ");
				userChoices = input.nextLine();
				p = new PPMImage(ppmNames[Integer.valueOf(userChoices)-1]);
				break;
			case 2:
				System.out.println("Enter file name: ");
				String fileName = input.nextLine();
				p.writeImage(fileName);
				break;
			case 3:
				p.sepia();
				System.out.println();
				break;
			case 4:
				p.grayscale();
				System.out.println();
				break;
			case 5:
				p.negative();
				System.out.println();
				break;
			}
		} while (Integer.valueOf(userChoices)!=0);		
		input.close();
	}

}
