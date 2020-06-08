package test_example.reader.factory;

import test_example.reader.impl.SQLReaderImpl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SQLReaderFactory {
    private static SQLReaderFactory instance;
    private static SQLReaderImpl reader;
    private static Lock lock = new ReentrantLock();

    private SQLReaderFactory(){}

    public static SQLReaderFactory getInstance() {
        if (instance == null) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new SQLReaderFactory();
                }
            }finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public SQLReaderImpl getSQLReaderImpl(){
        if (reader == null){
            reader = new SQLReaderImpl();
        }
        return reader;
    }
}
