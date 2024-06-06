package control;

import java.sql.SQLException;
import java.util.List;

import control.strategy.IStrategyController;
import model.Exemplar;
import model.Revista;

import perseverance.GenericDAO;
import perseverance.ICRUDDao;
import perseverance.RevistaDAO;

public class RevistaController extends ExemplarCollection implements IStrategyController<Exemplar>{

	private GenericDAO gDAO;
	private ICRUDDao<Revista> rDAO;
	
	public RevistaController() {
		gDAO = new GenericDAO();
		rDAO = new RevistaDAO(gDAO);
	}
	
	@Override
	public Exemplar createExemplar(String cmd, String cod, String nome, String qtdPag, String ISSN, String none) throws Exception {
		Revista revista = new Revista();
		if (!cmd.contains("Listar")) {
			if (cod.isEmpty()) throw new Exception("Err: Código Vazio!");
			revista.setCodigo(Integer.parseInt(cod));
		}

		if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
			if (nome.isEmpty() || qtdPag.isEmpty() || ISSN.isEmpty()) throw new Exception("Err: Campos Vazios!");
			revista.setNome(nome);
			revista.setQtdPaginas(Integer.parseInt(qtdPag));
			revista.setISSN(ISSN);
		}
		return revista;
	}
	
	@Override
	public void inserir(Exemplar revista) throws SQLException, ClassNotFoundException {
		rDAO.insert((Revista) revista);
	}
	
	@Override
	public int atualizar(Exemplar revista) throws SQLException, ClassNotFoundException {
		rDAO.update((Revista) revista);
		return revista.getCodigo();
	}
	
	@Override
	public void deletar(Exemplar revista) throws SQLException, ClassNotFoundException {
		rDAO.delete((Revista) revista);
	}
	
	@Override
	public Exemplar consultarUm(Exemplar revista) throws SQLException, ClassNotFoundException {
		revista = rDAO.findOne((Revista) revista);
		return revista;
	}
	
	@Override
	public List<Exemplar> listarTodos() throws SQLException, ClassNotFoundException {
		List<Exemplar> revistas;
		revistas = List.copyOf(rDAO.findAll());
		return revistas;
	}
}