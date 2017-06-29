import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@WebServlet("/Trending")

public class Trending extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Trending</a>");
		pw.print("</h2><div class='entry'>");
		
		pw.print("<a style='font-size: 18px;'>Top five most liked products</a><br>");
	 	pw.print("<table class='gridtable' id='rate'>");
		AggregationOutput rate_aggregate = MongoDBDataStoreUtilities.rateAggregate();
		for (DBObject result : rate_aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			String tableData = "<tr><td> "+bobj.getString("value")+"</td>&nbsp"
			+ "<td>"+bobj.getString("ReviewValue")+"</td></tr>";
			pw.print(tableData);
		}
		pw.print("</table>");
		
		pw.print("<br><br><a style='font-size: 18px;'>Top five zip-codes where maximum number of products sold</a><br>");
		pw.print("<table class='gridtable' id='zip'>");
		AggregationOutput zip_aggregate = MongoDBDataStoreUtilities.zipAggregate();
		for (DBObject result : zip_aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			String tableData = "<tr><td> "+bobj.getString("value")+"</td>&nbsp"
			+ "<td>"+bobj.getString("ReviewValue")+"</td></tr>";
			pw.print(tableData);
		}
		pw.print("</table>");
		
		pw.print("<br><br><a style='font-size: 18px;'>Top five most sold products regardless of the rating</a><br>");
		pw.print("<table class='gridtable' id='productReview'>");
		AggregationOutput productReview_aggregate = MongoDBDataStoreUtilities.productReviewAggregate();
		for (DBObject result : productReview_aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			String tableData = "<tr><td> "+bobj.getString("value")+"</td>&nbsp"
			+ "<td>"+bobj.getString("ReviewValue")+"</td></tr>";
			pw.print(tableData);
		}
		pw.print("</table>");	
		
		pw.print("</h2></div></div></div>");		
		utility.printHtml("Footer.html");
		
	}



	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	}

}

