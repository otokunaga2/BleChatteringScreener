/**
 * 
 */
package jp.kobe_u.cs24.service.BleChatteringScreening.logic;

import jp.kobe_u.cs24.service.BleChatteringScreening.model.WhenWhere;

/**
 * @author tokunaga
 *
 */
public class StaticWhenWhereQueue {
	private WhenWhere prevWhenWhere = null;

	private WhenWhere nextWhenWhere = null;
	private static StaticWhenWhereQueue self = null;
	public static StaticWhenWhereQueue getInstance(){
		if(self == null){
			self = new StaticWhenWhereQueue();
		}
		return self;
	}
	
	public void setPrevWhenWhere(WhenWhere whenWherearg){
		prevWhenWhere = whenWherearg;
	}
	public void setNextWhenwhenre(WhenWhere whenWherearg){
		nextWhenWhere = whenWherearg;
	}
	public WhenWhere getGetPrevWhenWhere() {
		return prevWhenWhere;
	}
	public WhenWhere getNextWhenWhere() {
		return nextWhenWhere;
	}
}
