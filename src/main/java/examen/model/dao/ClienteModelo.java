package examen.model.dao;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import examen.model.bean.Cliente;
import examen.model.bean.Vehiculo;
@Component
public class ClienteModelo {

	
	@Autowired
	JdbcTemplate db;
	
	public ArrayList<Cliente> selectAll() {
		ArrayList<Cliente> c = new ArrayList<Cliente>();
		
		String sql = "SELECT * FROM customers WHERE deleted=0";
		SqlRowSet rs = db.queryForRowSet(sql);
		
		while(rs.next()) {
			c.add(new Cliente(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("surname"),
						rs.getString("telephone"),
						rs.getInt("deleted")
					));
		}
		
		return c;
	}
	
	
	
}
