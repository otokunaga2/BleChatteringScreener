/**
 * 
 */
package jp.kobe_u.cs24.service.BleChatteringScreening;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @author tokunaga
 *
 */
public class Utility {
	public static Timestamp getCurrentTime(){
		Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
		return now;
	}
	
}
