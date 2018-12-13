package Test;

public class Solution2 {
	
	 public static String mergeStrings( String a,String b ) {
		 
		 String sms = "";
		 char [] strA = a.toCharArray();
		 char [] strB = b.toCharArray();
		 char [] strC = new char[a.length() + b.length()];
		 
		 int counterA = 0;
		 int counterB = 1;
		 
		 if(a.length() >= 1 && b.length() >= 1 && b.length() <= 25000 && a.length() <= 25000) {
			 
			 
			 if(a.length() < b.length()) {
				 
				 for(int i = 0; i < a.length(); i++) {
						strC[counterA] = strA[i];
						strC[counterB] = strB[i];
						counterA = counterA + 2;
						counterB = counterB + 2;
					 }
				 for(int i = a.length(); i < b.length(); i++) {
					 strC[counterA] = strB[i];
					 counterA++;
				 }
			 }
			 else if(a.length() > b.length()){
				 
				 for(int i = 0; i < b.length(); i++) {
						strC[counterA] = strA[i];
						strC[counterB] = strB[i];
						counterA = counterA + 2;
						counterB = counterB + 2;
				 }
				 for(int i = b.length(); i < a.length(); i++) {
					 strC[counterA] = strA[i];
					 counterA++;
				 }
			 }
			 else {
				 for(int i = 0; i < a.length(); i++) {
					strC[counterA] = strA[i];
					strC[counterB] = strB[i];
					counterA = counterA + 2;
					counterB = counterB + 2;
				 }
			 }
		 } 
		 sms = String.valueOf(strC);
		
		 return sms;
		    
		    
	}

}
