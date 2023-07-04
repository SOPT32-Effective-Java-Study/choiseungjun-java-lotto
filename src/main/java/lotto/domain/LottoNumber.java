package lotto.domain;

import java.util.List;

import static lotto.constant.ErrorMessage.BONUS_NUMBER_DUPLICATE_ERROR_MESSAGE;
import static lotto.constant.ErrorMessage.LOTTO_NUMBER_RANGE_ERROR_MESSAGE;
import static lotto.domain.constant.LottoConstant.END_LOTTO_NUMBER;
import static lotto.domain.constant.LottoConstant.START_LOTTO_NUMBER;

public class LottoNumber{
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

    public static LottoNumber bonusNumberOf(int bonusNumber, List<LottoNumber> winningLottoNumbers) {
        validateBonusNumberDuplicate(bonusNumber, winningLottoNumbers);
        return new LottoNumber(bonusNumber);
    }

    private static void validateBonusNumberDuplicate(int bonusNumber, List<LottoNumber> winningLottoNumbers) {
        for (LottoNumber lottoNumber : winningLottoNumbers) {
            if (lottoNumber.number == bonusNumber) {
                throw new IllegalArgumentException(BONUS_NUMBER_DUPLICATE_ERROR_MESSAGE);
            }
        }
    }


    private void validateNumberRange(int number) {
        if (number < START_LOTTO_NUMBER || number > END_LOTTO_NUMBER) {
            throw new IllegalArgumentException(LOTTO_NUMBER_RANGE_ERROR_MESSAGE);
        }
    }


    @Override
    public boolean equals(Object obj) {
        LottoNumber lottoNumber = (LottoNumber) obj;

        if(lottoNumber.number == this.number){
            return true;
        }
        return false;
    }
}
