package control.factory;

import model.Aluguel;

//Simple Factory
public interface IAluguelFactory {
	Aluguel createAluguel(String params, String ra, String codExemplar, String dataRet, String dataDev) throws Exception;
}
