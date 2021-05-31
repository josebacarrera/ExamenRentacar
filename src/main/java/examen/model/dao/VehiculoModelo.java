package examen.model.dao;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import examen.model.bean.Vehiculo;

@Component
public class VehiculoModelo {
	
		
		@Autowired
		JdbcTemplate db;
		
		//SellectAll
       public ArrayList<Vehiculo> selectAll(){
			ArrayList<Vehiculo> v = new ArrayList<Vehiculo>();

			String sql="Select * from vehicles o where deleted=0";
			SqlRowSet rs=db.queryForRowSet(sql);
			
			while(rs.next()){
				v.add(new Vehiculo(
					rs.getInt("id"),
					rs.getString("plate"),
					rs.getString("brand"),
					rs.getString("model"),
					rs.getString("color"),
					rs.getInt("customer_id"),
					rs.getInt("deleted")
					
					));
			}
			return v;
		}
	
		
		//filtro id 
		public Vehiculo vehiculoId(int id) {
			String sql="Select * from vehicles where id="+id;
			SqlRowSet rs=db.queryForRowSet(sql);
	
			Vehiculo v = null;
			if(rs.next()){
				return new Vehiculo( 
						rs.getInt("id"),
						rs.getString("plate"),
						rs.getString("brand"),
						rs.getString("model"),
						rs.getString("color"),
						rs.getInt("customer_id"),
						rs.getInt("deleted")
						);
			}
			return null;
	
		}
		
		//añadir
		public boolean anadir (String matricula, String marca, String modelo, String color, int cliente_id) {
			
			String sql = "INSERT INTO vehicles (plate, brand, model, color, customer_id) VALUES (?,?,?,?,?)";
			int i = db.update(sql,matricula, marca, modelo, color, cliente_id);
			if(i>0) {
				return true;
			}
			else {
				return false;

			}
		}
		//editar
		
		public int editar (int id, String matricula, String marca, String modelo, String color, int cliente_id) {
			
			String sql = "UPDATE vehicles SET plate=?, brand=?, model=?, color=?, customer_id=? WHERE deleted=0 AND id=?";
			return db.update(sql,matricula, marca, modelo, color, cliente_id, id);
		}

		//eliminar
		
		public int eliminar (int id) {
			
			String sql = "UPDATE vehicles SET deleted=1 WHERE deleted=0 AND id=?";
			return db.update(sql, id);
		}
		
		
}
