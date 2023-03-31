package me.technoholic;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TabExtendExport {

   public static void main(String[] args) {

      JSONParser parser = new JSONParser();
      BookmarkCreator bmcreateor = new BookmarkCreator();
	  ArrayList<String> bookmarks = new ArrayList<String>();
      try {

         // Read the JSON file into a JSON array
          // Get the current folder path
          String currentFolderPath = System.getProperty("user.dir");

          // Find the first JSON file in the current folder
          File[] files = new File(currentFolderPath).listFiles();
          File jsonFile = null;
          for (File file : files) {
             if (file.isFile() && file.getName().endsWith(".json")) {
                jsonFile = file;
                break;
             }
          }
          
          Object obj = parser.parse(new FileReader(jsonFile));
         //Object obj = parser.parse(new FileReader("file.json"));
         JSONArray jsonArray = (JSONArray) obj;
        
		  System.out.println("Exporting bookmarks..");
         // Loop through each element in the JSON array
         for(int i = 0; i < jsonArray.size(); i++) {
        	 System.out.print(".>");
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            // Extract the value for the "title" field
            String category = (String) jsonObject.get("title");
            //System.out.println("Title: " + category);

            // Extract the values for the "title" and "url" fields in "tabs"
            JSONArray tabsArray = (JSONArray) jsonObject.get("tabs");
            for(int j = 0; j < tabsArray.size(); j++) {
               JSONObject tabObject = (JSONObject) tabsArray.get(j);
               String tabTitle = (String) tabObject.get("title");
               String tabUrl = (String) tabObject.get("url");
               String tabfavIcon = (String) tabObject.get("favIcon");
             //  System.out.println("Tab Title: " + tabTitle);
             //  System.out.println("Tab URL: " + tabUrl);
               if(tabTitle!=null && tabTitle!="") {
            	   bookmarks.add(category+"~"+tabTitle+"~"+tabUrl+"~"+tabfavIcon);
               }

               // Extract the values for the "title" and "url" fields in "stackedItems"
               if(tabObject.containsKey("stackedItems")) {
                  JSONArray stackedItemsArray = (JSONArray) tabObject.get("stackedItems");
                  for(int k = 0; k < stackedItemsArray.size(); k++) {
                     JSONObject stackedItemObject = (JSONObject) stackedItemsArray.get(k);
                     String stackedItemTitle = (String) stackedItemObject.get("title");
                     String stackedItemUrl = (String) stackedItemObject.get("url");
                     String stackedfavIcon = (String) tabObject.get("favIcon");
                     //System.out.println("Stacked Item Title: " + stackedItemTitle);
                   //  System.out.println("Stacked Item URL: " + stackedItemUrl);
                     if(stackedItemTitle!=null && stackedItemTitle!="") {
                    	 bookmarks.add(category+"~"+stackedItemTitle+"~"+stackedItemUrl+"~"+stackedfavIcon);
                     }
                  }
               }
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
      bmcreateor.createBookmark(bookmarks);
   }
}
