package com.inicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
AuthenticationManager auth;
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration conf) throws Exception{
		auth=conf.getAuthenticationManager();
		return auth;
	}
	 
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {
		http.csrf(cus->cus.disable())
		  .authorizeHttpRequests(auth-> auth
		  .requestMatchers(HttpMethod.POST, "/hotels/bookings","/flights/bookings").authenticated() //  Requiere autenticación
          .requestMatchers(HttpMethod.PUT, "/hotels/bookings/confirm").authenticated() //  Requiere autenticación
              .anyRequest().permitAll())
			.addFilter(new JWTAuthorizationFilter(auth));
		      return http.build();
		
	}
}
	/*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/flights", "/hotels").permitAll() // Cualquier usuario puede consultar
                .requestMatchers(HttpMethod.POST, "/hotels/bookings").authenticated() //  Requiere autenticación
                .requestMatchers(HttpMethod.PUT, "/hotels/bookings/confirm").authenticated() //  Requiere autenticación
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); //  Agregar filtro JWT
        return http.build();
    }*/
//}


