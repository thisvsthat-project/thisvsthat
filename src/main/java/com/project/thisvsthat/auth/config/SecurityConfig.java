package com.project.thisvsthat.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 세션 관리 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 정적 리소스 접근 허용
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico", "/sm/**"
                        ).permitAll()

                        // 인증 없이 접근 가능
                        .requestMatchers(
                                "/", "/login", "/signup", "/auth/**"
                        ).permitAll()

                        // 인증 필요
                        .requestMatchers(
                                "/chat/{chatRoomId}",     // 채팅방 접근
//                                "/chat/**",             // 채팅방 접근
                                "/users",                 // 마이 페이지
                                "/post/create",           // 게시글 생성
                                "/post/{id}/update",      // 게시글 수정
                                "/post/{id}/delete",      // 게시글 삭제
                                "/post/{id}/voteFinished" // 투표 종료 처리
                        ).authenticated()

//                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                        .anyRequest().permitAll() // 나머지 요청은 인증 없이 접근 가능
                )

                // 인증이 필요할 경우 로그인 페이지로 리디렉션
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            // 원래 요청한 URL을 세션에 저장
                            String redirectUrl = request.getRequestURI();
                            request.getSession().setAttribute("redirectUrl", redirectUrl);
                            response.sendRedirect("/login"); // 로그인 페이지로 이동
                        })
                )

                // JWT 필터 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 URL 지정
                        .logoutSuccessUrl("/") // 로그아웃 후 이동할 URL
                        .deleteCookies("JSESSIONID", "jwt") // JWT 쿠키 삭제
                );

        return http.build();
    }

    /**
     * AuthenticationManager Bean 등록
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
