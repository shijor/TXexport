package me.technoholic;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BookmarkCreator {

   public static void main(String[] args) {

      ArrayList<String> bookmarks = new ArrayList<String>();
      bookmarks.add("Category 1|Bookmark 1|https://www.bookmark1.com|https://www.bookmark1.com/icon.png");
      bookmarks.add("Category 2|Bookmark 2|https://www.bookmark2.com|https://www.bookmark2.com/icon.png");
      bookmarks.add("Category 1|Bookmark 3|https://www.bookmark3.com|https://www.bookmark3.com/icon.png");
      bookmarks.add("Category 2|Bookmark 4|https://www.bookmark4.com|https://www.bookmark4.com/icon.png");
      bookmarks.add("Category 3|Bookmark 5|https://www.bookmark5.com|https://www.bookmark5.com/icon.png");

   }
   
   
   public static void createBookmark(ArrayList<String> bookmarks) {

	      // Create a map of categories and their corresponding bookmarks
	      Map<String, ArrayList<String>> categoryBookmarks = new HashMap<String, ArrayList<String>>();
	      Iterator<String> iterator = bookmarks.iterator();
	      while (iterator.hasNext()) {
	         String bookmark = iterator.next();
	         String[] parts = bookmark.split("\\~");
	         String category = "";
	         String bookmarkName = "";
	         String url = "";
	         String favIconUrl = "";
	         //System.out.println(parts.length);
	         if (parts.length > 3) {
	        	 category = parts[0];
		         bookmarkName = parts[1];
		         url = parts[2];
	        	 favIconUrl = parts[3];
	         }
	       
	         if (!categoryBookmarks.containsKey(category)) {
	            categoryBookmarks.put(category, new ArrayList<String>());
	         }
	         categoryBookmarks.get(category).add("<DT><A HREF=\"" + url + "\" ICON=\"" + favIconUrl + "\">" + bookmarkName + "</A>");
	      }

	      // Generate the bookmark file content
	      StringBuilder sb = new StringBuilder();
	      sb.append("<!DOCTYPE NETSCAPE-Bookmark-file-1>\n");
	      sb.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n");
	      sb.append("<TITLE>Bookmarks</TITLE>\n");
	      sb.append("<H1>Bookmarks</H1>\n");
	      sb.append("<DL><p>\n");

	      Iterator<Map.Entry<String, ArrayList<String>>> entries = categoryBookmarks.entrySet().iterator();
	      while (entries.hasNext()) {
	         Map.Entry<String, ArrayList<String>> entry = entries.next();
	         String category = entry.getKey();
	         ArrayList<String> bookmarksList = entry.getValue();

	         sb.append("<DT><H3 FOLDED>" + category + "</H3>\n");
	         sb.append("<DL><p>\n");

	         for (String bookmark : bookmarksList) {
	            sb.append(bookmark + "\n");
	         }

	         sb.append("</DL><p>\n");
	      }

	      sb.append("</DL><p>\n");

	      // Write the content to a file
	      try {
	         FileWriter fileWriter = new FileWriter("bookmarks.html");
	         fileWriter.write(sb.toString());
	         fileWriter.close();
	         System.out.println("Bookmark file created successfully!");
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
   }
}
