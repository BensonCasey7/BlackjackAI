package Blackjack;

public class BlackJackAutoPlayer
{
	public static void main (String[] args) throws InterruptedException
	{
		int numDecks = 6;
		int numPlayers = 4;
		boolean blackjack = false;
		int startMoney = 100000, money = startMoney;
		int baseBet = 100, bet = baseBet;
		int rounds = 0;
		int trials = 1, totalTrials = 100;
		int totalRounds = 0;
		int bustCount = 0, walkCount = 0;
		int[] aceCount = new int[numPlayers+1];
		Card justDealt = new Card();
		
		Hand[] hands = new Hand[numPlayers+1];
		for (int x = 0; x<hands.length; x++)
			hands[x] = new Hand();
		
		Pack pack = new Pack(numDecks);
		pack.shuffle();
		
		for (int t = 1; t <= totalTrials; t++)
		{
			trials = t;
			while (money >= bet && money < startMoney*1.5)
			{
				while (pack.cardsLeft() > 52 && money >= bet && money < startMoney*1.5)
				{
					for (int k = 0; k <= numPlayers; k++) //User will be 0th player, Dealer will be the numPlayers'th player
					{
						for (int i = 0; i < 2; i++) //Initial two card deal per player
						{
							justDealt = pack.deal();
							if (justDealt.getPointValue()==11)
								aceCount[k]++;
							hands[k].add(justDealt);
						}
					}
					if (hands[0].totalValue() == 21) //Determines if user earned a blackjack
						blackjack = true;
					for (int i = 0; i <= numPlayers; i++) //Plays for each player
					{
						while (hands[i].totalValue() < 17)
						{
							justDealt = pack.deal();
							if (justDealt.getPointValue()==11)
								aceCount[i]++;
							hands[i].add(justDealt);
							if (hands[i].totalValue() > 21 && aceCount[i] > 0)
							{
								hands[i].ace();
								aceCount[i]--;
							}
						}
					}
					
					//System.out.println("Player total: " + hands[0].totalValue() + " Dealer total: " + hands[numPlayers].totalValue());
					if (blackjack == true)
						money += (bet*1.5);
					else if (hands[0].totalValue() > 21)
						money -= bet;
					else if (hands[numPlayers].totalValue() > 21)
						money += bet;
					else if (hands[0].totalValue() > hands[numPlayers].totalValue())
						money += bet;
					else if (hands[0].totalValue() < hands[numPlayers].totalValue())
						money -= bet;
					//System.out.println("Money: $" + money);
					for (int k = 0; k <= numPlayers; k++)
						hands[k].clearHand();
					for (int k = 0; k < aceCount.length; k++)
						aceCount[k] = 0;
					blackjack = false;
					rounds++;
					//Thread.sleep(1000);
				}
				pack.reShuffle();
			}
			if (money > startMoney)
				walkCount++;
			else
				bustCount++;
			totalRounds += rounds;
			rounds = 0;
			money = startMoney;
		}
		//System.out.println("Average of " + (totalRounds/trials) + " rounds to bust out.");
		System.out.println("Walked away in " + walkCount + " games.");
		System.out.println("Busted out in " + bustCount + " games.");
	}
}
