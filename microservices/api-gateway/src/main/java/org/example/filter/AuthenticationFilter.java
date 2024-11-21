package org.example.filter;

import org.apache.http.HttpHeaders;
import org.example.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory {

    @Autowired
    private RouterValidator routerValidator;
    @Autowired
    WebClient.Builder clientBuilder;


    private JwtService getJwtService() {
        return new JwtService();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (((exchange, chain) -> {
           if(routerValidator.isSecured.test(exchange.getRequest())) {
               if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Authorization header not present");
               }
               String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
               if(authHeader!=null && authHeader.startsWith("Bearer ")) {
                   authHeader=authHeader.replace("Bearer ", "");
               }
               try{
                   System.out.println(authHeader);
                  /*clientBuilder.build().get()
                          .uri("http://auth-service/auth/validate?token="+authHeader)
                          .retrieve().bodyToMono(String.class);*/
                   getJwtService().validateJwtToken(authHeader);
               }
               catch(Exception e){
                   System.out.println("invalid token");
                   throw new RuntimeException("invalid token");
               }
           }
            return chain.filter(exchange);
        }));
    }
}
