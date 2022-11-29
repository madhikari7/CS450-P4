<%@page import="java.sql.*" %>

<%      String tref=request.getParameter("tref");
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String url="jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        Connection conn=DriverManager.getConnection(url,"nhuynh26","ygruwory");
	PreparedStatement stmt = conn.prepareStatement("SELECT t.t_date,t.t_points,p.prod_name, tp.quantity, p.prod_points FROM Transactions t, Products p, Transactions_Products tp WHERE t.tref=? AND t.tref=tp.tref  AND p.prod_id = tp.prod_id");
	stmt.setObject(1,tref);
        ResultSet rs=stmt.executeQuery();
        String output="";
      
        while(rs.next()){
            
            output+=rs.getDate(1)+","+rs.getObject(2)+","+rs.getObject(3) + "," + rs.getObject(4)+","+rs.getObject(5) +"#";
            
            }
        conn.close();
        out.print(output);
    
%>