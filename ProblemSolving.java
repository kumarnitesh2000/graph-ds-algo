import java.util.*;
public class ProblemSolving {
    // maximum unit area or maxareofISLAND
    public static int findBFS(int[][] grid,int i,int j){
        int count = 0;
        int n = grid.length,m = grid[0].length;
        Queue<PairXY> q = new LinkedList<>();
        q.add(new PairXY(i,j));
        grid[i][j] = 0;
        int[] dx = {-1,1,0,0,-1,1,1,-1};
        int[] dy = {0,0,-1,1,-1,1,-1,1};
        while(!q.isEmpty()){
            PairXY poll = q.poll();
            int x = poll.x,y = poll.y;
            count++;
            for(int k=0;k<dx.length;k++){
                int new_x = x+dx[k],new_y = y+dy[k];
                if(new_x>=0 && new_x<n && new_y>=0 && new_y<m && grid[new_x][new_y]==1){
                    q.add(new PairXY(new_x,new_y));
                    grid[new_x][new_y] = 0;
                }
            }
        }
        return count;
    }
    public int findMaxArea(int[][] grid)
    {
        int tot = 0;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j]==1){
                    tot=Math.max(findBFS(grid,i,j),tot);
                }
            }           
        }
        return tot;
    }
}

class PairXY{
    int x,y;
    PairXY(int x,int y){
        this.x = x;
        this.y = y;
    }
    
}
