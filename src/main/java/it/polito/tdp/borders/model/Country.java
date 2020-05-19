package it.polito.tdp.borders.model;

public class Country {
	
	private int codice;
	private String abb;
	private String nome;
	
	public Country(int codice, String abb, String nome) {
		super();
		this.codice = codice;
		this.abb = abb;
		this.nome = nome;
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public String getAbb() {
		return abb;
	}

	public void setAbb(String abb) {
		this.abb = abb;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abb == null) ? 0 : abb.hashCode());
		result = prime * result + codice;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (abb == null) {
			if (other.abb != null)
				return false;
		} else if (!abb.equals(other.abb))
			return false;
		if (codice != other.codice)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Country [nome=" + nome + "]";
	}
	
	

}
