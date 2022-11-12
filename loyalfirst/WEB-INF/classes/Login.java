import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.*;

import java.io.PrintWriter;

@WebServlet("/login")
public class Login extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try{
            String user = req.getParameter("user");
            String pass = req.getParameter("pass");
            PrintWriter printWriter = resp.getWriter();
            printWriter.println("Hello " + user + " " + pass);
            printWriter.println("<br />");
            printWriter.println("This is server");
            printWriter.println("<br />");
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
            Connection connection = DriverManager.getConnection(url,"nhuynh26","ygruwory");

            PreparedStatement statement = connection.prepareStatement("Select cid FROM Login WHERE username = ? and passwd = ?");
            statement.setObject(1,user);
            statement.setObject(2,pass);
            ResultSet resultSet = statement.executeQuery();
            String res = "";
            while (resultSet.next()){
                res+=resultSet.getObject(1);

            }
            connection.close();
            if (res.length() == 0){
                printWriter.println("wrong");
            }
            else{
                printWriter.println("Welcome back "+res);
            }


        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }
}