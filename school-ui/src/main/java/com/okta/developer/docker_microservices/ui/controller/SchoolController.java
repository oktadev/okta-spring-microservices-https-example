package com.okta.developer.docker_microservices.ui.controller;

import com.okta.developer.docker_microservices.ui.dto.TeachingClassDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class SchoolController {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final RestTemplate restTemplate;

    public SchoolController(OAuth2AuthorizedClientService clientService,
                            RestTemplate restTemplate) { // <1>
        this.authorizedClientService = clientService;
        this.restTemplate = restTemplate;
    }

    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/classes")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public ResponseEntity<List<TeachingClassDto>> listClasses(@AuthenticationPrincipal OAuth2AuthenticationToken authentication) { // <2>

        OAuth2AuthorizedClient authorizedClient =
            this.authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName()); // <3>

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();// <4>
        HttpHeaders headers = new HttpHeaders() {{
            set("Authorization", "Bearer " + accessToken.getTokenValue()); // <5>
        }};

        return restTemplate
            .exchange("https://school-service/class", HttpMethod.GET, new HttpEntity<String>(headers),
                new ParameterizedTypeReference<List<TeachingClassDto>>() {
                });
    }
}
