package hw4;

import java.util.ArrayList;
import java.util.Arrays;

import api.Card;
import api.Hand;
import api.IEvaluator;
import util.SubsetFinder;

/**
 * The class AbstractEvaluator includes common code for all evaluator types.
 * 
 * The way I structured my class hierarchy is by including all the common code
 * for all the evaluators in the AbstractEvaluator. I implemented IEvaluator in
 * this class. I mostly overrode the canSatisfy for each evaluator as they had
 * different conditions.
 * 
 * For the OnePairEvaluator, FourOfAKindEvaluator and the ThreeOfAKind
 * evaluators, I exploited the fact that we just need to check if the rank of
 * the first mainCard is equal to rank of the last mainCard. So I created a
 * parent class for these three Evaluators called AbstractKind which extends
 * Abstract Evaluator.
 * 
 * For the StraightEvaluator and the StraightFlushEvaluator I saw that the only
 * difference between them is checking the suits. So I created a parent class
 * for them called AbstractStraight extending AbstractEvaluator. I put all of
 * the common code for these two classes in there.
 * 
 * The AllPrimesEvaluator, CatchAllEvaluator and the FullHouseEvaluator all
 * extend the parent AbstractEvaluator.
 * 
 * @author - Hrishikesha Kyathsandra
 * @version - 1.0
 */
public abstract class AbstractEvaluator implements IEvaluator
{
	/*
	 * Holds the value for the ranking of the hand.
	 */
	private int ranking;

	/*
	 * Holds the value for how many cards can be held in the hand.
	 */
	private int handSize;

	/*
	 * Holds the name of the Evaluator
	 */
	private String name;

	/*
	 * Holds the value for the number of cards required to satisfy a given evaluator.
	 */
	private int cardsRequired;

	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	protected AbstractEvaluator(String name, int cardsRequired, int ranking, int handSize) // put all the getters in the super constructor, think of these as the getters
	{
		this.name = name;
		this.cardsRequired = cardsRequired;
		this.ranking = ranking;
		this.handSize = handSize;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public int getRanking()
	{
		return ranking;
	}

	@Override
	public int cardsRequired()
	{
		return cardsRequired;
	}

	@Override
	public int handSize()
	{
		return handSize;
	}

	//@Override
	public boolean canSatisfy(Card[] mainCards) // overriden in each evaluator
	{
		if (handSize() == cardsRequired()) // through dynamic binding, for catchAllEvaluators it goes through this method.
		{
			return true;
		} 
		else
		{
			return false;
		}
	}

	@Override
	public boolean canSubsetSatisfy(Card[] allCards)
	{
		if(allCards.length<cardsRequired) 
		{
			return false;
		}
		
		ArrayList<int[]> allPossibleSubsets = SubsetFinder.findSubsets(allCards.length, cardsRequired());

		for (int[] subset : allPossibleSubsets)
		{
			Card[] mainCards = myMainCards(allCards, subset);

			if (canSatisfy(mainCards))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public Hand createHand(Card[] allCards, int[] subset)
	{
		if (allCards.length < handSize() || subset.length != cardsRequired)
		{
			return null;
		}

		Hand requiredHand = null;

		Card[] mainCards = myMainCards(allCards, subset);
		Card[] sideCards = mySideCards(allCards, subset);
		
		if(mainCards.length!=cardsRequired || mainCards.length + sideCards.length < handSize || !(canSatisfy(mainCards)) ) 
		{
			return null;
		}
		else
		{
			requiredHand = new Hand(mainCards, sideCards, this);
		}

		return requiredHand;
	}

	@Override
	public Hand getBestHand(Card[] allCards) 
	{
		Hand bestPossibleHand = null;
		
		ArrayList<int[]> bestSubSet = SubsetFinder.findSubsets(allCards.length, cardsRequired());

		bestPossibleHand = createHand(allCards, bestSubSet.get(0));

		for (int i = 0; i < bestSubSet.size(); i++)
		{
			Hand compareHand = createHand(allCards, bestSubSet.get(i));

			if (bestPossibleHand == null)
			{
				bestPossibleHand = compareHand;
			}

			else if (compareHand != null)
			{
				if (compareHand.compareTo(bestPossibleHand) < 0)
				{
					bestPossibleHand = compareHand;
				}
			}
		}

		return bestPossibleHand;
	}
	
	/*
	 * Helper method to get the main cards from the given subset.
	 */
	protected Card[] myMainCards(Card[] allCards, int[] subset) // helper to get the main cards would reduce redundancy and make the code more readable.
	{
		Card[] mainCards = new Card[subset.length];
		for (int i = 0; i < mainCards.length; i++)
		{
			mainCards[i] = allCards[subset[i]];
		}

		return mainCards;
	}

	/*
	 * Helper method to get the side cards.
	 */
	protected Card[] mySideCards(Card[] allCards, int[] subset) // helper to get the side cards would reduce redundancy and make the code more readable
	{
		ArrayList<Card> cardsList = new ArrayList<Card>();
		ArrayList<Card> mainCardsList = new ArrayList<Card>();
		ArrayList<Card> sideCardsList = new ArrayList<Card>();

		for (int i = 0; i < allCards.length; i++)
		{
			cardsList.add(allCards[i]);
		}

		for (int i = 0; i < subset.length; i++)
		{
			mainCardsList.add(allCards[subset[i]]);
		}

		for (int i = 0; i < allCards.length - subset.length; i++)
		{
			if (!(mainCardsList.contains(allCards[i])) && mainCardsList.size() != handSize())
			{
				sideCardsList.add(allCards[i]);
			}
		}

		Card[] mainCards = new Card[mainCardsList.size()];

		for (int i = 0; i < mainCards.length; i++)
		{
			mainCards[i] = mainCardsList.get(i);
		}

		Card[] sideCards = new Card[sideCardsList.size()];
		for (int i = 0; i < sideCards.length; i++)
		{
			sideCards[i] = sideCardsList.get(i);
		}

		return sideCards;
	}
}
