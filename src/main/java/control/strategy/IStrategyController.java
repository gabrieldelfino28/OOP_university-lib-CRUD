package control.strategy;

import java.sql.SQLException;
import java.util.List;

public interface IStrategyController<T> {
	void inserir(T t) throws SQLException, ClassNotFoundException;

	int atualizar(T t) throws SQLException, ClassNotFoundException;

	void deletar(T t) throws SQLException, ClassNotFoundException;

	T consultarUm(T t) throws SQLException, ClassNotFoundException;

	List<T> listarTodos() throws SQLException, ClassNotFoundException;
}
