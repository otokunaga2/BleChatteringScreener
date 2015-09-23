package jp.kobe_u.cs24.service.BleChatteringScreening.model;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "route")
public class Route {
	private String userid;
	
	private String routeName;
	private Timestamp time; 
	
	public Route(){
		this("test",-1,"None",new Timestamp(0));
	}
	public Route(final String userarg, final int stationarg, 
				final String patharg, final Timestamp timestamparg){
		this.userid = userarg;
		
		this.routeName = patharg;
		this.time = timestamparg;
		
	}
	
	@XmlElement(name = "userid")
	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	@XmlElement(name = "routename")
	public String getPathName() {
		return routeName;
	}
	public void setPathName(String pathName) {
		this.routeName = pathName;
	}
	@XmlElement(name = "time")
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}

}
