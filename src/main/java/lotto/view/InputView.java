package lotto.view;

import camp.nextstep.edu.missionutils.Console;

import static lotto.constant.ErrorMessage.NUMBER_FORMAT_ERROR_MESSAGE;

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

    public int readBonusNumber() {
        outputView.printInputBonusNumberMessage();
        return readInt();
    }


    private String read() {
        return Console.readLine();
    }
    private int readInt() {
        try {
            return Integer.parseInt(Console.readLine());

        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(NUMBER_FORMAT_ERROR_MESSAGE);
        }
    }


}
