import java.util.Random;

public class Player 
{
	Hand hand;
	int bank;
	
	// Must be less than 100
	// 5 means bluff 5 percent of times
	private static final int willingnessToTakeRisk = 5;
	
	public Player()
	{
		hand = new Hand();
		bank = 0;
	} // end constructor Player
	
	public void setBank( final int bank )
	{
		this.bank = ( bank >= 0 ) ? bank : 0;
	} // end method setBank
	
	public int getBank()
	{
		return bank;
	} // end method getBank
	
	public String printToString()
	{
		String message = new String();
		
		message = hand.printToString();
		
		return message;
	} // end method print
	
	public void setHand( final Deck deck )
	{
		this.hand.buildHand( deck );
	} // end method setHand
	
	public void bet( final int betValue )
	{
		// bank can become negative, which is totally fine!
		bank -= betValue;
	} // end method bet
	
	public int howMuchDoYouWantToBet( final int callAmount )
	{
		double risk;
		double ratio;
		double value = ( double ) hand.getValue();
		int bet;
		final int raise = 1;
		final int call = 2;
		final int fold = 3;
		int bluff;
		
		ratio = ( callAmount <= bank ) ? ( ( double ) callAmount ) / bank : 1;		
		risk = 2000.0 * ratio - value * value;
		
		Random generator = new Random();
		bluff = generator.nextInt( 100 );
		
		if( bluff < willingnessToTakeRisk )
			risk -= 10000 + Math.abs( generator.nextInt( 10000 ) );
			
		// All in
		if( risk <= -100000 )
		{
			risk = bank;
			bet = raise;
		}
		
		else if( risk <= -2000 )
		{
			risk = 0.8 * bank;
			bet = raise;
		}
		
		else if( risk <= -1000 )
		{
			risk = 0.7 * bank;
			bet = raise;
		}
		
		else if( risk <= -750 )
		{
			risk = 0.6 * bank;
			bet = raise;
		}
		
		else if( risk <= -500 )
		{
			risk = 0.5 * bank;
			bet = raise;
		}
		
		else if( risk <= -250 )
		{
			risk = 0.4 * bank;
			bet = raise;
		}
		
		else if( risk <= 0 )
		{
			risk = 0.3 * bank;
			bet = call;
		}
		
		else if( risk <= 100 )
		{
			risk = 0.2 * bank;
			bet = call;
		}
		
		else if( risk <= 500 )
		{
			risk = 0.1 * bank;
			bet = call;
		}
		
		else 
		{
			risk = 0;
			bet = fold;
		}
		
		if( bet == raise && callAmount <= risk )
			return ( int ) risk;
		
		if( bet == call && callAmount <= risk )
			return ( int ) callAmount;
		
		return 0;
		
	} // end method howMuchDoYouWantToBet
	
	public void addToBank( final int prize )
	{
		bank += prize;
	} // end method addToBank
	
} // end class Player