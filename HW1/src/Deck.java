// A collection of five cards
import java.util.Random;

import javax.swing.JOptionPane;

public class Deck
{
	// There are 52 unique cards in each deck
	Card[] cards;
	
	// The index of the card that is not distributed yet
	// It is initially zero, because no card is initially
	// distributed
	private static int currentCard = 0;
	
	// There are 52 cards in a deck. I do not use Joker
	private static final int size = 52;
	
	// Number of times two random cards in the deck
	// must be swapped
	private static final int shufflingFrequency = 1024 * 1024;
	
	public Deck()
	{
		createNewDeck();
	} // end constructor
	
	public void createNewDeck()
	{
		cards = new Card[size];
		
		// Initially the deck is sorted
		for( int i = 0; i < size; i++ )
		{
			cards[i] = new Card();
			cards[i].buildFromNumber( i );
		} // end for
		
		// shuffle the deck. There is no use for
		// a sorted deck
		shuffle();		
		
	} // end method createNewDeck
	
	public Card[] giveMeFiveRandomCards()
	{
		Card[] fiveCards = new Card[5];
		
		// After each round of the game, ten cards are distributed.
		// These ten cards are the first ten cards in the deck.
		// Deck must be re-shuffled after each round
		if( currentCard >= 47 )
		{
			JOptionPane.showMessageDialog( null , "Deck is out of cards! Do not forget that " + 
					"you must shuffle the deck after each round.\n" +
					"I am goining to shuffle it now. But it might cause problems.\n" +
					"For example, there might be two cards with the same suit and face!\n" );
			shuffle();
		}
		
		// Assign the next five available cards
		for( int i = 0; i < 5; i++, currentCard++ )
		{
			fiveCards[i] = new Card();
			fiveCards[i] = cards[currentCard];
		}
		
		return fiveCards;
	} // end method giveMeFiveRandomCards
	
	public void shuffle()
	{
		Random generator = new Random();
		currentCard = 0;
		
		// Select two cards randomly and swap them
		// Repeat this process 'shufflingFrequency' times
		for( int i = 0; i < shufflingFrequency; i++ )
		{
			int randomCard1 = generator.nextInt( 52 );
			int randomCard2 = generator.nextInt( 52 );
			
			randomCard1 = Math.abs( randomCard1 );
			randomCard2 = Math.abs( randomCard2 );
			swap( randomCard1, randomCard2 );
		} // end for
		
	} // end method shuffle
	
	private void swap( final int i, final int j )
	{
		Card temp = new Card();
		
		if( i < 0 || i >= 52 )
		{
			JOptionPane.showMessageDialog( null , "Error in Deck class function Swap. Out of range index.\n" );
			return;
		}
		
		if( j < 0 || j >= 52 )
		{
			JOptionPane.showMessageDialog( null , "Error in Deck class function Swap. Out of range index.\n" );
			return;
		}
		
		temp = cards[i];
		cards[i] = cards[j];
		cards[j] = temp;		
	} // end utility method swap
	
	public void print()
	{
		String message = new String();
		
		for( int i = 0; i < size; i++ )
		{
			message += cards[i].getName();
			message += "\n";
		}
		
		JOptionPane.showMessageDialog( null, message );
		
	} // end method print
	
} // end method Deck
