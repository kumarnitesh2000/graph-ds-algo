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
    //minimum cost path from left to right
    //dijkstra problem solving
    //https://www.geeksforgeeks.org/minimum-cost-path-left-right-bottom-moves-allowed/
    public int minimumCostPath(int[][] grid)
    {
        int n = grid.length,m = grid[0].length;
        PriorityQueue<DijkstraNode> pq = new PriorityQueue<>((a,b) -> a.dis - b.dis);
        int[][] dis = new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                dis[i][j] = Integer.MAX_VALUE;
            }
        }
        int[] dx = {-1,1,0,0};
        int[] dy = {0,0,-1,1};
        dis[0][0] = grid[0][0];
        pq.add(new DijkstraNode(0,0,dis[0][0]));
        while(!pq.isEmpty()){
            DijkstraNode poll = pq.poll();      
            int x = poll.x,y = poll.y;
            for(int i=0;i<4;i++){
                int new_x = x+dx[i];
                int new_y = y+dy[i];
                if(new_x>=0 && new_x<n && new_y>=0 && new_y<m){
                    if(dis[new_x][new_y] > dis[x][y]+grid[new_x][new_y]){
                        // if already in priority queue
                        if(dis[new_x][new_y]!=Integer.MAX_VALUE){
                            pq.remove(new DijkstraNode(new_x,new_y,dis[new_x][new_y]));
                        }
                        dis[new_x][new_y] = dis[x][y]+grid[new_x][new_y];
                        pq.add(new DijkstraNode(new_x,new_y,dis[new_x][new_y]));
                    }
                }
            }
        }
        return dis[n-1][m-1];
    }
    // minimum-time-taken-by-each-job-to-be-completed
    // https://www.geeksforgeeks.org/minimum-time-taken-by-each-job-to-be-completed-given-by-a-directed-acyclic-graph/
    public static ArrayList<ArrayList<Integer>> adj_list_directed(int n,int m,int[][] connections){
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        // n is vertices
        for(int i=0;i<n;i++){
            list.add(new ArrayList<Integer>());   
        }
        // m is edges
        for(int i=0;i<m;i++){
            int u = connections[i][0],v = connections[i][1];
            list.get(u).add(v);
        }
        return list;
    }
    public static int[] minimumTimeJobs(int num_of_jobs,int[][] job_order){
        Queue<Node> q = new LinkedList<>();
        ArrayList<ArrayList<Integer>> adj_list = adj_list_directed(num_of_jobs,job_order.length,job_order);
        int[] res = new int[num_of_jobs];
        int[] indegree = new int[num_of_jobs];
        // set indegree value
        for(int i=0;i<num_of_jobs;i++){
            Iterator<Integer> itr = adj_list.get(i).listIterator();
            while(itr.hasNext()){
                int n = itr.next();
                indegree[n]++;
            }
        }
        //init the queue
        for(int i=0;i<num_of_jobs;i++){
            if(indegree[i]==0){
                q.add(new Node(1,i));
            }
        }
        //q traverse
        while(!q.isEmpty()){
            Node poll = q.poll();
            int n = poll.node,time = poll.time;
            res[n] = time;
            Iterator<Integer> itr = adj_list.get(n).listIterator();
            while(itr.hasNext()){
                int neigh = itr.next();
                if(indegree[neigh]>0)indegree[neigh]--;
                if(indegree[neigh]==0){
                    q.add(new Node(time+1,neigh));
                }
            }
        }
        return res;
    }   
}

class PairXY{
    int x,y;
    PairXY(int x,int y){
        this.x = x;
        this.y = y;
    }
    
}


class DijkstraNode{
    int x,y,dis;
    DijkstraNode(int x,int y,int dis){
        this.x = x;
        this.y = y;
        this.dis = dis;
    }
}

class Node{
    int time,node;
    Node(int time,int node){
        this.time = time;
        this.node = node;
    }
}
