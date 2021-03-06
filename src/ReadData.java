// package TMF_OPC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class ReadData
{
    public static void readData() throws IOException 
	{
        // --- some statistics, start from index "1"
    	Data.userRatingSumTrain = new float[Data.n+1];
    	//Data.itemRatingSumTrain = new float[Data.m+1];
        Data.userRatingNumTrain = new int[Data.n+1];
  
        Data.ave_r_u = new float[Data.n + 1];
        Data.I_u = new HashSet[Data.n + 1];
        for (int u = 1; u <= Data.n; ++u) {
        	Data.I_u[u] = new HashSet<Integer>();
        }
        Data.I = new HashSet<Integer>();
        for (int i = 1; i <= Data.m; ++i)  {
        	Data.I.add(i);
        }
        		

    	
    	    	
    	// --- number of test records
    	Data.num_test = 0;
    	BufferedReader brTest = new BufferedReader(new FileReader(Data.fnTestData));
    	String line = null;
    	while ((line = brTest.readLine())!=null)
    	{
    		Data.num_test += 1;
    	}
    	System.out.println("num_test: " + Data.num_test);
    	// ----------------------------------------------------
    	
    	// ----------------------------------------------------
		// --- Locate memory for the data structure    	
        // --- train data
  
    	Data.r = new float [Data.n + 1][Data.m + 1];
        
        // --- test data
    	Data.indexUserTest = new int[Data.num_test];
    	Data.indexItemTest = new int[Data.num_test];
    	Data.ratingTest = new float[Data.num_test];
    	// ----------------------------------------------------

        
    	// ----------------------------------------------------        
        int id_case=0;
    	double ratingSum=0;
    //	int gradedNum[] = new int[Data.num_rating_types + 1];

    	// ----------------------------------------------------
    	// Training data: (userID,itemID,rating)
    	BufferedReader brTrain = new BufferedReader(new FileReader(Data.fnTrainData));    	
    	line = null;
    	while ((line = brTrain.readLine())!=null)
    	{	
    		String[] terms = line.split("\\s+|,|;");
    		int userID = Integer.parseInt(terms[0]);
    		int itemID = Integer.parseInt(terms[1]);
    		float rating = Float.parseFloat(terms[2]);

    		Data.I_u[userID].add(itemID);
    		
    		Data.r[userID][itemID] = rating;
    		id_case+=1;
    		    		
    		// ---
    		Data.userRatingSumTrain[userID] += rating;
    		Data.userRatingNumTrain[userID] += 1; 
    
    		ratingSum+=rating;
    		
    
    	}
    	
    	brTrain.close();
    	System.out.println("Finished reading the target training data");
    	float sum = 0;
    	int num = 0;
    	for (int u = 1; u <= Data.n; u++) {
    		Data.ave_r_u[u] = Data.userRatingSumTrain[u] / Data.userRatingNumTrain[u]; 
			 sum += Data.userRatingSumTrain[u];
			 num +=	Data.userRatingNumTrain[u]; 
		}
    
    	
    	// ----------------------------------------------------
    	// --- normalization    	
    	// ----------------------------------------------------
    	// Test data: (userID,itemID,rating)   	
    	id_case = 0; // initialize it to zero
    	brTest = new BufferedReader(new FileReader(Data.fnTestData));
    	line = null;
    	while ((line = brTest.readLine())!=null)
    	{	
    		String[] terms = line.split("\\s+|,|;");
    		int userID = Integer.parseInt(terms[0]);    		
    		int itemID = Integer.parseInt(terms[1]);
    		float rating = Float.parseFloat(terms[2]);
    		Data.indexUserTest[id_case] = userID;
    		Data.indexItemTest[id_case] = itemID;
    		Data.ratingTest[id_case] = rating;
    		id_case+=1;
    	}
    	brTest.close();
    	System.out.println("Finished reading the target test data");
    	// ----------------------------------------------------


    }    
}
