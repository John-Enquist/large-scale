/**
 * Gameboard.java
 * 
 * This class stores activities performed by different buttons.
 * Gameboard is called in Game.java
 * 
 *
 * @author John Enquist, An Nguyen, Kim Wellington
 */

import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Gameboard  {
	 	public ArrayList<Card> theBoard;   // Holds the cards currently on the board.
	    public ArrayList<Card> selectedCards;  // Holds references to cards the user clicks and updates the array
	    private Deck currentDeck;   // Hold a reference to the current Deck in play.

	    private String whySetIsInvalid; //used to display a message as to why the set was invalid
	    private boolean boardPlayable;  // True if there is at least a valid SET in currentBoard.
	    private boolean gameOver;   // True when game has ended

	    public ArrayList<ArrayList<Card>> setsAvailable;   // Holds the SETs currently on the Board
	    private int setsAvailableIndex; // used to hold the index of the last set displayed
	    
	    /*
	     * Constructor for gameboard. creates the set deck, shuffles it, and deals 12 cards
	     */
	    public Gameboard() {

	        // initializes the ArrayLists for the current board and the cards the user selects.
	        theBoard = new ArrayList<>();
	        selectedCards = new ArrayList<>();
	        whySetIsInvalid = "";

	        currentDeck = new Deck();
//	        System.out.println(currentDeck.toString()); //check the deck
	        currentDeck.shuffle();

	        for (int i = 0; i < 12; i++) {
	            // Note: Deck.deal() updates the cards left in the Deck.
	            theBoard.add(currentDeck.draw());
	        }
	        System.out.println(theBoard.toString());
	        setsAvailable = new ArrayList<ArrayList<Card>>();
	        setsAvailableIndex = 0;
	    }
	    public void resetSetIndex(){
	    	setsAvailableIndex = 0;
	    }

	    /**
	     * Find all sets in a deck. 
	     * 
	     * @return the setArray, which contains arrays of sets 
	     */
	    public ArrayList<ArrayList<Card>> findSets() {
	    	
	    	ArrayList<Card> tempArray = new ArrayList<Card>(3); // temporary that store a set of cards so its size is 3 
	    	
	    	int cardNum = theBoard.size();
	    	
	    	if (theBoard != null) {
	    		for (int i = 0; i < cardNum; i++) {
	    			Card cardA = theBoard.get(i); // first card 
	    			for (int n = 1; n < cardNum; n++) {
	    				Card cardB = theBoard.get(n); // second card 
	    				for (int m = 2; m < cardNum; m++) {
	    					Card cardC = theBoard.get(m); // third card 
	    					if (isSet(cardA, cardB, cardC)){
	    						tempArray.add(cardA);
	    						tempArray.add(cardB);
	    						tempArray.add(cardC); 
	    						if (firstSet(tempArray)) {
	    							setsAvailable.add(tempArray);
	    						}
	    						tempArray = new ArrayList<Card>(3); // reset tempArray to blank after every call
	    					}
	    				}
	    			}
	    		}
	    	}
	    	return setsAvailable;
	    }

	    /**
	     * Given a set array and an array of all sets, check if
	     * the permutations of the set array is already in the array of all sets.
	     * If yes, return false. Return true otherwise
	     * 
	     * @param setArray = the array contains a set
	     * @param setsAvailable = the array contains all sets 
	     * @return
	     */
	    private boolean firstSet(ArrayList<Card> setArray) {
	    	Card cardA = setArray.get(0);
	    	Card cardB = setArray.get(1);
	    	Card cardC = setArray.get(2);
	    	
	    	ArrayList<Card> set1 = new ArrayList<Card>();
	    	set1.add(cardA);
	    	set1.add(cardC);
	    	set1.add(cardB);
	    	
	    	ArrayList<Card> set2 = new ArrayList<Card>();
	    	set2.add(cardB);
	    	set2.add(cardA);
	    	set2.add(cardC);
	    	
	    	ArrayList<Card> set3 = new ArrayList<Card>();
	    	set3.add(cardB);
	    	set3.add(cardC);
	    	set3.add(cardA);
	    	
	    	ArrayList<Card> set4 = new ArrayList<Card>();
	    	set4.add(cardC);
	    	set4.add(cardA);
	    	set4.add(cardB);
	    	
	    	ArrayList<Card> set5 = new ArrayList<Card>();
	    	set5.add(cardC);
	    	set5.add(cardB);
	    	set5.add(cardA);
	    	
	    	if (setsAvailable.contains(set1) || setsAvailable.contains(set2) || setsAvailable.contains(set3) || setsAvailable.contains(set4) || setsAvailable.contains(set5)) {
	    		return false;
	    	} return true;
	
	    }
	    
	    /**
	     * Check if three cards are a set 
	     * Return true if 3 cards are a set, false otherwise
	     * 
	     * @param cardA
	     * @param cardB
	     * @param cardC
	     * @return
	     */
	    public boolean isSet(Card cardA, Card cardB, Card cardC) { 
	    	if ((compareCardShapes(cardA, cardB, cardC) == false) || (compareCardColors(cardA, cardB, cardC) == false) || 
	    			(compareCardCounts(cardA, cardB, cardC) == false) || (compareCardShadings(cardA, cardB, cardC) == false)) {
	    		return false;
	    	} else if (cardA.toString().equals(cardB.toString())){
	    		return false;
	    	} else {
	    		return true;
	    	}
	    }
	    
	    /**
	     * Return false of two cards have the same shape and the third does not 
	     * Return true otherwise 
	     * @param cardA
	     * @param cardB
	     * @param cardC
	     * @return
	     */
	    private boolean compareCardShapes(Card cardA, Card cardB, Card cardC) {
			 if ((cardA.getShape() == cardB.getShape()) && (cardB.getShape() != cardC.getShape())){
				 return false;
			 } else if ((cardA.getShape() != cardB.getShape()) && (cardB.getShape() == cardC.getShape())){
				 return false;
			 } else if ((cardA.getShape() == cardC.getShape()) && (cardB.getShape() != cardA.getShape())){
				 return false;
			 } else { 
				 return true;	 
			 }
		 }	

		 /**
		  * Returns false if two colors match but the third does not
		  * @param cardA the first card to be compared
		  * @param cardB the second card to be compared
		  * @param cardC the third card to be compared
		  */
		 private boolean compareCardColors(Card cardA, Card cardB, Card cardC){
			 if ((cardA.getColor() == cardB.getColor()) && (cardB.getColor() != cardC.getColor())){
				 return false;
			 } else if ((cardA.getColor() != cardB.getColor()) && (cardB.getColor() == cardC.getColor())){
				 return false;
			 } else if ((cardA.getColor() == cardC.getColor()) && (cardB.getColor() != cardA.getColor())){
				 return false;
			 } else { 
				 return true; 
			 }
		 }
		 
		 /**
		  * Returns false if two counts match but the third does not
		  * @param cardA the first card to be compared
		  * @param cardB the second card to be compared
		  * @param cardC the third card to be compared
		  */
		 private boolean compareCardCounts(Card cardA, Card cardB, Card cardC){
			 if ((cardA.getCount() == cardB.getCount()) && (cardB.getCount() != cardC.getCount())){
				 return false;
			 } else if ((cardA.getCount() != cardB.getCount() ) && (cardB.getCount() == cardC.getCount())){
				 return false;
			 } else if ((cardA.getCount() == cardC.getCount()) && (cardB.getCount() != cardA.getCount())){
				 return false;
			 } else { 
				 return true; 
			 }
		 }
		 
		 /**
		  * Returns false if two shadings match but the third does not
		  * @param cardA the first card to be compared
		  * @param cardB the second card to be compared
		  * @param cardC the third card to be compared
		  */
		 private boolean compareCardShadings(Card cardA, Card cardB, Card cardC){
			 if ((cardA.getShading() == cardB.getShading()) && (cardB.getShading() != cardC.getShading())){
				 return false;
			 } else if ((cardA.getShading() != cardB.getShading()) && (cardB.getShading() == cardC.getShading())){
				 return false;
			 } else if ((cardA.getShading() == cardC.getShading()) && (cardB.getShading() != cardA.getShading())){
				 return false;
			 } else { 
				 return true;	 
			 }
		 }
	    
		public void removeCardsFromBoard(Card cardA, Card cardB, Card cardC){
			theBoard.remove(cardA);
			theBoard.remove(cardB);
			theBoard.remove(cardC);
		}
		 
	    // gets the last used index of the sets available array
	    public int getSetsAvailableIndex() {
	        return setsAvailableIndex;
	    }

	    
	    /*
	     * gets the current Board for the game
	     */
	    public ArrayList<Card> getCurrentBoard() {
	        return theBoard;
	    }


	    /*
	     * gets the deck being used
	     *
	     * @return the deck for the game
	     */
	    public Deck getCurrentDeck() {
	        return currentDeck;
	    }


	    /*
	     * returns the next set in setsAvailable.
	     *
	     * @return the next valid SET in setsAvailable.
	     */
	    public ArrayList<Card> getNextSetAvaialble() {
	    	int setNum = setsAvailable.size();
	    	System.out.println("The num of sets is: ");
	    	System.out.println(setNum);
	    	if (setsAvailableIndex < setNum) {
		        ArrayList<Card> nextSet = setsAvailable.get(setsAvailableIndex);
		        setsAvailableIndex += 1;
		        return nextSet;
	    	} else {
	    		return null;
	    	}
	    }
	

	    /*
	     * returns a reference to an ArrayList of ArrayLists of the sets on the board currently.
	     *
	     * @return a reference to the sets.
	     */
	    public ArrayList<ArrayList<Card>> getSetsAvailable() {
	        return setsAvailable;
	    }


	    /*
	     * This method returns the reason why the last three cards selected were not a set, if they were not.
	     *
	     * @return a String containing why last three cards selected were not a set.
	     */
	    public String getNotASet() {
	        return whySetIsInvalid;
	    }


	    /*
	     * This method adds a Card to the current Board
	     *
	     * @return the card added
	     */
	    public Card addCardFromDeck() {

	        Card cardFromDeck = null;

	        if (!currentDeck.isEmpty()) {
	            cardFromDeck = currentDeck.draw();
	            theBoard.add(cardFromDeck);

	        }

	        return cardFromDeck;
	    }


	    /*
	     * This method adds a user selected Card
	     *
	     * @param selectedCard the Card the user selects
	     */
	    public void addCardsToSelected(Card selectedCard) {

	        selectedCards.add(selectedCard);
	    }
	    
	    /*
	     * This method clears the cardsSelected array
	     */
	    public void clearSelected() {
	        selectedCards = new ArrayList<>();

	    }
	    
	    /*
	     * clears the cards currently on the board by emptying the array
	     */
	    public void clearBoard() {
	    	theBoard = new ArrayList<>();
	    }
	    
	    /*
	     *removes a user selected Card
	     *
	     * @param selectedCard the Card the user selects
	     */
	    public void removeCardsFromSelected(Card selectedCard) {
	        selectedCards.remove(selectedCard);
	    }
	    
	    /*
	     *gets the current selected Cards
	     *
	     * @return the Cards the user selects
	     */
	    public ArrayList<Card> getSelected() {
	        return selectedCards;
	    }
	    
	    /*
	     * Draw next 12 cards 
	     */
	    public void next12Cards(){

	    	//System.out.println("___first_____");

	    	//System.out.println(theBoard.toString());
	    	//System.out.println("________");
	    	for (int i = 0; i < 12; i++) {  		// adding new 12 cards to the board 
	            theBoard.add(currentDeck.draw());
	    	}
	    }
	    
	    /*
	     * returns a string representation of the board
	     */
	    public String toString() {
	        String currentBoard = "";
	        int index = 1;
	        for (Card card : this.theBoard) {
	            currentBoard += index + " " + card.toString() + "\n";
	            index++;
	        }
	        return currentBoard;
	    }

}
