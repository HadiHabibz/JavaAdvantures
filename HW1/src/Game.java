// This handles the sequence of event in the game
import javax.swing.JOptionPane;

public class Game 
{
	Player humanPlayer;
	Player computerPlayer;
	Deck deck;
	
	// Value of pot
	int pot;
	
	// Blind is the mandatory bet that players must make every
	// other round
	int blind;
	
	// Keep track of number of rounds played so far
	int round;
	
	// The value a player must call, if they want to stay in the
	// game
	int callAmount;
	
	public Game( final int bank, final int blind )
	{
		deck = new Deck();
		humanPlayer = new Player();
		computerPlayer = new Player();
		round = 0;
		pot = 0;
		callAmount = 0;
		this.setBank( bank );
		this.setBlind( blind );
	} // end constructor
	
	public void setBank( final int bank )
	{
		this.humanPlayer.setBank( bank );
		this.computerPlayer.setBank( bank ); 
	} // end method setBank
	
	// Make sure the value of blind makes sense as in within range
	public void setBlind( final int blind )
	{
		this.blind = ( blind >= 0 && blind <= humanPlayer.getBank() ) ? blind : 0; 				
	} // setBlind
	
	// Run the game. Divide the game to even and odd rounds
	// The sequence of event is very similar but some 
	// differences exist
	public void run()
	{		
		String message = new String();
		
		// In case, the programmer has forgotten to set the value of blind
		if( this.blind == 0 )
		{
			JOptionPane.showMessageDialog( null , "You have forgotten to initialize blind.\n", 
					( "# " + round + ": Pot(" + pot + "), bank(" + humanPlayer.getBank() +
							"), bank2(" + computerPlayer.getBank() + ")" ), JOptionPane.INFORMATION_MESSAGE );
			return;
		} // end if
		
		JOptionPane.showMessageDialog( null, "Welcome! Click on OK to begin the game. Each player has"
				+ " 1000$ initially. The blind value is 100$. In the first round, you bet blindly.\n");
		
		// If the bank of a player reaches below blind, that player is the looser
		while( humanPlayer.getBank() >= blind && computerPlayer.getBank() >= blind )
		{		
			// Shuffle deck, reset pot, and other stuff
			roundInitializations();
			
			// First pop-up window showing a player has already 
			// put something in the pot, before seeing their 
			// hand
			showFirstWindow( round % 2 );
			
			// If it is an odd hand, run the sequence of the odd hand
			if( round % 2 == 1 )
			{
				message = printHumanHandToString();
				JOptionPane.showMessageDialog( null,  message, ( "# " + round + ": Pot(" + pot + "), bank(" +
				humanPlayer.getBank() + "), bank2(" + computerPlayer.getBank() + ")" ),
						JOptionPane.INFORMATION_MESSAGE );
				runOddRound( true );
			}
		
			else 
				runEvenRound();
		
		} // end while
		
		endGameMessage();
		
	} // end method run
	
	private void showFirstWindow( final int isItOddRound )
	{
		// The game begins with one of the players adding the blind
		// to the pot, blindly; i.e., without seeing their hand
		pot += blind;
		callAmount = blind;
		
		// Human player is the blind in the odd rounds
		if( isItOddRound == 1 )
		{
			humanPlayer.bet( blind );
			JOptionPane.showMessageDialog( null, "You have put " + blind + "$ to the pot.\n",
					( "# " + round + ": Pot(" + pot + "), bank(" + humanPlayer.getBank() +
							"), bank2(" + computerPlayer.getBank() + ")" ), JOptionPane.INFORMATION_MESSAGE );
		} // end if
		
		// AI is the blind in the even rounds
		else 
		{
			computerPlayer.bet( blind );
			JOptionPane.showMessageDialog( null, "Computer has put " + blind + "$ to the pot.\n",
					( "# " + round + ": Pot(" + pot + "), bank(" + humanPlayer.getBank() + 
							"), bank2(" + computerPlayer.getBank() + ")" ), JOptionPane.INFORMATION_MESSAGE );
		} // end else
				
	} // end utility method showFirstWindow
	
	// Execute the sequence of the odd rounds
	private void runOddRound( final boolean letComputerRaise )
	{
		int computerBet;
	
		// See how much the AI is willing to bet
		computerBet = getComputerBet();
		
		if( computerBet >= callAmount && letComputerRaise == false )
			computerBet = callAmount;
		
		// Deduct the computer's bet from its bank and
		// add it to the pot
		computerPlayer.bet( computerBet );
		pot += computerBet;
		
		// Computer can fold by specifying that is willing
		// to bet zero amount
		if( computerBet == 0 )
		{
			computerFolds();
			return;
		} // end if
		
		if( computerBet == callAmount )
		{			
			computerCalls( computerBet );
			return;
		} // end if
				
		// Run this sequence if computer raises
		callAmount = computerBet - callAmount;
		JOptionPane.showMessageDialog( null,  "Computer raises by " + callAmount + ".\nPot: " + pot + "$\n",
				( "# " + round + ": Pot(" + pot + "), bank(" + humanPlayer.getBank() + "),"
						+ " bank2(" + computerPlayer.getBank() + ")" ), JOptionPane.INFORMATION_MESSAGE );
		computerRaises();

	} // end method runOddRound
	
	private void runEvenRound(  )  
	{
		int userBet;
		final boolean allowPlayerToRaise = true;
		final boolean doNotAllowPlayerToRaise = false;

		// Make sure the input specified by user makes sense
		// For example, check if it is in range
		userBet = getUserBet( allowPlayerToRaise );		
		
		if( userBet == 0 )
		{
			userFolds();
			return;
		}
		
		if( userBet == callAmount )
		{
			compareHandWindow();
			determineWinner();
			return;
		}
		
		// From this point on, every thing is similar to
		// odd rounds, i.e., it is computer turn now
		callAmount = userBet - callAmount;
		runOddRound( doNotAllowPlayerToRaise );
		
	} // end method runEvenRound
	
	// Print the player hand into a string
	private String printHumanHandToString()
	{
		String message = new String();
		
		message = "Player:\n";
		message += humanPlayer.printToString();
		
		return message;
	} // end utility method printHumanHandToString
	
	// Reset everything in the beginning of each round
	private void roundInitializations()
	{
		//  Shuffle deck
		deck.shuffle();
		
		// Assign new hands to players (but do not show it)
		computerPlayer.setHand( deck );
		humanPlayer.setHand( deck );
		
		// Increment the round counter 
		round++;
		pot = 0;
		callAmount = 0;
	} // end utility method roundInitializations
	
	// Make sure the user bet is logical
	// For example it cannot be negative
	private int getUserBet( final boolean letUserRaise )
	{
		String message = new String();
		int userBet;
		
		do
		{
			// Show user hand and let them decide what they want to do
			message = printHumanHandToString();
			
			if( letUserRaise == true )
				message += "\nEnter " + callAmount + " to call. Anything less than " + callAmount + " to fold, or anything else to raise.\n";
			
			else 
				message += "\nEnter anthing less than " + callAmount + " to fold. Anything else to call. You cannot raise";
			
			message = JOptionPane.showInputDialog( null, message, ( "# " + round + ": Pot(" + pot + "), bank(" + humanPlayer.getBank() + "), "
					+ "bank2(" + computerPlayer.getBank() + ")" ), JOptionPane.INFORMATION_MESSAGE );
			userBet = Integer.parseInt( message );
			
			// User cannot bet more than they have
			if( userBet > humanPlayer.bank )
			{
				JOptionPane.showMessageDialog( null, "Invalid input. You bet cannot exceed your bank. Try again.\n",
						( "# " + round + ": Pot(" + pot + "), bank(" + humanPlayer.getBank() + "), "
								+ "bank2(" + computerPlayer.getBank() + ")" ), JOptionPane.INFORMATION_MESSAGE );
				continue;
			}
			
			// Bet cannot be negative
			if( userBet < 0 )
			{
				JOptionPane.showMessageDialog( null, "Invalid input. You bet must be non-negative. Try again.\n",
						( "# " + round + ": Pot(" + pot + "), bank(" + humanPlayer.getBank() + "),"
								+ " bank2(" + computerPlayer.getBank() + ")" ), JOptionPane.INFORMATION_MESSAGE );
				continue;
			}
			
			if( letUserRaise == false && userBet > callAmount )
				userBet = callAmount;
			
			if( userBet > callAmount + computerPlayer.getBank() )
			{
				JOptionPane.showMessageDialog( null, "Computer does not have enough money in its bank to call."
						+ "\nTo make the computer play all in enter at most " + ( int )( callAmount + computerPlayer.getBank() ) +
						". Please try again.\n", ( "# " + round + ": Pot(" + pot + "), bank1(" + 
						humanPlayer.getBank() + "), bank2(" + computerPlayer.getBank() + ")" ),
						JOptionPane.INFORMATION_MESSAGE );
				continue;
			}
			
			// If the bet is less than callAmount, it means that user 
			// wants to fold
			if( userBet < callAmount )
				userBet = 0;
			
			break;
		
		} while( true );
		
		pot += userBet;
		humanPlayer.bet( userBet );
		
		return userBet;
		
	} // end utility method getUserBet
	
	// Let computer evaluate its hand and decides if it wants 
	// to fold, call, or raise
	private int getComputerBet()
	{
		int bet;
		
		bet = computerPlayer.howMuchDoYouWantToBet( callAmount );
		
		if( bet > humanPlayer.getBank() + callAmount )
			bet = humanPlayer.getBank() + callAmount;
		
		// If the player is all in, do let the computer bet any
		// value higher than call amount even if the computer
		// can afford it. In this case, computer can either fold
		// or call human's all in
		if( humanPlayer.getBank() == 0 && bet >= callAmount )
			bet = callAmount;
		
		return bet;
	} // end utility method getComputerBet
	
	// Run the sequences in case computer calls
	private void computerCalls( final int computerBet )
	{
		JOptionPane.showMessageDialog( null,  "Computer calls.\nPot: " + pot + "$\n", ( "# " +
				round + ": Pot(" + pot + "), bank(" + humanPlayer.getBank() + "), bank2(" +
				computerPlayer.getBank() + ")" ), JOptionPane.INFORMATION_MESSAGE );
		compareHandWindow();
		determineWinner();		
	} //  end utility method computerCalls
	
	private void compareHandWindow()
	{
		String message = new String();
		message += "Player:\n";
		message += humanPlayer.printToString();
		message += "\n\n\nComputer:\n";
		message += computerPlayer.printToString();
		JOptionPane.showMessageDialog( null,  message, ( "# " + round + ": Pot(" + pot + "), bank(" +
				humanPlayer.getBank() + "), bank2(" + computerPlayer.getBank() +
				")" ), JOptionPane.INFORMATION_MESSAGE );
	} // end utility method compareHandWindow
	
	// Run the sequences in case computer folds
	private void computerFolds()
	{
		JOptionPane.showMessageDialog( null, "Computer Folds...\nCongrats! You won the round.\n" +
						pot + "$ is added to bank.", ( "# " + round + ": Pot(" + pot +
						"), bank(" + humanPlayer.getBank() + "), bank2(" + 
						computerPlayer.getBank() + ")" ), JOptionPane.INFORMATION_MESSAGE );
		humanPlayer.addToBank( pot );
	} // end utility method computerFolds
	
	// Run the sequences in case human player folds
	private void userFolds()
	{
		JOptionPane.showMessageDialog( null, "You Fold...\nComputer won the round.\n" + pot +
				"$ is added to computer's bank.", ( "# " + round + ": Pot(" + pot + "), bank(" + 
				humanPlayer.getBank() + "), bank2(" + computerPlayer.getBank() + 
				")" ), JOptionPane.INFORMATION_MESSAGE );
		computerPlayer.addToBank( pot );
	} // end utility method userFolds
	
	// Run the sequences in case computer raises
	private void computerRaises()
	{
		final boolean doNotLetUserRaise = false;
		
		String message = new String();
		int userBet = getUserBet( doNotLetUserRaise );
		
		if( userBet < callAmount )
		{
			message = "You fold.\n";
			message += printHumanHandToString();
			message += "\n\n\nComputer:\n" + computerPlayer.printToString();
			JOptionPane.showMessageDialog( null, message, ( "# " + round + ": Pot(" + pot +
					"), bank(" + humanPlayer.getBank() + "), bank2(" +
					computerPlayer.getBank() + ")" ), JOptionPane.INFORMATION_MESSAGE );
			computerPlayer.addToBank( pot );
		}
		
		else
		{
			message += printHumanHandToString();
			message += "\n\n\nComputer:\n";
			message += computerPlayer.printToString();
			JOptionPane.showMessageDialog( null,  message, ( "# " + round + ": Pot(" + pot +
					"), bank(" + humanPlayer.getBank() + "), bank2(" +
					computerPlayer.getBank() + ")" ), JOptionPane.INFORMATION_MESSAGE );
			determineWinner();
		} // end else
	} // end utility method computerRaised
	
	// Based on values of their hand, determine who is the winner
	// Add the pot to the winner's bank
	private void determineWinner()
	{
		String message = new String();
		final int initialBankComputer = computerPlayer.getBank();
		final int initialBankHuman = humanPlayer.getBank();
		
		if( computerPlayer.hand.getValue() > humanPlayer.hand.getValue() )
		{
			message +=  "Computer Won.\n";
			computerPlayer.addToBank( pot );
		}
		
		else if( computerPlayer.hand.getValue() == humanPlayer.hand.getValue() )
		{
			message += "It is a tie.\n ";
			message += pot/2;
			message += "$ is added to your account.\n";
			computerPlayer.addToBank( pot / 2 );
			humanPlayer.addToBank( pot / 2 );
		}
		
		else
		{
			message +=  "You Won.\nAdding ";
			message += pot;
			message += "$ to your account.\n";
			humanPlayer.addToBank( pot );
		}
		
		JOptionPane.showMessageDialog( null, message, ( "# " + round + ": Pot(" + pot + "),"
				+ " bank(" + initialBankHuman + "), bank2(" + initialBankComputer +
				")" ), JOptionPane.INFORMATION_MESSAGE );
	} // end determineWinder
	
	private void endGameMessage()
	{
		if( humanPlayer.getBank() <= computerPlayer.getBank() )
			JOptionPane.showMessageDialog( null, "You are defeated! Pathetic!" );
		
		else
			JOptionPane.showMessageDialog( null, "You are victorious! Impressive!" );
	} // end utility method endGameMessage
	
} // end class Game
