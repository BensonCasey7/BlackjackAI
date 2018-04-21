package Blackjack;

public class Hand
{
	Card[] hand;
	int index = 0;
	int aces = 0;
	
	// 1 = hit
	// 2 = stand
	// 3 = double
	// 4 = split/hit
	int[][] stratTable1 = new int[][] { // Hard hand table
	   //0,1,2,3,4,5,6,7,8,9,X,A - Dealer's Card
		{1,1,1,1,1,1,1,1,1,1,1,1},  // 0 - Will never be reached
		{1,1,1,1,1,1,1,1,1,1,1,1},  // 1 - Will never be reached
		{1,1,1,1,1,1,1,1,1,1,1,1},  // 2 - Will never be reached
		{1,1,1,1,1,1,1,1,1,1,1,1},  // 3 - Will never be reached
	    {1,1,1,1,1,1,1,1,1,1,1,1},  // 4
	    {1,1,1,1,1,1,1,1,1,1,1,1},  // 5
	    {1,1,1,1,1,1,1,1,1,1,1,1},  // 6
	    {1,1,1,1,1,1,1,1,1,1,1,1},  // 7
	    {1,1,1,1,1,1,1,1,1,1,1,1},  // 8
	    {1,1,1,3,3,3,3,1,1,1,1,1},  // 9
	    {1,1,3,3,3,3,3,3,3,3,1,1},  // 10
	    {1,1,3,3,3,3,3,3,3,3,3,1},  // 11
	    {1,1,1,1,2,2,2,1,1,1,1,1},  // 12
	    {1,1,2,2,2,2,2,1,1,1,1,1},  // 13
	    {1,1,2,2,2,2,2,1,1,1,1,1},  // 14
	    {1,1,2,2,2,2,2,1,1,1,1,1},  // 15
	    {1,1,2,2,2,2,2,1,1,1,1,1},  // 16
	    {1,1,2,2,2,2,2,2,2,2,2,2},  // 17
	    {1,1,2,2,2,2,2,2,2,2,2,2},  // 18
	    {1,1,2,2,2,2,2,2,2,2,2,2},  // 19
	    {1,1,2,2,2,2,2,2,2,2,2,2},  // 20
	    {1,1,2,2,2,2,2,2,2,2,2,2},  // 21
	    {2,2,2,2,2,2,2,2,2,2,2,2}}; // 22
	    
    int[][] stratTable2 = new int[][] { //Soft hand table
       //0,1,2,3,4,5,6,7,8,9,X,A - Dealer's Card
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 0 - Will never be reached
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 1 - Will never be reached
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 2 - Will never be reached
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 3 - Will never be reached
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 4 - Will never be reached
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 5 - Will never be reached
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 6 - Will never be reached
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 7 - Will never be reached
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 8 - Will never be reached
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 9 - Will never be reached
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 10 - Will never be reached
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 11 - Will never be reached
    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 12 - Will never be reached
    	{1,1,1,1,1,3,3,1,1,1,1,1},  // 13
	    {1,1,1,1,1,3,3,1,1,1,1,1},  // 14
	    {1,1,1,1,3,3,3,1,1,1,1,1},  // 15
	    {1,1,1,1,3,3,3,1,1,1,1,1},  // 16
	    {1,1,1,3,3,3,3,1,1,1,1,1},  // 17
	    {1,1,2,3,3,3,3,2,2,1,1,1},  // 18
	    {1,1,2,2,2,2,2,2,2,2,2,2},  // 19
	    {1,1,2,2,2,2,2,2,2,2,2,2},  // 20
	    {1,1,2,2,2,2,2,2,2,2,2,2}}; // 21
	    
    int[][] stratTable3 = new int[][] {//Split table
	       //0,1,2,3,4,5,6,7,8,9,X,A - Dealer's Card
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 0 - Will never be reached
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 1 - Will never be reached
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 2 - Will never be reached
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 3 - Will never be reached
	    	{1,1,1,1,4,4,4,4,1,1,1,1},  // 2-2 -- 4
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 5 - Will never be reached
	    	{1,1,1,1,4,4,4,4,1,1,1,1},  // 3-3 -- 6
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 7 - Will never be reached
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 4-4 -- 8 
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 9 - Will never be reached
	    	{1,1,3,3,3,3,3,3,3,3,1,1},  // 5-5 -- 11
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 11 - Will never be reached
	    	{1,1,1,4,4,4,4,1,1,1,1,1},  // 6-6 -- 12
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 13 - Will never be reached
	    	{1,1,4,4,4,4,4,4,1,1,1,1},  // 7-7 -- 14
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 15 - Will never be reached
	    	{1,1,4,4,4,4,4,4,4,4,4,4},  // 8-8 -- 16
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 17 - Will never be reached
	    	{1,1,4,4,4,4,4,1,4,4,1,1},  // 9-9 -- 18
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 19 - Will never be reached
	    	{1,1,2,2,2,2,2,2,2,2,2,2},  // 10-10 -- 20
	    	{1,1,1,1,1,1,1,1,1,1,1,1},  // 21 - Will never be reached
	    	{1,1,4,4,4,4,4,4,4,4,4,4}}; // A-A -- 22
	
	Hand()
	{
		hand = new Card[15];
	}
	
	public void add(Card c)
	{
		hand[index++] = c;
	}
	
	public void removeTopCard()
	{
		hand[--index] = null;
	}
	
	public int totalValue()
	{
		int total = 0;
		for (int x = 0; x < index; x++)
		{
			total += hand[x].getPointValue();
		}
		total = total-(aces*10);
		return total;
	}
	
	public Card getCardAt(int i)
	{
		return hand[i];
	}
	
	public void clearHand()
	{
		for (int k = 0; k < hand.length; k++)
		{
			hand[k] = null;
		}
		index = 0;
		aces = 0;
	}
	
	public void ace()
	{
		aces++;
	}
	
	
	public int makeDecision(Card dealerCard, int aceCount)
	{
		if (hand[0].equals(hand[1]) && index == 2) //Split hand
			return stratTable3[totalValue()][dealerCard.getPointValue()];
		else if (aceCount > 0) //Soft hand
			return stratTable2[totalValue()][dealerCard.getPointValue()];
		else //Hard hand
			return stratTable1[totalValue()][dealerCard.getPointValue()];
	}
	
	public int makeDecisionSplit(Card dealerCard, int aceCount)
	{
		if (aceCount > 0) //Soft hand
			return stratTable2[totalValue()][dealerCard.getPointValue()];
		else //Hard hand
			return stratTable1[totalValue()][dealerCard.getPointValue()];
	}
	
}
