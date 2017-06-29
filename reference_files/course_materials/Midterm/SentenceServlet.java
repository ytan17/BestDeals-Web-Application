/*
 * SentenceServlet.java
 *
 */
 

import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

public class SentenceServlet extends HttpServlet {
   
    // protected Map sentences = new HashMap();
    // /** 
    //  * Initializes the servlet with some sentences
    // */  
    // public void init() {
    //             sentences.put("test", "TEST");
    // }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        String sentenceid = request.getParameter("sentenceid");
        HashMap<Character,Integer> map = new HashMap<Character,Integer>();
        if(sentenceid != null && sentenceid.length() != 0) {
            sentenceid = sentenceid.trim();
            for (int i = 0; i < sentenceid.length(); i++){
                char c = sentenceid.charAt(i);
                if(c != " "){
                    Integer val = map.get(new Character(C));
                    if(val != null){
                        map.put(c, new Integer(val + 1));
                    }
                    else{
                        map.put(c,1);
                    }
                }
            }
            showPage(response,HashMap<Character,Integer> map);
            
        }
    } 
    
    /**
     * Actually shows the <code>HTML</code> result page
     */
    protected void showPage(HttpServletResponse response, HashMap<Character,Integer> map)
    throws ServletException, java.io.IOException {
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>sentence Servlet Result</title>");  
        out.println("</head>");
        out.println("<body>");
        out.println("<table>")
        out.println("<tr>")
        out.println("<th>Character</th>");
        out.println("<th>frequency</th>");
        out.println("</tr>")
        Iterator iterate = map.entrySet().iterator();
        while(iterate.hasnext()){
            Map.Entry pair = (Map.Entry) iterate.next();
            out.println("<tr>")
            outprintln("<th>" + pair.getKey() + "</th>");
            outprintln("<th>" + pair.getValue() + "</th>");
            out.println("</tr>")
            iterate.remove();
        }
        out.println("</table>")
        out.println("</body>");
        out.println("</html>");
        out.close();
 
    }
    
    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    }
}
