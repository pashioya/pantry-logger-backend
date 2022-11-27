package int3.team2.website.pantry_loogr.presentation.helper;

import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

/*
* This class exists to make sure that only the existing items can be passed to
* the DataItem class as parameters. The different Enums represent all the possible
* Headers and Footers in the "header-parts" and "footer-parts" files respectively.
* */
public enum HtmlItems {
    //different possible headers
    HEADER_TITLE, HEADER_TITLES, BACK_BUTTON, SEARCH_CONTAINER, DROPDOWN, LOGO,

    //Different possible footers
    RECOMMENDATIONS, SHOPPINGLIST, SCANNER, RECIPE_BROWSER, REFRESH, CREATE_RECIPE
    ;

    @Override
    public String toString() {
        StringBuilder current = new StringBuilder(super.toString());
        IntStream.range(0, current.length()).forEach(x -> {
            if (current.charAt(x) == '_') {
                current.setCharAt(x, '-');
            }
        });
        LoggerFactory.getLogger(this.getClass()).debug(current.toString().toLowerCase());
        return current.toString().toLowerCase();
    }
}
