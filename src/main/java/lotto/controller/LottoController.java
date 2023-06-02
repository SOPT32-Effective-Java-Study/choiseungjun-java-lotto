package lotto.controller;

import lotto.domain.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.*;
import java.util.stream.Collectors;

import static lotto.constant.ErrorMessage.NUMBER_FORMAT_ERROR_MESSAGE;
import static lotto.domain.constant.DomainConstant.*;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }


    public void run() {
        LottoMachine lottoMachine = new LottoMachine();

        Money money = inputPurchaseAmount();
        List<Lotto> lottos = purchaseLotto(money, lottoMachine);

        Lotto winningLotto = inputWinningLotto();
        LottoNumber bonusNumber = inputBonusNumber(winningLotto);

        List<LottoWinning> lottoWinnings = getLottoWinnings(lottos, winningLotto, bonusNumber);
        printWinningStatistics(lottoWinnings, money);
    }

    private Money inputPurchaseAmount() {
        try {
            return Money.from(inputView.readPurchaseAmount());

        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            return inputPurchaseAmount();
        }
    }

    private List<Lotto> purchaseLotto(Money money, LottoMachine lottoMachine) {
        try {
            List<Lotto> lottos = lottoMachine.generate(money);
            outputView.printPurchaseLottoList(lottos);

            return lottos;

        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            return purchaseLotto(money, lottoMachine);
        }
    }

    private Lotto inputWinningLotto() {
        try {
            String winningLottoNumber = inputView.readWinningLottoNumber();
            List<Integer> winningLottoNumbers = getWinningLottoNumbers(winningLottoNumber);

            return new Lotto((winningLottoNumbers));

        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            return inputWinningLotto();
        }
    }

    private LottoNumber inputBonusNumber(Lotto winningLotto) {
        try {
            LottoNumber bonusNumber = LottoNumber.bonusNumberOf(inputView.readBonusNumber(), winningLotto);
            return bonusNumber;

        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            return inputBonusNumber(winningLotto);
        }
    }


    private List<Integer> getWinningLottoNumbers(String winningLottoNumber) {
        try {
            return Arrays.stream(winningLottoNumber.split(LOTTO_NUMBER_SEPARATOR))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(NUMBER_FORMAT_ERROR_MESSAGE);
        }
    }

    private List<LottoWinning> getLottoWinnings(List<Lotto> purchaseLottos, Lotto winningLotto, LottoNumber bonusNumber) {
        List<LottoWinning> winnings = new ArrayList<>();
        List<LottoNumber> winningNumbers = winningLotto.getNumbers();

        for (Lotto purchaseLotto : purchaseLottos) {
            Optional<LottoWinning> lottoWinning = getLottoWinning(bonusNumber, winningNumbers, purchaseLotto);
            if(lottoWinning.isEmpty()) {
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
        if(winningCount < FIFTH_PRIZE_COUNT) {
            return Optional.empty();
        }
        return Optional.of(getLottoWinning(winningCount, purchaseLottoNumbers.contains(bonusNumber)));
    }

    private int getWinningCount(List<LottoNumber> winningNumbers, List<LottoNumber> purchaseLottoNumbers) {
        int winningCount = 0;

        for (LottoNumber lottoNumber : purchaseLottoNumbers) {
            if(winningNumbers.contains(lottoNumber)) {
                winningCount +=1;
            }
        }
        return winningCount;
    }

    private LottoWinning getLottoWinning(int winningCount, boolean isBonusNumberCollected) {
        if (winningCount == FIFTH_PRIZE_COUNT) {
            return LottoWinning.FIFTH_PRIZE;

        } else if (winningCount == FOURTH_PRIZE_COUNT) {
            return LottoWinning.FOURTH_PRIZE;

        } else if(winningCount == THIRD_PRIZE_COUNT) {
            if (isBonusNumberCollected) {
                return LottoWinning.SECOND_PRIZE;
            }
            return LottoWinning.THIRD_PRIZE;

        }
        return LottoWinning.FIRST_PRIZE;
    }

    private void printWinningStatistics(List<LottoWinning> lottoWinnings, Money money) {
        Map<LottoWinning, Integer> winnings = createWinningMap();
        applyWinningData(lottoWinnings, winnings);

        outputView.printWinningStatisticsMessage(winnings, getProfitPercentage(winnings, money));

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

    private float getProfitPercentage(Map<LottoWinning, Integer> winnings, Money money) {
        return calculateProfitPercentage(money, calculateProfit(winnings));
    }

    private float calculateProfit(Map<LottoWinning, Integer> winnings) {
        float profit = 0.0f;

        for (LottoWinning lottoWinning : LottoWinning.values()) {
            Integer winningCount = winnings.get(lottoWinning);
            profit += lottoWinning.getReward() * winningCount;
        }
        return profit;
    }

    private float calculateProfitPercentage(Money money, float profitPercentage) {
        return (profitPercentage / money.getAmount()) * 100;
    }
}
