package lotto.config;

import lotto.controller.LottoController;
import lotto.view.InputView;
import lotto.view.OutputView;

public class AppConfig {

    public static InputView inputView() {
        return new InputView(outputView());
    }

    public static OutputView outputView() {
        return new OutputView();
    }

    public static LottoController lottoController() {
        return new LottoController(inputView(), outputView());
    }
}
