
package at.jku.ce.stockexchange.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.jku.ce.stockexchange.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetTradedStocks_QNAME = new QName("http://service.stockexchange.ce.jku.at/", "getTradedStocks");
    private final static QName _BuyStockResponse_QNAME = new QName("http://service.stockexchange.ce.jku.at/", "buyStockResponse");
    private final static QName _Reset_QNAME = new QName("http://service.stockexchange.ce.jku.at/", "reset");
    private final static QName _ResetResponse_QNAME = new QName("http://service.stockexchange.ce.jku.at/", "resetResponse");
    private final static QName _BuyStock_QNAME = new QName("http://service.stockexchange.ce.jku.at/", "buyStock");
    private final static QName _SellStock_QNAME = new QName("http://service.stockexchange.ce.jku.at/", "sellStock");
    private final static QName _SellStockResponse_QNAME = new QName("http://service.stockexchange.ce.jku.at/", "sellStockResponse");
    private final static QName _GetExchangesResponse_QNAME = new QName("http://service.stockexchange.ce.jku.at/", "getExchangesResponse");
    private final static QName _GetTradedStocksResponse_QNAME = new QName("http://service.stockexchange.ce.jku.at/", "getTradedStocksResponse");
    private final static QName _GetStock_QNAME = new QName("http://service.stockexchange.ce.jku.at/", "getStock");
    private final static QName _GetExchanges_QNAME = new QName("http://service.stockexchange.ce.jku.at/", "getExchanges");
    private final static QName _GetStockResponse_QNAME = new QName("http://service.stockexchange.ce.jku.at/", "getStockResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.jku.ce.stockexchange.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTradedStocksResponse }
     * 
     */
    public GetTradedStocksResponse createGetTradedStocksResponse() {
        return new GetTradedStocksResponse();
    }

    /**
     * Create an instance of {@link GetExchangesResponse }
     * 
     */
    public GetExchangesResponse createGetExchangesResponse() {
        return new GetExchangesResponse();
    }

    /**
     * Create an instance of {@link GetStock }
     * 
     */
    public GetStock createGetStock() {
        return new GetStock();
    }

    /**
     * Create an instance of {@link GetExchanges }
     * 
     */
    public GetExchanges createGetExchanges() {
        return new GetExchanges();
    }

    /**
     * Create an instance of {@link GetStockResponse }
     * 
     */
    public GetStockResponse createGetStockResponse() {
        return new GetStockResponse();
    }

    /**
     * Create an instance of {@link GetTradedStocks }
     * 
     */
    public GetTradedStocks createGetTradedStocks() {
        return new GetTradedStocks();
    }

    /**
     * Create an instance of {@link Reset }
     * 
     */
    public Reset createReset() {
        return new Reset();
    }

    /**
     * Create an instance of {@link BuyStockResponse }
     * 
     */
    public BuyStockResponse createBuyStockResponse() {
        return new BuyStockResponse();
    }

    /**
     * Create an instance of {@link ResetResponse }
     * 
     */
    public ResetResponse createResetResponse() {
        return new ResetResponse();
    }

    /**
     * Create an instance of {@link BuyStock }
     * 
     */
    public BuyStock createBuyStock() {
        return new BuyStock();
    }

    /**
     * Create an instance of {@link SellStockResponse }
     * 
     */
    public SellStockResponse createSellStockResponse() {
        return new SellStockResponse();
    }

    /**
     * Create an instance of {@link SellStock }
     * 
     */
    public SellStock createSellStock() {
        return new SellStock();
    }

    /**
     * Create an instance of {@link Stock }
     * 
     */
    public Stock createStock() {
        return new Stock();
    }

    /**
     * Create an instance of {@link StockExchange }
     * 
     */
    public StockExchange createStockExchange() {
        return new StockExchange();
    }

    /**
     * Create an instance of {@link Exchange }
     * 
     */
    public Exchange createExchange() {
        return new Exchange();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTradedStocks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.stockexchange.ce.jku.at/", name = "getTradedStocks")
    public JAXBElement<GetTradedStocks> createGetTradedStocks(GetTradedStocks value) {
        return new JAXBElement<GetTradedStocks>(_GetTradedStocks_QNAME, GetTradedStocks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyStockResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.stockexchange.ce.jku.at/", name = "buyStockResponse")
    public JAXBElement<BuyStockResponse> createBuyStockResponse(BuyStockResponse value) {
        return new JAXBElement<BuyStockResponse>(_BuyStockResponse_QNAME, BuyStockResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reset }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.stockexchange.ce.jku.at/", name = "reset")
    public JAXBElement<Reset> createReset(Reset value) {
        return new JAXBElement<Reset>(_Reset_QNAME, Reset.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.stockexchange.ce.jku.at/", name = "resetResponse")
    public JAXBElement<ResetResponse> createResetResponse(ResetResponse value) {
        return new JAXBElement<ResetResponse>(_ResetResponse_QNAME, ResetResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyStock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.stockexchange.ce.jku.at/", name = "buyStock")
    public JAXBElement<BuyStock> createBuyStock(BuyStock value) {
        return new JAXBElement<BuyStock>(_BuyStock_QNAME, BuyStock.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SellStock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.stockexchange.ce.jku.at/", name = "sellStock")
    public JAXBElement<SellStock> createSellStock(SellStock value) {
        return new JAXBElement<SellStock>(_SellStock_QNAME, SellStock.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SellStockResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.stockexchange.ce.jku.at/", name = "sellStockResponse")
    public JAXBElement<SellStockResponse> createSellStockResponse(SellStockResponse value) {
        return new JAXBElement<SellStockResponse>(_SellStockResponse_QNAME, SellStockResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetExchangesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.stockexchange.ce.jku.at/", name = "getExchangesResponse")
    public JAXBElement<GetExchangesResponse> createGetExchangesResponse(GetExchangesResponse value) {
        return new JAXBElement<GetExchangesResponse>(_GetExchangesResponse_QNAME, GetExchangesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTradedStocksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.stockexchange.ce.jku.at/", name = "getTradedStocksResponse")
    public JAXBElement<GetTradedStocksResponse> createGetTradedStocksResponse(GetTradedStocksResponse value) {
        return new JAXBElement<GetTradedStocksResponse>(_GetTradedStocksResponse_QNAME, GetTradedStocksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.stockexchange.ce.jku.at/", name = "getStock")
    public JAXBElement<GetStock> createGetStock(GetStock value) {
        return new JAXBElement<GetStock>(_GetStock_QNAME, GetStock.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetExchanges }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.stockexchange.ce.jku.at/", name = "getExchanges")
    public JAXBElement<GetExchanges> createGetExchanges(GetExchanges value) {
        return new JAXBElement<GetExchanges>(_GetExchanges_QNAME, GetExchanges.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStockResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.stockexchange.ce.jku.at/", name = "getStockResponse")
    public JAXBElement<GetStockResponse> createGetStockResponse(GetStockResponse value) {
        return new JAXBElement<GetStockResponse>(_GetStockResponse_QNAME, GetStockResponse.class, null, value);
    }

}
