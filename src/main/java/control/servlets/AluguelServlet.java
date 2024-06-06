package control.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Aluguel;
import model.Aluno;
import model.Exemplar;

import java.io.IOException;
import java.util.List;

import control.AluguelController;
import control.factory.IAluguelFactory;

@WebServlet("/alugar")
public class AluguelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public AluguelServlet() {
    	super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AluguelController aluguelControl = new AluguelController();
		List<Aluno> alunos = null;
		List<Exemplar> exemplares = null;
		
		String err = "";
		
		try {
			alunos = aluguelControl.findAlunos();
			exemplares = aluguelControl.buscaExemplares();
		} catch(Exception e) {
			err = e.getMessage();
		} finally {
			request.setAttribute("err", err);
			request.setAttribute("alunos", alunos);
			request.setAttribute("exemplares", exemplares);
			RequestDispatcher rd = request.getRequestDispatcher("alugar.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IAluguelFactory factory = new AluguelController();
		AluguelController aluguelCont = new AluguelController();
		
		List<Aluguel> alugueis = null;
		List<Exemplar> exemplares = null;
		List<Aluno> alunos = null;
		Aluguel aluguel = null;
		
		//input dos dados
		String params = request.getParameter("Button");
		String alunoRA = request.getParameter("alunoRA");
		String codExemplar = request.getParameter("codExemplar");
		String dataRet = request.getParameter("dataRet");
		String dataDev = request.getParameter("dataDev");
		
		// output dos dados
		String out = "";
		String err = "";
		try {
			alunos = aluguelCont.findAlunos();
			exemplares = aluguelCont.buscaExemplares();
			
			aluguel = factory.createAluguel(params, alunoRA, codExemplar, dataRet, dataDev);
			
			if (params.contains("Cadastrar")) {
				aluguelCont.inserir(aluguel);
				out = "Aluguel cadastrado com sucesso!";
				aluguel = null;
			}

			if (params.contains("Alterar")) {
				aluguelCont.atualizar(aluguel);
				out = "Aluguel alterado com sucesso!";
				aluguel = null;
			}

			if (params.contains("Excluir")) {
				aluguelCont.deletar(aluguel);
				out = "Aluguel excluído com sucesso!";
				aluguel = null;
			}

			if (params.contains("Buscar")) {
				aluguel = aluguelCont.consultarUm(aluguel);
			}

			if (params.contains("Listar")) {
				alugueis = aluguelCont.listarTodos();
			}

		} catch(Exception e) {
			err = e.getMessage();
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("saida", out);
			request.setAttribute("err", err);
			request.setAttribute("alunos", alunos);
			request.setAttribute("exemplares", exemplares);
			request.setAttribute("aluguel", aluguel);
			request.setAttribute("alugueis", alugueis);
			
			RequestDispatcher rd = request.getRequestDispatcher("alugar.jsp");
			rd.forward(request, response);
		}
	}

}
