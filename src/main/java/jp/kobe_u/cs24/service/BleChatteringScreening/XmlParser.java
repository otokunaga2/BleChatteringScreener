package jp.kobe_u.cs24.service.BleChatteringScreening;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class XmlParser {
	public static void main(String[] args){
		
		
		try {
			Document doc = (Document) Jsoup.connect("http://192.168.100.115:8080/LOCS4Beacon/api/whenwhere?userid=tokunaga").get();
//			System.out.println(doc.html());
			System.out.println(doc.getElementsByAttributeStarting("lastupdate"));
			Elements elements = doc.select("lastupdate");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date formatDate = sdf.parse(elements.text());
				System.out.println(formatDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(elements.text());
//			for(Element element:)
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*if(updateDate-holdDate < 60000*5){
			//do nothing
		}else{
			//httpを発行する？RuCASが判定できる．もしくは，ほりほりに向けて発行する？
		}*/
	}
}
