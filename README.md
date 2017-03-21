# Algorithms
Learning problems and projects of general algorithms 


public class Solution {
    public int lgt(int[] nums){
       int n = nums.length;
       int[] dp = new int[n];
       dp[0] = nums[0];
       for (int i=1;i<n;i++){
         if (dp[i-1]>0) dp[i] = dp[i-1]+nums[i];
         else dp[i] = nums[i];
       }
       int max = Integer.MIN_VALUE;
       for (int i=0;i<n;i++){
          max = Math.max(dp[i],max);
       }
       return max;
    }
  
    public static void main(String[] args) {
        Solution p = new Solution();
        int[] nums = {-2,-3,4,-1,-2,1,5,-3};
        int res = p.lgt(nums);
        System.out.println(res);
    }
}

I think its just a kadane's algorithm.
