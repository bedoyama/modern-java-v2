package com.modernjava.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdvancedAsyncMoviesDemoTest {

    private static HttpServer server;
    private static String baseUrl;

    @BeforeAll
    static void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/movie_by_id.json", exchange -> writeJson(exchange, featuredMovieJson()));
        server.createContext("/movies.json", exchange -> writeJson(exchange, catalogJson()));
        server.start();
        baseUrl = "http://127.0.0.1:" + server.getAddress().getPort();
    }

    @AfterAll
    static void stopServer() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    void loadDashboardAsync_combinesFeaturedMovieAndCatalog() {
        MoviesClient client = new MoviesClient(baseUrl + "/movies.json", baseUrl + "/movie_by_id.json");

        AdvancedAsyncMoviesDemo.MovieDashboard dashboard = AdvancedAsyncMoviesDemo
                .loadDashboardAsync(client, 1)
                .join();

        assertNotNull(dashboard);
        assertEquals("The Matrix", dashboard.featuredMovie().name());
        assertEquals(3, dashboard.catalog().size());
        assertEquals("The Matrix", dashboard.catalog().get(0).name());
        assertEquals("Inception", dashboard.catalog().get(1).name());
    }

    private static void writeJson(HttpExchange exchange, String body) throws IOException {
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

