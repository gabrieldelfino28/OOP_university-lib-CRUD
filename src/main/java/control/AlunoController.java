package control;

import java.sql.SQLException;
import java.util.List;

import control.factory.IAlunoFactory;
import control.strategy.IStrategyController;
import model.Aluno;
import perseverance.AlunoDAO;
import perseverance.GenericDAO;
import perseverance.ICRUDDao;

public class AlunoController implements IStrategyController<Aluno>, IAlunoFactory {

	private GenericDAO gDAO;
	private ICRUDDao<Aluno> aDAO;

	public AlunoController() {
		gDAO = new GenericDAO();
		aDAO = new AlunoDAO(gDAO);
	}
	
	@Override
	public Aluno createAluno(String cmd, String RA, String nome, String email) throws Exception {
		Aluno aluno = new Aluno();

		if (!cmd.contains("Listar")) {
			if (RA.isEmpty()) throw new Exception("Err: RA Vazio!");
			aluno.setRA(Integer.parseInt(RA));
		}

		if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
			if (nome.isEmpty() || email.isEmpty()) throw new Exception("Err: Nome, Email Vazios!");
			aluno.setNome(nome);
			aluno.setEmail(email);
		}
		return aluno;
	}

	@Override
	public void inserir(Aluno a) throws SQLException, ClassNotFoundException {
		aDAO.insert(a);
	}

	@Override
	public int atualizar(Aluno a) throws SQLException, ClassNotFoundException {
		aDAO.update(a);
		return a.getRA();
	}

	@Override
	public void deletar(Aluno a) throws SQLException, ClassNotFoundException {
		aDAO.delete(a);
	}

	@Override
	public Aluno consultarUm(Aluno a) throws SQLException, ClassNotFoundException {
		return aDAO.findOne(a);
	}

	@Override
	public List<Aluno> listarTodos() throws SQLException, ClassNotFoundException {
		return aDAO.findAll();
	}
}