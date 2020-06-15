package entity;

import entity.exception.EntityException;

public enum  GenreType {
    ACTION("Action"),
    ADVENTURE ("Adventure"),
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
    WESTERN("Western");

    private String genreType;

    GenreType(String genreType){
        this.genreType = genreType;
    }

    public String getGenreType(){
        return genreType;
    }

    public static GenreType getGenreTypeByValue(String value) throws EntityException {
        GenreType[] genreTypes = GenreType.values();
        for (GenreType genre : genreTypes) {
            if (genre.getGenreType().equals(value)){
                return genre;
            }
        }
        throw new EntityException("Illegal argument");
    }
}
