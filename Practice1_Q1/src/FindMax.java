// ICEN213 - Practice1 - Practice program 1
// Wrote on 07/10/2017 - Hadi Habibzade - Albany NY
// This program receives bunch of number from
// the user. The user specifies the end of data
// by entering -1. The program then finds the 
// two largest numbers among the input data.

// Import GUI stuff
import javax.swing.JOptionPane;

public class FindMax
{
	public static void main( String[] inputArgs )
	{
		int number;
		String stringNumber;
		
		// max1 is the maximum number
		int max1;
		
		// max2 is the maximum number excluding  max1
		int max2;
		
		
		max1 = -1;
		max2 = -1;
		
		do
		{
			// Receive the input from the user and convert it to the integer
			stringNumber = JOptionPane.showInputDialog( "Enter a positive integer (enter -1 to exit): " );
			number = Integer.parseInt(  stringNumber );
			
			// If it is the last number, break the loop
			if( number == -1 )
				break;
		
			// Check if the new input should be the max or not
			if( number > max1 )
			{
				max2 = max1;
				max1 = number;
			}
			
			// Check if the new input should be the second max or not
			else if( number > max2 )
				max2 = number;
		
		} while( true );
		
		// Show the results
		JOptionPane.showMessageDialog( null,  "Max 1 = " + max1 + "\nMax 2 = " + max2 );
		
	} // end function main
	
} // end class FindMax
