package com.north47.offersdashboard;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class DashboardController {
    private RestTemplate restTemplate;
    private Validator validator;

    public DashboardController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @GetMapping("/dashboard")
    public ResponseEntity getStats() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<List<Offer>> offerResponse = restTemplate.exchange("http://localhost:8080/offers", HttpMethod.GET, new HttpEntity<>(httpHeaders.toSingleValueMap()), new ParameterizedTypeReference<List<Offer>>() {
        });

        if (!offerResponse.getStatusCode().is2xxSuccessful() || offerResponse.getBody() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bad response from offer service");
        }

        for (Offer offer : offerResponse.getBody()) {
            Set<ConstraintViolation<Offer>> constraintViolations = validator.validate(offer);
            if (!constraintViolations.isEmpty()) {
                return ResponseEntity.badRequest().body(constraintViolations.toString());
            }
        }

        List<String> offerNames = offerResponse.getBody().stream()
                .map(Offer::getOfferName)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new DashboardData(offerNames.size(), offerNames));
    }
}
