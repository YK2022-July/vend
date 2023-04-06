package drinkMachine.add;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import drinkMachine.dao.T001_ItemDao;

/**
 * Servlet implementation class ListController
 */
public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// 受け取るデータの文字コードをUTF-8にセットする
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// パラメーター"code"の値を取り出し、変数Codeに代入（商品コード）
		String Code = request.getParameter("code");

		// コンソールに表示する
		System.out.println("商品コード：" + Code);

		// パラメーター"name"の値を取り出し、変数Nameに代入（商品名）
		String Name = request.getParameter("name");

		// コンソールに表示する
		System.out.println("商品名：" + Name);

		// Daoのコントラクタの呼び出し
		T001_ItemDao itemDao;

		try {
			itemDao = new T001_ItemDao();

			// データベース内から検索するメソッドの呼び出し
			List<ItemBean> list = itemDao.searchItem(Code,Name);
			String nextPage = null;

			// 検索したデータの値が0でなければテーブルに検索結果を表示する
			if (list.size() != 0) {
				// Beanにリストを送る
				request.getSession().setAttribute("sItm",list);
				nextPage = "/list.jsp";

				// 0だったらエラーメッセージを表示
			} else {
				String errorMessage = "検索条件に一致するデータがありません。" + "もう一度入力してください";
				request.setAttribute("error", errorMessage);
				nextPage = "/list.jsp";

			}

			// Dispatcherの中に次に遷移する文字列nextPageを代入する
			ServletContext application = getServletContext();
			application.getRequestDispatcher(nextPage).forward(request,
					response);

		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
