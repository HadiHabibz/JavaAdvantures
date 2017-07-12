// CSI 213 - Practice1 - Q5
// Matrix Multiplication
// This program multiplies two matrices
public class Matrix
{
	private double[][] data;
	private int rows;
	private int cols;
	private static final int maxNumber = 10;
	private static final int minNumber = 0;
	
	public Matrix( final int rows, final int cols )
	{
		this.rows = ( rows >= 1 ) ? rows : 1;
		this.cols = ( cols >= 1 ) ? cols : 1;
		allocateMemory( );
		resetData( );
	} // end constructor
	
	// Allocate space for a two dimensional array
	public void allocateMemory( )
	{
		data = new double[rows][];
		
		for( int i = 0; i < rows; i++ )
			data[i] = new double[cols];
	} // end allocateMemory
	
	// Set all elements to zero
	public void resetData( )
	{
		for( int i = 0; i < rows; i++ )
			for( int j = 0; j < cols; j++ )
				data[i][j] = 0;
	} // end resetData
	
	// Populate the matrix with some random numbers
	public void randomize( )
	{
		double randomNumber;
		
		for( int i = 0; i < rows; i++ )
			for( int j = 0; j < cols; j++ )
			{
				// Random numbers must be between minNumber and maxNumber
				randomNumber = minNumber + Math.random() * ( maxNumber - minNumber );
				randomNumber = Math.floor( randomNumber );
				data[i][j] = randomNumber;
			} // end for( j )
		
	} // end randomize
	
	// Print the matrix
	public void print( )
	{
		for( int i = 0; i < rows; i++ )
		{
			
			for( int j = 0; j < cols; j++ )
				System.out.print( data[i][j] + "  " );
			
			System.out.print( "\n" );
			
		} // end for( i )
		
	} // end print
	
	// Multiply two matrices
	public Matrix multiply( final Matrix multiplier )
	{
		// Create a matrix for result
		// Make sure dimensions are right
		Matrix result = new Matrix( this.rows, multiplier.cols );
		
		// If the dimensions do not match, terminate the operaiton
		if( this.cols != multiplier.rows)
		{
			System.out.println( "Cannot multiply these two matrices." );
			return result;
		}
		
		// Multiply two matrices
		for( int i = 0; i < this.rows; i++ )
			for( int j = 0; j < multiplier.cols; j++ )
				for( int k = 0; k < this.cols; k++ )
					result.data[i][j] += data[i][k] * multiplier.data[k][j];
		
		return result;
	} // end function multiply
	
	public static void main( String[] args )
	{
		Matrix matrix1 = new Matrix( 2, 3 );
		Matrix matrix2 = new Matrix( 3, 5 );
		Matrix result = new Matrix( 1, 1 );
		matrix1.randomize( );
		matrix2.randomize( );
		matrix1.print( );
		matrix2.print( );
		result = matrix1.multiply( matrix2 );
		result.print(); 
	} // end main
}
