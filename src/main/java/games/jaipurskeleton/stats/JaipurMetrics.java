package games.jaipurskeleton.stats;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import core.AbstractGameState;
import core.AbstractPlayer;
import core.Game;
import evaluation.listeners.GameListener;
import evaluation.metrics.*;
import games.jaipurskeleton.JaipurGameState;

public class JaipurMetrics implements IMetricsCollection {

    // Inner static class for simple metric: round score difference
    public static class RoundScoreDifference extends AbstractMetric {
        @Override
        public Object run(GameListener listener, Event e) {
            JaipurGameState gs = (JaipurGameState) e.state;
            double scoreDiff = 0;
            for (int i = 0; i < gs.getNPlayers() - 1; i++) {
                scoreDiff += Math.abs(gs.getPlayerScores().get(i).getValue() -
                        gs.getPlayerScores().get(i + 1).getValue());
            }
            return scoreDiff / (gs.getNPlayers() - 1);
        }

        @Override
        public Set<Event.GameEvent> getEventTypes() {
            return Collections.singleton(Event.GameEvent.ROUND_OVER);
        }
    }
}