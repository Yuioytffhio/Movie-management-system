//---------------------------------------------------------------------------------------------------------------------
// Assignment 2 - COMP 249
// due March 27th 2024
// Written by: Hiba Talbi
//---------------------------------------------------------------------------------------------------------------------
package Movie;
import java.io.Serializable;
import Exception.SemanticErrorException;

/**
 * The Movie class represents a movie record with various attributes such as year, title, duration, genres, rating,
 * score, director, and actors. It implements the Serializable interface to serialize the files with movie records
 * later on in different classes.
 */

public class Movie implements Serializable {
    private boolean numberException = false;
    private String badScore;
    private String badDuration;
    private String badYear;
    private int year;
    private String title;
    private int duration;
    private String genres;
    private String rating;
    private double score;
    private String director;
    private String actor1;
    private String actor2;
    private String actor3;
    private boolean validRecord;

    // Default constructor
    public Movie() {
    }

    // Copy constructor
    public Movie(Movie other) {
        this.year = other.year;
        this.title = other.title;
        this.duration = other.duration;
        this.genres = other.genres;
        this.rating = other.rating;
        this.score = other.score;
        this.director = other.director;
        this.actor1 = other.actor1;
        this.actor2 = other.actor2;
        this.actor3 = other.actor3;
    }

    // Accessors
    /**
     * Gets the year of the movie.
     * @return year of the movie.
     */
    public int getYear() {
        return year;
    }

    /**
     * gets the title of the movie
     * @return title of the movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the duration of the movie.
     * @return duration of the movie.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the genre of the movie.
     * @return genre of the movie.
     */
    public String getGenres() {
        return genres;
    }

    /**
     * Gets the rating of the movie.
     * @return rating of the movie.
     */
    public String getRating() {
        return rating;
    }

    /**
     * Gets the score of the movie.
     * @return score of the movie.
     */
    public double getScore() {
        return score;
    }

    /**
     * Gets the director of the movie.
     * @return director of the movie.
     */
    public String getDirector() {
        return director;
    }

    /**
     * Gets the actor1 of the movie.
     * @return actor1 of the movie.
     */
    public String getActor1() {
        return actor1;
    }

    /**
     * Gets the actor2 of the movie.
     * @return actor2 of the movie.
     */
    public String getActor2() {
        return actor2;
    }

    /**
     * Gets the actor3 of the movie.
     * @return actor3 of the movie.
     */
    public String getActor3() {
        return actor3;
    }

    /**
     * Gets the validRecord of the movie, to know if the movie is written in a valid format or not.
     * @return validRecord of the movie.
     */
    public boolean getValidRecord() {return validRecord;}

    /**
     * Gets the bad year of a movie, if it's not an integer
     * @return bad year
     */
    public String getBadYear() {return badYear;}

    /**
     * Gets a boolean that returns true if a parameter that's supposed to be an integer isn't one
     * @return boolean numberException
     */
    public boolean getNumberException() {return numberException;}

    /**
     * Gets the bad score of a movie, if it's not an integer
     * @return bad score
     */
    public String getBadScore() {return badScore;}

    /**
     * Gets the bad duration of a movie, if it's not an integer
     * @return bad duration
     */
    public String getBadDuration() {return badDuration;}

    // Mutators
    /**
     * Sets the year of the movie.
     * @param year of the movie.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Sets the title of the movie.
     * @param title of the movie.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the duration of the movie.
     * @param duration of the movie.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Sets the genre of the movie.
     * @param genres of the movie.
     */
    public void setGenres(String genres) {
        this.genres = genres;
    }

    /**
     * Sets the rating of the movie.
     * @param rating of the movie.
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * Sets the score of the movie.
     * @param score of the movie.
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Sets the director of the movie.
     * @param director of the movie.
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Sets the first actor of the movie.
     * @param actor1 of the movie.
     */
    public void setActor1(String actor1) {
        this.actor1 = actor1;
    }

    /**
     * Sets the second actor of the movie.
     * @param actor2 of the movie.
     */
    public void setActor2(String actor2) {
        this.actor2 = actor2;
    }

    /**
     * Sets the third actor of the movie.
     * @param actor3 of the movie.
     */
    public void setActor3(String actor3) {
        this.actor3 = actor3;
    }

    /**
     * Sets if the movie has a valid record or not.
     * @param validRecord of the movie.
     */
    public void setValidRecord(boolean validRecord) {this.validRecord = validRecord;}

    /**
     * Sets the bad year as a string if it can't be cast as an integer
     * @param badYear
     */
    public void setBadYear(String badYear) {this.badYear = badYear;}

    /**
     * Sets the boolean numberException of a movie that sets if some of the integer parameters can't be casted into integers
     * @param numberException
     */
    public void setNumberException(boolean numberException) {this.numberException = numberException;}

    /**
     * Sets the bad score of a movie as a String, if it can't be cast as an integer
     * @param badScore
     */
    public void setBadScore(String badScore) {this.badScore = badScore;}

    /**
     * Sets the bad duration of a movie as a String, if it can't be cast as an integer
     * @param badDuration
     */
    public void setBadDuration(String badDuration) {this.badDuration = badDuration;}

    // Override equals() method

    /**
     * Checks if this movie is equal to another object.
     * @param o The object to compare.
     * @return True if the objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year &&
                duration == movie.duration &&
                Double.compare(movie.score, score) == 0 &&
                title.equals(movie.title) &&
                genres.equals(movie.genres) &&
                rating.equals(movie.rating) &&
                director.equals(movie.director) &&
                actor1.equals(movie.actor1) &&
                actor2.equals(movie.actor2) &&
                actor3.equals(movie.actor3);
    }

    // Override toString() method
    /**
     * Returns a string representation of the movie.
     * @return A string representation of the movie.
     */
    @Override
    public String toString() {
        if (getNumberException()) {
            return getBadYear() + "," + getTitle() + "," + getBadDuration() + "," + getGenres() + "," + getRating() +
                    "," + getBadScore() + "," + getDirector() + "," + getActor1() + "," + getActor2() + "," + getActor3();
        } else {
            return getYear() + "," + getTitle() + "," + getDuration() + "," + getGenres() + "," + getRating() +
                    "," + getScore() + "," + getDirector() + "," + getActor1() + "," + getActor2() + "," + getActor3();
        }

    }
}