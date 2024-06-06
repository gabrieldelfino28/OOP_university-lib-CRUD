package perseverance;

import java.sql.SQLException;
import java.util.List;

public interface ICRUDDao<T> {
	void insert(T t) throws SQLException, ClassNotFoundException;

	int update(T t) throws SQLException, ClassNotFoundException;

	void delete(T t) throws SQLException, ClassNotFoundException;

	T findOne(T t) throws SQLException, ClassNotFoundException;

	List<T> findAll() throws SQLException, ClassNotFoundException;
}
