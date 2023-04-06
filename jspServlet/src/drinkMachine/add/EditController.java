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
 * Servlet implementation class EditController
 */
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditController() {
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

		// パラメーター" code "の値を取り出し、変数Codeに代入（商品コードの入力）
		String Code = request.getParameter("code");

		// コンソールに表示する
		System.out.println("商品コード：" + Code);


		// Daoのコントラクタの呼び出し
		T001_ItemDao itemDao;

		try {
			itemDao = new T001_ItemDao();

			// 商品情報の取得メソッドの呼び出し
			ItemBean itembean = itemDao.editItm(Code);
			String nextPage = null;

			// 検索したデータの値が0でなければテーブルに検索結果を表示する
			if (!itembean.equals(0)) {
				String beforeDate = itembean.getRecord();
				// Beanに商品データを送る
				request.setAttribute("itembean", itembean);
				request.getSession().setAttribute("RECORD_DATE", beforeDate);

				System.out.println("Code=" + itembean.getCode());
				System.out.println("Name=" + itembean.getName());
				System.out.println("Price=" + itembean.getPrice());
				System.out.println("Count=" + itembean.getCount());
				System.out.println("Record=" + itembean.getRecord());

				nextPage = "/edit.jsp";

			} else {
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
