package com.modernjava.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Educational demo showing how to use async HTTP requests with the movies API example
 * in a realistic way.
 *
 * <p>The demo starts a tiny in-process HTTP server, then compares sequential versus
 * asynchronous loading of a featured movie plus the full catalog.</p>
 */
public class AdvancedAsyncMoviesDemo {

    static void main(String[] args) throws Exception {
        try (DemoApiServer server = DemoApiServer.start()) {
            MoviesClient client = new MoviesClient(server.allMoviesUrl(), server.movieByIdUrl());
            int featuredMovieId = 1;

            System.out.println("=== Advanced Async Movies Demo ===");
            System.out.println("Demo server: " + server.baseUrl());
            if (args.length > 0) {
                System.out.println("Ignoring CLI args: " + List.of(args));
            }
            System.out.println();

            runSequentialLoad(client, featuredMovieId);
            runAsyncLoad(client, featuredMovieId);
        }
    }

    static CompletableFuture<MovieDashboard> loadDashboardAsync(MoviesClient client, int featuredMovieId) {
        CompletableFuture<Movie> featuredMovieFuture = client.getMovieByIdAsync(featuredMovieId)
                .orTimeout(2, TimeUnit.SECONDS);

        CompletableFuture<List<Movie>> catalogFuture = client.getAllMoviesAsync()
                .orTimeout(2, TimeUnit.SECONDS);

        return featuredMovieFuture.thenCombine(catalogFuture, MovieDashboard::new);
    }

    private static MovieDashboard loadDashboardSequential(MoviesClient client, int featuredMovieId)
            throws IOException, InterruptedException {
        Movie featuredMovie = client.getMovieById(featuredMovieId);
        List<Movie> catalog = client.getAllMovies();
        return new MovieDashboard(featuredMovie, catalog);
    }

    private static void runSequentialLoad(MoviesClient client, int featuredMovieId)
            throws IOException, InterruptedException {
        System.out.println("1) Sequential loading (baseline)");
        Instant start = Instant.now();

        MovieDashboard dashboard = loadDashboardSequential(client, featuredMovieId);

        Duration elapsed = Duration.between(start, Instant.now());
        printDashboard(dashboard);
        System.out.println("Sequential load time: " + elapsed.toMillis() + "ms");
        System.out.println();
    }

    private static void runAsyncLoad(MoviesClient client, int featuredMovieId) {
        System.out.println("2) Async loading (parallel requests)");
        Instant start = Instant.now();

        MovieDashboard dashboard = loadDashboardAsync(client, featuredMovieId)
                .exceptionally(ex -> {
                    throw new RuntimeException("Failed to load dashboard asynchronously", ex);
                })
                .join();

        Duration elapsed = Duration.between(start, Instant.now());
        printDashboard(dashboard);
        System.out.println("Async load time: " + elapsed.toMillis() + "ms");
        System.out.println();
    }

    private static void printDashboard(MovieDashboard dashboard) {
        System.out.println("Featured movie: " + dashboard.featuredMovie().name()
                + " (" + dashboard.featuredMovie().year() + ")");
        System.out.println("Catalog count: " + dashboard.catalog().size());
        System.out.println("Catalog preview:");
        dashboard.catalog().stream()
                .limit(3)
                .forEach(movie -> System.out.println("  - " + movie.name()));
    }

    record MovieDashboard(Movie featuredMovie, List<Movie> catalog) {
    }

    private static final class DemoApiServer implements AutoCloseable {
        private final HttpServer server;
        private final String baseUrl;
        private final String movieByIdUrl;
        private final String allMoviesUrl;

        private DemoApiServer(HttpServer server, String baseUrl) {
            this.server = server;
            this.baseUrl = baseUrl;
            this.movieByIdUrl = baseUrl + "/movie_by_id.json";
            this.allMoviesUrl = baseUrl + "/movies.json";
        }

        static DemoApiServer start() throws IOException {
            HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);

            server.createContext("/movie_by_id.json", exchange -> respondWithDelay(exchange, 400, featuredMovieJson()));
            server.createContext("/movies.json", exchange -> respondWithDelay(exchange, 700, catalogJson()));

            server.start();
            String baseUrl = "http://127.0.0.1:" + server.getAddress().getPort();
            return new DemoApiServer(server, baseUrl);
        }

        String baseUrl() {
            return baseUrl;
        }

        String movieByIdUrl() {
            return movieByIdUrl;
        }

        String allMoviesUrl() {
            return allMoviesUrl;
        }

        @Override
        public void close() {
            server.stop(0);
        }

        private static void respondWithDelay(HttpExchange exchange, long delayMillis, String body) throws IOException {
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            byte[] response = body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(response);
            }
        }

        private static String featuredMovieJson() {
            return """
                    {
                      "movie_id": 1,
                      "name": "The Matrix",
                      "cast": "Keanu Reeves",
                      "year": 1999,
                      "release_date": "1999-03-31"
                    }
                    """;
        }

        private static String catalogJson() {
            return """
                    [
                      {
                        "movie_id": 1,
                        "name": "The Matrix",
                        "cast": "Keanu Reeves",
                        "year": 1999,
                        "release_date": "1999-03-31"
                      },
                      {
                        "movie_id": 2,
                        "name": "Inception",
                        "cast": "Leonardo DiCaprio",
                        "year": 2010,
                        "release_date": "2010-07-16"
                      },
                      {
                        "movie_id": 3,
                        "name": "Interstellar",
                        "cast": "Matthew McConaughey",
                        "year": 2014,
                        "release_date": "2014-11-07"
                      }
                    ]
                    """;
        }
    }
}

