package test_example.reader.impl;

import model.dao.exception.DaoException;
import test_example.reader.SQLReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validator.UserValidator;

import java.util.List;

public class SQLReaderImpl implements SQLReader {
    private static final String FILE_PATH = "resourses/data/filmsRaiting";
    private static final Logger LOGGER = LogManager.getLogger();
    private UserValidator validator = new UserValidator();
    private List<String> lines;

    @Override
    public List<String> read(String filePath) throws DaoException {
//        boolean validation = validator.checkPath(filePath);
//        if (validation){
//                throw new DaoException("File path is empty!");
//        }
//        Path path = Paths.get(filePath);
//        try{
//            lines = Files.readAllLines(path);
//        } catch (IOException e) {
//            LOGGER.error("IO exception!");
//        }
//        return lines;
        return null;
    }
}
