/**
 * 
 */
package jp.co.eit.association.r;

import java.util.ArrayList;

/**
 * QID_625 の設問のためのクラス
 * 2013/09/04 convert機能実装
 * 
 * @author hidetarou2013
 *
 */
public class QID_625 {

	
	public static final String fieldnames = "[HORIZONTAL_SEQ_ID],[サンプルID],[調査年月日],[Q_00001_Q1_年齢],[QID_626],[QID_627],[QID_628],[QID_629],[QID_630],[QID_631],[QID_632],[QID_633],[QID_634]";

	public static String[] fieldname = fieldnames.split(",",-1);

	private String keyword = "000_未選択";

	private static int[] col_num = {0,1,2,3,4,5,6,7,8,9,10,11,12};

	/**
	 * R_list_object変数名
	 */
	public static final String r_listname = "QID_625_data1";
	public static final String csv_org_filename = ".csv";
	public static final String r_script_filename = ".R";

	/**
	 * メソッドconvertの動作確認
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		QID_625 myapp = new QID_625();
		String[] ret_one_rec = myapp.convert("128,10128,2013-04-18,57,001_吸引力,003_運転音,004_ノズルの種類数,005_本体重量,000_未選択,000_未選択,000_未選択,000_未選択,000_未選択");
		for(int i = 0 ; i < ret_one_rec.length;i++){
			System.out.println(ret_one_rec[i]);
		}

	}

	/**
	 * R言語のlist形式オブジェクト用に、「000_未選択」以外の回答（※つまり、チェックボックスONのみ）のみ抽出して配列にして返す
	 * @param rec 1レコード分のデータ：1サンプルの回答データ
	 * 捜査対象は1レコードのうちQID_625の子設問（QID_626~QID_634）の列のみ
	 * @return
	 */
	public String[] convert(String rec) {
		// TODO 自動生成されたメソッド・スタブ
		String[] original = rec.split(",",-1);
		String[] retStr = {""};
		ArrayList<String> one_rec_list = new ArrayList<String>();
		for(int i = col_num[4] ; i < original.length;i++){
			if(keyword.equals(original[i])){

			}else{
				one_rec_list.add(original[i]);
			}
		}
		return one_rec_list.toArray(retStr);
	}

	/**
	 * R言語のlist形式オブジェクトへの代入コード生成
	 * 1サンプルで選択された回答をc()関数でまとめる。
	 * @param lines_list
	 * @return
	 */
	public String getRListString(ArrayList<String[]> lines_list) {
		// TODO 自動生成されたメソッド・スタブ
		String strRet = r_listname + " <- list(";
		String strEnd = ")";
		ArrayList<String> rec_list = new ArrayList<String>();

		for(int i = 0 ; i < lines_list.size() ; i++){
			StringBuffer buf = new StringBuffer();
			String[] one_rec = lines_list.get(i);
			buf.append("c(");
			for(int j = 0 ; j < one_rec.length ; j++){
				buf.append("\"");
				buf.append(one_rec[j]);
				buf.append("\",");
			}
			//最後の1文字修正
			String tmp = buf.toString();
			tmp = tmp.substring(0,tmp.length()-1);
			tmp = tmp + "),";
			rec_list.add(tmp);
		}
		//
		for(int i = 0 ; i < rec_list.size() ; i++){
			strRet = strRet + rec_list.get(i);
		}
		strRet = strRet.substring(0,strRet.length()-1);
		strRet = strRet + strEnd;
		return strRet;
	}

}
