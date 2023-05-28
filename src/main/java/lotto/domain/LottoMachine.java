package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

import static lotto.domain.constant.DomainConstant.*;

public class LottoMachine {

    public List<Lotto> generate(Money money) {
        int purchaseCount = money.getAmount() / MONEY_AMOUNT_UNIT;

        return generateLottos(purchaseCount);
    }

    private List<Lotto> generateLottos(int purchaseCount) {
        List<Lotto> lottos = new ArrayList<>();

        for(int i = 0; i < purchaseCount; i++) {
            List<Integer> lottoNumbers = Randoms.pickUniqueNumbersInRange(START_LOTTO_NUMBER, END_LOTTO_NUMBER, LOTTO_NUMBER_COUNT);
            lottos.add(new Lotto(lottoNumbers));
        }
        return lottos;
    }
}
