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
    void checkingThatTheMethodWillShowThatThereIsOnlyOneMatchInTheWords(){
       int result =  wordleDictionary.numberOfMatches("прив" , "пока");
       assertEquals(1, result);
       //Проверка что метод покажет что только одно совпадение есть в словах
    }

    @Test
    void checkingThatTheMethodWillShowThatThereIsOnlyNullMatchInTheWords(){
        int result =  wordleDictionary.numberOfMatches("fff" , "bbb");
        assertEquals(0, result);
        //Проверка что метод покажет что только одно совпадение есть в словах
    }

    @Test
    void theWordIsInTheList(){
       assertTrue(wordleDictionary.availableWords("парок"));
       //Проверка что слово есть в списке
    }

    @Test
    void thisWordIsNotIncludedInTheList(){
        assertFalse(wordleDictionary.availableWords("нетус"));
        //Проверка что слово есть в списке
    }

    @Test
    void theListWillGiveNullBecauseTheListOfAnswersIsEmpty (){
        List<String> listOFfWords = new ArrayList<>();
        listOFfWords.add("cлово");
        LinkedHashMap<String, Integer> emptyResponseCounter = new LinkedHashMap<>();
        assertNull(wordleGame.gameHint(listOFfWords, emptyResponseCounter, "парок"));
        //Список даст null, потому что список ответов пуст
    }

    @Test
    void theGameHintWillGiveYouAааа (){
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
    void thePromptWillNotWorkAndWillWrite (){
        List<String> listOFfWords = new ArrayList<>();
        listOFfWords.add("ааааб");
        LinkedHashMap<String, Integer> ResponseCounter = new LinkedHashMap<>();
        ResponseCounter.put("ааааб", 4);
        String result = wordleGame.gameHint(listOFfWords, ResponseCounter, "ааааа");
        assertNull(result);
        //Подсказка не сработает и напишет что "Не смогли найти подсказку
    }


    @Test
    void theWordWillNotPassVerification(){
       String check = wordleGame.checkingTheWord("мало" , "много");
       assertEquals("Введите слово из 5 букв, пожалуйста", check);
        //Это слово не будет принято и будет просить 5 букв
    }

    @Test
    void itWillIndicateThatThereIsNoSuchWordInTheList(){
        String check = wordleGame.checkingTheWord("fffff", "brrrr");
        assertEquals("Такого слова нету в списке загаданных слов, повторите попытку",check );
        // Выдаст что Такого слова нету в списке
    }

    @Test
    void gameWinner(){
        String check = wordleGame.checkingTheWord("парок" , "парок");
        assertEquals("Поздравляю, вы правильно угадали слово!!",check );
        //Выдаст что слово угадали
    }

    @Test
    void gameOver(){
        wordleGame.setSteps(5);
        String check = wordleGame.checkingTheWord("слово" , "друго");
        assertEquals("К сожалению ваши попытки закончились, попробуйте в другой раз",check );
        wordleGame.setSteps(0);
    }


}
