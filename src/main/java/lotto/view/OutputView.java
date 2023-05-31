package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoNumber;

import java.util.List;
import java.util.StringJoiner;

import static lotto.constant.ConsoleMessage.*;
import static lotto.constant.ErrorMessage.ERROR_PREFIX;
import static lotto.domain.constant.DomainConstant.*;

public class OutputView {

    private OutputView() {
    }

    public static OutputView newInstance() {
        return new OutputView();
    }

    public void inputPurchaseAmountMessage() {
        println(INPUT_PURCHASE_AMOUNT_MESSAGE);
    }

    private <T> void println(T message) {
        System.out.println(message);
    }

    private <P1> void printf(String message, P1 param1) {
        System.out.printf(message, param1);
        printEnter();
    }

    private void printEnter() {
        System.out.println();
    }

    public void printError(Exception error) {
        println(ERROR_PREFIX + error.getMessage());
    }

    public void printPurchaseLottoList(List<Lotto> lottos) {
        printEnter();
        printf("%d개를 구매했습니다.", lottos.size());
        printLottos(lottos);
    }

    private void printLottos(List<Lotto> lottos) {
        StringJoiner joiner;

        for (Lotto lotto : lottos) {
            joiner = new StringJoiner(LOTTO_NUMBER_DELIMITER, LOTTO_NUMBER_PREFIX, LOTTO_NUMBER_SUFFIX);
            printLotto(joiner, lotto.getNumbers());
        }
    }

    private void printLotto(StringJoiner joiner, List<LottoNumber> numbers) {
        for (LottoNumber number : numbers) {
            joiner.add(number.getNumber().toString());
        }
        println(joiner.toString());
    }

    public void printInputWinningLottoMessage() {
        printEnter();
        println(INPUT_WINNING_LOTTO_NUMBER_MESSAGE);
    }

    public void printInputBonusNumberMessage() {
        printEnter();
        println(INPUT_BONUS_NUMBER_MESSAGE);
    }
}
