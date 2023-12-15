package es.unex.cum.mdai.studient.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import es.unex.cum.mdai.studient.model.Usuario;
import es.unex.cum.mdai.studient.repository.UsuarioRepository;

//usando spring-boot con cualquiera de las dos anotaciones, o las dos, es suficiente. Dejo las dos por tradicion 
@Configuration
@EnableWebSecurity
//@Configuration Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime
public class SecurityConfiguration{// extends WebSecurityConfigurerAdapter (clase deprecated)
									// https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter{

	@Autowired
	// TODO handler exceptions
	private AccessDeniedHandler customAccessDeniedHandler;

	// configuración de Spring Security. Para definir como deben manejarse la
	// autenticación y la autorización en la app-web.
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		System.out.println("\tSecurityConfiguration::filterChain(HttpSecurity http) ");
		// Las llamadas a metodos devuelven un objeto HttpSecurity.
		// Se usa para configurar reglas de autorizacion de manera mas concisa
		// FORMATO LAMBDA OBLIGATORIO PARA SPRING BOOT 3.2 Y SPRING SECURITY 6.2
		//
		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				// para definir patrones de URL especificos y aplicar reglas de autorizacion a
				// esos patrones.
				.requestMatchers("/user/**").authenticated()
				// .anyRequest().denyAll()
				.anyRequest().permitAll() // el resto de peticiones pueden ser realizadas sin login (index.html y hola)
		).exceptionHandling((exception) -> exception.accessDeniedHandler(customAccessDeniedHandler)) // deprecated o
																										// remove:
																										// .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
																										// //una vez
																										// logueado, si
																										// no es nuestro
																										// rol se
																										// lanzará la
																										// excepcion y
																										// mostraremos
																										// nuestra pag
				.formLogin(form -> form.loginPage("/login").permitAll()); // loginPage por defecto proporcionada por
																			// Spring. Acceso mediante form: /login y
																			// /logout respectivamente.
		// .formLogin(Customizer.withDefaults());
		return http.build();

	}

	// Ignoro la ruta de H2. No es necesario login. Esto es para poder acceder
	// facilmente a la BD.
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		System.out.println("\tSecurityConfiguration::webSecurityCustomizer() ");
		return (web) -> web.ignoring().requestMatchers("/h2-console/**"); // Permitir acceso sin login a la consola H2
	}

	// Creamos usuarios en memoria al arrancar la app-web y no necesitamos nada mas
	// Creamos dos usuarios user y admin, con los roles USER y ADMIN respectivamente
	// El usuario admin, tiene ambos roles
	// Encriptamos sus passwords llamando al @Bean del final

	/*@Bean
	CommandLineRunner initUsers(UsuarioRepository ur) {
		return args -> {
			ur.save(new Usuario("alvaro@gmail.com", "alvaro"));
			ur.save(new Usuario("pepe@gmail.com", "pepe"));
		};
	}*/

	@Bean
	UserDetailsService userService(UsuarioRepository repo, PasswordEncoder encoder) {
		// La lambda representa al único método de la interfaz UserDetailsService
		// (loadUserByUserme)
		return username -> repo.findByCorreo(username).get().asUser(encoder);
	}

//	@Bean
//	InMemoryUserDetailsManager userDetailsService() {
//    	System.out.println("\t SecurityConfiguration::userDetailsService() ");
//    	
//		UserDetails user = User.withUsername("alvaro@gmail.com")
//				.passwordEncoder(passwordEncoder()::encode).password("alvaro")
//				.roles("USER").build();
//
//		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//
//		userDetailsManager.createUser(user);
//		
//		return userDetailsManager;  			
//    }

	// Encriptamos sus passwords usando BCryptPasswordEncoder
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	            .build();
	}

}
