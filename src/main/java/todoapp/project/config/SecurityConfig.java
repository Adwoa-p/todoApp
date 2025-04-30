package todoapp.project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import todoapp.project.security.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll() // allow all requests
//                );
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/").permitAll();
                    auth.anyRequest().authenticated();
                        })
                .oauth2Login(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                ;
        return http.build();
    }

//        @Bean // at startup spring security looks  for a bean of type security filter chain. It configures all http security of the app
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//            http
//                    .csrf(AbstractHttpConfigurer::disable)
//                    .authorizeHttpRequests()
//                    .requestMatchers("/api/security/**")
//                    .permitAll()
//                    .anyRequest()
//                    .authenticated()
//                    .and()
//                    .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
//                    .authenticationProvider(authenticationProvider)
//                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//            return http.build();
//        }
}
