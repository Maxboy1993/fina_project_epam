package model.dao.reader;

import model.dao.exception.ReaderExceprion;

import java.util.List;

public interface SQLReader {
    List<String> read(String filePath) throws ReaderExceprion;
}
