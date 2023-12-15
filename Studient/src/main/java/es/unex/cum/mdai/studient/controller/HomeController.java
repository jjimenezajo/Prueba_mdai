package es.unex.cum.mdai.studient.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.unex.cum.mdai.studient.model.Carpeta;
import es.unex.cum.mdai.studient.model.Estado;
import es.unex.cum.mdai.studient.model.FormularioCarpeta;
import es.unex.cum.mdai.studient.model.FormularioTarea;
import es.unex.cum.mdai.studient.model.Prioridad;
import es.unex.cum.mdai.studient.model.Tarea;
import es.unex.cum.mdai.studient.model.Usuario;
import es.unex.cum.mdai.studient.services.CarpetaService;
import es.unex.cum.mdai.studient.services.TareaService;
import es.unex.cum.mdai.studient.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/** CONTROLADOR DEL PANEL DE GESTIÓN DEL USUARIO **/
@Controller
public class HomeController {

	private UsuarioService us;
	private CarpetaService cs;
	private TareaService ts;

	public HomeController(UsuarioService us, CarpetaService cs, TareaService ts) {
		System.out.println("\t HomeController builder");
		this.us = us;
		this.cs = cs;
		this.ts = ts;
	}

	@GetMapping("/logout")
	public String doLogout(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession(true);
        SecurityContextHolder.clearContext();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        session.invalidate();
		model.addAttribute("usuario", new Usuario());
		return "login";
	}

	@GetMapping("/user/cargarCarpeta")
	public String cargarCarpeta(@RequestParam Long id, @RequestParam Long id2, Model model) {
		
		//comprobación de que el usuario que accede a esta URL es autenticado y no se accede
		//a un dashboard ajeno
		if ( checkId(id2) == false ) {
			return "accessDenied";
		}
		
		Usuario logged_user = us.findUsuarioById(id2).get();
		List<Tarea> lt = (List<Tarea>) ts.orderByTaskPriority(id);
		List<Carpeta> lc = (List<Carpeta>) cs.findAllCarpetaByUsuarioId(logged_user.getId());
		List<Tarea> lt_final = new ArrayList<Tarea>();
		Carpeta carp = cs.findCarpetaById(id).get();
		if (carp.isMutabilidad() == true) {
			for (int i = 0; i < lt.size(); i++) {
				if (lt.get(i).getEstado() != Estado.NULO) {
					lt_final.add(lt.get(i));
				}
			}
			lt = lt_final;
			boolean mutable = true;
			model.addAttribute("mutable", mutable);
		}
		model.addAttribute("tareas", lt.isEmpty() ? Collections.EMPTY_LIST : lt);
		model.addAttribute("carpetas", lc.isEmpty() ? Collections.EMPTY_LIST : lc);
		model.addAttribute("successful_login", logged_user);
		model.addAttribute("idCarpeta", id);
		model.addAttribute("formularioTarea", new FormularioTarea());
		model.addAttribute("formularioCarpeta", new FormularioCarpeta());

		return "dashboard";
	}

	@DeleteMapping("/user/deleteUsuario/{id}")
	public String deleteUsuario(@PathVariable("id") Long id, Model model) {
		
		if ( checkId(id) == false ) {
			return "accessDenied";
		}
		
		System.out.println("\t usuarioController::deleteUsuario");
		// no es estrictamente necesario aniadir los usuarios al modelo en este caso
		us.deleteUsuarioById(id);
		model.addAttribute("usuario", new Usuario());
		return "login";
	}

	@GetMapping("/user/dashboardVuelta/{id}")
	public String accessBack(@PathVariable("id") Long usuarioId, Model model) {
		

		//comprobación de que el usuario que accede a esta URL es autenticado y no se accede
		//a un dashboard ajeno
		if ( checkId(usuarioId) == false ) {
			return "accessDenied";
		}
		
		Usuario logged_user = us.findUsuarioById(usuarioId).get();
		// CARGAMOS EL MODELO CON LA INFORMACIÓN DEL USUARIO PARA MOSTRAR SU PANEL DE
		// GESTIÓN
		System.out.println(logged_user.toString());
		// El dashboard se mostrará inicialmente con las tareas de alta prioridad
		Carpeta alta = cs.findCarpetaPrioridadAltaByUsuarioId(logged_user.getId());
		List<Tarea> lt = (List<Tarea>) ts.findAllTareaByCarpetaId(alta.getId());
		model.addAttribute("tareas", lt.isEmpty() ? Collections.EMPTY_LIST : lt);
		List<Carpeta> lc = (List<Carpeta>) cs.findAllCarpetaByUsuarioId(logged_user.getId());
		model.addAttribute("carpetas", lc.isEmpty() ? Collections.EMPTY_LIST : lc);
		model.addAttribute("successful_login", logged_user);
		model.addAttribute("idCarpeta", alta.getId());
		model.addAttribute("formularioTarea", new FormularioTarea());
		model.addAttribute("formularioCarpeta", new FormularioCarpeta());
		return "dashboard";
	}

	@DeleteMapping("/user/deleteTarea")
	public String deleteTarea(@RequestParam Long tareaId, @RequestParam Long idUser, Model model) {
		
		//comprobación de que el usuario que accede a esta URL es autenticado y no se accede
		//a un dashboard ajeno
		if ( checkId(idUser) == false ) {
			return "accessDenied";
		}
		
		Usuario userbus = us.findUsuarioById(idUser).get();
		Tarea t = ts.findTareaById(tareaId).get();
		t.getCarpetas();
		userbus.setCarpetas((List<Carpeta>)cs.findAllCarpetaByUsuarioId(userbus.getId()));
		int borrado_carpetas=0;
		for (int i=0; i<userbus.getCarpetas().size(); i++) {
			if(userbus.getCarpetas().get(i).getId()==t.getCarpetas().get(0).getId() || userbus.getCarpetas().get(i).getId()==t.getCarpetas().get(1).getId()) {
				int q=0;
				int actual_borrado=borrado_carpetas;
				while (q<userbus.getCarpetas().get(i).getTareas().size() && actual_borrado==borrado_carpetas) {
					if (userbus.getCarpetas().get(i).getTareas().get(q).getId()==tareaId) {
						userbus.getCarpetas().get(i).getTareas().remove(q);
						cs.saveCarpeta(userbus.getCarpetas().get(i));
						borrado_carpetas++;
					}
					q++;
				}
			}

		}
		ts.deleteTareaById(tareaId);
		Carpeta alta = cs.findCarpetaPrioridadAltaByUsuarioId(userbus.getId());
		List<Tarea> lt = (List<Tarea>) ts.orderByTaskPriority(alta.getId());
		model.addAttribute("tareas", lt.isEmpty() ? Collections.EMPTY_LIST : lt);
		List<Carpeta> lc = (List<Carpeta>) cs.findAllCarpetaByUsuarioId(userbus.getId());
		model.addAttribute("carpetas", lc.isEmpty() ? Collections.EMPTY_LIST : lc);
		model.addAttribute("idCarpeta", alta.getId());
		
		model.addAttribute("successful_login", userbus);
		model.addAttribute("formularioTarea", new FormularioTarea());
		model.addAttribute("formularioCarpeta", new FormularioCarpeta());
		return"dashboard";
	}
	
	@PutMapping("/user/addCarpeta")
	public String addCarpeta(@ModelAttribute("formularioCarpeta") FormularioCarpeta formularioCarpeta, Model model) {
		Usuario userbus = us.findUsuarioById(formularioCarpeta.getId()).get();
		List<Carpeta> c= cs.findCarpetaByDescripcion(formularioCarpeta.getDescripcion());
		boolean contiene = false;
		if (c.size()>=1) {
			int contador=0;
			while (contador<c.size() && contiene==false) {
				if(c.get(contador).getUsuario().getId()==formularioCarpeta.getId()) {
					contiene=true;
				}
				contador++;
			}
		}
		//Si el usuario no contiene una carpeta que se llame asi (puede haber varios usuarios que tengan la carpeta matematicas por ejemplo)
		if (contiene==false) {
			Carpeta carp_new = new Carpeta(formularioCarpeta.getDescripcion(), true, userbus);
			userbus.addCarpeta(carp_new);
			cs.saveCarpeta(carp_new);
		}
		else {
			boolean mostrarAlerta = true;
			String mensaje = ("El usuario ya tiene una carpeta con esta descripcion");
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("mostrarAlerta", mostrarAlerta);
		}
		Carpeta alta = cs.findCarpetaPrioridadAltaByUsuarioId(userbus.getId());
		List<Tarea> lt = (List<Tarea>) ts.orderByTaskPriority(alta.getId());
		model.addAttribute("tareas", lt.isEmpty() ? Collections.EMPTY_LIST : lt);
		List<Carpeta> lc = (List<Carpeta>) cs.findAllCarpetaByUsuarioId(userbus.getId());
		model.addAttribute("carpetas", lc.isEmpty() ? Collections.EMPTY_LIST : lc);
		model.addAttribute("idCarpeta", alta.getId());
		
		model.addAttribute("successful_login", userbus);
		model.addAttribute("formularioTarea", new FormularioTarea());
		model.addAttribute("formularioCarpeta", new FormularioCarpeta());
		return"dashboard";
	}
	
	@PutMapping("/user/addTarea")
	public String addTarea(@ModelAttribute("formularioTarea") FormularioTarea formularioTarea, Model model) {
		
		String descripcion = formularioTarea.getNombre();
		String prioridad = formularioTarea.getPrioridad();
		Long idCarpeta = formularioTarea.getIdCarpeta();
		Long idUser = formularioTarea.getId();
		Usuario user_reg = us.findUsuarioById(idUser).get();
		List<Carpeta> carpetas = (List<Carpeta>) cs.findAllCarpetaByUsuarioId(idUser);
		List<Tarea> tareas = new ArrayList<Tarea>();
		user_reg.setCarpetas(carpetas);
		for (int i = 0; i < user_reg.getCarpetas().size(); i++) {
			if (user_reg.getCarpetas().get(i).getId() == idCarpeta) {
				Tarea t1 = new Tarea();
				t1.setDescripcion(descripcion);
				t1.setEstado(Estado.PENDIENTE);
				Carpeta car = new Carpeta();
				if (prioridad.equals("alta")) {
					t1.setPrioridad(Prioridad.ALTA);
					car = cs.findCarpetaPrioridadAltaByUsuarioId(idUser);

				} else {
					car = cs.findCarpetaPrioridadBajaByUsuarioId(idUser);
					t1.setPrioridad(Prioridad.BAJA);
				}
				t1.addCarpeta(car);
				car.addTareas(t1);
				user_reg.getCarpetas().get(i).addTareas(t1);
				t1.addCarpeta(carpetas.get(i));
				ts.saveTarea(t1);
				cs.saveCarpeta(user_reg.getCarpetas().get(i));
				cs.saveCarpeta(car);
			}
		}
		Carpeta c= cs.findCarpetaPrioridadAltaByUsuarioId(idUser);
		tareas=c.getTareas();
		model.addAttribute("tareas", tareas.isEmpty() ? Collections.EMPTY_LIST : tareas);
		model.addAttribute("carpetas",
				user_reg.getCarpetas().isEmpty() ? Collections.EMPTY_LIST : user_reg.getCarpetas());
		model.addAttribute("successful_login", user_reg);
		model.addAttribute("idCarpeta", c.getId());
		model.addAttribute("formularioTarea", new FormularioTarea());
		model.addAttribute("formularioCarpeta", new FormularioCarpeta());
		return "dashboard";
	}

	@GetMapping("/user/perfil/{id}")
	public String showPerfil(@PathVariable("id") Long usuarioId, Model model) {
//		if ( checkId(usuarioId) == false ) {
//			return "accessDenied";
//		}
		model.addAttribute("usuarioUpdate", us.findUsuarioById(usuarioId).get());
		return "perfil";
	}

	@PutMapping("/user/updateUsuario/{id}")
	public String updateUsuario(@PathVariable("id") Long id, Usuario usuario, Model model) {
		
		if ( checkId(id) == false ) {
			return "accessDenied";
		}
		
		Optional<Usuario> use = us.findUsuarioById(id);
		
		Usuario usuar = use.get();
		Usuario us_act = usuario;
		use = us.findUsuarioByCorreo(us_act.getCorreo());
		if (use.isPresent()) {
			Usuario aux = use.get();
			// ya existe otro usuario con ese correo
			if (aux.getId() != id) {
				boolean mostrarAlerta = true;
				String mensaje = ("El correo introducido ya ha sido registrado con otro usuario");
				model.addAttribute("mensaje", mensaje);
				model.addAttribute("mostrarAlerta", mostrarAlerta);
			} else {
				if (us_act.getContrasena().length() < 4) {
					boolean mostrarAlerta = true;
					String mensaje = ("La contraseña debe de contener al menos 4 caracteres");
					model.addAttribute("mensaje", mensaje);
					model.addAttribute("mostrarAlerta", mostrarAlerta);
				} else {
					usuar.setCorreo(us_act.getCorreo());
					usuar.setContrasena(us_act.getContrasena());
					us.saveUsuario(usuar);
				}
			}
		} else {
			if (us_act.getContrasena().length() < 4) {
				boolean mostrarAlerta = true;
				String mensaje = ("La contraseña debe de contener al menos 4 caracteres");
				model.addAttribute("mensaje", mensaje);
				model.addAttribute("mostrarAlerta", mostrarAlerta);
			} else {
				usuar.setCorreo(us_act.getCorreo());
				usuar.setContrasena(us_act.getContrasena());
				us.saveUsuario(usuar);
			}
		}
		model.addAttribute("usuarioUpdate", us.findUsuarioById(usuar.getId()).get());
		return "perfil";
	}
	
	
	private String showAndReturnAuthenticathedUsername (){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Obtiene el nombre de usuario del objeto Authentication
        String username = authentication.getName();        
        // Ahora 'username' contiene el nombre de usuario del usuario autenticado
        System.out.println("--------------");
        System.out.println("\tNombre de usuario authenticado: " + username);
        System.out.println("--------------");
        return username;
	}
	
	
	private boolean checkId (Long usuarioId) {
		
		Optional<Usuario> op = us.findUsuarioByCorreo(showAndReturnAuthenticathedUsername());
		if (op.get().getId()==usuarioId)
			return true;
		return false;
	}
	
	
}
