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
import search.db.DbOperation;

public class SearchFiles {
	
	String index = "D:\\indextest";
	String field = "contents";
	String queries = null;
	int repeat = 0;
	boolean raw = false;
	String queryString ;
	int hitsPerPage = 10;	
	
	public static char CharID;
	public static int IntID;
	
	public  String result="";
	
	public String search(String search) throws IOException, ParseException, SQLException 
	{
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();
		
		this.queryString = search;

		/*BufferedReader in = null;
		  if (queries != null) {
			  in = Files.newBufferedReader(Paths.get(queries), StandardCharsets.UTF_8);
		    } else {
		      in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		    }*/
		QueryParser parser = new QueryParser(field, analyzer);
		
		while (true) 
		{
			if (queries == null && queryString == null) 
			{
				System.out.println("queries && queryString can not be empty!");
				//Log.Error("queries && queryString can not be empty!");
		    }

		      /*String line = queryString != null ? queryString : in.readLine();

		      if (line == null || line.length() == -1) {
		        break;
		      }

		      
		      line = line.trim();
		      if (line.length() == 0) {
		        break;
		      }*/
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

		    return result;
	  }
	
	  @SuppressWarnings("unused")
	public void doPagingSearch(String queryString, IndexSearcher searcher, Query query, int hitsPerPage, boolean raw, boolean interactive) throws IOException, SQLException 
	  {
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
	        
	        String sql_1 = "select title from t_url where id="+IntID;
	        String sql_2 = "select url from t_url where id="+IntID;
	        
	        String Title = DbOperation.select(sql_1, "title");
	        String Url = DbOperation.select(sql_2, "url");
	        
	        result +="<div class=\"container\">"
        			+ "<div class=\"section\">"
        			+ "<a href="+Url+"><div class=\"title\">"+Title+"</div></a>"
        			+ "<a href="+Url+"class=\"a_links\"><div class=\"links\">"+Url+"</div></a>"
        			+ "</div>"
        			+ "</div>";
	System.out.println(result);
	        
	        if (path != null) {
	          System.out.println((i+1) + ". " + path);
	          String title = doc.get("title");
	          if (title != null) {
	            System.out.println("   Title: " + doc.get("title"));
	          }
	        } else {
	          System.out.println((i+1) + ". " + "No path for this document");
	        }
	                  
	      }

	      if (!interactive || end == 0) {
	        break;
	      }

	      /*if (numTotalHits >= end) {
	        boolean quit = false;
	        while (true) {
	          System.out.print("Press ");
	          if (start - hitsPerPage >= 0) {
	            System.out.print("(p)revious page, ");  
	          }
	          if (start + hitsPerPage < numTotalHits) {
	            System.out.print("(n)ext page, ");
	          }
	          System.out.println("(q)uit or enter number to jump to a page.");
	          
	          String line = in.readLine();
	          if (line.length() == 0 || line.charAt(0)=='q') {
	            quit = true;
	            break;
	          }
	          if (line.charAt(0) == 'p') {
	            start = Math.max(0, start - hitsPerPage);
	            break;
	          } else if (line.charAt(0) == 'n') {
	            if (start + hitsPerPage < numTotalHits) {
	              start+=hitsPerPage;
	            }
	            break;
	          } else {
	            int page = Integer.parseInt(line);
	            if ((page - 1) * hitsPerPage < numTotalHits) {
	              start = (page - 1) * hitsPerPage;
	              break;
	            } else {
	              System.out.println("No such page");
	            }
	          }
	        }
	        if (quit) break;
	        end = Math.min(numTotalHits, start + hitsPerPage);
	      }*/
	    }
	  }
	}

