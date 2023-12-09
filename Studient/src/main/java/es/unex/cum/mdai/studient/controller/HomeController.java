package es.unex.cum.mdai.studient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import es.unex.cum.mdai.studient.services.CarpetaService;
import es.unex.cum.mdai.studient.services.TareaService;
import es.unex.cum.mdai.studient.services.UsuarioService;

/**CONTROLADOR DEL PANEL DE GESTIÓN DEL USUARIO**/
@Controller
public class HomeController {
	
	private UsuarioService us;
	private CarpetaService cs;
	private TareaService ts;
	
	public HomeController(UsuarioService us, CarpetaService cs, TareaService ts) {
		System.out.println("\t HomeController builder");
		this.us=us;
		this.cs=cs;
		this.ts=ts;
	}
	
	@GetMapping("/logout")
	public String doLogout() {
		return "login";
	}
	
}
	