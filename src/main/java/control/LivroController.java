package control;

import java.sql.SQLException;
import java.util.List;

import control.strategy.IStrategyController;
import model.Exemplar;
import model.Livro;
import perseverance.GenericDAO;
import perseverance.ICRUDDao;
import perseverance.LivroDAO;

public class LivroController extends ExemplarCollection implements IStrategyController<Exemplar>{
	
	private GenericDAO gDAO;
	private ICRUDDao<Livro> lDAO;
	
	public LivroController() {
		gDAO = new GenericDAO();
		lDAO = new LivroDAO(gDAO);
	}

	@Override
	public Exemplar createExemplar(String params, String cod, String nome, String qtdPag, String ISBN, String edicao) throws Exception{
		Livro livro = new Livro();
		if (!params.contains("Listar")) {
			if (cod.isEmpty()) throw new Exception("Err: Código Vazio!");
			livro.setCodigo(Integer.parseInt(cod));
		}

		if (params.contains("Cadastrar") || params.contains("Alterar")) {
			if (nome.isEmpty() || qtdPag.isEmpty() || ISBN.isEmpty() || edicao.isEmpty()) throw new Exception("Err: Campos Vazios!");
			livro.setNome(nome);
			livro.setQtdPaginas(Integer.parseInt(qtdPag));
			livro.setISBN(ISBN);
			livro.setEdicao(Integer.parseInt(edicao));
		}
		return livro;
	}
	
	
	@Override
	public void inserir(Exemplar livro) throws SQLException, ClassNotFoundException {
		lDAO.insert((Livro) livro);
	}

	@Override
	public int atualizar(Exemplar livro) throws SQLException, ClassNotFoundException {
		lDAO.update((Livro) livro);
		return livro.getCodigo();
	}

	@Override
	public void deletar(Exemplar livro) throws SQLException, ClassNotFoundException {
		lDAO.delete((Livro) livro);
	}

	@Override
	public Exemplar consultarUm(Exemplar livro) throws SQLException, ClassNotFoundException {
		livro = lDAO.findOne((Livro) livro);
		return livro;
	}

	@Override
	public List<Exemplar> listarTodos() throws SQLException, ClassNotFoundException {
		List<Exemplar> livros;
		livros = List.copyOf(lDAO.findAll());
		return livros;
	}
}