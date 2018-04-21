package Blackjack;

public class Card
{
	
	private int value;
	public enum face {Clubs, Spades, Hearts, Diamonds};
	private face suit;
		
	public Card()
	{
	value = (int)(Math.random()*13)+1;
	int randSuit = (int)(Math.random()*4);
	switch (randSuit) 
		{
		case 0:
			suit = face.Clubs;
			break;
		case 1:
			suit = face.Spades;
			break;
		case 2:
			suit = face.Hearts;
			break;
		case 3:
			suit = face.Diamonds;
			break;
		}
	}
	
	public Card(int v, String s)
	{
		value = v;
		suit = face.valueOf(s);
	}
	
	public int getValue()
	{
		return value;
	}
	
	public int getPointValue()
	{
		if (value > 10)
			return 10;
		if (value == 1)
			return 11;
		return value;
	}
	
	public face getSuit()
	{
		return suit;
	}
	
	public void setValue(int v)
	{
		value = v;
	}
	
	public void setSuit(face s)
	{
		suit = s;
	}
	
	public String toString()
	{
		String toPrint, valuePrint;
		switch (value)
			{
			case 1:
				valuePrint = "Ace";
				break;
			case 11:
				valuePrint = "Jack";
				break;
			case 12:
				valuePrint = "Queen";
				break;
			case 13:
				valuePrint = "King";
				break;
			default:
				valuePrint = Integer.toString(value);
			}
		toPrint = valuePrint + " of " + suit;
		return toPrint;
	}
	
	public double getWongValue()
	{
		double wong = 0;
		if (getPointValue() == 10 || getPointValue() == 11)
			wong = -1;
		else if (getPointValue() == 2 || getPointValue() == 7)
			wong = .5;
		else if (getPointValue() == 3 || getPointValue() == 4 || getPointValue() == 6)
			wong = 1;
		else if (getPointValue() == 5)
			wong = 1.5;
		else if (getPointValue() == 8)
			wong = 0;
		else if (getPointValue() == 9)
			wong = -.5;
		return wong;
	}
	
	public boolean equals(Card c2)
	{
		boolean doesEqual = false;
		if (value == c2.getValue())
			doesEqual = true;
		return doesEqual;
	}
}