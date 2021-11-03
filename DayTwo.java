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
    // min step from start to destination by knight N*N Board
    //https://www.geeksforgeeks.org/minimum-steps-reach-target-knight/
    public static int minStepToReachTarget(int KnightPos[], int TargetPos[], int N){
        Queue<PairPos> q = new LinkedList<>();
        boolean[][] vis = new boolean[N][N];
        // convert into zero based indexing
        q.add(new PairPos(KnightPos[0]-1,KnightPos[1]-1,0));
        vis[KnightPos[0]-1][KnightPos[1]-1] = true;
        //horse can move in dir
        int dx[] = { -2, -1, 1, 2, -2, -1, 1, 2 };
        int dy[] = { -1, -2, -2, -1, 1, 2, 2, 1 };
        while(!q.isEmpty()){
            PairPos poll = q.poll();
            int x = poll.x,y = poll.y,step = poll.step;
            if(x==TargetPos[0]-1 && y==TargetPos[1]-1){
                return step;
            }
            for(int i=0;i<dx.length;i++){
                int new_x = x+dx[i],new_y = y+dy[i];
                if(new_x>=0 && new_x<N && new_y>=0 && new_y<N && !vis[new_x][new_y]){
                    vis[new_x][new_y] = true;
                    q.add(new PairPos(new_x,new_y,step+1));
                }
            }
        }
        return Integer.MAX_VALUE;
    }
    public static String newString(String s,int i,char c){
        char[] ca = s.toCharArray();
        ca[i] = c;
        return new String(ca);
    }
    public static boolean isOneWordDiffer(String s,String d){
        for(int i=0;i<s.length();i++){
            for (char c = 'a'; c <= 'z'; ++c){
                if(newString(s,i,c).equals(d)){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean findWordInList(String target,String[] list){
        for(String l: list){
            if(l.equals(target)){
                return true;
            }
        }
        return false;
    }
    public static int wordLadderLength(String startWord, String targetWord, String[] wordList)
    {
        if(!findWordInList(targetWord,wordList)){
            return 0;
        }
        boolean[] vis = new boolean[wordList.length];
        Queue<WordPair> q = new LinkedList<>();
        q.add(new WordPair(startWord,1));
        while(!q.isEmpty()){
            WordPair poll = q.poll();
            String word = poll.word;//System.out.println(word);
            int step = poll.step;
            //System.out.println(word+" "+step);
            if(word.equals(targetWord)){
                return step;
            }
            for(int i=0;i<wordList.length;i++){
                if(!vis[i] && isOneWordDiffer(word,wordList[i])){
                    q.add(new WordPair(wordList[i],step+1));
                    vis[i] = true;
                }
            }
        }
        return 0;
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
        // bfs problem for finding minimum steps to reach the target pos
        System.out.println("min steps are : "+minStepToReachTarget(new int[]{4,5},new int[]{1,1},6));
        //word ladder 1 problem with bfs
        System.out.println("step take in transformation: "+wordLadderLength("toon","plea",new String[]{"poon","plea","same","poie","plee","plie","poin"}));

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

class PairPos{
    int x,y,step;
    PairPos(int x,int y,int step){
        this.x = x;
        this.y = y;
        this.step = step;
    }
}

class WordPair{
    String word;
    int step;
    WordPair(String word,int step){
        this.word = word;
        this.step = step;
    }
}