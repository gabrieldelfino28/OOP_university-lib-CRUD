package control;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import control.factory.IAluguelFactory;
import control.factory.IAlunoFactory;
import control.strategy.IStrategyCollection;
import control.strategy.IStrategyController;
import model.Aluguel;
import model.Aluno;
import model.Exemplar;

import perseverance.AluguelDAO;
import perseverance.GenericDAO;
import perseverance.ICRUDDao;

public class AluguelController implements IAluguelFactory, IStrategyController<Aluguel>{
	
	private GenericDAO gDAO;
	private ICRUDDao<Aluguel> alDAO;
	
	public AluguelController() {
		gDAO = new GenericDAO();
		alDAO = new AluguelDAO(gDAO);
	}

	@Override
	public Aluguel createAluguel(String params, String alunoRA, String codExemplar, String dataRet, String dataDev) throws Exception{
		Aluguel al = new Aluguel();
		if (!params.contains("Listar")) {
			if (dataRet.isEmpty()) throw new Exception("Err: Data_Retirada Vazia!");
			
			al.setDataRetirada(LocalDate.parse(dataRet));
		}
		if (params.contains("Cadastrar") || params.contains("Alterar")) {
			if (alunoRA.isEmpty() || codExemplar.isEmpty() || dataDev.isEmpty()) throw new Exception("Err: Campos Vazios!");
			
			Aluno a = findAluno(params, alunoRA);
			Exemplar e = buscaExemplar(params,codExemplar);
			al.setAluno(a);
			al.setExemplar(e);
			al.setDataDevolucao(LocalDate.parse(dataDev));
		}
		return al;
	}
	
	private Aluno findAluno(String params, String alunoRA) throws Exception {
		IAlunoFactory factory = new AlunoController();
		IStrategyController<Aluno> control = new AlunoController();
		
		Aluno aluno = factory.createAluno(params, alunoRA, "placeholder", "placeholder");
		aluno = control.consultarUm(aluno);
		return aluno;
	}
	
	public List<Aluno> findAlunos() throws Exception{
		IStrategyController<Aluno> control = new AlunoController();
		return control.listarTodos();
	}
	
	private Exemplar buscaExemplar(String params, String codExemplar) throws Exception {
		IStrategyCollection collection = new ExemplarCollection();
		Exemplar e = collection.findExemplar(codExemplar);
		return e;
	}
	
	public List<Exemplar> buscaExemplares() throws Exception {
		IStrategyCollection collection = new ExemplarCollection();
		return collection.findExemplares();
	}
	
	@Override
	public void inserir(Aluguel al) throws SQLException, ClassNotFoundException {
		alDAO.insert(al);
	}

	@Override
	public int atualizar(Aluguel al) throws SQLException, ClassNotFoundException {
		alDAO.update(al);
		return 0;
	}

	@Override
	public void deletar(Aluguel al) throws SQLException, ClassNotFoundException {
		alDAO.delete(al);
	}

	@Override
	public Aluguel consultarUm(Aluguel al) throws SQLException, ClassNotFoundException {
		return alDAO.findOne(al);
	}

	@Override
	public List<Aluguel> listarTodos() throws SQLException, ClassNotFoundException {
		return alDAO.findAll();
	}
}
