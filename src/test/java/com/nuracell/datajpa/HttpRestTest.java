package com.nuracell.datajpa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class HttpRestTest {
    private final String apiKey = System.getenv("NasaApiKey");
    private final String baseURL = "https://api.nasa.gov/planetary/apod";

    @Test
    public void test() {
        System.out.println(apiKey);
    }


    @Test
    public void httpTest() throws IOException, URISyntaxException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // use this instead of @JsonIgnoreProperties(ignoreUnknown = true) on the Apod class

        URI uri = buildURI();
        HttpResponse<String> response = getResponse(uri);

        Apod apod;

        if (uri.getQuery().contains("start_date") || uri.getQuery().contains("end_date")) {
            Apod[] apodArray = objectMapper.readValue(response.body(), Apod[].class);
            // As list
            // List<Apod> apodList =  objectMapper.readValue(response.body(), new TypeReference<List<Apod>>() {});
            // List<Apod> apodList =  objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructType(List.class, Apod.class)); // returns List of LinkedHashMap
            apod = apodArray[0];
        } else {
            apod = objectMapper.readValue(response.body(), Apod.class);
        }

        saveImg(apod);
    }

    public URI buildURI() {
        return new DefaultUriBuilderFactory(baseURL).builder()
                .queryParam("start_date", "2022-11-20")
                .queryParam("end_date", "2022-11-20")
                // .queryParam("count", 1)
                // .queryParam("thumbs", thumbs)
                .queryParam("api_key", apiKey)
                .build();
    }

    public HttpResponse<String> getResponse(URI uri) throws IOException, InterruptedException {
        System.out.println("HTTP GET request to URI: " + uri);

        // creating HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri/*new URI("?api_key=" + apiKey)*/)
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        // sending HTTP request and receiving response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\n" + response.body());

        return response;
    }

    public void saveImg(Apod apod) throws IOException {
        URL url = new URL(apod.url);

        if (url.getPath().endsWith(".jpg")) {
            String dirName = "NasaApodImages";
            File imagesDir = new File(dirName);
            if (!imagesDir.exists() && !imagesDir.isDirectory()) {
                System.out.println("imagesDir.mkdir() = " + imagesDir.mkdir());
            }

            String imgPathName = dirName + "/" + apod.title + ".jpg";
            File imgFile = new File(imgPathName);

            if (!imgFile.exists()) {
                try (InputStream in = new BufferedInputStream(url.openStream());
                     ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                    byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }

                    byte[] imgBytes = out.toByteArray();

                    FileOutputStream fos = new FileOutputStream(imgPathName);
                    fos.write(imgBytes);
                    fos.close();
                    System.out.println(apod.title + " image successfully saved");
                }
            } else {
                System.out.println(imgPathName + " already exists");
            }
        } else {
            System.out.println("It is not an image");
        }

/*
        FileOutputStream fos = new FileOutputStream("C://borrowed_image.jpg");
        fos.write(response);
        fos.close();*/
    }

    @ParameterizedTest
    @ValueSource(strings = "2022-11-01")
    public void httpArrayTest(String dateFrom) throws Exception {
        URI uri = buildURI(LocalDate.parse(dateFrom), LocalDate.now());
        HttpResponse<String> response = getResponse(uri);
        ObjectMapper objectMapper = new ObjectMapper();

        if (uri.getQuery().contains("start_date") || uri.getQuery().contains("end_date")) {
            List<Apod> apodList = objectMapper.readValue(response.body(), new TypeReference<List<Apod>>() {
            });
            for (Apod apod : apodList) {
                System.out.println(apod);
                saveImg(apod);
            }

        } else {
            Apod apod = objectMapper.readValue(response.body(), Apod.class);
            saveImg(apod);
        }
    }

    @ParameterizedTest
    @CsvSource({"2021-12-19,2021-12-21"})
    public void httpArrayTest(String dateFrom, String dateTo) throws Exception {
        URI uri = buildURI(dateFrom, dateTo);
        HttpResponse<String> response = getResponse(uri);
        ObjectMapper objectMapper = new ObjectMapper();

        if (uri.getQuery().contains("start_date") || uri.getQuery().contains("end_date")) {
            List<Apod> apodList = objectMapper.readValue(response.body(), new TypeReference<List<Apod>>() {
            });
            for (Apod apod : apodList) {
                System.out.println(apod);
                saveImg(apod);
            }

        } else {
            Apod apod = objectMapper.readValue(response.body(), Apod.class);
            saveImg(apod);
        }
    }

    private URI buildURI(String dateFrom, String dateTo) {
        return new DefaultUriBuilderFactory(baseURL).builder()
                .queryParam("start_date", LocalDate.parse(dateFrom))
                .queryParam("end_date", LocalDate.parse(dateTo))
                // .queryParam("count", 1)
                // .queryParam("thumbs", thumbs)
                .queryParam("api_key", apiKey)
                .build();
    }

    private URI buildURI(String date) {
        return new DefaultUriBuilderFactory(baseURL).builder()
                .queryParam("start_date", LocalDate.parse(date))
                .queryParam("end_date", LocalDate.parse(date))
                // .queryParam("count", 1)
                // .queryParam("thumbs", thumbs)
                .queryParam("api_key", apiKey)
                .build();
    }

    private URI buildURI(LocalDate of, LocalDate now) {
        return new DefaultUriBuilderFactory(baseURL).builder()
                .queryParam("start_date", of)
                .queryParam("end_date", now)
                // .queryParam("count", 1)
                // .queryParam("thumbs", thumbs)
                .queryParam("api_key", apiKey)
                .build();
    }

    private URI buildURI(LocalDate date) {
        return new DefaultUriBuilderFactory(baseURL).builder()
                .queryParam("start_date", date)
                .queryParam("end_date", date)
                // .queryParam("count", 1)
                // .queryParam("thumbs", thumbs)
                .queryParam("api_key", apiKey)
                .build();
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void httpArrayTest(LocalDate dateFrom, LocalDate dateTo) throws Exception {
        URI uri = buildURI(dateFrom, dateTo);
        HttpResponse<String> response = getResponse(uri);
        ObjectMapper objectMapper = new ObjectMapper();

        if (uri.getQuery().contains("start_date") || uri.getQuery().contains("end_date")) {
            List<Apod> apodList = objectMapper.readValue(response.body(), new TypeReference<List<Apod>>() {
            });
            for (Apod apod : apodList) {
                System.out.println(apod);
                saveImg(apod);
            }

        } else {
            Apod apod = objectMapper.readValue(response.body(), Apod.class);
            saveImg(apod);
        }
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(LocalDate.of(2020, 9, 5), LocalDate.of(2020, 9, 7))
        );
    }
}
