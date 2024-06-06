package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Exemplar {

	protected int codigo;
	protected String nome;
	protected int qtdPaginas;
	
	public Exemplar() {
		super();
	}

	@Override
	public String toString() {
		return "Nome: " + nome + ", Págs: " + qtdPaginas;
	}
	
	
}