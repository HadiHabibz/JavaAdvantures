// CSI 321 - Practice1 - Q1
// This program receives two lines and finds
// their intersecting points
import javax.swing.JOptionPane;

public class Line 
{
	// A default constructor
	public Line( )
	{
		slope = 0;
		intercept = 0;
	} // end Line( void )
	
	// Overload constructor
	public Line( final double a, final double b )
	{
		slope = a;
		intercept = b;
	} // end Line
	
	// Overload constructor
	// The 'equation' must be in this format:
	// y = ax + b
	// Examples: y = 2 x + 3 or y = 4x or y = x
	public Line( final String equation )
	{
		int i;
		String string;
		string = "";
		
		// Skip everything before '='
		for( i = 0; equation.charAt( i ) != '='; i++ )
			;
		
		// Skip '='
		i++;
		
		// Everything after '=' and before 'x' is the slope
		// Skip all white spaces
		for( ; equation.charAt( i ) != 'x'; i++ )
		{
			if( equation.charAt( i ) == ' ' )
				continue;
			
			string += equation.charAt( i );
		}
		
		// If there were nothing between '=' and 'x'
		// then the slope is 1
		if( string.isEmpty() == true )
			string = "1";
		
		// Convert string to double
		slope = Double.parseDouble( string );
		
		// Skip the 'x' and reset 'string'
		i++;
		string = "";
		
		// Everything after the 'x' is the intercept with y axis
		for( ; i < equation.length( ); i++ )
		{
			if( equation.charAt( i ) == ' ' )
				continue;
			
			string += equation.charAt( i );
		}
		
		// If there is no intercept, it means that the intercept
		// is 0
		if( string.isEmpty() == true )
			string = "0";
		
		intercept = Double.parseDouble( string );
	} // end Line
	
	public double getSlope( )
	{
		return slope;
	} // end getSlope
	
	public double getIntercept( )
	{
		return intercept;
	} // end getIntercept
	
	public void setSlope( final double slope )
	{
		this.slope = slope;
	} // end setSlope
	
	public void setIntercept( final double intercept )
	{
		this.intercept = intercept;
	} // end setIntercept
	
	public void print( )
	{
		JOptionPane.showMessageDialog( null, "y = " + slope + "x + (" + intercept + ")" );
	} // end print
	
	// Find the point at which this line intersects with 'line'
	public void intersect( final Line line )
	{
		double x;
		double y;
		
		if( slope - line.slope == 0 )
		{
			JOptionPane.showMessageDialog( null, "The lines never intercept" );
			return;
		}
		
		x = ( this.intercept - line.intercept ) / ( line.slope - this.slope );
		y = this.slope * x + this.intercept;
		JOptionPane.showMessageDialog( null, "Two lines intercep at: (" + x + "," + y + ")");
	} // end intersect
	
	private double slope;
	private double intercept;
	
	public static void main( String[] args )
	{
		String equation1 = new String( );
		String equation2 = new String( );
		
		//equation1 = JOptionPane.showInputDialog( "Please enter the first equation: " );
		//equation2 = JOptionPane.showInputDialog( "Please enter the second equation: " );
		
		//Line line1 = new Line( equation1 );
		//Line line2 = new Line( equation2 );
		
		Line line1 = new Line();
		Line line2 = new Line();
		
		line1.print( );
		line2.print( );	
		
		line1.intersect( line2 );		
	} // end main
	
} // end class Line
