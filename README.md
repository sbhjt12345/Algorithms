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


"
public class Solution {
    List<String> res = new ArrayList<>();
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>(){
           public int compare(String s1, String s2){
             return s1.length()-s2.length();  
           } 
        });
        for (String w : words) pq.add(w);
        //System.out.println(pq.peek());
        List<String> list = new ArrayList<>();
        list.add(pq.poll());
        while (!pq.isEmpty()){
            String target= pq.poll();
            helper(target,list);
        }
        return res;
    }
    
    public void helper(String target, List<String> list){
        int n = target.length();
        boolean[] dp = new boolean[n+1];
        dp[n] = true;
        for (int i=n-1;i>=0;i--){
            for (int j=i+1;j<=n;j++){
                    dp[i] = dp[i] || (dp[j] && list.contains(target.substring(i,j)));
            }
        }
        if (dp[0]) res.add(target);
        list.add(target);
    }
}


"

