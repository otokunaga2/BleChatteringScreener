package jp.kobe_u.cs24.service.BleChatteringScreening;

import java.util.concurrent.ArrayBlockingQueue;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.kobe_u.cs24.service.BleChatteringScreening.logic.ChatteringScreeningLogic;
import jp.kobe_u.cs24.service.BleChatteringScreening.logic.StaticWhenWhereQueue;
import jp.kobe_u.cs24.service.BleChatteringScreening.model.Route;
import jp.kobe_u.cs24.service.BleChatteringScreening.model.WhenWhere;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class BleChatteringController {
	private String tempValue="";
	 
	private StaticWhenWhereQueue whenWhereQueue;
	private ArrayBlockingQueue<WhenWhere> blockingQueue = new ArrayBlockingQueue<>(10);
	private static WhenWhere prev = new WhenWhere();
	private static ChatteringScreeningLogic mainLogic = new ChatteringScreeningLogic();
	
	public BleChatteringController(){
		String uri = "http://192.168.100.115:8080/LOCS4Beacon/api/whenwhere?userid=tokunaga";
		prev =XmlParser.obtainCurrentDataFromWebAPI(uri);
		mainLogic.setStaticPrevData(prev);
		
		/*staticにデータ保存のququeを作る必要あり？*/
		whenWhereQueue = StaticWhenWhereQueue.getInstance();
		
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
	
	@Path("/route")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response readPath(){
		
		Route test = new Route();
		return Response.ok().entity(test).build();
	}
	
	@Path("/prevdata")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response readIt(@QueryParam(value = "user") String userName) {
		
		WhenWhere previousLocation= mainLogic.getPreviousLocation(userName);
		/*ScheduledExecutorService scheduledThread = Executors.newSingleThreadScheduledExecutor();
		scheduledThread.schedule(new ChatteringScreeningLogic(), 1000, TimeUnit.MILLISECONDS);*/
		return Response.ok().entity(previousLocation).build();
		
    }
	
}
