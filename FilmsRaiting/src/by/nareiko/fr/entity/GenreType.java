package by.nareiko.fr.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The enum Genre type.
 */
public enum GenreType {
    /**
     * Action genre type.
     */
    ACTION("Action"),
    /**
     * Adventure genre type.
     */
    ADVENTURE("Adventure"),
    /**
     * Comedy genre type.
     */
    COMEDY("Comedy"),
    /**
     * Crime genre type.
     */
    CRIME("Crime"),
    /**
     * Drama genre type.
     */
    DRAMA("Drama"),
    /**
     * Fantasy genre type.
     */
    FANTASY("Fantasy"),
    /**
     * Historical genre type.
     */
    HISTORICAL("Historical"),
    /**
     * The Historical fiction.
     */
    HISTORICAL_FICTION("Historical fiction"),
    /**
     * Horror genre type.
     */
    HORROR("Horror"),
    /**
     * The Magival realism.
     */
    MAGIVAL_REALISM("Magical realism"),
    /**
     * Mystery genre type.
     */
    MYSTERY("Mystery"),
    /**
     * Philososphical genre type.
     */
    PHILOSOSPHICAL("Philosophical"),
    /**
     * Political genre type.
     */
    POLITICAL("Political"),
    /**
     * Romance genre type.
     */
    ROMANCE("Romance"),
    /**
     * Satire genre type.
     */
    SATIRE("Satire"),
    /**
     * The Science fiction.
     */
    SCIENCE_FICTION("Science fiction"),
    /**
     * Thriller genre type.
     */
    THRILLER("Thriller"),
    /**
     * Western genre type.
     */
    WESTERN("Western"),
    /**
     * Unknown genre genre type.
     */
    UNKNOWN_GENRE("Uknown");

    private static final Logger LOGGER = LogManager.getLogger();

    private String genreType;

    GenreType(String genreType) {
        this.genreType = genreType;
    }

    /**
     * Gets genre type by value.
     *
     * @param value the value
     * @return the genre type by value
     */
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

    /**
     * Gets genre type.
     *
     * @return the genre type
     */
    public String getGenreType() {
        return genreType;
    }
}
