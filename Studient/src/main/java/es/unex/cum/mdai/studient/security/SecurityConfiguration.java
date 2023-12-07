package es.unex.cum.mdai.studient.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

//usando spring-boot con cualquiera de las dos anotaciones, o las dos, es suficiente. Dejo las dos por tradicion 
@Configuration 
@EnableWebSecurity 
//@Configuration Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime
public class SecurityConfiguration {// extends WebSecurityConfigurerAdapter (clase deprecated) https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter{

	@Autowired
	private AccessDeniedHandler customAccessDeniedHandler;	
	
	//configuración de Spring Security. Para definir como deben manejarse la autenticación y la autorización en la app-web.
	@Bean
	SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
    
    	System.out.println("\tSecurityConfiguration::filterChain(HttpSecurity http) ");
    	//Las llamadas a metodos devuelven un objeto HttpSecurity. 
    	//Se pueden encadenar metodos (and()) para configurar reglas de autorizacion de manera mas concisa.
    	http.authorizeHttpRequests()
                .antMatchers("/admin/**").hasRole("ADMIN") //para definir patrones de URL especificos y aplicar reglas de autorizacion a esos patrones.
                .antMatchers("/user/**").hasRole("USER")                                 
                .and() //nos permite encadenar configuraciones
                .formLogin()//loginPage por defecto proporcionada por Spring. Acceso mediante form: /login y /logout respectivamente. 
              	.and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler); //una vez logueado, si no es nuestro rol se lanzará la excepcion y mostraremos nuestra pag
    	
        return http.build();
				
    }
	//Ignoro la ruta de H2. No es necesario login. Esto es para poder acceder facilmente a la BD. 
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
    	System.out.println("\tSecurityConfiguration::webSecurityCustomizer() ");
    	return (web)->web
              .ignoring()
              .antMatchers("/h2-console/**"); //Permitir acceso sin login a la consola H2
    }

	//Creamos usuarios en memoria al arrancar la app-web y no necesitamos nada mas
	//Creamos dos usuarios user y admin, con los roles USER y ADMIN respectivamente
	//El usuario admin, tiene ambos roles
	//Encriptamos sus passwords llamando al @Bean del final
	@Bean
	InMemoryUserDetailsManager userDetailsService() {
    	System.out.println("\t SecurityConfiguration::userDetailsService() ");
    	
		UserDetails user = User.withUsername("user")
				.passwordEncoder(passwordEncoder()::encode).password("password")
				.roles("USER").build();
	    		
		UserDetails admin = User.withUsername("admin")
				.passwordEncoder(passwordEncoder()::encode).password("password")
				.roles("ADMIN","USER").build();

		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

		userDetailsManager.createUser(user);
		userDetailsManager.createUser(admin);
		
		return userDetailsManager;  			
    }

	//Encriptamos sus passwords usando BCryptPasswordEncoder
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}

