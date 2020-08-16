package by.nareiko.fr.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CustomDateTagWriter extends TagSupport {
    private static Logger LOGGER = LogManager.getLogger();

    private String date;

    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public int doStartTag() throws JspException {
        try {
            String output = date;
            pageContext.getOut().write(output);
        } catch (IOException e) {
            LOGGER.error("Error while custom tag parameter out put: ", e);
            throw new JspException("Error while custom tag parameter out put: ", e);
        }
        return SKIP_BODY;
    }
}
