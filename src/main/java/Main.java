import Back.Action;
import Back.Cards;
import Back.Match;
import Back.Robot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends JPanel {
    Match match = new Match();
    int numClickedForHisRound = 0;
    int indexShouldStartFromForChalesh = 0;
    int indexChangeCardBecauseLooseChalesh = 0;
    int indexShouldStartFromForChaleshForYouRound = 0;
    int counterForChalesh = 0;
    int indexFrontPlayerForRoundYou = 0;
    int[] YsForCards = new int[2];

    Main(){
        YsForCards[0] = 780;
        YsForCards[1] = 780;
        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if(match.getNumRound() == match.getRoundYou()){
                    if(numClickedForHisRound == 0){
                        if(match.getPlayersArePlaying().get(match.getNumRound()).getNumCoin() >= 10){
                            if(x >= 725 & x <= 925 & y >= 650 & y <= 750){
                                match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.Coup);
                            }
                        }

                        else if(x >= 25 & x <= 225 & y >= 650 & y <= 750){
                            match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.DaryafteKomakeKhraeji);
                            numClickedForHisRound = 0;

                            for (int i = 0; i < match.getPlayersArePlaying().size(); i++) {
                                if(-1 != match.getIndexFrontPlayer()){
                                    if(match.getPlayersArePlaying().get(i).getCardsForPlayer().size() != 0){
                                        ((Robot) match.getPlayersArePlaying().get(i)).ChangeActionForAttacked(match);
                                    }
                                }
                            }

                            for (int i = 0; i < match.getPlayersArePlaying().size(); i++) {
                                if(-1 != match.getIndexChaleshPlayer()){
                                    if(match.getPlayersArePlaying().get(i).getCardsForPlayer().size() != 0){
                                        ((Robot) match.getPlayersArePlaying().get(i)).ChangeActionForChalesh(match);
                                    }
                                }
                            }

                            if(match.getIndexFrontPlayer() == -1){
                                if(match.getIndexChaleshPlayer() == -1){
                                    numClickedForHisRound = 0;
                                    match.DoAction();
                                }
                                else{
                                    if(match.IsNumRoundWinChalesh()){
                                        numClickedForHisRound = 0;
                                        ((Robot) match.getPlayersArePlaying().get(match.getIndexChaleshPlayer())).ChangeCardBecauseLooseChalesh(match);
                                        match.DoAction();
                                    }
                                    else{
                                        numClickedForHisRound = 4;
                                    }
                                }
                            }
                            else{
                                if(match.getIndexChaleshPlayer() == -1){
                                    numClickedForHisRound = 0;
                                    match.DoAction();
                                }
                                else{
                                    if(match.getPlayersArePlaying().get(match.getIndexChaleshPlayer()).getActionForChalesh() == Action.ChaleshForFirst){
                                        if(match.IsNumRoundWinChalesh()){
                                            numClickedForHisRound = 0;
                                            ((Robot) match.getPlayersArePlaying().get(match.getIndexChaleshPlayer())).ChangeCardBecauseLooseChalesh(match);
                                            match.DoAction();
                                        }
                                        else{
                                            numClickedForHisRound = 4;
                                        }
                                    }
                                    else if(match.getPlayersArePlaying().get(match.getIndexChaleshPlayer()).getActionForChalesh() == Action.ChaleshForSecond){
                                        if(match.IsFrontWinChalesh()){
                                            numClickedForHisRound = 0;
                                            ((Robot) match.getPlayersArePlaying().get(match.getIndexChaleshPlayer())).ChangeCardBecauseLooseChalesh(match);
                                            match.DoAction();
                                        }
                                        else{
                                            numClickedForHisRound = 0;
                                            ((Robot) match.getPlayersArePlaying().get(match.getIndexFrontPlayer())).ChangeCardBecauseLooseChalesh(match);
                                            match.DoAction();
                                        }
                                    }
                                }
                            }

                            return;
                        }
                        else if(x >= 250 & x <= 450 & y >= 650 & y <= 750){
                            match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.KasbeDaramad);
                            numClickedForHisRound = 0;
                            match.DoAction();

                            return;
                        }
                        else if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                            match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.BozorgZade);

                            for (int i = 0; i < match.getPlayersArePlaying().size(); i++) {
                                if(-1 != match.getIndexChaleshPlayer()){
                                    if(match.getPlayersArePlaying().get(i).getCardsForPlayer().size() != 0){
                                        ((Robot) match.getPlayersArePlaying().get(i)).ChangeActionForChalesh( match);
                                    }
                                }
                            }

                            if(match.getIndexChaleshPlayer() == -1){
                                numClickedForHisRound = 0;
                                match.DoAction();
                            }
                            else{
                                if(match.IsNumRoundWinChalesh()){
                                    numClickedForHisRound = 0;
                                    ((Robot) match.getPlayersArePlaying().get(match.getIndexChaleshPlayer())).ChangeCardBecauseLooseChalesh(match);
                                    match.DoAction();
                                }
                                else{
                                    numClickedForHisRound = 4;
                                }
                            }

                            return;

                        }
                        else if(x >= 700 & x <= 900 & y >= 650 & y <= 750){
                            match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.Farmandeh);
                        }
                        else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                            if(match.getPlayersArePlaying().get(match.getRoundYou()).getNumCoin() >= 3){
                                match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.AdamKosh);
                            }
                            else{
                                return;
                            }
                        }
                        else if(x >= 1150 & x <= 1350 & y >= 650 & y <= 750){
                            match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.Safir0);
                        }
                        else if(x >= 1375 & x <= 1575 & y >= 650 & y <= 750){
                            if(match.getPlayersArePlaying().get(match.getRoundYou()).getNumCoin() >= 1){
                                match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.ChangeCard1);

                            }
                        }
                        else{
                            return;
                        }
                        numClickedForHisRound++;

                    }
                    else if(numClickedForHisRound == 1){
                        if(match.getPlayersArePlaying().get(match.getRoundYou()).getActionForRound() == Action.ChangeCard1){
                            if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 2){
                                if(x >= 680 & x <= 800 & y >= YsForCards[0] & y <= YsForCards[0] + 200){
                                    match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.ChangeCard1);
                                }
                                else if(x >= 830 & x <= 950 & y >= YsForCards[1] & y <= YsForCards[1] + 200){
                                    match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.ChangeCard2);
                                }
                                else{
                                    return;
                                }

                                match.DoAction();
                                numClickedForHisRound = 0;
                            }
                            else if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 1){
                                if(x >= 680 & x <= 800 & y >= YsForCards[0]  & y <= YsForCards[0] + 200){
                                    match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.ChangeCard1);
                                }
                                else{
                                    return;
                                }

                                match.DoAction();
                                numClickedForHisRound = 0;
                            }
                            else{
                                return;

                            }


                        }
                        else if(match.getPlayersArePlaying().get(match.getRoundYou()).getActionForRound() == Action.Safir0){
                            if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.Safir0);
                            }
                            else if(x >= 700 & x <= 900 & y >= 650 & y <= 750){
                                match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.SafirBoth);
                            }
                            else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                                match.getPlayersArePlaying().get(match.getNumRound()).setActionForRound(Action.Safir1);
                            }
                            else{
                                return;
                            }


                            for (int i = 0; i < match.getPlayersArePlaying().size(); i++) {
                                if(i != match.getNumRound() & match.getIndexChaleshPlayer() == -1){
                                    if(match.getPlayersArePlaying().get(i).getCardsForPlayer().size() != 0){
                                        ((Robot) match.getPlayersArePlaying().get(i)).ChangeActionForChalesh(match);
                                    }
                                }
                            }

                            if(match.getIndexChaleshPlayer() == -1){
                                numClickedForHisRound = 0;
                                match.DoAction();
                            }
                            else{
                                if(match.IsNumRoundWinChalesh()){
                                    numClickedForHisRound = 0;
                                    ((Robot) match.getPlayersArePlaying().get(match.getIndexChaleshPlayer())).ChangeCardBecauseLooseChalesh(match);
                                    match.DoAction();
                                }
                                else{
                                    numClickedForHisRound = 4;
                                }
                            }


                        }
                        else{
                            if(x >= 565 & x <= 730 & y >= 375 & y <= 515){
                                if(match.getPlayersArePlaying().get((match.getRoundYou() + 1) % 4).getCardsForPlayer().size() != 0){
                                    match.setIndexFrontPlayer((match.getRoundYou() + 1) % 4);
                                    ((Robot) match.getPlayersArePlaying().get(((match.getRoundYou() + 1) % 4))).ChangeActionForAttacked(match);
                                    numClickedForHisRound++;
                                }
                            }

                            else if(x >= 745 & x <= 880 & y >= 250 & y <= 420){
                                if(match.getPlayersArePlaying().get((match.getRoundYou() + 2) % 4).getCardsForPlayer().size() != 0){
                                    match.setIndexFrontPlayer((match.getRoundYou() + 2) % 4);
                                    ((Robot) match.getPlayersArePlaying().get(((match.getRoundYou() + 2) % 4))).ChangeActionForAttacked(match);
                                    numClickedForHisRound++;
                                }
                            }

                            else if(x >= 910 & x <= 1075 & y >= 380 & y <= 510){
                                if(match.getPlayersArePlaying().get((match.getRoundYou() + 3) % 4).getCardsForPlayer().size() != 0){
                                    match.setIndexFrontPlayer((match.getRoundYou() + 3) % 4);
                                    ((Robot) match.getPlayersArePlaying().get(((match.getRoundYou() + 3) % 4))).ChangeActionForAttacked(match);
                                    numClickedForHisRound++;
                                }

                            }

                            else{
                                return;
                            }
                        }
                    }
                    else if(numClickedForHisRound == 3){
                        if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                            indexShouldStartFromForChaleshForYouRound++;
                        }
                        else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                            match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.ChaleshForSecond);
                            indexShouldStartFromForChaleshForYouRound++;
                        }
                    }
                    else if(numClickedForHisRound == 4) {
                        if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 2){
                            if(x >= 680 & x <= 800 & y >= YsForCards[0] & y <= YsForCards[0] + 200){
                                match.getPlayersArePlaying().get(match.getNumRound()).setIndexCardShouldChangeForChalesh(0);
                            }
                            else if(x >= 830 & x <= 950 & y >= YsForCards[1] & y <= YsForCards[1] + 200){
                                match.getPlayersArePlaying().get(match.getNumRound()).setIndexCardShouldChangeForChalesh(1);

                            }
                            else{
                                return;
                            }

                            match.DoAction();
                            numClickedForHisRound = 0;
                        }
                        else if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 1){
                            if(x >= 680 & x <= 800 & y >= YsForCards[0] & y <= YsForCards[0] + 200){
                                match.getPlayersArePlaying().get(match.getNumRound()).setIndexCardShouldChangeForChalesh(0);
                            }
                            else{
                                return;
                            }

                            match.DoAction();
                            numClickedForHisRound = 0;
                        }

                    }
                }

                else{
                    if(indexChangeCardBecauseLooseChalesh != 0) {
                        if(match.getPlayersArePlaying().get(match.getRoundYou()).getCardsForPlayer().size() == 2){
                            if(x >= 680 & x <= 830 & y >= 785 & y <= 985){
                                match.getPlayersArePlaying().get(match.getRoundYou()).setIndexCardShouldChangeForChalesh(0);
                                indexChangeCardBecauseLooseChalesh = 2;
                            }
                            else if(x >= 830 & x <= 950 & y >= 785 & y <= 985){
                                match.getPlayersArePlaying().get(match.getRoundYou()).setIndexCardShouldChangeForChalesh(1);
                                indexChangeCardBecauseLooseChalesh = 2;
                            }
                        }
                        else if(match.getPlayersArePlaying().get(match.getRoundYou()).getCardsForPlayer().size() == 1){
                            if(x >= 680 & x <= 830 & y >= 785 & y <= 985){
                                match.getPlayersArePlaying().get(match.getRoundYou()).setIndexCardShouldChangeForChalesh(0);
                                indexChangeCardBecauseLooseChalesh = 2;
                            }
                            else{
                                return;
                            }

                            match.DoAction();
                            numClickedForHisRound = 0;
                        }

                    }
                    else if (match.getRoundYou() == match.getIndexFrontPlayer()) {
                        if (indexFrontPlayerForRoundYou == 0) {
                            if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.AdamKosh) {
                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForAttacked(Action.NoAction);
                                    indexFrontPlayerForRoundYou++;

                                }
                                else if(x >= 950 & x <= 1150 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForAttacked(Action.ShahDokht);
                                    indexFrontPlayerForRoundYou++;
                                }
                            }
                            else if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Farmandeh) {
                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForAttacked(Action.NoAction);
                                    indexFrontPlayerForRoundYou++;
                                }
                                else if(x >= 700 & x <= 900 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForAttacked(Action.Safir0);
                                    indexFrontPlayerForRoundYou++;
                                }
                                else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForAttacked(Action.Farmandeh);
                                    indexFrontPlayerForRoundYou++;
                                }
                            }
                        }
                        else if (indexShouldStartFromForChalesh == match.getRoundYou()) {
                            if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.DaryafteKomakeKhraeji) {

                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.NoAction);
                                    indexShouldStartFromForChalesh++;
                                }
                                else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.ChaleshForFirst);
                                    indexShouldStartFromForChalesh++;
                                }
                            }
                            else if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir0 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir1 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.SafirBoth) {
                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.NoAction);
                                    indexShouldStartFromForChalesh++;

                                }
                                else if(x >= 950 & x <= 1150 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.ChaleshForFirst);
                                    indexShouldStartFromForChalesh++;
                                }

                            }

                            else{
                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.NoAction);
                                    indexShouldStartFromForChalesh++;
                                }
                                else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.ChaleshForFirst);
                                    indexShouldStartFromForChalesh++;
                                }
                            }
                        }
                    }
                    else if(match.getRoundYou() != match.getIndexFrontPlayer()){
                        if (indexShouldStartFromForChalesh == match.getRoundYou()) {
                            if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.DaryafteKomakeKhraeji) {

                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.NoAction);
                                    indexShouldStartFromForChalesh++;
                                }
                                else if(x >= 700 & x <= 900 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.ChaleshForFirst);
                                    indexShouldStartFromForChalesh++;
                                }
                                else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.BozorgZade);
                                    indexShouldStartFromForChalesh++;
                                }
                            }
                            else if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir0 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir1 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.SafirBoth) {
                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.NoAction);
                                    indexShouldStartFromForChalesh++;

                                }
                                else if(x >= 950 & x <= 1150 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.ChaleshForFirst);
                                    indexShouldStartFromForChalesh++;
                                }

                            }

                            else{
                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.NoAction);
                                    indexShouldStartFromForChalesh++;
                                }
                                else if(x >= 700 & x <= 900 & y >= 650 & y <= 750){
                                    match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.ChaleshForFirst);
                                    indexShouldStartFromForChalesh++;
                                }
                                if(match.getIndexFrontPlayer() != -1){
                                    if((match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.ShahDokht | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.AdamKosh | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Farmandeh | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir0 | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir1 |
                                            match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.SafirBoth)){
                                        if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                                            match.getPlayersArePlaying().get(match.getRoundYou()).setActionForChalesh(Action.ChaleshForSecond);
                                            indexShouldStartFromForChalesh++;
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {

            }
        });

        addMouseMotionListener(new MouseAdapter() {



            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if(match.getNumRound() == match.getRoundYou()){
                    if(numClickedForHisRound == 0){
                        if(match.getPlayersArePlaying().get(match.getNumRound()).getNumCoin() >= 10){
                            if(x >= 725 & x <= 925 & y >= 650 & y <= 750){
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                        }

                        else if(x >= 25 & x <= 225 & y >= 650 & y <= 750){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                        else if(x >= 250 & x <= 450 & y >= 650 & y <= 750){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                        else if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                        else if(x >= 700 & x <= 900 & y >= 650 & y <= 750){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                        else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                            if(match.getPlayersArePlaying().get(match.getRoundYou()).getNumCoin() >= 3){
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                        }
                        else if(x >= 1150 & x <= 1350 & y >= 650 & y <= 750){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                        else if(x >= 1375 & x <= 1575 & y >= 650 & y <= 750){
                            if(match.getPlayersArePlaying().get(match.getRoundYou()).getNumCoin() >= 1){
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                        }
                        else{
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                    }
                    else if(numClickedForHisRound == 1){

                        if(match.getPlayersArePlaying().get(match.getRoundYou()).getActionForRound() == Action.ChangeCard1){

                            if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 2){

                                if(x >= 680 & x <= 800 & y >= YsForCards[0] & y <= YsForCards[0] + 200){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    if(YsForCards[0] == 780){
                                        YsForCards[0] -= 50;
                                    }

                                }
                                else if(x >= 830 & x <= 950 & y >= YsForCards[1] & y <= YsForCards[1] + 200){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    if(YsForCards[1] == 780){
                                        YsForCards[1] -= 50;

                                    }

                                }
                                else{
                                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                    YsForCards[0] = 780;
                                    YsForCards[1] = 780;


                                }

                            }
                            else if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 1){
                                if(x >= 680 & x <= 800 & y >= YsForCards[0] & y <= YsForCards[0] + 200){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    if(YsForCards[0] == 780){
                                        YsForCards[0] -= 50;

                                    }

                                }
                                else{
                                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                    YsForCards[0] = 780;
                                    YsForCards[1] = 780;

                                }
                            }
                            else{
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                            }
                        }
                        else if(match.getPlayersArePlaying().get(match.getRoundYou()).getActionForRound() == Action.Safir0){
                            if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                            else if(x >= 700 & x <= 900 & y >= 650 & y <= 750){
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                            else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                            else{
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }

                        }

                        else{
                            if(x >= 565 & x <= 730 & y >= 375 & y <= 515){
                                if(match.getPlayersArePlaying().get((match.getRoundYou() + 1) % 4).getCardsForPlayer().size() != 0){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                            }

                            else if(x >= 745 & x <= 880 & y >= 250 & y <= 420){
                                if(match.getPlayersArePlaying().get((match.getRoundYou() + 2) % 4).getCardsForPlayer().size() != 0){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                            }

                            else if(x >= 910 & x <= 1075 & y >= 380 & y <= 510){
                                if(match.getPlayersArePlaying().get((match.getRoundYou() + 3) % 4).getCardsForPlayer().size() != 0){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                            }
                            else{
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }
                        }
                    }
                    else if(numClickedForHisRound == 3){
                        if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                        else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                        else{
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                    }
                    else if(numClickedForHisRound == 4) {
                        if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 2){

                            if(x >= 680 & x <= 800 & y >= YsForCards[0] & y <= YsForCards[0] + 200){
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                                if(YsForCards[0] == 780){
                                    YsForCards[0] -= 50;
                                }

                            }
                            else if(x >= 830 & x <= 950 & y >= YsForCards[1] & y <= YsForCards[1] + 200){
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                                if(YsForCards[1] == 780){
                                    YsForCards[1] -= 50;

                                }

                            }
                            else{
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                YsForCards[0] = 780;
                                YsForCards[1] = 780;


                            }

                        }
                        else if(match.getPlayersArePlaying().get(match.getNumRound()).getCardsForPlayer().size() == 1){
                            if(x >= 680 & x <= 800 & y >= YsForCards[0] & y <= YsForCards[0] + 200){
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                                if(YsForCards[0] == 780){
                                    YsForCards[0] -= 50;

                                }

                            }
                            else{
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                YsForCards[0] = 780;
                                YsForCards[1] = 780;

                            }
                        }
                    }


                }

                else{
                    if(indexChangeCardBecauseLooseChalesh != 0) {
                        if(match.getPlayersArePlaying().get(match.getRoundYou()).getCardsForPlayer().size() == 2){
                            if(x >= 680 & x <= 830 & y >= 785 & y <= 985){
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                            else if(x >= 830 & x <= 950 & y >= 785 & y <= 985){
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                            else{
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }
                        }
                        else if(match.getPlayersArePlaying().get(match.getRoundYou()).getCardsForPlayer().size() == 1){
                            if(x >= 680 & x <= 830 & y >= 785 & y <= 985){
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                            else{
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }
                        }

                    }
                    else if (match.getRoundYou() == match.getIndexFrontPlayer()) {
                        if (indexFrontPlayerForRoundYou == 0) {
                            if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.AdamKosh) {
                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                                else if(x >= 950 & x <= 1150 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                                else{
                                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }
                            }
                            else if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Farmandeh) {
                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                                else if(x >= 700 & x <= 900 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                                else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                                else{
                                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }
                            }
                        }
                        else if (indexShouldStartFromForChalesh == match.getRoundYou()) {

                            if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.DaryafteKomakeKhraeji) {

                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));

                                }
                                else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));

                                }
                                else{
                                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }
                            }
                            else if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir0 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir1 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.SafirBoth) {

                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                                else if(x >= 950 & x <= 1150 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                                else{
                                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }

                            }

                            else{
                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }

                                else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }

                                else{
                                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }

                            }
                        }
                    }

                    else if(match.getRoundYou() != match.getIndexFrontPlayer()){

                        if (indexShouldStartFromForChalesh == match.getRoundYou()) {

                            if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.DaryafteKomakeKhraeji) {

                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));

                                }
                                else if(x >= 700 & x <= 900 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));

                                }
                                else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));

                                }
                                else{
                                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }
                            }
                            else if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir0 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir1 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.SafirBoth) {

                                if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                                else if(x >= 950 & x <= 1150 & y >= 650 & y <= 750){
                                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                                else{
                                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }

                            }

                            else{
                                if(match.getIndexFrontPlayer() != -1){
                                    if((match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.ShahDokht | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.AdamKosh | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Farmandeh | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir0 | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir1 |
                                            match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.SafirBoth)){
                                        if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                                        }
                                        else if(x >= 700 & x <= 900 & y >= 650 & y <= 750){
                                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                                        }
                                        else if(x >= 925 & x <= 1125 & y >= 650 & y <= 750){
                                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                                        }
                                        else{
                                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                        }
                                    }
                                }

                                else{
                                    if(x >= 475 & x <= 675 & y >= 650 & y <= 750){
                                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    }
                                    else if(x >= 700 & x <= 900 & y >= 650 & y <= 750){
                                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    }
                                    else{
                                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                    }
                                }

                            }
                        }
                    }
                }

            }
        });

    }

    @Override
    public void paint(Graphics g) {


        if(match.getNumRound() != match.getRoundYou()){
            YsForCards[0] = 780;
            YsForCards[1] = 780;
        }
        super.paint(g);
        BufferedImage image = null;
        try {
            File file1 = new File(".\\src\\main\\java\\Images\\Back2.png");
            image = ImageIO.read(file1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(image, 0 , 0, null);
        int counter = 0;



        BufferedImage image2 = null;
        try {
            File file1 = new File(".\\src\\main\\java\\Images\\Bank.png");
            image2 = ImageIO.read(file1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(image2, -10 , -10, null);

        g.setFont(new Font("SERIF", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString("Coin: " + match.getBank().getCoins(), 300 , 130);
        g.drawString("Num cards: " + match.getBank().getCards().size(), 300 , 180);



        for (int i = 0; i < 4; i++) {
            counter = 0;

            if(i != match.getRoundYou()){
                if(i == (match.getRoundYou() + 1) % 4){
                    try {
                        File file1 = new File(".\\src\\main\\java\\Images\\" + ((match.getRoundYou() + 1) % 4  + 1)  +"Right.png");
                        image = ImageIO.read(file1);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    g.drawImage(image, 550 , 360, null);


                    for (int j = 0; j < match.getPlayersArePlaying().get(i).getCardsForPlayer().size(); j++) {
                        try {
                            File file1 = new File(".\\src\\main\\java\\Images\\CartToRight.png");
                            image = ImageIO.read(file1);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        g.drawImage(image, 40 , 315 + counter * 150, null);
                        counter++;
                    }



                }

                else if(i == (match.getRoundYou() + 2) % 4){

                    try {
                        File file1 = new File(".\\src\\main\\java\\Images\\" + ((match.getRoundYou() + 2) % 4  + 1)  +"Down.png");
                        image = ImageIO.read(file1);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    g.drawImage(image, 715 , 250, null);


                    for (int j = 0; j < match.getPlayersArePlaying().get(i).getCardsForPlayer().size(); j++) {
                        try {
                            File file1 = new File(".\\src\\main\\java\\Images\\CartToDown.png");
                            image = ImageIO.read(file1);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        g.drawImage(image, 675 + counter * 150 , 0, null);
                        counter++;

                    }
                }

                else if(i == (match.getRoundYou() + 3) % 4){

                    try {
                        File file1 = new File(".\\src\\main\\java\\Images\\" + ((match.getRoundYou() + 3) % 4  + 1)  +"Left.png");
                        image = ImageIO.read(file1);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    g.drawImage(image, 890 , 360, null);



                    for (int j = 0; j < match.getPlayersArePlaying().get(i).getCardsForPlayer().size(); j++) {
                        try {
                            File file1 = new File(".\\src\\main\\java\\Images\\CartToLeft.png");
                            image = ImageIO.read(file1);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        g.drawImage(image, 1352 , 315 + counter * 150, null);
                        counter++;
                    }
                }
            }

            else{
                try {
                    File file1 = new File(".\\src\\main\\java\\Images\\" + ((match.getRoundYou()) % 4  + 1)  +"Up.png");
                    image = ImageIO.read(file1);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(image, 715 , 470, null);

                for (int j = 0; j < match.getPlayersArePlaying().get(match.getRoundYou()).getCardsForPlayer().size(); j++) {

                    try {
                        File file1 = null;
                        if( match.getPlayersArePlaying().get(match.getRoundYou()).getCardsForPlayer().get(j) == Cards.AdamKosh){
                            file1 = new File(".\\src\\main\\java\\Images\\Adamkosh.png");
                        }
                        else if( match.getPlayersArePlaying().get(match.getRoundYou()).getCardsForPlayer().get(j) == Cards.BozorgZade){
                            file1 = new File(".\\src\\main\\java\\Images\\Bozorgzade.png");
                        }
                        else if( match.getPlayersArePlaying().get(match.getRoundYou()).getCardsForPlayer().get(j) == Cards.Farmandeh){
                            file1 = new File(".\\src\\main\\java\\Images\\Farmandeh.png");
                        }
                        else if( match.getPlayersArePlaying().get(match.getRoundYou()).getCardsForPlayer().get(j) == Cards.Safir){
                            file1 = new File(".\\src\\main\\java\\Images\\Safir.png");
                        }
                        else if( match.getPlayersArePlaying().get(match.getRoundYou()).getCardsForPlayer().get(j) == Cards.ShahDokht){
                            file1 = new File(".\\src\\main\\java\\Images\\ShahDokht.png");
                        }

                        image = ImageIO.read(file1);
                        g.drawImage(image, 675 + counter * 150 , YsForCards[j], null);
                        counter++;
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                try {
                    File file1 = new File(".\\src\\main\\java\\Images\\Coin.png");
                    image = ImageIO.read(file1);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(image, 1450 , 780 , null);


                g.setColor(Color.BLACK);
                g.setFont(new Font("SERIF", Font.BOLD, 50));
                g.drawString(match.getPlayersArePlaying().get(match.getRoundYou()).getNumCoin() + "", 1500 , 950);
            }
        }

        match.WhoIsWinner();
        if(match.getWinner() != -1){
            g.drawString("Winner is : " + match.getWinner() , 800, 500 );
            return;
        }

        Color color;


        Graphics2D g3 = (Graphics2D) g;
        g3.setColor(Color.BLACK);
        g3.setStroke(new BasicStroke(3));
        g3.drawLine(0, 760 , 2000 , 760);

        if(match.getRoundYou() == 0){
            color = new Color(232, 84, 20);
        }
        else if(match.getRoundYou() == 1){
            color = new Color(255, 2, 4);
        }
        else if(match.getRoundYou() == 2){
            color = new Color(2, 115, 183);
        }
        else{
            color = new Color(20, 114, 37);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("SERIF", Font.BOLD+Font.ITALIC, 50));
        g.drawString("turn: " + (match.getNumRound() + 1), 1450, 50);





        if(match.getRoundYou() == match.getNumRound()){
            int count = 0;

            if(numClickedForHisRound == 0){
                if(match.getPlayersArePlaying().get(match.getNumRound()).getNumCoin() >= 10){
                    g.setColor(color);
                    g.fillOval(725 , 650, 200 , 100);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("SERIF", Font.ITALIC, 30));
                    g.drawString("Coup", 790, 710);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(5));
                    g2.setColor(Color.WHITE);
                    g2.drawOval(725 , 660, 180, 80);
                    count++;
                    return;
                }

                g.setColor(color);
                g.fillOval(225 * count + 25 , 650, 200 , 100);
                g.setColor(Color.BLACK);
                g.setFont(new Font("SERIF", Font.ITALIC, 25));
                g.drawString("KomakeKhraeji", 225 * count + 50 , 710);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.WHITE);
                g2.drawOval(225 * count + 35 , 660, 180, 80);
                count++;

                g.setColor(color);
                g.fillOval(225 * count + 25   , 650, 200 , 100);
                g.setColor(Color.BLACK);
                g.setFont(new Font("SERIF", Font.ITALIC, 25));
                g.drawString("KasbeDaramad", 225 * count + 45 , 710);
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.WHITE);
                g2.drawOval(225 * count + 35 , 660, 180, 80);
                count++;

                g.setColor(color);
                g.fillOval(225 * count + 25  , 650, 200 , 100);
                g.setColor(Color.BLACK);
                g.setFont(new Font("SERIF", Font.ITALIC, 25));
                g.drawString("BozorgZade", 225 * count + 60 , 710);
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.WHITE);
                g2.drawOval(225 * count + 35 , 660, 180, 80);
                count++;

                g.setColor(color);
                g.fillOval(225 * count + 25  , 650, 200 , 100);
                g.setColor(Color.BLACK);
                g.setFont(new Font("SERIF", Font.ITALIC, 25));
                g.drawString("Farmandeh", 225 * count + 65 , 710);
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.WHITE);
                g2.drawOval(225 * count + 35 , 660, 180, 80);
                count++;

                g.setColor(color);
                g.fillOval(225 * count + 25  , 650, 200 , 100);
                g.setColor(Color.BLACK);
                g.setFont(new Font("SERIF", Font.ITALIC, 25));
                g.drawString("AdamKosh", 225 * count + 70 , 710);
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.WHITE);
                g2.drawOval(225 * count + 35 , 660, 180, 80);
                count++;

                g.setColor(color);
                g.fillOval(225 * count + 25   , 650, 200 , 100);
                g.setColor(Color.BLACK);
                g.setFont(new Font("SERIF", Font.ITALIC, 25));
                g.drawString("Safir", 225 * count + 100 , 710);
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.WHITE);
                g2.drawOval(225 * count + 35 , 660, 180, 80);
                count++;

                g.setColor(color);
                g.fillOval(225 * count + 25   , 650, 200 , 100);
                g.setColor(Color.BLACK);
                g.setFont(new Font("SERIF", Font.ITALIC, 25));
                g.drawString("Change card", 225 * count + 50, 710);
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.WHITE);
                g2.drawOval(225 * count + 35 , 660, 180, 80);
                count++;
            }

            else if (numClickedForHisRound == 1){
                if(match.getPlayersArePlaying().get(match.getRoundYou()).getActionForRound() == Action.ChangeCard1){
                    g.setColor(color);
                    g.fillOval(225 * 2 + 25  , 650, 200 , 100);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("SERIF", Font.ITALIC, 25));
                    g.drawString("First", 225 * 2 + 100 , 710);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(5));
                    g2.setColor(Color.WHITE);
                    g2.drawOval(225 * 2 + 35 , 660, 180, 80);
                    count++;


                    g.setColor(color);
                    g.fillOval(225 * 4 + 25  , 650, 200 , 100);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("SERIF", Font.ITALIC, 25));
                    g.drawString("Second", 225 * 4 + 90 , 710);
                    g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(5));
                    g2.setColor(Color.WHITE);
                    g2.drawOval(225 * 4 + 35 , 660, 180, 80);
                    count++;
                }
                else if(match.getPlayersArePlaying().get(match.getRoundYou()).getActionForRound() == Action.Safir0){
                    g.setColor(color);
                    g.fillOval(225 * 2 + 25  , 650, 200 , 100);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("SERIF", Font.ITALIC, 25));
                    g.drawString("First", 225 * 2 + 100 , 710);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(5));
                    g2.setColor(Color.WHITE);
                    g2.drawOval(225 * 2 + 35 , 660, 180, 80);
                    count++;


                    g.setColor(color);
                    g.fillOval(225 * 3 + 25  , 650, 200 , 100);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("SERIF", Font.ITALIC, 25));
                    g.drawString("Both", 225 * 3 + 100 , 710);
                    g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(5));
                    g2.setColor(Color.WHITE);
                    g2.drawOval(225 * 3 + 35 , 660, 180, 80);
                    count++;

                    g.setColor(color);
                    g.fillOval(225 * 4 + 25  , 650, 200 , 100);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("SERIF", Font.ITALIC, 25));
                    g.drawString("Second", 225 * 4 + 90 , 710);
                    g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(5));
                    g2.setColor(Color.WHITE);
                    g2.drawOval(225 * 4 + 35 , 660, 180, 80);
                    count++;
                }

            }

            else if (numClickedForHisRound == 3){
                if (indexShouldStartFromForChaleshForYouRound == match.getRoundYou()) {
                    Graphics2D g2 = (Graphics2D) g;

                    if(match.getIndexFrontPlayer() != -1){
                        if(match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.ShahDokht | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.AdamKosh | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Farmandeh | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir0 | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir1 |
                                match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.SafirBoth){
                            g.setColor(color);
                            g.fillOval(225 * 2 + 25, 650, 200, 100);
                            g.setColor(Color.BLACK);
                            g.setFont(new Font("SERIF", Font.ITALIC, 25));
                            g.drawString("No action", 225 * 2 + 50, 710);
                            g2.setStroke(new BasicStroke(5));
                            g2.setColor(Color.WHITE);
                            g2.drawOval(225 * 2 + 35 , 660, 180, 80);


                            g.setColor(color);
                            g.fillOval(225 * 4 + 25, 650, 200, 100);
                            g.setColor(Color.BLACK);
                            g.setFont(new Font("SERIF", Font.ITALIC, 25));
                            g.drawString("Chalesh", 225 * 4 + 55, 710);
                            g2.setStroke(new BasicStroke(5));
                            g2.setColor(Color.WHITE);
                            g2.drawOval(225 * 4 + 35 , 660, 180, 80);
                        }
                    }
                }
            }

            else if(numClickedForHisRound == 4){
                if(match.getIndexChaleshPlayer() != -1){
                    if(!match.IsNumRoundWinChalesh()){
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("Choose one of your cards", 10, 850);
                    }
                }
            }
        }

        else {
            Graphics2D g2 = (Graphics2D) g;

            if(counterForChalesh >= 1){
                g.setColor(Color.BLACK);
                g.setFont(new Font("SERIF", Font.ITALIC, 25));
                g.drawString(match.getNumRound() + "  " + match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound().name(), 10, 800);
            }

            if(match.getIndexFrontPlayer() != -1){
                if(match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() != Action.DaryafteKomakeKhraeji & match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() != Action.BozorgZade & match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() != Action.Safir0 & match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() != Action.Safir1 & match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() != Action.SafirBoth){
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("SERIF", Font.ITALIC, 25));
                    g.drawString("Front: " + match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked().name(), 10, 850);
                }
            }

            if(match.getIndexChaleshPlayer() != -1){
                g.setColor(Color.BLACK);
                g.setFont(new Font("SERIF", Font.ITALIC, 25));
                g.drawString("Index chalesh player: " + match.getPlayersArePlaying().get(match.getIndexChaleshPlayer()).getNumRound() + "", 10, 900);
            }

            if(indexChangeCardBecauseLooseChalesh != 0){
                return;
            }

            if (match.getRoundYou() == match.getIndexFrontPlayer()) {
                if(match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Coup){

                }
                else if (indexFrontPlayerForRoundYou == 0) {
                    if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.AdamKosh) {
                        g.setColor(color);
                        g.fillOval(225 * 2 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("No action", 225 * 2 + 50, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 2 + 35 , 660, 180, 80);

                        g.setColor(color);
                        g.fillOval(225 * 4 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("Shahdokht", 225 * 4 + 50, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 4 + 35 , 660, 180, 80);

                    }
                    else if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Farmandeh) {
                        g.setColor(color);
                        g.fillOval(225 * 2 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("No action", 225 * 2 + 50, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 2 + 35 , 660, 180, 80);


                        g.setColor(color);
                        g.fillOval(225 * 3 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("Safir", 225 * 3 + 100, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 3 + 35 , 660, 180, 80);


                        g.setColor(color);
                        g.fillOval(225 * 4 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("Farmandeh", 225 * 4 + 50, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 4 + 35 , 660, 180, 80);

                    }
                }
                else if (indexShouldStartFromForChalesh == match.getRoundYou()) {
                    if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.DaryafteKomakeKhraeji) {
                        g.setColor(color);
                        g.fillOval(225 * 2 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("No action", 225 * 2 + 50, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 2 + 35 , 660, 180, 80);

                        g.setColor(color);
                        g.fillOval(225 * 4 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("Chalesh 1", 225 * 4 + 55, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 4 + 35 , 660, 180, 80);
                    }
                    else if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir0 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir1 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.SafirBoth) {
                        g.setColor(color);
                        g.fillOval(225 * 2 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("No action", 225 * 2 + 50, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 2 + 35 , 660, 180, 80);

                        g.setColor(color);
                        g.fillOval(225 * 4 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("Chalesh 1", 225 * 4 + 55, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 4 + 35 , 660, 180, 80);
                    }
                    else{
                        g.setColor(color);
                        g.fillOval(225 * 2 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("No action", 225 * 2 + 50, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 2 + 35 , 660, 180, 80);

                        g.setColor(color);
                        g.fillOval(225 * 4 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("Chalesh 1", 225 * 4 + 55, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 4 + 35 , 660, 180, 80);
                    }
                }
            }

            else if (match.getRoundYou() != match.getIndexFrontPlayer()){
                if (indexShouldStartFromForChalesh == match.getRoundYou()) {
                    if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.DaryafteKomakeKhraeji) {
                        g.setColor(color);
                        g.fillOval(225 * 2 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("No action", 225 * 2 + 50, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 2 + 35 , 660, 180, 80);

                        g.setColor(color);
                        g.fillOval(225 * 3 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("Chalesh 1", 225 * 3 + 55, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 3 + 35 , 660, 180, 80);

                        g.setColor(color);
                        g.fillOval(225 * 4 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("Bozorgzade", 225 * 4 + 10, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 4 + 35 , 660, 180, 80);
                    }
                    else if (match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir0 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Safir1 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.SafirBoth) {
                        g.setColor(color);
                        g.fillOval(225 * 2 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("No action", 225 * 2 + 50, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 2 + 35 , 660, 180, 80);

                        g.setColor(color);
                        g.fillOval(225 * 4 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("Chalesh 1", 225 * 4 + 55, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 4 + 35 , 660, 180, 80);
                    }

                    else{
                        g.setColor(color);
                        g.fillOval(225 * 2 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("No action", 225 * 2 + 50, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 2 + 35 , 660, 180, 80);

                        g.setColor(color);
                        g.fillOval(225 * 3 + 25, 650, 200, 100);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("SERIF", Font.ITALIC, 25));
                        g.drawString("Chalesh 1", 225 * 3 + 55, 710);
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(Color.WHITE);
                        g2.drawOval(225 * 3 + 35 , 660, 180, 80);
                        if(match.getIndexFrontPlayer() != -1){
                            if(match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.ShahDokht | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.AdamKosh | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Farmandeh | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir0 | match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.Safir1 |
                                    match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() == Action.SafirBoth){
                                g.setColor(color);
                                g.fillOval(225 * 4 + 25, 650, 200, 100);
                                g.setColor(Color.BLACK);
                                g.setFont(new Font("SERIF", Font.ITALIC, 25));
                                g.drawString("Chalesh 2", 225 * 4 + 55, 710);
                                g2.setStroke(new BasicStroke(5));
                                g2.setColor(Color.WHITE);
                                g2.drawOval(225 * 4 + 35 , 660, 180, 80);
                            }
                        }
                    }
                }
            }

        }
    }

    public void DoAction(){
        if(match.getRoundYou() == match.getNumRound()){
            return;
        }
        counterForChalesh++;
        if(counterForChalesh == 1){
            ((Robot)match.getPlayersArePlaying().get(match.getNumRound())).ChangeActionHisRound(match);

        }

        NotYourRound();
    }

    public void NotYourRound(){
        counterForChalesh++;
        if(indexChangeCardBecauseLooseChalesh == 1){
            return;
        }
        else if(indexFrontPlayerForRoundYou == 0 & match.getIndexFrontPlayer() == match.getRoundYou()){
            return;
        }

        else if(match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.KasbeDaramad | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.ChangeCard1 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.ChangeCard2 | match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() == Action.Coup){
            match.DoAction();
            indexShouldStartFromForChalesh = 0;
            indexFrontPlayerForRoundYou = 0;
            counterForChalesh = 0;
            indexChangeCardBecauseLooseChalesh = 0;
            return;
        }

        else if(match.getIndexFrontPlayer() != -1  & counterForChalesh == 2 & match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() != Action.DaryafteKomakeKhraeji & match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() != Action.BozorgZade & match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() != Action.Safir0 & match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() != Action.Safir1 & match.getPlayersArePlaying().get(match.getNumRound()).getActionForRound() != Action.SafirBoth){
            ((Robot)match.getPlayersArePlaying().get(match.getIndexFrontPlayer())).ChangeActionForAttacked(match);
        }
        for (int i = indexShouldStartFromForChalesh; i < match.getPlayersArePlaying().size(); i++) {
            if(match.getIndexChaleshPlayer() != -1){
                indexShouldStartFromForChalesh = match.getPlayersArePlaying().size();
                break;
            }
            else if(match.getPlayersArePlaying().get(i).getCardsForPlayer().size() == 0){

            }
            else if(i == match.getNumRound()){
                if(match.getIndexFrontPlayer() != -1){
                    if(match.getPlayersArePlaying().get(match.getIndexFrontPlayer()).getActionForAttacked() != Action.NoAction){
                        ((Robot)match.getPlayersArePlaying().get(i)).ChangeActionForChalesh(match);
                    }
                }
            }
            else if(i == match.getRoundYou()){
                break;
            }
            else{
                if(match.getPlayersArePlaying().get(i).getCardsForPlayer().size() != 0){
                    ((Robot)match.getPlayersArePlaying().get(i)).ChangeActionForChalesh(match);
                }
            }
            indexShouldStartFromForChalesh++;
        }



        if(indexShouldStartFromForChalesh == match.getPlayersArePlaying().size()){
            match.getIndexChaleshInAllPlayers();
            if(match.getIndexChaleshPlayer() != -1){
                if(match.getIndexChaleshPlayer() != match.getRoundYou()){
                    if(match.IsNumRoundWinChalesh()){
                        ((Robot)match.getPlayersArePlaying().get(match.getIndexChaleshPlayer())).ChangeCardBecauseLooseChalesh(match);
                    }
                    else{
                        ((Robot)match.getPlayersArePlaying().get(match.getNumRound())).ChangeCardBecauseLooseChalesh(match);
                    }
                }
                else{
                    if(match.IsNumRoundWinChalesh()){
                        if(indexChangeCardBecauseLooseChalesh == 0){
                            indexChangeCardBecauseLooseChalesh++;
                            return;
                        }
                    }
                    else{
                        ((Robot)match.getPlayersArePlaying().get(match.getNumRound())).ChangeCardBecauseLooseChalesh(match);
                    }
                }
            }



            match.DoAction();
            indexShouldStartFromForChalesh = 0;
            indexFrontPlayerForRoundYou = 0;
            counterForChalesh = 0;
            indexChangeCardBecauseLooseChalesh = 0;

        }

    }

    public void YourRound(){
        if(match.getNumRound() != match.getRoundYou()){
            return;
        }
        if(numClickedForHisRound != 2){
            return;
        }

        for (int i = indexShouldStartFromForChaleshForYouRound; i < match.getPlayersArePlaying().size(); i++) {
            if(match.getIndexChaleshPlayer() != -1){
                indexShouldStartFromForChaleshForYouRound = match.getPlayersArePlaying().size();
                System.out.println("hello");
                break;
            }
            else if(match.getPlayersArePlaying().get(i).getCardsForPlayer().size() == 0){

            }
            else if(i == match.getIndexFrontPlayer()){
                ((Robot)match.getPlayersArePlaying().get(i)).ChangeActionForChalesh(match);
            }
            else if(i == match.getRoundYou()){
                numClickedForHisRound = 3;
                break;
            }
            else{
                if(match.getPlayersArePlaying().get(i).getCardsForPlayer().size() != 0){
                    ((Robot)match.getPlayersArePlaying().get(i)).ChangeActionForChalesh(match);
                }
            }
            indexShouldStartFromForChaleshForYouRound++;
        }



        if(indexShouldStartFromForChaleshForYouRound == match.getPlayersArePlaying().size()){
            if(match.getIndexChaleshPlayer() != -1){
                if(match.IsNumRoundWinChalesh()){
                    ((Robot)match.getPlayersArePlaying().get(match.getIndexChaleshPlayer())).ChangeCardBecauseLooseChalesh(match);
                }
                else{
                    numClickedForHisRound = 4;
                    return;
                }
            }

            match.DoAction();
            numClickedForHisRound = 0;
            indexShouldStartFromForChaleshForYouRound = 0;


        }

    }

    public static void main(String[] args) {
        int w = 1000;
        int h = 950;
        int k = 600;
        final JFrame frame = new JFrame();
        frame.setTitle("Game");

        final Main graphicPanel = new Main();

        frame.setSize(w + k + 40, h + 100);
        frame.setLocation(1920 - (w + k + 40) / 2,1080 - (h + 100) / 2);
        frame.getContentPane().add(graphicPanel);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    graphicPanel.repaint();
                }
            }
        });

        thread.start();

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    graphicPanel.DoAction();
                    graphicPanel.YourRound();

                }
            }
        });

        thread1.start();


//        Thread thread2 = new Thread(new Runnable() {
//            public void run() {
//                while (true) {
//
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    System.out.println(graphicPanel.match.toString());
//                }
//            }
//        });
//
//        thread2.start();

    }

}
