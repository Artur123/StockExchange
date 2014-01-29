
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package at.jku.ce.stockexchange.service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import at.jku.ce.stockexchange.database.DBConnect;

/**
 * This class was generated by Apache CXF 2.3.7
 * 2013-12-08T16:00:29.751+01:00
 * Generated source version: 2.3.7
 * 
 */

@javax.jws.WebService(
                      serviceName = "ExchangeServiceService",
                      portName = "ExchangeServicePort",
                      targetNamespace = "http://service.stockexchange.ce.jku.at/",
                      wsdlLocation = "http://140.78.73.67:8080/CEStockExchangeWS/services/exchangeservice?wsdl",
                      endpointInterface = "at.jku.ce.stockexchange.service.ExchangeService")
                      
public class ExchangeServiceImpl implements ExchangeService {

    private static final Logger LOG = Logger.getLogger(ExchangeServiceImpl.class.getName());
    
    private List<Stock> stockList = new ArrayList<Stock>();
    
    private static final StockExchange stockExchange = new StockExchange("L3", "L3exchange");
    
    /* (non-Javadoc)
     * @see at.jku.ce.stockexchange.service.ExchangeService#buyStock(java.lang.String  arg0 ,)int  arg1 )*
     */
    public at.jku.ce.stockexchange.service.Exchange buyStock(java.lang.String arg0,int arg1) { 
        LOG.info("Executing operation buyStock");
        System.out.println(arg0);
        System.out.println(arg1);
        try {
			String isin = arg0;
			int order = arg1;
			Exchange exchange = null;
			int execution;
			
			//select stock
			Stock stock = getStock(isin);
			if(stock != null){
				//check availability and set execution
			    if(stock.getAvailability() >= arg1){
			    	execution = order;
			    }else{
			    	execution = stock.getAvailability();
			    }
			    //update amount of stocks
			    stock.setAvailability(stock.getAvailability() - execution);
			    
			    exchange = new Exchange(getCurrentDate(), execution, order, false, stock, stockExchange);
			    
			    //write to DB
			    DBConnect.addExchange(exchange);
			    
			    return exchange;
			}
			return null;
		} catch (java.lang.Exception ex) {
		    ex.printStackTrace();
		    throw new RuntimeException(ex);
		}
    }

    /* (non-Javadoc)
     * @see at.jku.ce.stockexchange.service.ExchangeService#getExchanges(*
     */
    public java.util.List<at.jku.ce.stockexchange.service.Exchange> getExchanges() { 
        LOG.info("Executing operation getExchanges");
        try {
            //connect to DB and get all exchanges
            List<Exchange> exchanges = DBConnect.getExchanges(stockExchange);
        	
            for(Exchange e : exchanges){
            	Stock s = getStock(e.getStock().getIsin());
            	//set availability for every stock
            	e.getStock().setAvailability(s.getAvailability());
            	//set publication for every stock
            	e.getStock().setPublication(s.getPublication());
            }
        	
            return exchanges;
            
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see at.jku.ce.stockexchange.service.ExchangeService#sellStock(java.lang.String  arg0 ,)int  arg1 )*
     */
    public void sellStock(java.lang.String arg0,int arg1) { 
        LOG.info("Executing operation sellStock");
        System.out.println(arg0);
        System.out.println(arg1);
        try {
        	String isin = arg0;
        	int order = arg1;
        	
        	Stock stock = getStock(isin);
        	if(stock!=null){
        		//TODO(maybe)
        		int execution=order;
        		
        		//update amount of stocks
        		stock.setAvailability(stock.getAvailability() + execution);
        		Exchange exchange = new Exchange(getCurrentDate(), execution, order, true, stock, stockExchange);
		        
		        //TODO: write to DB
        		DBConnect.addExchange(exchange);
        	}
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see at.jku.ce.stockexchange.service.ExchangeService#getStock(java.lang.String  arg0 )*
     */
    public at.jku.ce.stockexchange.service.Stock getStock(java.lang.String arg0) { 
        LOG.info("Executing operation getStock");
        System.out.println(arg0);
        try {
        	String isin = arg0;
        	
        	for(Stock s : stockList){
        		if(s.getIsin().equalsIgnoreCase(isin))
        			return s;
        	}
        	return null;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see at.jku.ce.stockexchange.service.ExchangeService#getTradedStocks(*
     */
    public java.util.List<at.jku.ce.stockexchange.service.Stock> getTradedStocks() { 
        LOG.info("Executing operation getTradedStocks");
        try {
        	return stockList;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see at.jku.ce.stockexchange.service.ExchangeService#reset(*
     */
    public void reset() { 
        LOG.info("Executing operation reset");
        try {
        	stockList.clear();
        	
        	//add some stocks
        	stockList.add(new Stock(2000, "EUR", "AT123456789", "Lehman Brothers", 0.01, getCurrentDate()));
        	stockList.add(new Stock(1000, "EUR", "AT024680246", "Hypo Alpe Adria", 50.00, getCurrentDate()));
        	
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    private XMLGregorianCalendar getCurrentDate() {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		XMLGregorianCalendar calendar = null;

		try {
			calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return calendar;
	}
}
