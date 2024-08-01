package lotto.config;

import lotto.controller.LottoController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AppConfigTest {

    @Test
    @DisplayName("싱글톤 테스트")
    void isSingleton() {
        AppConfig appConfig = new AppConfig();
        LottoController controller1 = appConfig.getLottoController();
        LottoController controller2 = appConfig.getLottoController();

        Assertions.assertThat(controller1).isSameAs(controller2);
    }

}