package cz.cvut.fit.tjv.foto.client.api_client;

import cz.cvut.fit.tjv.foto.client.model.Photographer;
import cz.cvut.fit.tjv.foto.client.model.PhotographerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
@Component
public class PhotographerClient {
    private String baseUrl;
    private RestClient photographerRestClient;
    private RestClient currentPhotographerRestClient;


    public PhotographerClient(@Value("${api.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        photographerRestClient = RestClient.create(baseUrl + "/photographer");
    }

    public void setCurrentPhotographer(Long id) {
        currentPhotographerRestClient = RestClient.builder()
                .baseUrl(baseUrl + "/photographer/{id}")
                .defaultUriVariables(Map.of("id", id))
                .build();
    }
    public Optional<Photographer> readOne() {
        try {
            return Optional.of(
                    currentPhotographerRestClient.get()
                            .retrieve().toEntity(Photographer.class).getBody()
            );
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
//        } catch (RestClientResponseException e) {
//            if (e.getStatusCode().)
        }
    }

    public Collection<Photographer> readAll() {
        return Arrays.asList(
                photographerRestClient.get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(Photographer[].class)
                        .getBody()
        );
    }

    public void create(PhotographerDto data) {
        data.setId(0L);
        photographerRestClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public void update(PhotographerDto formData) {
        currentPhotographerRestClient.put()
                .contentType(MediaType.APPLICATION_JSON)
                .body(formData)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(){
        currentPhotographerRestClient.delete()
                .retrieve().toBodilessEntity();
    }

}
