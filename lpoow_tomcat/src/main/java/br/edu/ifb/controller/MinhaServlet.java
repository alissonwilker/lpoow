package br.edu.ifb.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import br.edu.ifb.model.Employee;

/**
 * Servlet implementation class MinhaServlet
 */
public class MinhaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(MinhaServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MinhaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		SessionFactory sessionFactory = (SessionFactory) request.getServletContext().getAttribute("SessionFactory");
		Session sessao = null;
		Transaction transacao = null;
		Employee employee = null;
		try {
			sessao = sessionFactory.getCurrentSession();
			transacao = sessao.beginTransaction();
			employee = (Employee) sessao.get(Employee.class, 3);
			transacao.commit();
		} catch (RuntimeException ex) {
			logger.error("Ocorreu um erro durante o processamento da transação com o banco de dados.", ex);
			if (transacao != null) {
				transacao.rollback();
			}
		} finally {
			if (sessao != null) {
				sessao.close();
			}
		}
		
		response.getWriter().append("\nO nome do empregado com id 3 é: " + employee.getName());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
