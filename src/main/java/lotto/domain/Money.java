package lotto.domain;


import static lotto.constant.ErrorMessage.MONEY_AMOUNT_UNIT_ERROR_MESSAGE;
import static lotto.constant.ErrorMessage.NUMBER_FORMAT_ERROR_MESSAGE;
import static lotto.domain.constant.DomainConstant.MONEY_AMOUNT_UNIT;

public class Money {

    private final int amount;

    private Money(String amount) {

        this.amount = validate(amount);
    }

    public static Money from(String amount) {
        return new Money(amount);
    }

    public int getAmount() {
        return amount;
    }


    private int validate(String inputAmount) {
        int amount = validateFormat(inputAmount);
        validateAmountValue(amount);

        return amount;
    }

    private int validateFormat(String amount) {
        try {
            int intAmount = Integer.parseInt(amount);
            return intAmount;

        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(NUMBER_FORMAT_ERROR_MESSAGE);
        }
    }

    private void validateAmountValue(int amount) {
        if ((amount % MONEY_AMOUNT_UNIT) != 0) {
            throw new IllegalArgumentException(MONEY_AMOUNT_UNIT_ERROR_MESSAGE);
        }
        if (amount == 0) {
            throw new IllegalArgumentException(MONEY_AMOUNT_UNIT_ERROR_MESSAGE);
        }
    }
}
