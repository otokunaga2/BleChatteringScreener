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
	static final String querySelector = "existance, lastupdate, locationname, stationid, userid";

	// 右のurlを前提に作っている。http://192.168.100.115:8080/LOCS4Beacon/api/whenwhere?userid=";

	public static WhenWhere obtainCurrentDataFromWebAPI(final String uri) {
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
					whenWhereElem.setLastUpdate(parseDate(element.text()
							.toString()));
				case "locationname":
					whenWhereElem.setLocationName(element.text().toString());
					break;
				case "stationid":
					if ("".equals(element.text())) {

					} else {
						whenWhereElem.setStationid(Integer.valueOf(element
								.text()));
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

	private static boolean filterWithValue(Elements elms) {
		for (Element element : elms) {
			switch (element.tagName()) {
			case "existance":
				return true;
				
			default:
				break;
			}

		}
		return false;
	}

	public static Timestamp parseDate(final String dateData) {
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
}
