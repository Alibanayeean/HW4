package Back;

import java.util.LinkedList;
import java.util.Random;


public class GhateleMohtat extends Robot {
    int counter = 0;

    public GhateleMohtat(LinkedList<Cards> cardsForPlayer, int numRound) {
        super(cardsForPlayer, numRound);
    }

    @Override
    public void ChangeActionForChalesh(Match match){

        if(match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Farmandeh | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.ShahDokht | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.BozorgZade | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir){
            setActionForChalesh(Action.ChaleshForSecond);
            match.setIndexChaleshPlayer(this.getNumRound());
        }

    }

    @Override
    public void ChangeActionForAttacked(Match match){

        if(match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.AdamKosh){
            setActionForAttacked(Action.ShahDokht);
        }
        else if(match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.DaryafteKomakeKhraeji){
            setActionForAttacked(Action.BozorgZade);
        }
        else if(match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Farmandeh){
            Random random = new Random();
            int p = random.nextInt(2);
            if(p == 0){
                setActionForAttacked(Action.Farmandeh);
            }
            else{
                setActionForAttacked(Action.Safir);

            }
        }
        else{
            setActionForAttacked(Action.NoAction);
        }

        match.setIndexFrontPlayer(this.getNumRound());


    }

    @Override
    public void ChangeActionHisRound(Match match){
        int numAdamkosh = 0;
        int numSafir = 0;
        Random random = new Random();
        int p = random.nextInt(10);
        for (int i = 0; i < this.getCardsForPlayer().size(); i++) {
            if(this.getCardsForPlayer().get(i) == Cards.AdamKosh){
                numAdamkosh = 1;
            }
            else if(this.getCardsForPlayer().get(i) == Cards.Safir){
                numSafir = 1;
            }
        }
        if(numAdamkosh == 0){
            if(numSafir == 1){
                setActionForRound(Action.Safir);
                return;
            }
            else{
                if(getNumCoin() == 0){
                    setActionForRound(Action.KasbeDaramad);
                    return;
                }
                else if (getNumCoin() >= 1){
                    setActionForRound(Action.ChangeCard1);
                    return;
                }
            }
        }
        else{
            setActionForRound(Action.AdamKosh);
        }

        int i = match.numPlayersArePlaying();
        p = random.nextInt(i) + 1;
        int counter = 0;

        for (int j = 0; j < match.getPlayersArePlaying().size(); j++) {
            if(match.getPlayersArePlaying().get(j).getCardsForPlayer().size() != 0 & j != this.getNumRound()){
                counter++;

                if(counter == p){
                    match.setIndexFrontPlayer(match.getPlayersArePlaying().get(j).getNumRound());
                    return;
                }
            }
        }
    }

    @Override
    public void ChangeCardBecauseLooseChalesh(Match match){
        Random random = new Random();
        int p = random.nextInt(this.getCardsForPlayer().size());
        this.setIndexCardShouldChangeForChalesh(p);
    }


}