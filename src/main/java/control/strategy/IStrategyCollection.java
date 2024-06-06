package control.strategy;

import java.util.List;

import model.Aluno;
import model.Exemplar;

public interface IStrategyCollection {
	
	public List<Exemplar> findExemplares();
	
	public Exemplar findExemplar(String cod) throws Exception;
	
	public Aluno findAluno(String cod) throws Exception;
}
