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
import java.util.List;
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

	// TODO #0: Add members for the other 15 attributes.

	//private final SimpleFootypeProperty	foo;

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

		// Create properties for the first two attributes.
		title = new SimpleStringProperty(item.get(0));
		image = new SimpleStringProperty(item.get(1));

		// TODO #1: Create properties for the other 15 attributes. For
		// non-string types, look for methods in the Boolean, Integer, and
		// Double classes to do any needed conversions or parsing of strings.

		//foo = new SimpleFootypeProperty(item.get(2));
	}

	//**********************************************************************
	// Public Methods (Getters and Setters)
	//**********************************************************************

	// Each attribute has methods to access and modify its value.

	public String	getTitle()
	{
		return title.get();
	}

	public void	setTitle(String v)
	{
		title.set(v);
	}

	public String	getImage()
	{
		return image.get();
	}

	public void	setImage(String v)
	{
		image.set(v);
	}

	// TODO #2: Create access and modify methods for the other 15 attributes.
	// For non-string attributes, use primitive types (boolean, int, double)
	// for the argument and return types.

	//public footype	getFoo()
	//{
	//	return foo.get();
	//}

	//public void	setFoo(footype v)
	//{
	//	foo.set(v);
	//}

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
}

//******************************************************************************
