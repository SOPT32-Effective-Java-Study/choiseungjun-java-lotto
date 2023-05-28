package lotto.domain;

import java.util.ArrayList;
import java.util.List;

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
        List<LottoNumber> lottoNumbers = new ArrayList<>();

        validateNumberSize(numbers);

        generateLottoNumbers(numbers, lottoNumbers);
        return lottoNumbers;
    }

    private void generateLottoNumbers(List<Integer> numbers, List<LottoNumber> lottoNumbers) {
        for (Integer number : numbers) {
            lottoNumbers.add(LottoNumber.from(number));
        }
    }

    private void validateNumberSize(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException();
        }
    }
}
