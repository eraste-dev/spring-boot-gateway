package com.eraste.orderservice.infrastructure.client;

import com.eraste.orderservice.infrastructure.adapter.in.web.dto.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

/**
 * HTTP Client for communicating with User Service.
 * <p>
 * This client fetches user information from the user-service microservice
 * to enrich order responses with user details.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class UserServiceClient {

    private static final Logger log = LoggerFactory.getLogger(UserServiceClient.class);

    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    /**
     * Constructs a UserServiceClient with the configured user service URL.
     *
     * @param restTemplate   the REST template for HTTP calls
     * @param userServiceUrl the base URL of the user service
     */
    public UserServiceClient(
            RestTemplate restTemplate,
            @Value("${services.user-service.url:http://localhost:8081}") String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    /**
     * Fetches user information by user ID.
     *
     * @param userId the user ID to fetch
     * @return an Optional containing UserInfo if found, empty otherwise
     */
    @SuppressWarnings("unchecked")
    public Optional<UserInfo> getUserById(Long userId) {
        try {
            String url = userServiceUrl + "/users/" + userId;
            log.debug("Fetching user from: {}", url);

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("data")) {
                Map<String, Object> data = (Map<String, Object>) response.get("data");
                UserInfo userInfo = mapToUserInfo(data);
                return Optional.of(userInfo);
            }

            return Optional.empty();
        } catch (RestClientException e) {
            log.warn("Failed to fetch user with ID {}: {}", userId, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Maps the API response data to a UserInfo object.
     *
     * @param data the response data map
     * @return the mapped UserInfo object
     */
    private UserInfo mapToUserInfo(Map<String, Object> data) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(data.get("id") != null ? ((Number) data.get("id")).longValue() : null);
        userInfo.setUsername((String) data.get("username"));
        userInfo.setEmail((String) data.get("email"));
        userInfo.setFirstName((String) data.get("firstName"));
        userInfo.setLastName((String) data.get("lastName"));
        return userInfo;
    }
}
