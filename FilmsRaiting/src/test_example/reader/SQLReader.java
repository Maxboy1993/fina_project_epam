package test_example.reader;

import model.dao.exception.DaoException;

import java.util.List;

public interface SQLReader {
    List<String> read(String filePath) throws DaoException;
}
