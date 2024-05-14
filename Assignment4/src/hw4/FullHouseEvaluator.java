package hw4;

import java.util.ArrayList;

import api.Card;
import api.Hand;
import api.IEvaluator;

/**
 * Evaluator for a generalized full house. The number of required cards is equal
 * to the hand size. If the hand size is an odd number n, then there must be (n
 * / 2) + 1 cards of the matching rank and the remaining (n / 2) cards must be
 * of matching rank. In this case, when constructing a hand, the larger group
 * must be listed first even if of lower rank than the smaller group</strong>
 * (e.g. as [3 3 3 5 5] rather than [5 5 3 3 3]). If the hand size is even, then
 * half the cards must be of matching rank and the remaining half of matching
 * rank. Any group of cards, all of which are the same rank, always satisfies
 * this evaluator.
 * 
 * The name of this evaluator is "Full House".
 * 
 * @author - Hrishikesha Kyathsandra
 * @version - 1.0.
 */
public class FullHouseEvaluator extends AbstractEvaluator
{
	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public FullHouseEvaluator(int ranking, int handSize)
	{
		super("Full House", handSize, ranking, handSize);
	}

	@Override
	public boolean canSatisfy(Card[] mainCards)
	{
		if(mainCards.length!=cardsRequired()) 
		{
			return false;
		}
		
		if (mainCards.length % 2 != 0)
		{
			mainCards = reSortIfOddLen(mainCards);
			
			if (mainCards[0].getRank() != mainCards[mainCards.length / 2].getRank() || mainCards[mainCards.length / 2 + 1].getRank() != mainCards[mainCards.length - 1].getRank()) // check if 1 to mid rank is equal and from mid+1 to end rank is equal
			{
				return false;
			}
		}

		else if (mainCards[0].getRank() != mainCards[mainCards.length / 2 - 1].getRank() || mainCards[mainCards.length / 2].getRank() != mainCards[mainCards.length - 1].getRank())
		{
			return false;
		}

		return true;
	}

	@Override
	public Hand createHand(Card[] allCards, int[] subset) // think of a hand helper
	{
		if (allCards.length < handSize() || subset.length != cardsRequired())
		{
			return null;
		}

		Card[] mainCards = myMainCards(allCards, subset);
		Card[] sideCards = mySideCards(allCards, subset);

		if (mainCards.length % 2 != 0)
		{
			reSortIfOddLen(mainCards);
		}

		Hand requiredHand = null;

		if(mainCards.length!=cardsRequired() || mainCards.length + sideCards.length < handSize() || !(canSatisfy(mainCards)) ) 
		{
			return null;
		}

		else
		{
			requiredHand = new Hand(mainCards, sideCards, this);
		}

		return requiredHand;
	}
	
	/*
	 * Helper method to re-sort the cards when there are an odd number of cards.
	 */
	protected Card[] reSortIfOddLen(Card[] mainCards) // helper method to re-sort the cards in case there are a odd number of cards
	{
		if (mainCards.length % 2 != 0)
		{
			if (mainCards[mainCards.length / 2].getRank() != mainCards[mainCards.length / 2 - 1].getRank())
			{
				Card[] shorterHalf = new Card[mainCards.length / 2];
				Card[] longerHalf = new Card[mainCards.length / 2 + 1];

				int k = 0;
				for (int i = mainCards.length / 2; i < mainCards.length; i++)
				{
					longerHalf[k++] = mainCards[i];
				}

				for (int i = 0; i < mainCards.length / 2; i++)
				{
					shorterHalf[i] = mainCards[i];
				}

				for (int i = 0; i < mainCards.length / 2 + 1; i++)
				{
					mainCards[i] = longerHalf[i];
				}

				int j = 0;
				for (int i = mainCards.length / 2 + 1; i < mainCards.length; i++)
				{
					if (j < handSize())
					{
						mainCards[i] = shorterHalf[j++];
					}
				}
			}
		}

		return mainCards;
	}
}