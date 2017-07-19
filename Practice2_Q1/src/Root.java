import javax.swing.JOptionPane;

public class Root
{
	private float number;
	private float guess;
	private float precision;
	
	public Root()
	{
		number = 0;
		guess = 0;
		precision = 0;
	} // end constructor
	
	// Babylonian algorithm
	public float squareRoot( final float number )
	{
		float square;
		
		guess = 0.5F * number;
		square = guess * guess;
		
		if( Math.abs( this.number - square ) <= precision )
			return guess;
		
		return squareRoot( guess + ( this.number / guess ) );
	} // end method squareRoot
	
	public void printSquareRoot()
	{
		float result = squareRoot( number );
		
		JOptionPane.showMessageDialog( null, "The answer is " + result );
	} // end method printSquareRoot
	
	public void userPrompt() 
	{
		String input = new String();
		
		input = JOptionPane.showInputDialog( "Please enter n: " );
		setNumber( ( float ) Float.parseFloat( input ) );
		input = JOptionPane.showInputDialog( "Please enter precision: " );
		setPrecision( ( float ) Float.parseFloat( input ) );
		
	} // end method userPrompt
	
	public void setPrecision( final float precision )
	{
		this.precision = ( precision > 0F ) ? precision : 0F;
	} // end utility method setPrecision
	
	public void setNumber( final float number )
	{
		this.number = ( number >= 0F ) ? number : 0F;
	} // end method setNumber
	
	public static void main( String[] args )
	{
		Root root = new Root();
		root.userPrompt();
		root.printSquareRoot();
	} // end method main
	
} // end class Root
