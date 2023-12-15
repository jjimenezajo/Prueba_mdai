package es.unex.cum.mdai.studient.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.FormularioCarpeta;
import es.unex.cum.mdai.studient.model.FormularioTarea;
import es.unex.cum.mdai.studient.model.Tarea;
import es.unex.cum.mdai.studient.model.Usuario;
import es.unex.cum.mdai.studient.services.CarpetaService;
import es.unex.cum.mdai.studient.services.TareaService;
import es.unex.cum.mdai.studient.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/** CONTROLADOR DE LAS PANTALLAS DE AUTENTICACIÓN DEL USUARIO **/
@Controller
public class AuthController {

	private UsuarioService us;
	private CarpetaService cs;
	private TareaService ts;
    private AuthenticationManager authenticationManager;

	public AuthController(UsuarioService us, CarpetaService cs, TareaService ts, AuthenticationManager authenticationManager) {
		System.out.println("\t AuthController builder");
		this.us = us;
		this.cs = cs;
		this.ts = ts;
		this.authenticationManager=authenticationManager;
	}
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("usuario", new Usuario());
		System.out.println("Controller de /");
		return "redirect:/user/perfil/2";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());
		System.out.println("Controller de /");
		return "login";
	}

	@PostMapping("/doLogin")
	public String doLogin(HttpServletRequest req, Usuario usuario, Model model, RedirectAttributes attributes) {
		System.out.println("Iniciar sesión");
		Optional<Usuario> op = us.findUsuarioByCorreo(usuario.getCorreo());
		// El usuario no existe
		if (op.isEmpty()) {
			System.out.println("No lo encuentra");
			String mensaje = ("El usuario introducido no existe");
			boolean mostrarAlerta = true;
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("mostrarAlerta", mostrarAlerta);
			return "login";
		} else {// El usuario existe
			String contr = op.get().getContrasena();
			if (contr.equals(usuario.getContrasena())) {// El usuario es correcto por la contraseña
				Usuario logged_user = op.get();
				
				//AUTENTICACIÓN
				// Crea un token de autenticación con el nombre de usuario y la contraseña recibidos
	            
//
//	            // Realiza la autenticación llamando al AuthenticationManager
	        
//
//	            // Establece la autenticación en el contexto de seguridad
               
//	            
//	         // Obtiene el contexto de seguridad actual
	           
//	            
//	         // Obtiene la información de autenticación del contexto
	//			Authentication authentication2 = securityContext.getAuthentication();
	            
	//            System.out.println("Authentication: {}"+ authentication2);

	            // Verifica si el usuario está autenticado
	//            boolean isAuthenticated = authentication2.isAuthenticated();
                
				Authentication authentication = new UsernamePasswordAuthenticationToken(logged_user.getCorreo(), logged_user.getContrasena());
	            Authentication authenticated = authenticationManager.authenticate(authentication);
	            SecurityContextHolder.getContext().setAuthentication(authenticated);
                SecurityContext securityContext = SecurityContextHolder.getContext();
	            HttpSession session = req.getSession(true);
	            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
	            
	         // Imprime el resultado
	    //        System.out.println("¿El usuario está autenticado? " + isAuthenticated);
	            
//	            UserDetails userDetails = (UserDetails) authenticated.getPrincipal();
//	            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//	            authorities.forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));
	         // CARGAMOS EL MODELO CON LA INFORMACIÓN DEL USUARIO PARA MOSTRAR SU PANEL DE
	    		// GESTIÓN
	    		System.out.println("El usuario con id " + logged_user.getId() + " ha iniciado sesion");
	    		System.out.println(logged_user.toString());
	    	
				model.addAttribute("successful_login", logged_user);
	    		// El dashboard se mostrará inicialmente con las tareas de alta prioridad
	    		Carpeta alta = cs.findCarpetaPrioridadAltaByUsuarioId(logged_user.getId());
	    		List<Tarea> lt = (List<Tarea>) ts.orderByTaskPriority(alta.getId());
	    		model.addAttribute("tareas", lt.isEmpty() ? Collections.EMPTY_LIST : lt);
	    		List<Carpeta> lc = (List<Carpeta>) cs.findAllCarpetaByUsuarioId(logged_user.getId());
	    		model.addAttribute("carpetas", lc.isEmpty() ? Collections.EMPTY_LIST : lc);
	    		model.addAttribute("idCarpeta", alta.getId());
	    		model.addAttribute("formularioTarea", new FormularioTarea());
	    		model.addAttribute("formularioCarpeta", new FormularioCarpeta());
				return access(model);
				
			} else { // El usuario no es correcto por la contraseña
				System.out.println("No coincide la contrasena");
				boolean mostrarAlerta = true;
				String mensaje = ("Las credenciales introducidas no son correctas");
				model.addAttribute("mensaje", mensaje);
				model.addAttribute("mostrarAlerta", mostrarAlerta);
				return "login";
			}
		}

	}

	@GetMapping("/user/dashboard")
	public String access(Model model) {
		System.out.println(model.getAttribute("formularioCarpeta"));
		return "dashboard";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("usuarioRegister", new Usuario());
		return "register";
	}

	@PostMapping("/doRegister")
	public String doRegister(Usuario usuario, Model model) {
		Optional<Usuario> op = us.findUsuarioByCorreo(usuario.getCorreo());
		// Esta comprobacion la hago para que no haya personas con el mismo correo
		// registrandose
		if (op.isEmpty()) {
			us.insertUsers(usuario.getCorreo(), usuario.getContrasena());
			boolean mostrarAlerta = true;
			String mensaje = ("Usuario registrado correctamente");
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("mostrarAlerta", mostrarAlerta);
			return "login";
		} else {
			boolean mostrarAlerta = true;
			String mensaje = ("No se ha podido crear el usuario introducido");
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("mostrarAlerta", mostrarAlerta);
			return "login";
		}
	}

}
