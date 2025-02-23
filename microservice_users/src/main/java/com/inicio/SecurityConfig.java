package com.inicio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

//	https://medium.com/@CodeWithTech/spring-security-with-jdbcuserdetailsmanager-and-custom-userdetailsservice-a-complete-guide-248ddce0196c}
// https://medium.com/@tarikboudaa/a-crud-application-secured-using-a-customized-spring-security-configuration-51a5a4ccda
			
		AuthenticationManager auth;
		
		@Bean
		public AuthenticationManager authManager(AuthenticationConfiguration conf) throws Exception{
			auth=conf.getAuthenticationManager();
			return auth;
		}
		
		
		@Bean
		public JdbcUserDetailsManager usersDetailsJdbc(PasswordEncoder passwordEncoder) {
			DriverManagerDataSource ds=new DriverManagerDataSource();
			ds.setDriverClassName("org.postgresql.Driver");
			ds.setUrl("jdbc:postgresql://localhost:5432/usuarios");
			ds.setUsername("admin");
			ds.setPassword("admin");
			JdbcUserDetailsManager jdbcDetails=new JdbcUserDetailsManager(ds);
			
			jdbcDetails.setUsersByUsernameQuery("select email, password, enabled"
	           	+ " from usuarios where email=?");
			jdbcDetails.setAuthoritiesByUsernameQuery("select u.email, r.rol "
	           	+ "from roles r JOIN user_roles j ON r.id=j.id_rol "
	           	+ "JOIN usuarios u ON j.id_usuario=u.id_usuario where u.email=?");

			return jdbcDetails;
		}
		@Bean
		public PasswordEncoder passwordEncoder() {
		    return new BCryptPasswordEncoder();
		}
		
		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		    return http
		        .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF para APIs REST
		        .authorizeHttpRequests(auth -> auth
		            .requestMatchers("/login").permitAll()  // Permitir login sin autenticación
		            .anyRequest().authenticated()  // Proteger todo lo demás
		        )
		        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // API sin sesiones
		        .build();
		}

}
	
