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
	
	//TODO: transaction id
	private static double transactionID = 1.03;
	
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
		return DriverManager.getConnection("jdbc:mysql://140.78.73.67:3306/stockexchangeDB","ceue","cestock2013");
	}
	
	public static void addExchange(Exchange exchange){
		Connection cn = null;
		try {
			cn = getConnection();
			PreparedStatement pstmt = cn.prepareStatement("INSERT INTO Exchange VALUES(?,?,?,?,?,?,?,?,?,?)");
			
			//TODO: right value for transaction_ID
			transactionID++;
			pstmt.setDouble(1, transactionID);
			pstmt.setString(2, exchange.getStockExchange().getMic());
			pstmt.setString(3, exchange.getStockExchange().getName());
			pstmt.setString(4, exchange.getStock().getIsin());
			pstmt.setString(5, exchange.getStock().getName());
			pstmt.setString(6, exchange.getStock().getCurrency());
			pstmt.setDouble(7, exchange.getStock().getPrice());
			if(exchange.isSale()){
				pstmt.setInt(8, -exchange.getOrder());
				pstmt.setInt(9, -exchange.getExecution());
			}else{
				pstmt.setInt(8, exchange.getOrder());
				pstmt.setInt(9, exchange.getExecution());
			}
			
			//get datetime from XMLGregorianCalendar
			Timestamp timestamp = new Timestamp(exchange.getExchangeDate().toGregorianCalendar().getTimeInMillis());
			pstmt.setTimestamp(10, timestamp);
			
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
			PreparedStatement pstmt = cn.prepareStatement("SELECT * FROM Exchange WHERE mic=?");
			pstmt.setString(1,stockExchange.getMic());
			
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){		 	
			 	Stock newStock = new Stock();
			 	newStock.setIsin(rs.getString("isin"));
			 	newStock.setName(rs.getString("stock_name"));
			 	newStock.setPrice(rs.getDouble("stock_price"));
			 	newStock.setCurrency(rs.getString("stock_currency"));
			 	
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
