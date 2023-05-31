package lotto.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lotto.constant.ErrorMessage.LOTTO_NUMBER_DUPLICATE_ERROR_MESSAGE;
import static lotto.constant.ErrorMessage.LOTTO_NUMBER_SIZE_ERROR_MESSAGE;
import static lotto.domain.constant.DomainConstant.LOTTO_NUMBER_COUNT;

public class Lotto {
    private final List<LottoNumber> numbers;

    public Lotto(List<Integer> numbers) {
        List<LottoNumber> lottoNumbers = validate(numbers);
        this.numbers = lottoNumbers;
    }

    public List<LottoNumber> getNumbers() {
        return numbers;
    }

    private List<LottoNumber> validate(List<Integer> numbers) {
        validateNumberSize(numbers);
        validateDuplicate(numbers);
        List<LottoNumber> lottoNumbers = generateLottoNumbers(numbers);

        return lottoNumbers;
    }

    private List<LottoNumber> generateLottoNumbers(List<Integer> numbers) {
        List<LottoNumber> lottoNumbers = new ArrayList<>();
        for (Integer number : numbers) {
            lottoNumbers.add(LottoNumber.from(number));
        }

        return lottoNumbers;
    }

    private void validateNumberSize(List<Integer> numbers) {
        if (numbers.size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException(LOTTO_NUMBER_SIZE_ERROR_MESSAGE);
        }
    }

    private void validateDuplicate(List<Integer> numbers) {
        Set<Integer> removeDuplicatedNumbers = new HashSet<>(numbers);
        if(removeDuplicatedNumbers.size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException(LOTTO_NUMBER_DUPLICATE_ERROR_MESSAGE);
        }
    }
}
