package com.modernjava.http;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MoviesClientTest {

    private static HttpServer server;
    private static String baseUrl;

    private MoviesClient moviesClient;

    @BeforeAll
    static void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);

        server.createContext("/movie_by_id.json", exchange -> {
            String body = """
                    {
                      "movie_id": 1,
                      "name": "The Matrix",
                      "cast": "Keanu Reeves",
                      "year": 1999,
                      "release_date": "1999-03-31"
                    }
                    """;
            writeJson(exchange, body);
        });

        server.createContext("/movies.json", exchange -> {
            String body = """
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
                      }
                    ]
                    """;
            writeJson(exchange, body);
        });

        server.start();
        baseUrl = "http://127.0.0.1:" + server.getAddress().getPort();
    }

    @AfterAll
    static void stopServer() {
        if (server != null) {
            server.stop(0);
        }
    }

    @BeforeEach
    void setUpClient() {
        moviesClient = new MoviesClient(
                baseUrl + "/movies.json",
                baseUrl + "/movie_by_id.json"
        );
    }

    @Test
    void getMovieById_returnsMovie() throws Exception {
        Movie movie = moviesClient.getMovieById(1);

        assertNotNull(movie);
        assertEquals(1.0, movie.movie_id());
        assertEquals("The Matrix", movie.name());
        assertEquals(1999, movie.year());
    }

    @Test
    void getMovieByIdAsync_returnsMovie() {
        Movie movie = moviesClient.getMovieByIdAsync(1).join();

        assertNotNull(movie);
        assertEquals(1.0, movie.movie_id());
        assertEquals("The Matrix", movie.name());
    }

    @Test
    void getAllMovies_returnsMoviesList() throws Exception {
        List<Movie> movies = moviesClient.getAllMovies();

        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals("The Matrix", movies.get(0).name());
        assertEquals("Inception", movies.get(1).name());
    }

    @Test
    void getAllMoviesAsync_returnsMoviesList() {
        List<Movie> movies = moviesClient.getAllMoviesAsync().join();

        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals("The Matrix", movies.get(0).name());
    }

    private static void writeJson(com.sun.net.httpserver.HttpExchange exchange, String body) throws IOException {
        byte[] response = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(response);
        }
    }
}
