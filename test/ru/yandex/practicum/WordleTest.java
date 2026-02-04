package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

class WordleTest {

    private static WordleDictionaryLoader wordleDictionaryLoader;
    private static WordleDictionary wordleDictionary;
    private static WordleGame wordleGame;

    @BeforeAll
    static void CallingEveryone() {
        wordleDictionaryLoader = new WordleDictionaryLoader();
        wordleDictionary = new WordleDictionary();
        wordleGame = new WordleGame();
    }

    @Test
    void checkingThatTheListIsNotEmpty() throws IOException {
       assertNotNull(wordleDictionaryLoader.uploadingWords());
        //Проверка что список не пустой
    }

    @Test
    void AllTheWordsInTheListAreNoMoreAndNoLessThan5Letters() {
        boolean wordSize = true;
        for (String str: wordleDictionary.getWordsSort()){
            if (str.length() != 5){
                wordSize = false;
            }
        }
        assertTrue(wordSize);
        //проверка что все слова в списке будут равны 5 буквам
    }


    @Test
    void methodGuessWordWith5Letters() {
        String randomWorg = wordleGame.getRandomWord(wordleDictionary.getWordsSort());
        assertEquals(5, randomWorg.length());
        //Проверка что случайное слово состоит из 5 букв
    }

    @Test
    void ComparingWordsWithASuccessfulResult(){
       String result = wordleDictionary.wordСomparison("привет" , "привет");
        assertEquals("++++++" ,result);
        //Проверка на полное совпадения букв
    }

    @Test
    void comparisonWith1Hit(){
        String result = wordleDictionary.wordСomparison("прив" , "пока");
        assertEquals("+---", result);
        //Проверка на одно совпадения букв
    }
    @Test
    void comparisonWith1HitIsOutOfPlace(){
        String result = wordleDictionary.wordСomparison("аабаа" , "вбввв");
        assertEquals("--^--", result);
        //Проверка на одно совпадения букв но не том месте
    }



    @Test
    void checkWith0Hit(){
        String result = wordleDictionary.wordСomparison("ааааа" , "ддддд");
        assertEquals("-----", result);
        //Проверка на полное не совпадения букв
    }

    @Test
    void negativeWordLengthCheck(){
        String result = wordleDictionary.wordСomparison("длиное" , "ещёдлинее");
        assertEquals("" , result);
        //Проверка что метод выбросить пустоту, из-за различия слов в длине
    }

    @Test
    void CheckingThatTheMethodWillShowThatThereIsOnlyOneMatchInTheWords(){
       int result =  wordleDictionary.numberOfMatches("прив" , "пока");
       assertEquals(1, result);
       //Проверка что метод покажет что только одно совпадение есть в словах
    }

    @Test
    void CheckingThatTheMethodWillShowThatThereIsOnlyNullMatchInTheWords(){
        int result =  wordleDictionary.numberOfMatches("fff" , "bbb");
        assertEquals(0, result);
        //Проверка что метод покажет что только одно совпадение есть в словах
    }

    @Test
    void TheWordIsInTheList(){
       assertTrue(wordleDictionary.availableWords("парок"));
       //Проверка что слово есть в списке
    }

    @Test
    void ThisWordIsNotIncludedInTheList(){
        assertFalse(wordleDictionary.availableWords("нетус"));
        //Проверка что слово есть в списке
    }

    @Test
    void TheListWillGiveNullBecauseTheListOfAnswersIsEmpty (){
        List<String> listOFfWords = new ArrayList<>();
        listOFfWords.add("cлово");
        LinkedHashMap<String, Integer> emptyResponseCounter = new LinkedHashMap<>();
        assertNull(wordleGame.gameHint(listOFfWords, emptyResponseCounter, "парок"));
        //Список даст null, потому что список ответов пуст
    }

    @Test
    void TheGameHintWillGiveYouAааа (){
        List<String> listOFfWords = new ArrayList<>();
        listOFfWords.add("ааааб");
        listOFfWords.add("аааав");
        LinkedHashMap<String, Integer> ResponseCounter = new LinkedHashMap<>();
        ResponseCounter.put("ааааб", 4);
        String result = wordleGame.gameHint(listOFfWords, ResponseCounter, "ааааа");
        assertEquals("аааав", result);
        //Подсказка сработает
    }


    @Test
    void testStartGame(){
        wordleGame.processAnswer("парок");
        wordleGame.starGame();
        wordleGame.processAnswer("парок");
    }

}
