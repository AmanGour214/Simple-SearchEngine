package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;

public class crawel {
    private HashSet<String> urllink;
    private int MAX_DEPTH=2;
    public Connection connection;

    public  crawel(){
        // setting the connection to the databaseconnection class to MYSQL;
        // to there getConnection() method;D->2
        connection=DataBaseConnection.getConnection();

        urllink=new HashSet<String>();
    }
    public void getPageTextAndLinks(String url,int depth){
        if(!urllink.contains(url)){

            if(urllink.add(url)){
                System.out.println(url);
            }
            try {
                Document document = Jsoup.connect(url).timeout(10000).get();
                String text=document.text().length()<501?document.text():document.text().substring(0,499);
                System.out.println(text);

                // insreting data into the mysql in pages table;
                PreparedStatement preparedStatement=connection.prepareStatement("Insert into pages values(?,?,?)");
               // instering pagetitle,pageLink,pageData into my sql from java in recursive manner;
                preparedStatement.setString(1,document.title());
                preparedStatement.setString(2,url);
                preparedStatement.setString(3,text);
                // update or executive all quear into sql;
                // preparedStatment in used to prepaired data into mysql;
                preparedStatement.executeUpdate();

                // Increasing depth;
                depth++;
                if(depth>MAX_DEPTH){
                    return;
                }
                Elements availableLinksOnPage = document.select("a[href]");
                for(Element currentlink : availableLinksOnPage){
                    getPageTextAndLinks(currentlink.attr("abs:href"),depth);
                }
            }
            catch (IOException ioException){
                ioException.printStackTrace();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {

        crawel objcrawel =new crawel();
        objcrawel.getPageTextAndLinks("https://www.javatpoint.com/",0);
    }
}