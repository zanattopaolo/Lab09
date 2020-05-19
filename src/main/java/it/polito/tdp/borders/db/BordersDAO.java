package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> map) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			Country c;
			
			while (rs.next()) {
				int codice=rs.getInt("ccode");
				if(!map.containsKey(codice)) {
					c=new Country(codice, rs.getString("StateAbb"), rs.getString("StateNme"));
					map.put(codice, c);
				}
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		String sql = "SELECT state1no, state2no, state1ab, state2ab FROM contiguity WHERE conttype = 1 AND YEAR <= ? ;";
		List<Border> res=new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();
			Border b=null;
			
			while (rs.next()) {
				b=new Border(rs.getInt("state1no"), rs.getInt("state2no"), rs.getString("state1ab"), rs.getString("state2ab"));
				res.add(b);
			}
			
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		return res;
	}
}
