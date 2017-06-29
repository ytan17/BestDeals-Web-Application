import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;

@WebServlet("/SalesViewOrder")

public class SalesViewOrder extends HttpServlet {
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		
		String username=utility.username();
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='SalesViewOrder' action='SalesViewOrder' method='get'>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>SalesViewOrder</a>");
		pw.print("</h2><div class='entry'>");

		//hashmap gets all the order details from file 

		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		MySqlDataStoreUtilities msdsu = new MySqlDataStoreUtilities();
		String TOMCAT_HOME = System.getProperty("catalina.home");

		try
		{
			orderPayments= msdsu.selectOrder();
		}
		catch(Exception e)
		{
		}
		

		/*if order button is clicked that is user provided a order number to view order 
		order details will be fetched and displayed in  a table 
		Also user will get an button to cancel the order */

		if(request.getParameter("Order")==null )
		{
			try
		    {
				orderPayments= msdsu.selectOrder();
			}
			catch(Exception e)
			{
			
			}
		    int size=0;

			/*get the order size and check if there exist an order with given order number 
			if there is no order present give a message no order stored with this id */

			Set<Integer> orderIds = orderPayments.keySet();
			for(Integer id : orderIds){
				for(OrderPayment od:orderPayments.get(id)){	
				size+= orderPayments.get(id).size();
				}
			}

			// display the orders if there exist order with order id
			if(size>0)
			{	
				pw.print("<table  class='gridtable'>");
				pw.print("<tr><td></td>");
				pw.print("<td>OrderId:</td>");
				pw.print("<td>UserName:</td>");
				pw.print("<td>productOrdered:</td>");
				pw.print("<td>productPrice:</td></tr>");
				for(Integer id : orderIds){
					for (OrderPayment oi : orderPayments.get(id)) 
					{
						pw.print("<tr>");			
						pw.print("<td><input type='radio' name='orderName' value='"+oi.getOrderName()+"_"+oi.getOrderId()+"'></td>");			
						pw.print("<td>"+oi.getOrderId()+".</td><td>"+oi.getUserName()+"</td><td>"+oi.getOrderName()+"</td>");
						
						pw.print("<td><input type = 'text' size = '8' name ='Price"+oi.getOrderName()+"_"+oi.getOrderId()+"' value = '"+ oi.getOrderPrice()+"'</td>");
						pw.print("<td><input type='submit' name='Order' value='UpdateOrder' class='btnbuy'></td>");

						pw.print("<td><input type='submit' name='Order' value='CancelOrder' class='btnbuy'></td>");

						pw.print("</tr>");
						
					}
				}
				pw.print("</table>");

				//display the addOrder table
				pw.print("<br/>");
				pw.print("<h2 class='title meta'>");
				pw.print("<a style='font-size: 24px;'>Add new Order</a>");
				pw.print("</h2>");
				pw.print("<br/>");
				pw.print("<table  class='gridtable'>");
				pw.print("<tr><td>OrderId:</td>");
				pw.print("<td>UserName:</td>");
				pw.print("<td>productOrdered:</td>");
				pw.print("<td>productPrice:</td>");
				pw.print("<td>userAddress:</td>");
				pw.print("<td>creditCardNo</td></tr>");
				pw.print("<tr><td><input type='text' size = '5' name='newOrderId'value = '' ></td>");
				pw.print("<td><input type='text' size = '5' name='newUserName'value = '' ></td>");
				pw.print("<td><input type='text' size = '5' name='newproductOrdered'value = '' ></td>");
				pw.print("<td><input type='text' size = '5' name='newproductPrice'value = '' ></td>");
				pw.print("<td><input type='text' size = '5' name='newuserAddress'value = '' ></td>");
				pw.print("<td><input type='text' size = '5' name='newCreditCardNo'value = '' ></td></tr>");

				pw.print("<td><input type='submit' name='Order' value='AddOrder' class='btnbuy'></td>");

				pw.print("</table>");
			}
			else
			{
				pw.print("<h4 style='color:red'>You have not placed any order with this order id</h4>");
			}
		}

		//if the user presses cancel order from order details shown then process to cancel the order
		if(request.getParameter("Order")!=null && request.getParameter("Order").equals("CancelOrder"))
		{
			String orderName=request.getParameter("orderName").split("_")[0];
			int orderId=0;
			orderId=Integer.parseInt(request.getParameter("orderName").split("_")[1]);
			ArrayList<OrderPayment> ListOrderPayment =new ArrayList<OrderPayment>();
			//get the order from file
			try
			{
		
				orderPayments= msdsu.selectOrder();
			}
			catch(Exception e)
			{}

			//get the exact order with same ordername and add it into cancel list to remove it later
			for (OrderPayment oi : orderPayments.get(orderId)) 
				{
						if(oi.getOrderName().equals(orderName))
						{
							 ListOrderPayment.add(oi);
							 pw.print("<h4 style='color:red'>Your Order is Cancelled</h4>");								
						}
				}
			//remove all the orders from hashmap that exist in cancel list
			orderPayments.get(orderId).removeAll(ListOrderPayment);
			if(orderPayments.get(orderId).size()==0)
				{
						orderPayments.remove(orderId);
				}
			//save the updated hashmap with removed order to the file	

			String userName = request.getParameter("username");
			try
			{	
				msdsu.deleteOrder(orderId);
			}
			catch(Exception e)
			{
			
			}	
		}

		//if the user presses add order, then process to add new order
		if(request.getParameter("Order")!=null && request.getParameter("Order").equals("AddOrder"))
		{	
			Integer orderId = Integer.parseInt(request.getParameter("newOrderId"));
			String userName = request.getParameter("newUserName");
			String orderName = request.getParameter("newproductOrdered");
			Double orderPrice = Double.parseDouble(request.getParameter("newproductPrice"));
			String userAddress = request.getParameter("newuserAddress");
			String creditCardNo = request.getParameter("newCreditCardNo");
			msdsu.insertOrder(orderId,userName,orderName,orderPrice,userAddress,creditCardNo);

			// display the salesViewOrder page agian after udated order.
			int size=0;

			/*get the order size and check if there exist an order with given order number 
			if there is no order present give a message no order stored with this id */

			Set<Integer> orderIds = orderPayments.keySet();
			for(Integer id : orderIds){
				for(OrderPayment od:orderPayments.get(id)){	
				size+= orderPayments.get(id).size();
				}
			}
			if(size>0)
			{	
				pw.print("<table  class='gridtable'>");
				pw.print("<tr><td></td>");
				pw.print("<td>OrderId:</td>");
				pw.print("<td>UserName:</td>");
				pw.print("<td>productOrdered:</td>");
				pw.print("<td>productPrice:</td></tr>");
				for(Integer id : orderIds){
					for (OrderPayment oi : orderPayments.get(id)) 
					{
						pw.print("<tr>");			
						pw.print("<td><input type='radio' name='orderName' value='"+oi.getOrderName()+"_"+oi.getOrderId()+"'></td>");			
						pw.print("<td>"+oi.getOrderId()+".</td><td>"+oi.getUserName()+"</td><td>"+oi.getOrderName()+"</td>");
						
						pw.print("<td><input type = 'text' size = '8' name ='Price"+oi.getOrderName()+"_"+oi.getOrderId()+"' value = '"+ oi.getOrderPrice()+"'</td>");
						pw.print("<td><input type='submit' name='Order' value='UpdateOrder' class='btnbuy'></td>");

						pw.print("<td><input type='submit' name='Order' value='CancelOrder' class='btnbuy'></td>");

						pw.print("</tr>");
						
					}
				}
				pw.print("</table>");
			}

			//display the addOrder table
			pw.print("<br/>");
			pw.print("<h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Add new Order</a>");
			pw.print("</h2>");
			pw.print("<br/>");
			pw.print("<table  class='gridtable'>");
			pw.print("<tr><td>OrderId:</td>");
			pw.print("<td>UserName:</td>");
			pw.print("<td>productOrdered:</td>");
			pw.print("<td>productPrice:</td>");
			pw.print("<td>userAddress:</td>");
			pw.print("<td>creditCardNo</td></tr>");
			pw.print("<tr><td><input type='text' size = '5' name='newOrderId'value = '' ></td>");
			pw.print("<td><input type='text' size = '5' name='newUserName'value = '' ></td>");
			pw.print("<td><input type='text' size = '5' name='newproductOrdered'value = '' ></td>");
			pw.print("<td><input type='text' size = '5' name='newproductPrice'value = '' ></td>");
			pw.print("<td><input type='text' size = '5' name='newuserAddress'value = '' ></td>");
			pw.print("<td><input type='text' size = '5' name='newCreditCardNo'value = '' ></td></tr>");

			pw.print("<td><input type='submit' name='Order' value='AddOrder' class='btnbuy'></td>");

			pw.print("</table>");
		}

		
		//if the user presses update order from order details shown then process to update the order price
		if(request.getParameter("Order")!=null && request.getParameter("Order").equals("UpdateOrder")){
			String orderName=request.getParameter("orderName").split("_")[0];
			int orderId=0;
			orderId=Integer.parseInt(request.getParameter("orderName").split("_")[1]);
			Double orderPrice = Double.parseDouble(request.getParameter("Price"+orderName+"_"+Integer.toString(orderId)));

			//get the order from file
			try
			{
				msdsu.updateOrder(orderId,orderName,orderPrice);
				orderPayments= msdsu.selectOrder();
			}
			catch(Exception e){}

			// display the salesViewOrder page agian after udated order.
			int size=0;

			/*get the order size and check if there exist an order with given order number 
			if there is no order present give a message no order stored with this id */

			Set<Integer> orderIds = orderPayments.keySet();
			for(Integer id : orderIds){
				for(OrderPayment od:orderPayments.get(id)){	
				size+= orderPayments.get(id).size();
				}
			}

			if(size>0)
			{	
				pw.print("<table  class='gridtable'>");
				pw.print("<tr><td></td>");
				pw.print("<td>OrderId:</td>");
				pw.print("<td>UserName:</td>");
				pw.print("<td>productOrdered:</td>");
				pw.print("<td>productPrice:</td></tr>");
				for(Integer id : orderIds){
					for (OrderPayment oi : orderPayments.get(id)) 
					{
						pw.print("<tr>");			
						pw.print("<td><input type='radio' name='orderName' value='"+oi.getOrderName()+"_"+oi.getOrderId()+"'></td>");			
						pw.print("<td>"+oi.getOrderId()+".</td><td>"+oi.getUserName()+"</td><td>"+oi.getOrderName()+"</td>");
						
						pw.print("<td><input type = 'text' size = '8' name ='Price"+oi.getOrderName()+"_"+oi.getOrderId()+"' value = '"+ oi.getOrderPrice()+"'</td>");
						pw.print("<td><input type='submit' name='Order' value='UpdateOrder' class='btnbuy'></td>");

						pw.print("<td><input type='submit' name='Order' value='CancelOrder' class='btnbuy'></td>");

						pw.print("</tr>");
						
					}
				}
				pw.print("</table>");
			}

			//display the addOrder table
			pw.print("<br/>");
			pw.print("<h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Add new Order</a>");
			pw.print("</h2>");
			pw.print("<br/>");
			pw.print("<table  class='gridtable'>");
			pw.print("<tr><td>OrderId:</td>");
			pw.print("<td>UserName:</td>");
			pw.print("<td>productOrdered:</td>");
			pw.print("<td>productPrice:</td>");
			pw.print("<td>userAddress:</td>");
			pw.print("<td>creditCardNo</td></tr>");
			pw.print("<tr><td><input type='text' size = '5' name='newOrderId'value = '' ></td>");
			pw.print("<td><input type='text' size = '5' name='newUserName'value = '' ></td>");
			pw.print("<td><input type='text' size = '5' name='newproductOrdered'value = '' ></td>");
			pw.print("<td><input type='text' size = '5' name='newproductPrice'value = '' ></td>");
			pw.print("<td><input type='text' size = '5' name='newuserAddress'value = '' ></td>");
			pw.print("<td><input type='text' size = '5' name='newCreditCardNo'value = '' ></td></tr>");

			pw.print("<td><input type='submit' name='Order' value='AddOrder' class='btnbuy'></td>");

			pw.print("</table>");

		}



		pw.print("</form></div></div></div>");		
		utility.printHtml("Footer.html");
	}

}


