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

        List<Rank> ranks = getLottoWinnings(purchaseLottos, winningLotto, bonusNumber);
        printWinningStatistics(ranks, money);
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

    private List<Rank> getLottoWinnings(List<Lotto> purchaseLottos, Lotto winningLotto, LottoNumber bonusNumber) {
        return lottoService.getLottoWinnings(purchaseLottos, winningLotto, bonusNumber);
    }

    private void printWinningStatistics(List<Rank> ranks, Money money) {
        Map<Rank, Integer> winnings = lottoService.getWinnings(ranks);
        float profitPercentage = lottoService.getProfitPercentage(winnings, money);

        outputView.printWinningStatisticsMessage(winnings, profitPercentage);

    }

}
