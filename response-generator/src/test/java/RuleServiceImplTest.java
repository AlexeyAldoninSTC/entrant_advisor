import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.innopolis.project.entity.Condition;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.RulesRepository;
import ru.innopolis.project.service.RuleServiceImpl;

import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes =
        RuleServiceImplTest.RuleServiceImplTestContextConfiguration.class)
public class RuleServiceImplTest {
    private static IgniteCache<String, Rule> igniteCache;
    private final Rule firstRecord = new Rule(84L, "firstRecord", Collections.singleton(new Condition(10L, null, "op", "fe", 90)));
    private final Rule secondRecord = new Rule(100L, "secondRecord", Collections.emptySet());
    private final Rule stubRule = new Rule(-1L, "testNullRecord", Collections.emptySet());

    @MockBean
    RulesRepository rulesRepository;
    @Autowired
    private RuleServiceImpl ruleServiceImpl;

    /**
     * Начальное состояние: в кэше нет записей
     */
    @Before
    public void setup() {
        igniteCache.clear();
    }

    @Test
    public void getByNameFirst() {
        //эмуляция получения записи firstRecord по данному ключу из БД
        when(rulesRepository.findByName("firstRecord")).thenReturn(firstRecord);
        Assert.assertEquals("Первый запрос получает запись из БД", firstRecord, ruleServiceImpl.getByName("firstRecord"));

        //эмуляция получения другой записи (secondRecord) по данному ключу (если запись будет получена из БД, то тест на равность упадет)
        when(rulesRepository.findByName("firstRecord")).thenReturn(secondRecord);
        Assert.assertEquals("Второй запрос получает запись из кэша", firstRecord, ruleServiceImpl.getByName("firstRecord"));

        //эмуляция получения записи secondRecord по данному ключу из БД
        when(rulesRepository.findByName("secondRecord")).thenReturn(secondRecord);
        Assert.assertEquals("Проверка, что метод получения записи из БД работает", secondRecord, ruleServiceImpl.getByName("secondRecord"));

        //эмуляция НЕ получения записи из БД
        when(rulesRepository.findByName("testNullRecord")).thenReturn(null);
        Assert.assertEquals("Случай, когда в БД нет данных", stubRule, ruleServiceImpl.getByName("testNullRecord"));
    }

    @TestConfiguration
    static class RuleServiceImplTestContextConfiguration {

        @Bean
        public RuleServiceImpl ruleServiceImpl(Ignite ignite, RulesRepository rulesRepository) {
            return new RuleServiceImpl(ignite, rulesRepository);
        }

        @Bean
        public Ignite getIgnite() {
            Ignite ignite = Ignition.start();
            igniteCache = ignite.getOrCreateCache("ruleCache");
            return ignite;
        }
    }
}