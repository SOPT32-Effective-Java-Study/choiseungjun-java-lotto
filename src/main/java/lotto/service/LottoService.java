package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.LottoNumber;
import lotto.domain.LottoWinning;
import lotto.domain.Money;

import java.util.List;
import java.util.Map;

public interface LottoService {
    List<Integer> getWinningLottoNumbers(String winningLottoNumber);
    List<LottoWinning> getLottoWinnings(List<Lotto> purchaseLottos, Lotto winningLotto, LottoNumber bonusNumber);
    Map<LottoWinning, Integer> getWinnings(List<LottoWinning> lottoWinnings);
    float getProfitPercentage(Map<LottoWinning, Integer> winnings, Money money);

}
