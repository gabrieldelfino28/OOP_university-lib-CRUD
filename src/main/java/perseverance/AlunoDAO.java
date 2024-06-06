package perseverance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;

public class AlunoDAO implements ICRUDDao<Aluno> {
	
	private GenericDAO gDAO;
	
	public AlunoDAO(GenericDAO gDAO) {
		this.gDAO = gDAO;
	}

	@Override
	public void insert(Aluno a) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		String SQL = "INSERT INTO aluno VALUES (?,?,?)";
		
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setInt(1, a.getRA());
		ps.setString(2, a.getNome());
		ps.setString(3, a.getEmail());
		
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public int update(Aluno a) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		String SQL = "UPDATE aluno SET nome = ?, email = ? WHERE ra = ?";
		
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setString(1, a.getNome());
		ps.setString(2, a.getEmail());
		ps.setInt(3, a.getRA());
	
		ps.execute();
		ps.close();
		c.close();
		return a.getRA();
	}

	@Override
	public void delete(Aluno a) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		String SQL = "DELETE FROM aluno WHERE ra = ?";
		
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setInt(1, a.getRA());
		
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public Aluno findOne(Aluno a) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		String SQL = "SELECT * FROM aluno WHERE ra = ?";
		
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setInt(1, a.getRA());
		
		ResultSet result = ps.executeQuery();
		if (result.next()) {
			a.setRA(result.getInt("ra"));
			a.setNome(result.getString("nome"));
			a.setEmail(result.getString("email"));
		}
		ps.execute();
		ps.close();
		c.close();
		return a;
	}

	@Override
	public List<Aluno> findAll() throws SQLException, ClassNotFoundException {
		List<Aluno> alunos = new ArrayList<Aluno>();
		Connection c = gDAO.getConnection();
		String SQL = "SELECT * FROM aluno";
		
		PreparedStatement ps = c.prepareStatement(SQL);
		
		ResultSet result = ps.executeQuery();
		while (result.next()) {
			Aluno a = new Aluno();
			a.setRA(result.getInt("ra"));
			a.setNome(result.getString("nome"));
			a.setEmail(result.getString("email"));
			alunos.add(a);
		}
		ps.execute();
		ps.close();
		c.close();
		return alunos;
	}

}
