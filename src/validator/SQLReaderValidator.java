package validator;

public class SQLReaderValidator {
    public boolean checkPath(String filePath){
        boolean flag = false;
        if (filePath == null || filePath.isEmpty()){
            flag = true;
        }
        return flag;
    }
}
