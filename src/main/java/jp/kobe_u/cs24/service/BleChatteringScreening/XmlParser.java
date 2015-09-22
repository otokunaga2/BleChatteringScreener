package jp.kobe_u.cs24.service.BleChatteringScreening;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.kobe_u.cs24.service.BleChatteringScreening.model.WhenWhere;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class XmlParser {
	private String[] queryList = { "existance", "lastupdate", "locationName",
			"stationid" };
	static final String querySelector = "existance, lastupdate, locationname, stationid, userid";

	public static WhenWhere parseAPI(final String uri) {
		WhenWhere whenWhereElem = new WhenWhere();
		try {
			Document xmlDocument = (Document) Jsoup.connect(uri).get();
			Elements elems = xmlDocument.select(querySelector);
			for (Element element : elems) {
				System.out.println(element.data());
				switch (element.tagName()) {
				case "existance":
					whenWhereElem.setExistance(Boolean.parseBoolean(element
							.text()));
					break;
				case "lastupdate":
					whenWhereElem.setLastUpdate(parseDate(element.text().toString()));
				case "locationname" :
					whenWhereElem.setLocationName(element.text().toString());
					break;
				case "stationid":
					if("".equals(element.text())){
						
					}else{
						whenWhereElem.setStationid(Integer.valueOf(element.text()));
					}
					break;
				case "userid":
					whenWhereElem.setUserid(element.text());
					break;
				default:
					break;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return whenWhereElem;
	}
	
	public static Timestamp parseDate(final String dateData){
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
		Date formatToDate = null;
		Timestamp formatDate = null;
		try {
			formatToDate = sdf.parse(dateData);
			formatDate = new Timestamp(formatToDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatDate;
	}
	
	public static void main(String[] args) {
		parseAPI("http://192.168.100.115:8080/LOCS4Beacon/api/whenwhere?userid=tokunaga");

		// /*try {
		// Document doc = (Document)
		// Jsoup.connect("http://192.168.100.115:8080/LOCS4Beacon/api/whenwhere?userid=tokunaga").get();
		// // System.out.println(doc.html());
		// System.out.println(doc.getElementsByAttributeStarting("lastupdate"));
		// Elements elements = doc.select("lastupdate");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// try {
		// Date formatDate = sdf.parse(elements.text());
		// System.out.println(formatDate);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// System.out.println(elements.text());
		// // for(Element element:)
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		//
		// /*if(updateDate-holdDate < 60000*5){
		// //do nothing
		// }else{
		// //httpを発行する？RuCASが判定できる．もしくは，ほりほりに向けて発行する？
		// }*/

	}
}
