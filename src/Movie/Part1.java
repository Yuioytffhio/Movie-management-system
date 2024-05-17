//---------------------------------------------------------------------------------------------------------------------
// Assignment 2 - COMP 249
// due March 27th 2024
// Written by: Hiba Talbi
//---------------------------------------------------------------------------------------------------------------------
package Movie;
import java.io.*;
import Exception.*;

/**
 * The Part1 class takes the movie records in all the input files and verifies if they're valid based on different criteria:
 * Semantic errors:
 * - A valid year is an integer from 1990 through 1999.
 * - A valid duration is an integer from 30 through 300 minutes.
 * - A valid score is a positive double value less than or equal 10
 * - The valid ratings are PG, Unrated, G, R, PG-13, NC-17,
 * - The valid genre are musical, comedy, animation, adventure, drama, crime, biography,
 *   horror, action, documentary, fantasy, mystery, sci-fi, family, romance, thriller,
 *   western
 * Syntax errors:
 * - missing or excess field(s)
 * - missing end quote in a quoted field
 * If a movie record isn't valid it's stored in the bad_movie_record.txt file, else it's stored in a file .csv depending
 * on it's genre.
 */

public class Part1 {
    private String part1Manifest = "part1_manifest.txt";
    private static String part2Manifest = "part2_manifest.txt";
    private static String badMovieRecords = "bad_movie_records.txt";
    private static String inputFolder = "Input_files";

    /**
     * Gets the file path of the part 1 manifest.
     * @return The file path of the part 1 manifest.
     */
    public String getPart1Manifest() {
        return part1Manifest;
    }

    /**
     * Gets the filename of the part2 manifest.
     * @return The filename of the part2 manifest.
     */
    public static String getPart2Manifest() {
        return part2Manifest;
    }

    /**
     * Gets the filename of the bad movie records.
     * @return The filename of the bad movie records.
     */
    public static String getBadMovieRecords() {
        return badMovieRecords;
    }

    /**
     * Gets the input folder name.
     * @return The input folder name.
     */
    public static String getInputFolder() {
        return inputFolder;
    }

    /**
     * Generates part1_manifest.txt and genre-specific CSV files based on movie records.
     * @param manifestFileName The filename of the part1 manifest.
     * @param inputFolder The folder containing input movie files.
     * @return The path to the part2 manifest file.
     * @throws IOException If an I/O error occurs.
     * @throws SemanticErrorException If a semantic error is encountered.
     * @throws SyntaxErrorException If a syntax error is encountered.
     */
    public static String do_part1(String manifestFileName, String inputFolder) throws IOException, SemanticErrorException,
            SyntaxErrorException {
        // Add the "part1_manifest.txt" file in the "Manifest" folder
        String manifestFolderPath = "src/Manifest/";
        File manifestFolder = new File(manifestFolderPath);
        String manifestFilePath = manifestFolderPath + manifestFileName;

        // Creating part1_manifest.txt
        File folderName = new File("src/" + inputFolder);
        File[] files = folderName.listFiles();

        PrintWriter part1ManifestWriter = null;

        try {
            part1ManifestWriter = new PrintWriter(new FileWriter(manifestFilePath));
            if (files != null) {
                int count = 0;
                int action = 0;
                int adventure = 0;
                int animation = 0;
                int biography = 0;
                int comedy = 0;
                int crime = 0;
                int documentary = 0;
                int drama = 0;
                int family = 0;
                int fantasy = 0;
                int horror = 0;
                int mystery = 0;
                int scifi = 0;
                int western = 0;
                int romance = 0;
                int thriller = 0;
                int musical = 0;

                createGenreFiles();
                for (File file : files) {
                    if (file.isFile()) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String line;
                        while ( (line = reader.readLine()) != null) {
                            // Create a new movie object from the line
                            Movie movie = createMovieObject(line);
                            if (movie.getValidRecord()) {
                                addMovieInGenreFile(movie, action, adventure, animation, biography, comedy, crime, documentary, drama,
                                        family, fantasy, horror, mystery, scifi, western, romance, thriller, musical);
                                if (movie.getGenres().equals("Action")) {action++;}
                                else if (movie.getGenres().equals("Adventure")) {adventure++;}
                                else if (movie.getGenres().equals("Animation")) {animation++;}
                                else if (movie.getGenres().equals("Biography")) {biography++;}
                                else if (movie.getGenres().equals("Comedy")) {comedy++;}
                                else if (movie.getGenres().equals("Crime")) {crime++;}
                                else if (movie.getGenres().equals("Documentary")) {documentary++;}
                                else if (movie.getGenres().equals("Drama")) {drama++;}
                                else if (movie.getGenres().equals("Family")) {family++;}
                                else if (movie.getGenres().equals("Fantasy")) {fantasy++;}
                                else if (movie.getGenres().equals("Horror")) {horror++;}
                                else if (movie.getGenres().equals("Mystery")) {mystery++;}
                                else if (movie.getGenres().equals("Sci-fi")) {scifi++;}
                                else if (movie.getGenres().equals("Western")) {western++;}
                                else if (movie.getGenres().equals("Romance")) {romance++;}
                                else if (movie.getGenres().equals("Thriller")) {thriller++;}

                            } else {
                                addMovieInBadRecordFile(movie, count);
                                count++; // count is incremented everytime a movie is added in the badrecord file
                            }
                        }

                        part1ManifestWriter.println(file.getName());
                    }
                }
            }
            part1ManifestWriter.close();
        } catch(IOException | SemanticErrorException | NumberFormatException |
                SyntaxErrorException e) {
            e.getMessage();
        }

        PrintWriter part2ManifestWriter = null;
        try {
            String[] genres = {"Musical", "Comedy", "Animation", "Adventure", "Drama", "Crime",
                    "Biography", "Horror", "Action", "Documentary", "Fantasy", "Mystery", "Sci-fi",
                    "Family", "Romance", "Thriller", "Western"};

            String manifest2FilePath = "src/Manifest/part2_manifest.txt";
            for (int count = 0; count < genres.length; count++) {
                if (count == 0) {
                    part2ManifestWriter = new PrintWriter(new FileWriter(manifest2FilePath));
                } else {
                    part2ManifestWriter = new PrintWriter(new FileWriter(manifest2FilePath, true));
                }
            }

            for (String genre : genres) {
                part2ManifestWriter.println(genre + ".csv");
            }
            part2ManifestWriter.close();

        } catch(IOException e) {
            e.getMessage();
        }

        return "src/Manifest/part2_manifest.txt";
    }// end of do_part1

    /**
     * Creates a Movie object from a line of movie record.
     *
     * @param line The line containing movie record.
     * @return A Movie object created from the given line.
     * @throws SemanticErrorException If a semantic error occurs while creating the Movie object.
     * @throws SyntaxErrorException If a syntax error occurs while creating the Movie object.
     * @throws BadScoreException If the score is invalid.
     * @throws BadGenreException If the genre is invalid.
     * @throws BadDurationException If the duration is invalid.
     * @throws BadNameException If the movie title is invalid.
     * @throws BadRatingException If the movie rating is invalid.
     * @throws BadYearException If the movie year is invalid.
     * @throws ExcessFieldException If there are excess fields in the movie record.
     * @throws MissingFieldException If there are missing fields in the movie record.
     * @throws MissingQuotesException If quotes are missing in the movie record.
     */
    public static Movie createMovieObject(String line) throws SemanticErrorException, SyntaxErrorException,
            BadScoreException, BadGenreException, BadDurationException, BadNameException, BadRatingException, BadYearException, ExcessFieldException, MissingFieldException, MissingQuotesException {
        // Separate the line into fields
        String[] lineElements = line.split(",");

        String[] fields = new String[10];
        int j = 0;
        for (int i = 0; (i < 10) && (j<lineElements.length); i++) {
            fields[i] = lineElements[j];
            if (!lineElements[j].startsWith("\"") ||
                    lineElements[j].endsWith("\"")) {
                j = j + 1;
                continue;
            }

            int k = j + 1;
            for(; k < lineElements.length; k++) {
                fields[i] += lineElements[k];
                if (lineElements[k].endsWith("\"")) {
                    break;
                }
            }
            j = k + 1;
        }

        // Create new movie object
        Movie movie = new Movie();
        try{
            movie.setYear(Integer.parseInt(fields[0].trim()));
        } catch (NumberFormatException e) {
            movie.setValidRecord(false);
            movie.setNumberException(true);
            movie.setBadYear(fields[0].trim());
        }

        movie.setTitle(fields[1] != null ? fields[1].trim() : "");

        try {
            movie.setDuration(Integer.parseInt(fields[2].trim()));
        } catch (NumberFormatException e) {
            movie.setValidRecord(false);
            movie.setNumberException(true);
            movie.setBadDuration(fields[2].trim());
        }
        movie.setGenres(fields[3] != null ? fields[3].trim() : "");
        movie.setRating(fields[4] != null ? fields[4].trim() : "");

        String scoreField = fields[5].trim();
        if (scoreField.startsWith("\"") && scoreField.endsWith("\"")) {
            scoreField = scoreField.substring(1, scoreField.length() - 1);
        }
        if (!scoreField.isEmpty()) {
            try {
                movie.setScore(Double.parseDouble(scoreField));
            } catch (NumberFormatException e) {
                movie.setValidRecord(false);
                movie.setNumberException(true);
                movie.setBadScore(scoreField);
                e.getMessage();
            }
        }

        movie.setDirector(fields[6] != null ? fields[6].trim() : "");
        movie.setActor1(fields[7] != null ? fields[7].trim() : "");
        movie.setActor2(fields[8] != null ? fields[8].trim() : "");
        movie.setActor3(fields[9] != null ? fields[9].trim() : "");

        // Handle Syntax errors
        movie.setValidRecord(HandleMovieSyntaxErrorException(movie, line));

        // Handle Semantic errors
        movie.setValidRecord(HandleMovieSemanticErrorException(movie, line, fields));

        return movie;
    }// end of createMovieObject

    /**
     * Handles syntax errors in a movie record.
     *
     * @param movie The Movie object to be validated.
     * @param line The line containing the movie record.
     * @return True if no syntax errors are found, false otherwise.
     * @throws SyntaxErrorException If a syntax error is detected.
     * @throws MissingQuotesException If quotes are missing in the movie record.
     * @throws MissingFieldException If a field is missing in the movie record.
     * @throws ExcessFieldException If there are excess fields in the movie record.
     */
    public static boolean HandleMovieSyntaxErrorException(Movie movie, String line) throws SyntaxErrorException, MissingQuotesException,
            MissingFieldException, ExcessFieldException {
        boolean quotedField = false;
        int fieldcount = 0;
        try {

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if (c == '"') {
                    // Changes to true when there's a quotation mark and back to false when there's a second one
                    quotedField = !quotedField;
                }
                // if the line had reached the end and the boolean hasn't turned back to false, it means there's a missing quote
                else if (i == line.length() - 1 && quotedField) {
                    movie.setValidRecord(false);
                    throw new MissingQuotesException("MissingQuotesException: Mixing closing quote");
                }

                if (c == ',') {
                    fieldcount++;
                }
            }

            if (fieldcount < 9) {
                movie.setValidRecord(false);
                throw new MissingFieldException("MissingFieldException: Missing field");
            }
            else if (fieldcount > 9) {
                movie.setValidRecord(false);
                throw new ExcessFieldException("ExcessFieldException: Excess field");
            }
        } catch (MissingFieldException | MissingQuotesException | ExcessFieldException e) {
            e.getMessage();
        }
        return movie.getValidRecord();
    }// end of HandleMovieSyntaxErrorException

    /**
     * Handles semantic errors in a movie record.
     *
     * @param movie The Movie object to be validated.
     * @param line The line containing the movie record.
     * @param fields An array containing the fields of the movie record.
     * @return True if no semantic errors are found, false otherwise.
     * @throws BadYearException If the year is invalid.
     * @throws BadDurationException If the duration is invalid.
     * @throws SemanticErrorException If a semantic error is detected.
     * @throws BadGenreException If the genre is invalid.
     * @throws BadScoreException If the score is invalid.
     * @throws BadRatingException If the rating is invalid.
     * @throws BadNameException If the name is invalid.
     */
    public static boolean HandleMovieSemanticErrorException(Movie movie, String line, String[] fields) throws BadYearException,
            BadDurationException, SemanticErrorException,
            BadGenreException, BadScoreException, BadDurationException,
            BadRatingException, BadNameException {

        try{
            for (int i = 0; i < fields.length; i++) {
                String field = fields[i];
                if (fields[i] != null) {
                    field = fields[i].trim();
                } else field = "";

                assert field != null;
                if (field.isEmpty()) {
                    switch(i) {
                        case 0 -> throw new SemanticErrorException("SemanticErrorException: missing year");
                        case 1 -> throw new SemanticErrorException("SemanticErrorException: missing title");
                        case 2 -> throw new SemanticErrorException("SemanticErrorException: missing duration");
                        case 3 -> throw new SemanticErrorException("SemanticErrorException: missing genre");
                        case 4 -> throw new SemanticErrorException("SemanticErrorException: missing rating");
                        case 5 -> throw new SemanticErrorException("SemanticErrorException: missing score");
                        case 6 -> throw new SemanticErrorException("SemanticErrorException: missing director");
                        case 7 -> throw new SemanticErrorException("SemanticErrorException: missing actor1");
                        case 8 -> throw new SemanticErrorException("SemanticErrorException: missing actor2");
                        case 9 -> throw new SemanticErrorException("SemanticErrorException: missing actor 3");
                    }
                    movie.setValidRecord(false);
                }
                // Validate year
                if (i == 0) {
                    try {
                        int year = Integer.parseInt(field);
                        if (year < 1990 || year > 1999) {
                            movie.setValidRecord(false);
                            throw new BadYearException("Invalid year");
                        }
                    } catch (NumberFormatException | SemanticErrorException e)  {
                        throw new BadYearException("Invalid year format " + field);
                    }
                }
                // Validate Duration
                else if (i == 2) {
                    try {
                        int duration = Integer.parseInt(field);
                        if (duration < 30 || duration > 300) {
                            movie.setValidRecord(false);
                            throw new BadDurationException("Invalid duration");
                        }
                    } catch (BadDurationException e) {
                        throw new BadDurationException("Invalid duration");
                    }
                }
                // Validate genre
                else if (i == 3) {
                    try {
                        String genre = field;
                        boolean isValidGenre = false;
                        String[] validGenres = {"Musical", "Comedy", "Animation", "Adventure", "Drama", "Crime", "Biography", "Horror", "Action",
                                "Documentary", "Fantasy", "Mystery", "Sci-fi", "Family", "Romance", "Thriller", "Western"};
                        for (String validGenre : validGenres) {
                            if (validGenre.equals(genre)) {
                                isValidGenre = true;
                                movie.setValidRecord(true);
                                break;
                            }
                        }
                        if (!isValidGenre) {
                            movie.setValidRecord(false);
                            throw new BadGenreException("Invalid genre");
                        }
                    } catch (BadGenreException e) {
                        throw new BadGenreException("Invalid genre");
                    }
                }
                // Validate rating
                else if (i == 4) {
                    try {
                        String rating = field;
                        boolean isValidRating = false;
                        String[] validRatings = {"PG", "Unrated", "G", "R", "PG-13", "NC-17"};
                        for (String validRating : validRatings) {
                            if (rating.equals(validRating)) {
                                isValidRating = true;
                            }
                        }
                        if (!isValidRating) {
                            movie.setValidRecord(false);
                            throw new BadRatingException("Invalid rating");
                        }
                    } catch (BadRatingException e) {
                        throw new BadRatingException("Invalid rating");
                    }
                }
                // Validate score
                else if (i == 5) {
                    try {
                        String scorefield = field;
                        if (!scorefield.isEmpty()) {
                            double score = Double.parseDouble(scorefield);
                            if (score < 0 || score > 10) {
                                movie.setValidRecord(false);
                                throw new BadScoreException("Invalid score");
                            }
                        }

                    } catch (NumberFormatException e) {
                        throw new BadScoreException("Invalid Score");
                    }
                }

            }
        } catch(SemanticErrorException e) {
            e.getMessage();
        }
        return movie.getValidRecord();
    }// end of HandleMovieSemanticErrorException

    /**
     * Creates CSV files for each genre.
     *
     * @throws IOException If an I/O error occurs while creating the files.
     */
    public static void createGenreFiles() throws IOException {
        BufferedWriter writer = null;
        String[] genres = {"Action", "Adventure", "Animation", "Biography", "Comedy", "Crime", "Documentary", "Drama", "Family",
        "Fantasy", "Horror", "Mystery", "Western", "Thriller",  "Romance", "Sci-fi", "Musical"};
        for (String genre : genres) {
            String filename = "src/Manifest/csv_files/" + genre.trim() + ".csv";
            try {
                writer = new BufferedWriter(new FileWriter(filename));
                writer.write("");
                writer.close();
            } catch(IOException e) {
                e.getMessage();
            }
        }
    }// end of createGenreFiles

    /**
     * Adds a movie to the corresponding CSV file for each genre.
     *
     * @param movie     The movie to add to the CSV file.
     * @param action    The number of action movies already in the Action CSV file.
     * @param adventure The number of adventure movies already in the Adventure CSV file.
     * @param animation The number of animation movies already in the Animation CSV file.
     * @param biography The number of biography movies already in the Biography CSV file.
     * @param comedy    The number of comedy movies already in the Comedy CSV file.
     * @param crime     The number of crime movies already in the Crime CSV file.
     * @param documentary The number of documentary movies already in the Documentary CSV file.
     * @param drama     The number of drama movies already in the Drama CSV file.
     * @param family    The number of family movies already in the Family CSV file.
     * @param fantasy   The number of fantasy movies already in the Fantasy CSV file.
     * @param horror    The number of horror movies already in the Horror CSV file.
     * @param mystery   The number of mystery movies already in the Mystery CSV file.
     * @param western   The number of western movies already in the Western CSV file.
     * @param thriller  The number of thriller movies already in the Thriller CSV file.
     * @param romance   The number of romance movies already in the Romance CSV file.
     * @param scifi     The number of sci-fi movies already in the Sci-fi CSV file.
     * @param musical   The number of musical movies already in the Musical CSV file.
     * @throws IOException If an I/O error occurs while writing to the CSV file.
     */
    public static void addMovieInGenreFile(Movie movie, int action, int adventure, int animation, int biography, int comedy,
                                           int crime, int documentary, int drama,
                                           int family, int fantasy, int horror, int mystery, int western, int thriller,  int romance,
                                           int scifi, int musical) throws IOException {
        String[] genres = movie.getGenres().split(",");
        BufferedWriter writer = null;
        for (String genre : genres) {
            String fileName = "src/Manifest/csv_files/" + genre.trim() + ".csv";
            try {
                if (movie.getGenres().equals("Action") && action == 0 || movie.getGenres().equals("Adventure") && adventure == 0 ||
                movie.getGenres().equals("Animation") && animation == 0 || movie.getGenres().equals("Biography") && biography == 0 ||
                movie.getGenres().equals("Comedy") && comedy == 0 || movie.getGenres().equals("Crime") && crime == 0 ||
                movie.getGenres().equals("Documentary") && documentary == 0 || movie.getGenres().equals("Drama") && drama == 0 ||
                movie.getGenres().equals("Family") && family == 0 || movie.getGenres().equals("Fantasy") && fantasy == 0 ||
                movie.getGenres().equals("Horror") && horror == 0 || movie.getGenres().equals("Mystery") && mystery == 0
                || movie.getGenres().equals("Western") && western == 0 || movie.getGenres().equals("Thriller") && thriller == 0
                || movie.getGenres().equals("Romance") && romance == 0 || movie.getGenres().equals("Sci-fi") && scifi == 0
                || movie.getGenres().equals("Musical") && musical == 0) {
                    writer = new BufferedWriter(new FileWriter(fileName));
                    writer.write(movie.toString() + "\n");
                    writer.close();
                } else {
                    writer = new BufferedWriter(new FileWriter(fileName, true));
                    writer.write(movie.toString() + "\n");
                    writer.close();
                }

            } catch (IOException e) {
                e.getMessage();
            }
        }

    }// end of addMovieInGenreFile

    /**
     * Adds a movie to the bad movie records file.
     *
     * @param movie The movie to add to the bad movie records file.
     * @param count The number of movies already in the bad movie records file.
     * @throws IOException If an I/O error occurs while writing to the bad movie records file.
     */
    public static void addMovieInBadRecordFile(Movie movie, int count) throws IOException {
        try {
            if (count == 0) {
                PrintWriter badMovieRecordsWriter = new PrintWriter(new FileWriter("src/Manifest/" + getBadMovieRecords()));
                badMovieRecordsWriter.println(movie.toString());
                badMovieRecordsWriter.close();
            }
            else {
                PrintWriter badMovieRecordsWriter = new PrintWriter(new FileWriter("src/Manifest/" + getBadMovieRecords(), true));
                badMovieRecordsWriter.println(movie.toString());
                badMovieRecordsWriter.close();
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }// end of addMovieInBadRecordFile
}// end of Part1