package search.search;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import search.db.DbConnector;
import search.db.DbOperation;
import search.util.Log;

public class SearchFiles {
	
	public String index = "D:\\indextest";
	public String field = "contents";
	public String queries = null;
	public int repeat = 0;
	public boolean raw = false;
	public String queryString = null;
	public int hitsPerPage = 10;	
	
	public static char CharID;
	public static int IntID;
	
	public static String result;
	
	public String search(String search) throws IOException, ParseException, SQLException
	{
		
		this.queryString = search;
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		QueryParser parser = new QueryParser(field, analyzer);
		
		while (true) 
		{
			if (queries == null && queryString == null) 
			{
				Log.Error("queries && queryString can not be empty!");
		    }

			queryString = queryString.trim();
		      
		    Query query = parser.parse(queryString);
		      
		    System.out.println("Searching for: " + query.toString(field));
		            
		    if (repeat > 0) {                           // repeat & time as benchmark
		    	Date start = new Date();
		        
		    	for (int i = 0; i < repeat; i++) {
		          searcher.search(query, 100);
		        }
		        
		    	Date end = new Date();
		        
		    	System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
		      }

		      doPagingSearch(queryString, searcher, query, hitsPerPage, raw, queries == null && queryString == null);

		      if (queryString != null) {
		        break;
		      }
		    }
		    reader.close();
		    
		    return "";

	  }
	
	  @SuppressWarnings("unused")
	public static void doPagingSearch(String queryString, IndexSearcher searcher, Query query, int hitsPerPage, boolean raw, boolean interactive) throws IOException, SQLException 
	  {
	 
		  DbConnector.getconnection();
	    // Collect enough docs to show 5 pages
	    TopDocs results = searcher.search(query, 5 * hitsPerPage);
	    ScoreDoc[] hits = results.scoreDocs;
	    
	    int numTotalHits = results.totalHits;
	    System.out.println(numTotalHits + " total matching documents");

	    int start = 0;
	    int end = Math.min(numTotalHits, hitsPerPage);
	        
	    while (true) {
	      if (end > hits.length) {
	        System.out.println("Only results 1 - " + hits.length +" of " + numTotalHits + " total matching documents collected.");
	        System.out.println("Collect more (y/n) ?");
	        String line = queryString;
	        if (line.length() == 0 || line.charAt(0) == 'n') {
	          break;
	        }

	        hits = searcher.search(query, numTotalHits).scoreDocs;
	      }
	      
	      end = Math.min(hits.length, start + hitsPerPage);
	      
	      for (int i = start; i < end; i++) {
	        if (raw) {                              // output raw format
	          System.out.println("doc="+hits[i].doc+" score="+hits[i].score);
	          continue;
	        }

	        Document doc = searcher.doc(hits[i].doc);
	        
	        String path = doc.get("path");
	        
            path = path.trim();
	        
	        for(int j=0;j<path.length();j++)
	        {
	        	if(path.charAt(j)>=48 && path.charAt(j)<=57)
	        	{
	        		CharID=path.charAt(j);
	        	}
	        }
	     
	        String StringID = String.valueOf(CharID);
	        
	        IntID = Integer.parseInt(StringID);
	        
	        String sql = "select url from t_url where id="+IntID;
	        
	       String sql_url = DbOperation.select(sql,"url");
	       
	       String sql_title = DbOperation.select(sql, "title");
	        
	       result +="<div class=\"container\">"
       			+ "<div class=\"section\">"
       			+ "<a href="+sql_url+"><div class=\"title\">"+sql_title+"</div></a>"
       			+ "<a href="+sql_url+"class=\"a_links\"><div class=\"links\">"+sql_url+"</div></a>"
       			+ "</div>"
       			+ "</div>";
	        if (path != null) {
	          System.out.println((i+1) + ". " + path);
	          
	          String title = doc.get("title");
	          
	        if (title != null) {
	            System.out.println("   Title: " + doc.get("title"));
	          }
	        } 
	        else {
	          System.out.println((i+1) + ". " + "No path for this document");
	        }
	                  
	      }

	      if (!interactive || end == 0) {
	        break;
	      }
	    }
	  }
	}
