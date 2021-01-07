<HTML>

<Head> <Title> Social Magnet 2020 </Title> </Head>

<body>
    <h1>HELLO YOU MADE IT</h1>
    <%@ page import="java.util.*" %>
    <% 
        

        
        Enumeration<String> full = request.getHeaderNames();
        while (full.hasMoreElements()) {
            out.println(full.nextElement());
        }
        // out.println(fullName);

        // User user = new User(username, fullName);

        // HomeMenu homeMenu = new HomeMenu();
    %>

    <%-- <%
       

        

    %> --%>
</body>
</HTML>