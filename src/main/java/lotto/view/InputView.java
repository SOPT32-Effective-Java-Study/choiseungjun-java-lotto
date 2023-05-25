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
        String amount = read();

        return amount;
    }

    private String read() {
        return Console.readLine();
    }
}
