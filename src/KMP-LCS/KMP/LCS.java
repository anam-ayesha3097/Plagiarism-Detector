package KMP;

public class LCS {
	public static void main(String args[]) {
		LCS lcs = new LCS();
		String text1 = "forc electromagnet field exert electr charg particl call electromagnet forc one four fundament forc fundament forc strong nuclear forc hold atom nuclei togeth weak nuclear forc caus certain form radioact decay gravit forc forc ultim deriv fundament forc howev turn electromagnet forc one respons practic phenomena one encount daili life except graviti electromagnet wikipedia aug retriev aug httpenwikipediaorgwikielectromagnet";
		String text2= "electromagnet defin forc electromagnet field exert electr charg particl one four fundament forc electromagnet three fundament forc strong weak nuclear forc gravit forc foundat everi fundament forc electromagnet dictat almost everi phenomenon wit daili" ;
		int lcs_val  = lcs.longestCommonSubsequenceChar(text1, text2) ;
		System.out.println("LCS Value "+lcs_val);
		
		
	}
	
	public int longestCommonSubsequenceChar(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(text1.charAt(m - i) == text2.charAt(n - j)){
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                }else{
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        System.out.println("DP");
        for(int l = 1; l < m; l++) {
        	for(int k = 1; k< n; k++) {
        		System.out.print(dp[l][k]+"\t");
        	}
        	System.out.println();
        }
        return dp[m][n];
    }
}
