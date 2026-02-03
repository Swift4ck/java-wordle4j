package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    void methodGuessWordWith5Letters() {
        String randomWorg = wordleGame.getRandomWord(wordleDictionary.getWordsSort());
        assertEquals(5, randomWorg.length());
        //Проверка что случайное слово состоит из 5 букв
    }

    @Test
    void AllTheWordsInTheListAreNoMoreAndNoLessThan5Letters() {
        wordleDictionary.uploadingWordsSort();
    }

}
