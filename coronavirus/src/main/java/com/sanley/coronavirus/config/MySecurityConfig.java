package com.sanley.coronavirus.config;

import com.sanley.coronavirus.service.MyUserDetailService;
import net.sf.jsqlparser.statement.create.table.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private MyAccessDenied myAccessDenied;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/index/").permitAll()
                .antMatchers("/checkOut/**").hasRole("User")
                .antMatchers("/manager/**/","/patient/**","/touch/**","/cure/**","/dead/**").hasRole("Admin");

        http.exceptionHandling().accessDeniedHandler(myAccessDenied);
        //异常处理采用 MyAccessDenied

        //开启自动配置的登录功能
        http.formLogin();

        //关闭csrf保护功能
        http.csrf().disable();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        //开启记住我功能
    }
    //AuthentictionManagerBuilder认证管理器
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                //验证
                //AuthenticationManagerBuilder 身份验证管理器
                //BCryptPasswordEncoder()加密类
                //创建内存用户n-user
                .withUser("Sanley")
                //设置密码为123456
     .password( new BCryptPasswordEncoder().encode("123456"))
                .roles("User","Admin");
        auth.authenticationProvider(authenticationProvider());
    }           //身份验证提供程序
    public DaoAuthenticationProvider authenticationProvider(){
        //这个方法用于回应MyUserDetailService.java
        // DaoAuthenticationProvider 是用于解析并认证 UsernamePasswordAuthenticationToken 的一个认证服务提供者。
        //通过Authenticationdao返回user和admin。
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(myUserDetailService);
        return  authenticationProvider;
    }
}
