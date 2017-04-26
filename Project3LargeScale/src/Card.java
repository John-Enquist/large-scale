import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/*
 * The card class is used as a container for a card type object used in the game set.
 * This class is used in the deck and gameboard class to play the game set
 * 
 * Written by An Nguyen
 * 
 */

public class Card extends JPanel {
	
	private int cardID; 
    private boolean selected;
    private int cardsDiff = 36;
    private BufferedImage cardImage; // the image we will import for the card object
    private BufferedImage cardBack; // the back of all cards 
	
   /*
    * Constructor for the card object, sets all values for the card and stores them
    * 
    *  @param cardID: the ID of the card 
    */
    public Card(int cardID) 
    {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.black);
        this.cardID = cardID;
        this.selected = false;
      
        String getImg = "setImages/" + cardID + ".png"; //works in getting the files

        try {
            URL theImageUrl = getClass().getResource(getImg);
            cardImage = ImageIO.read(theImageUrl);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Get card's ID
     * 
     * @return cardID
     */
    public int getID() {
    	return cardID;
    }
    

    /*
     * This method returns the current state of the Card
     *
     * @return true if Card has been selected by user.
     */
    public boolean isSelected() {
        return selected;
    }

    /*
     * This method returns a String representation of the Card
     *
     * @return a String of the cards values
     */
    public String toString() {
        return cardID + " - " + selected;
    }

    /*
     * This method sets whether the Card has been selected
     *
     * @param selected: a boolean to select or deselect the card value
     */
    public void setSelect(boolean selected) 
    {
        this.selected = selected;
    }
    
    /*
     * overrides the paintComponent method of JPanel to create the set card
     * credit goes to https://github.com/SWhelan/Set/blob/master/CardPanel.java
     *
     * @param g: the current Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int xCoordinate = (int) Math.floor(this.getWidth() / 2 - 90);
        int yCoordinate = (int) Math.floor(this.getHeight() / 2 - 90);
 
        
        if (selected) { //switch back when done testing
        	g.drawImage(cardImage, xCoordinate, yCoordinate, null);
        } else {
        	g.drawImage(cardBack, xCoordinate, yCoordinate, null);
        }
    }
    
    /*
     * compares two instances of cards to one another and returns true if they have the same image
     * 
     * @param aCard: the card to be compared to this card
     * @return boolean: returns true if the cards match, otherwise returns false
     */
    public boolean compareTo(Card aCard){
    	int diff = Math.abs(this.getID()-aCard.getID());
    	if(diff == cardsDiff){
    		return true;
    	}
    	return false;
    }
    
    
}
