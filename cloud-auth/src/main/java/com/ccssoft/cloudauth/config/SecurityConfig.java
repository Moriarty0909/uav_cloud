package com.ccssoft.cloudauth.config;

import com.ccssoft.cloudauth.exception.JWTAccessDeniedHandler;
import com.ccssoft.cloudauth.exception.JWTAuthenticationEntryPoint;
import com.ccssoft.cloudauth.filter.JWTAuthenticationFilter;
import com.ccssoft.cloudauth.filter.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author moriarty
 * @date 2020/5/19 17:21
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    /**
     * 加密密码
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //security的认证功能
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    //security的授权功能
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()//关闭跨域防护

                .authorizeRequests()//开启认证请求
//
                // 登录相关放行
                .antMatchers("/consumer/admin/verificationCode").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/consumer/admin/login").permitAll()
                .antMatchers("/consumer/admin/authenticate").permitAll()
                .antMatchers("/consumer/admin/registerUser").permitAll()
                //限制好各功能模块
                .antMatchers("/consumer/admin/deleteUser/**").hasRole("ADMIN")
                .antMatchers("/consumer/airspace/approvalAirspace/**").hasRole("ADMIN")
                .antMatchers("/consumer/task/approvalTask/**").hasRole("ADMIN")

                .antMatchers("/consumer/**").authenticated()
//                .antMatchers(HttpMethod.DELETE, "/tasks/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()

                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //添加没登录时的处理
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                //添加无权限时的处理
                .accessDeniedHandler(new JWTAccessDeniedHandler());

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
