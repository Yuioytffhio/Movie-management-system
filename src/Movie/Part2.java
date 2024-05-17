//---------------------------------------------------------------------------------------------------------------------
// Assignment 2 - COMP 249
// due March 27th 2024
// Written by: Hiba Talbi
//---------------------------------------------------------------------------------------------------------------------
package Movie;
import java.io.*;
import Exception.*;

/**
 * The Part2 class serializes movie arrays from the genre files created in Part1. It takes the names of the .csv genre files
 * which are written in the part2_manifest.txt file and serializes all the .csv genre files into .ser files.
 * During this process, it creates a movie object for every line to then pint the objects in the serialized files.
 * Finally, it will generate a part3_manifest.txt file which will store the names of the serialized genre files.
 */
public class Part2 {
    /**
     * Processes genre files, creates movie objects, serializes movie arrays, and generates a part3 manifest file.
     * @param manifest2FilePath The file path of the part2 manifest file.
     * @return The file path of the part3 manifest file.
     * @throws FileNotFoundException If the file specified by manifest2FilePath is not found.
     * @throws IOException If an I/O error occurs while reading or writing files.
     * @throws SemanticErrorException If a semantic error occurs during movie object creation.
     * @throws SyntaxErrorException If a syntax error occurs during movie object creation.
     */
    public static String do_part2(String manifest2FilePath) throws FileNotFoundException, IOException, SemanticErrorException, SyntaxErrorException {
        String[] genreFiles = new String[0];
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(manifest2FilePath));
            StringBuilder genreFilesStringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                genreFilesStringBuilder.append(line.trim()).append("\n");
            }
            genreFiles = genreFilesStringBuilder.toString().split("\n");
        } catch (IOException e) {
            System.out.println("Error reading part2 manifest file: " + e.getMessage());

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing Buffered Reader: " + e.getMessage());
            }
        }

        // Loop process for each genre file
        for (String genreFile : genreFiles) {
            String genreFilePath = "src/Manifest/csv_files/" + genreFile;
            String serializedGenreFileName = genreFile.replace(".csv", ".ser");

            // Load movies from the genre file
            Movie[] movies;
            try {
                BufferedReader genreReader = new BufferedReader(new FileReader(genreFilePath));
                int numberOfMovies = (int) genreReader.lines().count();
                movies = new Movie[numberOfMovies];
                genreReader.close();
            } catch (IOException e) {
                System.out.println("Error reading genre file: " + e.getMessage());
                continue;
            }

            // Create movie object for each line of the genre file
            try {
                BufferedReader genreReader = new BufferedReader(new FileReader(genreFilePath));
                String line;
                int movieIndex = 0;
                while((line = genreReader.readLine()) != null) {
                    Movie movie = Part1.createMovieObject(line);
                    movies[movieIndex] = movie;
                    movieIndex++;
                }
            } catch (IOException | SemanticErrorException | SyntaxErrorException e) {
                System.out.println("Error creating movie object or reading genre file: " + e.getMessage());
                continue;
            }

            // Serialize the genre files into .ser files
            ObjectOutputStream outputStream = null;
            try {
                String serFilePath = "src/Manifest/ser_files/" + serializedGenreFileName;
                outputStream = new ObjectOutputStream(new FileOutputStream(serFilePath));
                outputStream.writeObject(movies);
            } catch (IOException e) {
                System.out.println("Error serializing movie array to .ser file: " + e.getMessage());
                continue;
            }
        }// end of for loop

        // Produce the path for part3_manifest file
        String part3ManifestPath = "src/Manifest/part3_manifest.txt";

        // Write the names of each serialized file into part3_manifest.txt
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(part3ManifestPath));
            for (String genreFile : genreFiles) {
                String serializedFileName = genreFile.replace(".csv", ".ser");
                writer.println(serializedFileName);
            }
        } catch (IOException e) {
            System.out.println("Error creating part3 manifest file: " + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return part3ManifestPath;
    }// end of do_part2

}// end of Part2
