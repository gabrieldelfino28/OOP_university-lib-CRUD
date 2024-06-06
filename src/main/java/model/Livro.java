package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Livro extends Exemplar{
	
	private String ISBN;
	private int edicao;
	
	public Livro() {
		super();
	}

	@Override
	public String toString() {
		return "Livro: " + getNome() + ", cod: " + getCodigo() + ", pags: " + getQtdPaginas() + ", ISBN: " + getISBN() + ", Edição: " + getEdicao();
	}
}
