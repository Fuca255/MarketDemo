<%@ page import="com.example.mala_prodavnica.beans.UserBean" %>
<%@ page import="com.example.mala_prodavnica.services.ReceiptService" %>
<%@ page import="com.example.mala_prodavnica.model.Receipt" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.mala_prodavnica.model.ReceiptItem" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.rmi.Naming" %>
<%@ page import="com.example.mala_prodavnica.rmi.ICalculation" %>
<%@ page import="java.rmi.NotBoundException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.mala_prodavnica.beans.ReceiptBean" %>
<%@ page import="com.example.mala_prodavnica.beans.ReceiptItemBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UserBean u = (UserBean) session.getAttribute("user");
    if(u==null)
    {
        response.sendRedirect("login.jsp");
    }
    else
    {
        ReceiptService receiptService = new ReceiptService();
        List<ReceiptBean> receipts = receiptService.getAllReceiptsByUser(u.getId());
        String message = "";
        if (receipts.isEmpty())
            message = "There are no purchases";
%>
<!DOCTYPE html>
<html>
<head>
    <title>Receipts</title>
    <link rel="stylesheet" href="styles/receipts.css">
</head>
<body>
<div id="menu">
    <ul>
        <li><a href="uputsvo.pdf" target="_blank">Help(?)</a></li>
        <li><a href="index.jsp">Home</a></li>
        <li><a href="receipts.jsp">Receipts</a></li>
        <li><a href="chat.jsp">Chat</a></li>
        <li><a href="logout">Logout</a></li>
    </ul>
</div>
<div class="container">
    <h1>Receipts</h1>
    <%
        if(receipts.isEmpty())
        {
    %>
    <h2 class="message"><%=message%></h2>
    <%  }
    else
    {%>
    <div class="receipts-container">
        <table class="receipts-table">
            <tr>
                <th>No.</th>
                <th>Items</th>
                <th>Purchase Date</th>
                <th>Total</th>
                <th>Action</th>
            </tr>
            <%
                for (ReceiptBean r : receipts)
                {%>
            <tr>
                <form action="removeReceipt" method="post">
                    <td><%=r.getId()%></td>
                    <td class="receipt-items">
                        <%
                            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
                            String dt = ft.format(r.getPurchaseDate());
                            List<ReceiptItemBean> items = r.getItems();
                            for (ReceiptItemBean item: items)
                            {
                        %>
                        <p><%=item.getName()%> x <%=item.getQuantity()%></p>
                        <%}
                        %>
                    </td>
                    <td><%=dt%></td>
                    <td><%=r.getTotal()%> din</td>
                    <input type="hidden" name="id" value="<%=r.getId()%>">
                    <td><button type="submit" class="remove-button">X</button></td>
                </form>
            </tr>
            <%}
            %>
        </table>
    </div>
    <%
        int total = 0;
        ICalculation calc = null;
        try {
//            ArrayList<Integer> recs= new ArrayList<>();
//            for (ReceiptBean r : receipts) {
//                recs.add(r.getTotal());
//            }
            calc = (ICalculation) Naming.lookup("rmi://localhost:1100/WebSevice");
            total = calc.calculateTotalSumOfReceiptTotals(receipts);
        } catch (NotBoundException e) {
            System.out.println("greska rmi konektovanje");
        }
    %>
    <div class="total-spent">
        <h2>Total Spent: <%=total%> din</h2>
    </div>
    <%}
    %>
</div>
</body>
</html>
<%}%>
