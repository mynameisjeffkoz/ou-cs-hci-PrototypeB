//******************************************************************************
// Copyright (C) 2020,2023 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Tue Feb 14 21:52:13 2023 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20200212 [weaver]:	Original file.
// 20230214 [weaver]:	Added comments about list parsing to the constructor.
//
//******************************************************************************
//
//******************************************************************************

package edu.ou.cs.hci.assignment.prototypeb;

//import java.lang.*;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import javafx.beans.property.*;
import javafx.scene.image.Image;

//******************************************************************************

/**
 * The <CODE>Movie</CODE> class manages the attributes of a movie as a set of
 * properties. The properties are created in the constructor. This differs from
 * the lazy creation of properties described in the TableView API (in the Person
 * class example), because we also use the properties to store the results of
 * parsing the inputs when the application starts.
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Movie
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// Each attribute has a matching property of the corresponding type.

	private final SimpleStringProperty		title;
	private final SimpleStringProperty		image;

	private final SimpleIntegerProperty year;

	private final SimpleStringProperty ageRating;

	private final SimpleIntegerProperty runtime;

	private final SimpleBooleanProperty awardPicture,
			awardDirecting,
			awardCinematography,
			awardActing;

	private final SimpleDoubleProperty userAvgRating;

	private final SimpleIntegerProperty numUserReviews;

	private final SimpleIntegerProperty genre;

    private final SimpleStringProperty genreString;

	private final SimpleStringProperty director;

	private final SimpleBooleanProperty animated;

	private final SimpleBooleanProperty color;

	private final SimpleStringProperty summary;

	private final SimpleStringProperty comments;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public Movie(List<String> item)
	{
		// The list contains a string for each attribute value read from the
		// resources/data/movies.csv file. See the movies-CSV-format.txt file
		// (in the About directory) for information about the order and format
		// of the attribute strings in the list.

		// Each attribute value is (or must be converted from) its string.

		// Hint for genre: The value is an integer in which the individual bits
		// represent which genres a movie has or not. See the footnotes in the
		// movies-CSV-format.txt file for more. Look in EditorPane for examples
		// of how to map integer bits to and from subsets of genres. You'll need
		// to write similar code if you chose genre as one of your 3 attributes.

		// Create an integer for storing the column number
        // This allows the column number to increment automatically
		int col = 0;

		// Create properties for every attribute.

		title = new SimpleStringProperty(item.get(col++));
		image = new SimpleStringProperty(item.get(col++));
		year = new SimpleIntegerProperty(Integer.parseInt(item.get(col++)));
		ageRating = new SimpleStringProperty(item.get(col++));
		runtime = new SimpleIntegerProperty(Integer.parseInt(item.get(col++)));
		awardPicture = new SimpleBooleanProperty(Boolean.parseBoolean(item.get(col++)));
		awardDirecting = new SimpleBooleanProperty(Boolean.parseBoolean(item.get(col++)));
		awardCinematography = new SimpleBooleanProperty(Boolean.parseBoolean(item.get(col++)));
		awardActing = new SimpleBooleanProperty(Boolean.parseBoolean(item.get(col++)));
		userAvgRating = new SimpleDoubleProperty(Double.parseDouble(item.get(col++)));
		numUserReviews = new SimpleIntegerProperty(Integer.parseInt(item.get(col++)));
		genre = new SimpleIntegerProperty(Integer.parseInt(item.get(col++)));
        genreString = new SimpleStringProperty(Movie.decodeGenre(getGenre()));
		director = new SimpleStringProperty(item.get(col++));
		animated = new SimpleBooleanProperty(Boolean.parseBoolean(item.get(col++)));
		color = new SimpleBooleanProperty(Boolean.parseBoolean(item.get(col++)));
		summary = new SimpleStringProperty(item.get(col++));
		comments = new SimpleStringProperty(item.get(col++));

	}

	//**********************************************************************
	// Public Methods (Getters and Setters)
	//**********************************************************************

	// Each attribute has methods to access and modify its value.

	public String	getTitle() {
        return title.get();
	}

	public void	setTitle(String v) {
        title.set(v);
	}

	public String	getImage() {
		return image.get();
	}

	public void	setImage(String v) {
		image.set(v);
	}

    public int getYear() {
        return year.get();
    }

    public void setYear(int i) {
        year.set(i);
    }

    public String getAgeRating() {
        return ageRating.get();
    }

    public int getAgeRatingInt() {
        String[] ratings = {"G", "PG", "PG-13", "R"};
        for (int i = 0; i < ratings.length;i++)
            if (getAgeRating().equalsIgnoreCase(ratings[i]))
                return i;
        return 0;
    }

    public void setAgeRating(String s) {
        ageRating.set(s);
    }

    public int getRuntime() {
        return runtime.get();
    }

    public void setRuntime(int i) {
        runtime.set(i);
    }

    public boolean getAwardPicture() {
        return awardPicture.get();
    }

    public void setAwardPicture(boolean b) {
        awardPicture.set(b);
    }

    public boolean getAwardDirecting() {
        return awardDirecting.get();
    }

    public void setAwardDirecting(boolean b) {
        awardDirecting.set(b);
    }

    public boolean getAwardCinematography() {
        return awardCinematography.get();
    }

    public void setAwardCinematography(boolean b) {
        awardCinematography.set(b);
    }

    public boolean getAwardActing() {
        return awardActing.get();
    }

    public void setAwardActing(boolean b) {
        awardActing.set(b);
    }

    public double getUserAvgRating() {
        return userAvgRating.get();
    }

    public void setUserAvgRating(double d) {
        userAvgRating.set(d);
    }

    public int getNumUserReviews() {
        return numUserReviews.get();
    }

    public void setNumUserReviews(int i) {
        numUserReviews.set(i);
    }

    public int getGenre() {
        return genre.get();
    }

    public void setGenre(int i) {
        genre.set(i);
        // Also update the genreString, if it's not already correct
        String decoded = Movie.decodeGenre(i);
        if (!genreString.equals(decoded))
            setGenreString(decoded);
    }

    public String getGenreString() {
        return genreString.get();
    }

    // Not to be accessed by users
    private void setGenreString(String s) {
        genreString.set(s);
        // Check if the int code should be updated, and do so if necessary
        int code = Movie.encodeGenre(s);
        if (code != getGenre())
            setGenre(code);
    }

    public String getDirector() {
        return director.get();
    }

    public void setDirector(String s) {
        director.set(s);
    }

    public boolean isAnimated() {
        return animated.get();
    }

    public void setAnimated(boolean b) {
        animated.set(b);
    }

    public boolean isColor() {
        return color.get();
    }

    public void setColor(boolean b) {
        color.set(b);
    }

    public String getSummary() {
        return summary.get();
    }

    public void setSummary(String s) {
        summary.set(s);
    }

    public String getComments() {
        return comments.get();
    }

    public void setComments(String s) {
        comments.set(s);
    }

	//**********************************************************************
	// Public Methods (Alternative Access Methods)
	//**********************************************************************

	// Convenience method for loading and resizing movie poster images. Loads
	// the image in the file named by the image property value, relative to the
	// given path, and returns a version resized to the given width and height.
	public Image	getImageAsImage(String path, double width, double height)
	{
		try
		{
			return new Image(path + getImage(), width, height, false, true);
		}
		catch (Exception ex)
		{
			return null;
		}
	}

    /**
     * Calculate the integer encoding of the genre data from a String
     * @param s The String containing movie genres
     * @return an integer based on a bitwise encoding of the genre data
     */
    public static int encodeGenre(String s) {
        // Create an integer to store the encoding
        int code = 0;
        // Split the list of genres into an array of individual entries
        String[] genres = s.split("[^\\w|-]");
        // Get the list of all genres
        String[] allGenres = Movie.getAllGenres();
        // For each entry in the input list
        for (String genre:genres) {
            // For each possible genre
            for (int i = 0; i < allGenres.length; i++) {
                // Check if the genre matches
                if (genre.equalsIgnoreCase(allGenres[i])) {
                    // Calculate the bit value and add it to the code
                    code += (1 << i);
                    // Exit the inner for loop on success
                    break;
                }
            }
        }
        return code;
    }

    /**
     * Generate the String representation of genres based on an encoded integer
     * @return a String containing the genres, seperated by commas
     */
    public static String decodeGenre(int i) {
        // Convert the int code into its binary representation (as a string)
        String code = Integer.toBinaryString(i);
        // Create a StringBuilder
        StringBuilder builder = new StringBuilder();
        // Reverse the order of the code String (to read right to left)
        // Add the code to the builder
        builder.append(code);
        // Reverse the builder;
        builder.reverse();
        // Store the new value
        code = builder.toString();
        // Create a StringJoiner, to automatically format the list of genres
        StringJoiner joiner = new StringJoiner(", ");
        // Get the list of all possible genres
        String[] allGenres = Movie.getAllGenres();
        // Read the code one character at a time
        for (int idx = 0; idx <  code.length(); idx++) {
            // If that bit shows true
            if (code.charAt(idx) == '1')
                // Add the corresponding genre to the list
                joiner.add(allGenres[idx]);
            // Otherwise, continue
        }
        // Return the String containing a formatted list
        return joiner.toString();
    }

    public static String[] getAllGenres() {
        String[] genres = {"Action", "Comedy", "Documentary", "Drama", "Fantasy", "Horror", "Romance",
                "Sci-Fi", "Thriller", "Western"};
        return genres;
    }

}

//******************************************************************************
