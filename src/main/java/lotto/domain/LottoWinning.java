package lotto.domain;


public enum LottoWinning {

    FIFTH_PRIZE(3, 5_000),
    FOURTH_PRIZE(4, 50_000),
    THIRD_PRIZE(5, 1_500_000),
    SECOND_PRIZE(5, 30_000_000),
    FIRST_PRIZE(6, 2_000_000_000),
    ;

    private final int matchingNumberCount;
    private final int reward;

    LottoWinning(int matchingNumberCount, int reward) {
        this.matchingNumberCount = matchingNumberCount;
        this.reward = reward;
    }

    public int getMatchingNumberCount() {
        return matchingNumberCount;
    }

    public Integer getReward() {
        return reward;
    }
}
