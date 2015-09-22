package jp.kobe_u.cs24.service.BleChatteringScreening;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jp.kobe_u.cs24.service.BleChatteringScreening.logic.ChatteringScreeningLogic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class BleChatteringScreeningService {
	private String tempValue="";
	private ChatteringScreeningLogic mainLogic;
	public BleChatteringScreeningService(){
		mainLogic = new ChatteringScreeningLogic();
		
	}
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	@Path("/init")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
	@Path("/set")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String setIt(@QueryParam("userName") String userName) {
		tempValue = userName;
		if("".equals(tempValue)){
			return "";
		}else{
			return tempValue;
		}
        
    }
	@Path("/read")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String readIt() {
		Timer timer1 = new Timer();
		timer1.schedule(new ChatteringScreeningLogic(),0,1000);
//		int test = mainLogic.getNumberOfData();
		return "ok";
//		Document doc = null;
//		try {
//			doc = (Document) Jsoup.connect("http://192.168.100.115:8080/LOCS4Beacon/api/whenwhere?userid=tokunaga").get();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
////		System.out.println(doc.html());
//		System.out.println(doc.getElementsByAttributeStarting("lastupdate"));
//		Elements elements = doc.select("lastupdate");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			Date formatDate = sdf.parse(elements.text());
//			System.out.println(formatDate);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return doc.toString();
        
    }
	
}
