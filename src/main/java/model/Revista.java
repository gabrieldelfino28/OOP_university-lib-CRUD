package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Revista extends Exemplar{
	
	private String ISSN;
	
	public Revista() {
		super();
	}

	@Override
	public String toString() {
		return "Revista: " + getNome() + ", cod: " + getCodigo() + ", qtdPáginas: " + getQtdPaginas() + ", ISSN: " + getISSN();
	}
}
