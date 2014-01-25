package at.jku.ce.juddi;

import java.io.File;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.juddi.v3.client.ClassUtil;
import org.apache.juddi.v3.client.config.UDDIClerkManager;
import org.apache.juddi.v3.client.config.UDDIClientContainer;
import org.apache.juddi.v3.client.transport.Transport;
import org.apache.juddi.v3_service.JUDDIApiPortType;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BindingTemplates;
import org.uddi.api_v3.BusinessDetail;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessInfo;
import org.uddi.api_v3.BusinessList;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.DeleteBusiness;
import org.uddi.api_v3.DeleteService;
import org.uddi.api_v3.FindBusiness;
import org.uddi.api_v3.FindQualifiers;
import org.uddi.api_v3.FindService;
import org.uddi.api_v3.GetAuthToken;
import org.uddi.api_v3.GetServiceDetail;
import org.uddi.api_v3.Name;
import org.uddi.api_v3.SaveBusiness;
import org.uddi.api_v3.SaveService;
import org.uddi.api_v3.ServiceDetail;
import org.uddi.api_v3.ServiceInfo;
import org.uddi.api_v3.ServiceInfos;
import org.uddi.api_v3.ServiceList;
import org.uddi.v3_service.DispositionReportFaultMessage;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;


/**
 * Simple UDDI Manager class that provides required methods to publish 
 * Webservices to the a UDDI Server and retrieve Businesses and Services from UDDI Service 
 * 
 * 
 */
public class UddiManager {
	protected JUDDIApiPortType _juddiApi = null;

	protected String userIdPublish = "se-admin"; //publisher configured @JUDDI, do not change
	protected String credentialsPublish = "";
	protected AuthToken tokenPublish = null;

	protected UDDISecurityPortType security = null;
	protected UDDIPublicationPortType publish = null;
	protected UDDIInquiryPortType inquiry = null;

	protected static UddiManager instance = null;

	private static final Logger log = Logger.getLogger(UddiManager.class.getName());

	/**
	 * hidden constructor for singelton pattern
	 */
	private UddiManager() {
	}

	/**
	 * 
	 * @return instance of UddiManager
	 */
	public static UddiManager getInstance() {
		if (instance == null) {
			instance = new UddiManager();
			instance.init();
		}
		return instance;
	}
	
	
	/**
	 * publish business and service at UDDI
	 * 
	 * @param businessName name of business, e.g. CE, MN1, LH1,... 
	 * @param servicename name of service, e.g. Stock Exchange Service
	 * @param accesspointURL URL of wsdl, e.g. http://140.78.73.67:8080/CEStockExchangeWS/services/exchangeservice?wsdl
	 * @return serviceKey identifier for published service
	 * @throws RemoteException
	 * @throws ConnectException
	 */
	public String publish(String businessName, String servicename,
			String accesspointURL) throws RemoteException, ConnectException {
		log.entering("UDDI Manager", "publishForThisBusiness>");
		
		String businessKey = registerBusiness(businessName);
		String serviceKey = registerService(businessKey, servicename,
				accesspointURL);
		
		log.exiting("UDDI Manager", "publishForThisBusiness<");
		
		return serviceKey;
	}
	
	
	/**
	 * delete a business and its services form UDDI by name
	 * 
	 * @param businessName name of business (CE, MN1,..)
	 * @return String containing a report of the deletion 
	 */
	public String deletePublishedBusinessFromUDDI(String businessName) {
		log.entering("UDDI Manager", "deletePublishedBusinessFromUDDIBy>");
		StringBuilder result = new StringBuilder();
		
		try {
			authenticate();

			FindBusiness fb = new FindBusiness();
			Name anyBusiness = new Name();
			anyBusiness.setValue(businessName);
			fb.getName().add(anyBusiness);

			FindQualifiers fq = new FindQualifiers();
			fq.getFindQualifier().add(org.apache.juddi.query.util.FindQualifiers.APPROXIMATE_MATCH);
			fb.setFindQualifiers(fq);

			fb.setAuthInfo(tokenPublish.getAuthInfo());
			
			BusinessList bilist = inquiry.findBusiness(fb);

			if (bilist != null
					&& bilist.getListDescription().getActualCount() > 0) {
				for (BusinessInfo bi : bilist.getBusinessInfos()
						.getBusinessInfo()) {
					if (bi != null
							&& bi.getName().get(0).getValue().indexOf("UDDI") == -1) {
						result.append("Delete BUSINESS:  ");
						log.info("Delete BUSINESS:");
						for (Name name : bi.getName()) {
							result.append("Name: " + name.getValue()
									+ "<br />\r\n");
							log.info("Name: " + name.getValue());
						}

						ServiceInfos sinfos = bi.getServiceInfos();
						if (sinfos != null) {
							for (ServiceInfo si : sinfos.getServiceInfo()) {
								if (si != null
										&& si.getName().get(0).getValue()
												.indexOf("UDDI") == -1) {//delete all except UDDI Node
									result.append("&nbsp; &nbsp;Delete Service: ");
									log.info("&nbsp; &nbsp;Delete Service:");
									for (Name name : si.getName()) {
										result.append("Name: "
												+ name.getValue()
												+ "<br />\r\n");
										log.info("Name: " + name.getValue());
									}
									DeleteService ds = new DeleteService();
									ds.setAuthInfo(tokenPublish.getAuthInfo());
									ds.getServiceKey().add(si.getServiceKey());
									try {
										publish.deleteService(ds);
									} catch (Exception e) {
										log.severe("NOT DELETED Service: "
												+ e.getLocalizedMessage());
										result.append(e.getLocalizedMessage()
												+ "<br />\r\n");
									}
								}
							}
						}
						
						DeleteBusiness db = new DeleteBusiness();
						db.setAuthInfo(tokenPublish.getAuthInfo());
						db.getBusinessKey().add(bi.getBusinessKey());
						try {
							
							publish.deleteBusiness(db);
						} catch (Exception e) {
							
							log.severe("NOT DELETED Business: "
									+ e.getLocalizedMessage());
							result.append(e.getLocalizedMessage()
									+ "<br />\r\n");
						}
					}
				}
			}
		} catch (Exception e) {
			result.append(e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.exiting("UDDI Manager", "deleteAllPublishedBusinessFromUDDI>");
		return result.toString();
	}
	
	/**
	 * get accesspoint of service by name of business and service name
	 * 
	 * @param businessName name of business, e.g. CE, MN1,...
	 * @param servicename name of service, e.g. Stock Exchange Service
	 * @return accesspoint, i.e. wsdl URL
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 * @throws ConnectException
	 */
	public String getServiceAccessPointBy(String businessName, String servicename)
			throws DispositionReportFaultMessage, RemoteException,
			ConnectException {
		log.entering("UDDI Manager", "getServiceAccessPointForThisBusiness>");

		String businesskey = getBusinessKey(businessName);
		if(businesskey == null) return null;
		
		String servicekey = getServiceKey(businesskey, servicename);

		String endpoint = null;
		if(servicekey != null){
			
			GetServiceDetail service = new GetServiceDetail();
			service.setAuthInfo(tokenPublish.getAuthInfo());
			service.getServiceKey().add(servicekey);
			ServiceDetail serviceInfo = inquiry.getServiceDetail(service);
			List<BusinessService> services = serviceInfo.getBusinessService();
			
			if (services.size() > 0) {
				BusinessService businessS = services.get(0);
				AccessPoint ap = businessS.getBindingTemplates()
						.getBindingTemplate().get(0).getAccessPoint();
				endpoint = ap.getValue();
			}
		}
		log.exiting("UDDI Manager", "getServiceAccessPointForThisBusiness<");
		return endpoint;
	}
	
	/**
	 * get all businesses that are published to uddi, e.g. CE, MN1
	 * 
	 * @return a list of business names
	 */
	public List<String> getAllPublishedBusinesses() {
		try {
			
			authenticate();
			FindBusiness fb = new FindBusiness();
			Name anyBusiness = new Name();
			anyBusiness.setValue("%");
			
			fb.getName().add(anyBusiness);

			FindQualifiers fq = new FindQualifiers();
			fq.getFindQualifier()
					.add(org.apache.juddi.query.util.FindQualifiers.APPROXIMATE_MATCH);
			
			fb.setFindQualifiers(fq);

			fb.setAuthInfo(tokenPublish.getAuthInfo());


			BusinessList bilist = inquiry.findBusiness(fb);
			List<String> businessNames = new ArrayList<String>();
			
			if (bilist != null
					&& bilist.getListDescription().getActualCount() > 0) {
				for (BusinessInfo bi : bilist.getBusinessInfos()
						.getBusinessInfo()) {
					System.out.println("business: "+bi.getName().get(0).getValue());
					if (bi != null
							&& bi.getName().get(0).getValue().indexOf("UDDI") == -1) {//escape UDDI node
						businessNames.add(bi.getName().get(0).getValue());
					}
						
				}
						
			}
			
			return businessNames;

		} catch (DispositionReportFaultMessage e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	/**
	 * get the published accesspoints from UDDI businesses. NOTE: within this implementation just the first service per business will be retrieved
	 * 
	 * @return a list of all access points published to UDDI 
	 */
	public List<String> getAllPublishedAccessPoints() {
		try {
			
			authenticate();
			FindBusiness fb = new FindBusiness();
			Name anyBusiness = new Name();
			anyBusiness.setValue("%");
			
			fb.getName().add(anyBusiness);

			FindQualifiers fq = new FindQualifiers();
			fq.getFindQualifier()
					.add(org.apache.juddi.query.util.FindQualifiers.APPROXIMATE_MATCH);
			
			fb.setFindQualifiers(fq);

			fb.setAuthInfo(tokenPublish.getAuthInfo());


			BusinessList bilist = inquiry.findBusiness(fb);
			List<String> accessPoints = new ArrayList<String>();
			
			if (bilist != null
					&& bilist.getListDescription().getActualCount() > 0) {
				for (BusinessInfo bi : bilist.getBusinessInfos()
						.getBusinessInfo()) {
					System.out.println("business: "+bi.getName().get(0).getValue());
					if (bi != null
							&& bi.getName().get(0).getValue().indexOf("UDDI") == -1) {//escape UDDI node
						accessPoints.add(this.getServiceAccessPointBy(bi.getName().get(0).getValue(), bi.getServiceInfos().getServiceInfo().get(0).getName().get(0).getValue()));
					}
						
				}
						
			}
			
			return accessPoints;

		} catch (DispositionReportFaultMessage e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * get the published accesspoint from UDDI for the given business. NOTE: within this implementation just the first service per business will be retrieved
	 * 
	 * @param businessName name of business (e.g. CE)
	 * @return URL of accesspoint, e.g. http://140.78.73.67:8080/CEStockExchangeWS/services/exchangeservice?wsdl
	 */
	public String getPublishedAccessPointFor(String businessName) {
		try {
			
			authenticate();
			FindBusiness fb = new FindBusiness();
			Name anyBusiness = new Name();
			anyBusiness.setValue(businessName);
			
			fb.getName().add(anyBusiness);

			FindQualifiers fq = new FindQualifiers();
			fq.getFindQualifier()
					.add(org.apache.juddi.query.util.FindQualifiers.APPROXIMATE_MATCH);
			
			fb.setFindQualifiers(fq);

			fb.setAuthInfo(tokenPublish.getAuthInfo());


			BusinessList bilist = inquiry.findBusiness(fb);
			String accessPoint = null;
			
			if (bilist != null
					&& bilist.getListDescription().getActualCount() > 0) {
				for (BusinessInfo bi : bilist.getBusinessInfos()
						.getBusinessInfo()) {
					System.out.println("business: "+bi.getName().get(0).getValue());
					if (bi != null
							&& bi.getName().get(0).getValue().indexOf("UDDI") == -1) {//escape UDDI node
						accessPoint = this.getServiceAccessPointBy(bi.getName().get(0).getValue(), bi.getServiceInfos().getServiceInfo().get(0).getName().get(0).getValue());
					}
						
				}
						
			}
			
			return accessPoint;

		} catch (DispositionReportFaultMessage e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	/**
	 * initialize values of manager for Juddi connection
	 */
	private void init() {
		log.entering("UDDI Manager", "init>");
		try {

			if (security == null || _juddiApi == null || publish == null) {
				//TODO
				String uddifile = getResource("uddi.xml");//necessary in web context -> uddi.xml needs to be located in WebContent/WEB-INF/classes/META-INF
//				String uddifile = getAbsoluteFilePath("src/at/jku/ce/juddi/uddi.xml"); //adaption for simple run as Java application


				UDDIClerkManager manager = new UDDIClerkManager(uddifile);
				UDDIClientContainer.addClerkManager(manager);
				manager.start();
				String clazz = manager.getClientConfig().getUDDINode("default")
						.getProxyTransport();
				Class<?> transportClass = ClassUtil.forName(clazz,
						Transport.class);
				log.info("++++ contacting transportclass ...");
				if (transportClass != null) {
					log.info("++++ done");
					Transport transport = (Transport) transportClass
							.getConstructor(String.class)
							.newInstance("default");

					try {
						log.info("++++ getting security service");
						security = transport.getUDDISecurityService();
						publish = transport.getUDDIPublishService();
						inquiry = transport.getUDDIInquiryService();
						_juddiApi = transport.getJUDDIApiService();
						log.info("++++ done");
					} catch (Exception ee) {
						log.info("++++ Stacktrace of init: "+ee.getMessage());
					}
				}
			}
		} catch (Exception e) {
			log.severe(e.getLocalizedMessage());
		}
		log.exiting("UDDI Manager", "init<");
	}
	
	/**
	 * get AuthToken for publisher to be able to save changes to UDDI
	 * 
	 * @return default AuthToken for "se-admin" publisher
	 * @throws RemoteException
	 * @throws ConnectException
	 */
	private AuthToken authenticate()throws RemoteException, ConnectException {
		if (tokenPublish == null) {
			GetAuthToken ga = new GetAuthToken();
			ga.setUserID(this.getUserId());
			ga.setCred(this.getCredentials());
			log.info("++++ Security: "+security);
			tokenPublish = security.getAuthToken(ga);
		}
		return tokenPublish;
	}
	
	


	/**
	 * register a business (e.g. CE, MN1,..) at JUDDI. If business already exists, existing businessKey will be returned
	 * 
	 * @param businessName name of business (e.g. CE, MN1,..)
	 * @return businessKey key for business
	 * @throws RemoteException
	 * @throws ConnectException
	 */
	private String registerBusiness(String businessName)
			throws RemoteException, ConnectException {
		log.entering("UDDI Manager", "registerBusiness>");

		authenticate();

		// Name object needed for searching or creating the business
		Name myBusName = new Name();
		myBusName.setValue(businessName);

		String businessKey = null;
		FindBusiness fb = new FindBusiness();
		fb.getName().add(myBusName);
		fb.setAuthInfo(tokenPublish.getAuthInfo());
		BusinessList list = inquiry.findBusiness(fb);
		if (list.getListDescription().getActualCount() > 0) {
			for (BusinessInfo bi : list.getBusinessInfos()
					.getBusinessInfo()) {
				log.info("BUSINESS:");
				log.info(bi.getName().get(0).getValue());
				log.info(bi.getBusinessKey());
				businessKey = bi.getBusinessKey();
			}

		} else {
				// Creating the parent business entity that will provide some
				// service.
				BusinessEntity myBusEntity = new BusinessEntity();
				myBusEntity.getName().add(myBusName);
		
				SaveBusiness sb = new SaveBusiness();
				sb.getBusinessEntity().add(myBusEntity);
				sb.setAuthInfo(tokenPublish.getAuthInfo());
				BusinessDetail bd = publish.saveBusiness(sb);
				List<BusinessEntity> be = bd.getBusinessEntity();
				if (be.size() > 0)
					businessKey = be.get(0).getBusinessKey();
				else
					businessKey = null;
		}
		log.exiting("UDDI Manager", "registerBusiness<");
		return businessKey;
	}
	
	
	/**
	 * get key of business
	 *
	 * @param businessName
	 * @return
	 * @throws RemoteException
	 * @throws ConnectException
	 */
	private String getBusinessKey(String businessName)
			throws RemoteException, ConnectException {
		log.entering("UDDI Manager", "registerBusiness>");

		authenticate();

		// Name object needed for searching or creating the business
		Name myBusName = new Name();
		myBusName.setValue(businessName);

		String businessKey = null;
		FindBusiness fb = new FindBusiness();
		fb.getName().add(myBusName);
		fb.setAuthInfo(tokenPublish.getAuthInfo());
		BusinessList list = inquiry.findBusiness(fb);
		if (list.getListDescription().getActualCount() > 0) {
			for (BusinessInfo bi : list.getBusinessInfos()
					.getBusinessInfo()) {
				log.info("BUSINESS:");
				log.info(bi.getName().get(0).getValue());
				log.info(bi.getBusinessKey());
				businessKey = bi.getBusinessKey();
			}

		} 
		log.exiting("UDDI Manager", "registerBusiness<");
		return businessKey;
	}



	/**
	 * NOTE: within this implementation the assumption is that a business has only one service to offer
	 * 
	 * @param businesskey
	 * @param servicename
	 * @param accesspointURL
	 * @return
	 * @throws RemoteException
	 * @throws ConnectException
	 */
	private String registerService(String businesskey, String servicename,
			String accesspointURL) throws RemoteException, ConnectException {
		log.entering("UDDI Manager", "registerService>");


		// Name object for the service's name
		Name sname = new Name();
		sname.setLang("de");
		sname.setValue(servicename);
		String serviceKey = null;

		// first check if service already registered
		FindService fs = new FindService();
		fs.setBusinessKey(businesskey);
		fs.getName().add(sname);
		ServiceList list = inquiry.findService(fs);

		if (list.getListDescription().getActualCount() > 0) {
			for (ServiceInfo si : list.getServiceInfos().getServiceInfo()) {
				log.info("SERVICE:");
				log.info(si.getName().get(0).getValue());
				log.info(si.getServiceKey());
				serviceKey = si.getServiceKey();

				// update / overwrite service info
				saveService(businesskey, serviceKey, sname, accesspointURL);
			}

		} else {
			serviceKey = saveService(businesskey, null, sname, accesspointURL);
		}
		log.exiting("UDDI Manager", "registerService<");
		return serviceKey;
	}
	
	/**
	 * get Service key
	 * 
	 * @param businesskey
	 * @param servicename
	 * @return
	 * @throws RemoteException
	 * @throws ConnectException
	 */
	private String getServiceKey(String businesskey, String servicename) throws RemoteException, ConnectException {
		log.entering("UDDI Manager", "registerService>");

		// Name object for the service's name
		Name sname = new Name();
		sname.setLang("de");
		sname.setValue(servicename);
		String serviceKey = null;

		// first check if service already registered
		FindService fs = new FindService();
		fs.setBusinessKey(businesskey);
		fs.getName().add(sname);
		ServiceList list = inquiry.findService(fs);

		if (list.getListDescription().getActualCount() > 0) {
			for (ServiceInfo si : list.getServiceInfos().getServiceInfo()) {
				log.info("SERVICE:");
				log.info(si.getName().get(0).getValue());
				log.info(si.getServiceKey());
				serviceKey = si.getServiceKey();
			}
		} 
		log.exiting("UDDI Manager", "registerService<");
		return serviceKey;
	}
	
	
	private ServiceList getServiceKeys(String businesskey) throws RemoteException, ConnectException {
		log.entering("UDDI Manager", "registerService>");

		// Name object for the service's name
		Name sname = new Name();
		sname.setLang("de");
		sname.setValue("%");

		// first check if service already registered
		FindService fs = new FindService();
		fs.setBusinessKey(businesskey);
		fs.getName().add(sname);
		return inquiry.findService(fs);

	}

	/**
	 * save service
	 * 
	 * @param businesskey
	 * @param servicekey
	 * @param sname
	 * @param accesspointURL
	 * @return
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 * @throws ConnectException
	 */
	private String saveService(String businesskey, String servicekey,
			Name sname, String accesspointURL)
			throws DispositionReportFaultMessage, RemoteException,
			ConnectException {

		authenticate();
		
		AccessPoint ap = new AccessPoint();
		ap.setUseType("wsdlDeployment");
		ap.setValue(accesspointURL);

		BindingTemplate temp = new BindingTemplate();
		temp.setAccessPoint(ap);

		BindingTemplates temps = new BindingTemplates();
		temps.getBindingTemplate().add(temp);

		BusinessService bs = new BusinessService();
		if (businesskey != null)
			bs.setBusinessKey(businesskey);
		if (servicekey != null)
			bs.setServiceKey(servicekey);

		bs.getName().add(sname);

		bs.setBindingTemplates(temps);

		SaveService saves = new SaveService();
		saves.setAuthInfo(tokenPublish.getAuthInfo());
		saves.getBusinessService().add(bs);
		ServiceDetail sd = publish.saveService(saves);
		return sd.getBusinessService().get(0).getServiceKey();
	}


	/**
	 * help method to retrieve file location in web context
	 * 
	 * @param name
	 * @return
	 */
	private String getResource(String name) {
		String result = null;
		/*
		 * find resource requires CXF 2.5; jUddi uses cxf 2.1 try { result =
		 * org.apache.cxf.bus.spring.BusApplicationContext
		 * .findResource(name).getFile().getAbsolutePath(); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */
		if (result == null) {
			ClassLoader threadClassLoader = Thread.currentThread()
					.getContextClassLoader();
			if (threadClassLoader != null) {
				URL url = threadClassLoader.getResource(name);
				if (url != null) {
					try {
						result = new File(url.toURI()).getAbsolutePath();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (result == null) {
			ClassLoader callerClassLoader = this.getClass().getClassLoader();
			URL url = callerClassLoader.getResource(name);
			if (url != null) {
				try {
					result = new File(url.toURI()).getAbsolutePath();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
		log.info("getResource name: "+name);
		log.info("getResource result: "+result);
		return result;
	}
	
	/**
	 * help method to retrieve absolut file path
	 * 
	 * @param name
	 * @return
	 */
	private static String getAbsoluteFilePath(String fileName) {
		String result = null;
		File f = null;
		try {
			f = new File(fileName);
			result = f.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
		
		
	
	public String getUserId() {
		return userIdPublish;
	}

	public void setUserId(String userId) {
		userIdPublish = userId;
	}

	public String getCredentials() {
		return credentialsPublish;
	}

	public void setCredentials(String credentials) {
		credentialsPublish = credentials;
	}

	
	
	
	

	public static void main(String[] args) {

		UddiManager inst = UddiManager.getInstance();
	    //call relevant methods for publishing of your service here


	}

}
