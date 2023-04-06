package drinkMachine.add;

// Serializableをインポート
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 商品一つ分のデータを格納するItemBeanクラスの作成
// Builderでさまざまな属性を変更したオブジェクトの状態を、ファイルに復元などする
public class ItemBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// private Stringで狭い領域で定義づけ
	// 商品コード
	private String code;
	// 商品名
	private String name;
	// 金額
	private String price;
	// 数量
	private String count;
	// 更新時間
	private String record;
	// おすすめ商品
	private String isPR;
	// 商品画像
	private String image;

	// 出力したい項目に空の値を代入する
	public ItemBean() {
		code = "";
		name = "";
		price = "";
		count = "";
		record = "";
		isPR = "";
		image = "";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getIsPR() {
		return isPR;
	}

	public void setIsPR(String isPR) {
		this.isPR = isPR;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.count = image;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
