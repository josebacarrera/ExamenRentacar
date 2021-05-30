package examen.controllers;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import examen.model.dao.ClienteModelo;
import examen.model.dao.VehiculoModelo;

@Controller

public class MainControllers {
	

	@Autowired
	JdbcTemplate db;
	
	@Autowired
	ClienteModelo cm;
	
	@Autowired
	VehiculoModelo vm;
	
	@RequestMapping("/")
	public String main(Model model, @RequestParam Map<String, String> params) {
		model.addAttribute("vehiculos", vm.selectAll());
		model.addAttribute("clientes", cm.selectAll());
		
		if (params.get("modo")!=null){
			model.addAttribute("modo", params.get("modo"));
		}

		return "page";
	}
	
	
}
