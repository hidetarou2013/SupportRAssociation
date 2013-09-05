/**
 *
 */
package jp.co.eit.association.r;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * RでAssociationsAnalysisするためのR Script作成ツール
 * 2013/09/04 C6_Analysis機能実装
 * 
 * @author hidetarou2013
 *
 */
public class AssociationsAnalysis {

	/**
	 * R Script作成機能動作確認
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		AssociationsAnalysis myapp = new AssociationsAnalysis();
		myapp.C6_Analysis(args);
	}

	/**
	 * R Script作成
	 * @param args
	 */
	private void C6_Analysis(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		String FN_QID_625_csv = "C:\\eclipse\\pleiades-e4.3\\workspace\\SupportRAssociation\\csv\\QID_625.csv";
		String RS_QID_625_R = "C:\\eclipse\\pleiades-e4.3\\workspace\\SupportRAssociation\\csv\\QID_625.R";
		QID_625 qid_625 = new QID_625();

        try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FN_QID_625_csv),"MS932"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RS_QID_625_R),"MS932"));
	        String rec;
	        int cnt = 0;
	        ArrayList<String[]> lines_list = new ArrayList<String[]>();
	        while((rec = br.readLine()) != null) {
	        	if(cnt == 0){
	        		// header部はそのままコピーする。
	        		//qid_625.initColumnListORG(rec);
	        		//bw.write(rec + "\r\n",0,rec.length()+1);
	        	}else{
	        		//1サンプル毎に選択された回答のみのリストを配列で返す。
	        		String[] cnv_rec = qid_625.convert(rec);
	        		lines_list.add(cnv_rec);
//		        	recCnv = model.getCnvRecord(rec, cnt);
//		        	bw.write(recCnv + "\r\n",0,recCnv.length()+1);
	        	}
	        	cnt++;
	        }
	        br.close();
	        //
	        String rListString = qid_625.getRListString(lines_list);
	        //1行で出力したものをRで実行するとなぜかマルチバイト文字エラーになるため、却下。
	        //System.out.println(rListString);
	        //
	        //R_STEP_1:arulesのロード
	        bw.write("library(arules)" + "\r\n",0,"library(arules)".length()+1);
	        //R_STEP_2:listオブジェクト生成部
	        //        :一旦サンプルごとのデータに分解（1レコードに対応）
	        String[] strArray = rListString.split("c\\(",-1);
	        for(int i = 0 ; i < strArray.length ; i++){
	        	if(i == 0){
	        		//R_STEP_2:listオブジェクト生成部:左辺の式
	        		System.out.println(strArray[i]);
	        		bw.write(strArray[i] + "\r\n",0,strArray[i].length()+1);
	        	}else{
	        		//R_STEP_2:listオブジェクト生成部:1レコード単位
	        		String tmp = "c(" + strArray[i];
	        		System.out.println("c(" + strArray[i]);
	        		bw.write(tmp + "\r\n",0,tmp.length()+1);
	        	}
	        }
	        //R_STEP_3:head関数でlistオブジェクトの中身を確認
	        String tmp2 = "head(" + QID_625.r_listname + ")";
	        bw.write(tmp2 + "\r\n",0,tmp2.length()+1);
	        //R_STEP_4:transaction形式変換
	        String tmp3 = QID_625.r_listname + ".tran<-as(" + QID_625.r_listname + ",\"transactions\")";
	        bw.write(tmp3 + "\r\n",0,tmp3.length()+1);
	        //R_STEP_5:transaction形式変換オブジェクトの型を確認
	        String tmp4 = "class(" + QID_625.r_listname + ".tran)";
	        bw.write(tmp4 + "\r\n",0,tmp4.length()+1);
	        //R_STEP_6:itemFrequencyPlot関数で棒グラフを作成
	        String tmp5 = "itemFrequencyPlot(" + QID_625.r_listname + ".tran" + ",type = \"absolute\")";
	        bw.write(tmp5 + "\r\n",0,tmp5.length()+1);
	        bw.close();

		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}



}
