package games.jaipurskeleton;

import core.AbstractGameState;
import core.AbstractParameters;
import evaluation.TunableParameters;
import games.jaipurskeleton.components.JaipurCard;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>This class should hold a series of variables representing game parameters (e.g. number of cards dealt to players,
 * maximum number of rounds in the game etc.). These parameters should be used everywhere in the code instead of
 * local variables or hard-coded numbers, by accessing these parameters from the game state via {@link AbstractGameState#getGameParameters()}.</p>
 *
 * <p>It should then implement appropriate {@link #_copy()}, {@link #_equals(Object)} and {@link #hashCode()} functions.</p>
 *
 * <p>The class can optionally extend from {@link TunableParameters} instead, which allows to use
 * automatic game parameter optimisation tools in the framework.</p>
 */
public class JaipurParameters extends AbstractParameters {
    Map<JaipurCard.GoodType, Integer> goodNCardsMinimumSell = new HashMap<JaipurCard.GoodType, Integer>() {{
        put(JaipurCard.GoodType.Diamonds, 2);
        put(JaipurCard.GoodType.Gold, 2);
        put(JaipurCard.GoodType.Silver, 2);
        put(JaipurCard.GoodType.Cloth, 1);
        put(JaipurCard.GoodType.Spice, 1);
        put(JaipurCard.GoodType.Leather, 1);
    }};
    Map<Integer, Integer[]> bonusTokensAvailable = new HashMap<Integer, Integer[]>() {{
        put(3, new Integer[]{1,1,2,2,2,3,3});
        put(4, new Integer[]{4,4,5,5,6,6});
        put(5, new Integer[]{8,8,9,10,10});
    }};

    Map<JaipurCard.GoodType, Integer[]> goodTokensProgression = new HashMap<JaipurCard.GoodType,Integer[]>(){{
       put(JaipurCard.GoodType.Diamonds,new Integer[]{5,5,5,7,7});
       put(JaipurCard.GoodType.Gold,new Integer[]{5,5,5,6,6});
       put(JaipurCard.GoodType.Silver,new Integer[]{5,5,5,5,5});
       put(JaipurCard.GoodType.Cloth,new Integer[]{1,1,2,2,3,3,5});
       put(JaipurCard.GoodType.Spice,new Integer[]{1,1,2,2,3,3,5});
       put(JaipurCard.GoodType.Leather,new Integer[]{1,1,1,1,1,1,2,3,4});
    }};
    int nPointsMostCamels = 5;
    int nGoodTokensEmptyRoundEnd = 3;
    int nRoundsWinForGameWin = 2;
    int nInitialDiamond = 6;
    int nInitialGold = 6;
    int nInitialSilver = 6;
    int nInitialCloth = 8;
    int nInitialSpice = 8;
    int nInitialLeather =10;
    int nInitialCamel=11;
    int nInitialCamelInMarket=3;

    boolean usingCreativeRule = false;
    public JaipurParameters(long seed) {
        super(seed);
    }

    // Copy constructor
    private JaipurParameters(long seed, JaipurParameters jaipurParameters) {
        super(seed);
        this.goodNCardsMinimumSell = new HashMap<>(jaipurParameters.getGoodNCardsMinimumSell());
        this.bonusTokensAvailable = new HashMap<>();
        for (int n: jaipurParameters.getBonusTokensAvailable().keySet()) {
            this.bonusTokensAvailable.put(n, jaipurParameters.getBonusTokensAvailable().get(n).clone());
        }
        this.goodTokensProgression = new HashMap<>();
        for (JaipurCard.GoodType n: jaipurParameters.getGoodTokensProgression().keySet()) {
            this.goodTokensProgression.put(n, jaipurParameters.getGoodTokensProgression().get(n).clone());
        }
        this.nPointsMostCamels = jaipurParameters.getNPointsMostCamels();
        this.nGoodTokensEmptyRoundEnd = jaipurParameters.getNGoodTokensEmptyGameEnd();
        this.nRoundsWinForGameWin = jaipurParameters.getnRoundsWinForGameWin();
        this.nInitialDiamond = jaipurParameters.getnInitialDiamond();
        this.nInitialGold = jaipurParameters.getnInitialGold();
        this.nInitialSilver = jaipurParameters.getnInitialSilver();
        this.nInitialCloth = jaipurParameters.getnInitialCloth();
        this.nInitialSpice = jaipurParameters.getnInitialSpice();
        this.nInitialLeather = jaipurParameters.getnInitialLeather();
        this.nInitialCamel = jaipurParameters.getnInitialCamel();
        this.nInitialCamelInMarket = jaipurParameters.getnInitialCamelInMarket();
        this.usingCreativeRule = jaipurParameters.getusingCreativeRule();
    }

    public Map<JaipurCard.GoodType, Integer> getGoodNCardsMinimumSell() {
        return goodNCardsMinimumSell;
    }

    public Map<Integer, Integer[]> getBonusTokensAvailable() {
        return bonusTokensAvailable;
    }

    public Map<JaipurCard.GoodType, Integer[]> getGoodTokensProgression() {
        return goodTokensProgression;
    }

    public int getnInitialDiamond() {
        return nInitialDiamond;
    }

    public int getnInitialGold() {
        return nInitialGold;
    }

    public int getnInitialSilver() {
        return nInitialSilver;
    }

    public int getnInitialCloth() {
        return nInitialCloth;
    }

    public int getnInitialSpice() {
        return nInitialSpice;
    }

    public int getnInitialLeather() {
        return nInitialLeather;
    }

    public int getnInitialCamel() {
        return nInitialCamel;
    }

    public int getnInitialCamelInMarket() {
        return nInitialCamelInMarket;
    }

    public boolean getusingCreativeRule(){return usingCreativeRule;}
    public int getNPointsMostCamels() {
        return nPointsMostCamels;
    }

    public int getNGoodTokensEmptyGameEnd() {
        return nGoodTokensEmptyRoundEnd;
    }

    public int getnRoundsWinForGameWin() {
        return nRoundsWinForGameWin;
    }

    @Override
    protected AbstractParameters _copy() {
        return new JaipurParameters(System.currentTimeMillis(), this);
    }

    @Override
    public boolean _equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JaipurParameters)) return false;
        if (!super.equals(o)) return false;
        JaipurParameters that = (JaipurParameters) o;
        return nPointsMostCamels == that.nPointsMostCamels && nGoodTokensEmptyRoundEnd == that.nGoodTokensEmptyRoundEnd && nRoundsWinForGameWin == that.nRoundsWinForGameWin && nInitialDiamond == that.nInitialDiamond && nInitialGold == that.nInitialGold && nInitialSilver == that.nInitialSilver && nInitialCloth == that.nInitialCloth && nInitialSpice == that.nInitialSpice && nInitialLeather == that.nInitialLeather && nInitialCamel == that.nInitialCamel && nInitialCamelInMarket == that.nInitialCamelInMarket && usingCreativeRule==that.usingCreativeRule && goodNCardsMinimumSell.equals(that.goodNCardsMinimumSell) && bonusTokensAvailable.equals(that.bonusTokensAvailable) && goodTokensProgression.equals(that.goodTokensProgression);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), goodNCardsMinimumSell, bonusTokensAvailable, goodTokensProgression, nPointsMostCamels, nGoodTokensEmptyRoundEnd, nRoundsWinForGameWin, nInitialDiamond, nInitialGold, nInitialSilver, nInitialCloth, nInitialSpice, nInitialLeather, nInitialCamel, nInitialCamelInMarket, usingCreativeRule);
    }
}
