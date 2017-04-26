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
 * Written by John Enquist
 * 
 */

public class Card extends JPanel {
	
    private String shape;
    private String color;
    private String shading;
    private int count;
    private boolean selected;
    private BufferedImage cardImage; // the image we will import for the card object
	
   /*
    * Constructor for the card object, sets all values for the card and stores them
    * 
    *  @param color: the cards color
    *  @param shape: the shape(s) on the card
    *  @param shading: the shading of the shape(s) on the card
    *  @param count: the number of shapes on the card
    */
    public Card(String color, String shape, String shading, int count) 
    {

        this.setBorder(BorderFactory.createLineBorder(Color.black));

        selected = false;
        this.shape = shape;
        this.color = color;
        this.count = count;
        this.shading = shading;
        
        String getImg = "setImages/" + color + "_" + shape + "_" + shading + ".png"; //works in getting the files
//        System.out.println(getImg);

        try {
            URL theImageUrl = getClass().getResource(getImg);
            cardImage = ImageIO.read(theImageUrl);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        this.setBackground(Color.white);
    }
    
    /*
     * Gets the cards shape
     *
     * @returns the shape
     */
    public String getShape() 
    {
        return shape;
    }

    /*
     * Gets the cards color
     *
     * @return the color of the card
     */
    public String getColor()
    {
        return color;
    }

    /*
     * Gets the number of shapes on the card
     *
     * @return the number of shapes
     */
    public int getCount() 
    {
        return count;
    }

    /*
     * Gets the cards shading type
     *
     * @return the shading of the card
     */
    public String getShading() 
    {
        return shading;
    }


    /*
     * This method returns the current state of the Card
     *
     * @return true if Card has been selected by user.
     */
    public boolean isSelected()
    {

        return selected;
    }

    /*
     * This method returns a String representation of the Card
     *
     * @return a String of the cards values
     */
    public String toString() 
    {
        return color + " - " + shape + " - " + shading + " - " + count + " - " + selected;

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

        int xCoordinate = (int) Math.floor(this.getWidth() / 2) - 50;
        int yCoordinate = (int) Math.floor(this.getHeight() / 2);
        if (count == 1) {
            g.drawImage(cardImage, xCoordinate, yCoordinate - 25, null);
        } else if (count == 2) {
            g.drawImage(cardImage, xCoordinate, yCoordinate - 50, null);
            g.drawImage(cardImage, xCoordinate, yCoordinate, null);
        } else {
            g.drawImage(cardImage, xCoordinate, yCoordinate - 75, null);
            g.drawImage(cardImage, xCoordinate, yCoordinate - 25, null);
            g.drawImage(cardImage, xCoordinate, yCoordinate + 25, null);
        }

    }
    
    
}
