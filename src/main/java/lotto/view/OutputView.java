package lotto.view;

import static lotto.constant.ConsoleMessage.*;

public class OutputView {

    private OutputView() {
    }

    public static OutputView newInstance() {
        return new OutputView();
    }

    public void inputPurchaseAmountMessage() {
        println(INPUT_PURCHASE_AMOUNT);
    }

    private <T> void println(T message) {
        System.out.println(message);
    }

    public void printError(Exception error) {
        println(error.getMessage());
    }

}
