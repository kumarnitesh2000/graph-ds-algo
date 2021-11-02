import java.util.*;
public class DayTwo {
    // rotten ornges bfs 4 directions are possible 
    /**
     * tell the min time to rotten all oranges
     * @param grid
     * @return
     */
    public static int orangesRotting(int[][] grid) {
        int r = grid.length,c = grid[0].length;
        Queue<PairRO> q = new LinkedList<PairRO>();
        int fresh_oranges_count = 0;
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                if(grid[i][j]==1){
                    fresh_oranges_count++;
                }else if(grid[i][j]==2){
                    q.add(new PairRO(i,j,0));
                }
            }
        }
        int[] dx = {-1,1,0,0};
        int[] dy = {0,0,1,-1};
        int tot_dir = 4;
        while(!q.isEmpty()){
            PairRO poll = q.poll();
            int x = poll.x;
            int y = poll.y;
            int time = poll.time;
            for(int i=0;i<tot_dir;i++){
                int new_x = dx[i]+x;
                int new_y = dy[i]+y;
                if(new_x>=0 && new_x<r && new_y>=0 && new_y<c && grid[new_x][new_y]==1){
                    if(fresh_oranges_count > 0)fresh_oranges_count--;
                    grid[new_x][new_y] = 2;
                    q.add(new PairRO(new_x,new_y,time+1));
                }
            }
            if(fresh_oranges_count == 0 && q.isEmpty()){
                // System.out.println(x+" "+y);
                return time;
            }
        }
        if(fresh_oranges_count > 0 && q.isEmpty()){
            return -1;
        }else{
            return 0;
        }
    }
    /**
     * @param maze: the maze
     * @param start: the start
     * @param destination: the destination
     * @return: whether the ball could stop at the destination
     */
    public static boolean hasPath(int[][] maze, int[] start, int[] destination) {
        Queue<Pos> q  = new LinkedList<>();
        int r = maze.length,c = maze[0].length;
        maze[start[0]][start[1]] = -1;
        q.add(new Pos(start[0],start[1]));
        int[] dx = {-1,1,0,0};
        int[] dy = {0,0,1,-1};
        int tot_dir = 4;
        while(!q.isEmpty()) {
            Pos poll = q.poll();int x = poll.x,y = poll.y;
            if(x==destination[0] && y==destination[1]){
                return true;
            }
            for(int i=0;i<tot_dir;i++) {
                int new_x = x;int new_y = y;
                int prev_x = x;int prev_y = y;
                while(new_x>=0 && new_x<r && new_y>=0 && new_y<c && (maze[new_x][new_y]==0 || maze[new_x][new_y]==-1)){
                    prev_x = new_x;prev_y = new_y;
                    new_x+=dx[i];new_y+=dy[i];
                }
                if(maze[prev_x][prev_y]==0){
                    maze[prev_x][prev_y] = -1;
                    q.add(new Pos(prev_x, prev_y));
                }
            }
        }
        return false;
    }
    /**
     * tell the minimum multiply operation to reach to target
     * @param start
     * @param end
     * @param options
     * @return
     */
    public static int bfsProblem_start_end(int start,int end,int[] options){
        System.out.print("bfs problem start_end problem");
        Queue<Pair> q = new LinkedList<>();
        int res = -1;boolean[] vis = new boolean[end+1];
        q.add(new Pair(start,0));vis[start] = true;
        while(!q.isEmpty()){
            Pair poll = q.poll();
            if(poll.node==end){
                res = poll.weight;
                break;
            }
            for(int i=0;i<options.length;i++){
                if(poll.node*options[i]<=end && !vis[poll.node*options[i]]){
                    vis[poll.node*options[i]] = true;
                    q.add(new Pair(poll.node*options[i],poll.weight+1));
                }
            }
        }
        return res;
    }
    public static void main(String[] args) {
        // rotten oranges
        int[][] grid_1 = {{2,1,1},{1,1,0},{0,1,1}};System.out.println("testcase 1: "+orangesRotting(grid_1));
        int[][] grid_2 = {{2,1,1},{0,1,1},{1,0,1}};System.out.println("testcase 2: "+orangesRotting(grid_2));
        int[][] grid_3 = {{0}};System.out.println("testcase 3: "+orangesRotting(grid_3)); 
        int[][] grid_4 = {{1}};System.out.println("testcase 4: "+orangesRotting(grid_4));
        //has Path on Maze
        int[][] maze = {{0,0,1,0,0},{0,0,0,0,0},{0,0,0,1,0},{1,1,0,1,1},{0,0,0,0,0}};int[] start={0,4};int[] destination = {4,4};
        System.out.println("haspath: "+hasPath(maze,start,destination));
        //bfs problem start end - description in pdf page(3)
        System.out.print(": "+bfsProblem_start_end(2,100,new int[]{2,5,10}));
        System.out.println();       
    }
}

class PairRO{
    int x,y,time;
    PairRO(int x,int y,int time){
        this.x = x;
        this.y = y;
        this.time = time;
    }
}
class Pos{
    int x,y;
    Pos(int x,int y){
        this.x = x;
        this.y = y;
    }
}
