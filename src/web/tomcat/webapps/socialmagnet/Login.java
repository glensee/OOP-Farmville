package web.tomcat.webapps.socialmagnet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.JSONObject;

import entity.users.*;

@WebServlet("/login")
public class Login extends HttpServlet {

   public Login() {
      super();
   }

   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException {
 
      // Set the response MIME type of the response message
      response.setContentType("text/html");
      // Allocate a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      String username = (request.getParameter("username"));

      String password = (request.getParameter("password"));

      UserDAO userDAO = new UserDAO();
      
      if (userDAO.authenticate(username, password)) {
         String site = new String("http://localhost:8080/socialmagnet/home.jsp");
         User user = userDAO.getUserbyUsername(username);
         String fullName = user.getFullname();

         JSONObject json = new JSONObject();
         try {
            json.put("username", username);
            json.put("fullName", fullName);
         } catch (JSONException e) {
            e.printStackTrace();
         }

         request.setAttribute("jsonString", json.toString());
         RequestDispatcher dispatcher = request.getRequestDispatcher(site);
         dispatcher.forward(request, response);
         
      } else {
         String site = new String("http://localhost:8080/socialmagnet/index.html");
         out.println("<HTML> FAILED </HTML>") ;
         response.setHeader("Location", site); 
      }

      

   }
}