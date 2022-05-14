package Back;

import java.util.LinkedList;
import java.util.Random;

abstract public class Robot extends Player{
    public Robot(LinkedList<Cards> cardsForPlayer, int numRound) {
        super(cardsForPlayer, numRound);
    }

    abstract public void ChangeActionForChalesh(Match match);

    abstract public void ChangeActionHisRound(Match match);

    abstract public void ChangeActionForAttacked(Match match);

    abstract public void ChangeCardBecauseLooseChalesh(Match match);



}
