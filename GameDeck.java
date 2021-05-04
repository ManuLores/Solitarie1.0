package Ejercicio007;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class GameDeck extends Rectangle {
	java.util.ArrayList<Card> gameDeck;
	public static final int POSITIONY = 200;

	public GameDeck(int positionx) {
		super(positionx, POSITIONY, Card.WIDTH, Card.HEIGHT);
		gameDeck = new java.util.ArrayList<Card>();
	}

	public void showGameCard(Graphics g, Applet ap) {
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		for (int i = 0; i < gameDeck.size(); i++)
			gameDeck.get(i).draw(g, ap);
	}

	public Card extractGameCard() {
		return gameDeck.get(gameDeck.size() - 1);
	}

	public void removeGameCard() {
		gameDeck.remove(gameDeck.size() - 1);
	}

	public void relocateGameCard() {
		gameDeck.get(gameDeck.size() - 1).x = x;
		gameDeck.get(gameDeck.size() - 1).y = (gameDeck.size() - 1) * 30 + y;
	}

	public boolean addGameCard(Card cardAdded) {
		if (gameDeck.size() == 0) {
			gameDeck.add(cardAdded);
			relocateGameCard();
			return true;
		} else {
			if (cardAdded.getColor() != gameDeck.get(gameDeck.size() - 1).getColor())
				if ((cardAdded.getValue() + 1) == gameDeck.get(gameDeck.size() - 1).getValue()) {
					gameDeck.add(cardAdded);
					relocateGameCard();
					return true;
				}
		}
		return false;
	}
}

