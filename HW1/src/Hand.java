// Here are the base values as reference
// Straight Flush: 1200
// Four of A Kind: 1000
// Full House: 420
// Flush: 400
// Straight: 350
// Three of A Kind: 330
// Two Pair: 30
// One Pair: 14
// High Card: 0

public class Hand 
{
	/// There are five cards in each hand
	private Card[] cards;
	
	// Better hands get higher value
	private int value;
	
	private String status;
	
	// Just to improve readability
	private static final int numberOfCardsInHand = 5;
	
	public Hand()
	{
		// Allocate memory for cards and call the 
		// constructor for each one
		cards = new Card[numberOfCardsInHand];
		
		for(int i = 0; i < numberOfCardsInHand; i++ )
			cards[i] = new Card();
		
		// Set the value to an invalid amount
		value = -1;
		
		status = new String();
	} // end method Hand()
	
	public void buildHand( final Deck deck )
	{
		// Get five random cards from the deck
		cards = deck.giveMeFiveRandomCards();
		
		// Find the value of the cards you just got 
		evaluateHand();
	} // end method buildHand
	
	public String printToString()
	{
		String message = new String();
		
		// Print the cards, one card in a line
		for( int i = 0; i < numberOfCardsInHand; i++ )
		{
			message += cards[i].getName();
			message += " ";
		}
		
		message += "\nStatus: ";
		message += status;
		message +=  "\nHand Value: ";
		message += value;
		message += "\n";
		return message;
		
	} // end method print
	
	// Sort the hand in an ascending order
	private void sortHand()
	{	
		// Used for swapping purposes
		Card temp = new Card();
		
		// Selection sort
		for( int i = 0; i < numberOfCardsInHand; i++ )
		{
			int minIndex = i;
			
			for( int j = i+1; j < numberOfCardsInHand; j++ )
				if( cards[minIndex].getFace() > cards[j].getFace() )
					minIndex = j;
			
			temp = cards[minIndex];
			cards[minIndex] = cards[i];
			cards[i] = temp;
			
		} // end for( i )
	} // end utility method sortHand
	
	// Find the value of the hand
	public void evaluateHand()
	{
		// The faces of each card in the hand are saved here
		int[] faces = new int[numberOfCardsInHand];
		
		// This is crucial. Otherwise, this algorithm does not work
		sortHand();
		
		// Copy the face of each card in the hand
		for( int i = 0; i < numberOfCardsInHand; i++ )
			faces[i] = cards[i].getFace();
		
		// Reset status
		status = "";
		
		// Also take care of flush and straight
		value = checkStraightFlush( faces );
		
		// If it is straight flush, or flush, or straight
		// then return
		if ( value != 0 )
			return;
		
		// Check if the hand is four of a kind
		value = checkFourOfAKind( faces );
		
		if( value != 0 )
			return;		
		
		// Function above changes the values of faces
		// Restore the original values
		for( int i = 0; i < numberOfCardsInHand; i++ )
			faces[i] = cards[i].getFace();
		
		// Check if its a full house
		value = checkFullHouse( faces );
		
		if( value != 0 )
			return;
		
		// Check if it is a three of a kind
		value = checkThreeOfAKind( faces );
		
		if( value != 0 )
			return;
		
		// Check if it is a two pair
		value = checkTwoPair( faces );
		
		if( value != 0 )
			return;		
		
		// Also takes care of high card
		value = checkPair( faces );
				
	} // end utility method evaluateHand
	
	private int checkPair( final int[] faces )
	{
		int pair = 0;
		
		for( int i = 1; i < numberOfCardsInHand; i++ )
			if( faces[i-1] == faces[i] )
			{
				pair = faces[i];
				break;
			}
		
		// It is high card then
		if( pair == 0 )
		{
			status = "High Card";
			return faces[numberOfCardsInHand-1];
		}
		
		status = "Pair";
		// 14 is the base
		return ( 14 + pair );
	} // end utility method checkPair
	
	private int checkTwoPair( final int[] faces )
	{
		int highPair = 0;
		int lowPair = 0;
		
		// In two pair, there is only one card, which is not in a pair
		// This card can be in the end
		if( faces[0] == faces[1] && faces[2] == faces[3] )
		{
			highPair = faces[2];
			lowPair = faces[0];
		}
		
		// or it can be in the beginning 
		else if( faces[1] == faces[2] && faces[3] == faces[4] )
		{
			highPair = faces[3];
			lowPair = faces[1];
		}
		
		// or it can be in the middle
		else if( faces[0] == faces[1] && faces[3] == faces[4] )
		{
			highPair = faces[3];
			lowPair = faces[0];
		}
		
		if( highPair == 0 )
			return 0;
		
		status = "Two Pair";
		
		// 30 is the base. The high pair is more important than 
		// the lower pair, therefore, its coefficient is higher
		return ( 30 + 20 * highPair + lowPair );
	} // end utility method checkTwoPair
	
	private int checkThreeOfAKind( final int[] faces )
	{
		int tripleValue = 0;
		
		// Check if there are three consecutive cards with the same
		// face. If so, save the face
		for( int i = 0; i < numberOfCardsInHand-2; i++ )
			if( faces[i] == faces[i+1] && faces[i] == faces[i+2] )
			{
				tripleValue = faces[i];
				break;
			}
		
		// If face is still zero, it means that it is not three of a kind
		if( tripleValue == 0 )
			return 0;
		
		status = "Three of A Kind";
		
		// 330 is the base of three of a kind
		return ( 330 + tripleValue );
	} // end utility method checkThreeOfAKind
	
	private int checkFullHouse( final int[] faces )
	{
		int threeOfAKind;
		int pair;
		
		// If the last two cards or the first two cards are not the same, then it cannot be
		// a full house
		if( faces[0] != faces[1] || faces[numberOfCardsInHand-1] != faces[numberOfCardsInHand-2] )
			return 0;
		
		
		// This means that the first two cards in the hand form a pair
		// and the last three form a three of a kind
		if( faces[2] == faces[numberOfCardsInHand-1] )
		{
			threeOfAKind = faces[2];
			pair = faces[0];
		}
		
		// This means that the first three cards in 
		// the hand form a three of a kind and the 
		// last two form a pair
		else if( faces[2] == faces[1] )
		{
			threeOfAKind = faces[0];
			pair = faces[numberOfCardsInHand-1];
		}
		
		// It is not a full house then
		// It is a two pair 
		else 
			return 0;
		
		status = "Full House";
		
		// 420 is the base value of three of a kind
		// The three of a kind portion of the full house
		// is more important than the pair portion
		// Therefore, its coefficient is higher
		return ( 420 +  20 * threeOfAKind + pair );
			
	} // end utility method checkFullHouse

	private int checkFourOfAKind( final int[] faces )
	{
		// If it is four of a kind, this if...else structure must
		// make it five of kind (although it is not possible).
		// This works because faces is already sorted. 
		if( faces[0] != faces[1] )
			faces[0] = faces[1];
		
		else if( faces[numberOfCardsInHand-1] != faces[numberOfCardsInHand-2] )
			faces[numberOfCardsInHand-1] = faces[numberOfCardsInHand-2];
		
		// Check if its is five of a kind
		for( int i = 1; i < numberOfCardsInHand; i++ )
			if( faces[0] != faces[i] )
				return 0;
		
		status = "Four of a Kind";
		
		// 1000 is the base value of four of a kind. 
		// Add the value of one of the card to get
		// the actual value of the hand. Note that
		// because of the if...else above, we are sure
		// that faces[0] is the same as the face of 
		// other four cards
		return ( 1000 + faces[0] );
	} // end utility method checkFourOfAKind
	
	private int checkStraightFlush( final int[] faces )
	{
		int flushValue;
		int straightvalue;
		
		flushValue = checkFlush( faces );
		straightvalue = checkStraight( faces );
		
		// If the hand is a flush and is a straight, then it is a straight flush
		// 1200 is the base value of straight flush. The value of the highest 
		// card in the hand is then added to this base value to calculate the
		// actual value
		if( checkFlush( faces ) != 0 && checkStraight( faces ) != 0 )
		{
			status = "Straight Flush!!!";
			return ( 1200 + faces[numberOfCardsInHand-1] );
		}
		
		if( flushValue != 0 )
		{
			status = "Flush";
			return flushValue;
		}
		
		status = "Straight";
		
		return straightvalue;
	} // end utility method checkStraightFlush
	
	private int checkFlush( final int[] faces )
	{
		// Check if all cards have the same suit
		for( int i = 1; i < numberOfCardsInHand; i++ )
			if( cards[0].getSuit() != cards[i].getSuit() )
				return 0;
		
		// 400 is the base value of flush
		// The value of the highest card is added to base value
		// to get the actual value
		return ( 400 + faces[numberOfCardsInHand-1] );
	} // end utility method checkFlush
	
	private int checkStraight( final int[] faces )
	{
		// Check if we have consecutive cards
		// Cards are already sorted in ascending order
		for( int i = 1; i < numberOfCardsInHand; i++ )
			if( faces[i-1] - faces[i] != -1 )
				return 0;
		
		// 350 is the base value of straight. The value
		// of the highest card in the hand is then added
		// to the base value to get the actual value
		return ( 350 + faces[numberOfCardsInHand-1] );		
	} // end utility method checkStraight
	
	public int getValue()
	{
		return value;
	} // end method getValue
	
} // end class Hand
