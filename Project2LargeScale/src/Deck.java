
import java.util.ArrayList;
import java.util.Collections;

/*
 * Written by John Enquist
 * lalalala
 * the deck class is responsible for creating a deck of set cards, and dealing them
 */
public class Deck {

    private static String[] cardColors = {"green", "purple", "red"};
    private static String[] cardShapes = {"diamond", "oval", "squiggly"};
    private static String[] cardShades = {"empty", "filled", "lined"};
    int[] cardCounts = {1, 2, 3};

    private ArrayList<Card> cards;
    private int cardsLeft;
	
	
    /*
     * Deck object Constructor
     * iterates through the card attributes making a total of 81 cards
     */
    public Deck() {
        cards = new ArrayList<>();
        for (String color : cardColors) {
            for (String shape : cardShapes) {
                for (String shade : cardShades) {
                    for (int count : cardCounts) {
                        Card newCard = new Card(color, shape, shade, count);
                        cards.add(newCard);
                        cardsLeft += 1;
                    }
                }
            }
        }
        
    }
    
    
    /*
     * a built in method for shuffling a deck of cards
     */
    public void shuffle() {
        Collections.shuffle(cards); //shuffles the order of the cards in the deck
    }
    
    
    /*
     * gets the top card from the deck, and removes it. 
     * updates the instance variable cardsLeft.
     *
     * @returns: the Card on top of the Deck
     */
    public Card draw() {
        cardsLeft -= 1;
        Card theCard = cards.get(0);
        cards.remove(theCard); //built in remove method for arraylists
        return theCard;

    }
    
    /*
     * returns the number of cards left in the current Deck.
     *
     * @returns: the number of cards left in the current Deck.
     */
    public int getCardsLeft() {
        return cardsLeft;
    }

    public ArrayList<Card> cardList() {
    	return cards;
    }
    
    /*
     * returns true if the Deck is empty.
     */
    public boolean isEmpty() {
        return cards.size() == 0;
    }
    
    /*
     * returns a String representation of all the cards in the deck
     *
     * @returns a string of all the cards in the deck
     */
    @Override
    public String toString() {

        int index = 1;
        String cardsInDeck = "";

        for (Card card : cards) {

            if (index < cards.size()) {
                cardsInDeck += index + ": " + card.toString() + "\n";
            } else {
                cardsInDeck += index + ": " + card.toString();
            }

            index += 1;

        }

        return cardsInDeck;
    }
}
