package model.dao.reader.impl;

import model.dao.exception.ReaderExceprion;
import model.dao.reader.SQLReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validator.SQLReaderValidator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class SQLReaderImpl implements SQLReader {
    private static final String FILE_PATH = "resourses/data/filmsRaiting";
    private static final Logger LOGGER = LogManager.getLogger();
    private SQLReaderValidator validator = new SQLReaderValidator();
    private List<String> lines;

    @Override
    public List<String> read(String filePath) throws ReaderExceprion {
        boolean validation = validator.checkPath(filePath);
        if (validation){
                throw new ReaderExceprion("File path is empty!");
        }
        Path path = Paths.get(filePath);
        try{
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            LOGGER.error("IO exception!");
        }
        return lines;
    }
}
