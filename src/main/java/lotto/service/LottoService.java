package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.LottoNumber;
import lotto.domain.Rank;
import lotto.domain.Money;

import java.util.List;
import java.util.Map;

public interface LottoService {
    Lotto formatWinningLotto(String winningLottoNumber);
    List<Rank> getLottoWinnings(List<Lotto> purchaseLottos, Lotto winningLotto, LottoNumber bonusNumber);
    Map<Rank, Integer> getResult(List<Rank> ranks);
    float getProfitPercentage(Map<Rank, Integer> winnings, Money money);

}
