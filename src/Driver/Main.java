//---------------------------------------------------------------------------------------------------------------------
// Assignment 2 - COMP 249
// due March 27th 2024
// Written by: Hiba Talbi
//---------------------------------------------------------------------------------------------------------------------
package Driver;

import Movie.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import Exception.*;

/**
 * The Main class orchestrates operations related to managing movie records.
 * It provides methods for navigation, displaying menus, and counting movies in different genres.
 *
 * @author Hiba Talbi
 * @version 1.0
 */

public class Main {
    private static Scanner sc = new Scanner(System.in);

    /**
     * The main method is the entry point of the program.
     * It performs tasks related to reading, processing, and navigating movie records.
     *
     * @param args Command-line arguments (not used).
     * @throws IOException if an I/O error occurs.
     * @throws SemanticErrorException if a semantic error occurs during processing.
     * @throws SyntaxErrorException if a syntax error occurs during processing.
     */
    public static void main(String[] args) throws IOException, SemanticErrorException, SyntaxErrorException {
        try {
            String part1_manifest = "part1_manifest.txt";
            String inputFolder = "Input_files";
            String part2_manifest = Part1.do_part1(part1_manifest, inputFolder);
            String part3_manifest = Part2.do_part2(part2_manifest );
            Movie[][] allMovies = Part3.do_part3(part3_manifest);
            int genreChoice = 0;
            //String genreName = Part3.extractGenreName(part3_manifest);

            System.out.println("""
                    Library Management System
                    created by Hiba Talbi & Khalil Labban\n""");
            while (true) {
                genreChoice = navigateMovies(allMovies, genreChoice, part3_manifest);
            }
        } catch (IOException | SemanticErrorException | SyntaxErrorException e) {
            e.printStackTrace();
        }


    }// end of main

    /**
     * Allows navigation through movies within different genres or sub-menus.
     *
     * @param allMovies A 2D array containing movie records grouped by genre.
     * @param genreChoice The index of the currently selected genre.
     * @param part3_manifest The file path of the manifest file containing movie records.
     * @return The index of the selected genre after navigation.
     */
    public static int navigateMovies(Movie[][] allMovies, int genreChoice, String part3_manifest) {
        int movieCount = allMovies[genreChoice].length;
        String genreName = "";

        switch (genreChoice) {
            case 0 -> {genreName = "Musical";}
            case 1 -> {genreName = "Comedy";}
            case 2 -> {genreName = "Animation";}
            case 3 -> {genreName = "Adventure";}
            case 4 -> {genreName = "Drama";}
            case 5 -> {genreName = "Crime";}
            case 6 -> {genreName = "Biography";}
            case 7 -> {genreName = "Horror";}
            case 8 -> {genreName = "Action";}
            case 9 -> {genreName = "Documentary";}
            case 10 -> {genreName = "Fantasy";}
            case 11 -> {genreName = "Mystery";}
            case 12 -> {genreName = "Sci-fi";}
            case 13 -> {genreName = "Family";}
            case 14 -> {genreName = "Western";}
            case 15 -> {genreName = "Romance";}
            case 16 -> {genreName = "Thriller";}
        }

        System.out.print("""
                    ------------------------------------------
                                    Main Menu
                    ------------------------------------------
                        s   Select a movie array to navigate
                        n   Navigate\s""" + genreName + " movies (" + movieCount + " records)" +
                    "\n    x   Exit" +
                    "\n-------------------------------------------"+
                    
                    "\nEnter Your Choice: ");
        String choice = sc.next();
        System.out.println();

        if (choice.equalsIgnoreCase("x")) {
            System.exit(0);
        } else if (choice.equalsIgnoreCase("s")) {
            int userGenreChoice = displaySubMenu();
            if (userGenreChoice != 18) {
                genreChoice = userGenreChoice - 1;
            }
            System.out.println();

        } else if (choice.equalsIgnoreCase("n")) {
            assert allMovies != null;
            navigateGenre(allMovies[genreChoice], genreName);
        }
        return genreChoice;

    }// end of NavigateMovies

    /**
     * Displays a sub-menu for selecting different genres of movies.
     *
     * @return The user's choice of genre from the sub-menu.
     */
    public static int displaySubMenu() {

        int musicalCount = countMoviesInGenre("Musical");
        int comedyCount = countMoviesInGenre("Comedy");
        int animationCount = countMoviesInGenre("Animation");
        int adventureCount = countMoviesInGenre("Adventure");
        int dramaCount = countMoviesInGenre("Drama");
        int crimeCount = countMoviesInGenre("Crime");
        int bioCount = countMoviesInGenre("Biography");
        int horrorCount = countMoviesInGenre("Horror");
        int actionCount = countMoviesInGenre("Action");
        int documentaryCount = countMoviesInGenre("Documentary");
        int fantasyCount = countMoviesInGenre("Fantasy");
        int mysteryCount = countMoviesInGenre("Mystery");
        int sciFiCount = countMoviesInGenre("Sci-fi");
        int familyCount = countMoviesInGenre("Family");
        int westernCount = countMoviesInGenre("Western");
        int romanceCount = countMoviesInGenre("Romance");
        int thrillerCount = countMoviesInGenre("Thriller");

        System.out.print("""
                -----------------------------------------------------
                                    Genre Sub-Menu
                -----------------------------------------------------
                    1  musical                          (""" + musicalCount + " movies)" +
                "\n    2  comedy                           (" + comedyCount + " movies)" +
                "\n    3  animation                        (" + animationCount + " movies)" +
                "\n    4  adventure                        (" + adventureCount + " movies)" +
                "\n    5  drama                            (" + dramaCount + " movies)" +
                "\n    6  crime                            (" + crimeCount + " movies)" +
                "\n    7  biography                        (" + bioCount + " movies)" +
                "\n    8  horror                           (" + horrorCount + " movies)" +
                "\n    9  action                           (" + actionCount + " movies)" +
                "\n   10  documentary                      (" + documentaryCount + " movies)" +
                "\n   11  fantasy                          (" + fantasyCount + " movies)" +
                "\n   12  mystery                          (" + mysteryCount + " movies)" +
                "\n   13  sci-fi                           (" + sciFiCount + " movies)" +
                "\n   14  family                           (" + familyCount + " movies)" +
                "\n   15  western                          (" + westernCount + " movies)" +
                "\n   16  romance                          (" + romanceCount + " movies)" +
                "\n   17  thriller                         (" + thrillerCount + " movies)" +
                "\n   18  Exit" +
                "\n-------------------------------------------------------" +
                "\nEnter Your Choice: ");

        return sc.nextInt();
    }// end of displaySubMenu

    /**
     * Counts the number of movies available in a specific genre.
     *
     * @param genre The name of the genre.
     * @return The count of movies in the specified genre.
     */
    public static int countMoviesInGenre(String genre) {
        File genreFolder = new File("src/Manifest/csv_files");
        File[] genreFiles = genreFolder.listFiles();
        int count = 0;

        if (genreFiles != null) {
            for (File genreFile : genreFiles) {
                if (genreFile.isFile() && genreFile.getName().equalsIgnoreCase(genre + ".csv")) {
                    BufferedReader genreReader = null;
                    try {
                        genreReader = new BufferedReader(new FileReader(genreFile));
                        count = (int) genreReader.lines().count();
                    } catch (IOException e) {
                        e.getMessage();
                    } finally {
                        try {
                            if (genreReader != null) {
                                genreReader.close();
                            }
                        } catch (IOException e) {
                            System.out.println("Error closing BufferedReader: " + e.getMessage());
                        }
                    }
                }
            }
        }
        return count;
    }// end of countMoviesInGenreFiles

    /**
     * Facilitates navigation through movies within a specific genre.
     *
     * @param movieGenre An array containing movie records of a specific genre.
     * @param genreName The name of the genre.
     */
    public static void navigateGenre(Movie[] movieGenre, String genreName) {
        int currentPosition = 0;
        boolean repeat = true;
        if (movieGenre.length == 0) {
            System.out.println("There are no movie records of this genre.\n");
            return;
        } else {
            while (repeat) {
                System.out.println("Navigating " + movieGenre[0].getGenres() + " movies (" + movieGenre.length + " records)");
                System.out.println("Current movie: " + movieGenre[currentPosition]);
                System.out.print("Enter your choice to navigate (1 to " + movieGenre.length + ") or enter \"x\" to exit: ");
                String choice = sc.next();
                System.out.println();

                if (choice.equalsIgnoreCase("x")) {
                    return;
                } else {
                    int indexChoice = Integer.parseInt(choice) - 1;
                    if (indexChoice == -1) {
                        repeat = false;
                    } else {
                        int newPosition = currentPosition + indexChoice;

                        if (newPosition < 0) {
                            System.out.println("BOF has been reached. Displaying all records from the top of the array.");
                            for (int i = 0; i < movieGenre.length; i++) {
                                System.out.println(movieGenre[i]);
                            }
                            System.out.println();
                            currentPosition = 0;

                        } else if (newPosition >= movieGenre.length) {
                            System.out.println("EOF has been reached. Displaying all records until the end.");
                            for (int i = currentPosition; i < movieGenre.length; i++) {
                                System.out.println(movieGenre[i]);
                            }
                            System.out.println();
                            currentPosition = movieGenre.length - 1;

                        } else {
                            currentPosition = newPosition;
                        }
                    }
                }

            }
        }

    }// end of navigateGenre
}// end of Main