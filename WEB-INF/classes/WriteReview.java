import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/WriteReview")

// once the user clicks buy now button page is redirected to checkout page where
// user has to give checkout information

public class WriteReview extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities Utility = new Utilities(request, pw);

		storeReview(request, response);
	}

	protected void storeReview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String submit = request.getParameter("submit");
		String productName = request.getParameter("name");
		String productType = request.getParameter("type");
		String productMaker = request.getParameter("maker");
		String productPrice = request.getParameter("price");
		String userAge = request.getParameter("age");
		String userGender = request.getParameter("gender");
		String userOccupation = request.getParameter("occupation");
		String retailerName = "BestDeals";
		String retailerZip = request.getParameter("zipCode");
		String retailerCity = request.getParameter("city");
		String retailerState = request.getParameter("state");
		// check!! the type
		String productOnSale = request.getParameter("onSale");
		String manufacturerRebate = request.getParameter("rebate");
		String rate = request.getParameter("rate");
		int reviewRate = 0;
		try {
		reviewRate = Integer.parseInt(rate);
		} catch (NumberFormatException e) {
			
		}
		String reviewDate = request.getParameter("date");
		String reviewText = request.getParameter("reviewText");

		try {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request, pw);
			if (!utility.isLoggedin()) {
				HttpSession session = request.getSession(true);
				session.setAttribute("login_msg", "Please Login to write the review");
				response.sendRedirect("Login");
				return;
			}
			HttpSession session = request.getSession();
			String userName = session.getAttribute("username").toString();

			if (submit.equals("StoreReview")) {
				HashMap<String, ArrayList<Review>> reviews = new HashMap<String, ArrayList<Review>>();
				try {
					reviews = MongoDBDataStoreUtilities.selectReview();
				} catch (Exception e) {
				}
				if (!reviews.containsKey(productName)) {
					ArrayList<Review> arr = new ArrayList<Review>();
					reviews.put(productName, arr);
				}
				ArrayList<Review> listReview = reviews.get(productName);
				Review review = new Review(productName, userName, userAge, userGender, userOccupation, retailerZip,
						retailerCity, retailerState, productOnSale, manufacturerRebate, reviewRate, reviewDate,
						reviewText);
				listReview.add(review);
				try {
					MongoDBDataStoreUtilities.insertReview(productName, userName, userAge, userGender, userOccupation,
							retailerZip, retailerCity, retailerState, productOnSale, manufacturerRebate, reviewRate,
							reviewDate, reviewText);
				} catch (Exception e) {
				}
			}

			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<form name ='WriteReview' action='WriteReview' method='post'>");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Review</a>");
			pw.print("</h2><div class='entry'>");

			if (submit.equals("StoreReview")) {
				pw.print("Review submitted.");
			}

			else {
				// product info
				pw.print("<table ><tr><td>");
				pw.print("Product Name: " + productName + "</td></tr><input type='hidden' name='name' value='"
						+ productName + "'>");

				pw.print("<tr><td>");
				pw.print("Product Type: " + productType + "</td></tr>");

				pw.print("<tr><td>");
				pw.print("Product Maker: " + productMaker + "</td></tr>");

				pw.print("<tr><td>");
				pw.print("Product Price: " + productPrice + "</td></tr>");
				
				pw.print("<tr><td></td></tr>");
				pw.print("<tr><td></td></tr>");

				// user info
				pw.print("<tr><td>");
				pw.print("Customer Name: " + userName + "</td></tr>");

				pw.print("<tr><td>");
				pw.print("Customer Age: </td>");
				pw.print("<td><input type='text' name='age'>");
				pw.print("</td></tr>");

				pw.print("<tr><td>");
				pw.print("Customer Gender: </td>");
				pw.print("<td><input type='text' name='gender'>");
				pw.print("</td></tr>");

				pw.print("<tr><td>");
				pw.print("Customer Occupation: </td>");
				pw.print("<td><input type='text' name='occupation'>");
				pw.print("</td></tr>");
				
				pw.print("<tr><td></td></tr>");
				pw.print("<tr><td></td></tr>");

				// retailer info
				pw.print("<tr><td>");
				pw.print("Retailer Name: " + retailerName + "</td></tr>");

				pw.print("<tr><td>");
				pw.print("Retailer zipCode: </td>");
				pw.print("<td><input type='text' name='zipCode'>");
				pw.print("</td></tr>");

				pw.print("<tr><td>");
				pw.print("Retailer City: </td>");
				pw.print("<td><input type='text' name='city'>");
				pw.print("</td></tr>");

				pw.print("<tr><td>");
				pw.print("Retailer State: </td>");
				pw.print("<td><input type='text' name='state'>");
				pw.print("</td></tr>");
				
				pw.print("<tr><td></td></tr>");
				pw.print("<tr><td></td></tr>");

				// onSale, rebate
				pw.print("<tr><td>");
				pw.print("Product onSale: </td>");
				pw.print("<td><input type='radio' name='onSale'>Yes<br>");
				pw.print("<td><input type='radio' name='onSale'>No<br>");
				pw.print("</td></tr>");

				pw.print("<tr><td>");
				pw.print("ManufactureRebate: </td>");
				pw.print("<td><input type='radio' name='rebate'>Yes<br>");
				pw.print("<td><input type='radio' name='rebate'>No<br>");
				pw.print("</td></tr>");
				
				pw.print("<tr><td></td></tr>");
				pw.print("<tr><td></td></tr>");

				// review
				pw.print("<tr><td>");
				pw.print("Review Rating(1-5): </td>");
				pw.print("<td><input type='text' name='rate'>");
				pw.print("</td></tr>");

				pw.print("<tr><td>");
				pw.print("Review Date(MM/DD/YYYY): </td>");
				pw.print("<td><input type='text' name='date'>");
				pw.print("</td></tr>");

				pw.print("<tr><td>");
				pw.print("Review: </td>");
				pw.print("<td><input type='text' name='reviewText'>");
				pw.print("</td></tr>");

				pw.print("<tr><td colspan='2'>");
				pw.print("<input type='submit' name='submit' value='StoreReview' class='btnbuy'>");
			}
			pw.print("</td></tr></table></form>");
			pw.print("</div></div></div>");
			utility.printHtml("Footer.html");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	}
}
