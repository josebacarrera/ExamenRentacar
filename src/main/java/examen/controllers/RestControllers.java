package examen.controllers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import examen.model.bean.Cliente;
import examen.model.bean.Vehiculo;
import examen.model.dao.ClienteModelo;
import examen.model.dao.VehiculoModelo;


@RestController
@RequestMapping("/api")
public class RestControllers {

	@Autowired
	JdbcTemplate db;
	
	@Autowired
	ClienteModelo cm;
	
	@Autowired
	VehiculoModelo vm;
	
	

	//selectAll vehiculos
	 
	 
	@GetMapping("/vehiculos")
	public Map<String, Object> productos() {
		Map<String, Object> m = new HashMap<String, Object>();

		m.put("peticion", "recibida");
		m.put("allVehiculos", vm.selectAll());

		return m;

	}
	
	//selectAll clientes
	@GetMapping("/clientes")
	public ArrayList<Cliente> clientes() {
		return cm.selectAll();

	}
	

	//idvehiculo
	@GetMapping("/vehiculos/{id}")
	public Vehiculo idVehiculo(@PathVariable int id) {


		return vm.vehiculoId(id);

	}
	
	//añadir vehiculo
	@PostMapping ("/vehiculos")
	public Map<String, Object> anadirVehiculo(@RequestParam Map <String, String> params){
		Map <String, Object> map = new HashMap <String, Object>();
		map.put("insertado", vm.anadir(params.get("matricula"),params.get("marca"), params.get("modelo"), params.get("color"), Integer.parseInt(params.get("cliente_id"))));
		map.put("recibido", "ok");
		return map;
	}
	
	//editar vehiculo
	@PutMapping("/vehiculos/{id}")
	public boolean editarVehiculo (@PathVariable int id, String matricula, String marca, String modelo, String color, int cliente_id) {
		
		if (vm.editar(id, matricula, marca, modelo, color, cliente_id) == 1) {
			return true;
		} else {
			return false;
		}
	}
	//eliminar vehiculo
	@DeleteMapping("/vehiculos/{id}")
	public boolean eliminarVehiculo (@PathVariable int id) {
		if (vm.eliminar(id) == 1) {
			return true;
		} else {
			return false;
		}
	}
}
