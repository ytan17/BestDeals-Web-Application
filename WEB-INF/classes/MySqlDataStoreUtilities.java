import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.*;

public class MySqlDataStoreUtilities
{
	Connection conn = null; 

	public void init(){
		getConnection();
	}

	public void closeConnection(){
		try{
		if(conn.isClosed()){
			conn.close();
			conn=null;
		}
		}
		catch(Exception e){}
	}

	public void getConnection()
	{ if (conn==null){
		try
		{
		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealsdatabase","root","Elac1024@");
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	}

	//Registration

	public HashMap<String, User> selectUser(){
		ResultSet rs = null;
		HashMap<String, User> map = new HashMap<String, User>();
		try{
			// Class.forName("com.mysql.jdbc.Driver").newInstance();
			// conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealsdatabase","root","Elac1024@");
			init();
			String selectUserQuery = "SELECT * FROM Registration " ;
			PreparedStatement pst =
			conn.prepareStatement(selectUserQuery);
			rs = pst.executeQuery();
			User user = null;
			
			while(rs.next()){
				user = new User(rs.getString("username"),rs.getString("password"),rs.getString("usertype"));
				map.put(rs.getString("username"),user);
			}
			closeConnection();
		}
		catch(Exception e){}
		return map;
	}

	public void insertUser(String username,String password,String usertype){
		try{
			init();

			String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,usertype) "+ "VALUES (?,?,?);";
			PreparedStatement pst =
			conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1,username);
			pst.setString(2,password);
			pst.setString(3,usertype);
			pst.execute();
			closeConnection();
		}
		catch(Exception e){}
	}

	//Payments
	public HashMap<Integer, ArrayList<OrderPayment>> selectOrder()
	{
		HashMap<Integer,ArrayList<OrderPayment>> orderPayments=new
		HashMap<Integer,ArrayList<OrderPayment>>();
		try{ 
			init();

			String selectOrderQuery ="select * from customerorders";
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			ResultSet rs = pst.executeQuery();
			ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
			while(rs.next())
			{
				if(!orderPayments.containsKey(rs.getInt("OrderId"))){
					ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
					orderPayments.put(rs.getInt("orderId"), arr);
				}
				ArrayList<OrderPayment> listOrderPayment =orderPayments.get(rs.getInt("OrderId"));
				OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("userName"),rs.getString("orderName"),rs.getDouble("orderPrice"),rs.getString("userAddress"),rs.getString("creditCardNo"));
				listOrderPayment.add(order);
			}
			closeConnection();
		}
		catch(Exception e){}
		return orderPayments;
	}

	public void insertOrder(int orderId,String userName,String orderName,Double orderPrice, String userAddress,String creditCardNo)
		{ 
			try
			{
				init();
				String insertIntoCustomerOrderQuery = "INSERT INTO customerOrders(OrderId,UserName,OrderName,orderPrice,userAddress,creditCardNo) " + "VALUES (?,?,?,?,?,?);";
				PreparedStatement pst =
				conn.prepareStatement(insertIntoCustomerOrderQuery);
				pst.setInt(1,orderId);
				pst.setString(2,userName);
				pst.setString(3,orderName);
				pst.setDouble(4,orderPrice);
				pst.setString(5,userAddress);
				pst.setString(6,creditCardNo);
				pst.execute();
				closeConnection();
			}
		catch(Exception e){}
	}

	public void deleteOrder(int orderId)
		{ 
			try
			{
				init();
				String deleteIntoCustomerOrderQuery = "DELETE FROM customerOrders " + "WHERE orderId = ?;";
				PreparedStatement pst =
				conn.prepareStatement(deleteIntoCustomerOrderQuery);
				pst.setInt(1,orderId);
				pst.execute();
				closeConnection();
			}
		catch(Exception e){}
	}

	public void updateOrder(int orderId,String orderName,Double orderPrice)
		{ 
			try
			{
				init();
				String updateIntoCustomerOrderQuery = "UPDATE customerOrders SET orderPrice = ? WHERE orderId = ?  AND orderName = ?;";
				PreparedStatement pst =
				conn.prepareStatement(updateIntoCustomerOrderQuery);
				pst.setDouble(1,orderPrice);
				pst.setInt(2,orderId);
				pst.setString(3,orderName);
				pst.execute();
				closeConnection();
			}
		catch(Exception e){}
	}
}
