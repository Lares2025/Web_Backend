package last.lares.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import last.lares.global.config.filter.JwtFilter;
import last.lares.global.config.filter.LoginFilter;
import last.lares.global.config.security.CustomAccessDeniedHandler;
import last.lares.global.config.security.CustomAuthenticationEntryPoint;
import last.lares.global.config.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration configuration;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        AuthenticationManager manager = configuration.getAuthenticationManager();
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager manager = authenticationManager(configuration);

        LoginFilter loginFilter = new LoginFilter(manager, objectMapper, jwtUtil);
        loginFilter.setFilterProcessesUrl("/login");

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/user/register").permitAll()
                                .requestMatchers("/user/**").hasRole("ADMIN")

                                .requestMatchers("/robot/**").hasRole("ADMIN")

                                .requestMatchers("/control/img").permitAll()
                                .requestMatchers("/control/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/order/**").authenticated()
                                .requestMatchers("/order/**").hasRole("ADMIN")

                                .requestMatchers("/sig").permitAll()

                                .anyRequest().authenticated()
                )

                .addFilterBefore(new JwtFilter(objectMapper, jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(
                        ex -> ex
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper))
                                .accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper))
                )

                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
