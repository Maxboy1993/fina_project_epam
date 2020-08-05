package by.nareiko.fr.controller;

import by.nareiko.fr.controller.command.PagePath;

public class Router {
    private String page = PagePath.INDEX;
    private DisPathType disPathType = DisPathType.FORWARD;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public DisPathType getDisPathType() {
        return disPathType;
    }

    public void setRedirect() {
        this.disPathType = DisPathType.REDIRECT;
    }

    enum DisPathType {
        FORWARD,
        REDIRECT
    }
}
