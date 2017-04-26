/**
 * BoardGUI.java
 * Create an applet to display cards using data taken from Gameboard.java
 *
 * @author John Enquist, An Nguyen
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class BoardGUI extends JPanel implements MouseListener {

	Gameboard theBoard; // reference to the gameboard container object
	
	/*
	 * constructor for the gameboard gui, creates a gui with a grid layout to be used for adding cards
	 * 
	 * @param currentBoard: the gameboard container object
	 * @param solitaireMode: a boolean to determine what game mode the board should be in right now
	 * (if it is in tutorial mode then the user should not be clicking on the board)
	 */
    public BoardGUI(Gameboard currentBoard, boolean solitaireMode) {

        // Stores the currentPlayingBoard from when the user clicked on a Deal listener.
        theBoard = currentBoard;

        // initializes mouseListener for solitiare mode.
        if (solitaireMode) {
            addMouseListener(this);
        }
        // use setLayout to get the cards to display properly.
        this.setLayout(new GridLayout(3, 5, 5, 5));
    }
	

   /* adds a Card to the visual representation of the current board.
    *
    * @param cardToAdd a Card object to display on the PlayingBoard.
    */
   public void addCard(Card cardToAdd) {

       this.add(cardToAdd); // .add() method is from JPanel
       this.revalidate(); // used to refresh the game board
   }
   
   public void clearPlayingBoard() {

       Component[] cards = this.getComponents();
       for (Component curCard : cards) {
    	   //System.out.println(curCard.toString());
           this.remove(curCard);
       }
   }
   
   
   /*
    * This method adds cards to the current PlayingBoard according to the state of the current Deck.
    */
   public void restoreBoard() {

       Deck currentDeck = theBoard.getCurrentDeck();
       int cardsLeft = currentDeck.getCardsLeft();
       boolean deckHasCards = cardsLeft >= 3;

       int currentSize = theBoard.getCurrentBoard().size();
       if (currentSize < 12) {
           if (deckHasCards) {
               for (int index = 0; index < 3; index++) {
                   this.add(theBoard.addCardFromDeck());
               }
           } else {
               for (int index = 0; index < 3; index++) {
                   JPanel emptyCard = new JPanel();
                   emptyCard.setBackground(Color.green);
                   this.add(emptyCard);
               }
           }
       }
       this.revalidate();
   }
   
   /*
    * Private helper method for giving the user visual feedback and modifying the state of a Card.
    */
   private void cardSelection(Card selectedCard) {

       if (selectedCard.isSelected()) {
           selectedCard.setBackground(Color.white);
           selectedCard.setSelect(false);
           theBoard.removeCardsFromSelected(selectedCard);
       } else {
    	   if(theBoard.getSelected().size() < 3){ //this is so only 3 cards can be selected at a time
               selectedCard.setBackground(Color.lightGray);
               selectedCard.setSelect(true);
               theBoard.addCardsToSelected(selectedCard); 
    	   }

       }
   }
   
   /*
    * This is only called when the 3 cards are confirmed to be a set.
    * 
    * NOTE: This does not check whether these 3 cards are a set.
    * That task is handled in RemoveCmd
    * 
    * @param cardA
    * @param cardB
    * @param cardC
    */
   public void removeSelectedSet(Card cardA, Card cardB, Card cardC) {
	   remove(cardA);
	   remove(cardB);
	   remove(cardC);
   }
   
   /**
    * Dehighlight all the cards on the set 
    */
   public void deHighLight() {
	   ArrayList<Card> currentBoard = theBoard.theBoard;
	   for (int i = 0; i < currentBoard.size(); i++) {
		   if (currentBoard.get(i).isSelected()) {
			   currentBoard.get(i).setBackground(Color.WHITE);
			   currentBoard.get(i).setSelect(false);
		   }
		   this.revalidate();
	   }
   }

   /*
    * This method sets the card to selected if the users click on it. If the users clicks again on it again, this
    * method does the opposite.
    *
    * @param e A MouseEvent
    */
   @Override
   public void mouseClicked(MouseEvent e) {

       Object userClickedOn = getComponentAt(e.getPoint());
       
       if (userClickedOn instanceof Card) {
           Card selectedCard = (Card) userClickedOn; // Cast the Card the user clicked on.
           cardSelection(selectedCard);
       }    
   }

	//dont need these methods
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }

}
