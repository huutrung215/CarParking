package com.trycoder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.trycoder.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class UserConfig implements WebSecurityConfigurer {

	@Autowired
	public AuthenticationSuccessHandler customSuccessHandler;
	
	@Autowired
	private UserRepository userRepo;
	
	@Bean
	public UserDetailsService getUserDetailsService() {
	    return new UserDetailsServiceImpl(userRepo);
	}
	 
	// là một đối tượng encoder trong Spring Security được sử dụng để mã hóa mật khẩu người dùng trong quá trình xác thực
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } //mã hóa mật khẩu giúp bảo mật mật khẩu người dùng trong quá trình truyền tải và lưu trữ chúng
    
    //xác thực tài khoản người dùng bằng cách so sánh thông tin đăng nhập với thông tin trong cơ sở dữ liệu
    @Bean
	public DaoAuthenticationProvider getDaoAuthProvider(){//tạo ra một đối tượng DaoAuthenticationProvider và cấu hình cho nó các thuộc tính cần thiết
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getDaoAuthProvider());
	}

    //xác thực yêu cầu tới ứng dụng
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	// Cấu hình chi tiết cho việc xác thực yêu cầu tới ứng dụng
        http.authorizeRequests()
                .requestMatchers("/**").hasRole("ADMIN") //yêu cầu tới tất cả các đường dẫn trong ứng dụng yêu cầu người dùng có vai trò là "ADMIN" để được xác thực
                .requestMatchers("/user/**").hasRole("USER") // yêu cầu tới các đường dẫn bắt đầu bằng "/user" yêu cầu người dùng có vai trò là "USER" để được xác thực
                .requestMatchers("/**").permitAll() //Các yêu cầu tới tất cả các đường dẫn trong ứng dụng không yêu cầu xác thực
                .and()
                .formLogin().loginPage("/signin").loginProcessingUrl("/login") // Cấu hình xác thực bằng form. Trong đoạn code trên, form sẽ được trình diễn bởi trang "/signin" và sẽ được xử lý bởi "/login"
                .successHandler(customSuccessHandler) //Nếu xác thực thành công, người dùng sẽ được chuyển hướng đến trang được cấu hình trong phương thức customSuccessHandler.
                .and()
                .csrf().disable(); //Cấu hình tính năng bảo vệ chống giả mạo yêu cầu theo chuẩn CSRF (Cross-Site Request Forgery)
        http.headers().frameOptions().sameOrigin(); //Cấu hình cho phép truy cập iframe từ cùng một nguồn (same origin)
        
        return http.build();
    }

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/resources/**");
    }

	public void init(HttpSecurity builder) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(SecurityBuilder builder) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(SecurityBuilder builder) throws Exception {
		// TODO Auto-generated method stub
		
	}
}