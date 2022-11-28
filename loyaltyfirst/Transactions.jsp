<%@page import="java.sql.*" %>

<%      String cid=request.getParameter("cid");
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String url="jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        Connection conn=DriverManager.getConnection(url,"nhuynh26","ygruwory");
	  PreparedStatement stmt = conn.prepareStatement("Select tr.tref, tr.t_date, tr.t_points, tr.amount FROM Transactions tr WHERE tr.cid = ?");
	  stmt.setObject(1,cid);
        ResultSet rs=stmt.executeQuery();
        String output="";
      
        while(rs.next()){
            
            output+=rs.getObject(1)+","+rs.getDate(2)+","+rs.getObject(3) + "," + rs.getObject(4)+"#";
            
            }
        conn.close();
        out.print(output);
    
%>