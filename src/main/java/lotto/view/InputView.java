package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private final OutputView outputView = OutputView.newInstance();

    private InputView() {
    }

    public static InputView newInstance() {
        return new InputView();
    }


    public String readPurchaseAmount() {
        outputView.inputPurchaseAmountMessage();
        return read();
    }

    public String readWinningLottoNumber() {
        outputView.printInputWinningLottoMessage();
        return read();
    }


    private String read() {
        return Console.readLine();
    }
}
