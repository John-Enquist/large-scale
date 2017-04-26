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


import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.math.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Gameboard  {
		private int cardNum = 72;

	 	private ArrayList<Card> theBoard;   // Holds the cards currently on the board.
	    private ArrayList<Card> selectedCards;  // Holds references to cards the user clicks and updates the array
	    private Deck currentDeck;   // Hold a reference to the current Deck in play.
	    private String playerName; //the current players name

		private int currentTurns; //tracks the number of turns taken
		
	    //private boolean boardPlayable;  // True if there is at least a valid SET in currentBoard.
	    public boolean gameOver;   // True when game has ended
	     
	    
	    /*
	     * Constructor for Gameboard. creates the set deck, shuffles it, and deals 12 cards
	     */
	    public Gameboard() {

	        // initializes the ArrayLists for the current board and the cards the user selects.
	        theBoard = new ArrayList<>();
	        selectedCards = new ArrayList<>();
	        currentDeck = new Deck();
	        currentDeck.shuffle();
	        currentTurns = 0; // sets current turns to 0

	        for (int i = 1; i <= cardNum; i++) {
	            // Note: Deck.deal() updates the cards left in the Deck.
	            theBoard.add(currentDeck.draw());
	        }
	        
	        // Print out the board
	        System.out.println(this.toString());
	    }
	    

	    
		public void removeCardsFromBoard(){
			cardNum = cardNum -2; //change back when done testing
			if(cardNum == 0){
				gameOver();
				
			}
		}
		
		public void setPlayerName(String theName){
			playerName = theName;
		}
		
		public String getPlayerName(){
			return playerName;
		}

	    
	    /*
	     * gets the current Board for the game
	     */
	    public ArrayList<Card> getCurrentBoard() {
	        return this.theBoard;
	    }


	    /*
	     * gets the deck being used
	     *
	     * @return the deck for the game
	     */
	    public Deck getCurrentDeck() {
	        return this.currentDeck;
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
	    
	    public ArrayList<Card> returnSelectedArray() {
	    	return this.selectedCards;
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
	     * returns a string representation of the board
	     */
	    public String toString() {
	        String currentBoard = "";
	        int index = 1;
	        for (Card card : this.theBoard) {
	            currentBoard += "[" + index + "] " + card.toString() + "\n";
	            index++;
	        }
	        return currentBoard;
	    }
	    
	    public int GetTurns(){
			return  currentTurns;
		}
	    
		public void IncrementTurns() {
			currentTurns += 1;
			
		}
		
		
		/*
		 * Given a card c, make the card's status as selected
		 */
		public void makeSelected(Card c) {
			int i = this.theBoard.indexOf(c);
			c.setSelect(true);
			this.theBoard.set(i, c);
		}
		
		/*
		 * Given a card c, make the card's status as deselected
		 */
		public void makeDeSelected(Card c) {
			int i = this.theBoard.indexOf(c);
			c.setSelect(false);
			this.theBoard.set(i, c);
		}
		
		public String gameOver(){
			System.out.println("game is over");
			gameOver = true;
			return playerName;
		}


}
