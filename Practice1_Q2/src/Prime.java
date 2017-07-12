// CSI213 - Practice1 - Q2
// This program receives a number from the user and
// prints all the prime numbers up to that number
// It uses Trial Division algorithm

import javax.swing.JOptionPane;

public class Prime 
{
	
	static boolean isPrime( final int number )
	{
		// We only to check numbers up to square root of n
		int squareRoot;
				
		// Any number less than or equal to 1 is not prime
		// by definition
		if( number <= 1 )
			return false; 
		
		// Two is prime
		if( number == 2 )
			return true;
		
		// Any even number other than 2 is not prime. This 
		// if allows us to increase i two at a time, thereby
		// significantly improve the performance
		if( number % 2 == 0)
			return false;
		
		// Doing it out of the for loop improves the performance
		// because square root is a costly operation
		squareRoot = ( int ) Math.sqrt( ( double ) number );
		
		for( int i = 3; i <= squareRoot; i += 2 )
			if( number % i == 0 )
				return false;
		
		return true;			
		
	} // end isPrime
	
	static void printAllPrimes( final int number )
	{
		// Used for formatting
		String message;
		int counter;
		
		message = "";
		counter = -1;
		
		// Check to see if a number is prime
		// if so, print it
		for( int i = 2; i <= number; i++ )
			if( isPrime( i ) == true )
			{
				message += String.format( "%04d   ",  i );
				counter++;
				
				// This is just for formatting purposes
				if( counter % 10 == 9 )
					message += "\n";					
			}
		
		JOptionPane.showMessageDialog( null, message );
	} // end printAllPrimes
	
	public static void main( String[] args )
	{
		int number;
		String numberAsString;
		
		numberAsString = JOptionPane.showInputDialog( "Please Enter an Integer" );
		number = Integer.parseInt(  numberAsString );
		printAllPrimes( number );		
	} // end main
	
} // end of class Prime
