package Extra_Question;
import java.util.*;

class IslandType
{
    //Function to find the number of 'X' total shapes.
    public int xShape(char[][] grid)
    {
        Queue<PairXShape> q = new LinkedList<>();
        int n = grid.length,m = grid[0].length;
        int count = 0;
        int[] dx = {0,0,-1,1};
        int[] dy = {-1,1,0,0};
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]=='X'){
                    q.add(new PairXShape(i,j));
                    while(!q.isEmpty()){
                        PairXShape poll = q.poll();
                        int x = poll.x;int y = poll.y;
                        grid[x][y] = 'O';
                        for(int k=0;k<4;k++){
                            int new_x = dx[k]+x,new_y = dy[k]+y;
                            if(new_x>=0 && new_x<n && new_y>=0 && new_y<m && grid[new_x][new_y]!='O'){
                                q.add(new PairXShape(new_x,new_y));
                            }
                        }
                    } 
                    count++;
                }
            }
        }
        return count;
    }
}

class PairXShape{
    int x,y;
    PairXShape(int x,int y){
        this.x = x;
        this.y = y;
    }
}
