package jp.kobe_u.cs24.service.BleChatteringScreening;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.kobe_u.cs24.service.BleChatteringScreening.logic.ChatteringScreeningLogic;
import jp.kobe_u.cs24.service.BleChatteringScreening.model.Route;
import jp.kobe_u.cs24.service.BleChatteringScreening.model.WhenWhere;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class BleChatteringScreenigWebAPI {
	private String tempValue="";
	private ChatteringScreeningLogic mainLogic;
	private ChatteringScreeningLogic watchTimer;
	public BleChatteringScreenigWebAPI(){
		mainLogic = new ChatteringScreeningLogic();
		mainLogic.startTimerTask();
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
	
	@Path("/read")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response readIt() {
		
		WhenWhere currentWhenWhere= mainLogic.getCurrentWhenWhere();
		/*ScheduledExecutorService scheduledThread = Executors.newSingleThreadScheduledExecutor();
		scheduledThread.schedule(new ChatteringScreeningLogic(), 1000, TimeUnit.MILLISECONDS);*/
		return Response.ok().entity(currentWhenWhere).build();
		
    }
	
}
