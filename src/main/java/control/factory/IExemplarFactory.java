package control.factory;

import model.Exemplar;

//Factory Method
public interface IExemplarFactory {
	Exemplar createExemplar(String params, String cod, String nome, String qtdPag, String ISBN_or_ISSN, String edicao) throws Exception;
}
