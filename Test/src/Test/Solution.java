package Test;
public class Solution {

	  public int solution(int number) {
	    //TODO: Code stuff here
	    int _number = number;
	    int mod3 = 3;
	    int mod5 = 5;
	    int solution = 0;
	    
	    for(int i = 1; i < _number; i++ ) {
	      
	      if(i%mod3 == 0) {
	        
	    	  solution += i;	  
	      }
	      else if (i%mod5 == 0){
	       
	    	  solution += i;  
	      }
	      
	    }
	    return solution;
	  }
	}