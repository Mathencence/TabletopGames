package games.jaipurskeleton.stats;

import java.util.*;

import core.CoreConstants;
import core.actions.AbstractAction;
import evaluation.listeners.GameListener;
import evaluation.metrics.*;
import evaluation.summarisers.TAGOccurrenceStatSummary;
import evaluation.summarisers.TAGStatSummary;
import games.jaipurskeleton.JaipurGameState;
import games.jaipurskeleton.actions.TakeCards;
import games.jaipurskeleton.components.JaipurCard;
import games.sushigo.SGGameState;
import games.sushigo.cards.SGCard;
import utilities.Group;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class JaipurMetrics implements IMetricsCollection {

    // Inner static class for simple metric: round score difference
    public static class RoundScoreDifference extends AbstractMetric {
        @Override
        public Object run(GameListener listener, Event e) {
            JaipurGameState gs = (JaipurGameState) e.state;
            double scoreDiff = 0;
            for (int i = 0; i < gs.getNPlayers() - 1; i++) {
                scoreDiff += Math.abs(gs.getPlayerScores().get(i).getValue() - gs.getPlayerScores().get(i + 1).getValue());
            }
            return scoreDiff / (gs.getNPlayers() - 1);
        }
        @Override
        public Set<Event.GameEvent> getEventTypes() {
            return Collections.singleton(Event.GameEvent.ROUND_OVER);
        }
    }
    public static class PurchaseFromMarket extends AbstractParameterizedMetric {
        public PurchaseFromMarket(){super();}
        public PurchaseFromMarket(Object arg){super(arg);}
        @Override
        public Object run(GameListener listener, Event e) {
            AbstractAction action = e.action;
            JaipurCard.GoodType goodType = (JaipurCard.GoodType) getParameterValue("goodType");

            if(action instanceof TakeCards){
                TakeCards tc = (TakeCards) action;
                if(tc.howManyPerTypeTakeFromMarket.containsKey(goodType)){
                    return 1;
                }
            }
            return null;
        }

        @Override
        public Set<Event.GameEvent> getEventTypes() {
            return Collections.singleton(Event.GameEvent.ACTION_CHOSEN);
        }

        @Override
        public List<Group<String, List<?>, ?>> getAllowedParameters() {
            return Collections.singletonList(new Group<>("goodType", Arrays.asList(JaipurCard.GoodType.values()),JaipurCard.GoodType.Diamonds));
        }
        public Map<String, Object> postProcessingGameOver(Event e, TAGStatSummary recordedData){
            // Process the recorded data during the game and return game over summarised data
            Map<String, Object> toRecord = new HashMap<>();
            int nTakeCardsActions = 0;
            for (AbstractAction aa:e.state.getHistory()) {
                if (aa instanceof TakeCards) nTakeCardsActions++;
            }
            toRecord.put(getName() + " : " + e.type , 100* recordedData.n() /  nTakeCardsActions) ;
            return toRecord;
        }
    }
    public static class FirstPlayedWinRound extends AbstractMetric {
        @Override
        public Object run(GameListener listener, Event e) {
            JaipurGameState gs = (JaipurGameState) e.state;
            double max =0;
            int winnerId=-1;
            for (int i=0;i<gs.getNPlayers();i++){
                if(gs.getPlayerScores().get(i).getValue()>max){
                    max = gs.getPlayerScores().get(i).getValue();
                    winnerId = i;
                }
            }
            if (gs.getFirstPlayer()==winnerId) {
                return 100;
            }
            return 0;
        }

        @Override
        public Set<Event.GameEvent> getEventTypes() {
            return Collections.singleton(Event.GameEvent.ROUND_OVER);
        }
    }
    public static class FirstPlayedWinGame extends AbstractMetric {
        @Override
        public Object run(GameListener listener, Event e) {
            JaipurGameState gs = (JaipurGameState) e.state;
            double max = 0;
            int winnerId = -1;
            for (int i = 0; i < gs.getNPlayers(); i++) {
                if (gs.getPlayerScores().get(i).getValue() > max) {
                    max = gs.getPlayerScores().get(i).getValue();
                    winnerId = i;
                }
            }
            if (gs.getFirstPlayer() == winnerId) {
                return 100;
            }
            return 0;
        }

        @Override
        public Set<Event.GameEvent> getEventTypes() {
            return Collections.singleton(Event.GameEvent.GAME_OVER);
        }
    }
}