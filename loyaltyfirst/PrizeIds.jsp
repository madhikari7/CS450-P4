<%@page import="java.sql.*" %>

<%      String cid=request.getParameter("cid");
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String url="jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        Connection conn=DriverManager.getConnection(url,"nhuynh26","ygruwory");
	PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT prize.prize_id FROM Customers c,Redemption_History rh,Prizes prize WHERE prize.prize_id = rh.prize_id AND c.cid = rh.cid AND c.cid = ?");
	stmt.setObject(1,cid);
        ResultSet rs=stmt.executeQuery();
        String output="";
      
        while(rs.next()){
            
            output+=rs.getObject(1)+"#";
            
            }
        conn.close();
        out.print(output);
    
%>