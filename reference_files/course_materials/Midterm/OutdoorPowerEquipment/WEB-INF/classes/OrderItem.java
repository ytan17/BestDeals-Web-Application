import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/OrderItem")

/* 
	OrderItem class contains class variables name,price,image,retailer.

	OrderItem  class has a constructor with Arguments name,price,image,retailer.
	  
	OrderItem  class contains getters and setters for name,price,image,retailer.
*/

public class OrderItem extends HttpServlet{
	private int replacement1 = 0;
	private int replacement2 = 0;
	private int warranty=0;
	private String lName;
	private String type;
	private String maker;
	private String access;
	private String name;
	private double price;
	private String image;
	private String retailer;
	
	public OrderItem(int warranty, String lName, String type, String maker, String access, String name, double price, String image, String retailer,int replacement1, int replacement2, int limitedRent, int singleRent, int shipping){
		this.shipping = shipping;
		this.limitedRent = limitedRent;
		this.singleRent = singleRent;
		this.replacement1 = replacement1;
		this.replacement2 = replacement2;
		this.warranty = warranty;
		this.lName = lName;
		this.type = type;
		this.maker = maker;
		this.access = access;
		this.name = name;
		this.price = price;
		this.image = image;
		this.retailer = retailer;
	}
	public int getShipping() {
		return shipping;
	}

	public void setShipping(int shipping) {
		this.shipping = shipping;
	}

	public int getLimitedRent() {
		return limitedRent;
	}

	public void setLimitedRent(int limitedRent) {
		this.limitedRent = limitedRent;
	}

	public int getSingleRent() {
		return singleRent;
	}

	public void setSingleRent(int singleRent) {
		this.singleRent = singleRent;
	}

	public int getReplacement1() {
		return replacement1;
	}

	public void setReplacement1(int replacement1) {
		this.replacement1 = replacement1;
	}

	public int getReplacement2() {
		return replacement2;
	}

	public void setReplacement2(int replacement2) {
		this.replacement2 = replacement2;
	}

	public int getWarranty() {
		return warranty;
	}

	public void setWarranty(int warranty) {
		this.warranty = warranty;
	}

	public String getlName() {
		return lName;
	}

	public String getType() {
		return type;
	}

	public String getMaker() {
		return maker;
	}

	public String getAccess() {
		return access;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRetailer() {
		return retailer;
	}

	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}
}
