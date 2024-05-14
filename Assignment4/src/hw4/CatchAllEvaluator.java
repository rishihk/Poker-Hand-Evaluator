package hw4;

/**
 * Evaluator satisfied by any set of cards. The number of required cards is
 * equal to the hand size.
 * 
 * The name of this evaluator is "Catch All".
 * 
 * @author - Hrishikesha Kyathsandra
 * @version - 1.0.
 */
public class CatchAllEvaluator extends AbstractEvaluator
{
	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public CatchAllEvaluator(int ranking, int handSize)
	{
		super("Catch All", handSize, ranking, handSize);	
	}

}
