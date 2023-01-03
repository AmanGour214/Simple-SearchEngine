package com.Accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search") //search servlte for searching keyword
// extending the property of HttpServlet .
public class Search extends HttpServlet {
// making doget() method for request and response;

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        // request for keyword
        String keyword= request.getParameter("keyword");
        System.out.println(keyword);

         /* we have to read data from sql so that we are using dataBaseconnection class
           for connecting to the sql
     */
        try{
            //connecting databassclass
            Connection connection =DataBaseConnection.getConnection();

            //for history table
            PreparedStatement preparedStatement=connection.prepareStatement("insert into history values(?,?)");
            preparedStatement.setString(1 ,keyword);
            preparedStatement.setString(2,"http://localhost:8080/AccioSearchengine/Search?keyword="+keyword);
            preparedStatement.executeUpdate();//for history

            // we are going to write sql quary form java
            // result stored the data
            ResultSet resultSet= connection.createStatement().executeQuery("select pageTitle, pageLink, (length(lower(pageData))-length(replace(lower(pageData),'"+keyword+"',\"\")))/length('"+keyword+"') as countoccurance from pages order by countoccurance desc limit 30 ;");
            // we are just printing the Resultset .and it act like a linklisted;
          ArrayList<SearchResult>results=new ArrayList<SearchResult>();
            while(resultSet.next()){
              //  System.out.println(resultSet.getString("pageTitle"));
                //System.out.println(" "+resultSet.getString("pageLink")+"\n");
                //insted of printing the tile and links we have to store it in Arraylist
                //so we making object of searchResult class; and there we are setting or geting the links and title;
                SearchResult searchResult=new SearchResult();
                searchResult.setPageTitle(resultSet.getString("pageTitle"));
                searchResult.setPageLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }

            /* Now here making search.jsp file in the webapp
            * we are forwarding the Arraylist to the FrontEnd which contain SearchResult i.e pagetitle and pagelink
            * search.jsp will show all rhe information to the clint related to keyword*/
            request.setAttribute("results",results);//setting the arrtibute;

            //forwarding the request to the FrontEnd;
            request.getRequestDispatcher("/search.jsp").forward(request,response);

            // response is setting in htms contains ;
            response.setContentType("text/html");

            // this is for getting response for outpute
            PrintWriter out = response.getWriter();


        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        catch(ServletException servletException){
            servletException.printStackTrace();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

}
