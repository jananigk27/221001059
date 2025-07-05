package com.example.amtAsgmnt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
        import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class amtService {



    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id).map(customer -> {
            customer.setReplaceBy(updatedCustomer.getReplaceBy());
            return customerRepository.save(customer);
        }).orElse(null);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    I
    public String shortenUrl(String longUrl, String customName) {
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "https://ulvis.net/api/v1/shorten";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("url", longUrl);
        bodyMap.put("custom", customName);
        bodyMap.put("private", "1");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(bodyMap, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> resBody = response.getBody();
            return resBody != null ? resBody.get("short").toString() : "Error: No response body";
        } else {
            return "Error: " + response.getStatusCode();
        }
    }
}
