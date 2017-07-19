// A class the represent a card

import javax.swing.JOptionPane;

public class Card 
{
	// ID is a number from 0 to 51
	private int ID;
	
	// Suit is a number from 1 to 4
	private int suit;
	
	// face is a number from 2 to 14 
	private int face;
	
	// Card name
	String name;
	
	// Just for code clarity 
	private static final int spades = 0;
	private static final int hearts = 1;
	private static final int diamonds = 2;
	private static final int clubs = 3;
	
	// Set everything to invalid variables
	// Issue an error instead of creating an 
	// unwanted card
	public Card()
	{
		// Fill variables with invalid numbers
		ID = -1;
		suit = 0;
		face = 0;
		name = new String();
	} // end constructor
	
	public Card( final int ID )
	{
		this.ID = 0;
		this.suit = 0;
		this.face = 0;
		name = new String();
		
		buildFromNumber( ID );
	} // end overloaded constructor
	
	private void determineSuit( )
	{
		// There are 14 cards in each suit
		// ID must be divided by 13 because it begins
		// form zero
		switch( ID / 13 )
		{
		case  spades:
			suit = spades;
			break;
			
		case hearts:
			suit = hearts;
			break;
			
		case diamonds:
			suit = diamonds;
			break;
			
		case clubs:
			suit = clubs;
			break;
			
		default:
			JOptionPane.showMessageDialog( null , "Something went wrong. Cannot determine the suit.\n" );
			break;
		} // end switch
		
	} // end method determinesSuit
	
	private void determineFace()
	{	
		// Create a number from 2 to 14
		// Ace is 14 not 1
		this.face = ( ID % 13  ) + 2;
	} // end method determineFace
	
	// Find the suit and the face based on ID
	public void buildFromID()
	{
		// Get rid of the current name
		// and assign a new name
		name = "";
		
		// Invalid ID. This should never happen.
		if( ID < 0 )
		{
			JOptionPane.showMessageDialog( null , "Cannot build the card. Invalid ID.\n" );
			return;
		}
		
		// Find the suit based on ID
		determineSuit();
		
		// Find the face based on ID
		determineFace();
		
		// Set the name based on suit and face
		setName();
		
	} // end method buildFromID
	
	// Receive a number and create a new card based on that
	public void buildFromNumber( final int number )
	{
		setID( number );
		buildFromID();
	} // end method buildFromNumber
	
	// If ID is out of bound, we want to get an error instead
	// creating a possibly wrong card
	public void setID( final int ID )
	{
		this.ID = ( ID >= 0 && ID <= 51 ) ? ID : -1;
	} // end method setID
	
	public void setSuit( final int suit )
	{
		this.suit = ( suit >= 1 && suit <= 4 ) ? suit : 0;
	} // end method setSuit
	
	public void setFace( final int face )
	{
		this.face = ( face >= 2 && face <= 14 ) ? face : 0;
	} // end method setFace
	
	public int getID()
	{
		return ID;
	} // end method getID
	
	public int getSuit()
	{
		return suit;
	} // end method getSuit
	
	public int getFace()
	{
		return face;
	} // end method getFace
	
	public void print()
	{
		
		if( ID == - 1 )
		{
			JOptionPane.showMessageDialog( null , "Empty card. You have forgotten to initialize this card.\n" );
			return;
		}
		
		JOptionPane.showMessageDialog( null , getName() );
	} // end method print
	
	public String getName()
	{
		return name;
	} // end method getName
	
	public void setName()
	{		
		if( face <= 10 )
			name += face;
		
		else if( face == 11 )
			name += "J";
		
		else if( face == 12 )
			name += "Q";
		
		else if( face == 13 )
			name += "K";
		
		else if( face == 14 )
			name += "A";
		
		switch( this.suit )
		{
		case spades:
			name += "S";
			break;
			
		case diamonds:
			name += "D";
			break;
			
		case clubs:
			name += "C";
			break;
			
		case hearts:
			name += "H";
			break;
			
		default:
			JOptionPane.showMessageDialog( null , "Failed to set the card name. Not sure what the suit is.\n" );
			break;
		} // end switch
		
	} // end method getCard	
	
} // end class Card
