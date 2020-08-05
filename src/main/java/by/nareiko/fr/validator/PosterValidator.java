package by.nareiko.fr.validator;

import javax.servlet.http.Part;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PosterValidator {
    private static final String IMAGE_TYPE_PATTERN = "(?i)(png|gif|jpg|jpeg)(?-i)$";

    public boolean validatePosterExtension(Part filePart) {
        Pattern pattern = Pattern.compile(IMAGE_TYPE_PATTERN);
        Matcher matcher = pattern.matcher(filePart.getContentType());
        boolean isCorrect = matcher.find();
        return isCorrect;
    }
}
