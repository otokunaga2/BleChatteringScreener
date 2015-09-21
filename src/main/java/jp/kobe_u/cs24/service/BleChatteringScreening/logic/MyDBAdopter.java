package jp.kobe_u.cs24.service.BleChatteringScreening.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * mySQL接続へのシングルトン管理を行う
 *
 * @author tktk
 *
 */
public class MyDBAdopter {

	public static MyDBAdopter instance = new MyDBAdopter();

	// mySQLとの接続
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	private final String IP = "133.30.159.229";
	private final String DB_NAME = "beacon_log";
	private final String USER = "otokunaga";
	private final String PASS = "lifelog";

	// コンストラクタ
	private MyDBAdopter() {
		// MySQLのドライバを指定
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		connect();
	}

	public static MyDBAdopter getInstance() {
		return MyDBAdopter.instance;
	}

	/**
	 * mySQLに接続を行う
	 */
	public boolean connect() {
		// mySQLとの接続の確立
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + IP + "/"
					+ DB_NAME + "?user=" + USER + "&password=" + PASS
					+ "&autoReconnect=true");
			return true;
		} catch (SQLException e) {
			System.out.println("mySQLとの接続に失敗しました");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * mySQLとの接続を解除する
	 *
	 * @return
	 */
	public boolean close() {
		try {
			connection.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Statementを閉じる
	 * @return
	 */
	public boolean closeStatement(){
		try {
			statement.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * 引数に指定されたクエリ文を実行する
	 *
	 * @param query
	 * @return
	 */
	public ResultSet execute(String query) {
		resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (SQLException | NullPointerException e) {
			// 接続に失敗した場合，再接続を試みてから実行
			close();
			connect();
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
			} catch (SQLException e1) {
				// それでもダメな場合
				System.out.println("クエリ文の実行に失敗しました");
				e1.printStackTrace();
			}
		}

		return resultSet;
	}

	/**
	 * connectionの取得
	 *
	 * @return
	 */
	public Connection getConnection() {
		return connection;
	}

}
