package hw4;

import api.Card;
import api.Hand;
import api.IEvaluator;

/**
 * Evaluator for a hand containing (at least) three cards of the same rank. The
 * number of cards required is three.
 * 
 * The name of this evaluator is "Three of a Kind".
 * 
 * @author - Hrishikesha Kyathsandra.
 * @version - 1.0.
 */
public class ThreeOfAKindEvaluator extends AbstractKind
{
	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public ThreeOfAKindEvaluator(int ranking, int handSize)
	{
		super("Three of a Kind", 3, ranking, handSize);
	}
}
			
