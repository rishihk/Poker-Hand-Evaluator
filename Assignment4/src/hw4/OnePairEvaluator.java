package hw4;

/**
 * Evaluator for a hand containing (at least) two cards of the same rank. The
 * number of cards required is two.
 * 
 * The name of this evaluator is "One Pair".
 * 
 * @author - Hrishikesha Kyathsandra
 * @version - 1.0.
 */
public class OnePairEvaluator extends AbstractKind
{
	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public OnePairEvaluator(int ranking, int handSize)
	{
		super("One Pair", 2, ranking, handSize);
	}

}