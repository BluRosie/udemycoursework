package apcsudemy;

import java.util.Scanner;

/**
 * 
 * @author Nathan
 *
 * quick gathering thoughts here
 * args[0] refers to first argument
 *
 */

public class SumNumbers {
	
	private static Scanner input;
	
	public static void main(String[] args)
	{
		input = new Scanner(System.in);
		
		System.out.print("Enter an integer:");
		int a = input.nextInt();
		System.out.print("Enter another integer:");
		int b = input.nextInt();
		
		int sum = a + b;
		
		System.out.print("Adding together " + a + " and " + b + " nets " + sum);
	}
}
