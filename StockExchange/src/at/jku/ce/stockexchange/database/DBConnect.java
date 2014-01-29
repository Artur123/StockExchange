package at.jku.ce.stockexchange.database;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;

import at.jku.ce.stockexchange.service.Exchange;
import at.jku.ce.stockexchange.service.Stock;
import at.jku.ce.stockexchange.service.StockExchange;

public class DBConnect {
	
	
	private static Connection getConnection() throws SQLException, ClassNotFoundException{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public static List<Exchange> getExchanges(StockExchange stockExchange) {
		List<Exchange> exchanges = new ArrayList<Exchange>();
		Connection cn = null;
		try {
			cn = getConnection();
			PreparedStatement pstmt = cn.prepareStatement("SELECT transaction_id,mic,stockexchange_name,isin,stock_name,stock_currency,stock_price,order,execution,exchange_date FROM exchange WHERE mic=?");
			pstmt.setString(1,stockExchange.getMic());
			
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){		 	
			 	Stock newStock = new Stock();
			 	newStock.setIsin(rs.getString("isin"));
			 	newStock.setName(rs.getString("stock_name"));
			 	newStock.setPrice(rs.getDouble("stock_price"));
			 	newStock.setCurrency(rs.getString("stock_currency"));
			 	//no availability or publication
			 	
			 	Exchange newExchange = new Exchange();
			 	newExchange.setStock(newStock);
			 	newExchange.setOrder(rs.getInt("order"));
			 	newExchange.setExecution(rs.getInt("execution"));
			 	newExchange.setStockExchange(stockExchange);
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(rs.getDate("exchange_date"));
				newExchange.setExchangeDate(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			 	
			 	exchanges.add(newExchange);

			}
			rs.close();
			pstmt.close();
			return exchanges;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatatypeConfigurationException e) {
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
		
		return exchanges;
	}
		
}
