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


Backtracking:

/**
* why this problem takes so long:
* 1. forgot to put res as global
* 2. when firsting doing it, only counting if array starts with i would make a valid array, thus only plus 1 at a time
*   e.g. countArrangement(15) = 15 or sth like that
* 3. just practice.... 

**/
  
public class Solution {
    int res = 0;
    
    public int countArrangement(int N) {
        List<Integer> cache = new ArrayList<>();
        for (int i=1;i<=N;i++) cache.add(i);
        for (int i=0;i<cache.size();i++) helper(i,cache,0);
        return res;
    }
    
    public void helper(int num, List<Integer> cache, int idx){
        int cur = cache.get(num);
        if (cur % (idx+1)==0 || (idx+1)%cur==0){
            cache.remove(num);
            if (cache.size()==0){
                res++;
                cache.add(num,cur);
                return;
            }
            else{
               for (int j=0;j<cache.size();j++){
                helper(j,cache,idx+1);
               } 
            }
            cache.add(num,cur);
        }
    }
}


//////////////////////////////////////////find leaves of a binary tree ////////////////////////////////////////////////
public class Solution {
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root==null) return res;
        List<Integer> lres = new ArrayList<>(root.val);
        if (root.left==null && root.right==null){
            res.add(lres);
            return res;
        }
        List<List<Integer>> left = findLeaves(root.left);
        List<List<Integer>> right = findLeaves(root.right);
        int i = left.size()-1, j=right.size()-1;
        while (i>=0 && j>=0){
            List<Integer> leftLeaves = left.get(i);
            List<Integer> rightLeaves = right.get(j);
            leftLeaves.addAll(rightLeaves);
            res.add(leftLeaves);
            i--;j--;
        }
        if (i>=0) {
            while (i>=0){
                res.add(leftLeaves);
                i--;
            }
        }
        if (j>=0){
            while (j>=0){
                res.add(rightLeaves);
                j--;
            }
        }
        res.add(lres);
        return res;
    }
}


------------------------------------------------------------------------------------------------------------
Maze (leetcode)

//few problems: 
// 1. when first doing this, i changed every cell in the board I walked to 2. But I defined if there is a 2 in the way
// we just stop there. This blocks our way to the destination.
// 2. So I then changed to only changing the cell we stopped due to reaching a wall/boundary to 2. This is correct logic, not working
// only because in the loop of helper function, I did not check if we had been that cell before, which gives stackoverflow.

     public class Solution {
    int[][] dirs = {{-1,0},{1,0},{0,1},{0,-1}};  //up,down,right,left
    boolean res = false;
    int[][] copy;
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        //dfs, get all the path possible, 
        if (maze==null || maze.length==0) return false;
        int m = maze.length, n = maze[0].length;
        maze[start[0]][start[1]] = 2;
        for (int[] dir : dirs){
            if (res) return res;
            int i = start[0] + dir[0];
            int j = start[1] + dir[1];
            if (i>=0 && j>=0 && i<m && j<n && maze[i][j]==0){
                helper(maze,destination,i,j,m,n,dir[0],dir[1]);
            }
        }
        return res;
    }
    
    public void helper(int[][] maze, int[] des,int row, int col,int m, int n,int ud,int lr){
        while (row+ud>=0 && row+ud<m && col+lr>=0 && col+lr<n && maze[row+ud][col+lr]!=1){
            row += ud;
            col += lr;
        }
        if (row==des[0] && col==des[1]) {
            res = true;
            return;
        }
        else{
            if (maze[row][col]==0){
                maze[row][col] = 2;
                for (int[] dir : dirs){
                    int i = row + dir[0];
                    int j=  col + dir[1];
                    if (i>=0 && j>=0 && i<m && j<n && maze[i][j]==0){
                        helper(maze,des,i,j,m,n,dir[0],dir[1]);
                     }
                }
            }
            
        }
    }
}

// backtracking : N queens 
public class Solution {
    int[][] dirs = {{-1,-1},{-1,1}};
    //constraint1:make sure no attack
    //cons2:finally gets n queens in the board
    public List<List<String>> solveNQueens(int n) {
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<n;i++){
            sb.append('.');
        }
        List<List<String>> res = new ArrayList<>();
        for (int i=0;i<n;i++){
            List<String> temp = new ArrayList<>();
            String s = change(new String(sb),i);
            temp.add(s);
            backtrack(res,temp,n-1,n,new String(sb));
        }
        return res;
    }
    
    public void backtrack(List<List<String>> res, List<String> temp, int count,int n,String sb){
        if (count==0){
            res.add(new ArrayList<String>(temp));
            return;
        }
        
        for (int i=0;i<n;i++){
            String ss = change(sb,i);
            int size = temp.size();
            if (noattack(temp,size,i,n)){
                temp.add(ss);
                backtrack(res,temp,count-1,n,sb);
                temp.remove(temp.size()-1);
            }
        }
    }
    
    public boolean noattack(List<String> temp, int row, int col, int n){
        for (int i=0;i<temp.size();i++){
            if (temp.get(i).charAt(col)=='Q') return false;
        }
        for (int[] dir : dirs){
            int a = dir[0], b = dir[1], tmprow = row+a, tmpcol = col+b;
            while (tmprow>=0 && tmprow<n && tmpcol>=0 && tmpcol<n){
                if (temp.get(tmprow).charAt(tmpcol)=='Q') return false;
                tmprow += a;
                tmpcol += b;
            }
        }
        return true;
    }
    
    public String change(String ori, int pos){
        return ori.substring(0,pos)+'Q'+ori.substring(pos+1);
    }
}
