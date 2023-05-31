package lotto.controller;

import lotto.domain.Lotto;
import lotto.domain.LottoMachine;
import lotto.domain.Money;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static lotto.constant.ErrorMessage.NUMBER_FORMAT_ERROR_MESSAGE;

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

        inputWinningLotto();
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

    private List<Integer> getWinningLottoNumbers(String winningLottoNumber) {
        try {
            return Arrays.stream(winningLottoNumber.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(NUMBER_FORMAT_ERROR_MESSAGE);
        }
    }
}
