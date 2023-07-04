package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.LottoNumber;
import lotto.domain.Rank;
import lotto.domain.Money;

import java.util.*;
import java.util.stream.Collectors;

import static lotto.constant.ErrorMessage.NUMBER_FORMAT_ERROR_MESSAGE;
import static lotto.domain.constant.LottoConstant.*;

public class LottoServiceImpl implements LottoService {

    public Lotto formatWinningLotto(String winningLottoNumber) {
        try {
            List<Integer> lottoNumbers = Arrays.stream(winningLottoNumber.split(LOTTO_NUMBER_SEPARATOR))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            return new Lotto(lottoNumbers);

        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(NUMBER_FORMAT_ERROR_MESSAGE);
        }
    }

    public List<Rank> getLottoWinnings(List<Lotto> purchaseLottos, Lotto winningLotto, LottoNumber bonusNumber) {
        List<Rank> winnings = new ArrayList<>();
        List<LottoNumber> winningNumbers = winningLotto.getNumbers();

        for (Lotto purchaseLotto : purchaseLottos) {
            Optional<Rank> lottoWinning = getLottoWinning(bonusNumber, winningNumbers, purchaseLotto);
            if (lottoWinning.isEmpty()) {
                continue;
            }
            winnings.add(lottoWinning.get());
        }

        return winnings;
    }

    public Map<Rank, Integer> getWinnings(List<Rank> ranks) {
        Map<Rank, Integer> winnings = createWinningMap();
        applyWinningData(ranks, winnings);

        return winnings;
    }

    public float getProfitPercentage(Map<Rank, Integer> winnings, Money money) {
        return calculateProfitPercentage(money, calculateProfit(winnings));
    }


    private Optional<Rank> getLottoWinning(LottoNumber bonusNumber, List<LottoNumber> winningNumbers, Lotto purchaseLotto) {
        int matchCount;
        List<LottoNumber> purchaseLottoNumbers = purchaseLotto.getNumbers();

        matchCount = getWinningCount(winningNumbers, purchaseLottoNumbers);
        if (matchCount < FIFTH_PRIZE_COUNT) {
            return Optional.empty();
        }
        return Optional.of(getLottoWinning(matchCount, purchaseLottoNumbers.contains(bonusNumber)));
    }

    private int getWinningCount(List<LottoNumber> winningNumbers, List<LottoNumber> purchaseLottoNumbers) {
        int winningCount = 0;

        for (LottoNumber lottoNumber : purchaseLottoNumbers) {
            if (winningNumbers.contains(lottoNumber)) {
                winningCount += 1;
            }
        }
        return winningCount;
    }

    private Rank getLottoWinning(int matchCount, boolean isBonusNumberMatched) {
        return Rank.findLottoWinning(matchCount, isBonusNumberMatched);
    }


    private void applyWinningData(List<Rank> ranks, Map<Rank, Integer> winnings) {
        for (Rank rank : ranks) {
            winnings.replace(rank, winnings.get(rank)+1);
        }
    }

    private Map<Rank, Integer> createWinningMap() {
        Map<Rank, Integer> winnings = new LinkedHashMap<>();
        for (Rank rank : Rank.values()) {
            winnings.put(rank, 0);
        }
        return winnings;
    }

    private float calculateProfit(Map<Rank, Integer> winnings) {
        float profit = 0.0f;

        for (Rank rank : Rank.values()) {
            Integer winningCount = winnings.get(rank);
            profit += rank.getReward() * winningCount;
        }
        return profit;
    }

    private float calculateProfitPercentage(Money money, float profitPercentage) {
        return (profitPercentage / money.getAmount()) * 100;
    }


}
