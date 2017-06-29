import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session; 
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



	/*  Printhtml Function gets the html file name as function Argument, 
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

	public void printHtml(String file) {
		String result = HtmlToString(file);
		//to print the right navigation in header of username cart and logout etc
		if (file == "Header.html") {
				result=result+"<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				result = result + "<li><a href='ViewOrder'>ViewOrder</a></li>"
						+ "<li><a>Hello,"+username+"</a></li>"
						+ "<li><a href='Account'>Account</a></li>"
						+ "<li><a href='Logout'>Logout</a></li>";
			}
			else
				result = result +"<li><a href='ViewOrder'>View Order</a></li>"+ "<li><a href='Login'>Login</a></li>";
				result = result +"<li><a href='Cart'>Cart("+CartCount()+")</a></li></ul></div></div><div id='page'>";
				pw.print(result);
		} else
				pw.print(result);
	}
	

	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} 
		catch (Exception e) {
		}
		return result;
	} 

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
	
	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/
	
	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
	
	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}
	
	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<String, User>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{		
				FileInputStream fileInputStream=new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\outdoorPowerEquipment\\UserDetails.txt"));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				hm= (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{
			}	
		User user = hm.get(username());
		return user;
	}
	
	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{
				FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\outdoorPowerEquipment\\PaymentDetails.txt"));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				orderPayments = (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{
			
			}
			int size=0;
			for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
					 size=entry.getKey();
			}
			return size;		
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}

	/* update warranty*/
	public void setOIWarranty(int warranty, int index){
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		orderItems.get(index).setWarranty(warranty);
	}

	/* update replacement1*/
	public void setOIReplacement1(int replacement1, int index){
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		orderItems.get(index).setReplacement1(replacement1);
	}

	/* update replacement2*/
	public void setOIReplacement2(int replacement2, int index){
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		orderItems.get(index).setReplacement2(replacement2);
	}

	/* update limitedRent*/
	public void setOILimitedRent(int limitedRent, int index){
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		orderItems.get(index).setLimitedRent(limitedRent);
	}

	/* update SingleRent*/
	public void setOISingleRent(int SingleRent, int index){
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		orderItems.get(index).setSingleRent(singleRent);
	}

	/* update Shipping*/
	public void setOIShipping(int Shipping, int index){
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		orderItems.get(index).setShipping(shipping);
	}
	
	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

	public void storeProduct(int warranty,String name,String type,String maker, String acc, int replacement1, int replacement2,limitedRent, singleRent,shipping){
		if(!OrdersHashMap.orders.containsKey(username())){	
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		if(type.equals("lawnMowers")){
			LawnMower lawnMower;
			lawnMower = SaxParserDataStore.lawnMowers.get(name);
			OrderItem orderitem = new OrderItem(warranty,name, type, maker, acc, lawnMower.getName(), lawnMower.getPrice(), lawnMower.getImage(), lawnMower.getRetailer(), replacement1, replacement2,limitedRent, singleRent,shipping);
			orderItems.add(orderitem);
		}
		if(type.equals("snowBlowers")){
			SnowBlower snowBlower = null;
			snowBlower = SaxParserDataStore.snowBlowers.get(name);
			OrderItem orderitem = new OrderItem(warranty,name, type, maker, acc, snowBlower.getName(), snowBlower.getPrice(), snowBlower.getImage(), snowBlower.getRetailer(),replacement1, replacement2, limitedRent, singleRent,shipping);
			orderItems.add(orderitem);
		}
		
	}
	// store the payment details for orders
	public void storePayment(int orderId,
		String orderName,double orderPrice,String userAddress,String creditCardNo){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			// get the payment details file 
			try
			{
				FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\outdoorPowerEquipment\\PaymentDetails.txt"));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				orderPayments = (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{
			
			}
			if(orderPayments==null)
			{
				orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
			}
			// if there exist order id already add it into same list for order id or create a new record with order id
			
			if(!orderPayments.containsKey(orderId)){	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(orderId, arr);
			}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);		
		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);
		listOrderPayment.add(orderpayment);	
			
			// add order details into file

			try
			{	
				FileOutputStream fileOutputStream = new FileOutputStream(new File(TOMCAT_HOME+"\\webapps\\outdoorPowerEquipment\\PaymentDetails.txt"));
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            	objectOutputStream.writeObject(orderPayments);
				objectOutputStream.flush();
				objectOutputStream.close();       
				fileOutputStream.close();
			}
			catch(Exception e)
			{
				System.out.println("inside exception file not written properly");
			}	
	}

	
	/* getConsoles Functions returns the Hashmap with all lawnMowers in the store.*/

	public HashMap<String, LawnMower> getLawnMowers(){
			HashMap<String, LawnMower> hm = new HashMap<String, LawnMower>();
			hm.putAll(SaxParserDataStore.lawnMowers);
			return hm;
	}
	
	/* getGames Functions returns the  Hashmap with all snowBlowers in the store.*/

	public HashMap<String, SnowBlower> getSnowBlowers(){
			HashMap<String, SnowBlower> hm = new HashMap<String, SnowBlower>();
			hm.putAll(SaxParserDataStore.snowBlowers);
			return hm;
	}
	
	
	
	/* getProducts Functions returns the Arraylist of lawnMowers in the store.*/

	public ArrayList<String> getProducts(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, LawnMower> entry : getLawnMowers().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of snowBlowers in the store.*/

	public ArrayList<String> getProductsSnowBlowers(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, SnowBlower> entry : getSnowBlowers().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	

}
