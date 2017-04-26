/**
 * BoardGUI.java
 * Create an applet to display cards using data taken from Gameboard.java
 *
 * @author John Enquist, An Nguyen
 */
import java.awt.CardLayout;
import javax.swing.*;

import javafx.scene.control.Cell;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class BoardGUI extends JPanel implements MouseListener {

	Gameboard theBoard; // reference to the Gameboard container object
	private JLabel scoreLabel;
	
	/*
	 * constructor for the Gameboard GUI, creates a GUI with a grid layout to be used for adding cards
	 * 
	 * @param currentBoard: the Gameboard container object
	 * @param solitaireMode: a boolean to determine what game mode the board should be in right now
	 * (if it is in tutorial mode then the user should not be clicking on the board)
	 */
    public BoardGUI(Gameboard currentBoard, JLabel scoreLabel) {
        theBoard = currentBoard; // Stores the currentPlayingBoard object
        scoreLabel.setText("num turns: " + theBoard.GetTurns()); //initializes the players current score
        this.scoreLabel = scoreLabel;
        addMouseListener(this);
        this.setLayout(new GridLayout(6, 12, 5, 5));
    }
	
    
   /* adds a Card to the visual representation of the current board.
    *
    * @param cardToAdd a Card object to display on the PlayingBoard.
    */
   public void addCard(Card cardToAdd) {

       this.add(cardToAdd); // .add() method is from JPanel
       this.revalidate(); 	// used to refresh the game board
   }
   
   /*
    * clears the playing board of all cards
    */
   public void clearPlayingBoard() {
       Component[] cards = this.getComponents();
       for (Component curCard : cards) {
           this.remove(curCard);
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
    	   Card selectedCard = (Card) userClickedOn;
    	   
    	   scoreLabel.setText("num turns: " + theBoard.GetTurns());
    	   
    	   if (selectedCard.getBackground() == Color.yellow) {
    		   return; //you should not be able to de-select a card once it is selected
    	   } else if (selectedCard.getBackground() == Color.black){
        	   if (this.theBoard.getSelected().size() < 2) {
        		   selectedCard.setBackground(Color.yellow);
        		   this.theBoard.addCardsToSelected(selectedCard);
        	   } 
    	   }
       }  
   }

	//don't need these methods
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	

}
