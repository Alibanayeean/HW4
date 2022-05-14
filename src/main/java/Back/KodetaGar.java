package Back;
import java.util.LinkedList;
import java.util.Random;


public class KodetaGar extends Robot {

    public KodetaGar(LinkedList<Cards> cardsForPlayer, int numRound) {
        super(cardsForPlayer, numRound);
    }

    @Override
    public void ChangeActionForChalesh(Match match){
//        setActionForChalesh(Action.NoAction);

        if(match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Farmandeh | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.AdamKosh | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.BozorgZade | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.SafirBoth | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir0 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir1){
            setActionForChalesh(Action.ChaleshForFirst);
            match.setIndexChaleshPlayer(this.getNumRound());
//            if(counter % 2 == 1){
//                setActionForChalesh(Action.ChaleshForFirst);
//                match.setIndexChaleshPlayer(this.getNumRound());
//            }
//
//            else{
//                setActionForChalesh(Action.NoAction);
//            }
//
//            counter++;
        }
        else if(match.getIndexFrontPlayer() != -1){
            if(match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Farmandeh | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.ShahDokht | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.BozorgZade | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.SafirBoth | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir0 | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir1){
                setActionForChalesh(Action.ChaleshForSecond);
                match.setIndexChaleshPlayer(this.getNumRound());
            }
        }
    }

    @Override
    public void ChangeActionHisRound(Match match){
        for (int i = 0; i < this.getCardsForPlayer().size(); i++) {
            if(this.getCardsForPlayer().get(i) == Cards.Safir){
                setActionForRound(Action.Safir0);
                return;
            }
            else if(this.getCardsForPlayer().get(i) == Cards.BozorgZade){
                setActionForRound(Action.BozorgZade);
                return;
            }
        }
        if(this.getNumRound() == match.getNumRound()){
            if(this.getNumCoin() < 7){
                setActionForRound(Action.BozorgZade);
                return;
            }
            else{
                setActionForRound(Action.Coup);
            }
            Random random = new Random();
            int i = match.numPlayersArePlaying();
            int p = random.nextInt(i);
            int counter = 0;



            for (int j = 0; j < match.getPlayersArePlaying().size(); j++) {
                if(match.getPlayersArePlaying().get(j).getCardsForPlayer().size() != 0 & j != this.getNumRound() ){
                    counter++;
                    if(counter == p){
                        match.setIndexFrontPlayer(match.getPlayersArePlaying().get(j).getNumRound());

                        return;
                    }
                }
            }
        }

    }


    @Override
    public void ChangeActionForAttacked(Match match){
        setActionForAttacked(Action.NoAction);
        match.setIndexFrontPlayer(this.getNumRound());
    }

    @Override
    public void ChangeCardBecauseLooseChalesh(Match match){
        Random random = new Random();
        int p = random.nextInt(this.getCardsForPlayer().size());
        this.setIndexCardShouldChangeForChalesh(p);
    }


}
