package jp.kobe_u.cs24.service.BleChatteringScreening.model;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * "いつ"と"どこ"を返すクラス
 *
 * @author tktk
 *
 */
@XmlRootElement(name = "whenwhere")
public class WhenWhere {

	private String userid;
	private String stationid;
	private String locationName;
	private boolean existance;
	private Timestamp lastUpdate;
	private final int THREASHOLD = 120000; 
	
	
	// デフォルトコンストラクタ
	public WhenWhere() {
		this("", "", "", new Timestamp(0));
	}

	// コンストラクタ
	public WhenWhere(String userid, String stationid, String locationName,
			Timestamp lastUpdate) {
		this.userid = userid;
		this.stationid = stationid;
		this.locationName = locationName;
		this.lastUpdate = lastUpdate;

		// 現在時刻をTimestampの形で取得
		Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());

		// last_updateが1分(60000ms)以内なら今そこにいる
		if (Math.abs(lastUpdate.getTime() - now.getTime()) < THREASHOLD) {
			existance = true;
		} else
			existance = false;
	}
	
	
	@XmlElement(name = "userid")
	public String getUserid() {
		return userid;
	}

	@XmlElement(name = "stationid")
	public String getStationid() {
		return stationid;
	}

	@XmlElement(name = "locationName")
	public String getLocationName() {
		return locationName;
	}

	@XmlElement(name = "existance")
	public boolean isExistance() {
		return existance;
	}

	@XmlElement(name = "lastUpdate")
	public String getLastUpdatetoString() {
		return lastUpdate.toString();
	}

	public Timestamp getLastUpdateTimestamp() {
		return lastUpdate;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setExistance(boolean existance) {
		this.existance = existance;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
