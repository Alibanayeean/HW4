package Back;

import java.util.LinkedList;

public class Bank {

    private LinkedList<Cards> cards;
    private int Coins;

    public Bank() {

        cards = new LinkedList<Cards>();
        Coins = 42;

    }


    public LinkedList<Cards> getCards() {
        return cards;
    }

    public void setCards(LinkedList<Cards> cards) {
        this.cards = cards;
    }

    public int getCoins() {
        return Coins;
    }

    public void setCoins(int coins) {
        Coins = coins;
    }


}
