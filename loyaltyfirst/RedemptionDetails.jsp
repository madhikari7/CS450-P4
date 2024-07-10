<%@page import="java.sql.*" %>

<%      
	String prizeId = request.getParameter("prizeid");
	String cid=request.getParameter("cid");
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String url="jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        Connection conn=DriverManager.getConnection(url,"madhikar","fartogny");
	PreparedStatement stmt = conn.prepareStatement("SELECT prize.p_description,prize.points_needed,rh.r_date,ec.center_name FROM Customers c,Redemption_History rh,Prizes prize,ExchgCenters ec WHERE ec.center_id = rh.center_id AND prize.prize_id = rh.prize_id AND prize.prize_id = ? AND c.cid = rh.cid AND c.cid = ? ");
	stmt.setObject(1,prizeId);
	stmt.setObject(2,cid);
        ResultSet rs=stmt.executeQuery();
        String output="";
      
        while(rs.next()){
            
            output+=rs.getObject(1)+","+rs.getObject(2)+","+rs.getDate(3)+","+rs.getObject(4)+"#";
            
            }
        conn.close();
        out.print(output);
    
%>