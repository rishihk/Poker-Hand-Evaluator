package hw4;

import api.Card;
import api.Suit;

/**
 * Evaluator for a hand consisting of a "straight" in which the card ranks are
 * consecutive numbers AND the cards all have the same suit. The number of
 * required cards is equal to the hand size. An ace (card of rank 1) may be
 * treated as the highest possible card or as the lowest (not both) To evaluate
 * a straight containing an ace it is necessary to know what the highest card
 * rank will be in a given game; therefore, this value must be specified when
 * the evaluator is constructed. In a hand created by this evaluator the cards
 * are listed in descending order with high card first, e.g. [10 9 8 7 6] or [A
 * K Q J 10], with one exception: In case of an ace-low straight, the ace must
 * appear last, as in [5 4 3 2 A]
 * 
 * The name of this evaluator is "Straight Flush".
 * 
 * @author - Hrishikesha Kyathsandra.
 * @version - 1.0.
 */
public class StraightFlushEvaluator extends AbstractStraight
{
	/**
	 * Constructs the evaluator. Note that the maximum rank of the cards to be used
	 * must be specified in order to correctly evaluate a straight with ace high.
	 * 
	 * @param ranking     ranking of this hand
	 * @param handSize    number of cards in a hand
	 * @param maxCardRank largest rank of any card to be used
	 */
	public StraightFlushEvaluator(int ranking, int handSize, int maxCardRank)
	{
		super("Straight Flush", handSize, ranking, handSize, maxCardRank);
	}

	@Override
	public boolean canSatisfy(Card[] mainCards) 
	{
		if(mainCards.length!=cardsRequired()) 
		{
			return false;
		}
		
		boolean isStraight = super.canSatisfy(mainCards);

		boolean equalSuits = false;

		for (int i = 1; i < mainCards.length; i++)
		{
			if (mainCards[i - 1].getSuit().equals(mainCards[i].getSuit())) // only difference here, is we check if the suits are equal as well.
			{
				equalSuits = true;
			}

			else
			{
				return false;
			}
		}

		if (isStraight && equalSuits)
		{
			return true;
		}
		else
		{
			return false;
		}

	}
}
