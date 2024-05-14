package hw4;
import api.Card;
import api.Hand;

/*
 * This class exploits the similarities between the Straight evaluator and the StraightFlush evaluators.
 * It includes common code of both the classes.
 * 
 * @author - Hrishikesha Kyathsandra
 * @version - 1.0
 */
public abstract class AbstractStraight extends AbstractEvaluator
{
	private int maxCardRank;

	protected AbstractStraight(String name, int cardsRequired, int ranking, int handSize, int maxCardRank)
	{
		super(name, cardsRequired, ranking, handSize);
		this.maxCardRank = maxCardRank;
	}
	
	@Override
	public boolean canSatisfy(Card[] mainCards)
	{
		boolean satisfies = false;
		
		if(mainCards.length!=cardsRequired()) 
		{
			return false;
		}

		if (isAceLow(mainCards) || isAceHigh(mainCards))
		{
			return true;
		}

		for (int i = 1; i < mainCards.length; i++)
		{
			if (mainCards[i - 1].getRank() == mainCards[i].getRank() + 1)
			{
				satisfies = true;
			}

			else
			{
				return false;
			}
		}

		return satisfies;
	}
	
	
	@Override
	public Hand createHand(Card[] allCards, int[] subset)
	{
		if (allCards.length < handSize() || subset.length != cardsRequired())
		{
			return null;
		}
		
		Hand requiredHand = null;

		Card[] mainCards = myMainCards(allCards, subset);
		Card[] sideCards = mySideCards(allCards, subset);

		if(mainCards.length!=cardsRequired() || mainCards.length + sideCards.length < handSize() || !(canSatisfy(mainCards)) ) 
		{
			return null;
		}

		if (isAceLow(mainCards))
		{
			mainCards = reSortIfAceLow(mainCards);
			requiredHand = new Hand(mainCards, sideCards, this);
		}
		else
		{
			requiredHand = new Hand(mainCards, sideCards, this);
		}

		return requiredHand;
	}
	
	/*
	 * Helper method to check if the given cards are an ace high straight.
	 */
	protected boolean isAceHigh(Card[] mainCards) // helper to check if the given cards are a ace-high straight
	{
		boolean isHigh = false;

		for (int i = 2; i < mainCards.length; i++)
		{
			if (mainCards[0].getRank() == 1 && mainCards[1].getRank() == maxCardRank && mainCards[i - 1].getRank() == mainCards[i].getRank() + 1)
			{
				isHigh = true;
			}

			else
			{
				return false;
			}
		}

		return isHigh;
	}
	
	/*
	 * Helper method to check if the given cards are an ace low straight.
	 */
	protected boolean isAceLow(Card[] mainCards) // helper to check whether the cards are an ace-low straight.
	{
		boolean isLow = false;

		for (int i = 2; i < mainCards.length; i++)
		{
			if (mainCards[0].getRank() == 1 && mainCards[mainCards.length - 1].getRank() == 2 && mainCards[i - 1].getRank() == mainCards[i].getRank() + 1)
			{
				isLow = true;
			}

			else
			{
				return false;
			}
		}

		return isLow;
	}

	/*
	 * Helper method to resort the cards in case of an ace low straight.
	 */
	protected Card[] reSortIfAceLow(Card[] mainCards) // helper to sort the cards if its an aceLow to make code more readable.
	{
		Card aceCard = mainCards[0];

		for (int i = 0; i < mainCards.length - 1; i++)
		{
			mainCards[i] = mainCards[i + 1];
		}

		mainCards[mainCards.length - 1] = aceCard;

		return mainCards;
	}
}
