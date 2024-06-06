package control.factory;

import model.Aluno;

//Simple Factory
public interface IAlunoFactory {
	Aluno createAluno(String params, String RA, String nome, String email) throws Exception;
}
