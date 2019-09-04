package gr.efood.foodsearcher.services;

import gr.efood.foodsearcher.model.Address;
import gr.efood.foodsearcher.model.GeoCoordinates;
import gr.efood.foodsearcher.model.OpenCageResponse;
import gr.efood.foodsearcher.model.OpenCageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class OpenCageService implements GeoCodingService {

    @Value("${foodsearcher.geocoding.host}")
    private String host;

    @Value("${foodsearcher.geodocing.forward.uri}")
    private String uri;

    @Value("${foodsearcher.geodocing.api-key}")
    private String key;

    @Override
    public GeoCoordinates getCoordinates(Address address) {
        log.info("Getting GeoCoding coordinates for: [{}]", address);
        String query = String.format("%s %s, %s, %s",
                address.getStreetName(), address.getStreetNumber(), address.getPostalCode(), address.getSuburb());
        WebClient webClient = WebClient
                .builder()
                .baseUrl(host)
                .build();
        OpenCageResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(this.uri)
                        .queryParam("key", this.key)
                        .queryParam("q", query)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(OpenCageResponse.class)
                .block();
        log.info("OpenCageResponse [{}]", response);
        return mapResponseToCoordinates(response);
    }

    private GeoCoordinates mapResponseToCoordinates(OpenCageResponse openCageResponse) {
        if (openCageResponse == null || openCageResponse.getResults() == null
                || openCageResponse.getResults().isEmpty()) {
            return null;
        }
        OpenCageResult openCageResult = openCageResponse.getResults().get(0);
        if (openCageResult.getGeometry() == null) {
            return null;
        }
        GeoCoordinates geoCoordinates = new GeoCoordinates();
        geoCoordinates.setLat(openCageResult.getGeometry().getLat());
        geoCoordinates.setLng(openCageResult.getGeometry().getLng());
        return geoCoordinates;
    }
}
