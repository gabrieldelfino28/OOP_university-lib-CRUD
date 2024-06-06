package model;

import java.time.LocalDate;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Aluguel {

	private Aluno aluno;
	private Exemplar exemplar;
	private LocalDate dataRetirada;
	private LocalDate dataDevolucao;

	public Aluguel() {
		super();
	}

	@Nonnull
	@Override
	public String toString() {
		return "Aluguel: " + aluno + ", exemplar: " + exemplar + ", dataRetirada:" + dataRetirada + ", dataDevolucao: " + dataDevolucao;
	}

}
