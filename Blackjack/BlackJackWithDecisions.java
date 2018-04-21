package Blackjack;

public class BlackJackWithDecisions
{
	public static void main (String[] args) throws InterruptedException
	{
		int numDecks = 6;
		int numPlayers = 4;
		boolean blackjack = false, blackjackSplit = false;
		int startMoney = 100000, money = startMoney;
		int baseBet = 100, bet = baseBet, splitBet = bet;
		int rounds = 0;
		int trials = 1, totalTrials = 100;
		int totalRounds = 0;
		double wongCount = 0, trueCount = 0;
		int bustCount = 0, walkCount = 0;
		boolean stillPlaying = true, stillPlayingSplit = false;
		boolean split = false;
		int decision;
		int[] aceCount = new int[numPlayers+2]; //0th hand: User's hand --- numPlayers'th hand: Dealers hand --- numPlayers+1'th hand: Split hand
		Card justDealt = new Card();
		
		Hand[] hands = new Hand[numPlayers+2];
		for (int x = 0; x<hands.length; x++)
			hands[x] = new Hand();
		
		Pack pack = new Pack(numDecks);
		pack.shuffle();
		
		
		for (int t = 1; t <= totalTrials; t++)
		{
			trials = t;
			while (money > 0 && money < startMoney*1.5)
			{
				while (pack.cardsLeft() > 52 && money > 0 && money < startMoney*1.5)
				{
					trueCount = wongCount/(pack.cardsLeft()/52.0);
					bet = (int)(baseBet*(trueCount-1));
					if (bet > money)
						bet = money;
					if (bet <= 0)
						bet = 1;
					//System.out.println("wongCount: " + wongCount +  " | trueCount: " + trueCount);
					//System.out.println("Betting $" + bet);
					for (int k = 0; k <= numPlayers; k++) //User will be 0th player, Dealer will be the numPlayers'th player
					{
						for (int i = 0; i < 2; i++) //Initial two card deal per player
						{
							justDealt = pack.deal();
							if (justDealt.getPointValue()==11)
								aceCount[k]++;
							wongCount += justDealt.getWongValue();
							hands[k].add(justDealt);
						}
					}
					System.out.println(hands[0].getCardAt(1));
					if (hands[0].totalValue() == 21) //Determines if user earned a blackjack
						blackjack = true;
					
					while (stillPlaying == true)
					{
						decision = hands[0].makeDecision(hands[numPlayers].getCardAt(0), aceCount[0]);
						//System.out.println("Decision: " + decision);
							
						if (decision == 1) // Hit
						{
							 justDealt = pack.deal();
							 wongCount += justDealt.getWongValue();
							 hands[0].add(justDealt);
						}
						if (decision == 2) // Stand
							 stillPlaying = false;
						if (decision == 3) // Double
						{
							 bet *= 2;
							 justDealt = pack.deal();
							 wongCount += justDealt.getWongValue();
							 hands[0].add(justDealt);
						}
						if (decision == 4) // Split/hit
						{
							 split = true;
							 stillPlaying = false;
							 stillPlayingSplit = true;
						}
						
						if (hands[0].totalValue() > 21 && aceCount[0] > 0)
							{
								hands[0].ace();
								aceCount[0]--;
							} 
						if (hands[0].totalValue() > 21)
								stillPlaying = false;
					}
					
					if (split == true)
					{
						hands[numPlayers+1].add(hands[0].getCardAt(1)); //Adds one card to split hand from original hand
						if (hands[0].getCardAt(0).getPointValue()==11)
						{
							aceCount[0]--;
							hands[numPlayers+1].ace();
							aceCount[numPlayers+1]++;
						}
						hands[0].removeTopCard(); //Removes that card from original hand
						justDealt = pack.deal(); //Deals second card to original hand
						if (justDealt.getPointValue()==11)
							aceCount[0]++;
						wongCount += justDealt.getWongValue();
						hands[0].add(justDealt);
						justDealt = pack.deal(); //Deals second card to split hand
						if (justDealt.getPointValue()==11)
							aceCount[numPlayers+1]++;
						wongCount += justDealt.getWongValue();
						hands[numPlayers].add(justDealt);

						stillPlaying = true;
						if (hands[0].totalValue() == 21) //Determines if original hand earned a blackjack
							blackjack = true;
						if (hands[numPlayers+1].totalValue() == 21) //Determines if split hand earned a blackjack
							blackjackSplit = true;

						while (stillPlaying == true)
						{
							decision = hands[0].makeDecisionSplit(hands[numPlayers].getCardAt(0), aceCount[0]);
							if (decision == 1) // Hit
							{
								justDealt = pack.deal();
								wongCount += justDealt.getWongValue();
								hands[0].add(justDealt);
							}
							if (decision == 2) // Stand
							{
								stillPlaying = false;
							}
							if (decision == 3) // Double
							{
								bet *= 2;
								justDealt = pack.deal();
								wongCount += justDealt.getWongValue();
								hands[0].add(justDealt);
							}
							if (decision == 4) // Split/hit  -- Should never reach this case. If so, hit
							{
								justDealt = pack.deal();
								wongCount += justDealt.getWongValue();
								hands[0].add(justDealt);
							}
							
							if (hands[0].totalValue() > 21 && aceCount[0] > 0)
							{
								hands[0].ace();
								aceCount[0]--;
							} 
							if (hands[0].totalValue() > 21)
								stillPlaying = false;
						}
						
						while (stillPlayingSplit == true)
						{
							decision = hands[numPlayers+1].makeDecisionSplit(hands[numPlayers].getCardAt(0), aceCount[numPlayers+1]);
							if (decision == 1) // Hit
							{
								justDealt = pack.deal();
								wongCount += justDealt.getWongValue();
								hands[numPlayers+1].add(justDealt);
							}
							if (decision == 2) // Stand
							{
								stillPlayingSplit = false;
							}
							if (decision == 3) // Double
							{
								bet *= 2;
								justDealt = pack.deal();
								wongCount += justDealt.getWongValue();
								hands[numPlayers+1].add(justDealt);
							}
							if (decision == 4) // Split/hit  -- Should never reach this case. If so, hit
							{
								justDealt = pack.deal();
								wongCount += justDealt.getWongValue();
								hands[numPlayers+1].add(justDealt);
							}
							
							if (hands[numPlayers+1].totalValue() > 21 && aceCount[numPlayers+1] > 0)
							{
								hands[numPlayers+1].ace();
								aceCount[numPlayers+1]--;
							} 
							if (hands[numPlayers+1].totalValue() > 21)
							{
								stillPlayingSplit = false;
							}
						}
					}
					
					for (int i = 1; i <= numPlayers; i++) //Plays for all other players
					{
						while (hands[i].totalValue() < 17)
						{
							justDealt = pack.deal();
							if (justDealt.getPointValue()==11)
								aceCount[i]++;
							wongCount += justDealt.getWongValue();
							hands[i].add(justDealt);
							if (hands[i].totalValue() > 21 && aceCount[i] > 0)
							{
								hands[i].ace();
								aceCount[i]--;
							}
						}
					}
					
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
					
					if (split == true)
					{
						if (blackjackSplit == true)
							money += (splitBet*1.5);
						else if (hands[numPlayers+1].totalValue() > 21)
							money -= splitBet;
						else if (hands[numPlayers].totalValue() > 21)
							money += splitBet;
						else if (hands[numPlayers+1].totalValue() > hands[numPlayers].totalValue())
							money += splitBet;
						else if (hands[numPlayers+1].totalValue() < hands[numPlayers].totalValue())
							money -= splitBet;
					}
					
					//System.out.println("Money: $" + money);
					
					for (int k = 0; k <= numPlayers+1; k++) //Resets round
						hands[k].clearHand();
					for (int k = 0; k < aceCount.length; k++)
						aceCount[k] = 0;
					blackjack = false;
					blackjackSplit = false;
					rounds++;
					bet = baseBet;
					stillPlaying = true;
					stillPlayingSplit = false;
					split = false;
					//Thread.sleep(1000);
				}
				pack.reShuffle();
				wongCount = 0;
				trueCount = 0;
			}
			if (money > startMoney)
				walkCount++;
			else
				bustCount++;
			totalRounds += rounds;
			rounds = 0;
			money = startMoney;
		}
		System.out.println("Walked away in " + walkCount + " games.");
		System.out.println("Busted out in " + bustCount + " games.");
	}
}
