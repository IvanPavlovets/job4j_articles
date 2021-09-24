package ru.job4j.articles.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.articles.model.Article;
import ru.job4j.articles.model.Word;
import ru.job4j.articles.service.generator.ArticleGenerator;
import ru.job4j.articles.store.Store;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleArticleService implements ArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleArticleService.class.getSimpleName());

    private final ArticleGenerator articleGenerator;

    public SimpleArticleService(ArticleGenerator articleGenerator) {
        this.articleGenerator = articleGenerator;
    }

    /**
     * В цикле идет сохранение сгенирированых статей в хранилище.
     *
     * Расшифровка предыдущего варианта.
     *
     * 1) Передаем входящие парметры лябды:
     * var articles = IntStream.iterate(0, i -> i < count, i -> i + 1)
     * 2) Сообщение создваемой статьи:
     * .peek(i -> LOGGER.info("Сгенерирована статья № {}", i))
     * 3) ко всем элементам stream сгенирировать статью:
     * .mapToObj((x) -> articleGenerator.generate(words))
     * 4) Результат потока скомпоновать в List:
     * .collect(Collectors.toList());
     * 5) Cохранить все члены списка в articleStore:
     * articles.forEach(articleStore::save);
     * @param wordStore
     * @param count
     * @param articleStore
     */
    @Override
    public void generate(Store<Word> wordStore, int count, Store<Article> articleStore) {
        LOGGER.info("Генерацация статей в количестве {}", count);
        var words = wordStore.findAll();
        for (int i = 0; i < count; i++) {
            articleStore.save(articleGenerator.generate(words));
            LOGGER.info("Сгенерирована статья № {}", i);
        }
    }
}
