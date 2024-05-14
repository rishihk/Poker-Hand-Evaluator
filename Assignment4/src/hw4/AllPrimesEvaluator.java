package hw4;

import api.Card;

/**
 * Evaluator for a hand in which the rank of each card is a prime number. The
 * number of cards required is equal to the hand size.
 * 
 * The name of this evaluator is "All Primes".
 * 
 * @author - Hrishikesha Kyathsandra
 * @version - 1.0
 */
public class AllPrimesEvaluator extends AbstractEvaluator
{
	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public AllPrimesEvaluator(int ranking, int handSize)
	{
		super("All Primes", handSize, ranking, handSize);
	}

	protected boolean isPrime(Card givenCard) // helper to check if a given card is a prime.
	{
		if (givenCard.getRank() <= 1)
		{
			return false;
		}

		if (givenCard.getRank() == 2)
		{
			return true;
		}

		for (int i = 2; i <= givenCard.getRank() / 2; i++)
		{
			if (givenCard.getRank() % i == 0)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean canSatisfy(Card[] mainCards)
	{
		boolean satisfies = false;
		
		if(mainCards.length!=cardsRequired()) 
		{
			return false;
		}

		for (int i = 0; i < mainCards.length; i++)
		{
			if (isPrime(mainCards[i]))
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

}
