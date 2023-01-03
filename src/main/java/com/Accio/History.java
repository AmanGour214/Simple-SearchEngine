package com.Accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/History")
public class History extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
       try{
           Connection connection=DataBaseConnection.getConnection();
           ResultSet resultSet=connection.createStatement().executeQuery("select * from History");


           // as we done periously stored the links and pagetitle in arraylist;
           // creating arraylist which store historyresult i.e link and keyword ;
           ArrayList<Historyresult> results=new ArrayList<Historyresult>();
           while(resultSet.next()){
               // after that we have creat historyresult class
               // creating the object of Historyresult class;

               Historyresult historyresult=new Historyresult();
               historyresult.setKeyword(resultSet.getString("keyword"));
               historyresult.setLink(resultSet.getString("searchLink"));
               results.add(historyresult);

           }
           // forwarding the request to the history.jsp ; as we did priously ;in a search class;
           request.setAttribute("results",results);
           request.getRequestDispatcher("/history.jsp").forward(request,response);
          }catch (SQLException sqlException){
           sqlException.printStackTrace();;
       }catch (ServletException servletException){
           servletException.printStackTrace();
       }catch (IOException ioException){
           ioException.printStackTrace();
       }

    }
}
