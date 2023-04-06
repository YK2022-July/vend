package drinkMachine.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import drinkMachine.add.ItemBean;

public class T001_ItemDao {
	private Connection conn = null;

	public T001_ItemDao() throws ClassNotFoundException, SQLException {

		// 接続方法 "jdbc:oracle:thin@ホスト名:ポート番号:SID";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// jdbc経由でデータベースに接続する
		conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.0.117:1521:" + "zeroSchool",
				"EDU_Y_KATOU", "ykatou");
	}

	// プリペアードステートメント宣言
	java.sql.PreparedStatement pstmt = null;
	// java.sql.PreparedStatement statement = null;

	String kensaku = null;

	// 商品登録メソッド
	public int addItem(String Name, String Price, String Count) {
		// 登録件数格納
		int result = 0;
		try {
			// SQL文発行
			String sql = "INSERT INTO T001_ITEM"
					+ "(ITEM_NO,ITEM_NM,UNIT_PRICE,STOCK_COUNT,RECORD_DATE)"
					+ " VALUES (TABLE_SEQ.NEXTVAL ,'" + Name + "','" + Price
					+ "','" + Count + "', sysdate)";

			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);

			// SQL文を実行 1なら登録成功、0なら失敗
			result = pstmt.executeUpdate();
			// データ作成が正常か判定
			if (result < 0)
				throw new SQLException();

			// コンソールに出力（確認用）
			System.out.println("登録成功！");

		} catch (SQLException e) {
			// コンソールに出力（確認用）
			System.out.println("登録失敗");
			e.printStackTrace();
		}
		try {
			if (conn != null)
				conn.close();
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 商品データ重複チェックメソッド
	public String checkItem(String Name) {

		// データの重複チェック（商品名をDBから検索）
		try {
			// SELECT文の作成。既に登録されている商品名と比較して取り出す。
			// 「検索」のSQL文：DB内の商品を検索するためのSQL文
			String sql = "SELECT count(*) as checkItem" + " FROM T001_ITEM"
					+ " WHERE ITEM_NM ='" + Name + "'";

			System.out.println(sql);
			// SQL文をDBと接続して使える形に変換する
			pstmt = conn.prepareStatement(sql);

			// SELECT文の実行（問い合わせ文の送信：ResultSetに実行結果を格納して返す）
			// ResultSet型の変数resultSet
			ResultSet resultSet = pstmt.executeQuery(sql);

			// 結果取り出し
			// 検索結果（データの件数）はcheckItemに代入
			resultSet.next();
			kensaku += resultSet.getString("checkItem");
			System.out.println(kensaku);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (conn != null)
				conn.close();
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kensaku;
	}

	// データベース内から検索するメソッド
	public List<ItemBean> searchItem(String Code, String Name) {

		// 検索結果を格納するListを宣言
		List<ItemBean> list = new ArrayList();

		// 検索したデータがあるかどうか調べる
		try {
			// データベース内から検索するSQL文
			// 商品コード検索（前方一致検索）、商品名検索（あいまい検索）
			// SELECT以下で出力したいデータを記述
			// 今回の場合は４項目（コード、名前、金額、数量）
			String sql = "SELECT ITEM_NO,ITEM_NM,UNIT_PRICE,STOCK_COUNT"
					+ " FROM T001_ITEM " + "WHERE ITEM_NO Like '" + Code + "%'"
					+ "AND ITEM_NM Like '%" + Name + "%'";

			System.out.println(sql);
			// SQL文をDBと接続して使える形に変換する
			pstmt = conn.prepareStatement(sql);

			// 検索のSELECT文の実行（resultSetに実行結果を格納して返す）
			ResultSet resultSet = pstmt.executeQuery();

			// resultSetで返された結果をセットし、
			// ItemBeanのsetメソッドを使用して各値をセットする
			// .nextは該当データがなくなるまで１件ずつ繰り返し処理を行う記述
			while (resultSet.next()) {

				// １件分の商品データを格納するItemBeanを宣言する
				ItemBean selectedItm = new ItemBean();

				// 検索するデータをセットする
				selectedItm.setCode(resultSet.getString("ITEM_NO"));
				selectedItm.setName(resultSet.getString("ITEM_NM"));
				selectedItm.setPrice(resultSet.getString("UNIT_PRICE"));
				selectedItm.setCount(resultSet.getString("STOCK_COUNT"));
				// 商品データをリストに追加
				list.add(selectedItm);
			}
			System.out.println("商品検索結果：" + list.size() + "件ヒット");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (conn != null)
				conn.close();
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 商品情報の取得メソッド
	public ItemBean editItm(String Code) {

		// １件分の商品データを格納するitembeanを宣言
		ItemBean itembean = new ItemBean();

		try {
			// 商品コードを使ってデータベースから更新情報を検索するSQL文
			String sql = "SELECT ITEM_NO,ITEM_NM,UNIT_PRICE,STOCK_COUNT,RECORD_DATE"
					+ " FROM T001_ITEM WHERE ITEM_NO ='" + Code + "'";

			System.out.println(sql);
			// SQL文をDBと接続して使える形に変換する
			pstmt = conn.prepareStatement(sql);

			// SELECT文の実行（resultSetに実行結果を格納して返す）
			ResultSet resultSet = pstmt.executeQuery(sql);

			// 結果の取り出し
			// 該当データがなくなるまで１件ずつ繰り返し処理を行う記述
			resultSet.next();

			itembean.setCode(resultSet.getString("ITEM_NO"));
			itembean.setName(resultSet.getString("ITEM_NM"));
			itembean.setPrice(resultSet.getString("UNIT_PRICE"));
			itembean.setCount(resultSet.getString("STOCK_COUNT"));
			itembean.setRecord(resultSet.getString("RECORD_DATE"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (conn != null)
				conn.close();
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itembean;
	}

	// アップデートメソッド
	public int upItm(ItemBean itembean) {
		//更新件数を格納
		int result = 0;
		try {

			System.out.println("Code=" + itembean.getCode());
			System.out.println("Name=" + itembean.getName());
			System.out.println("Price=" + itembean.getPrice());
			System.out.println("Count=" + itembean.getCount());

			// UPDATE文をString型に代入
			String sql =
					"UPDATE EDU_Y_KATOU.T001_ITEM SET" +
					" ITEM_NM = '"+ itembean.getName() + "'," +
					" UNIT_PRICE = " + itembean.getPrice() + " , " +
					" STOCK_COUNT = " + itembean.getCount() + " , " +
					/*" IS_PR= " + itembean.getIsPR() + " , " +
					" ITEM_IMAGE_FILE_PATH = ' " + itembean.getImage() + " ', " +*/
					" RECORD_DATE = CURRENT_TIMESTAMP " +
					" WHERE ITEM_NO = " + itembean.getCode();

			System.out.println(sql);
			// SQL文をDBと接続して使える形に変換する
			pstmt = conn.prepareStatement(sql);

			// SELECT文の実行
			result = pstmt.executeUpdate(sql);

			System.out.println("Code=" + itembean.getCode());
			System.out.println("Name=" + itembean.getName());
			System.out.println("Price=" + itembean.getPrice());
			System.out.println("Count=" + itembean.getCount());


			// データ更新が正常か判定(1なら更新成功、0なら失敗)
			if (result < 0)
				throw new SQLException();

			// コンソールに出力（確認用）
			System.out.println("更新成功！");

		} catch (SQLException e) {
			e.printStackTrace();
			// コンソールに出力（確認用）
			System.out.println("更新失敗");
			e.printStackTrace();
		}
		try {
			if (conn != null)
				conn.close();
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//更新日時の検索メソッド
	public String getRecordDate(String Code){

		String date = null;

		try{

			String sql =
					"SELECT * FROM T001_ITEM WHERE ITEM_NO ='" + Code + "'";

			System.out.println(sql);
			// SQL文をDBと接続して使える形に変換する
			pstmt = conn.prepareStatement(sql);

			// SELECT文の実行
			ResultSet resultSet = pstmt.executeQuery(sql);

			// 結果の取り出し
			resultSet.next();
			date = resultSet.getString("RECORD_DATE");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (conn != null)
				conn.close();
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}




}
