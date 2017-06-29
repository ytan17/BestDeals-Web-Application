import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SnowBlowerstList")

public class SnowBlowerstList extends HttpServlet {

	/* SnowBlower Page Displays all the snowBlowers and their Information in Laptop Speed */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = null;
		String CategoryName = request.getParameter("maker");


		/* Checks the Tablets type whether it is microsft or sony or nintendo */

		HashMap<String, SnowBlower> hm = new HashMap<String, SnowBlower>();
		if(CategoryName==null){
			hm.putAll(SaxParserDataStore.snowBlowers);
			name = "";
		}
		else
		{
		   if(CategoryName.equals("toro"))
		   {
			 for(Map.Entry<String,SnowBlower> entry : SaxParserDataStore.snowBlowers.entrySet())
			 {
				if(entry.getValue().getRetailer().equals("Toro"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
			 }
				name = "Toro";
		   }
		   else if(CategoryName.equals("powersmart"))
		    {
			for(Map.Entry<String,SnowBlower> entry : SaxParserDataStore.snowBlowers.entrySet())
				{
				 if(entry.getValue().getRetailer().equals("Power Smart"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				 name = "Power Smart";
			}
			else if(CategoryName.equals("ariens"))
			{
				for(Map.Entry<String,SnowBlower> entry : SaxParserDataStore.snowBlowers.entrySet())
				{
				 if(entry.getValue().getRetailer().equals("Ariens"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Ariens";
			}
			else if(CategoryName.equals("snotek"))
			{
				for(Map.Entry<String,SnowBlower> entry : SaxParserDataStore.snowBlowers.entrySet())
				{
				 if(entry.getValue().getRetailer().equals("Sno-Tek"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Sno-Tek";
			}
		}
		
		/* Header, Left Navigation Bar are Printed.

		All the SnowBlower and SnowBlower information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" snowBlowers</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, SnowBlower> entry : hm.entrySet())
		{
			SnowBlower snowBlowers = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+snowBlowers.getName()+"</h3>");
			pw.print("<strong>$"+snowBlowers.getPrice()+"</strong><ul>");
			pw.print("<li id='item'><img src='images/snowBlowers/"+snowBlowers.getImage()+"' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='snowBlowers'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='CartRent'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='snowBlowers'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnrent' value='Rent'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='snowBlowers'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='snowBlowers'>"+
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
