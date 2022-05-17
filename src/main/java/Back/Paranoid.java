package Back;

import java.util.LinkedList;
import java.util.Random;


public class Paranoid extends Robot {
    int counter = 0;

    public Paranoid(LinkedList<Cards> cardsForPlayer, int numRound) {
        super(cardsForPlayer, numRound);
    }

    @Override
    public void ChangeActionForChalesh(Match match){
        if(match.getNumRound() == match.getRoundYou()){
            if(match.getIndexFrontPlayer() == -1){

            }
            else if(match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Farmandeh | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.ShahDokht | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.BozorgZade | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir){
                setActionForChalesh(Action.ChaleshForSecond);
                match.setIndexChaleshPlayer(this.getNumRound());
            }
        }
        else{
            if(match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Farmandeh | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.AdamKosh | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.BozorgZade | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir){
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
                if(match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Farmandeh | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.ShahDokht | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.BozorgZade | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir){
                    setActionForChalesh(Action.ChaleshForSecond);
                    match.setIndexChaleshPlayer(this.getNumRound());
                }
            }
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
        if(true){
            setActionForRound(Action.Safir);
            Random random = new Random();
            int p = 0;
            if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 2){
                match.ShuffleCardsFromBank();
                random = new Random();
                p = random.nextInt(match.getBank().getCards().size());
                match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().add(match.getBank().getCards().get(p));
                match.getBank().getCards().remove(p);

                p = random.nextInt(match.getBank().getCards().size());
                match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().add(match.getBank().getCards().get(p));
                match.getBank().getCards().remove(p);
            }
            else if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 1){
                match.ShuffleCardsFromBank();
                p = random.nextInt(match.getBank().getCards().size());
                this.getCardsForPlayer().add(match.getBank().getCards().get(p));
                match.getBank().getCards().remove(p);
            }

            if(this.getCardsForPlayer().size() == 4){
                p = random.nextInt(this.getCardsForPlayer().size());

                int q = random.nextInt(this.getCardsForPlayer().size());

                while(q == p){
                    p = random.nextInt(this.getCardsForPlayer().size());
                }

                this.CardsChangeForSafir.add(q);
                this.CardsChangeForSafir.add(p);
            }
            else if(this.getCardsForPlayer().size() == 2){
                p = random.nextInt(this.getCardsForPlayer().size());
                this.CardsChangeForSafir.add(p);
            }

            return;
        }
        for (int i = 0; i < this.getCardsForPlayer().size(); i++) {
            if(this.getCardsForPlayer().get(i) == Cards.Safir){
                setActionForRound(Action.Safir);
                Random random = new Random();
                int p = 0;
                if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 2){
                    match.ShuffleCardsFromBank();
                    random = new Random();
                    p = random.nextInt(match.getBank().getCards().size());
                    match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().add(match.getBank().getCards().get(p));
                    match.getBank().getCards().remove(p);

                    p = random.nextInt(match.getBank().getCards().size());
                    match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().add(match.getBank().getCards().get(p));
                    match.getBank().getCards().remove(p);
                }
                else if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 1){
                    match.ShuffleCardsFromBank();
                    p = random.nextInt(match.getBank().getCards().size());
                    this.getCardsForPlayer().add(match.getBank().getCards().get(p));
                    match.getBank().getCards().remove(p);
                }

                if(this.getCardsForPlayer().size() == 4){
                    p = random.nextInt(this.getCardsForPlayer().size());

                    int q = random.nextInt(this.getCardsForPlayer().size());

                    while(q == p){
                        p = random.nextInt(this.getCardsForPlayer().size());
                    }

                    this.getCardsForPlayer().remove(q);
                    this.getCardsForPlayer().remove(p);
                }
                else if(this.getCardsForPlayer().size() == 2){
                    p = random.nextInt(this.getCardsForPlayer().size());
                    this.getCardsForPlayer().remove(p);
                }

                return;
            }
            else if(this.getCardsForPlayer().get(i) == Cards.BozorgZade){
                setActionForRound(Action.BozorgZade);
                return;
            }
        }
        if(match.getNumRound() == getNumRound()){

            Random random = new Random();
            int p = random.nextInt(10);
            if(p == 0){
                if(this.getNumCoin() >= 1){
                    setActionForRound(Action.ChangeCard1);
                    return;
                }
                else{
                    setActionForRound(Action.BozorgZade);
                    return;
                }

            }
            else if(p == 1){
                if(this.getNumCoin() >= 1){
                    setActionForRound(Action.ChangeCard2);
                    return;
                }
                else{
                    setActionForRound(Action.BozorgZade);
                    return;
                }


            }
            else if(p == 3){
                setActionForRound(Action.DaryafteKomakeKhraeji);
                return;
            }
            else if(p == 4){
                setActionForRound(Action.KasbeDaramad);
                return;

            }
            else if(p == 5){
                setActionForRound(Action.BozorgZade);
                return;
            }
            else if(p == 6){
                if(this.getNumCoin() >= 3){
                    setActionForRound(Action.AdamKosh);

                }
                else{
                    setActionForRound(Action.BozorgZade);
                    return;
                }
            }
            else if(p == 7){
                setActionForRound(Action.Farmandeh);
            }
            else{
                setActionForRound(Action.Safir);
                if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 2){
                    match.ShuffleCardsFromBank();
                    random = new Random();
                    p = random.nextInt(match.getBank().getCards().size());
                    match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().add(match.getBank().getCards().get(p));
                    match.getBank().getCards().remove(p);

                    p = random.nextInt(match.getBank().getCards().size());
                    match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().add(match.getBank().getCards().get(p));
                    match.getBank().getCards().remove(p);
                }
                else if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 1){
                    match.ShuffleCardsFromBank();
                    p = random.nextInt(match.getBank().getCards().size());
                    this.getCardsForPlayer().add(match.getBank().getCards().get(p));
                    match.getBank().getCards().remove(p);
                }

                if(this.getCardsForPlayer().size() == 4){
                    p = random.nextInt(this.getCardsForPlayer().size());

                    int q = random.nextInt(this.getCardsForPlayer().size());

                    while(q == p){
                        p = random.nextInt(this.getCardsForPlayer().size());
                    }

                    this.getCardsForPlayer().remove(q);
                    this.getCardsForPlayer().remove(p);
                }
                else if(this.getCardsForPlayer().size() == 2){
                    p = random.nextInt(this.getCardsForPlayer().size());
                    this.getCardsForPlayer().remove(p);
                }

                return;
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
    }

    @Override
    public void ChangeCardBecauseLooseChalesh(Match match){
        if(this.getActionForRound() == Action.Safir){
            if(this.getCardsForPlayer().size() == 4){
                match.getBank().getCards().add(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().get(3));
                match.getBank().getCards().add(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().get(2));

                match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().remove(3);
                match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().remove(2);
            }
            else if(this.getCardsForPlayer().size() == 2){
                match.getBank().getCards().add(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().get(1));

                match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().remove(1);
            }
        }
        Random random = new Random();
        int p = random.nextInt(this.getCardsForPlayer().size());
        this.setIndexCardShouldChangeForChalesh(p);
    }


}