package by.nareiko.fr.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum GenreType {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    FANTASY("Fantasy"),
    HISTORICAL("Historical"),
    HISTORICAL_FICTION("Historical fiction"),
    HORROR("Horror"),
    MAGIVAL_REALISM("Magical realism"),
    MYSTERY("Mystery"),
    PHILOSOSPHICAL("Philosophical"),
    POLITICAL("Political"),
    ROMANCE("Romance"),
    SATIRE("Satire"),
    SCIENCE_FICTION("Science fiction"),
    THRILLER("Thriller"),
    WESTERN("Western"),
    UNKNOWN_GENRE("Uknown");

    private static final Logger LOGGER = LogManager.getLogger();

    private String genreType;

    GenreType(String genreType) {
        this.genreType = genreType;
    }

    public static GenreType getGenreTypeByValue(String value) {
        GenreType[] genreTypes = GenreType.values();
        for (GenreType genre : genreTypes) {
            if (genre.getGenreType().equalsIgnoreCase(value)) {
                return genre;
            }
        }
        LOGGER.error("Illegal argument, required genre doesn't exist. Returned defualt genre - uknown. Fix in MYSQL.");
        return UNKNOWN_GENRE;
    }

    public String getGenreType() {
        return genreType;
    }
}
