package Ejercicio007;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;


public class Solitarie extends Applet {
	public static final String DIRECTION = "/C:/imagenes/Cartas/";
	private String names[] = { "_of_clubs.png", "_of_diamonds.png", "_of_hearts.png", "_of_spades.png" };
    public static final int NUM_CARDS = 52;
	public static final int CPD = 13;
	public static final int SUITS = 4;
	public static final int DECKS = 7;

    Image imagen;
    Graphics hidden;
	Image cardImgs[];
	Image cardBack;
	Deck deck;
	Rectangle cardBackRectangle;
	SecondDeck secondDeck;
	Card activeCard;
	SuitDeck[] suitDecks;
	GameDeck[] gameDecks;
    
    public void init() {
		imagen = this.createImage(800, 700);
        hidden = imagen.getGraphics();
        
		cardImgs = new Image[NUM_CARDS];
		for (int i = 0; i < SUITS; i++)
            for(int j = 0; j < CPD; j++)
				cardImgs[(i * CPD) + j] = getImage(getCodeBase(), DIRECTION + (j + 1) + names[i]);

		cardBack = getImage(getCodeBase(), DIRECTION + "reverso.png");
		cardBackRectangle = new Rectangle(20, 20, Card.WIDTH, Card.HEIGHT);
		deck = new Deck(cardImgs);
		deck.shuffle();
		secondDeck = new SecondDeck();
		suitDecks = new SuitDeck[SUITS];
		for (int i = 0; i < SUITS; i++)
			suitDecks[i] = new SuitDeck((i * 100) + 400);
		gameDecks = new GameDeck[DECKS];
		for (int i = 0; i < DECKS; i++)
			gameDecks[i] = new GameDeck((i * 100) + 100);

		resize(800, 700);
    }

    public void paint(Graphics g) {
		hidden.setColor(Color.GREEN);
		hidden.fillRect(0, 0, 800, 700);
		// suitDecks rectangles
		for (int i = 0; i < SUITS; i++)
			suitDecks[i].draw(hidden, this);
		// Deck to take cards
		hidden.drawImage(cardBack, 20, 20, Card.WIDTH, Card.HEIGHT, this);
		// Game decks
		for (int i = 0; i < DECKS; i++) {
			gameDecks[i].showGameCard(hidden, this);
		}
		// Deck where you show cards
		secondDeck.showCard(hidden, this);
		if (activeCard != null)
			activeCard.draw(hidden, this);

		g.drawImage(imagen, 0, 0, this);
    }

    public void update(Graphics g) {
        paint(g);
    }

	public boolean mouseDown(Event ev, int x, int y) {
		if (cardBackRectangle.contains(x, y)) {
			if (deck.getMainDeck().size() == 0) {
				deck.setMainDeck(secondDeck.getSecondDeck());
				secondDeck = new SecondDeck();
			}
			secondDeck.addCard(deck.takeCard());
			secondDeck.relocateCard();
			repaint();
		}
		if (secondDeck.extractCard().contains(x, y)) {
			activeCard = secondDeck.extractCard();
		}
		return true;
	}

	public boolean mouseDrag(Event ev, int x, int y) {
		if (activeCard != null) {
			activeCard.x = x - (Card.WIDTH / 2);
			activeCard.y = y - (Card.HEIGHT / 2);
			repaint();
		}
		return true;
	}

	public boolean mouseUp(Event ev, int x, int y) {
		if (activeCard != null) {
			for (int i = 0; i < SUITS; i++)
				if (activeCard.intersects(suitDecks[i]))
					if (suitDecks[i].addSuitCard(activeCard)) {
						secondDeck.removeCard();
					}
			for (int i = 0; i < DECKS; i++)
				if (activeCard.intersects(gameDecks[i]))
					if (gameDecks[i].addGameCard(activeCard)) {
						secondDeck.removeCard();
					}

			secondDeck.relocateCard();
			activeCard = null;
			repaint();
		}
		return true;
	}
}