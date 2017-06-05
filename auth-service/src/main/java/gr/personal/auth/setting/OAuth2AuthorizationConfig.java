package gr.personal.auth.setting;

import gr.personal.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Created by Nick Kanakis on 4/6/2017.
 */


/*
* Authorization server is the one responsible for verifying credentials and if credentials are OK,
* providing the tokens[refresh-token as well as access-token]. It also contains information about registered
* clients and possible access scopes and grant types.
*
* */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private static final String STORY_SERVICE_PASSWORD = "storyServicePassword";
    private static final String USER_SERVICE_PASSWORD = "userServicePassword";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(new InMemoryTokenStore())
                .authenticationManager(authenticationManager) //Enable password grant type.
                .userDetailsService(userService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // TODO: persist clients details using MongoDB (or JdbcTokenStore)
        // TODO: get secrets by env or yml files

        /*
        * In the following lines we register 3 clients (browser, story-service, user-service).
        * Each has a scope (ui, server), that specifies the limits of the client. Grand type is the "method" by which
        * the client can acquire access tokens. There are 5 grants types (Authorization code, Implicit, Resource owner credentials,
        *Client credentials, Refresh token). Here we use:
        *
        * > Refresh Toke (refresh_token): Used to get a new access token without requiring the user to be redirected.
        * the client will send: (grant_type = refresh_token, refresh_token, client_id,  )
        *
        * > Client Credentials: Suitable for machine-to-machine authentication, The client sends a POST request with
        * following body parameters: (grant_type = client_credentials, client_id, client_secret, scope) the authorization server
        * respond will a json object containing: (token_type, expires_in , access_token, client_secret, scope)
        *
        *
        * > Resource owner credentials (password): This grant is a great user experience for trusted first party.
        * client will ask the user for their authorization credentials(username/pass). The client sends a post request
        * with (grant_type = password, client_id, client_secret, scope, username, password ) the authorization server will
        * respond with a json object containing (token_type, expires_in, access_token, refresh_token)
        * */

        clients.inMemory()
                .withClient("browser")
                .authorizedGrantTypes("refresh_token", "password")
                .scopes("ui")
                .and()
                .withClient("story-service")
                .secret(STORY_SERVICE_PASSWORD)
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("server")
                .autoApprove(true)
                .and()
                .withClient("user-service")
                .secret(USER_SERVICE_PASSWORD)
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("server")
                .autoApprove(true);
    }

    /*Defines the security constraints on the token endpoint*/
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

}
