package ru.yandex.practicum;

import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
WordleDictionary
 */

public class WordleGame {
    public void setSteps(int steps) {
        this.steps = steps;
    }

    private String answer = "";

    private int steps;

    private WordleDictionary dictionary = new WordleDictionary();
    Scanner scanner = new Scanner(System.in);
    String randomWord;
   private LinkedHashMap<String, Integer> hintMap = new LinkedHashMap<>();
    boolean finish = false;


    public void starGame() {
        steps = 0;
        randomWord = getRandomWord(dictionary.getWordsSort());

        System.out.println("Добро пожаловать в игру, вам нужно угадать слово из 5 букв, за 5 попыток, удачи");

        while (!finish) {

            System.out.println(randomWord);

            System.out.println("Введите ваш ответ:");

            answer = scanner.nextLine();

            System.out.println(checkingTheWord(answer, randomWord));
        }
    }

    public String gameHint(List<String> list, LinkedHashMap<String, Integer> hintMap, String rightAnswer) {
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : hintMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (maxCount < value) {
                maxCount = value;
            }
        }

        if (maxCount == 0) {
            System.out.println("Вы пока не ввели не одного слова, пожалуйста введите минимум один ответ");
            return null;
        }

        List<String> appropriateWords = new ArrayList<>();
        for (String word : list) {
            int considens = dictionary.numberOfMatches(word, rightAnswer);
            if (considens >= maxCount && word.length() >= 5 && !hintMap.containsKey(word)) {
                appropriateWords.add(word);
            }
        }

        if (!appropriateWords.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(appropriateWords.size());
            String suggestedWord = appropriateWords.get(index);
            System.out.println("Вот ваша подсказка");
            hintMap.put(suggestedWord, dictionary.numberOfMatches(suggestedWord, randomWord));
            return suggestedWord;
        } else {
            System.out.println("Не смогли найти вам подсказку");
            return null;
        }
    }


    public String getRandomWord(List<String> words) {
        Collections.shuffle(words); // Перемешиваем переданный список

        Random randomWords = new Random();
        String randomWord = words.get(randomWords.nextInt(words.size()));
        return randomWord;
    }


    public void processAnswer(String userInput) {
        answer = userInput;
    }

    public int getSteps() {
        return steps;
    }

    public WordleDictionary getDictionary() {
        return dictionary;
    }

    public boolean checkFinish(String check) {
        if (answer.equals(randomWord)) {
            return finish = true;
        }
        return finish;
    }

/*
    public void checkingTheWord(String word) {
        if (word.isEmpty()) {
            String empty = gameHint(dictionary.getWordsSort(), hintMap, randomWord);
            if (empty != null) {
                System.out.println(empty);
            } else {
                System.out.println("Не нашел для вас подсказку");
            }
        } else if (word.length() != 5) {
            System.out.println("Введите слово из 5 букв, пожалуйста");
        } else if (!dictionary.availableWords(word)) {
            System.out.println("Такого слова нету в списке загаданных слов, повторите попытку");
        } else if (dictionary.availableWords(word)) {
            String print = dictionary.wordСomparison(word, randomWord);
            System.out.println(print);
            hintMap.put(word, dictionary.numberOfMatches(word, randomWord));
            steps++;
        }
        if (word.equals(randomWord)) {
            checkFinish(word);
        } else if (steps >= 5) {
            GameOver();
        } else {
            System.out.println("Пока что вы не угадали слово, у вас осталось попыток: " + (5 - steps));
            System.out.println("Попробуйте, использовать подсказку, для этого нажмите enter");
        }

    }*/


    public String checkingTheWord(String word, String randomWord) {
        if (word.isEmpty()) {
            String empty = gameHint(dictionary.getWordsSort(), hintMap, randomWord);
            if (empty != null) {
                return empty;
            } else {
                return "Не нашел для вас подсказку";
            }
        } else if (word.length() != 5) {
            return "Введите слово из 5 букв, пожалуйста";
        } else if (!dictionary.availableWords(word)) {
            return "Такого слова нету в списке загаданных слов, повторите попытку";
        } else if (dictionary.availableWords(word)) {
            String print = dictionary.wordСomparison(word, randomWord);
            System.out.println(print);
            hintMap.put(word, dictionary.numberOfMatches(word, randomWord));
            steps++;
            if (steps >= 5) {
                GameOver();
                return "К сожалению ваши попытки закончились, попробуйте в другой раз";
            } else if (word.equals(randomWord)) {
                checkFinish(word);
                return "Поздравляю, вы правильно угадали слово!!";
            } else {
                return "Пока что вы не угадали слово, у вас осталось попыток: " + (5 - steps) + "\n" +
                        "Попробуйте, использовать подсказку, для этого нажмите enter";
            }
        }else {
            return "Пока что вы не угадали слово, у вас осталось попыток: " + (5 - steps) + "\n" +
                    "Попробуйте, использовать подсказку, для этого нажмите enter";
        }

    }


    public boolean GameOver() {
        System.out.println("Загадонное слово, было: " + randomWord);
        return finish = true;
    }

}
