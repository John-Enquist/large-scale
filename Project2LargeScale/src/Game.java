/**
 * Game.java
 * 
 * Keeps track of what button is being clicked
 * and call Gameboard to perform the appropriate activities
 * and call BoardGUI to put the correct cards on the table
 * 
 *
 * @author John Enquist, An Nguyen, Kim Wellington
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Game extends JApplet {
	
  private static final long serialVersionUID = 1L;
  
  private Command cmd; // the command being executed
  private Gameboard gbd; // the gameboard container
  
//  private Next12Cmd draw12cmd; 
 

  
  // Sidebar container that holds JButtons
  JPanel gameModePanel = new JPanel(); // contains tutorial, solitaire, or quit buttons
  JPanel actionPanel = new JPanel(); // all the buttons used for each game mode
  JLabel messageBox = new JLabel("here"); // used for displaying messages
  BoardGUI theGameBoard; //theGameBoard gui to be displayed

  
  
  
  // Make JButton objects for all the command buttons.
  JButton solitaireButton = new JButton("solitaire"); // button for solitaire mode
  JButton tutorialButton = new JButton("tutorial"); // button for tutorial mode
  
  JButton removeButton = new JButton("remove"); // button for removing a set
  JButton drawButton = new JButton("draw"); // button for drawing cards
  JButton quitButton = new JButton("quit"); // button for quitting current game
  JButton nextButton = new JButton("next 12"); // button for next 12 in tutorial mode
  JButton showSetButton = new JButton("show set"); //button used for showing sets in tutorial mode
  JButton hintButton = new JButton("hint"); // button used for solitaire mode, shows hint for a set

  
  /*
   * Set up the buttons and board and register the listeners.
   */
  public void init() {

	  this.setSize(800, 600); // sets the board size
	  createLayout(); // creates the layout of the board
	  makeButtonListeners(); // creates the button listeners for the buttons
	  

  }
  /*
   * creates the boards layout
   */
  private void createLayout(){
	  setLayout(new BorderLayout());
	  
      /*
      Changes the background color of the game mode panel (right side) to green.
      Sets is layout to BoxLayout().
      */
      gameModePanel.setBackground(Color.orange);
      gameModePanel.setLayout(new BoxLayout(gameModePanel, BoxLayout.X_AXIS));
      
      actionPanel.setBackground(Color.PINK);
      actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
      
      messageBox.setForeground(Color.BLUE);
      messageBox.setVisible(false);

      // Adds buttons to control_panel.
      gameModePanel.add(tutorialButton);
      gameModePanel.add(solitaireButton);
      
      //buttons used for solitaire mode (added to action panel)
      actionPanel.add(removeButton);
      removeButton.setVisible(false);
      actionPanel.add(hintButton);
      hintButton.setVisible(false);
      actionPanel.add(drawButton);
      drawButton.setVisible(false);
      actionPanel.add(quitButton);
      quitButton.setVisible(false);
      
      //buttons used for tutorial mode (added to action panel
      actionPanel.add(quitButton);
      quitButton.setVisible(false);
      actionPanel.add(nextButton);
      nextButton.setVisible(false);
      actionPanel.add(showSetButton);
      showSetButton.setVisible(false);
      
//      add("North", set_game); would be used to display the title "set"
      add("North", gameModePanel);
      add("East", actionPanel);
      add("South", messageBox); // adding the message box to the center (starts invisible)
  }
  
  //sets up all button listeners for the player panel
  private void makeButtonListeners(){
	    // Add listeners for all the command buttons.
	    solitaireButton.addActionListener(new SolitaireButtonListener());
	    tutorialButton.addActionListener(new TutorialButtonListener());
	    removeButton.addActionListener(new RemoveButtonListener());
	    drawButton.addActionListener(new DrawButtonListener());
	    quitButton.addActionListener(new QuitButtonListener());
	    nextButton.addActionListener(new NextButtonListener());
	    showSetButton.addActionListener(new ShowSetButtonListener());
	    hintButton.addActionListener(new HintButtonListener());
  }

  /**
   * What to do when Solitaire button is pressed.
   */
  private class SolitaireButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
    
    	// sets the buttons used for solitaire mode to visible so they can be used
      hintButton.setVisible(true); 
      removeButton.setVisible(true);
      drawButton.setVisible(true);

      quitButton.setVisible(true);
      nextButton.setVisible(false);
      showSetButton.setVisible(false);
      messageBox.setVisible(true);
      messageBox.setText("Welcome to Solitaire Mode! Match sets to finish the game :)");
      gbd = new Gameboard();
      if(theGameBoard != null){
          remove(theGameBoard);
      }

      theGameBoard = new BoardGUI(gbd, true); // true b/c in solitaire mode
      theGameBoard.setBackground(Color.lightGray);
      add("Center", theGameBoard);
//      System.out.println(theGameBoard.theBoard.toString()); //checking to see if board is there

      ArrayList<Card> cardsOnBoard = gbd.getCurrentBoard();
      for (Card card : cardsOnBoard) {
          theGameBoard.addCard(card);
      }
      gbd.findSets(); 
      theGameBoard.revalidate();
    }
  }

  /**
   * What to do when Tutorial button is pressed.
   */
  private class TutorialButtonListener implements ActionListener {
	    public void actionPerformed(ActionEvent event) {
	      //sets the buttons used for tutorial mode to visible so they can be used
	      hintButton.setVisible(false); 
	      removeButton.setVisible(false);
	      drawButton.setVisible(false);
	      quitButton.setVisible(true);
	      nextButton.setVisible(true); // next12 cmd
	      showSetButton.setVisible(true);
	      
	        gbd = new Gameboard();
	        if(theGameBoard != null){
	            remove(theGameBoard);
	        }

	        theGameBoard = new BoardGUI(gbd, false); // false b/c in tutorial mode
	        theGameBoard.setBackground(Color.LIGHT_GRAY);
	        add("Center", theGameBoard);
	        ArrayList<Card> cardsOnBoard = gbd.getCurrentBoard();
	        for (Card card : cardsOnBoard) {
	            theGameBoard.addCard(card);
	        }
	       	gbd.findSets(); 
	        messageBox.setVisible(true);
	        messageBox.setText("Welcome to Tutorial Mode! hit show set to see the sets on the board. When you are ready, hit next 12 for a new board");
	        theGameBoard.revalidate(); 
	    }
  }

  /**
   * What to do when removeButton is pressed.
   */
  private class RemoveButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
    	if(gbd.getSelected().size() == 3){
    		Card cardA = gbd.selectedCards.get(0);
    		Card cardB = gbd.selectedCards.get(1);
    		Card cardC = gbd.selectedCards.get(2);
    		if(gbd.isSet(cardA, cardB ,cardC ) == true){
    			theGameBoard.removeSelectedSet(cardA, cardB, cardC);
    			gbd.selectedCards = new ArrayList<Card>();
    			gbd.removeCardsFromBoard(cardA, cardB, cardC);
    			theGameBoard.restoreBoard();
    	        gbd.setsAvailable = new ArrayList<ArrayList<Card>>();
    	        gbd.resetSetIndex();
    	       	gbd.findSets(); 
    	       	if(gbd.getSetsAvailable().size() == 0){
    	            messageBox.setText("No more sets available, Game Over!");
    	       	}
    	        messageBox.setText("Set removed, good job!");
    		}
    		else {
    		      messageBox.setText("Sorry thats not a set :(");
    		}
    	}
    	theGameBoard.revalidate();
    }
  }

  /**
   * What to do when drawButton is pressed.
   */
  private class DrawButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
    	if(gbd.getCurrentBoard().size() <= 12){
    		if(gbd.getCurrentDeck().getCardsLeft() == 0){
    		      messageBox.setVisible(true);
    		      messageBox.setText("no cards left in the deck");
    		      if(gbd.findSets() == null){
    		          messageBox.setText("There are no cards left in the deck and no sets left on the baord... GAME OVER");
    		      }
    		}
    		for(int i= 0; i< 3; i++){
    			theGameBoard.addCard(gbd.addCardFromDeck());
    			
    		}
	        gbd.setsAvailable = new ArrayList<ArrayList<Card>>();
	        gbd.resetSetIndex();
			gbd.findSets();
			
    	}
    	else {
    	      messageBox.setVisible(true);
    	      messageBox.setText("3 more cards added");
    	}

    	theGameBoard.revalidate();
    }
  }

  /**
   * What to do when quitButton is pressed.
   */
  private class QuitButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      gbd.clearBoard();
      theGameBoard.clearPlayingBoard();
      nextButton.setVisible(false);				
      showSetButton.setVisible(false);
      removeButton.setVisible(false);
      hintButton.setVisible(false);
      drawButton.setVisible(false);
      messageBox.setVisible(false);
      theGameBoard.revalidate();
      messageBox.setVisible(false);
    }
  }

  /**
   * What to do when next12Button is pressed.
   */
  private class NextButtonListener implements ActionListener { // add next 12
	    public void actionPerformed(ActionEvent event) {
	    	gbd.clearBoard();
	        for (int i = 0; i < 12; i++) {
	        	gbd.addCardFromDeck();
	        }
	        theGameBoard.clearPlayingBoard();
	        ArrayList<Card> cardsOnBoard = gbd.getCurrentBoard();
	        for (Card card : cardsOnBoard) {
	            theGameBoard.addCard(card);
	        }
	        gbd.setsAvailable = new ArrayList<ArrayList<Card>>();
	        gbd.resetSetIndex();
	       	gbd.findSets(); 
	        messageBox.setText("the next 12 cards have been added. There are " + gbd.getCurrentDeck().getCardsLeft() + " cards left in the deck.");
	        theGameBoard.revalidate();
	    }
	  }

  /**
   * What to do when showSetButton is pressed.
   */
  private class ShowSetButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
    	
    	theGameBoard.deHighLight();
    	
    	ArrayList<Card> currentSet = gbd.getNextSetAvaialble();
    	if(currentSet == null){
    	      messageBox.setVisible(true);
    	      messageBox.setText("No more sets available :(");
    		return;
    	}  	
		for (int m = 0; m < 3; m++) {

			currentSet.get(m).setSelect(true);
			currentSet.get(m).setBackground(Color.lightGray);
		}
		revalidate(); 	
    }
  }

  /**
   * What to do when hintButton is pressed. 
   */
  private class HintButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
    	
    	ArrayList<Card> currentSet = gbd.getNextSetAvaialble();
    	theGameBoard.deHighLight(); // get rid of any highlighted cards to show the hint
    	gbd.clearSelected();
  		if(currentSet != null){
  	  		currentSet.get(0).setSelect(true);
  	  		gbd.addCardsToSelected(currentSet.get(0));
  	  		currentSet.get(0).setBackground(Color.lightGray);
  	  		currentSet.get(1).setSelect(true);
  	  		gbd.addCardsToSelected(currentSet.get(1));
  	  		currentSet.get(1).setBackground(Color.lightGray);
  	      messageBox.setVisible(true);
  	      messageBox.setText("There is a card that completes this set");
  		}
  		else{
  			gbd.resetSetIndex(); //loop us back to the original set
  			messageBox.setVisible(true);
  			messageBox.setText("You have gone through all the set hints");
  			
  			if(gbd.getCurrentDeck().getCardsLeft() == 0){
		      messageBox.setVisible(true);
		      messageBox.setText("There are no cards left in the deck and no sets left on the baord... GAME OVER");
		      if(gbd.findSets() == null){
		          messageBox.setText("There are no cards left in the deck and no sets left on the baord... GAME OVER");
		      }
  			}
  		}
  		
  		revalidate();

    }
    
  }
  

    /*
     * When the mouse is clicked, call the executeClick method of the
     * current command.
     */
    public void mouseClicked(MouseEvent event) {
      cmd.executeClick(event.getPoint(), gbd);
      repaint();
    }
}


