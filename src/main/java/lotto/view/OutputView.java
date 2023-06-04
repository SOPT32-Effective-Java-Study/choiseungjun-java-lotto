package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoNumber;
import lotto.domain.LottoWinning;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static lotto.constant.ConsoleMessage.*;
import static lotto.constant.ErrorMessage.ERROR_PREFIX;
import static lotto.domain.constant.LottoConstant.*;

public class OutputView {

    public void inputPurchaseAmountMessage() {
        println(INPUT_PURCHASE_AMOUNT_MESSAGE);
    }

    public void printPurchaseLottoList(List<Lotto> lottos) {
        printEnter();
        printf(PURCHASE_LOTTO_COUNT_INFO_MESSAGE, lottos.size());
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

    public void printWinningStatisticsMessage(Map<LottoWinning, Integer> winnings, float profitPercentage) {
        printEnter();
        println(WINNING_STATISTICS_MESSAGE);

        printLottoWinnings(winnings);
        printProfitPercentage(profitPercentage);
    }

    private void printProfitPercentage(float profitPercentage) {
        printf(TOTAL_PROFIT_PERCENTAGE_MESSAGE, String.format("%.1f", profitPercentage));
        printEnter();
    }

    private void printLottoWinnings(Map<LottoWinning, Integer> winnings) {
        DecimalFormat winningRewardFormat = new DecimalFormat(WINNING_REWARD_FORMAT);

        for (LottoWinning lottoWinning : LottoWinning.values()) {
            printLottoWinning(winningRewardFormat, lottoWinning, winnings.get(lottoWinning));
        }
    }

    private void printLottoWinning(DecimalFormat winningRewardFormat, LottoWinning lottoWinning, int winningCount) {
        String reward = winningRewardFormat.format(lottoWinning.getReward());

        if (lottoWinning == LottoWinning.SECOND_PRIZE) {
            printf(LOTTO_SECOND_PRIZE_COLLECT_INFO_MESSAGE, lottoWinning.getMatchingNumberCount(), reward, winningCount);
            return;
        }
        printf(LOTTO_COLLECT_INFO_MESSAGE, lottoWinning.getMatchingNumberCount(), reward, winningCount);
    }


    private <T> void println(T message) {
        System.out.println(message);
    }

    private <P1> void printf(String message, P1 param1) {
        System.out.printf(message, param1);
        printEnter();
    }

    private <P1, P2, P3> void printf(String message, P1 param1, P2 param2, P3 param3) {
        System.out.printf(message, param1, param2, param3);
        printEnter();
    }

    private void printEnter() {
        System.out.println();
    }

    public void printError(Exception error) {
        println(ERROR_PREFIX + error.getMessage());
    }
}
