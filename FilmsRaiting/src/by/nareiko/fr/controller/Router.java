package by.nareiko.fr.controller;

import by.nareiko.fr.controller.command.PagePath;

/**
 * The type Router.
 */
public class Router {
    private String page = PagePath.INDEX;
    private DisPathType disPathType = DisPathType.FORWARD;

    /**
     * Gets page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * Gets dis path type.
     *
     * @return the dis path type
     */
    public DisPathType getDisPathType() {
        return disPathType;
    }

    /**
     * Sets redirect.
     */
    public void setRedirect() {
        this.disPathType = DisPathType.REDIRECT;
    }

    /**
     * The enum Dis path type.
     */
    enum DisPathType {
        /**
         * Forward dis path type.
         */
        FORWARD,
        /**
         * Redirect dis path type.
         */
        REDIRECT
    }
}
