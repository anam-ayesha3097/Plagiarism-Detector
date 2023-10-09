package KMP;

public class KMP {
	public static void main(String args[]) {
		String haystack = "this is a test file";
		String pattern = "test";
		
		KMP kmp = new KMP();
		int value = kmp.strStr(haystack, pattern);
		System.out.println("Value Returned "+value);
		
		
	}

	 public int strStr(String haystack, String needle) {
	      if(needle.isEmpty()) return 0;
	       
	      int[] prefix = generatePrefix(needle.toCharArray());
	      int i =0, j= 0;
	        while(i < haystack.length() && j < needle.length()){
	          if(haystack.charAt(i) == needle.charAt(j)){
	              i++;
	              j++;                
	          }else{
	            if(j == 0){
	              i++;
	            }else{
	              j = prefix[j-1];
	            }
	          }
	        }
	        return (j==prefix.length) ? (i-j):-1;
	    }
	 
	 public int[] generatePrefix(char[] arr ){
	      int [] prefix = new int[arr.length];
	      int index = 0;
	      for(int i = 1; i < prefix.length;){
	        // match
	        if(arr[i] == arr[index]){ 
	          prefix[i] = index+1; 
	          index++;
	          i++;
	        }else{
	        // no match
	          if(index == 0){
	            prefix[i] = 0;
	            i++;
	          }else{
	            index = prefix[index-1];  
	          }
	        }
	        
	      }
	      
	      System.out.println("Prefix");
	      for(int l = 0; l < prefix.length; l++)
	    	  System.out.println("Prefix "+arr[l]+" is "+prefix[l]);

	      return prefix;
	    }
}
