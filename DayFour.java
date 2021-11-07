import java.util.*;
public class DayFour {
    // convert input format in weighted undirected graph
    public static ArrayList<ArrayList<Pair>> adj_list_weighted_undirected(int n,int m,int[][] connections){
        ArrayList<ArrayList<Pair>> list = new ArrayList<>();
        // n is vertices
        for(int i=0;i<n;i++){
            list.add(new ArrayList<Pair>());   
        }
        // m is edges
        for(int i=0;i<m;i++){
            int u = connections[i][0],v = connections[i][1];
            int w = connections[i][2];
            list.get(u).add(new Pair(v,w));
            list.get(v).add(new Pair(u,w));
        }
        return list;
    }
    public static int[] shortestPath(int source,ArrayList<ArrayList<Pair>> adj_list){
        int V = adj_list.size();int[] dis = new int[V];
        Deque<DequeNode> dq = new LinkedList<>();
        // init distance array
        for(int i=0;i<V;i++){
            dis[i] = i!=source ? Integer.MAX_VALUE: 0;
        }
        dq.add(new DequeNode(source, 0));
        while(!dq.isEmpty()){
            DequeNode poll = dq.poll();
            int node = poll.node,dis_from_source = poll.dis_from_source;
            Iterator<Pair> itr = adj_list.get(node).listIterator();
            while(itr.hasNext()){
                Pair neigh = itr.next();
                int neigh_node = neigh.node,neigh_weight = neigh.weight;
                if(dis_from_source+neigh_weight < dis[neigh_node]){
                    dis[neigh_node] = neigh_weight+dis_from_source;
                    if(neigh_weight==0){
                        dq.addFirst(new DequeNode(neigh_node,dis[neigh_node]));
                    }else{
                        dq.addLast(new DequeNode(neigh_node,dis[neigh_node]));
                    }
                }
            }
        }
        return dis;
    }
    public static int[] shortestPathDijkstra(int source, ArrayList<ArrayList<Pair>> adj_list,int target){
        int V = adj_list.size();int[] dis = new int[V],par = new int[V];
        // init distance array
        for(int i=0;i<V;i++){
            dis[i] = i!=source ? Integer.MAX_VALUE: 0;
            par[i] = i!=source? -1: 0;
        }
        // this priority queue will return the small distance element first
        PriorityQueue<DequeNode> pq = new PriorityQueue<DequeNode>((a,b) -> a.dis_from_source - b.dis_from_source);
        pq.add(new DequeNode(source,dis[source]));
        while(!pq.isEmpty()) {
            DequeNode poll = pq.poll();
            int node = poll.node,dis_from_source = poll.dis_from_source;
            Iterator<Pair> itr = adj_list.get(node).listIterator();
            while(itr.hasNext()) {
                Pair pair = itr.next();int neigh  = pair.node,wt = pair.weight;
                if(wt+dis_from_source < dis[neigh]){
                    dis[neigh] = wt+dis_from_source;
                    par[neigh] = node;
                    pq.add(new DequeNode(neigh,dis[neigh]));
                }
            }
        }
        int[] traversal_path = new int[target+1];  
        int index=0;
        int x = target+1;
        while(par[x]!=0){
            traversal_path[index++] = par[x];
            x = par[x];
        }traversal_path[index++] = par[x];
        System.out.print(" traversal_path from "+source+" to "+target+" is ");
        for(int i=index-1;i>=0;i--){
            System.out.print(traversal_path[i]+" ");
        }
        System.out.println();
        System.out.print("distance from source to each node is: ");
        return dis;
    }
    // Maze 2 problem
    /**
     * @param maze: the maze
     * @param start: the start
     * @param destination: the destination
     * @return: the shortest distance for the ball to stop at the destination
     */
    public static int shortestDistanceMazeTwo(int[][] maze, int[] start, int[] destination) {
        int r = maze.length,c = maze[0].length;int[][] maze_dis = new int[r][c];
        PriorityQueue<Maze> pq = new PriorityQueue<Maze>((a,b) -> a.dis-b.dis); 
        int short_dis = 0;
        int weight = 1;
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                maze_dis[i][j] = Integer.MAX_VALUE;
            }
        }
        int[] dx = {-1,1,0,0};
        int[] dy = {0,0,-1,1};
        int tot_dir = 4;
        maze_dis[start[0]][start[1]] = 0;
        pq.add(new Maze(start[0],start[1],maze_dis[start[0]][start[1]]));
        while(!pq.isEmpty()){
            Maze poll = pq.poll();
            int x = poll.x,y = poll.y,dis = poll.dis;
            for(int i = 0;i<tot_dir;i++){
                int new_x = x,new_y = y;
                int dis_count=0;
                while(new_x<r && new_x>=0 && new_y<c && new_y>=0 && maze[new_x][new_y]==0){
                    new_x+=dx[i];
                    new_y+=dy[i];
                    dis_count+=weight;
                }
                new_x-=dx[i];new_y-=dy[i];dis_count-=weight;
                if(maze_dis[new_x][new_y] > dis+dis_count){
                    maze_dis[new_x][new_y] = dis+dis_count;
                    pq.add(new Maze(new_x,new_y,dis+dis_count));
                }
            }
        } 
        if(maze_dis[destination[0]][destination[1]] == Integer.MAX_VALUE) {
            short_dis = -1;
        }else{
            short_dis = maze_dis[destination[0]][destination[1]];
        }
        return short_dis;
    }
    public static int countPaths(int n, int[][] roads) {
        long[] ways = new long[n];
        int[] dis = new int[n];
        int s = 0;
        long mod = 1000000007;
        ArrayList<ArrayList<Pair>> adj_list = adj_list_weighted_undirected(n, roads.length, roads); 
        for(int i=0;i<n;i++){
            dis[i] = Integer.MAX_VALUE;
        }
        dis[s] = 0;ways[s] = 1;
        PriorityQueue<DequeNode> pq = new PriorityQueue<>((a,b) -> a.dis_from_source - b.dis_from_source);
        pq.add(new DequeNode(s,0));
        while(!pq.isEmpty())
        {   
            DequeNode poll = pq.poll();
            int node = poll.node,dis_from_source = poll.dis_from_source;
            Iterator<Pair> itr = adj_list.get(node).listIterator();
            while(itr.hasNext()){
                Pair neigh = itr.next();
                int neigh_n = neigh.node,neigh_w = neigh.weight;
                if(dis[neigh_n] > neigh_w+dis_from_source){
                    dis[neigh_n] = neigh_w+dis_from_source;
                    ways[neigh_n] = ways[node];
                    pq.add(new DequeNode(neigh_n,dis[neigh_n]));
                }else if(dis[neigh_n] == neigh_w+dis_from_source){
                    ways[neigh_n]=(ways[neigh_n]+ways[node])%mod;
                }
            }
        }
        //for(int d: dis)System.out.print(d+" ");
        return (int)ways[n-1];
    }
    public static void main(String[] args){
        // create a weighted undirected graph
        int[][] matrix_2 = {{0,1,0},{1,2,1},{0,3,1},{3,4,1},{2,3,0}};
        ArrayList<ArrayList<Pair>> adj_weighted_undirected_list = adj_list_weighted_undirected(5,5,matrix_2);
        System.out.print("single source shortest path in graph (weighted 0/1) using 0/1 BFS: ");
        int[] s_p = shortestPath(0,adj_weighted_undirected_list);for(int p: s_p) System.out.print(p+" ");System.out.println();
        int[][] matrix_3 = {{0,1,1},{0,3,4},{1,2,1},{2,3,1},{2,4,5},{3,4,1}};
        ArrayList<ArrayList<Pair>> adj_weighted_undirected_list_1 = adj_list_weighted_undirected(5,6,matrix_3);
        System.out.print("single source shortest path in graph (weighted) using Diskastra(greedy): ");
        int[] s_p_d = shortestPathDijkstra(0,adj_weighted_undirected_list_1,3);for(int p: s_p_d) System.out.print(p+" ");System.out.println();
        System.out.println("maze2 problem: "+shortestDistanceMazeTwo(new int[][]{{0,0,1,0,0},{0,0,0,0,0},{0,0,0,1,0},{1,1,0,1,1},{0,0,0,0,0}}, new int[]{0,4},new int[]{0,0}));
        // count path problem https://leetcode.com/problems/number-of-ways-to-arrive-at-destination/submissions/
        System.out.println("number-of-ways-to-arrive-at-destination: "+countPaths(2,new int[][]{{1,0,10}}));
    }
}

class DequeNode{
    int node,dis_from_source;
    DequeNode(int node,int dis_from_source){
        this.node = node;
        this.dis_from_source = dis_from_source;
    }
}

class Maze{
    int x, y ,dis;
    Maze(int x,int y,int dis){
        this.x = x;
        this.y = y;
        this.dis = dis;
    }
}