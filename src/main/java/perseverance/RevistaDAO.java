package perseverance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Revista;

public class RevistaDAO implements ICRUDDao<Revista> {

	private GenericDAO gDAO;

	public RevistaDAO(GenericDAO gDAO) {
		this.gDAO = gDAO;
	}

	@Override
	public void insert(Revista r) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		StringBuffer SQL = new StringBuffer("INSERT INTO exemplar VALUES (?,?,?)");
		PreparedStatement ps = c.prepareStatement(SQL.toString());
		ps.setInt(1, r.getCodigo());
		ps.setString(2, r.getNome());
		ps.setInt(3, r.getQtdPaginas());
		ps.execute();
		ps.close();
		// Limpeza para proximo Statement
		ps = null;
		// New statement
		SQL = new StringBuffer("INSERT INTO revista(codigo, issn) ");
		SQL.append("VALUES ((SELECT codigo FROM exemplar WHERE nome = ?),?)");
		ps = c.prepareStatement(SQL.toString());
		ps.setString(1, r.getNome());
		ps.setString(2, r.getISSN());
		ps.execute();
		ps.close();
		
		c.close();
	}

	@Override
	public int update(Revista r) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		String SQL = "UPDATE exemplar SET nome = ?, qtd_paginas = ? WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setString(1, r.getNome());
		ps.setInt(2, r.getQtdPaginas());
		ps.setInt(3, r.getCodigo());
		ps.execute();
		ps.close();
		
		//Limpeza para proximo Statement
		ps = null;
		SQL = null;
		//New statement
		
		SQL = "UPDATE livro SET issn = ? WHERE codigo = (SELECT codigo FROM exemplar)";
		ps = c.prepareStatement(SQL);
		ps.setString(1, r.getISSN());
		ps.execute();
		ps.close();
		
		c.close();
		return r.getCodigo();
	}

	@Override
	public void delete(Revista r) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		String SQL = "DELETE FROM revista WHERE codigo = (SELECT codigo FROM exemplar WHERE codigo = ?)";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setInt(1, r.getCodigo());
		ps.execute();
		ps.close();
		
		ps = null;
		SQL = null;
		
		SQL = "DELETE FROM exemplar WHERE codigo = ?";
		ps = c.prepareStatement(SQL);
		ps.setInt(1, r.getCodigo());
		ps.execute();
		ps.close();
		
		c.close();
	}

	@Override
	public Revista findOne(Revista r) throws SQLException, ClassNotFoundException {
		Connection c = gDAO.getConnection();
		StringBuffer SQL = new StringBuffer("SELECT e.codigo, e.nome, e.qtd_paginas, r.issn ");
		SQL.append("FROM exemplar e INNER JOIN revista r ON e.codigo = r.codigo ");
		SQL.append("WHERE e.codigo = ?");
		PreparedStatement ps = c.prepareStatement(SQL.toString());
		ps.setInt(1, r.getCodigo());
		
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			r.setCodigo(rs.getInt("e.codigo"));
			r.setNome(rs.getString("e.nome"));
			r.setQtdPaginas(rs.getInt("e.qtd_paginas"));
			r.setISSN(rs.getString("r.issn"));
		}
		rs.close();
		ps.close();

		c.close();
		return r;
	}

	@Override
	public List<Revista> findAll() throws SQLException, ClassNotFoundException {
		List<Revista> revistas = new ArrayList<Revista>();
		Connection c = gDAO.getConnection();
		StringBuffer SQL = new StringBuffer("SELECT e.codigo, e.nome, e.qtd_paginas, r.issn ");
		SQL.append("FROM exemplar e INNER JOIN revista r ON e.codigo = r.codigo");
		PreparedStatement ps = c.prepareStatement(SQL.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Revista r = new Revista();
			
			r.setCodigo(rs.getInt("e.codigo"));
			r.setNome(rs.getString("e.nome"));
			r.setQtdPaginas(rs.getInt("e.qtd_paginas"));
			r.setISSN(rs.getString("r.issn"));
			revistas.add(r);
		}
		rs.close();
		ps.close();

		c.close();
		return revistas;
	}

}
