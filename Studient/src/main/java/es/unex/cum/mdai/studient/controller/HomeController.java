package es.unex.cum.mdai.studient.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import es.unex.cum.mdai.studient.model.Usuario;
import es.unex.cum.mdai.studient.repository.UsuarioRepository;
import es.unex.cum.mdai.studient.services.UsuarioService;

@Controller
public class HomeController {
	
	private UsuarioRepository u;
	private UsuarioService us;
	
	public HomeController(UsuarioRepository u, UsuarioService us) {
		System.out.println("\t HomeController builder");
		this.u=u;
		this.us=us;
	}
	
	@GetMapping("/")
	public String index(Model model) {
		System.out.println("HOLAAAAA");
		model.addAttribute("usuario", new Usuario(null, null));
		return "login.html";
	}
	
	@GetMapping("/doLogin")
	public String doLogin(Usuario usuario, Model model) {
		System.out.println("Iniciar sesión");
		Optional<Usuario> op= u.findByCorreo(usuario.getCorreo());
		if (op.isEmpty()) {
			System.out.println("No lo encuentra");
			model.addAttribute("Error", "Las credenciales introducidas no son correctas" );
			return "error.html";
		}else {
			String contr=op.get().getContrasena();
			if (contr.equals(usuario.getContrasena())) {
				System.out.println("Inicio de sesion correcto");
				return "paginaInicial.html";
			}
			else {
				System.out.println("No coincide la contrasena");
				model.addAttribute("Error", "Error al iniciar sesion, las credenciales introducidas no son correctas" );
				return "error.html";
			}
		}
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("usuarioRegister", new Usuario(null, null));
		return "register";
	}
	
	@PostMapping("/doRegister")
	public String doRegister(Usuario usuario, Model model) {
		Optional<Usuario> op= u.findByCorreo(usuario.getCorreo());
		//Esta comprobacion la hago para que no haya personas con el mismo correo registrandose
		if (op.isEmpty()) {
			us.insertUsers(usuario.getCorreo(), usuario.getContrasena());
			return "login";
		}else {
			model.addAttribute("Error", "No se ha podido crear el usuario introducido" );
			return "error";
		}
	}
	
	
}

