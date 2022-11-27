package int3.team2.website.pantry_loogr.presentation.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* This class is a container for the controllers to pass on data to
* Thymeleaf. It contains which header/footer should be selected from the
* respective html files and if there is content needed what
* text should be inserted in that html.
* */
public class DataItem {
    private Logger logger;

    //Represents the possible options of types
    private final String type;
    private final String content;

    public DataItem(HtmlItems type, String content) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.type = type.toString();
        this.content = content;
    }

    public DataItem(HtmlItems type) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.type = type.toString();
        this.content = "";
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
