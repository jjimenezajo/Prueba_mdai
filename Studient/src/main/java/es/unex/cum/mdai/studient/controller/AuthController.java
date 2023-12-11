package es.unex.cum.mdai.studient.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Tarea;
import es.unex.cum.mdai.studient.model.Usuario;
import es.unex.cum.mdai.studient.repository.UsuarioRepository;
import es.unex.cum.mdai.studient.services.CarpetaService;
import es.unex.cum.mdai.studient.services.TareaService;
import es.unex.cum.mdai.studient.services.UsuarioService;

/** CONTROLADOR DE LAS PANTALLAS DE AUTENTICACIÓN DEL USUARIO **/
@Controller
public class AuthController {

	private UsuarioService us;
	private CarpetaService cs;
	private TareaService ts;
	private UsuarioRepository ur;

	public AuthController(UsuarioService us, CarpetaService cs, TareaService ts, UsuarioRepository ur) {
		System.out.println("\t AuthController builder");
		this.us = us;
		this.cs = cs;
		this.ts = ts;
		this.ur = ur;
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "login";
	}

	@PostMapping("/doLogin")
	public String doLogin(Usuario usuario, Model model, RedirectAttributes attributes) {
		System.out.println("Iniciar sesión");
		Optional<Usuario> op = us.findUsuarioByCorreo(usuario.getCorreo());
		//El usuario no existe
		if (op.isEmpty()) {
			System.out.println("No lo encuentra");
			String mensaje = ("El usuario introducido no existe");
			boolean mostrarAlerta = true;
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("mostrarAlerta", mostrarAlerta);
			return "login";
		} else {//El usuario existe
			String contr = op.get().getContrasena();
			if (contr.equals(usuario.getContrasena())) {//El usuario es correcto por la contraseña
				Usuario logged_user = op.get();
				model.addAttribute("successful_login", logged_user);
				return access(model);
			} else { //El usuario no es correcto por la contraseña
				System.out.println("No coincide la contrasena");
				boolean mostrarAlerta = true;
				String mensaje = ("Las credenciales introducidas no son correctas");
				model.addAttribute("mensaje", mensaje);
				model.addAttribute("mostrarAlerta", mostrarAlerta);
				return "login";
			}
		}

	}
	
	@GetMapping("/dashboard")
	public String access(Model model) {
		Usuario logged_user = (Usuario) model.getAttribute("successful_login");
		//CARGAMOS EL MODELO CON LA INFORMACIÓN DEL USUARIO PARA MOSTRAR SU PANEL DE GESTIÓN
		System.out.println("El usuario con id " + logged_user.getId() + " ha iniciado sesion");
		System.out.println(logged_user.toString());
		//El dashboard se mostrará inicialmente con las tareas de alta prioridad
		Carpeta alta = cs.findCarpetaPrioridadAltaByUsuarioId(logged_user.getId());
		List<Tarea> lt = (List<Tarea>) ts.findAllTareaByCarpetaId(alta.getId());
		model.addAttribute("tareas", lt.isEmpty() ? Collections.EMPTY_LIST : lt);
		List<Carpeta> lc = (List<Carpeta>) cs.findAllCarpetaByUsuarioId(logged_user.getId());
		model.addAttribute("carpetas", lc.isEmpty() ? Collections.EMPTY_LIST : lc);
		model.addAttribute("usuario", logged_user);
		return "dashboard";
	}
	
	@GetMapping("/dashboardVuelta/{id}")
	public String accessBack(@PathVariable("id") Long usuarioId, Model model) {
		Usuario logged_user = us.findUsuarioById(usuarioId).get();
		model.addAttribute("successful_login", logged_user);
		//CARGAMOS EL MODELO CON LA INFORMACIÓN DEL USUARIO PARA MOSTRAR SU PANEL DE GESTIÓN
		System.out.println(logged_user.toString());
		//El dashboard se mostrará inicialmente con las tareas de alta prioridad
		Carpeta alta = cs.findCarpetaPrioridadAltaByUsuarioId(logged_user.getId());
		List<Tarea> lt = (List<Tarea>) ts.findAllTareaByCarpetaId(alta.getId());
		model.addAttribute("tareas", lt.isEmpty() ? Collections.EMPTY_LIST : lt);
		List<Carpeta> lc = (List<Carpeta>) cs.findAllCarpetaByUsuarioId(logged_user.getId());
		model.addAttribute("carpetas", lc.isEmpty() ? Collections.EMPTY_LIST : lc);
		model.addAttribute("successful_login", logged_user);
		return "dashboard";
	}
	@GetMapping("/cargarCarpeta")
	public String cargarCarpeta(@RequestParam Long id, @RequestParam Long id2, Model model) {
		Usuario logged_user= us.findUsuarioById(id2).get();
		List<Tarea> lt = (List<Tarea>) ts.findAllTareaByCarpetaId(id);
		model.addAttribute("tareas", lt.isEmpty() ? Collections.EMPTY_LIST : lt);
		List<Carpeta> lc = (List<Carpeta>) cs.findAllCarpetaByUsuarioId(logged_user.getId());
		model.addAttribute("carpetas", lc.isEmpty() ? Collections.EMPTY_LIST : lc);
		model.addAttribute("successful_login", logged_user);
		return "dashboard";
	}
	
	@GetMapping("/perfil/{id}")
	public String showPerfil(@PathVariable("id") Long usuarioId, Model model) {
		model.addAttribute("usuarioUpdate",us.findUsuarioById(usuarioId).get() );
		return "perfil";
	}
	
	@PutMapping("/updateUsuario/{id}")
    public String updateUsuario(@PathVariable("id") Long id, Usuario usuario, Model model) {  
		Optional<Usuario> use= us.findUsuarioById(id);
		Usuario usuar= use.get();
		Usuario us_act= usuario;
		use= us.findUsuarioByCorreo(us_act.getCorreo());
		if (use.isPresent()) {
		Usuario aux= use.get();
		//ya existe otro usuario con ese correo
		if (aux.getId() != id) {
			boolean mostrarAlerta = true;
			String mensaje = ("El correo introducido ya ha sido registrado con otro usuario");
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("mostrarAlerta", mostrarAlerta);
		}else {
			if (us_act.getContrasena().length()<4) {
				boolean mostrarAlerta = true;
				String mensaje = ("La contraseña debe de contener al menos 4 caracteres");
				model.addAttribute("mensaje", mensaje);
				model.addAttribute("mostrarAlerta", mostrarAlerta);
			}else {
				usuar.setCorreo(us_act.getCorreo());
				usuar.setContrasena(us_act.getContrasena());
				ur.save(usuar);
			}
		}
		}else {
			if (us_act.getContrasena().length()<4) {
				boolean mostrarAlerta = true;
				String mensaje = ("La contraseña debe de contener al menos 4 caracteres");
				model.addAttribute("mensaje", mensaje);
				model.addAttribute("mostrarAlerta", mostrarAlerta);
			}else {
				usuar.setCorreo(us_act.getCorreo());
				usuar.setContrasena(us_act.getContrasena());
				ur.save(usuar);
			}
		}
		model.addAttribute("usuarioUpdate",us.findUsuarioById(usuar.getId()).get() );
		return "perfil"; 
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("usuarioRegister", new Usuario());
		return "register";
	}

	@DeleteMapping("/deleteUsuario/{id}")
	public String deleteUsuario(@PathVariable("id") Long id, Model model) {
    	System.out.println("\t usuarioController::deleteUsuario");
    	//no es estrictamente necesario aniadir los usuarios al modelo en este caso
    	us.deleteUsuarioById(id);
		model.addAttribute("usuario", new Usuario());
        return "login";
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
