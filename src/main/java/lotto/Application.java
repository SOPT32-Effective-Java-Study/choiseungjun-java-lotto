package lotto;

import lotto.controller.LottoController;
import lotto.view.InputView;
import lotto.view.OutputView;

public class Application {
    public static void main(String[] args) {
        LottoController lottoController = LottoController
                .of(InputView.newInstance(), OutputView.newInstance());

        lottoController.run();
    }
}
