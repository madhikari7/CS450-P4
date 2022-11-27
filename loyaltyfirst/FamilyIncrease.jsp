<%@page import="java.sql.*" %>
<%@page import="java.util.ArrayList" %>

<%      
        String fid = request.getParameter("fid");
        String cid = request.getParameter("cid");
        String npoints = request.getParameter("npoints");
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String url="jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        Connection conn=DriverManager.getConnection(url,"nhuynh26","ygruwory");      
        PreparedStatement stmt = conn.prepareStatement("SELECT pa.cid, pa.num_of_points FROM Point_Accounts pa WHERE pa.family_id = ? AND pa.cid != ?");

        stmt.setObject(1, fid);
        stmt.setObject(2, cid);
        ResultSet rs=stmt.executeQuery();
        
        ArrayList<String> updateList = new ArrayList<String>();
        while(rs.next()) {
            updateList.add(rs.getString(1));
            updateList.add(String.valueOf(rs.getInt(2) + Integer.parseInt(npoints)));
        }
        
        for(int i = 0; i < updateList.size(); i += 2) {
            stmt = conn.prepareStatement("UPDATE Point_Accounts SET num_of_points = ? WHERE cid = ?");
            stmt.setObject(1, updateList.get(i+1));
            stmt.setObject(2, updateList.get(i));
            stmt.executeUpdate();
        }
        
        conn.close();
        out.print(npoints + " Points added to the members of Family ID " + fid);
    
%>