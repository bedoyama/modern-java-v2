package com.modernjava.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.net.http.HttpRequest.newBuilder;

public class MoviesClient {

    public static final String ALL_MOVIES_URL = "http://127.0.0.1:8000/explore-latest-java-features/src/main/resources/movies.json";
    public static final String MOVIE_BY_ID_URL = "http://127.0.0.1:8000/explore-latest-java-features/src/main/resources/movie_by_id.json";

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public MoviesClient() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static void main(String[] args) {
        MoviesClient moviesClient = new MoviesClient();
        int demoMovieId = 1;

        System.out.println("=== MoviesClient Demo ===");

        // 1) Sync: get a movie by id
        System.out.println("\n1) getMovieById(" + demoMovieId + ")");
        try {
            Movie movie = moviesClient.getMovieById(demoMovieId);
            System.out.println("Sync movie by id result: " + movie);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Sync movie by id interrupted: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Sync movie by id failed: " + e.getMessage());
        }

        // 2) Sync: get all movies
        System.out.println("\n2) getAllMovies()");
        try {
            List<Movie> movies = moviesClient.getAllMovies();
            System.out.println("Sync all movies count: " + movies.size());
            movies.stream().limit(3).forEach(m -> System.out.println("  - " + m.name()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Sync all movies interrupted: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Sync all movies failed: " + e.getMessage());
        }

        // 3) Async first: get a movie by id
        System.out.println("\n3) getMovieByIdAsync(" + demoMovieId + ")");
        try {
            Movie movieAsync = moviesClient.getMovieByIdAsync(demoMovieId).join();
            System.out.println("Async movie by id result: " + movieAsync);
        } catch (RuntimeException e) {
            System.out.println("Async movie by id failed: " + e.getMessage());
        }

        // 4) Async: get all movies
        System.out.println("\n4) getAllMoviesAsync()");
        try {
            List<Movie> moviesAsync = moviesClient.getAllMoviesAsync().join();
            System.out.println("Async all movies count: " + moviesAsync.size());
            moviesAsync.stream().limit(3).forEach(m -> System.out.println("  - " + m.name()));
        } catch (RuntimeException e) {
            System.out.println("Async all movies failed: " + e.getMessage());
        }
    }

    public Movie getMovieById(int movieId) throws IOException, InterruptedException {
        HttpRequest request = buildGetRequest(MOVIE_BY_ID_URL + "?movie_id=" + movieId);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return parseMovie(response.body());
    }

    public List<Movie> getAllMovies() throws IOException, InterruptedException {
        HttpRequest request = buildGetRequest(ALL_MOVIES_URL);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return parseMovies(response.body());
    }

    public CompletableFuture<Movie> getMovieByIdAsync(int movieId) {
        HttpRequest request = buildGetRequest(MOVIE_BY_ID_URL + "?movie_id=" + movieId);

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parseMovie);
    }

    public CompletableFuture<List<Movie>> getAllMoviesAsync() {
        HttpRequest request = buildGetRequest(ALL_MOVIES_URL);

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parseMovies);
    }

    private static HttpRequest buildGetRequest(String url) {
        return newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
    }

    private Movie parseMovie(String body) {
        try {
            return objectMapper.readValue(body, Movie.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Movie> parseMovies(String body) {
        try {
            return objectMapper.readValue(body, new TypeReference<List<Movie>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
