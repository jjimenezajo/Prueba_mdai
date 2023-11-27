package es.unex.cum.mdai.studient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.unex.cum.mdai.studient.repository.UsuarioRepository;

@Controller
public class HomeController {
	
	private UsuarioRepository u;
	
	public HomeController(UsuarioRepository u) {
		System.out.println("\t HomeController builder");
		this.u=u;
	}
	
	@GetMapping("/")
	public String index() {
		System.out.println("HOLAAAAA");
		return "login.html";
	}
	
	@GetMapping("/doLogin")
	public void doLogin() {
		System.out.println("Iniciar sesión");
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	
}

