package ru.yandex.practicum;


import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    private PrintWriter log;

    public void setLog(PrintWriter log) {
        this.log = log;
    }

    public List<String> uploadingWords() throws IOException {
        List<String> readerList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("words_ru.txt", StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                readerList.add(reader.readLine());
            }
        } catch (IOException ex) {
            log.println("Ошибка чтения файла: " + ex.getMessage());
        }
        return readerList;
    }

}