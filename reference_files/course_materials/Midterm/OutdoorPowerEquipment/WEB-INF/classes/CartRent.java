import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@WebServlet("/CartRent")

public class CartRent extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();


		/* From the HttpServletRequest variable name,type,maker and acessories information are obtained.*/

		Utilities utility = new Utilities(request, pw);
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String maker = request.getParameter("maker");
		String access = request.getParameter("access");

		/*Check which action should take base on the button clicked*/
		Enumeration<String> passedName = request.getParameterNames();
		String[] nameLst;
		while(passedName.hasMoreElements()){
			String element = passedName.nextElement();
			if(element!=null){
				if(element.contains("CheckOut")){
					//jump to checkout.java
					request.getRequestDispatcher("CheckOut").forward(request,response);
				} else if (element.contains("Delete")) {
					nameLst = element.split("_");
					for(String s: nameLst) {
						System.out.println(s);
					}
					utility.getCustomerOrders().remove(Integer.parseInt(nameLst[1]));
				} else if (element.contains("Add")) {
					nameLst = element.split("_");
					for(String s: nameLst) {
						System.out.println(s);
					}
					String iolName = nameLst[1];
					String iotype = nameLst[2];
					String iomaker = nameLst[3];
					String ioaccess = nameLst[4];
					String ioName = nameLst[5];
					String ioPrice = nameLst[6];
					String ioImage = nameLst[7];
					String ioRetailer = nameLst[8];
					OrderItem item = new OrderItem(0,iolName,iotype,iomaker,ioaccess,ioName,Double.parseDouble(ioPrice),ioImage,ioRetailer);
					//utility.getCustomerOrders().add(item);
					utility.storeProduct(0,iolName, iotype, iomaker, ioaccess);
				} else if (element.contains("name")&&request.getParameter("name")!=null){
					utility.storeProduct(0,name, type, maker, access);
				} else if (element.contains("warranty")){
					nameLst = element.split("_");
					for(String s: nameLst) {
						System.out.println(s);
					}
					int ioIndex = Integer.parseInt(nameLst[1]);
					utility.setOIWarranty(50, ioIndex);
				}
				else if (element.contains("limitedRent")){
					nameLst = element.split("_");
					for(String s: nameLst) {
						System.out.println(s);
					}
					int ioIndex = Integer.parseInt(nameLst[1]);
					int ioPrice = Integer.parseInt(nameLst[2]);
					utility.setOILimitedRent(ioPrice*0.5, ioIndex);
				}
				else if (element.contains("singleRent")){
					nameLst = element.split("_");
					for(String s: nameLst) {
						System.out.println(s);
					}
					int ioIndex = Integer.parseInt(nameLst[1]);
					int ioIndex = Integer.parseInt(nameLst[2]);
					utility.setOISingleRent(100, ioIndex);
				}
				else if (element.contains("delivery")){
					nameLst = element.split("_");
					for(String s: nameLst) {
						System.out.println(s);
					}
					int ioIndex = Integer.parseInt(nameLst[1]);
					utility.setOIShipping(10, ioIndex);
				}
				else if (element.contains("pickup")){
					nameLst = element.split("_");
					for(String s: nameLst) {
						System.out.println(s);
					}
					int ioIndex = Integer.parseInt(nameLst[1]);
					int ioIndex = Integer.parseInt(nameLst[2]);
					utility.setOIShipping(0, ioIndex);
				}
			}
		}


		/* StoreProduct Function stores the Purchased product in Orders HashMap.*/	
		
		//utility.storeProduct(name, type, maker, access);
		displayCart(request, response);
	}
	

/* displayCart Function shows the products that users has bought, these products will be displayed with Total Amount.*/

	protected void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
		
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='Cart' action='Cart' method='post'>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Cart("+utility.CartCount()+")</a>");
		pw.print("</h2><div class='entry'>");
		if(utility.CartCount()>0)
		{
		pw.print("<table  class='gridtable'>");
		int i = 1;
		int index = 0;
		double total = 0;
		int delivery = 10;
		int pickup = 0;
		for (OrderItem oi : utility.getCustomerOrders()) 
		{
			index = utility.getCustomerOrders().indexOf(oi);
			pw.print("<tr>");
			String add = "_"+oi.getlName()+"_"+oi.getType()+"_"+oi.getMaker()+"_"+oi.getAccess()+"_"+oi.getName()+"_"+oi.getPrice()+"_"+oi.getImage()+"_"+oi.getRetailer();
			pw.print("<td>"+i+".</td><td>"+oi.getName()+"</td><td>: "+oi.getPrice()+"</td><td><input type='submit' name='Add"+add+"' value='Add' class='btnadd'></td><td><input type='submit' name='Delete_"+index+"' value='Delete' class='btndelete'></td><td><input type='submit' name='warranty_"+index+"' value='warranty:$50' class='btnwarranty'></td><td><input type='submit' name='limitedRent_"+index+oi.getPrice()+"' value='Limited duration rental' class='btnrelimitedRent'></td><td><input type='submit' name='singleRent_"+index+oi.getPrice()+"' value='Single season rental' class='btnsingleRent'>");
			pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
            pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
            pw.print("<input type='radio' name='shipping' value='delivery"+delivery+"'> delivery <br><input type='radio' name='shipping' value='pickup"+pickup+"'> pickup <br>")
			pw.print("</tr>");
			total = total +oi.getPrice()+oi.getWarranty()+oi.getLimitedRent()+oi.getSingleRent()+oi.getShipping();
			i++;
		}
		pw.print("<input type='hidden' name='orderTotal' value='"+total+"'>");	
		pw.print("<tr><th></th><th>Total</th><th>"+total+"</th>");
		pw.print("<tr><td></td><td></td><td><input type='submit' name='CheckOut' value='CheckOut' class='btnRent'></td>");
		pw.print("</table>");
		pw.print("<br>")
		pw.print("<p>Limited duration rental (for example renting an equipment for 2 days)</p>");
		pw.print("<p>Single season rental (for example rent a snow blower for the entire winter season for a $100 fee per season)</p>");
		}
		else
		{
		pw.print("<h4 style='color:red'>Your Cart is empty</h4>");
		}
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		displayCart(request, response);
	}
}