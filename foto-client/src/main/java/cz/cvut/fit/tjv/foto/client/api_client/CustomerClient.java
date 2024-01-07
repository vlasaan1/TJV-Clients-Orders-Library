package cz.cvut.fit.tjv.foto.client.api_client;

import cz.cvut.fit.tjv.foto.client.model.CustomerDto;
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
public class CustomerClient {
    private final String baseUrl;
    private final RestClient customerRestClient;
    private RestClient currentCustomerRestClient;


    public CustomerClient(@Value("${api.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        customerRestClient = RestClient.create(baseUrl + "/customer");
    }

    public void setCurrentCustomer(Long id) {
        currentCustomerRestClient = RestClient.builder()
                .baseUrl(baseUrl + "/customer/{id}")
                .defaultUriVariables(Map.of("id", id))
                .build();
    }
    public Optional<CustomerDto> readOne() {
        try {
            return Optional.of(
                    currentCustomerRestClient.get()
                            .retrieve().toEntity(CustomerDto.class).getBody()
            );
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
//        } catch (RestClientResponseException e) {
//            if (e.getStatusCode().)
        }
    }

    public Collection<CustomerDto> readAll() {
        return Arrays.asList(
                customerRestClient.get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(CustomerDto[].class)
                        .getBody()
        );
    }

    public void create(CustomerDto data) {
        data.setId(0L);
        customerRestClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public void update(CustomerDto formData) {
        currentCustomerRestClient.put()
                .contentType(MediaType.APPLICATION_JSON)
                .body(formData)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(){
        currentCustomerRestClient.delete()
                .retrieve().toBodilessEntity();
    }

}
