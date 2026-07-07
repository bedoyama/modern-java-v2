package com.modernjava.streamgatherers;

import com.modernjava.streamgatherers.domain.Movie;
import com.modernjava.streamgatherers.domain.MovieGenre;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

/**
 * MovieDemo class demonstrating various Gatherer functions with MovieWithLocalDate
 * This class explores the complete Gatherer API including:
 * - Value
 * - GathererImpl
 * - Composite
 * - Gatherers() factory methods
 * - windowFixed(int)
 * - windowSliding(int)
 * - fold(Supplier, BiFunction)
 * - scan(Supplier, BiFunction)
 * - mapConcurrent(int, Function)
 */
public class StreamGatherersDemo {

    /**
     * Main entry point for demonstrating various Stream Gatherer operations.
     * This method creates a sample movie collection and sequentially demonstrates
     * all major gatherer functionalities including windowing, folding, scanning,
     * concurrent processing, and custom implementations.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("=== MovieDemo: Exploring Gatherer Functions ===\n");

        List<Movie> movies = createSampleMovies();

        System.out.println("Sample Movies:");
        movies.forEach(System.out::println);
        System.out.println();

        // Demonstrate all Gatherer functions
//        demonstrateWindowFixed(movies);
//        demonstrateWindowSliding(movies);
//        demonstrateFold(movies);
//        demonstrateScan(movies);

        // sequentials vs mapconcurrent
//        demonstrateSequentialMap(movies);
//        demonstrateMapConcurrent(movies);


        //composite gatherers
//        demonstrateCompositeGatherers(movies);

        //simple custom gatherers
//        demonstrateSimpleCustomGatherer(movies);

        //Advanced custom gatherers
        demonstrateTraditionalGrouping(movies);
        demonstrateCustomGathererImpl(movies);
    }

    /**
     * Demonstrates the windowFixed(int) gatherer operation which groups stream elements
     * into fixed-size, non-overlapping windows. This is useful for batch processing
     * where you need to process elements in chunks of a specific size.
     *
     * <p>Key Features:</p>
     * <ul>
     *   <li>Creates windows of exactly the specified size (except possibly the last window)</li>
     *   <li>Windows do not overlap</li>
     *   <li>Final window may contain fewer elements if stream size is not divisible by window size</li>
     *   <li>Memory efficient - processes one window at a time</li>
     * </ul>
     *
     * @param movies the list of movies to be processed in fixed-size windows
     */
    private static void demonstrateWindowFixed(List<Movie> movies) {
        System.out.println("=== windowFixed(3) - Fixed-size windows ===");
        movies.stream()
                .gather(Gatherers.windowFixed(3))
                .forEach(window -> {
                    System.out.println("Window of 3 movies:");
                    window.forEach(movie -> System.out.println("  - " + movie.title()));
                    System.out.println();
                });
    }

    /**
     * Demonstrates the windowSliding(int) gatherer operation which creates overlapping
     * windows of the specified size. Each window shares (n-1) elements with the next window,
     * making it ideal for trend analysis and comparing consecutive elements.
     *
     * <p>Key Features:</p>
     * <ul>
     *   <li>Creates overlapping windows where each window shares elements with adjacent windows</li>
     *   <li>Useful for analyzing trends and patterns across consecutive elements</li>
     *   <li>Produces more windows than fixed windowing for the same input</li>
     *   <li>Memory usage: O(window_size) as each window is processed independently</li>
     * </ul>
     *
     * @param movies the list of movies to be processed in sliding windows
     */
    private static void demonstrateWindowSliding(List<Movie> movies) {
        System.out.println("=== windowSliding(2) - Sliding windows ===");
        movies.stream()
                .limit(8)
                .gather(Gatherers.windowSliding(3))
                .forEach(window -> {
                    System.out.println("Sliding window:");
                    window.forEach(movie -> System.out.println("  - " + movie.title()));
                    System.out.println();
                });
    }

    /**
     * Demonstrates fold operations using the Gatherers.fold() method which reduces
     * an entire stream to a single accumulated value. This is similar to the reduce()
     * operation but provides more flexibility for complex accumulation patterns.
     *
     * <p>Examples demonstrated:</p>
     * <ul>
     *   <li>Calculating total duration of all movies</li>
     *   <li>Computing average rating across all movies</li>
     *   <li>Concatenating all movie titles into a single string</li>
     * </ul>
     *
     * <p>Key Characteristics:</p>
     * <ul>
     *   <li>Produces exactly one result value</li>
     *   <li>Requires an initializer and accumulator function</li>
     *   <li>Result is wrapped in Optional, requiring findFirst().orElse() to extract</li>
     * </ul>
     *
     * @param movies the list of movies to be folded into aggregate values
     */
    private static void demonstrateFold(List<Movie> movies) {
        System.out.println("=== fold() - Folding operations ===");

        int totalDuration = movies.stream()
                .gather(Gatherers.fold(() -> 0, (acc, movie) -> acc + movie.duration()))
                .findFirst()
                .orElse(0);

        double totalRating = movies.stream()
                .gather(Gatherers.fold(() -> 0.0, (acc, movie) -> acc + movie.rating()))
                .findFirst()
                .orElse(0.0);
        double averageRating = movies.isEmpty() ? 0.0 : totalRating / movies.size();

        String allTitles = movies.stream()
                .gather(Gatherers.fold(
                        () -> "",
                        (acc, movie) -> acc.isEmpty() ? movie.title() : acc + ", " + movie.title()))
                .findFirst()
                .orElse("");

        System.out.println("Total duration of all movies: " + totalDuration + " minutes");
        System.out.println("Average rating: " + String.format("%.2f", averageRating));
        System.out.println("All movie titles: " + allTitles);

        System.out.println();
    }

    /**
     * Demonstrates scan operations using Gatherers.scan() which produces intermediate
     * results at each step of the accumulation process. Unlike fold(), scan() emits
     * a result for each input element, showing the running state of the computation.
     *
     * <p>Examples demonstrated:</p>
     * <ul>
     *   <li>Running total of movie durations (cumulative sum)</li>
     *   <li>Running average of movie ratings (progressive average calculation)</li>
     * </ul>
     *
     * <p>Key Characteristics:</p>
     * <ul>
     *   <li>Produces one output for each input element</li>
     *   <li>Shows intermediate states of accumulation</li>
     *   <li>Useful for progress tracking and incremental analysis</li>
     *   <li>Can use external variables (like AtomicInteger) for complex calculations</li>
     * </ul>
     *
     * @param movies the list of movies to be scanned for running calculations
     */
    private static void demonstrateScan(List<Movie> movies) {
        System.out.println("=== scan() - Scanning operations ===");
        System.out.println("scan emits intermediate values for each element, unlike fold which emits only the final result.");

        System.out.println("Running duration totals:");
        movies.stream()
                .gather(Gatherers.scan(() -> 0, (acc, movie) -> acc + movie.duration()))
                .forEach(total -> System.out.println("Running total: " + total + " minutes"));

        AtomicInteger ratingCounter = new AtomicInteger(0);
        ratingCounter.set(0); // Reset counter before starting a different scan operation.

        System.out.println("Running rating averages:");
        movies.stream()
                .gather(Gatherers.scan(
                        () -> 0.0,
                        (acc, movie) -> {
                            int count = ratingCounter.incrementAndGet();
                            return ((acc * (count - 1)) + movie.rating()) / count;
                        }))
                .forEach(avg -> System.out.println("Running average: " + String.format("%.2f", avg)));

        System.out.println();
    }

    /**
     * Demonstrates concurrent processing using Gatherers.mapConcurrent() which processes
     * stream elements in parallel using a specified number of concurrent threads.
     * This is particularly useful for expensive operations like I/O, network calls,
     * or CPU-intensive computations while maintaining stream order.
     *
     * <p>Key Features:</p>
     * <ul>
     *   <li>Processes elements concurrently using specified thread count</li>
     *   <li>Maintains original stream order in output</li>
     *   <li>Ideal for I/O-bound or CPU-intensive transformations</li>
     *   <li>Includes timing measurements to demonstrate performance benefits</li>
     * </ul>
     *
     * <p>Performance Considerations:</p>
     * <ul>
     *   <li>Thread pool size should match the nature of work (I/O vs CPU bound)</li>
     *   <li>Overhead of thread management may not benefit small datasets</li>
     *   <li>Always handle InterruptedException properly</li>
     * </ul>
     *
     * @param movies the list of movies to be processed concurrently
     */
    private static void demonstrateMapConcurrent(List<Movie> movies) {
        System.out.println("=== mapConcurrent(2) - Concurrent processing ===");

        long startTime = System.currentTimeMillis();

        movies.stream()
                .gather(Gatherers.mapConcurrent(6, movie -> {
                    try {
                        Thread.sleep(100); // Simulate expensive work per movie.
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    return Map.entry(
                            movie.title(),
                            "Processed: " + movie.genre() + " (" + movie.duration() + "min, " + movie.rating() + "★)"
                    );
                }))
                .forEach(result -> System.out.println(result.getKey() + " -> " + result.getValue()));

        long endTime = System.currentTimeMillis();
        System.out.println("Concurrent processing time: " + (endTime - startTime) + "ms");
        System.out.println();
    }

    /**
     * Demonstrates traditional sequential processing using the standard map() operation
     * as a comparison baseline for the concurrent processing approach. This method
     * performs the same transformation as mapConcurrent() but in a single thread,
     * allowing for performance comparison.
     *
     * <p>Purpose:</p>
     * <ul>
     *   <li>Provides performance baseline for comparison with concurrent processing</li>
     *   <li>Shows identical transformation logic in sequential context</li>
     *   <li>Includes timing measurements for direct performance comparison</li>
     *   <li>Demonstrates when concurrent processing provides benefits</li>
     * </ul>
     *
     * @param movies the list of movies to be processed sequentially
     */
    private static void demonstrateSequentialMap(List<Movie> movies) {
        System.out.println("=== Sequential map() - Traditional processing ===");

        long startTime = System.currentTimeMillis();

        // Process movies sequentially to simulate expensive operations
        movies.stream()
                .map(movie -> {
                    // Simulate some processing time (same as concurrent version)
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    // Return a processed result (same format as concurrent version)
                    return Map.entry(
                            movie.title(),
                            "Processed: " + movie.genre() + " (" + movie.duration() + "min, " + movie.rating() + "★)"
                    );
                })
                .forEach(result ->
                        System.out.println(result.getKey() + " -> " + result.getValue()));

        long endTime = System.currentTimeMillis();
        System.out.println("Sequential processing time: " + (endTime - startTime) + "ms");
        System.out.println();
    }


    /**
     * Demonstrates composite gatherer operations by chaining multiple gatherers together
     * to create sophisticated data processing pipelines. This example shows how filtering,
     * windowing, and scanning can be combined for complex analytics scenarios.
     *
     * <p>Pipeline Operations:</p>
     * <ol>
     *   <li>Filter high-rated movies (rating >= 8.0)</li>
     *   <li>Group filtered movies into fixed windows of size 2</li>
     *   <li>Calculate average duration for each window using scan operation</li>
     * </ol>
     *
     * <p>Real-world Applications:</p>
     * <ul>
     *   <li>Data analytics pipelines for business intelligence</li>
     *   <li>Content recommendation systems</li>
     *   <li>Quality assessment and engagement analysis</li>
     *   <li>Batch processing workflows</li>
     * </ul>
     *
     * @param movies the list of movies to be processed through the composite pipeline
     */
    private static void demonstrateCompositeGatherers(List<Movie> movies) {
        System.out.println("=== Composite Gatherers - Chaining operations ===");
        System.out.println("Pipeline: filter(rating >= 8.0) → windowFixed(2) → scan(avg duration per window)");
        System.out.println();

        // Stage 1: filter high-rated movies
        // Stage 2: group them into non-overlapping windows of 2
        // Stage 3: scan each window to compute its average duration
        movies.stream()
                .filter(movie -> movie.rating() >= 8.0)
                .gather(Gatherers.windowFixed(2))
                .gather(Gatherers.scan(
                        () -> 0.0,
                        (acc, window) -> window.stream()
                                .mapToInt(Movie::duration)
                                .average()
                                .orElse(0.0)))
                .forEach(avg -> System.out.printf(
                        "High-rated movie window average duration: %.1f minutes%n", avg));

        System.out.println();

    }

    /**
     * Demonstrates a simple custom gatherer that combines filtering and transformation
     * in a single operation. This stateless gatherer filters high-rated movies and
     * transforms them into formatted summary strings, showcasing basic custom gatherer patterns.
     *
     * <p>Functionality:</p>
     * <ul>
     *   <li>Filters movies with rating >= 8.5</li>
     *   <li>Transforms qualifying movies into formatted summary strings</li>
     *   <li>Uses stateless design (Void state type)</li>
     *   <li>Demonstrates basic Gatherer.of() usage patterns</li>
     * </ul>
     *
     * <p>Design Patterns:</p>
     * <ul>
     *   <li>Single-responsibility principle (filter + transform)</li>
     *   <li>Stateless operation for simplicity</li>
     *   <li>Immediate downstream pushing without accumulation</li>
     *   <li>Comparison with equivalent traditional stream operations</li>
     * </ul>
     *
     * @param movies the list of movies to be filtered and transformed
     */
    private static void demonstrateSimpleCustomGatherer(List<Movie> movies) {
        System.out.println("=== Simple Custom Gatherer.of() - Filter & Transform ===");

        // Compare with traditional approach
        System.out.println("Traditional approach (filter + map):");
        movies.stream()
                .filter(movie -> movie.rating() >= 8.5)
                .map(movie -> String.format("⭐ %s (%d) - %.1f★ [%s]",
                        movie.title(),
                        movie.getReleaseYear(),
                        movie.rating(),
                        movie.genre()))
                .forEach(summary -> System.out.println("  " + summary));

        System.out.println();

        // Custom stateless gatherer using Gatherer.of(integrator)
        // State type is Void — no accumulation, each element is handled independently.
        // The integrator receives (state, element, downstream):
        //   - returns true  → continue processing more elements
        //   - returns false → short-circuit and stop the stream
        Gatherer<Movie, Void, String> highRatedSummaryGatherer = Gatherer.of(
                Gatherer.Integrator.ofGreedy((Void state, Movie movie, Gatherer.Downstream<? super String> downstream) -> {
                            if (movie.rating() >= 8.5) {
                                String summary = String.format("⭐ %s (%d) - %.1f★ [%s]",
                                        movie.title(),
                                        movie.getReleaseYear(),
                                        movie.rating(),
                                        movie.genre());
                                return downstream.push(summary); // push result and continue
                            }
                            return true; // skip low-rated movies, keep going
                        }
                ));

        System.out.println("Custom Gatherer.of() approach (filter & transform in one step):");
        movies.stream()
                .gather(highRatedSummaryGatherer)
                .forEach(summary -> System.out.println("  " + summary));

        System.out.println();
    }

    /**
     * Demonstrates traditional stream operations that achieve the same result as the
     * custom gatherer implementation without using the Gatherer API. This provides
     * a comparison between gatherer-based and collector-based approaches for grouping operations.
     *
     * <p>Traditional Approach:</p>
     * <ul>
     *   <li>Uses Collectors.groupingBy() for decade-based grouping</li>
     *   <li>Applies sorting for consistent output ordering</li>
     *   <li>Shows equivalent functionality with standard stream operations</li>
     * </ul>
     *
     * <p>Comparison Points:</p>
     * <ul>
     *   <li>Code complexity and readability</li>
     *   <li>Performance characteristics</li>
     *   <li>Reusability and composability</li>
     *   <li>Type safety and compile-time checking</li>
     * </ul>
     *
     * @param movies the list of movies to be grouped using traditional collectors
     */
    private static void demonstrateTraditionalGrouping(List<Movie> movies) {
        System.out.println("=== Traditional Stream Grouping (without Gatherer) ===");

        // Group movies by decade using traditional collectors
        Map<String, List<Movie>> moviesByDecade = movies.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        movie -> {
                            int decade = (movie.getReleaseYear() / 10) * 10;
                            return decade + "s";
                        }
                ));
        System.out.println("Movies grouped by decade:" + moviesByDecade);

        System.out.println("Movies grouped by decade:");
        moviesByDecade.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey()) // Sort decades for consistent output
                .forEach(entry -> {
                    System.out.println("Decade: " + entry.getKey());
                    entry.getValue().forEach(movie ->
                            System.out.println("  - " + movie.title() + " (" + movie.getReleaseYear() + ")"));
                    System.out.println();
                });
    }


    /**
     * Demonstrates custom gatherer implementation using Gatherer.ofSequential() to create
     * domain-specific gathering operations. This example groups movies by decade,
     * showing how to implement stateful gathering with custom business logic.
     *
     * <p>Custom Gatherer Components:</p>
     * <ul>
     *   <li><strong>Initializer:</strong> Creates a HashMap to store movies by decade</li>
     *   <li><strong>Integrator:</strong> Processes each movie and groups by calculated decade</li>
     *   <li><strong>Finisher:</strong> Outputs the grouped results as Map.Entry objects</li>
     * </ul>
     *
     * <p>Business Logic:</p>
     * <ul>
     *   <li>Calculates decade from movie release year (e.g., 1994 -> 1990s)</li>
     *   <li>Groups movies into decade-based collections</li>
     *   <li>Provides structured output for further processing</li>
     * </ul>
     *
     * @param movies the list of movies to be grouped by decade
     */
    private static void demonstrateCustomGathererImpl(List<Movie> movies) {
        System.out.println("=== Custom Gatherer Implementation ===");

        // Gatherer.ofSequential() builds a stateful, sequential-only gatherer.
        // Type parameters: <T, A, R>
        //   T = Movie        — input element type
        //   A = HashMap<...> — mutable state type built by the initializer
        //   R = Map.Entry<String, List<Movie>> — output type emitted by the finisher
        Gatherer<Movie, HashMap<String, List<Movie>>, Map.Entry<String, List<Movie>>> moviesByDecadeGatherer =
                Gatherer.ofSequential(
                        // Initializer: creates the state (a fresh map) once per stream
                        HashMap::new,

                        // Integrator: called for each Movie; groups it into the map by decade.
                        // Returns true to continue processing every element.
                        Gatherer.Integrator.ofGreedy((state, movie, downstream) -> {
                            int decade = (movie.getReleaseYear() / 10) * 10;
                            String decadeKey = decade + "s";
                            state.computeIfAbsent(decadeKey, k -> new java.util.ArrayList<>()).add(movie);
                            return true;
                        }),

                        // Finisher: called once after all elements are processed.
                        // Pushes every collected Map.Entry downstream so the stream can iterate them.
                        (state, downstream) -> state.entrySet().forEach(downstream::push)
                );

        System.out.println("Movies grouped by decade (custom gatherer):");
        movies.stream()
                .gather(moviesByDecadeGatherer)
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    System.out.println("Decade: " + entry.getKey());
                    entry.getValue().forEach(movie ->
                            System.out.println("  - " + movie.title() + " (" + movie.getReleaseYear() + ")"));
                    System.out.println();
                });
    }


    /**
     * Creates and returns a comprehensive sample dataset of movies for demonstration purposes.
     * The dataset includes diverse movies from different decades, genres, ratings, and durations
     * to provide meaningful examples for all gatherer operations.
     *
     * <p>Dataset Characteristics:</p>
     * <ul>
     *   <li><strong>Time Range:</strong> Movies from 1940s to 2010s (8 decades)</li>
     *   <li><strong>Genres:</strong> Drama, Action, Science Fiction, Romance</li>
     *   <li><strong>Ratings:</strong> Range from 8.3 to 9.3 (high-quality films)</li>
     *   <li><strong>Durations:</strong> Range from 102 to 195 minutes</li>
     *   <li><strong>Count:</strong> 12 carefully selected movies</li>
     * </ul>
     *
     * <p>Includes Classic Films:</p>
     * <ul>
     *   <li>The Godfather, Casablanca, Citizen Kane (classics)</li>
     *   <li>The Shawshank Redemption, Pulp Fiction (modern classics)</li>
     *   <li>Inception, The Matrix, The Dark Knight (contemporary hits)</li>
     * </ul>
     *
     * @return an immutable list of Movie objects representing a diverse film collection
     */
    private static List<Movie> createSampleMovies() {
        return List.of(
                new Movie("The Godfather", MovieGenre.DRAMA, LocalDate.of(1972, 3, 24), 9.2, 175),
                new Movie("The Shawshank Redemption", MovieGenre.DRAMA, LocalDate.of(1994, 9, 23), 9.3, 142),
                new Movie("Pulp Fiction", MovieGenre.DRAMA, LocalDate.of(1994, 10, 14), 8.9, 154),
                new Movie("The Dark Knight", MovieGenre.ACTION, LocalDate.of(2008, 7, 18), 9.0, 152),
                new Movie("Schindler's List", MovieGenre.DRAMA, LocalDate.of(1993, 12, 15), 9.0, 195),
                new Movie("Forrest Gump", MovieGenre.DRAMA, LocalDate.of(1994, 7, 6), 8.8, 142),
                new Movie("Inception", MovieGenre.SCIENCE_FICTION, LocalDate.of(2010, 7, 16), 8.8, 148),
                new Movie("The Matrix", MovieGenre.SCIENCE_FICTION, LocalDate.of(1999, 3, 31), 8.7, 136),
                new Movie("Goodfellas", MovieGenre.DRAMA, LocalDate.of(1990, 9, 19), 8.7, 146),
                new Movie("Star Wars: A New Hope", MovieGenre.SCIENCE_FICTION, LocalDate.of(1977, 5, 25), 8.6, 121),
                new Movie("Casablanca", MovieGenre.ROMANCE, LocalDate.of(1942, 11, 26), 8.5, 102),
                new Movie("Citizen Kane", MovieGenre.DRAMA, LocalDate.of(1941, 5, 1), 8.3, 119)
        );
    }
}
