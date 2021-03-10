package pl.kubaretip.cpo.api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.kubaretip.cpo.api.util.Translator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@TestConfiguration
public class TranslatorTestConfig {


    @Bean
    public Translator translator() {

        var translator = mock(Translator.class);

        given(translator.translate(any())).willReturn("fake_message");
        given(translator.translate(any(), any())).willReturn("fake_massage");

        return translator;
    }


}
