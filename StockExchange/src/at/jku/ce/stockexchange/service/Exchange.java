
package at.jku.ce.stockexchange.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for exchange complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exchange">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="exchangeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="execution" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="order" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sale" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="stock" type="{http://service.stockexchange.ce.jku.at/}stock" minOccurs="0"/>
 *         &lt;element name="stockExchange" type="{http://service.stockexchange.ce.jku.at/}stockExchange" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exchange", propOrder = {
    "exchangeDate",
    "execution",
    "order",
    "sale",
    "stock",
    "stockExchange"
})
public class Exchange {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar exchangeDate;
    protected int execution;
    protected int order;
    protected boolean sale;
    protected Stock stock;
    protected StockExchange stockExchange;

    /**
     * Gets the value of the exchangeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExchangeDate() {
        return exchangeDate;
    }

    /**
     * Sets the value of the exchangeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExchangeDate(XMLGregorianCalendar value) {
        this.exchangeDate = value;
    }

    /**
     * Gets the value of the execution property.
     * 
     */
    public int getExecution() {
        return execution;
    }

    /**
     * Sets the value of the execution property.
     * 
     */
    public void setExecution(int value) {
        this.execution = value;
    }

    /**
     * Gets the value of the order property.
     * 
     */
    public int getOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.
     * 
     */
    public void setOrder(int value) {
        this.order = value;
    }

    /**
     * Gets the value of the sale property.
     * 
     */
    public boolean isSale() {
        return sale;
    }

    /**
     * Sets the value of the sale property.
     * 
     */
    public void setSale(boolean value) {
        this.sale = value;
    }

    /**
     * Gets the value of the stock property.
     * 
     * @return
     *     possible object is
     *     {@link Stock }
     *     
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * Sets the value of the stock property.
     * 
     * @param value
     *     allowed object is
     *     {@link Stock }
     *     
     */
    public void setStock(Stock value) {
        this.stock = value;
    }

    /**
     * Gets the value of the stockExchange property.
     * 
     * @return
     *     possible object is
     *     {@link StockExchange }
     *     
     */
    public StockExchange getStockExchange() {
        return stockExchange;
    }

    /**
     * Sets the value of the stockExchange property.
     * 
     * @param value
     *     allowed object is
     *     {@link StockExchange }
     *     
     */
    public void setStockExchange(StockExchange value) {
        this.stockExchange = value;
    }

}
