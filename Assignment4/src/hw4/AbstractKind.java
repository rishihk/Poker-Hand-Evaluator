package hw4;

import api.Card;

/*
 * This class exploits the similarities between One Pair, Three of A Kind and Four of A Kind evaluators.
 * It just checks whether the first and last rank of the main cards are equal.
 * 
 * @author - Hrishikesha Kyathsandra
 * @version - 1.0
 */
public abstract class AbstractKind extends AbstractEvaluator
{
	protected AbstractKind(String name, int cardsRequired, int ranking, int handSize)
	{
		super(name, cardsRequired, ranking, handSize);
	}

	@Override
	public boolean canSatisfy(Card[] mainCards)
	{
		if (mainCards.length != cardsRequired())
		{
			return false;
		}

		if (mainCards[0].getRank() == mainCards[mainCards.length - 1].getRank()) // if the first main cards rank is the same as the one in the end, it satisfies any evaluators that are looking for runs.
		{
			return true;
		}

		return false;
	}
}
