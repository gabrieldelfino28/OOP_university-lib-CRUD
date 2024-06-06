package control.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Exemplar;

import java.io.IOException;
import java.util.List;

import control.LivroController;
import control.factory.IExemplarFactory;
import control.strategy.IStrategyController;

@WebServlet("/livro")
public class LivroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LivroServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("livro.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Acoplamento Abstrato
		IStrategyController<Exemplar> exemplarControl = new LivroController();
		IExemplarFactory factory = new LivroController();
		
		List<Exemplar> exemplares = null;
		Exemplar exemplar = null;

		// input de dados
		String params = request.getParameter("Button");
		String cod = request.getParameter("cod");
		String nome = request.getParameter("nome");
		String qtdPags = request.getParameter("qtdpags");
		String isbn = request.getParameter("ISBN");
		String edicao = request.getParameter("edicao");

		// output dos dados
		String out = "";
		String err = "";
		
		try {
			exemplar = factory.createExemplar(params, cod, nome, qtdPags, isbn, edicao);

			if (params.contains("Cadastrar")) {
				exemplarControl.inserir(exemplar);
				out = "Livro cadastrado com sucesso!";
				exemplar = null;
			}

			if (params.contains("Alterar")) {
				exemplarControl.atualizar(exemplar);
				out = "Livro alterado com sucesso!";
				exemplar = null;
			}

			if (params.contains("Excluir")) {
				exemplarControl.deletar(exemplar);
				out = "Livro excluído com sucesso!";
				exemplar = null;
			}

			if (params.contains("Buscar")) {
				exemplar = exemplarControl.consultarUm(exemplar);
			}

			if (params.contains("Listar")) {
				exemplares = exemplarControl.listarTodos();
			}
			
		} catch (Exception e) {
			err = e.getMessage();
		} finally {
			request.setAttribute("saida", out);
			request.setAttribute("err", err);
			request.setAttribute("livro", exemplar);
			request.setAttribute("livros", exemplares);

			RequestDispatcher rd = request.getRequestDispatcher("livro.jsp");
			rd.forward(request, response);
		}
	}

}
