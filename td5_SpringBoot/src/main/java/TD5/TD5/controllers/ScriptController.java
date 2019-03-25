package TD5.TD5.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@RequestMapping("/create")
	@ResponseBody
	public String create() {
		User user1 = new User();
		user1.setLogin("21602008");
		user1.setPassword("Oui");
		user1.setIdentity("Hugo GRAINVILLE--BERTRAN");
		user1.setEmail("21602008@etu.unicaen.fr");
		userRepository.save(user1);
		
		User user2 = new User();
		user2.setLogin("Rebotini");
		user2.setPassword("techno");
		user2.setIdentity("Arnaud REBOTINI");
		user2.setEmail("arnaud.rebotini@techno.fr");
		userRepository.save(user2);
		
		Language html = new Language();
		html.setName("HTML");
		
		Language php = new Language();
		php.setName("PHP");
		
		Category catego1 = new Category();
		catego1.setName("Page HTML");
		categoryRepository.save(catego1);
		
		languageRepository.save(html);
		languageRepository.save(php);
		
		return "Félicitations vous avez créé : 2 utilisateurs, 2 langages et 1 catégorie !";
	}	
	
	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}	
	
	@PostMapping("loginPost")
	public RedirectView loginPost(HttpServletRequest request) {
		
		List<User> users = userRepository.findAll();
	
		User connection = new User();
		connection.setLogin(request.getParameter("login"));
		connection.setPassword(request.getParameter("password"));
				
		for(User u : users) {
			if(u.getLogin().equals(connection.getLogin()) && u.getPassword().equals(connection.getPassword())) {
				user = u;

				List<Script> allScripts = scriptRepository.findAll();
				List<Script> usersScript = new ArrayList<>();
				
				for(Script s : allScripts) {
					if(s.getUser().getId() == user.getId())
					usersScript.add(s);
				}
				user.setScripts(usersScript);
				
				return new RedirectView("index");
			}
		}		
		return new RedirectView("login");
	}	
	
	@RequestMapping("/logout")
	public RedirectView logout() {
		user = null;
		return new RedirectView("index");
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
		return "non_connected";
	}
	
	@PostMapping("/script/submit")
	public RedirectView addScript(Script script) {
		if(user != null) {
			script.setUser(user);
			scriptRepository.save(script);
			
			return new RedirectView("../index");
		}
		return new RedirectView("../non_connected");
	}	
	
	@RequestMapping("/script/{id}")
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
	
	@RequestMapping("non_connected")
	public String nonConnected() {
		return "non_connected";
	}
	
}