package lotto.domain;


import java.util.stream.Stream;

// 클래스 네이밍 가독성 향상
public enum Rank {

    FIFTH_PRIZE(3, 5_000, false),
    FOURTH_PRIZE(4, 50_000, false),
    THIRD_PRIZE(5, 1_500_000, false),
    SECOND_PRIZE(5, 30_000_000, true),
    FIRST_PRIZE(6, 2_000_000_000, false),
    ;

    private final int matchingNumberCount;
    private final int reward;
    private final boolean isBonusNumberMatched;

    Rank(int matchingNumberCount, int reward, boolean isBonusNumberMatched) {
        this.matchingNumberCount = matchingNumberCount;
        this.reward = reward;
        this.isBonusNumberMatched = isBonusNumberMatched;
    }

    public int getMatchingNumberCount() {
        return matchingNumberCount;
    }

    public Integer getReward() {
        return reward;
    }

    // 예외 메시지 추가
    public static Rank findLottoWinning(int matchingNumberCount, boolean isBonusNumberMatched) {
        return Stream.of(values())
                .filter(rank -> rank.matchingNumberCount == matchingNumberCount)
                .filter(rank -> rank.isBonusNumberMatched == isBonusNumberMatched)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException());
    }
}
