package Back;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;


public class Match {
    private static final Logger logger = LogManager.getLogger(Match.class);

    private Bank bank;
    private LinkedList<Player> playersArePlaying;
    private LinkedList<Player> playersLoser;
    private int roundYou;
    private int numRound;
    private int indexFrontPlayer;
    private int indexChaleshPlayer;
    private int Winner;

    Gson gson = new GsonBuilder().create();

    public Match() {

        numRound = 0;
        Random random = new Random();
        roundYou = 0;//random.nextInt(4);
        bank = new Bank();
        playersArePlaying = new LinkedList<Player>();
        bank.setCards(GiveShuffleCardsToPlayers(AddCardForFirstTime()));
        playersLoser  = new LinkedList<Player>();
        indexFrontPlayer = -1;
        indexChaleshPlayer = -1;
        Winner = -1;

        GozareshGeri();
        logger.info("----------------------------------------- : start : -----------------------------------------");



    }

    public LinkedList<Cards> GiveShuffleCardsToPlayers(LinkedList<Cards> cards){
        LinkedList<Cards> copy_cards = new LinkedList<Cards>();

        for (int i = 0; i < cards.size(); i++) {
            copy_cards.add(cards.get(i));
        }

        LinkedList<Cards> for_copy = new LinkedList<Cards>();
        Random rand = new Random();
        int p = 0;
        for (int i = 0; i < 8; i++) {
            p = rand.nextInt(copy_cards.size());
            for_copy.add(copy_cards.get(p));
            if(i % 2 == 1){
                LinkedList<Cards> forAddPlayer = new LinkedList<Cards>();
                for (int j = 0; j < for_copy.size(); j++) {
                    forAddPlayer.add(for_copy.get(j));
                }
                if(i / 2 == roundYou){
                    playersArePlaying.add(new Player(forAddPlayer, i / 2));
                }
                else if(i / 2 == (roundYou + 1) % 4 ){
                    playersArePlaying.add(new Paranoid(forAddPlayer, i / 2));
                }
                else if(i / 2 == (roundYou + 2) % 4 ){
                    playersArePlaying.add(new Paranoid(forAddPlayer, i / 2));
                }
                else if(i / 2 == (roundYou + 3) % 4 ){
                    playersArePlaying.add(new Paranoid(forAddPlayer, i / 2));
                }
                for_copy.clear();

            }
            copy_cards.remove(p);
        }

        return copy_cards;
    }

    private LinkedList<Cards> AddCardForFirstTime(){
        LinkedList<Cards> cards = new LinkedList<Cards>();

        cards.add(Cards.BozorgZade);
        cards.add(Cards.BozorgZade);
        cards.add(Cards.BozorgZade);

        cards.add(Cards.AdamKosh);
        cards.add(Cards.AdamKosh);
        cards.add(Cards.AdamKosh);

        cards.add(Cards.Farmandeh);
        cards.add(Cards.Farmandeh);
        cards.add(Cards.Farmandeh);

        cards.add(Cards.Safir);
        cards.add(Cards.Safir);
        cards.add(Cards.Safir);

        cards.add(Cards.ShahDokht);
        cards.add(Cards.ShahDokht);
        cards.add(Cards.ShahDokht);

        return cards;
    }

    public int getIndexFrontPlayer() {
        return indexFrontPlayer;
    }

    public void setIndexFrontPlayer(int indexFrontPlayer) {
        this.indexFrontPlayer = indexFrontPlayer;
    }

    public void DoAction(){
        getIndexChaleshInAllPlayers();

        logger.info("Round: " + numRound);
        GozareshGeri();

        Random random = new Random();
        int p = 0;
        if(playersArePlaying.get(numRound).getNumCoin() >= 10 & playersArePlaying.get(numRound).getActionForRound() != Action.Coup){
            return;
        }

        if(playersArePlaying.get(numRound).getActionForRound() == Action.DaryafteKomakeKhraeji){
            logger.info(numRound + " -> Get money from bank" + " : 2");

            if(indexFrontPlayer == -1){
                playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() + 2);
                bank.setCoins(bank.getCoins() - 2);


            }
            else if(playersArePlaying.get(indexFrontPlayer).getActionForAttacked() == Action.BozorgZade){
                logger.info(indexFrontPlayer + " ->" + numRound + " : " + playersArePlaying.get(indexFrontPlayer).getActionForAttacked());


                if(indexChaleshPlayer != -1){
                    if(playersArePlaying.get(indexChaleshPlayer).getActionForAttacked() == Action.ChaleshForSecond){
                        logger.info(indexChaleshPlayer + " ->" + indexFrontPlayer + " : Chalesh");

                        if(!IsFrontWinChalesh()){
                            logger.info(indexFrontPlayer + " -> loose chalesh" +  " : Card should remove " + playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(playersArePlaying.get(indexFrontPlayer).getIndexCardShouldChangeForChalesh()));

                            playersArePlaying.get(indexFrontPlayer).getCardsRemove().add(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(playersArePlaying.get(indexFrontPlayer).getIndexCardShouldChangeForChalesh()));
                            playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexFrontPlayer).getIndexCardShouldChangeForChalesh());

                            logger.info(numRound + " -> Bank : Get 3 money");
                            playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() + 2);
                            bank.setCoins(bank.getCoins() - 2);

                        }
                        else{
                            logger.info(indexChaleshPlayer + " -> loose chalesh" +  " : Card should remove " + playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));

                            playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                            playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                            ShuffleCardsFromBank();
                            p = random.nextInt(bank.getCards().size());
                            playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().add(bank.getCards().get(p));
                            bank.getCards().remove(p);

                            for (int i = 0; i < playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size() ; i++) {
                                if(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(i) == Cards.BozorgZade){
                                    bank.getCards().add(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(i));
                                    logger.info(indexFrontPlayer + " -> Win chalesh" +  " : Should change card Bozorgzade");
                                    playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(i);
                                    break;
                                }
                            }


                        }
                    }
                }
                else{

//                    No Action
                }
            }
            else{
                playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() + 3);
                bank.setCoins(bank.getCoins() - 3);
            }
        }

        else if(playersArePlaying.get(numRound).getActionForRound() == Action.ChangeCard1){


            if(playersArePlaying.get(numRound).getNumCoin() < 1){
                return;
            }
            logger.info(numRound + " -> Change first card ");

            playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 1);
            bank.setCoins(bank.getCoins() + 1);



            ShuffleCardsFromBank();
            p = random.nextInt(bank.getCards().size());
            playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
            logger.info(numRound + " -> Card "+ playersArePlaying.get(numRound).getCardsForPlayer().getFirst() + " change to " + bank.getCards().get(p));

            bank.getCards().remove(p);
            bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
            playersArePlaying.get(numRound).getCardsForPlayer().removeFirst();
        }
        else if(playersArePlaying.get(numRound).getActionForRound() == Action.ChangeCard2){
            if(playersArePlaying.get(numRound).getNumCoin() < 1){
                return;
            }
            logger.info(numRound + " -> Change second card ");

            playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 1);
            bank.setCoins(bank.getCoins() + 1);


            logger.info(numRound + " -> Card "+ playersArePlaying.get(numRound).getCardsForPlayer().getLast() + " change to " + bank.getCards().get(p));


            ShuffleCardsFromBank();
            p = random.nextInt(bank.getCards().size());
            playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
            bank.getCards().remove(p);
            bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getLast());
            playersArePlaying.get(numRound).getCardsForPlayer().removeLast();
        }

        else if(playersArePlaying.get(numRound).getActionForRound() == Action.Coup){
            if(playersArePlaying.get(numRound).getNumCoin() < 7){
                return;
            }
            logger.info(numRound + " -> "+ indexFrontPlayer + " -> Coup");

            playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 7);
            bank.setCoins(bank.getCoins() + 7);

            logger.info(indexFrontPlayer + " -> Loose card " + playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(playersArePlaying.get(indexFrontPlayer).getIndexCardShouldChangeForChalesh()));


            playersArePlaying.get(indexFrontPlayer).getCardsRemove().add(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(playersArePlaying.get(indexFrontPlayer).getIndexCardShouldChangeForChalesh()));
            playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexFrontPlayer).getIndexCardShouldChangeForChalesh());


        }

        else if(playersArePlaying.get(numRound).getActionForRound() == Action.KasbeDaramad){
            logger.info(numRound + " -> Bank : Get 1 coin ");

            playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() + 1);
            bank.setCoins(bank.getCoins() - 1);
        }

        else if(playersArePlaying.get(numRound).getActionForRound() == Action.BozorgZade){
            logger.info(numRound + " -> Bank : BozorgZade(Get 3 money)");

            if(indexChaleshPlayer == -1){

                playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() + 3);
                bank.setCoins(bank.getCoins() - 3);
            }
            else if(playersArePlaying.get(indexChaleshPlayer).getActionForChalesh() == Action.ChaleshForFirst){
                logger.info(indexChaleshPlayer + " -> " + numRound + " : Chalesh");


                if(!IsNumRoundWinChalesh()){
                    logger.info(numRound + " -> loose chalesh" +  " : Card should remove " + playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));

                    playersArePlaying.get(numRound).getCardsRemove().add(playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));
                    playersArePlaying.get(numRound).getCardsForPlayer().remove(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh());
                }
                else{
                    logger.info(indexChaleshPlayer + " -> loose chalesh" +  " : Card should remove " + playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));

                    playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                    playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                    ShuffleCardsFromBank();
                    p = random.nextInt(bank.getCards().size());
                    playersArePlaying.get(numRound).getCardsForPlayer().add(bank.getCards().get(p));
                    bank.getCards().remove(p);

                    for (int i = 0; i < playersArePlaying.get(numRound).getCardsForPlayer().size() ; i++) {
                        if(playersArePlaying.get(numRound).getCardsForPlayer().get(i) == Cards.BozorgZade){
                            logger.info(indexFrontPlayer + " -> Win chalesh" +  " : Should change card Bozorgzade");
                            bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().get(i));
                            playersArePlaying.get(numRound).getCardsForPlayer().remove(i);
                            break;
                        }
                    }

                    playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() + 3);
                    bank.setCoins(bank.getCoins() - 3);
                    logger.info(indexChaleshPlayer + " -> Bank : Get 3 money");


                }
            }
            else{

                playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() + 3);
                bank.setCoins(bank.getCoins() - 3);
            }

        }


        else if(playersArePlaying.get(numRound).getActionForRound() == Action.AdamKosh){

            if(playersArePlaying.get(numRound).getNumCoin() < 3){
                return;
            }

            logger.info(numRound + " -> " + indexFrontPlayer + " : Adamkosh(Kill)");


            if(indexFrontPlayer == -1){
                if(indexChaleshPlayer == -1){
                    playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                    bank.setCoins(bank.getCoins() + 3);

                    p = random.nextInt(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size());
                    playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(p);
                }
                else if(playersArePlaying.get(indexChaleshPlayer).getActionForChalesh() == Action.ChaleshForFirst){

                    if(!IsNumRoundWinChalesh()){
                        playersArePlaying.get(numRound).getCardsRemove().add(playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(numRound).getCardsForPlayer().remove(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh());
                    }
                    else{
                        playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                        ShuffleCardsFromBank();
                        p = random.nextInt(bank.getCards().size());
                        playersArePlaying.get(numRound).getCardsForPlayer().add(bank.getCards().get(p));
                        bank.getCards().remove(p);

                        for (int i = 0; i < playersArePlaying.get(numRound).getCardsForPlayer().size() ; i++) {
                            if(playersArePlaying.get(numRound).getCardsForPlayer().get(i) == Cards.AdamKosh){
                                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().get(i));
                                playersArePlaying.get(numRound).getCardsForPlayer().remove(i);
                                break;
                            }
                        }


                        playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                        bank.setCoins(bank.getCoins() + 3);
                        p = random.nextInt(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size());
                        playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(p);
                    }
                }
                else{
                    playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                    bank.setCoins(bank.getCoins() + 3);

                    p = random.nextInt(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size());
                    playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(p);
                }
            }
            else if(playersArePlaying.get(indexFrontPlayer).getActionForAttacked() == Action.ShahDokht){
                logger.info(indexFrontPlayer + " -> Shahdokht");


                if(indexChaleshPlayer == -1){
                    playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                    bank.setCoins(bank.getCoins() + 3);

                    logger.info(numRound + " -> Bank : Get 3 money");


                }
                else if(playersArePlaying.get(indexChaleshPlayer).getActionForChalesh() == Action.ChaleshForFirst){
                    logger.info(indexChaleshPlayer + " -> " + numRound + " : Chalesh");

                    if(!IsNumRoundWinChalesh()){
                        logger.info(numRound + " -> loose chalesh" +  " : Card should remove " + playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));

                        playersArePlaying.get(numRound).getCardsRemove().add(playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(numRound).getCardsForPlayer().remove(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh());

                        playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                        bank.setCoins(bank.getCoins() + 3);
                        logger.info(numRound + " -> Bank : Get 3 money");


                    }
                    else{

                        logger.info(indexChaleshPlayer + " -> Loose chalesh" +  " : Card should remove " + playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));

                        playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                        ShuffleCardsFromBank();
                        p = random.nextInt(bank.getCards().size());
                        playersArePlaying.get(numRound).getCardsForPlayer().add(bank.getCards().get(p));

                        bank.getCards().remove(p);

                        for (int i = 0; i < playersArePlaying.get(numRound).getCardsForPlayer().size() ; i++) {
                            if(playersArePlaying.get(numRound).getCardsForPlayer().get(i) == Cards.AdamKosh){
                                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().get(i));
                                playersArePlaying.get(numRound).getCardsForPlayer().remove(i);
                                logger.info(numRound + " -> Win chalesh" +  " : Should change card Adamkosh");

                                break;
                            }
                        }

                        logger.info(numRound + " -> Bank : Get 3 money");

                        playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                        bank.setCoins(bank.getCoins() + 3);
                    }
                }
                else if(playersArePlaying.get(indexChaleshPlayer).getActionForChalesh() == Action.ChaleshForSecond){

                    if(!IsFrontWinChalesh()){
                        logger.info(indexFrontPlayer + " -> loose chalesh" +  " : Card should remove " + playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(playersArePlaying.get(indexFrontPlayer).getIndexCardShouldChangeForChalesh()));

                        playersArePlaying.get(indexFrontPlayer).getCardsRemove().add(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(playersArePlaying.get(indexFrontPlayer).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexFrontPlayer).getIndexCardShouldChangeForChalesh());

                        logger.info(numRound + " -> Bank : Get 3 money");
                        logger.info(numRound + " -> " + indexFrontPlayer + " : Kill");


                        playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                        bank.setCoins(bank.getCoins() + 3);
                        p = random.nextInt(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size());
                        playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(p);
                    }
                    else{
                        logger.info(indexChaleshPlayer + " -> loose chalesh" +  " : Card should remove " + playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));

                        playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                        ShuffleCardsFromBank();
                        p = random.nextInt(bank.getCards().size());
                        playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().add(bank.getCards().get(p));
                        bank.getCards().remove(p);

                        for (int i = 0; i < playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size() ; i++) {
                            if(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(i) == Cards.ShahDokht){
                                bank.getCards().add(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(i));
                                playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(i);
                                logger.info(indexFrontPlayer + " -> Win chalesh" +  " : Change Shahdokht");
                                break;
                            }
                        }


                        logger.info(numRound + " -> Bank : Get 3 money");


                        playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                        bank.setCoins(bank.getCoins() + 3);
                    }
                }
                else{
                    playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                    bank.setCoins(bank.getCoins() + 3);

                    logger.info(numRound + " -> Bank : Get 3 money");

                }

            }
            else{
                if(indexChaleshPlayer == -1){
                    playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                    bank.setCoins(bank.getCoins() + 3);

                    p = random.nextInt(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size());
                    playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(p);

                    logger.info(numRound + " -> Bank : Get 3 money");
                    logger.info(numRound + " -> " + indexFrontPlayer + " : Kill");
                }
                else if(playersArePlaying.get(indexChaleshPlayer).getActionForChalesh() == Action.ChaleshForFirst){

                    if(!IsNumRoundWinChalesh()){
                        logger.info(numRound + " -> loose chalesh" +  " : Card should remove " + playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));

                        playersArePlaying.get(numRound).getCardsRemove().add(playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(numRound).getCardsForPlayer().remove(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh());

                        playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                        bank.setCoins(bank.getCoins() + 3);

                        logger.info(numRound + " -> Bank : Get 3 money");

                    }
                    else{
                        logger.info(indexChaleshPlayer + " -> Loose chalesh" +  " : Card should remove " + playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));

                        playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                        ShuffleCardsFromBank();
                        p = random.nextInt(bank.getCards().size());
                        playersArePlaying.get(numRound).getCardsForPlayer().add(bank.getCards().get(p));
                        bank.getCards().remove(p);

                        for (int i = 0; i < playersArePlaying.get(numRound).getCardsForPlayer().size() ; i++) {
                            if(playersArePlaying.get(numRound).getCardsForPlayer().get(i) == Cards.AdamKosh){
                                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().get(i));
                                playersArePlaying.get(numRound).getCardsForPlayer().remove(i);
                                logger.info(indexFrontPlayer + " -> Win chalesh" +  " : Should change card Adamkosh");
                                break;
                            }
                        }


                        playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                        bank.setCoins(bank.getCoins() + 3);
                        p = random.nextInt(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size());
                        playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(p);

                        logger.info(numRound + " -> Bank : Get 3 money");
                        logger.info(numRound + " -> " + indexFrontPlayer + " : Kill");
                    }
                }
                else{
                    playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(numRound).getNumCoin() - 3);
                    bank.setCoins(bank.getCoins() + 3);

                    p = random.nextInt(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size());
                    playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(p);

                    logger.info(numRound + " -> Bank : Get 3 money");
                    logger.info(numRound + " -> " + indexFrontPlayer + " : Kill");
                }
            }
        }

        else if(playersArePlaying.get(numRound).getActionForRound() == Action.Farmandeh){
            if(indexFrontPlayer == -1){
                if(indexChaleshPlayer == -1){
                    if (playersArePlaying.get(indexFrontPlayer).getNumCoin() <= 1) {
                        playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() + playersArePlaying.get(numRound).getNumCoin());
                        playersArePlaying.get(indexFrontPlayer).setNumCoin(0);
                    }
                    else if (playersArePlaying.get(numRound).getNumCoin() >= 2) {
                        playersArePlaying.get(numRound).setNumCoin(2 + playersArePlaying.get(numRound).getNumCoin());
                        playersArePlaying.get(indexFrontPlayer).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() - 2);
                    }
                }
                else if(playersArePlaying.get(indexChaleshPlayer).getActionForChalesh() == Action.ChaleshForFirst){

                    if(!IsNumRoundWinChalesh()){
                        playersArePlaying.get(numRound).getCardsRemove().add(playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(numRound).getCardsForPlayer().remove(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh());
                    }
                    else{
                        playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                        ShuffleCardsFromBank();
                        p = random.nextInt(bank.getCards().size());
                        playersArePlaying.get(numRound).getCardsForPlayer().add(bank.getCards().get(p));
                        bank.getCards().remove(p);

                        for (int i = 0; i < playersArePlaying.get(numRound).getCardsForPlayer().size() ; i++) {
                            if(playersArePlaying.get(numRound).getCardsForPlayer().get(i) == Cards.Farmandeh){
                                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().get(i));
                                playersArePlaying.get(numRound).getCardsForPlayer().remove(i);
                                break;
                            }
                        }

                        if (playersArePlaying.get(indexFrontPlayer).getNumCoin() <= 1) {
                            playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() + playersArePlaying.get(numRound).getNumCoin());
                            playersArePlaying.get(indexFrontPlayer).setNumCoin(0);
                        }
                        else if (playersArePlaying.get(numRound).getNumCoin() >= 2) {
                            playersArePlaying.get(numRound).setNumCoin(2 + playersArePlaying.get(numRound).getNumCoin());
                            playersArePlaying.get(indexFrontPlayer).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() - 2);
                        }

                    }
                }
                else{
                    if (playersArePlaying.get(indexFrontPlayer).getNumCoin() <= 1) {
                        playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() + playersArePlaying.get(numRound).getNumCoin());
                        playersArePlaying.get(indexFrontPlayer).setNumCoin(0);
                    }
                    else if (playersArePlaying.get(numRound).getNumCoin() >= 2) {
                        playersArePlaying.get(numRound).setNumCoin(2 + playersArePlaying.get(numRound).getNumCoin());
                        playersArePlaying.get(indexFrontPlayer).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() - 2);
                    }
                }
            }
            else if(playersArePlaying.get(indexFrontPlayer).getActionForAttacked() == Action.Farmandeh | playersArePlaying.get(indexFrontPlayer).getActionForAttacked() == Action.Safir0 | playersArePlaying.get(indexFrontPlayer).getActionForAttacked() == Action.Safir1 | playersArePlaying.get(indexFrontPlayer).getActionForAttacked() == Action.SafirBoth){
                if(indexChaleshPlayer == -1){

                }
                else if(playersArePlaying.get(indexChaleshPlayer).getActionForChalesh() == Action.ChaleshForFirst){

                    if(!IsNumRoundWinChalesh()){
                        playersArePlaying.get(numRound).getCardsRemove().add(playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(numRound).getCardsForPlayer().remove(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh());
                    }
                    else{
                        playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                        ShuffleCardsFromBank();
                        p = random.nextInt(bank.getCards().size());
                        playersArePlaying.get(numRound).getCardsForPlayer().add(bank.getCards().get(p));
                        bank.getCards().remove(p);

                        for (int i = 0; i < playersArePlaying.get(numRound).getCardsForPlayer().size() ; i++) {
                            if(playersArePlaying.get(numRound).getCardsForPlayer().get(i) == Cards.Farmandeh){
                                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().get(i));
                                playersArePlaying.get(numRound).getCardsForPlayer().remove(i);
                                break;
                            }
                        }
                    }
                }
                else if(playersArePlaying.get(indexChaleshPlayer).getActionForChalesh() == Action.ChaleshForSecond){
                    if(!IsFrontWinChalesh()){
                        playersArePlaying.get(indexFrontPlayer).getCardsRemove().add(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(playersArePlaying.get(indexFrontPlayer).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexFrontPlayer).getIndexCardShouldChangeForChalesh());

                        if (playersArePlaying.get(indexFrontPlayer).getNumCoin() <= 1) {
                            playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() + playersArePlaying.get(numRound).getNumCoin());
                            playersArePlaying.get(indexFrontPlayer).setNumCoin(0);
                        }
                        else if (playersArePlaying.get(numRound).getNumCoin() >= 2) {
                            playersArePlaying.get(numRound).setNumCoin(2 + playersArePlaying.get(numRound).getNumCoin());
                            playersArePlaying.get(indexFrontPlayer).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() - 2);
                        }
                    }
                    else{
                        playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                        ShuffleCardsFromBank();
                        p = random.nextInt(bank.getCards().size());
                        playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().add(bank.getCards().get(p));
                        bank.getCards().remove(p);

                        if(playersArePlaying.get(indexFrontPlayer).getActionForAttacked() == Action.Farmandeh){
                            for (int i = 0; i < playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size() ; i++) {
                                if(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(i) == Cards.Farmandeh){
                                    bank.getCards().add(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(i));
                                    playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(i);
                                    break;
                                }
                            }
                        }
                        else{
                            for (int i = 0; i < playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size() ; i++) {
                                if(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(i) == Cards.Farmandeh | playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(i) == Cards.Safir){
                                    bank.getCards().add(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(i));
                                    playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().remove(i);
                                    break;
                                }
                            }
                        }


                    }
                }
                else{

                }
            }
            else{
                if(indexChaleshPlayer == -1){
                    if (playersArePlaying.get(indexFrontPlayer).getNumCoin() <= 1) {
                        playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() + playersArePlaying.get(numRound).getNumCoin());
                        playersArePlaying.get(indexFrontPlayer).setNumCoin(0);
                    }
                    else if (playersArePlaying.get(numRound).getNumCoin() >= 2) {
                        playersArePlaying.get(numRound).setNumCoin(2 + playersArePlaying.get(numRound).getNumCoin());
                        playersArePlaying.get(indexFrontPlayer).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() - 2);
                    }
                }
                else if(playersArePlaying.get(indexChaleshPlayer).getActionForChalesh() == Action.ChaleshForFirst){

                    if(!IsNumRoundWinChalesh()){
                        playersArePlaying.get(numRound).getCardsRemove().add(playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(numRound).getCardsForPlayer().remove(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh());
                    }
                    else{
                        playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                        playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                        ShuffleCardsFromBank();
                        p = random.nextInt(bank.getCards().size());
                        playersArePlaying.get(numRound).getCardsForPlayer().add(bank.getCards().get(p));
                        bank.getCards().remove(p);

                        for (int i = 0; i < playersArePlaying.get(numRound).getCardsForPlayer().size() ; i++) {
                            if(playersArePlaying.get(numRound).getCardsForPlayer().get(i) == Cards.Farmandeh){
                                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().get(i));
                                playersArePlaying.get(numRound).getCardsForPlayer().remove(i);
                                break;
                            }
                        }

                        if (playersArePlaying.get(indexFrontPlayer).getNumCoin() <= 1) {
                            playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() + playersArePlaying.get(numRound).getNumCoin());
                            playersArePlaying.get(indexFrontPlayer).setNumCoin(0);
                        }
                        else if (playersArePlaying.get(numRound).getNumCoin() >= 2) {
                            playersArePlaying.get(numRound).setNumCoin(2 + playersArePlaying.get(numRound).getNumCoin());
                            playersArePlaying.get(indexFrontPlayer).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() - 2);
                        }

                    }
                }
                else{
                    if (playersArePlaying.get(indexFrontPlayer).getNumCoin() <= 1) {
                        playersArePlaying.get(numRound).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() + playersArePlaying.get(numRound).getNumCoin());
                        playersArePlaying.get(indexFrontPlayer).setNumCoin(0);
                    }
                    else if (playersArePlaying.get(numRound).getNumCoin() >= 2) {
                        playersArePlaying.get(numRound).setNumCoin(2 + playersArePlaying.get(numRound).getNumCoin());
                        playersArePlaying.get(indexFrontPlayer).setNumCoin(playersArePlaying.get(indexFrontPlayer).getNumCoin() - 2);
                    }
                }
            }

        }

        else if(playersArePlaying.get(numRound).getActionForRound() == Action.Safir0){
            logger.info(numRound + " -> Safir : Change first card");


            if(indexChaleshPlayer == -1){
                ShuffleCardsFromBank();
                p = random.nextInt(bank.getCards().size());
                playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
                bank.getCards().remove(p);
                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
                playersArePlaying.get(numRound).getCardsForPlayer().removeFirst();

                logger.info(numRound + " -> First card changed");

            }
            else if(playersArePlaying.get(indexChaleshPlayer).getActionForChalesh() == Action.ChaleshForFirst){
                logger.info(indexChaleshPlayer + " -> " + numRound + " : Chalesh");

                if(!IsNumRoundWinChalesh()){
                    logger.info(numRound + " -> Loose chalesh : Remove card " +  playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));

                    playersArePlaying.get(numRound).getCardsRemove().add(playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));
                    playersArePlaying.get(numRound).getCardsForPlayer().remove(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh());
                }
                else{
                    logger.info(indexChaleshPlayer + " -> Loose chalesh : Remove card " +  playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));

                    playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                    playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                    for (int i = 0; i < playersArePlaying.get(numRound).getCardsForPlayer().size() ; i++) {
                        if(playersArePlaying.get(numRound).getCardsForPlayer().get(i) == Cards.Safir){
                            bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().get(i));
                            playersArePlaying.get(numRound).getCardsForPlayer().remove(i);
                            logger.info(numRound + " -> Should change card safir");

                            break;
                        }
                    }


                    ShuffleCardsFromBank();
                    p = random.nextInt(bank.getCards().size());
                    playersArePlaying.get(numRound).getCardsForPlayer().add(bank.getCards().get(p));
                    bank.getCards().remove(p);

                    ShuffleCardsFromBank();
                    p = random.nextInt(bank.getCards().size());
                    playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
                    bank.getCards().remove(p);
                    bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
                    playersArePlaying.get(numRound).getCardsForPlayer().removeFirst();

                    logger.info(numRound + " -> First card changed");

                }
            }
            else{
                ShuffleCardsFromBank();
                playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
                bank.getCards().remove(p);
                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
                playersArePlaying.get(numRound).getCardsForPlayer().removeFirst();

                logger.info(numRound + " -> First card changed");

            }

        }
        else if(playersArePlaying.get(numRound).getActionForRound() == Action.Safir1){
            logger.info(numRound + " -> Safir : Change second card");

            if(indexChaleshPlayer == -1){

                ShuffleCardsFromBank();
                playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
                bank.getCards().remove(p);
                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
                playersArePlaying.get(numRound).getCardsForPlayer().removeLast();

                logger.info(numRound + " -> Second card changed");

            }
            else if(playersArePlaying.get(indexChaleshPlayer).getActionForChalesh() == Action.ChaleshForFirst){
                logger.info(indexChaleshPlayer + " -> " + numRound + " : Chalesh");

                if(!IsNumRoundWinChalesh()){
                    logger.info(numRound + " -> Loose chalesh : Remove card " +  playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));

                    playersArePlaying.get(numRound).getCardsRemove().add(playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));
                    playersArePlaying.get(numRound).getCardsForPlayer().remove(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh());
                }
                else{
                    logger.info(indexChaleshPlayer + " -> Loose chalesh : Remove card " +  playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));

                    playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                    playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                    for (int i = 0; i < playersArePlaying.get(numRound).getCardsForPlayer().size() ; i++) {
                        if(playersArePlaying.get(numRound).getCardsForPlayer().get(i) == Cards.Safir){
                            bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().get(i));
                            playersArePlaying.get(numRound).getCardsForPlayer().remove(i);
                            logger.info(numRound + " -> Should change card safir");

                            break;
                        }
                    }

                    ShuffleCardsFromBank();
                    p = random.nextInt(bank.getCards().size());
                    playersArePlaying.get(numRound).getCardsForPlayer().add(bank.getCards().get(p));
                    bank.getCards().remove(p);

                    ShuffleCardsFromBank();
                    p = random.nextInt(bank.getCards().size());
                    playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
                    bank.getCards().remove(p);
                    bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
                    playersArePlaying.get(numRound).getCardsForPlayer().removeLast();

                    logger.info(numRound + " -> Second card changed");






                }

            }
            else{
                ShuffleCardsFromBank();
                p = random.nextInt(bank.getCards().size());
                playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
                bank.getCards().remove(p);
                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
                playersArePlaying.get(numRound).getCardsForPlayer().removeLast();

                logger.info(numRound + " -> Second card changed");

            }

        }
        else if(playersArePlaying.get(numRound).getActionForRound() == Action.SafirBoth){
            logger.info(numRound + " -> Safir : Change both card");

            if(indexChaleshPlayer == -1){
                ShuffleCardsFromBank();
                p = random.nextInt(bank.getCards().size());
                playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
                bank.getCards().remove(p);
                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
                playersArePlaying.get(numRound).getCardsForPlayer().removeLast();

                ShuffleCardsFromBank();
                p = random.nextInt(bank.getCards().size());
                playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
                bank.getCards().remove(p);
                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
                playersArePlaying.get(numRound).getCardsForPlayer().removeFirst();

                logger.info(numRound + " -> Cards changed");

            }
            else if(playersArePlaying.get(indexChaleshPlayer).getActionForChalesh() == Action.ChaleshForFirst){
                if(!IsNumRoundWinChalesh()){
                    logger.info(numRound + " -> Loose chalesh : Remove card " +  playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));

                    playersArePlaying.get(numRound).getCardsRemove().add(playersArePlaying.get(numRound).getCardsForPlayer().get(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh()));
                    playersArePlaying.get(numRound).getCardsForPlayer().remove(playersArePlaying.get(numRound).getIndexCardShouldChangeForChalesh());
                }
                else{
                    logger.info(indexChaleshPlayer + " -> Loose chalesh : Remove card " +  playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));

                    playersArePlaying.get(indexChaleshPlayer).getCardsRemove().add(playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().get(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh()));
                    playersArePlaying.get(indexChaleshPlayer).getCardsForPlayer().remove(playersArePlaying.get(indexChaleshPlayer).getIndexCardShouldChangeForChalesh());

                    ShuffleCardsFromBank();
                    p = random.nextInt(bank.getCards().size());
                    playersArePlaying.get(numRound).getCardsForPlayer().add(bank.getCards().get(p));
                    bank.getCards().remove(p);

                    for (int i = 0; i < playersArePlaying.get(numRound).getCardsForPlayer().size() ; i++) {
                        if(playersArePlaying.get(numRound).getCardsForPlayer().get(i) == Cards.Safir){
                            bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().get(i));
                            playersArePlaying.get(numRound).getCardsForPlayer().remove(i);
                            logger.info(numRound + " -> Should change card safir");

                            break;
                        }
                    }

                    ShuffleCardsFromBank();
                    p = random.nextInt(bank.getCards().size());
                    playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
                    bank.getCards().remove(p);
                    bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
                    playersArePlaying.get(numRound).getCardsForPlayer().removeLast();

                    ShuffleCardsFromBank();
                    p = random.nextInt(bank.getCards().size());
                    playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
                    bank.getCards().remove(p);
                    bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
                    playersArePlaying.get(numRound).getCardsForPlayer().removeFirst();

                    logger.info(numRound + " -> Cards changed");




                }

            }
            else{
                ShuffleCardsFromBank();
                p = random.nextInt(bank.getCards().size());
                playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
                bank.getCards().remove(p);
                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
                playersArePlaying.get(numRound).getCardsForPlayer().removeLast();

                ShuffleCardsFromBank();
                p = random.nextInt(bank.getCards().size());
                playersArePlaying.get(numRound).getCardsForPlayer().add(1, bank.getCards().get(p));
                bank.getCards().remove(p);
                bank.getCards().add(playersArePlaying.get(numRound).getCardsForPlayer().getFirst());
                playersArePlaying.get(numRound).getCardsForPlayer().removeFirst();

                logger.info(numRound + " -> Cards changed");

            }
        }

        else{
            return;
        }

        if(indexFrontPlayer != -1){
            playersArePlaying.get(indexFrontPlayer).setActionForAttacked(Action.NoAction);
        }
        if(indexChaleshPlayer != -1){
            playersArePlaying.get(indexChaleshPlayer).setActionForChalesh(Action.NoAction);
        }

        playersArePlaying.get(numRound).setActionForRound(Action.NoAction);
        numRound++;
        numRound = numRound % 4;
        indexChaleshPlayer = -1;
        indexFrontPlayer = -1;


        for (int i = 0; i < playersArePlaying.size(); i++) {
            if(playersArePlaying.get(numRound).getCardsForPlayer().size() == 0){
                numRound++;
                numRound = numRound % 4;
            }
            else{
                break;
            }
        }


        WhoIsWinner();



        GozareshGeri();

        logger.info("----------------------------------------- : next round : -----------------------------------------");




    }

    public void ShuffleCardsFromBank() {
        Collections.shuffle(bank.getCards());
    }

    public void WhoIsWinner(){
        int counter = 0;
        int help = 0;
        for (int i = 0; i < playersArePlaying.size(); i++) {
            if(playersArePlaying.get(i).getCardsForPlayer().size() == 0){
                counter++;
            }
            else{
                help = i;
            }
        }
        if (counter == 3) {
            Winner = help;

            logger.info("Winner: " + Winner);
            logger.info("----------------------------------------- : end : -----------------------------------------");

        }

    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public LinkedList<Player> getPlayersArePlaying() {
        return playersArePlaying;
    }

    public void setPlayersArePlaying(LinkedList<Player> playersArePlaying) {
        this.playersArePlaying = playersArePlaying;
    }

    public LinkedList<Player> getPlayersLoser() {
        return playersLoser;
    }

    public void setPlayersLoser(LinkedList<Player> playersLoser) {
        this.playersLoser = playersLoser;
    }

    public int getRoundYou() {
        return roundYou;
    }

    public void setRoundYou(int roundYou) {
        this.roundYou = roundYou;
    }

    public int getNumRound() {
        return numRound;
    }

    public void setNumRound(int numRound) {
        this.numRound = numRound;
    }

    public int getIndexChaleshPlayer() {
        return indexChaleshPlayer;
    }

    public void setIndexChaleshPlayer(int indexChaleshPlayer) {
        this.indexChaleshPlayer = indexChaleshPlayer;
    }

    public int getWinner() {
        return Winner;
    }

    public void setWinner(int winner) {
        Winner = winner;
    }

    public int numPlayersArePlaying(){
        int i = 0;
        for (int j = 0; j < this.getPlayersArePlaying().size(); j++) {
            if(this.getNumRound() == j){

            }
            else if(this.getPlayersArePlaying().get(j).getCardsForPlayer().size() != 0){
                i++;
            }
        }

        return i;
    }

    public void getIndexChaleshInAllPlayers() {
        for (int i = 0; i < playersArePlaying.size(); i++) {
            if (playersArePlaying.get(i).getActionForChalesh() == Action.ChaleshForFirst | playersArePlaying.get(i).getActionForChalesh() == Action.ChaleshForSecond) {
                indexChaleshPlayer = i;
                return;
            }
        }
        indexChaleshPlayer = -1;
    }


    public boolean IsNumRoundWinChalesh(){
        if(indexChaleshPlayer == -1){
            return true;
        }

        Cards card = ChangeActionToCardForRoundPlayer(playersArePlaying.get(numRound));
        if(card == null){
            return true;
        }

        int i;
        for (i = 0; i < playersArePlaying.get(numRound).getCardsForPlayer().size(); i++) {
            if(playersArePlaying.get(numRound).getCardsForPlayer().get(i) == card){
                break;
            }
        }
        if(i == playersArePlaying.get(numRound).getCardsForPlayer().size()){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean IsFrontWinChalesh(){
        if(indexChaleshPlayer == -1){
            return true;
        }
        else if(indexFrontPlayer == -1){
            return true;
        }

        Cards card = ChangeActionToCardForFrontPlayer(playersArePlaying.get(indexFrontPlayer));
        if(card == null){
            return true;
        }

        int i;
        for (i = 0; i < playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size(); i++) {
            if(playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().get(i) == card){
                break;
            }
        }
        if(i == playersArePlaying.get(indexFrontPlayer).getCardsForPlayer().size()){
            return false;
        }
        else{
            return true;
        }

    }

    public Cards ChangeActionToCardForRoundPlayer(Player player){
        if(player.getActionForRound() == Action.Farmandeh){
            return Cards.Farmandeh;
        }
        else if(player.getActionForRound() == Action.Safir0 | player.getActionForRound() == Action.Safir1 | player.getActionForRound() == Action.SafirBoth){
            return Cards.Safir;
        }
        else if(player.getActionForRound() == Action.BozorgZade){
            return Cards.BozorgZade;
        }
        else if(player.getActionForRound() == Action.AdamKosh){
            return Cards.AdamKosh;
        }
        else{
            return null;
        }

    }

    public Cards ChangeActionToCardForFrontPlayer(Player player){
        if(player.getActionForRound() == Action.ShahDokht){
            return Cards.ShahDokht;
        }
        else if(player.getActionForRound() == Action.Farmandeh){
            return Cards.Farmandeh;
        }
        else if(player.getActionForRound() == Action.Safir0 | player.getActionForRound() == Action.Safir1 | player.getActionForRound() == Action.SafirBoth){
            return Cards.Safir;
        }
        else if(player.getActionForRound() == Action.BozorgZade){
            return Cards.BozorgZade;
        }
        else{
            return null;
        }

    }


    @Override
    public String toString() {
        String  result = "";
        result = result + "round you: " + roundYou + " numRound: " + numRound + " indexFrontPlayer: " + indexFrontPlayer + " ChaleshPlayer: " + indexChaleshPlayer + '\n';
        for (int i = 0; i < playersArePlaying.size(); i++) {
            result = result + playersArePlaying.get(i).getNumRound() + " : ";
            for (int j = 0; j < playersArePlaying.get(i).getCardsForPlayer().size(); j++) {
                result = result + playersArePlaying.get(i).getCardsForPlayer().get(j) + " " ;
            }
            result = result + "Action for round : " + playersArePlaying.get(i).getActionForRound() + "  Action for front : " + playersArePlaying.get(i).getActionForAttacked() + "   Action for chalesh : " + playersArePlaying.get(i).getActionForChalesh() + "  ";
            result = result + " Coin: " +  playersArePlaying.get(i).getNumCoin() + " ";
            result = result + '\n';
        }
        result = result + "Bank: Cards:  ";
        for (int i = 0; i < bank.getCards().size(); i++) {
            result = result + bank.getCards().get(i).name() + " // ";
        }
        result = result + "Coin: " + bank.getCoins();
        result = result + '\n';
        result = result + "Winner: " + Winner;


        return result;
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

    public void GozareshGeri(){
        for (int i = 0; i < playersArePlaying.size(); i++) {
            if(playersArePlaying.get(i).getCardsForPlayer().size() != 0){
                String result = "Round: " + playersArePlaying.get(i).getNumRound() + " -- ";
                for (int j = 0; j < playersArePlaying.get(i).getCardsForPlayer().size(); j++) {
                    result = result + playersArePlaying.get(i).getCardsForPlayer().get(j) + "  " ;
                }
                result = result + " -- ";
                result = result + " Coin: " + playersArePlaying.get(i).getNumCoin() + " ";
                logger.info(result);
            }
        }
    }


}
