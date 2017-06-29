import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TvsList")

public class TvsList extends HttpServlet {

	/* TVs Page Displays all the TVs and their Information in Best Deal */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the TVs type whether it is Samsung,Sony,LG */
				
		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, Tv> hm = new HashMap<String, Tv>();
		String tvId = request.getParameter("searchId");
		
		if(tvId != null){
			hm.put(tvId,SaxParserDataStore.tvs.get(tvId));
			name = "";
		}
		else{
			if(CategoryName==null)
			{
				hm.putAll(SaxParserDataStore.tvs);
				name = "";
			}
			else
			{
			  if(CategoryName.equals("samsung"))
			  {
				for(Map.Entry<String,Tv> entry : SaxParserDataStore.tvs.entrySet())
					{
					if(entry.getValue().getRetailer().equals("Samsung"))
					 {
						 hm.put(entry.getValue().getId(),entry.getValue());
					 }
					}
				name = "Samsung";
			  }
			  else if(CategoryName.equals("sony"))
			  {
				for(Map.Entry<String,Tv> entry : SaxParserDataStore.tvs.entrySet())
					{
					if(entry.getValue().getRetailer().equals("Sony"))
					 {
						 hm.put(entry.getValue().getId(),entry.getValue());
					 }
					}	
				name = "Sony";
			  }
			  else if(CategoryName.equals("lg"))
			  {
				for(Map.Entry<String,Tv> entry : SaxParserDataStore.tvs.entrySet())
					{
					if(entry.getValue().getRetailer().equals("LG"))
					 {
						 hm.put(entry.getValue().getId(),entry.getValue());
					 }
					}
				name = "LG";
			  }
			}
		}

		/* Header, Left Navigation Bar are Printed.

		All the TVs and TVs information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" tvs</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Tv> entry : hm.entrySet()){
			Tv tv = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+tv.getName()+"</h3>");
			pw.print("<strong>"+ "$" + tv.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/tvs/"+tv.getImage()+"' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='tvs'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='tvs'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+tv.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' name='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='tvs'>"+
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
