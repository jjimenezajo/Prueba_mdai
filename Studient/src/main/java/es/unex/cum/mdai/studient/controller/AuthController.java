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
import es.unex.cum.mdai.studient.services.CarpetaService;
import es.unex.cum.mdai.studient.services.TareaService;
import es.unex.cum.mdai.studient.services.UsuarioService;

/** CONTROLADOR DE LAS PANTALLAS DE AUTENTICACIÓN DEL USUARIO **/
@Controller
public class AuthController {

	private UsuarioService us;
	private CarpetaService cs;
	private TareaService ts;

	public AuthController(UsuarioService us, CarpetaService cs, TareaService ts) {
		System.out.println("\t AuthController builder");
		this.us = us;
		this.cs = cs;
		this.ts = ts;
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "login";
	}

	@GetMapping("/doLogin")
	public String doLogin(Usuario usuario, Model model) {
		System.out.println("Iniciar sesión");
		Optional<Usuario> op = us.findUsuarioByCorreo(usuario.getCorreo());
		if (op.isEmpty()) {
			System.out.println("No lo encuentra");
			String mensaje = ("El usuario introducido no existe");
			boolean mostrarAlerta = true;
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("mostrarAlerta", mostrarAlerta);
			return "login";
		} else {
			String contr = op.get().getContrasena();
			if (contr.equals(usuario.getContrasena())) {
				System.out.println("El usuario con id " + usuario.getId() + " ha iniciado sesion");
				System.out.println(usuario.toString());
				//El dashboard se mostrará inicialmente con las tareas de alta prioridad
				Carpeta alta = cs.findCarpetaPrioridadAltaByUsuarioId(op.get().getId());
				List<Tarea> lt = (List<Tarea>) ts.findAllTareaByCarpetaId(alta.getId());
				model.addAttribute("tareas", lt.isEmpty() ? Collections.EMPTY_LIST : lt);
				List<Carpeta> lc = (List<Carpeta>) cs.findAllCarpetaByUsuarioId(op.get().getId());
				model.addAttribute("carpetas", lc.isEmpty() ? Collections.EMPTY_LIST : lc);
				model.addAttribute("usuario", op.get());
				return "dashboard";
			} else {
				System.out.println("No coincide la contrasena");
				boolean mostrarAlerta = true;
				String mensaje = ("Las credenciales introducidas no son correctas");
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
