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
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends JApplet {
	
  private static final long serialVersionUID = 1L;
  private static final int two = 2;
  private Gameboard gbd; // the Game Board container
  
  //__________________________file writing stuff______________________________________
  public File scoreFile; // where the score will be kept for the game
  private int theHighScore; //tracks the current number of pairs matched
  
  //the line below creates an arraylist with objects of type (string, int) this will be used to store the high scores
  //you can add to this array by simply:
  //java.util.Map.Entry<String,Integer> highScore=new java.util.AbstractMap.SimpleEntry<>("Not Unique key2",2);
  //pairList.add(highScore);
  java.util.List<java.util.Map.Entry<String,Integer>> scoreList= new java.util.ArrayList<>();
  
  //__________________________________________________________________________

  JPanel actionPanel = new JPanel(); // all the buttons used for each game mode
  BoardGUI gameBoardGUI; //theGameBoard GUI to be displayed
  JLabel turnsCounter = new JLabel("Score to be displayed");
  PlayerInfo playerGUI;
  
  JButton removeButton = new JButton("Remove");
  JButton flipButton = new JButton("Flip");
  JButton quitButton = new JButton("Quit"); // button for quitting current game
  JButton newGameButton = new JButton("New Game"); // button for entering a new game 

  /*
   * Set up the buttons and board and register the listeners.
   */
  public void init() {

	  this.setSize(1200, 600); // sets the board size
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
      
      actionPanel.setBackground(Color.PINK);
      actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
      
      turnsCounter.setForeground(Color.BLUE);
      turnsCounter.setVisible(false);

      actionPanel.add(quitButton);
      quitButton.setVisible(false);
      actionPanel.add(newGameButton);
      newGameButton.setVisible(true);
      
      actionPanel.add(removeButton);
      removeButton.setVisible(false);
      actionPanel.add(flipButton);
      flipButton.setVisible(false);

 
      add("North", actionPanel);
      add("South", turnsCounter); // adding the message box to the center (starts invisible)
  }
  
  /*
   * Set up the button listeners
   */
  private void makeButtonListeners(){
	    quitButton.addActionListener(new QuitButtonListener());
	    removeButton.addActionListener(new RemoveButtionListener());
	    flipButton.addActionListener(new FlipButtonListener());
	    newGameButton.addActionListener(new NewGameButtonListener());  
  }
  
  
  /*
   * Flip the cards up if the cards' backs are currently showing.
   * Flip the cards down if the cards' fronts are currently showing.
   * 
   * Note: When the player flip cards without removing them first, 
   * 	   turns do not increment
   */
  private class FlipButtonListener implements ActionListener {
	  public void actionPerformed(ActionEvent event) {
		  
		  if (gbd.getSelected().size() == two) { // if there are 2 selected cards 
			  
			  Card cardA = gbd.getSelected().get(0);
			  Card cardB = gbd.getSelected().get(1);
			  
			  if ((cardA.isSelected()) && (cardB.isSelected())) {
				  if (cardA.compareTo(cardB)) {
					  turnsCounter.setText("thats a match, now remove them. " + " Number of turns: " + gbd.GetTurns());
					  return; // this is so that they cannot flip the cards back over if they are a match
				  }
				  gbd.makeDeSelected(cardA);
				  gbd.makeDeSelected(cardB);
				  
				  cardA.setBackground(Color.black);
				  cardB.setBackground(Color.black);
				  gbd.IncrementTurns();
				  turnsCounter.setText("better luck next time, " + " Number of turns: " + gbd.GetTurns());
				  
				  
				  gbd.clearSelected();
				  
			  } else {
				  gbd.makeSelected(cardA);
				  gbd.makeSelected(cardB);
				  
				  cardA.repaint();
				  cardB.repaint();
			  }  
			  
		  } else if (gbd.getSelected().size() == 1) { // if there is 1 selected card
//			  return;
			  Card c = gbd.getSelected().get(0);
			  System.out.println(c.toString());
			  
			  if (c.isSelected()) {
				  gbd.makeDeSelected(c);
				  c.setBackground(Color.black);
				  gbd.clearSelected();
				  
			  } else {
				  gbd.makeSelected(c);
				  c.repaint();
			  }  
		  }
	  }
  }
  
  
  /*
   * Remove the 2 selected cards if they are a set.
   * Do nothing except for suggesting using the Flip button if they are not.
   * 
   * Note: Remove Button makes sure that it will only run with 2 cards selected, no more, no less
   */
  private class RemoveButtionListener implements ActionListener {
	  public void actionPerformed(ActionEvent event) {
		  
		  if (gbd.getSelected().size() == two) { // if there are 2 cards selected 
//			  turnsCounter.setText("");
			  
			  Card cardA = gbd.getSelected().get(0);
			  Card cardB = gbd.getSelected().get(1);
			   
			  if (cardA.compareTo(cardB)) {
				  cardA.setVisible(false);
				  cardB.setVisible(false);
				  
				  gbd.IncrementTurns();
				  turnsCounter.setText("You just removed a set!" + " Number of turns: " + gbd.GetTurns());
				  
				  gbd.clearSelected();
				  gbd.removeCardsFromBoard();
				  if(gbd.gameOver){
					  setHighScore();
				  }
			  } else {
				  gbd.IncrementTurns();
				  turnsCounter.setText("That's not a set. Click on Flip to see them." 
						  				+ " Number of turns: " + gbd.GetTurns());
			  }
		  }  
	  }
  }
  
  
/*
 * Initialize the game, Draw all the cards, and Display them on the GUI
 */
  private class NewGameButtonListener implements ActionListener {
	  public void actionPerformed(ActionEvent event) {
		  removeButton.setVisible(true);
		  flipButton.setVisible(true);
		  quitButton.setVisible(true);
		  turnsCounter.setVisible(true);
	      gbd = new Gameboard();
		  playerGUI = new PlayerInfo(gbd);
	      if(gameBoardGUI != null){
	          remove(gameBoardGUI);
	      }
	      gameBoardGUI = new BoardGUI(gbd, turnsCounter); 
	      gameBoardGUI.setBackground(Color.lightGray);
	      add("Center", gameBoardGUI);
	      
	      ArrayList<Card> cardsOnBoard = gbd.getCurrentBoard();
	      for (Card card : cardsOnBoard) {
	          gameBoardGUI.addCard(card);
	      }	
	      
	  }
  }
  
  /*
   * Clear the game and the GUI.
   * Make remove and flip buttons invisible
   */
  private class QuitButtonListener implements ActionListener {
	  public void actionPerformed(ActionEvent event) {
		  gbd.clearBoard();
	      gameBoardGUI.clearPlayingBoard();
	      gameBoardGUI.repaint();
	      gameBoardGUI.revalidate(); 
	      
	      removeButton.setVisible(false);
	      flipButton.setVisible(false);
	  }
  }
  
  
	/*
	 * this method will be called at the instantiation of Game to create a save location for the high scores
	 */
	public void createScoreFile(){
  	try {

	      scoreFile = new File("c:\\highScores.txt");

	      if (scoreFile.createNewFile()){
	        System.out.println("File is created!");
	      }else{
	        System.out.println("File already exists.");
	      }

    	} catch (IOException e) {
	      e.printStackTrace();
	}
	}
	
	/*
	 * this method will run the highScoresGUI class, displaying the 10 top scores of previous games by reading from 
	 * the high scores file
	 */
	public String getHighScore() {
		try {
			FileReader fileReader = new FileReader(scoreFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			return stringBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * once the game is finished, this method will determine where the current players score fits with regards to 
	 * the already saved scores within the arraylist of scores, insert it appropriately, and rewrite the file
	 * accordingly. if the score does not make the top 10, it will not be added to the high scores list
	 */
	public void setHighScore(){
		createScoreFile();
		gbd.setPlayerName(playerGUI.getName());
//		System.out.println("the scorelist is: " + scoreList.toString());
		java.util.Map.Entry<String,Integer> theScore =new java.util.AbstractMap.SimpleEntry<>(gbd.getPlayerName(), gbd.GetTurns());
		
		//the line above makes the tuple object to be added to the high scores array
		if(scoreList.isEmpty()){
			scoreList.add(theScore); // it is the only score recorded right now
			writeHighScore(); //writes the high score to the file
			displayHighScores(getHighScore());
			return;
		}
		else {
			for(int i = 0; i < scoreList.size(); i++){
				int tempScore = scoreList.get(i).getValue(); //make sure this works
				if(gbd.GetTurns() < tempScore){
					scoreList.add(i, theScore);
					writeHighScore(); //writes the high score to the file
					displayHighScores(getHighScore());
					return;
				}
			}
			scoreList.add(theScore); // if it is larger than all the other scores, it will be added to the end
		}
		writeHighScore(); //writes the high score to the file
		displayHighScores(getHighScore());
		//need to copy the updated arraylist to file at the end of this function ***
	}
	
	/*
	 * writes the array of high scores to a file to be saved forever 
	 */
	public void writeHighScore(){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(scoreFile))) {
			for(int i = 0; i < scoreList.size(); i++){
				String content = scoreList.get(i).toString() + "\n";
				bw.write(content);
			}
			// no need to close it.
			//bw.close();
			System.out.println("Done");
		} catch (IOException e) {

			e.printStackTrace();

			}
	}
	/*
	 * displays the high scores on a jpanel (sorry couldnt make it look prettier)
	 */
	public void displayHighScores(String highScores){
		
        // We create a bottom JPanel to place everything on.
        JPanel playerInfoGUI = new JPanel();
        playerInfoGUI.setLayout(null);

        JLabel titleLabel = new JLabel("-___High Scores___-");
        titleLabel.setLocation(0,0);
        titleLabel.setSize(290, 30);
        titleLabel.setHorizontalAlignment(0);
        playerInfoGUI.add(titleLabel);
        
        JPanel textPanel = new JPanel(); //lets you put text on it
        textPanel.setLayout(null);
        textPanel.setLocation(10, 35);
        textPanel.setSize(290, 100);
        playerInfoGUI.add(textPanel);
        
        // Username Label
	    String toDisplay = "";
	    String[] splited = highScores.split("\\s+"); // a string array of the scores 
	    for(int i = 0; i < splited.length; i++){ // 10 possible high scores
	    	toDisplay = toDisplay.concat(i + ": " + splited[i].toString() + '\n');
	    }
        JLabel usernameLabel = new JLabel(toDisplay);
        usernameLabel.setLocation(0, 0);
        usernameLabel.setSize(280, 90);
        usernameLabel.setHorizontalAlignment(0);
        textPanel.add(usernameLabel);
        
        JFrame frame = new JFrame("High Scores List");
        frame.setContentPane(playerInfoGUI);
        frame.setSize(310, 200);
        frame.setVisible(true);
	}
}


