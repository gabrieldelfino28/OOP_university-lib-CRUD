package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Aluno {

	private int RA;
	private String nome;
	private String email;

	public Aluno() {
		super();
	}

	@Override
	public String toString() {
		return nome + ", RA: " + RA + ", email: " + email;
	}

}
