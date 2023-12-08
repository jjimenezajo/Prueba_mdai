package es.unex.cum.mdai.studient.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Tarea;
import es.unex.cum.mdai.studient.model.Usuario;
import es.unex.cum.mdai.studient.repository.CarpetaRepository;
import es.unex.cum.mdai.studient.repository.TareaRepository;
import es.unex.cum.mdai.studient.repository.UsuarioRepository;
import es.unex.cum.mdai.studient.services.UsuarioService;

@Controller
public class HomeController {
	
	private UsuarioRepository u;
	private UsuarioService us;
	private TareaRepository t;
	private CarpetaRepository c;
	
	public HomeController(UsuarioRepository u, UsuarioService us, TareaRepository t, CarpetaRepository c) {
		System.out.println("\t HomeController builder");
		this.u=u;
		this.us=us;
		this.t=t;
		this.c=c;
	}
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("usuario", new Usuario(null, null));
		return "login";
	}
	
	@GetMapping("/doLogin")
	public String doLogin(Usuario usuario, Model model) {
		System.out.println("Iniciar sesión");
		Optional<Usuario> op= u.findByCorreo(usuario.getCorreo());
		if (op.isEmpty()) {
			System.out.println("No lo encuentra");
			String mensaje=("El usuario introducido no existe");
			boolean mostrarAlerta=true;
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("mostrarAlerta", mostrarAlerta);
			return "login";
		}else {
			String contr=op.get().getContrasena();
			if (contr.equals(usuario.getContrasena())) {
				System.out.println("El usuario con id "+usuario.getId() +" ha iniciado sesion");
				System.out.println(usuario.toString());
				Iterable<Carpeta> it_c=c.findByUsuarioId(op.get().getId());
				for (Carpeta elemento : it_c) {
					if(elemento.getNombre().equals("Prioridad Alta")) {
						List<Tarea> lt= (List<Tarea>)c.findAllTareaByCarpetaId(elemento.getId());
						model.addAttribute("tareas", lt.isEmpty() ? Collections.EMPTY_LIST : lt);
						List<Carpeta> lc= (List<Carpeta>)c.findByUsuarioId(op.get().getId());
						model.addAttribute("carpetas", lc.isEmpty() ? Collections.EMPTY_LIST : lc);
					}
				}
				model.addAttribute("usuario", op.get());
				return "dashboard";
			}
			else {
				System.out.println("No coincide la contrasena");
				boolean mostrarAlerta=true;
				String mensaje=("Las credenciales introducidas no son correctas");
				model.addAttribute("mensaje", mensaje);
				model.addAttribute("mostrarAlerta", mostrarAlerta);
				return "login";
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
			boolean mostrarAlerta=true;
			String mensaje=("Usuario registrado correctamente");
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("mostrarAlerta", mostrarAlerta);
			return "login";
		}else {
			boolean mostrarAlerta=true;
			String mensaje=("No se ha podido crear el usuario introducido");
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("mostrarAlerta", mostrarAlerta);
			return "login";
		}
	}
	
	
}

