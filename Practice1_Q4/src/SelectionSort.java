public class SelectionSort extends Sort 
{
	// Call the superclass constructor
	public SelectionSort() 
	{
		super( );
	} // end constructor
	
	// Call the superclass constructor
	public SelectionSort( final int size )
	{
		super( size );
	} // end constructor
	
	// Sort data using selection sort algorithm
	public void sortData( )
	{
		// The location of the smallest number
		int indexMin;
		
		// Find the i-th smallest number 
		for( int i = 0; i < size; i++ )
		{
			// Assume the current number is the lowest
			indexMin = i;
			
			// Check if the the above assumption is right
			// If not, change indexMin
			for( int j = i; j < size; j++ )
				if( data[j] <  data[indexMin] )
					indexMin = j;
			
			// Put the i-th smallest number in its location
			int holder = data[i];
			data[i] = data[indexMin];
			data[indexMin] = holder;
			
		} // end for( i )
				
	} // end sortData
	
} // end class SelectionSort
