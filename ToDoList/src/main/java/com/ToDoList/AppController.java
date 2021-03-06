package com.ToDoList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

	@Autowired
	private TaskService service; 
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<Task> listTasks = service.listAll();
		model.addAttribute("listTasks", listTasks);
		
		return "index";
	}
	
	@RequestMapping("/new")
	public String showNewTaskPage(Model model) {
		Task task = new Task();
		model.addAttribute("task", task);
		
		return "new_task";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveTask(@ModelAttribute("task") Task task) {
		
		if (task.getId()==null || task.getCondicion()=="") {
			task.setCondicion("false"); 
		} 
		
		service.save(task);
		
		return "redirect:/"; 
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditTaskPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_task");
		Task task = service.get(id);
		mav.addObject("task", task);
		
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteTask(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/";		
	}
}
