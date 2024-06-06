package control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import control.factory.IExemplarFactory;

import control.strategy.IStrategyCollection;
import control.strategy.IStrategyController;
import model.Aluno;
import model.Exemplar;
import model.Livro;
import model.Revista;

import perseverance.*;

public class ExemplarCollection implements IStrategyCollection, IExemplarFactory {
	
	private GenericDAO gDAO = new GenericDAO();
	
	private static final LinkedHashMap<Integer, List<Exemplar>> EXEMPLAR_MAP = new LinkedHashMap<Integer, List<Exemplar>>();
	
	public ExemplarCollection() {
		super();
	}
	
	@Override
	public List<Exemplar> findExemplares() {
		List<Exemplar> lista = new ArrayList<Exemplar>();
		try {
			IStrategyController<Exemplar> Inew;
			
			Inew = new LivroController();
			EXEMPLAR_MAP.put(0, Inew.listarTodos());
			
			Inew = new RevistaController();
			EXEMPLAR_MAP.put(1, Inew.listarTodos());
			
			Inew = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		EXEMPLAR_MAP.forEach((k, list) -> {
			lista.addAll(list);
		});
		return lista;
	}
	
	@Override
	public Aluno findAluno(String alunoRA) throws Exception {
		AlunoDAO aDAO = new AlunoDAO(gDAO);
		Aluno aluno = new Aluno();

		aluno.setRA(Integer.parseInt(alunoRA));
		aluno = aDAO.findOne(aluno);
		
		return aluno;
	}
	
	private LivroDAO lDAO = new LivroDAO(gDAO);
	private RevistaDAO rDAO = new RevistaDAO(gDAO);
	
	@Override
	public Exemplar findExemplar(String codEx) throws Exception {
		Exemplar e = createExemplar(null, codEx, null, "1", "1", "1");
		
		String type = especifyExemplar(e);
		
		if (type.contains("Livro")) {
			Livro l = lDAO.findOne((Livro) e);
			return l;
		}
		
		if (type.contains("Revista")) {
			Revista r = rDAO.findOne((Revista) e);
			return r;
		}
		
		return e;
	}
	
	private String especifyExemplar(Exemplar e) throws ClassNotFoundException, SQLException {	
		String type = "";
		
		e = lDAO.findOne((Livro) e);
		if (e.getNome() != null) {
			type = "Livro";
			e = null;
			return type;
		}
		
		e = rDAO.findOne((Revista) e);
		if (e.getNome() != null) {
			type = "Revista";
			e = null;
			return type;
		}
		
		return type;
	}

	@Override
	public Exemplar createExemplar(String p, String cod, String nome, String qtdPag, String isbn, String edicao) throws Exception {
		Exemplar e = new Livro();
		e.setCodigo(Integer.parseInt(cod));
		e.setNome(nome);
		e.setQtdPaginas(Integer.parseInt(qtdPag));
		return e;
	}
	
}
