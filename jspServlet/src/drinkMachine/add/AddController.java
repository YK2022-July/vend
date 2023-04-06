package drinkMachine.add;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import drinkMachine.dao.T001_ItemDao;

/**
 * Servlet implementation class AddController
 */
public class AddController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddController() {
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
		response.setContentType("text/html:charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		// パラメーター" code "の値を取り出し、変数Codeに代入（商品コードの入力）
		String Code = request.getParameter("code");

		// コンソールに表示する
		System.out.println("商品コード：" + Code);

		// パラメーター" name "の値を取り出し、変数Nameに代入（商品名の入力）
		String Name = request.getParameter("name");

		// コンソールに表示する
		System.out.println("商品名：" + Name);

		// パラメーター" unitPrice "の値を取り出し、変数Priceに代入（金額の入力）
		String Price = request.getParameter("unitPrice");

		// パラメーター" count "の値を取り出し、変数Countに代入（数量の入力）
		String Count = request.getParameter("count");

		// Daoのコントラクタの呼び出し
		T001_ItemDao itemDao;

		try {
			itemDao = new T001_ItemDao();

			// 重複チェックメソッド（checkAdd）の呼び出し
			String checkItem = itemDao.checkItem(Name);
			String nextPage = null;

			// 重複していたら登録画面へ
			if (!checkItem.equals("null0")) {
				request.setAttribute("kensaku", "入力した商品は既に登録されています。"
						+ "商品名を変更して登録し直してください");
				nextPage = "/add.jsp";

				// 重複していなかったら
			} else {
				// 商品登録メソッド（addItem）の呼び出し
				itemDao = new T001_ItemDao();
				int result = itemDao.addItem(Name, Price, Count);

				// 画面遷移先判定 成功→一覧画面 失敗→登録画面
				if (result == 1) {
					nextPage = "/list.jsp";
				} else {
					nextPage = "/add.jsp";
				}

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
