package ru.yandex.practicum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    private List<String> words;

    public void setWordsSort(List<String> wordsSort) {
        this.wordsSort = wordsSort;
    }

    public List<String> getWordsSort() {
        return wordsSort;
    }

    private PrintWriter log;

    public void setLog(PrintWriter log) {
        this.log = log;
    }

    private List<String> wordsSort = new ArrayList<>();

    public WordleDictionary() {
        WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader();
        try {
            words = wordleDictionaryLoader.uploadingWords();
        } catch (IOException ex) {
            log.println("Ошибка чтения файла: " + ex.getMessage());
            log.close();
        }
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            if (word.length() != 5) {
                iterator.remove();
            } else {
                wordsSort.add(word.toLowerCase().replace("ё", "е"));
            }
        }
    }

    public String wordСomparison(String enterWord, String checkWord) {
        if (enterWord.length() != checkWord.length()) {
            System.out.println("Слова должны быть одной длинны");
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < enterWord.length(); i++) {

            char enterWordChar = enterWord.charAt(i);
            char checkWordChar = checkWord.charAt(i);

            if (enterWordChar == checkWordChar) {
                result.append("+");
            } else if (checkWord.indexOf(enterWordChar) != -1 && checkWord.indexOf(enterWordChar) != i) {
                result.append("^");
            } else {
                result.append("-");
            }
        }
        return result.toString();
    }

    public int numberOfMatches(String enterWord, String checkWord) {
        if (enterWord == null || checkWord == null) {
            return 0;
        }
        int matches = 0;
        Set<Integer> matchePos = new HashSet<>();
        for (int i = 0; i < enterWord.length(); i++) {
            char enterWordChar = enterWord.charAt(i);
            for (int j = 0; j < checkWord.length(); j++)
                if (!matchePos.contains(j) && enterWordChar == checkWord.charAt(j)) {
                    matches++;
                    matchePos.add(j);
                }
        }
        return matches;
    }

    public boolean availableWords(String word) {
        boolean checkWords = false;
        for (String s : getWordsSort()) {
            if (word.equals(s)) {
                return checkWords = true;
            }
        }
        return checkWords;
    }
}
