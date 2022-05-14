package Back;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Player {

    private LinkedList<Cards> cardsForPlayer;
    private LinkedList<Cards> cardsRemove;
    private int numCoin;
    private Action actionForRound;
    private Action actionForAttacked;
    private Action actionForChalesh;
    private int numRound;
    private int indexCardShouldChangeForChalesh;
    public Player(LinkedList<Cards> cardsForPlayer, int numRound) {
        this.cardsForPlayer = cardsForPlayer;
        this.numRound = numRound;
        numCoin = 2;
        indexCardShouldChangeForChalesh = -1;
        actionForRound = Action.NoAction;
        actionForAttacked = Action.NoAction;
        actionForChalesh = Action.NoAction;
        cardsRemove = new LinkedList<Cards>();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(this);
        Save("player.json", json);
    }


    public LinkedList<Cards> getCardsForPlayer() {
        return cardsForPlayer;
    }

    public void setCardsForPlayer(LinkedList<Cards> cardsForPlayer) {
        this.cardsForPlayer = cardsForPlayer;
    }

    public int getNumCoin() {
        return numCoin;
    }

    public void setNumCoin(int numCoin) {
        this.numCoin = numCoin;
    }

    public Action getActionForRound() {
        return actionForRound;
    }

    public void setActionForRound(Action actionForRound) {
        this.actionForRound = actionForRound;
    }

    public Action getActionForAttacked() {
        return actionForAttacked;
    }

    public void setActionForAttacked(Action actionForAttacked) {
        this.actionForAttacked = actionForAttacked;
    }

    public Action getActionForChalesh() {
        return actionForChalesh;
    }

    public void setActionForChalesh(Action actionForChalesh) {
        this.actionForChalesh = actionForChalesh;
    }

    public int getNumRound() {
        return numRound;
    }

    public void setNumRound(int numRound) {
        this.numRound = numRound;
    }

    public void Save(String filename, String text){
        File file = new File(filename);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(text);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Cards> getCardsRemove() {
        return cardsRemove;
    }

    public void setCardsRemove(LinkedList<Cards> cardsRemove) {
        this.cardsRemove = cardsRemove;
    }

    public int getIndexCardShouldChangeForChalesh() {
        return indexCardShouldChangeForChalesh;
    }

    public void setIndexCardShouldChangeForChalesh(int indexCardShouldChangeForChalesh) {
        this.indexCardShouldChangeForChalesh = indexCardShouldChangeForChalesh;
    }


}
