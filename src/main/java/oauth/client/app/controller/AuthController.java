package oauth.client.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private OAuth2AuthorizedClientService oauth2AuthorizedClientService;

    @GetMapping("/oauth")
    public String oauth2SignIn(Authentication authentication) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        String accessToken = oauth2AuthorizedClientService
            .loadAuthorizedClient("demo-client", authentication.getName())
            .getAccessToken().getTokenValue();
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response =
            restTemplate.exchange("http://localhost:8081/hello", HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

}
