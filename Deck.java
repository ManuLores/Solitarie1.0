package Ejercicio007;

import java.awt.Image;
import java.util.ArrayList;

public class Deck {
	private ArrayList<Card> mainDeck;

	public Deck(Image[] cardImgs) {
        mainDeck = new ArrayList<Card>();
		for (int i = 0; i < cardImgs.length; i++)
			mainDeck.add(new Card(cardImgs[i], (i % 13) + 1, ((i / 13) == 0 || (i / 13) == 3) ? Card.BLACK : Card.RED,
					(i / 13)));
    }
    
    public Card draw() {
		Card aux;
		aux = mainDeck.get(0);
		mainDeck.remove(0);
		return aux;
    }
    
    public void shuffle() {
        Card aux;
        for(int i = 0; i < 100; i++) {
            int p1 = (int)(Math.random() * 52);
            int p2 = (int)(Math.random() * 52);
            aux = mainDeck.get(p1);
            mainDeck.set(p1, mainDeck.get(p2));
            mainDeck.set(p2, aux);
        }
    }

	public Card takeCard() {
		Card aux = mainDeck.get(0);
		mainDeck.remove(0);
		return aux;
	}

	public ArrayList<Card> getMainDeck() {
		return mainDeck;
	}

	public void setMainDeck(ArrayList<Card> mainDeck) {
		this.mainDeck = mainDeck;
	}
}