import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Review")

/* 
	OrderItem class contains class variables name,price,image,retailer.

	OrderItem  class has a constructor with Arguments name,price,image,retailer.
	  
	OrderItem  class contains getters and setters for name,price,image,retailer.
*/

public class Review extends HttpServlet{
	private String productName;
	private String userName;
	private String userAge;
	private String userGender;
	private String userOccupation;
	private String retailerZip;
	private String retailerCity;
	private String retailerState;
	private String productOnSale;
	private String manufacturerRebate;
	private int reviewRate;
	private String reviewDate;
	private String reviewText;
	
	public Review(String productName, String userName, String userAge, String userGender, String userOccupation, String retailerZip, String retailerCity, String retailerState, String productOnSale,
			String manufacturerRebate, int reviewRate, String reviewDate, String reviewText){
		this.productName = productName;
		this.userName = userName;
		this.userAge = userAge;
		this.userGender = userGender;
		this.userOccupation = userOccupation;
		this.retailerZip = retailerZip;
		this.retailerCity = retailerCity;
		this.retailerState = retailerState;
		this.productOnSale = productOnSale;
		this.manufacturerRebate = manufacturerRebate;
		this.reviewRate = reviewRate;
		this.reviewDate = reviewDate;
		this.reviewText = reviewText;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getUserAge() {
		return userAge;
	}
	
	public void SetUserAge(String userAge) {
		this.userAge = userAge;
	}
	
	public String getUserGender() {
		return userGender;
	}
	
	public void SetUserGender(String userGender) {
		this.userGender = userGender;
	}
	
	public String getUserOccupation() {
		return userOccupation;
	}
	
	public void SetUserOccupation(String userOccupation) {
		this.userOccupation = userOccupation;
	}
	
	public String getRetailerZip() {
		return retailerZip;
	}

	public void setRetailerZip(String retailerZip) {
		this.retailerZip = retailerZip;
	}
	
	public String getRetailerCity() {
		return retailerCity;
	}

	public void setRetailerCity(String retailerCity) {
		this.retailerCity = retailerCity;
	}
	
	public String getRetailerState() {
		return retailerState;
	}

	public void setRetailerState(String retailerState) {
		this.retailerState = retailerState;
	}
	
	public String getProductOnSale() {
		return productOnSale;
	}

	public void setProductOnSale(String productOnSale) {
		this.productOnSale = productOnSale;
	}
	
	public String getManufacturerRebate() {
		return manufacturerRebate;
	}

	public void setManufacturerRebate(String manufacturerRebate) {
		this.manufacturerRebate = manufacturerRebate;
	}
	
	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	public int getReviewRate() {
		return reviewRate;
	}

	public void setReviewRate(int reviewRate) {
		this.reviewRate = reviewRate;
	}
	
	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
}
