package it.polito.tdp.borders.db;

import java.util.List;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Border;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();
		List<Border> list=dao.getCountryPairs(1945);
		System.out.println("Lista di tutti i "+list.size()+"confini:\n"+list);
//		List<Country> countries = dao.loadAllCountries();
	}
}
