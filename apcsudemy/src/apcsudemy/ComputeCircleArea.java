package apcsudemy;

import java.util.Scanner;

public class ComputeCircleArea {
	
	private static Scanner input;
	
	public static void main(String[] args)
	{
		input = new Scanner(System.in);
		System.out.print("Enter a number to be the radius: ");
		
		double radius = input.nextDouble();
		double area;
		
		area = radius * radius * MATH.Pi;
		System.out.print("The area for circle with radius " + radius + " is " + area + ".");
	}
}
