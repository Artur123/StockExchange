
package at.jku.ce.stockexchange.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stockExchange complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="stockExchange">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stockExchange", propOrder = {
    "mic",
    "name"
})
public class StockExchange {

    protected String mic;
    protected String name;

    public StockExchange(){
    	
    }
    
    public StockExchange(String mic, String name){
    	this.mic = mic;
    	this.name = name;
    }
    
    /**
     * Gets the value of the mic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMic() {
        return mic;
    }

    /**
     * Sets the value of the mic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMic(String value) {
        this.mic = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
