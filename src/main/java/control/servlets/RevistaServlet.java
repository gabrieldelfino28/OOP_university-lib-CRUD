package control.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import model.Exemplar;
import control.RevistaController;
import control.factory.IExemplarFactory;
import control.strategy.IStrategyController;

@WebServlet("/revista")
public class RevistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RevistaServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("revista.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Acoplamento Abstrato
		IStrategyController<Exemplar> exemplarCont = new RevistaController();
		IExemplarFactory factory = new RevistaController();

		List<Exemplar> exemplares = null;
		Exemplar exemplar = null;

		// input de dados
		String params = request.getParameter("Button");
		String cod = request.getParameter("cod");
		String nome = request.getParameter("nome");
		String qtdPags = request.getParameter("qtdpags");
		String issn = request.getParameter("ISSN");

		// output dos dados
		String out = "";
		String err = "";

		try {
			exemplar = factory.createExemplar(params, cod, nome, qtdPags, issn, null);

			if (params.contains("Cadastrar")) {
				exemplarCont.inserir(exemplar);
				out = "Revista cadastrada com sucesso!";
				exemplar = null;
			}

			if (params.contains("Alterar")) {
				exemplarCont.atualizar(exemplar);
				out = "Revista alterada com sucesso!";
				exemplar = null;
			}

			if (params.contains("Excluir")) {
				exemplarCont.deletar(exemplar);
				out = "Revista excluída com sucesso!";
				exemplar = null;
			}

			if (params.contains("Buscar")) {
				exemplar = exemplarCont.consultarUm(exemplar);
			}

			if (params.contains("Listar")) {
				exemplares = exemplarCont.listarTodos();
			}

		} catch (Exception e) {
			err = e.getMessage();
		} finally {
			request.setAttribute("saida", out);
			request.setAttribute("err", err);
			request.setAttribute("revista", exemplar);
			request.setAttribute("revistas", exemplares);

			RequestDispatcher rd = request.getRequestDispatcher("revista.jsp");
			rd.forward(request, response);
		}
	}
}