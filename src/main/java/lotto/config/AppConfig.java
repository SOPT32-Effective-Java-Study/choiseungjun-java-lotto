package lotto.config;

import lotto.controller.LottoController;
import lotto.service.LottoService;
import lotto.service.LottoServiceImpl;
import lotto.view.InputView;
import lotto.view.OutputView;


public class AppConfig {
    private static final InputView inputView = inputView();
    private static final OutputView outputView = outputView();
    private static final LottoController lottoController = lottoController();
    private static final LottoService lottoService = lottoService();

    public AppConfig() {
    }

    private static InputView inputView() {
        return new InputView(outputView());
    }

    private static OutputView outputView() {
        return new OutputView();
    }

    private static LottoController lottoController() {
        return new LottoController(inputView(), outputView(), lottoService());
    }

    private static LottoService lottoService() {
        return new LottoServiceImpl();
    }


    public LottoController getLottoController() {
        return lottoController;
    }
}
