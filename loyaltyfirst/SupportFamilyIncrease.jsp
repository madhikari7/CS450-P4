<%@page import="java.sql.*" %>

<%      
        String cid = request.getParameter("cid");
        String tref = request.getParameter("tref");
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String url="jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        Connection conn=DriverManager.getConnection(url,"madhikar","fartogny");
        PreparedStatement stmt = conn.prepareStatement("SELECT t.t_points, c.family_id, pa.percent_added FROM Customers c, Point_Accounts pa, Transactions t WHERE c.cid = ? AND t.tref = ? AND t.cid = c.cid AND t.point_acct_no = pa.point_acct_no");
        stmt.setObject(1, cid);
        stmt.setObject(2, tref);
        ResultSet rs=stmt.executeQuery();
        String output="";
        
        while(rs.next()){
            
            output+=rs.getObject(1)+","+rs.getObject(2)+","+rs.getObject(3)+"#";
            
        }
        conn.close();
        out.print(output);
    
%>