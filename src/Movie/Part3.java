//---------------------------------------------------------------------------------------------------------------------
// Assignment 2 - COMP 249
// due March 27th 2024
// Written by: Hiba Talbi
//---------------------------------------------------------------------------------------------------------------------
package Movie;
import java.io.*;
import Exception.*;

/**
 * The Part 3 class provides the do_part3 method which deserializes the genre files and create a  2D object of type
 * Movie[][] name allMovies. allMovies[0][j] represents the j’th Movie object within the array of a certain movie genre.
 */
public class Part3 {
    /**
     * Deserializes movie arrays from serialized files based on the provided manifest file.
     * @param manifest3Path The file path of the manifest file containing the names of serialized files.
     * @return A 2D array of Movie objects, where each array represents a genre of movies.
     * @throws FileNotFoundException If the manifest file specified by manifest3Path is not found.
     */
    public static Movie[][] do_part3(String manifest3Path) throws FileNotFoundException {
        // Read names of serialized files from manifest file
        BufferedReader reader = null;
        Movie[][] allMovies = null;
        try {
            reader = new BufferedReader(new FileReader(manifest3Path));
            String line;
            int fileCount = 0;
            while ((line = reader.readLine()) != null) {fileCount++;}

            // The allMovies array has as many arrays as there are binary files
            allMovies = new Movie[fileCount][];

            reader.close(); // resets it to the beginning of the file
            reader = new BufferedReader(new FileReader(manifest3Path));

            // Deserializing the movie arrays
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String serializedFilePath = "src/Manifest/ser_files/" + line.trim();
                String genreName = extractGenreName(line);
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(serializedFilePath));
                allMovies[i] = (Movie[]) inputStream.readObject();
                inputStream.close();

                // Assign genreName to the appropriate location
                for (Movie movie: allMovies[i]) {
                    movie.setGenres(genreName);
                }

                i++; // Increment to do it for every serialized file name
            }
        } catch (IOException | ClassNotFoundException e) {
            e.getMessage();
        }
        // close the buffered reader
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }

        }
        return allMovies;
    }// end of do_part3

    /**
     * Extracts genre name from a file name.
     * @param fileName The name of the file containing the genre name.
     * @return The extracted genre name.
     */
    public static String extractGenreName(String fileName) {
        int endIndex = fileName.lastIndexOf('.');
        int startIndex = fileName.lastIndexOf('/') + 1;
        if (startIndex < 0 || endIndex < 0 || startIndex >= endIndex) {
            return "";
        }
        return fileName.substring(startIndex, endIndex);
    }

}// end of Part3
