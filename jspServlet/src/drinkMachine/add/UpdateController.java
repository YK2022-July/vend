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
 * Servlet implementation class UpdateController
 */
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateController() {
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

		// パラメーター" name "の値を取り出し、変数Nameに代入（商品名の入力）
		String Name = request.getParameter("name");

		// コンソールに表示する
		System.out.println("商品名：" + Name);

		// パラメーター" unitPrice "の値を取り出し、変数Priceに代入（金額の入力）
		String Price = request.getParameter("unitPrice");

		// コンソールに表示する
		System.out.println("金額：" + Price);

		// パラメーター" count "の値を取り出し、変数Countに代入（数量の入力）
		String Count = request.getParameter("count");

		// コンソールに表示する
		System.out.println("数量：" + Count);



		T001_ItemDao itemDao;

		try {

			String nextPage = null;

			//１件分の商品データを格納するitembeanを宣言
			ItemBean itembean = new ItemBean();

			//ItemBeanクラスにセット

			itembean.setCode(Code);
			itembean.setName(Name);
			itembean.setPrice(Price);
			itembean.setCount(Count);

			// Beanに商品データを送る
			request.setAttribute("itembean", itembean);


			//Sessionに格納されている更新日時の取得
			String beforeDate =
					(String) request.getSession().getAttribute("RECORD_DATE");



			//DB更新日時の検索メソッドの呼び出し
			// Daoのコントラクタの呼び出し
			itemDao = new T001_ItemDao();
			String afterDate =itemDao.getRecordDate(Code);

			int result = 0;

			//afterDateの値が空文字またはnullの場合に削除済みエラー
			if(afterDate == "" || (afterDate == null)){

				String error1 = "削除済みエラー";
				request.setAttribute("errormessage1", error1);
				nextPage = "/edit.jsp";
			}else{
				//更新日時の確認
				//beforeDateとafterDateが同じ値の場合
				if(beforeDate.equals(afterDate)){

					//商品更新
					result = new T001_ItemDao().upItm(itembean);
					//executeUpdateで帰ってきた値が0の時は更新失敗エラー
					if(result == 0){
						//更新失敗
						String error2 = "更新失敗エラー";
						request.setAttribute("errormessage2",error2);
						nextPage = "/edit.jsp";
					}else{
						//更新成功
						nextPage = "/list.jsp";
					}
				}else{
					//beforeDateとafterDateの値が違う場合は更新済みエラー
					String error3 = "更新済みエラー";
					request.setAttribute("errormessage3",error3);
					nextPage = "/edit.jsp";
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
