
public class DP {
	
	
	/**
	 * Given an array, find out the num of longest increasing sequence
	 * Idea: for every index get the lis whose last index is the position we are stopping at. Then get the max 
	 * out from each index.
	 * **/
	public int LIS(int[] A){
		int max = 1;
		int[] dp = new int[A.length];
		for (int i=0;i<A.length;i++) dp[i] = 1;
		for (int i=1;i<A.length;i++){
			for (int j=0;j<i;j++){
				if (A[j]<A[i]) {
					dp[i] = Math.max(dp[i], 1+dp[j]);
					max = Math.max(max, dp[i]);
				}
			}
		}
		return max;
	}
	
	/**
	 * Longest Common Subsequence
	 * Given two arrays, find out the longest common subsequence
	 * 
	 * Idea: Optimal substructure is fulfilled
	 * 
	 * **/
   public int LCS(int[] A, int[] B){
	   int m = A.length, n=B.length;
	   int[][] dp = new int[m+1][n+1];
	  // dp[0][0] = 0;
	   for (int i=1;i<=m;i++){
		   for (int j=1;j<=n;j++){
			   if (A[i-1]==B[j-1]){
				   dp[i][j] = Math.max(dp[i-1][j-1] + 1,dp[i][j]);
			   }
			   else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
		   }
	   }
	   return dp[m][n];
   }
   
   public int eggDrop(int n, int k){
	   // k floors , n eggs
	   // find the connection from drop 1 egg at 1 floor, 1 egg 2 floor, 1 egg 3 floor
	   int[][] dp = new int[n+1][k+1];
	   // dp[1][k] = k-1 dp[n][1] = 
	return k;
   }
   
   public int coinChange(int amount, int[] coins){
	   // find how many ways to reach amount using given coins
	   // for each coin, we find the least num of coins to get the amount j without using this coin
	   // and compare it to the least num of coins when using at least one this coin to get j
	   int[] dp = new int[amount+1];
	   for (int coin : coins){
		   for (int j= coin;j<=amount;j++){
			   dp[j] += dp[j-coin];
		   }
	   }
	   return dp[amount];
   }
   
   /**
    * Given a sequence, find the length of the longest palindromic subsequence in it.
    *  For example, if the given sequence is “BBABCBCAB”, then the output should be 7 as “BABCBAB” 
    *  is the longest palindromic subseuqnce in it. “BBBBB” and “BBCBB” are also palindromic subsequences 
    *  of the given sequence, but not the longest ones.
    * **/
   
   public int LPS(String s){
	   // dp[i][j] is the length of lps from char i to char j.
	   // the way to compute is, if s(i)==s(j),then dp[i][j] = 2 + dp[i+1][j-1]
	   // if s(i) != s(j), then dp[i][j] = max(dp[i+1][j],dp[i][j-1])
	   // since the lps of shorter substring is needed in prior, we should use the same way as we did
	   // in longest chain multiplication problem
	   int n = s.length();
	   int[][] dp = new int[n][n];
	   for (int i=0;i<n;i++) dp[i][i] = 1;
	   for (int k = 1;k<n;k++){
		   for (int left = 0;left+k<n;left++){
			   int right = left+k;
			   if (s.charAt(left) == s.charAt(right) && k==1){
				   dp[left][right] = 2;
			   }
			   else if (s.charAt(left) == s.charAt(right)){
				   dp[left][right] = 2 + dp[left+1][right-1];
			   }
			   else{
				   dp[left][right] = Math.max(dp[left+1][right], dp[left][right-1]);
			   }
		   }
	   }
	   return dp[0][n-1];
   }
}
