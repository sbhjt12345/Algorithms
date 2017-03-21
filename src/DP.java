import java.util.HashMap;
import java.util.Map;

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

	public boolean canPartition(int[] nums) {
		/**
		 * Just some knapsack problem
		 * **/
		int sum = 0;
		for (int num : nums) sum += num;
		if (sum%2!=0) return false;
		sum /=2;
		boolean[] dp = new boolean[sum+1];
		dp[0] = true;
		for (int num : nums){
			for (int j= sum;j>=num;j--){
				dp[j] = dp[j] || dp[j-num];
			}
		}
		return dp[sum];
	}


	//ugly number is the a number whose prime factor is only 2,3 and 5
	//1,2,3,4,5,6,8,9,10,...
	// given n, find nth ugly number
	public int uglyNumber(int n){
		/**
		 * idea:
		 * ugly number is built on ugly number
		 * For 2 : 1*2,2*2,3*2,4*2,5*2,...
		 * For 3 : 1*3,2*3,3*3,4*3,5*3,....
		 * For 5 : 1*5,2*5,3*5,4*5,5*5,....
		 * So it is just like we have these three arrays,where
		 * Array(factor) = [1*n matrix of ugly numbers] * factor
		 * The only difficulty here is getting the matrix, since we can get new from old, we should apply this idea
		 * **/
		int[] index = new int[3];
		int[] dp = new int[n];
		dp[0] =1;
		for (int i=1;i<n;i++){
			dp[i] = Math.min(2*dp[index[0]], Math.min(3*dp[index[1]], 5*dp[index[2]]));
			if (dp[i]==2*dp[index[0]]) index[0]++;
			if (dp[i]==3*dp[index[1]]) index[1]++;
			if (dp[i]==5*dp[index[2]]) index[2]++;
		}
		return dp[n-1];  
	}

	public String longestPalindrome(String s) {
		if (s.length()<=1) return s;
		int n = s.length();
		int max = 0;
		int[] pos = new int[2];
		boolean[][] dp = new boolean[n][n];
		for (int i=0;i<n;i++) dp[i][i] = true;
		for (int k=1;k<n;k++){
			for (int left=0;left+k<n;left++){
				int right = left+k;
				if (s.charAt(left)==s.charAt(right) && k==1){
					dp[left][right]=true;
					if (right-left+1>max){
						max = right-left+1;
						pos[0] = left;pos[1]=right;
					}
				}
				else if (s.charAt(left)==s.charAt(right)){
					dp[left][right] = dp[left+1][right-1];
					if (dp[left][right] && right-left+1>max){
						max = right-left+1;
						pos[0] = left;pos[1]=right;
					}
				}
			}
		}
		return s.substring(pos[0],pos[1]+1);
	}

	/** 
	 * find if there is a subarray with length>=2 and sum is multiple of k
	 * **/

	public boolean checkSubarraySum(int[] nums, int k) {
		Map<Integer,Integer> map = new HashMap<>();
		map.put(0,-1);
		int runningsum = 0;
		for (int i=0;i<nums.length;i++){
			runningsum += nums[i];
			if (k!=0) runningsum %= k;
			//System.out.println(runningsum);
			if (map.containsKey(runningsum) && i-map.get(runningsum)>1) return true;
			if (!map.containsKey(runningsum)) map.put(runningsum,i);
		}
		return false;
	}

	/**
	 * Given an array of number and two player, one can pick either head or tail of the arrays,
	 * determine whether player 1 will win in the end.
	 * 
	 * Some much wiser solution is posted on leetcode, but this one comes from myself.
	 * **/

	public boolean PredictTheWinner(int[] nums) {
		int n = nums.length;
		int[] sum = new int[nums.length];
		sum[0] = nums[0];
		for (int i=1;i<nums.length;i++){
			sum[i] = sum[i-1] + nums[i];
		}
		int[][] dp = new int[n][n];
		for (int i=0;i<n;i++) dp[i][i] = nums[i];
		for (int k=1;k<n;k++){
			for (int left=0;left+k<n;left++){
				int right = left+k;
				//System.out.println("left is now " + left + " and right is now " + right);
				if (k==1) dp[left][right] = Math.max(nums[left],nums[right]);
				else{
					dp[left][right] = Math.max(nums[left]+sum[right]-sum[left]-dp[left+1][right],
							nums[right]+sum[right-1]-sum[left]+nums[left]-dp[left][right-1]);
				}
				// System.out.println("dp[left][right] is now " + dp[left][right]);
			}
		}
		//System.out.println(dp[0][n-1]);
		int res = dp[0][n-1]*2-sum[n-1];
		if (res>=0) return true;
		else return false;
	}
	/**
	 * given coins, count the fewest number of coins which add up to n
	 * **/
	public int coinchange2(int[] arr, int n){
		int[] dp = new int[n+1];
		for (int i=1;i<n;i++) dp[i] = Integer.MAX_VALUE;

		for (int i=1;i<=n;i++){
			for (int coin:arr){
				if (coin<=i && dp[i-coin]!=Integer.MAX_VALUE){
					dp[i] = Math.min(dp[i-coin]+1, dp[i]);
				}
			}
		}
		if (dp[n]==Integer.MAX_VALUE) return -1;
		else return dp[n];
	}

	/**
	 * given an array representing prices each day, calculate max profit doing at most k transactions.
	 * 
	 * idea :
	 * dp[k+1][n]
	 * dp[i][j] = Math.max(dp[i][j-1],dp[i-1][jj-1] + prices[j]-prices[jj] where jj in range[0,j]
	 *          = Math.max(dp[i][j-1],prices[j]-Math.max(dp[i-1][jj-1]-prices[jj]);
	 *          
	 * **/

	public int maxProfit(int k, int[] prices) {
		int len = prices.length;
		if (k >= len / 2) return quickSolve(prices);

		int[][] t = new int[k + 1][len];
		for (int i = 1; i <= k; i++) {
			int tmpMax =  -prices[0];
			for (int j = 1; j < len; j++) {
				t[i][j] = Math.max(t[i][j - 1], prices[j] + tmpMax);
				tmpMax =  Math.max(tmpMax, t[i - 1][j - 1] - prices[j]); 
				// ignore the first line in this loop and think only about the second line
				// if we only implement second line, what we got?
				// for every j, we will get the largest value that ends at j
				// and that makes prices[j]+tmpMax the largest value we can get for i transactions
				// we suppose the value we want to get end at position j, since t[i][j-1] allows we to get
				// any bigger value which ends before position j
			}
		}
		return t[k][len - 1];
	}


	private int quickSolve(int[] prices) {
		int len = prices.length, profit = 0;
		for (int i = 1; i < len; i++)
			// as long as there is a price gap, we gain a profit.
			if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
		return profit;
	}
}
