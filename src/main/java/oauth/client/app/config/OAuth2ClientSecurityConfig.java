package oauth.client.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;

@Configuration
@EnableWebSecurity
public class OAuth2ClientSecurityConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(getClientRegistration());
    }

    @Bean
    public OAuth2AuthorizedClientService oauth2AuthorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }
    
    private ClientRegistration getClientRegistration() {
        return ClientRegistration.withRegistrationId("demo-client")

        .clientId("demo-app")
        .clientSecret("secret")
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUri("http://127.0.0.1:8082/login/oauth2/code/demo-client")
        .authorizationUri("http://localhost:8080/oauth2/authorize")
        .tokenUri("http://localhost:8080/oauth2/token")
        .userInfoUri("http://localhost:8080/userinfo")
        .jwkSetUri("http://localhost:8080/oauth2/jwks")
        .scope(OidcScopes.PROFILE)
        .scope(OidcScopes.OPENID)
        .build();

    }
}
