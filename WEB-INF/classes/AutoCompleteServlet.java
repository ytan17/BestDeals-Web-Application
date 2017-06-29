import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AutoCompleteServlet")

public class AutoCompleteServlet extends HttpServlet {
	private ServletContext context;
	private SaxParserDataStore productData = new SaxParserDataStore();
    private HashMap<String, SmartPhone> phonesData = productData.getPhones();
    private HashMap<String, Laptop> laptopsData = productData.getLaptops();
    private HashMap<String, Tv> tvsData = productData.getTvs();
    private HashMap<String, Tablet> tabletsData = productData.getTablets();
    private HashMap<String, Accessory> accessoriesData = productData.getAccessories();

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");
        String targetId = request.getParameter("searchId");
        StringBuffer sb = new StringBuffer();


        boolean namesAdded = false;
        if (action.equals("complete")) {

            if (targetId != null) {
                targetId = targetId.trim().toLowerCase();
            } 

            // check if user sent empty string
            if (!targetId.equals("")) {
            	
            	for(Entry<String, SmartPhone> phone: phonesData.entrySet()){
            		if(phone.getValue().getName().toLowerCase().startsWith(targetId)){
            			sb.append("<product>");
            			sb.append("<id>" + phone.getValue().getId() + "</id>");
            			sb.append("<productName>" + phone.getValue().getName() + "</productName>");
            			sb.append("</product>");
            			namesAdded = true;
            		}
            	}	
            	
            	for(Entry<String, Laptop> laptop: laptopsData.entrySet()){
            		if(laptop.getValue().getName().toLowerCase().startsWith(targetId)){
            			sb.append("<product>");
            			sb.append("<id>" + laptop.getValue().getId() + "</id>");
            			sb.append("<productName>" + laptop.getValue().getName() + "</productName>");
            			sb.append("</product>");
            			namesAdded = true;
            		}
            	}	
            	
            	for(Entry<String, Tv> tv: tvsData.entrySet()){
            		if(tv.getValue().getName().toLowerCase().startsWith(targetId)){
            			sb.append("<product>");
            			sb.append("<id>" + tv.getValue().getId() + "</id>");
            			sb.append("<productName>" + tv.getValue().getName() + "</productName>");
            			sb.append("</product>");
            			namesAdded = true;
            		}
            	}	
            	
            	for(Entry<String, Tablet> tablet: tabletsData.entrySet()){
            		if(tablet.getValue().getName().toLowerCase().startsWith(targetId)){
            			sb.append("<product>");
            			sb.append("<id>" + tablet.getValue().getId() + "</id>");
            			sb.append("<productName>" + tablet.getValue().getName() + "</productName>");
            			sb.append("</product>");
            			namesAdded = true;
            		}
            	}
            	
            	for(Entry<String, Accessory> accessory: accessoriesData.entrySet()){
            		if(accessory.getValue().getName().toLowerCase().startsWith(targetId)){
            			sb.append("<product>");
            			sb.append("<id>" + accessory.getValue().getId() + "</id>");
            			sb.append("<productName>" + accessory.getValue().getName() + "</productName>");
            			sb.append("</product>");
            			namesAdded = true;
            		}
            	}
            	
            }

            if (namesAdded) {
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write("<products>" + sb.toString() + "</products>");
            } else {
                //nothing to show
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }

        if (action.equals("lookup")) {
            // put the target composer in the request scope to display 
            if (targetId != null) {
            	for(String s: phonesData.keySet()) {
            		System.out.println(s);
            	}
            	if(phonesData.keySet().contains(targetId)){
            		context.getRequestDispatcher("/SmartPhoneList").forward(request, response);
            	}
            	else if(laptopsData.keySet().contains(targetId)){
            		context.getRequestDispatcher("/LaptopsList").forward(request, response);
            	}
            	else if(tvsData.keySet().contains(targetId)){
            		context.getRequestDispatcher("/TvsList").forward(request, response);
            	}
            	else if(tabletsData.keySet().contains(targetId)){
            		context.getRequestDispatcher("/TabletList").forward(request, response);
            	}
            }
        }
    }
}

