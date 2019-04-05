package TD5.TD5.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import TD5.TD5.repositories.*;
import TD5.TD5.entities.*;

@Controller
public class ScriptController {	
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private LanguageRepository languageRepository;
	
	@Autowired
	private ScriptRepository scriptRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private User user;
	
	@RequestMapping("")
	public String Accueil() {
		return "accueil";
	}
	
	@RequestMapping("/create")
	public String create() {
		User user1 = new User();
		user1.setLogin("21602008");
		user1.setPassword("Oui");
		user1.setIdentity("Hugo GRAINVILLE--BERTRAN");
		user1.setEmail("21602008@etu.unicaen.fr");
		userRepository.save(user1);
		
		User user2 = new User();
		user2.setLogin("hgb");
		user2.setPassword("Non");
		user2.setIdentity("Hugo GRAINVILLE--BERTRAN");
		user2.setEmail("21602008@etu.unicaen.fr");
		userRepository.save(user2);
		
		Language html = new Language();
		html.setName("HTML");		
		languageRepository.save(html);
		
		Category catego1 = new Category();
		catego1.setName("Opération");
		categoryRepository.save(catego1);
		
		return "create";
	}	
	
	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}	
	
	@PostMapping("loginPost")
	public RedirectView loginPost(HttpServletRequest request,HttpSession session) {
		
		List<User> users = userRepository.findAll();
	
		User connection = new User();
		connection.setLogin(request.getParameter("login"));
		connection.setPassword(request.getParameter("password"));
				
		for(User u : users) {
			if(u.getLogin().equals(connection.getLogin()) && u.getPassword().equals(connection.getPassword())) {
				user = u;

				List<Script> allScripts = scriptRepository.findAll();
				List<Script> usersScript = new ArrayList<Script>();
				
				for(Script s : allScripts) {
					if(s.getUser().getId() == user.getId())
					usersScript.add(s);
				}
				user.setScripts(usersScript);
				
				session.setAttribute("user", user);
				
				return new RedirectView("index");
			}
		}		
		return new RedirectView("login");
	}	
	
	@RequestMapping("/logout")
	public RedirectView logout() {
		user = null;
		return new RedirectView("login");
	}	
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		if(user != null) {
			model.addAttribute("user", user);
			return "index";
		}
		return "login";
	}	
	
	@RequestMapping("/script/new")
	public String scriptNew(ModelMap model) {
		if(user != null) {
			List<Category> categories = categoryRepository.findAll();
			
			List<Language> languages = languageRepository.findAll();
			
			model.addAttribute("categories", categories);
			model.addAttribute("languages", languages);
			return "script_new";
		}
		return "../non_connected";
	}
	
	@PostMapping("/script/submit")
	public RedirectView addScript(Script script) {
		if(user != null) {
			script.setUser(user);
			scriptRepository.save(script);
			user.getScripts().add(script);
			
			return new RedirectView("../index");
		}
		return new RedirectView("../non_connected");
	}	
	
	@GetMapping("/script/{id}/delete")
	public RedirectView deleteScript(@PathVariable("id") int id, ModelMap model) {
		if(user != null) {
			Optional<Script> optScript = scriptRepository.findById(id);
			if(optScript.isPresent()) {				
				scriptRepository.delete(optScript.get());
				user.getScripts().remove(optScript.get());
			}
					return new RedirectView("/suppression");
			}
		return new RedirectView("/non_connected");
	}	
	
	@RequestMapping("/script/{id}/edit")
	@GetMapping
	public String scriptEdit(@PathVariable("id") int id, ModelMap model) {		
		if(user == null)
			return "non_connected";		
		Optional<Script> opt = scriptRepository.findById(id);		
		if(opt.isPresent()) {
			Script s = opt.get();
			if(s.getUser().getId() == user.getId()) {
				model.addAttribute("script", s);
				
				List<Category> categories = categoryRepository.findAll();				
				List<Language> languages = languageRepository.findAll();
				
				Category selectedCat = s.getCategory();
				categories.remove(selectedCat);
				model.addAttribute("selectedCategory", selectedCat);
				
				Language selectedLanguage = s.getLanguage();
				languages.remove(selectedLanguage);
				model.addAttribute("selectedLanguage", selectedLanguage);
				
				model.addAttribute("categories", categories);
				model.addAttribute("languages", languages);
				
				return "script_edit";
			}
		}		
		model.addAttribute("user", user);
		return "index";		
	}
	
	@RequestMapping("/non_connected")
	public String nonConnected() {
		return "non_connected";
	}
	
	@RequestMapping("/404")
	public String error404() {
		return "404";
	}
	
	@RequestMapping("search")
	public String search(ModelMap model) {		
		if(user != null) {			
			model.addAttribute("scriptsTrouves", user.getScripts());
			model.addAttribute("user", user);				
			return "search";
		} else {
			return "login";			
		}
	}
	
}