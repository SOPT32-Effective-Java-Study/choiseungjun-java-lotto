package lotto.controller;

import lotto.domain.Lotto;
import lotto.domain.LottoMachine;
import lotto.domain.Money;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;

    private LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public static LottoController of(InputView inputView, OutputView outputView) {
        return new LottoController(inputView, outputView);
    }

    public void run() {
        LottoMachine lottoMachine = new LottoMachine();

        Money money = inputPurchaseAmount();
        List<Lotto> lottos = purchaseLotto(money, lottoMachine);
        outputView.printPurchaseLottoList(lottos);
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
            return lottoMachine.generate(money);

        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            return purchaseLotto(money, lottoMachine);
        }
    }
}
