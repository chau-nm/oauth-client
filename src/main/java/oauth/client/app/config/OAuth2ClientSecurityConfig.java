package oauth.client.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class OAuth2ClientSecurityConfig {

    @Bean
    public SecurityFilterChain oauth2SecutiryFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> 
                authorize
                    .requestMatchers("/login/oauth2/code/demo-client")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )
            .oauth2Login(oauth2 -> 
                oauth2
                    .defaultSuccessUrl("/auth/oauth")
                    .failureUrl("/login?error")
            )
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        return http.build();
    }

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
        .userInfoUri("http://localhost:8080/oauth2/userInfo")
        .scope("all")
        .build();

    }
}
