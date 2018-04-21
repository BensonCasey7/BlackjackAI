package Blackjack;

import Blackjack.Card.face;

public class Pack
{
	Card[] pack;
	int numDecks;
	int index = 0;
	
	Pack(int num) //Creates sorted pack of cards based on how many decks will be used
	{
		numDecks = num;
		pack = new Card[52*numDecks];
		for (int k=0; k<numDecks; k++)
		{
			for(int i=0; i<4; i++ ) // Suits
			{
				for (int j=1; j<=13; j++) //Values
				{
					switch (i) 
					{
					case 0:
						pack[(k*52) + (i*13) + (j-1)] = new Card(j, "Clubs");
						break;
					case 1:
						pack[(k*52) + (i*13) + (j-1)] = new Card(j, "Spades");
						break;
					case 2:
						pack[(k*52) + (i*13) + (j-1)] = new Card(j, "Hearts");
						break;
					case 3:
						pack[(k*52) + (i*13) + (j-1)] = new Card(j, "Diamonds");
						break;
					}
				}
			}
		}
	}
	
	public void shuffle()
	{
		for (int k = 0; k < 52*numDecks; k++) //Shuffles pack
		{
			int shuffleLocation = (int)(Math.random()*52);
			int transitionVal = pack[k].getValue();
			face transitionSuit = pack[k].getSuit();
			pack[k].setValue(pack[shuffleLocation].getValue());
			pack[k].setSuit(pack[shuffleLocation].getSuit());
			pack[shuffleLocation].setValue(transitionVal);
			pack[shuffleLocation].setSuit(transitionSuit);
		}
	}
	
	public Card deal()
	{
		return pack[index++];
	}
	
	public int cardsLeft()
	{
		return (52*numDecks - index);
	}
	
	public void reShuffle()
	{
		for (int x = 0; x < 52*numDecks; x++)
		{
			pack[x] = null;
		}
		for (int k=0; k<numDecks; k++)
		{
			for(int i=0; i<4; i++ ) // Suits
			{
				for (int j=1; j<=13; j++) //Values
				{
					switch (i) 
					{
					case 0:
						pack[(k*52) + (i*13) + (j-1)] = new Card(j, "Clubs");
						break;
					case 1:
						pack[(k*52) + (i*13) + (j-1)] = new Card(j, "Spades");
						break;
					case 2:
						pack[(k*52) + (i*13) + (j-1)] = new Card(j, "Hearts");
						break;
					case 3:
						pack[(k*52) + (i*13) + (j-1)] = new Card(j, "Diamonds");
						break;
					}
				}
			}
		}
		shuffle();
		index = 0;
	}
}