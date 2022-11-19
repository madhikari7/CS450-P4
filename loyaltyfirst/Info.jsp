<%@page import="java.sql.*" %>

<%      String cid=request.getParameter("cid");
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String url="jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        Connection conn=DriverManager.getConnection(url,"nhuynh26","ygruwory");
	PreparedStatement stmt = conn.prepareStatement("Select pa.num_of_points,c.cname from Customers c, Point_Accounts pa WHERE c.cid = ? AND c.cid = pa.cid");
	stmt.setObject(1,cid);
        ResultSet rs=stmt.executeQuery();
        String output="";
      
        while(rs.next()){
            
            output+=rs.getObject(1)+","+rs.getObject(2);
            
            }
        conn.close();
        out.print(output);
    
%>