package perseverance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import control.AluguelController;
import control.AlunoController;
import control.ExemplarCollection;
import control.LivroController;
import control.RevistaController;
import control.factory.IAluguelFactory;
import control.factory.IAlunoFactory;
import control.factory.IExemplarFactory;
import control.strategy.IStrategyCollection;
import model.Aluguel;
import model.Aluno;
import model.Exemplar;
import model.Livro;
import model.Revista;

@SuppressWarnings("all")
public class AluguelDAO implements ICRUDDao<Aluguel> {

	private GenericDAO gDAO;

	public AluguelDAO(GenericDAO gDAO) {
		this.gDAO = gDAO;
	}

	@Override
	public void insert(Aluguel al) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		String SQL = "INSERT INTO Aluguel VALUES(?,?,?,?)";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setInt(1, al.getExemplar().getCodigo());
		ps.setInt(2, al.getAluno().getRA());
		ps.setString(3, al.getDataRetirada().toString());
		ps.setString(4, al.getDataDevolucao().toString());
		ps.execute();
		ps.close();

		c.close();
	}

	@Override
	public int update(Aluguel al) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		String SQL = "UPDATE Aluguel SET data_devolucao = ? WHERE codigo = ? AND ra = ? AND data_retirada = ?";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setString(1, al.getDataDevolucao().toString());
		ps.setInt(2, al.getExemplar().getCodigo());
		ps.setInt(3, al.getAluno().getRA());
		ps.setString(4, al.getDataRetirada().toString());
		ps.execute();
		ps.close();

		c.close();
		return 0;
	}

	@Override
	public void delete(Aluguel al) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		String SQL = "DELETE FROM Aluguel WHERE codigo = ? AND ra = ? AND data_retirada = ?";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setString(1, al.getDataDevolucao().toString());
		ps.setInt(2, al.getExemplar().getCodigo());
		ps.setInt(3, al.getAluno().getRA());
		ps.setString(4, al.getDataRetirada().toString());
		ps.execute();
		ps.close();

		c.close();
	}

	@Override
	public Aluguel findOne(Aluguel al) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		StringBuffer SQL = new StringBuffer();
		IAlunoFactory aFact = new AlunoController();
		IExemplarFactory eFact = new ExemplarCollection();

		SQL.append("SELECT al.data_retirada, al.data_devolucao, a.*, e.codigo, e.nome as nome_exemplar, e.qtd_paginas ");
		SQL.append("FROM Aluguel al INNER JOIN Aluno a ON al.ra = a.ra ");
		SQL.append("INNER JOIN Exemplar e ON al.codigo = e.codigo ");
		SQL.append("WHERE al.data_retirada = ?");

		PreparedStatement firstStatement = c.prepareStatement(SQL.toString());
		firstStatement.setString(1, al.getDataRetirada().toString());
		ResultSet RS = firstStatement.executeQuery();
		try {
			if (RS.next()) {
				Exemplar exemplar = 
						 eFact.createExemplar(null, 
						 String.valueOf(RS.getInt("codigo")), RS.getString("nome_exemplar"), String.valueOf(RS.getInt("qtd_paginas")), 
						 null, null);
				Aluno aluno = aFact.createAluno("Cadastro", String.valueOf(RS.getInt("ra")), RS.getString("nome"), RS.getString("email"));

				al.setDataRetirada(RS.getDate("data_retirada").toLocalDate());
				al.setDataDevolucao(RS.getDate("data_devolucao").toLocalDate());
				al.setExemplar(exemplar);
				al.setAluno(aluno);
			}
		} catch (Exception err) {
			System.out.println(err.getLocalizedMessage());
		}
		firstStatement.close();
		c.close();
		return al;
	}

	@Override
	public List<Aluguel> findAll() throws SQLException, ClassNotFoundException {
		List<Aluguel> alugueis = new ArrayList<Aluguel>();
		
		IStrategyCollection col = new ExemplarCollection();
		
		Connection c = gDAO.getConnection();

		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT al.data_retirada, al.data_devolucao, a.*, e.codigo, e.nome as nome_exemplar, e.qtd_paginas ");
		SQL.append("FROM Aluguel al INNER JOIN Aluno a ON al.ra = a.ra ");
		SQL.append("INNER JOIN Exemplar e ON al.codigo = e.codigo");
		
		PreparedStatement ps = c.prepareStatement(SQL.toString());
		ResultSet RS = ps.executeQuery();
		try {
			while (RS.next()) {
				Aluguel al = new Aluguel();
				
				Exemplar exemplar = col.findExemplar(String.valueOf(RS.getInt("codigo")));
				Aluno aluno = col.findAluno(String.valueOf(RS.getInt("ra")));
				
				al.setDataRetirada(RS.getDate("data_retirada").toLocalDate());
				al.setDataDevolucao(RS.getDate("data_devolucao").toLocalDate());
				al.setExemplar(exemplar);
				al.setAluno(aluno);
				
				alugueis.add(al);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		ps.close();
		c.close();
		return alugueis;
	}
}
