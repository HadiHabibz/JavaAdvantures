// CSI 213 - Lab1
// This is a Sudoku generator
// It does not allow the user to solve it
// It just creates it.
// 07/12/2017 - Albany, NY - Hadi

import javax.swing.JOptionPane;
import java.util.Random;

public class Sudoku 
{
	// This is the 9 by 9 table 
	private int[][] grid;
	
	// Generate unique puzzles for each ID
	// User inputs the ID
	private int ID;
	
	// The number of elements that must be deleted
	// from the gird. Increasing this variable increases
	// the difficulty of the game
	private int deleteThisMany;
	
	// Random number generator engine, used to create
	// unique tables based on ID
	private Random generator;
	
	// Constructor sets all values including the grid to zero
	public Sudoku( )
	{
		ID = 0;
		grid = new int[9][9];
		deleteThisMany = 0;
		
		letUserChooseID();
		
		// Use ID as the seed
		generator = new Random( ( long ) ID );
		
		for( int i = 0; i < 9; i++ )
			for( int j = 0; j < 9; j++ )
				grid[i][j] = 0;
		
	} // end constructor
	
	// Print the data on a pop up window in the most primitive
	// way. Prints 0 for numbers that are hidden.
	public void print( )
	{
		String puzzle = new String( );
		puzzle = "";
				
		for( int i = 0; i < 9; i++ )
		{
			for( int j = 0; j < 9; j++ )
			{
				// This if statement is not required, if we want to use
				// 0s. Otherwise, it is required
				if( grid[i][j] == 0 )
				{
					puzzle += "0";
					// puzzle += " ";
				} // end if
				
				else
					puzzle += grid[i][j];
				
				puzzle += "       ";
			} // end for( j )
			
			puzzle += "\n";
			
		} // end for( i )
		
		JOptionPane.showMessageDialog( null,  puzzle );
		
	} // end method print
	
	// Load a pre-defined puzzle into the grid
	public void initialize( )
	{
		final int[][] sample = { 
				{1, 2, 3, 4, 5, 6, 7, 8, 9},
				{2, 3, 1, 5, 6, 4, 8, 9, 7},
				{3, 1, 2, 6, 4, 5, 9, 7, 8},
				{4, 5, 6, 7, 8, 9, 1, 2, 3},
				{5, 6, 4, 8, 9, 7, 2, 3, 1},
				{6, 4, 5, 9, 7, 8, 3, 1, 2},
				{7, 8, 9, 1, 2, 3, 4, 5, 6},
				{8, 9, 7, 2, 3, 1, 5, 6, 4},
				{9, 7, 8, 3, 1, 2, 6, 4, 5}				
		};
		
		for( int i = 0; i < 9; i++ )
			for( int j = 0; j < 9; j++ )
				grid[i][j] = sample[i][j];
		
	} // end method initialize
	
	// Create a new puzzle by shuffling the template
	// The number of replacements and shuffling is 
	// determined based on ID
	public void randomize( )
	{
		// Total number of shuffling
		long numberOfIterations;
		
		// In every iteration these two numbers must
		// be swapped, whenever they occur in the gird
		int digit1;
		int digit2;
		
		numberOfIterations = giveMeARandomNumber( 100000, 1000000, generator );
		
		for( int i = 0; i < numberOfIterations; i++ )
		{
			digit1 = giveMeARandomNumber( 1, 9, generator );
			digit2 = giveMeARandomNumber( 1, 9, generator );
			
			if( digit1 == digit2 )
				continue;
			
			swap( digit1, digit2 );
		} // end for( i )
		
	} // end method randomize
	
	// Delete (or hide) some random elements in the gird
	// The number of elements that must be deleted are determined
	// by difficulty. The elements are chosen based on ID
	public void eliminate( )
	{
		// Keep track of number of elements you have deleted so far
		int deletedSoFar;
		
		// Location of a randomly selected element (to delete it)
		int randomRow;
		int randomCol;
		
		letUserChooseDifficulty( );
		deletedSoFar = 0;
				
		while( deletedSoFar < deleteThisMany )
		{
			randomRow = giveMeARandomNumber( 0, 8, generator );
			randomCol = giveMeARandomNumber( 0, 8, generator );
			
			// If the element is already deleted, try again and choose
			// another element
			if( grid[randomRow][randomCol] == 0 )
				continue;
			
			// Delete the element
			grid[randomRow][randomCol] = 0;
			deletedSoFar++;
			
		} // end while
				
	} // end method eliminate
	
	// Ask the user to select a difficulty
	public void letUserChooseDifficulty( )
	{
		String difficulty = new String( "" );
				
		// Keeping asking until the user inputs the right answer
		do
		{
			difficulty = JOptionPane.showInputDialog( null,  "Plese choose the difficulty of the game: " );
			difficulty = difficulty.toLowerCase( );
			
			if( difficulty.contains( "solved" ) ||  difficulty.contains( "1" ) )
				deleteThisMany = 0;
			
			else if(  difficulty.contains( "very easy" ) || difficulty.contains( "veryeasy" ) || difficulty.contains( "2" ) )
				deleteThisMany = 18;
			
			else if( difficulty.contains( "easy" ) ||  difficulty.contains( "3" ) )
				deleteThisMany = 27;
			
			else if( difficulty.contains( "normal" ) ||  difficulty.contains( "4" ) )
				deleteThisMany = 36;
			
			else if( difficulty.contains( "hard" )  ||  difficulty.contains( "5" ))
				deleteThisMany = 45;
			
			else if( difficulty.contains( "veryhard" ) || difficulty.contains( "very hard" ) ||  difficulty.contains( "6" ) )
				deleteThisMany = 63;
			
			else
			{
				JOptionPane.showMessageDialog( null, "Invalid difficulty. It must be one of the followings:\n"
						+ "1. Solved\n2. Very Easy\n3. Easy\n4. Normal\n5. Hard\n6. Very Hard\nPress OK to try agian" );
				continue;
			} // else
			
			break;
			
		} while( true );		
	
	} // letUserChooseDifficulty
	
	// Swap all digit1s in the grid with digit2 and all difit2s with digit1
	private void swap( final int digit1, final int digit2 )
	{
		for( int i = 0; i < 9; i++ )
			for( int j = 0; j < 9; j++ )
			{
				if( grid[i][j] == digit1 )
					grid[i][j] = digit2;
				
				else if( grid[i][j] == digit2 )
					grid[i][j] = digit1;
			}
		
	} // end utility method
	
	private void letUserChooseID( )
	{
		String idString = new String( "" );
		
		while( ID == 0 )
		{
			idString = JOptionPane.showInputDialog( "Please enter the ID (from 1 to 10,000,000): " );
			setID( ( int ) Integer.parseInt( idString ) );			
		} // end while
		
	} // end method letUserChooseID
	
	public void setID( final int ID )
	{
		this.ID = ( ID >= 1 && ID <= 10000000 ) ? ID : 0 ;
	} // end utility method setID
	
	// Give me a random number from lowerBound to upperBound (including both)
	private int giveMeARandomNumber( final int lowerBound, final int upperBound, final Random generator )
	{
		
		int randomNumber;
		
		randomNumber = generator.nextInt( );
		randomNumber = Math.abs( randomNumber );
		randomNumber %= ( upperBound - lowerBound + 1 );
		randomNumber += lowerBound;
		
		return randomNumber;
	} // end utility method giveMeARandomNumber
	
	// Give me a game to play. 
	public void launch( )
	{
		initialize();
		randomize( );
		eliminate();
		print();
	} // end method launch
	
	public static void main( String[] args )
	{
		Sudoku game = new Sudoku( );
		game.launch();
		
	} // end function main
	
} // end class Sudoku
