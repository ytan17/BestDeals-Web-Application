import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LawnMowersList")

public class LawnMowersList extends HttpServlet {

	/* LawnMowers Page Displays all the LawnMowers and their Information in Laptop Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the lawnMowers type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, Laptop> hm = new HashMap<String, Laptop>();
		
		if(CategoryName==null)
		{
			hm.putAll(SaxParserDataStore.lawnMowers);
			name = "";
		}
		else
		{
		  if(CategoryName.equals("ego"))
		  {
			for(Map.Entry<String,Laptop> entry : SaxParserDataStore.lawnMowers.entrySet())
				{
				if(entry.getValue().getRetailer().equals("EGO"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "EGO";
		  }
		  else if(CategoryName.equals("honda"))
		  {
			for(Map.Entry<String,Laptop> entry : SaxParserDataStore.lawnMowers.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Honda"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Honda";
		  }
		  else if(CategoryName.equals("toro"))
		  {
			for(Map.Entry<String,Laptop> entry : SaxParserDataStore.lawnMowers.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Toro"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Toro";
		  }
		  else if(CategoryName.equals("cubcadet"))
		  {
			for(Map.Entry<String,Laptop> entry : SaxParserDataStore.lawnMowers.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Cub Cadet"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Cub Cadet";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the lawnMowers and lawnMowers information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" LawnMowers</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Laptop> entry : hm.entrySet()){
			Laptop laptop = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+laptop.getName()+"</h3>");
			pw.print("<strong>"+ "$" + laptop.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/lawnMowers/"+lawnMower.getImage()+"' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lawnMowers'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='CartRent'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lawnMowers'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnrent' value='Rent'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lawnMowers'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lawnMowers'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) pw.print("</tr>");
			i++;
		}		
		pw.print("</table></div></div></div>");		
		utility.printHtml("Footer.html");
		
	}

}
