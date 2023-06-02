package lotto.controller;

import lotto.domain.*;
import lotto.service.LottoService;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;
import java.util.Map;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;
    private final LottoService lottoService;

    public LottoController(InputView inputView, OutputView outputView, LottoService lottoService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoService = lottoService;
    }


    public void run() {
        Money money = inputPurchaseAmount();
        List<Lotto> purchaseLottos = purchaseLotto(money, new LottoMachine());

        Lotto winningLotto = inputWinningLotto();
        LottoNumber bonusNumber = inputBonusNumber(winningLotto);

        List<LottoWinning> lottoWinnings = getLottoWinnings(purchaseLottos, winningLotto, bonusNumber);
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
            List<Integer> winningLottoNumbers = lottoService.getWinningLottoNumbers(winningLottoNumber);

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

    private List<LottoWinning> getLottoWinnings(List<Lotto> purchaseLottos, Lotto winningLotto, LottoNumber bonusNumber) {
        return lottoService.getLottoWinnings(purchaseLottos, winningLotto, bonusNumber);
    }

    private void printWinningStatistics(List<LottoWinning> lottoWinnings, Money money) {
        Map<LottoWinning, Integer> winnings = lottoService.getWinnings(lottoWinnings);

        outputView.printWinningStatisticsMessage(winnings, getProfitPercentage(winnings, money));

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
