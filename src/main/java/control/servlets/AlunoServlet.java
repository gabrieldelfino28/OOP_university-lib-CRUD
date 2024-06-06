package control.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;

import java.io.IOException;
import java.util.List;

import control.AlunoController;
import control.factory.IAlunoFactory;
import control.strategy.IStrategyController;

@WebServlet("/aluno")
public class AlunoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public AlunoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("aluno.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IStrategyController<Aluno> alunoControl = new AlunoController();
		IAlunoFactory factory = new AlunoController();
		Aluno aluno = null;
		List<Aluno> alunos = null;
		
		// input de dados
		String params = request.getParameter("Button");
		String ra = request.getParameter("ra");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");

		// output dos dados
		String out = "";
		String err = "";

		try {
			aluno = factory.createAluno(params, ra, nome, email);
			
			if (params.contains("Cadastrar")) {
				alunoControl.inserir(aluno);
				out = "Aluno cadastrado com sucesso!";
				aluno = null;
			}

			if (params.contains("Alterar")) {
				alunoControl.atualizar(aluno);
				out = "Aluno alterado com sucesso!";
				aluno = null;
			}

			if (params.contains("Excluir")) {
				alunoControl.deletar(aluno);
				out = "Aluno excluído com sucesso!";
				aluno = null;
			}

			if (params.contains("Buscar")) {
				aluno = alunoControl.consultarUm(aluno);
			}

			if (params.contains("Listar")) {
				alunos = alunoControl.listarTodos();
			}
			
		} catch (Exception e) {
			err = e.getMessage();
		} finally {
			request.setAttribute("saida", out);
			request.setAttribute("err", err);
			request.setAttribute("aluno", aluno);
			request.setAttribute("alunos", alunos);

			RequestDispatcher rd = request.getRequestDispatcher("aluno.jsp");
			rd.forward(request, response);
		}
	}

}
