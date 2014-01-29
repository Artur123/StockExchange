package at.jku.ce.brokerplatform;
import java.sql.*;
import at.jku.ce.stockexchange.service.Exchange;

public class DBConnect {
	
	private static Connection getConnection() throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://140.78.73.67:3306/stockexchangeDB","uece","cestock2013");
	}
	
	public static void addExchange(Exchange exchange){
		Connection cn = null;
		try {
			cn = getConnection();
			PreparedStatement pstmt = cn.prepareStatement("INSERT INTO exchange(mic,stockexchange_name,isin,stock_name,stock_currency,stock_price,order,execution,exchange_date) VALUES(?,?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1, exchange.getStockExchange().getMic());
			pstmt.setString(2, exchange.getStockExchange().getName());
			pstmt.setString(3, exchange.getStock().getIsin());
			pstmt.setString(4, exchange.getStock().getName());
			pstmt.setString(5, exchange.getStock().getCurrency());
			pstmt.setDouble(6, exchange.getStock().getPrice());
			pstmt.setInt(7, exchange.getOrder());
			pstmt.setInt(8, exchange.getExecution());
			pstmt.setDate(9, java.sql.Date.valueOf(exchange.getExchangeDate().toGregorianCalendar().toString()));
			pstmt.executeUpdate();
			pstmt.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(cn != null){
				try {
					cn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
