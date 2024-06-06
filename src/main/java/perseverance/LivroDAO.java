package perseverance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Livro;

public class LivroDAO implements ICRUDDao<Livro>{
	
	private GenericDAO gDAO;
	
	public LivroDAO(GenericDAO gDAO) {
		this.gDAO = gDAO;
	}

	@Override
	public void insert(Livro l) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		StringBuffer SQL = new StringBuffer("INSERT INTO exemplar VALUES (?,?,?)");
		PreparedStatement ps = c.prepareStatement(SQL.toString());
		ps.setInt(1, l.getCodigo());
		ps.setString(2, l.getNome());
		ps.setInt(3, l.getQtdPaginas());
		ps.execute();
		ps.close();
		
		//Limpeza para proximo Statement
		ps = null;
		SQL = null;
		//New statement	
		SQL = new StringBuffer("INSERT INTO livro(codigo, isbn, edicao) ");
		SQL.append("VALUES ((SELECT codigo FROM exemplar WHERE nome = ?),?,?)");
		ps = c.prepareStatement(SQL.toString());
		ps.setString(1, l.getNome());
		ps.setString(2, l.getISBN());
		ps.setInt(3, l.getEdicao());
		ps.execute();
		ps.close();
		
		c.close();
	}

	@Override
	public int update(Livro l) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		String SQL = "UPDATE exemplar SET nome = ?, qtd_paginas = ? WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setString(1, l.getNome());
		ps.setInt(2, l.getQtdPaginas());
		ps.setInt(3, l.getCodigo());
		ps.execute();
		ps.close();
		
		//Limpeza para proximo Statement
		ps = null;
		SQL = null;
		//New statement
		
		SQL = "UPDATE livro SET isbn = ?, edicao = ? WHERE codigo = (SELECT codigo FROM exemplar)";
		ps = c.prepareStatement(SQL);
		ps.setString(1, l.getISBN());
		ps.setInt(2, l.getEdicao());
		ps.execute();
		ps.close();
		
		c.close();
		return l.getCodigo();
	}

	@Override
	public void delete(Livro l) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		String SQL = "DELETE FROM livro WHERE codigo = (SELECT codigo FROM exemplar WHERE codigo = ?)";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setInt(1, l.getCodigo());
		ps.execute();
		ps.close();
		
		ps = null;
		SQL = null;
		
		SQL = "DELETE FROM exemplar WHERE codigo = ?";
		ps = c.prepareStatement(SQL);
		ps.setInt(1, l.getCodigo());
		ps.execute();
		ps.close();
		
		c.close();
	}

	@Override
	public Livro findOne(Livro l) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		StringBuffer SQL = new StringBuffer("SELECT e.codigo, e.nome, e.qtd_paginas, l.isbn, l.edicao ");
		SQL.append("FROM exemplar e INNER JOIN livro l ON e.codigo = l.codigo ");
		SQL.append("WHERE e.codigo = ?");
		PreparedStatement ps = c.prepareStatement(SQL.toString());
		ps.setInt(1, l.getCodigo());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			l.setCodigo(rs.getInt("e.codigo"));
			l.setNome(rs.getString("e.nome"));
			l.setQtdPaginas(rs.getInt("e.qtd_paginas"));
			l.setISBN(rs.getString("l.isbn"));
			l.setEdicao(rs.getInt("l.edicao"));
		}
		rs.close();
		ps.close();
		
		c.close();
		return l;
	}

	@Override
	public List<Livro> findAll() throws SQLException, ClassNotFoundException {
		List<Livro> livros = new ArrayList<>();
		Connection c = gDAO.getConnection();
		StringBuffer SQL = new StringBuffer("SELECT e.codigo, e.nome, e.qtd_paginas, l.isbn, l.edicao ");
		SQL.append("FROM exemplar e INNER JOIN livro l ON e.codigo = l.codigo");
		PreparedStatement ps = c.prepareStatement(SQL.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Livro l = new Livro(); 
			l.setCodigo(rs.getInt("e.codigo"));
			l.setNome(rs.getString("e.nome"));
			l.setQtdPaginas(rs.getInt("e.qtd_paginas"));
			l.setISBN(rs.getString("l.isbn"));
			l.setEdicao(rs.getInt("l.edicao"));
			livros.add(l);
		}
		rs.close();
		ps.close();
		
		c.close();
		return livros;
	}
}

