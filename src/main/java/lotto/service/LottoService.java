package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.LottoNumber;
import lotto.domain.LottoWinning;

import java.util.*;
import java.util.stream.Collectors;

import static lotto.constant.ErrorMessage.NUMBER_FORMAT_ERROR_MESSAGE;
import static lotto.domain.constant.DomainConstant.*;

public class LottoService {

    public List<Integer> getWinningLottoNumbers(String winningLottoNumber) {
        try {
            return Arrays.stream(winningLottoNumber.split(LOTTO_NUMBER_SEPARATOR))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(NUMBER_FORMAT_ERROR_MESSAGE);
        }
    }

    public List<LottoWinning> getLottoWinnings(List<Lotto> purchaseLottos, Lotto winningLotto, LottoNumber bonusNumber) {
        List<LottoWinning> winnings = new ArrayList<>();
        List<LottoNumber> winningNumbers = winningLotto.getNumbers();

        for (Lotto purchaseLotto : purchaseLottos) {
            Optional<LottoWinning> lottoWinning = getLottoWinning(bonusNumber, winningNumbers, purchaseLotto);
            if (lottoWinning.isEmpty()) {
                continue;
            }
            winnings.add(lottoWinning.get());
        }

        return winnings;
    }

    private Optional<LottoWinning> getLottoWinning(LottoNumber bonusNumber, List<LottoNumber> winningNumbers, Lotto purchaseLotto) {
        int winningCount;
        List<LottoNumber> purchaseLottoNumbers = purchaseLotto.getNumbers();

        winningCount = getWinningCount(winningNumbers, purchaseLottoNumbers);
        if (winningCount < FIFTH_PRIZE_COUNT) {
            return Optional.empty();
        }
        return Optional.of(getLottoWinning(winningCount, purchaseLottoNumbers.contains(bonusNumber)));
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

    private LottoWinning getLottoWinning(int winningCount, boolean isBonusNumberCollected) {
        if (winningCount == FIFTH_PRIZE_COUNT) {
            return LottoWinning.FIFTH_PRIZE;

        } else if (winningCount == FOURTH_PRIZE_COUNT) {
            return LottoWinning.FOURTH_PRIZE;

        } else if (winningCount == THIRD_PRIZE_COUNT) {
            if (isBonusNumberCollected) {
                return LottoWinning.SECOND_PRIZE;
            }
            return LottoWinning.THIRD_PRIZE;

        }
        return LottoWinning.FIRST_PRIZE;
    }

    public Map<LottoWinning, Integer> getWinnings(List<LottoWinning> lottoWinnings) {
        Map<LottoWinning, Integer> winnings = createWinningMap();
        applyWinningData(lottoWinnings, winnings);

        return winnings;
    }

    private void applyWinningData(List<LottoWinning> lottoWinnings, Map<LottoWinning, Integer> winnings) {
        for (LottoWinning lottoWinning : lottoWinnings) {
            winnings.replace(lottoWinning, winnings.get(lottoWinning)+1);
        }
    }

    private Map<LottoWinning, Integer> createWinningMap() {
        Map<LottoWinning, Integer> winnings = new LinkedHashMap<>();
        for (LottoWinning lottoWinning : LottoWinning.values()) {
            winnings.put(lottoWinning, 0);
        }
        return winnings;
    }


}
