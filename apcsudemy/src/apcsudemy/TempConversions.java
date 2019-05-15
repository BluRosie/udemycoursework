/*package apcsudemy;

import java.util.Scanner;

public class TempConversions {
	
	private static Scanner input;
	
	public static void main(String[] args)
	{
		input = new Scanner(System.in);
		System.out.print("What is your temperature scale? Supported are C, F, R, and K. ");
		char srcSystem = input.nextChar();
		System.out.print("What is your target temperature scale? Supported are C, F, R, and K. ");
		char targetSystem = input.nextChar();
		
		if (targetSystem == srcSystem)
		{
			System.out.print("No conversion necessary.");
			break;
		}
		else if ((srcSystem != C && srcSystem != F && srcSystem != R && srcSystem != K)
			|| (targetSystem != C && targetSystem != F && targetSystem != R && targetSystem != K))
		{
			System.out.print("One or both systems invalid.");
			break;
		}
		
		System.out.print("How many degrees in " + srcSystem + "?");
		double degrees = input.nextDouble();
		double result;
		
		switch (srcSystem) {
			case C:
				switch (targetSystem) {
					case F:
						
						break;
					case R:
						
						break;
					case K:
						result = (degrees + 273.15);
						break;
				break;
				}
			case F:
				switch (targetSystem) {
					case C:
						
						break;
					case R:
						
						break;
					case K:
						
						break;
				break;
				}
			case R:
				switch (targetSystem) {
					case C:
						
						break;
					case F:
						
						break;
					case K:
						
						break;
				break;
				}
			case K:
				switch (targetSystem) {
					case C:
						result = (degrees - 273.15);
						break;
					case F:
						
						break;
					case R:
						
						break;
				break;
				}
	
		System.out.print(degrees + " in the " + srcSystem + " system is " + result + " in the " + targetSystem + "system.");
	}
}
*/