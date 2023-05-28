package lotto.domain;

import static lotto.constant.ErrorMessage.LOTTO_NUMBER_RANGE_ERROR_MESSAGE;
import static lotto.domain.constant.DomainConstant.END_LOTTO_NUMBER;
import static lotto.domain.constant.DomainConstant.START_LOTTO_NUMBER;

public class LottoNumber {
    private int number;

    private LottoNumber(int number) {
        validateNumberRange(number);
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public static LottoNumber from(int number) {
        return new LottoNumber(number);
    }


    private void validateNumberRange(int number) {
        if (number < START_LOTTO_NUMBER || number > END_LOTTO_NUMBER) {
            throw new IllegalArgumentException(LOTTO_NUMBER_RANGE_ERROR_MESSAGE);
        }
    }
}
