package cz.cvut.fit.tjv.foto.client.api_client;

import cz.cvut.fit.tjv.foto.client.model.Order;
import cz.cvut.fit.tjv.foto.client.model.OrderDto;
import cz.cvut.fit.tjv.foto.client.model.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.*;

@Component
public class OrderClient {

    private String baseUrl;
    private RestClient orderRestClient;
    private RestClient currentOrderRestClient;


    public OrderClient(@Value("${api.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        orderRestClient = RestClient.create(baseUrl + "/order");
    }

    public void setCurrentOrder(Long id) {
        currentOrderRestClient = RestClient.builder()
                .baseUrl(baseUrl + "/order/{id}")
                .defaultUriVariables(Map.of("id", id))
                .build();
    }
    public Optional<Order> readOne() {
        try {
            return Optional.of(
                    currentOrderRestClient.get()
                            .retrieve().toEntity(Order.class).getBody()
            );
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }


    public Collection<Order> readAll(Range range) {
        var res = orderRestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("min",range.getMin())
                        .queryParam("max",range.getMax())
                        .build())
                .accept(MediaType.APPLICATION_JSON).retrieve().toEntity(Order[].class).getBody();
        if(res!=null)
            return Arrays.asList(res);
        return new HashSet<Order>();
    }

    public void create(OrderDto data) {
        data.setId(0L);
        orderRestClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public void update(OrderDto formData) {
        currentOrderRestClient.put()
                .contentType(MediaType.APPLICATION_JSON)
                .body(formData)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(){
        currentOrderRestClient.delete()
                .retrieve().toBodilessEntity();
    }


}
