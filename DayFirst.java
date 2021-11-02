import java.util.*;

public class DayFirst
{
    // this will convert input format to adjancy list assuming undirected graph taking 0 based indexing
    public static ArrayList<ArrayList<Integer>> adj_list_undirected(int n,int m,int[][] connections){
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        // n is vertices
        for(int i=0;i<n;i++){
            list.add(new ArrayList<Integer>());   
        }
        // m is edges
        for(int i=0;i<m;i++){
            int u = connections[i][0],v = connections[i][1];
            list.get(u).add(v);
            list.get(v).add(u);
        }
        return list;
    }
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
    // print undirected adjancy list
    public static void print_undirected_adj_list(ArrayList<ArrayList<Integer>> adj_list){
        System.out.println("undirected graph");
        int index=0;
        for(ArrayList<Integer> lis: adj_list){
            System.out.print(index+" -> ");System.out.print(lis);
            System.out.println();
            index++;
        }
    }
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
    //print weighted undirected graph
    public static void print_weighted_undirected_adj_list(ArrayList<ArrayList<Pair>> adj_weighted_undirected_list){
        System.out.println("weighted undirected graph");
        int index=0;
        for(ArrayList<Pair> lis: adj_weighted_undirected_list){
            System.out.print(index+" -> ");System.out.print(lis);
            System.out.println();
            index++;
        }
    }
    //dfs util function
    public static void dfsUtil(int node,boolean[] vis, ArrayList<ArrayList<Integer>> adj_list) {
        vis[node] = true;
        System.out.print(node+" ");
        Iterator<Integer> it = adj_list.get(node).listIterator();
        while(it.hasNext()) {
            int neigh = it.next();
            if(!vis[neigh]){
                dfsUtil(neigh, vis,adj_list);
            }
        }
    }
    // dfs traversal
    public static void dfs(ArrayList<ArrayList<Integer>> adj_list){
        System.out.println("dfs");
        int V = adj_list.size();
        boolean[] vis = new boolean[V];
        int source = 0;
        for(int i=0;i<V;i++){
            if(!vis[i]){
                dfsUtil(source,vis,adj_list);
            }
        }
        System.out.println("");
    }
    // bfs traversal
    public static void bfsUtil(int source,boolean[] vis,ArrayList<ArrayList<Integer>> adj_list){
        Queue<Integer> q = new LinkedList<Integer>();q.add(source);vis[source] = true;
        while(!q.isEmpty()){
            int poll = q.poll();
            System.out.print(poll+" ");
            Iterator<Integer> itr = adj_list.get(poll).listIterator();
            while(itr.hasNext()){
                int neigh = itr.next();
                if(!vis[neigh]){
                    vis[neigh] = true;
                    q.add(neigh);
                }
            }
        }
    }
    public static void bfs(ArrayList<ArrayList<Integer>> adj_list){
        System.out.println("bfs");
        int V = adj_list.size();
        boolean[] vis = new boolean[V];
        int source = 0;
        for(int i=0;i<V;i++){
            if(!vis[i]){
                bfsUtil(source,vis,adj_list);
            }
        }
        System.out.println("");
    }
    public static boolean detectCycleinUndirectedGraph_DFS_Util(PairC node,boolean[] vis,ArrayList<ArrayList<Integer>> adj_list){
        int n = node.node;
        int par = node.par; 
        vis[n] = true;
        Iterator<Integer> itr = adj_list.get(n).listIterator();
        while(itr.hasNext()){
            int neigh = itr.next();
            if(!vis[neigh]){
                if(detectCycleinUndirectedGraph_DFS_Util(new PairC(neigh,n),vis,adj_list)){
                    return true;
                }
            }else{
                if(!(par==neigh)){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * tell the status of cycle in undirected graph
     * @param adj_list
     * @return
     */
    public static boolean detectCycleinUndirectedGraph_DFS(ArrayList<ArrayList<Integer>> adj_list){
        int V = adj_list.size();boolean[] vis = new boolean[V];
        for(int i=0;i<V;i++){
            if(!vis[i]){
                boolean is_cycle = detectCycleinUndirectedGraph_DFS_Util(new PairC(i,0),vis,adj_list);
                if(!is_cycle){
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean detectCycleinUndirectedGraph_BFS_Util(PairC node,boolean[] vis,ArrayList<ArrayList<Integer>> adj_list){
        int n = node.node;
        Queue<PairC> q = new LinkedList<>();
        vis[n] = true;q.add(node);
        while(!q.isEmpty()){
            PairC poll = q.poll();
            int poll_node = poll.node,parent = poll.par;
            Iterator<Integer> itr = adj_list.get(poll_node).listIterator();
            while(itr.hasNext()){
                int neigh = itr.next();
                if(!vis[neigh]){
                    vis[neigh] = true;
                    q.add(new PairC(neigh, poll_node));
                }else{
                    if(neigh!=parent){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * tell the status of cycle in undirected graph
     * @param adj_list
     * @return
     */
    public static boolean detectCycleinUndirectedGraph_BFS(ArrayList<ArrayList<Integer>> adj_list){
        int V = adj_list.size();boolean[] vis = new boolean[V];
        for(int i=0;i<V;i++){
            if(!vis[i]){
                boolean is_cycle = detectCycleinUndirectedGraph_BFS_Util(new PairC(i,-1),vis,adj_list);
                if(!is_cycle){
                    return false;
                }
            }
        }
        return true;
    }
    // detect cycle in directed graph using dfs
    public static boolean detectCycleInDirectedGraph_DFS_Util(int node,boolean[] vis,boolean[] mark,ArrayList<ArrayList<Integer>> adj_list){
        vis[node] = true;mark[node] = true;
        Iterator<Integer> itr = adj_list.get(node).listIterator();
        while(itr.hasNext()){
            int neigh = itr.next();
            if(!vis[neigh]){
                if(detectCycleInDirectedGraph_DFS_Util(neigh,vis,mark,adj_list)){
                    return true;
                }
            }else{
                // moving in same path
                if(mark[neigh]){
                    return true;
                }
            }
        }
        mark[node] = false;
        return false;
    }
    public static boolean detectCycleInDirectedGraph_DFS(ArrayList<ArrayList<Integer>> adj_list){
        int V = adj_list.size();
        boolean[] vis = new boolean[V],mark = new boolean[V];
        for(int i=0;i<V;i++){
            if(detectCycleInDirectedGraph_DFS_Util(i,vis,mark,adj_list)){
                return true;
            }
        }
        return false;
    }
	public static void main(String[] args) {
	    //sample undirected graph
	    int input_n = 4,input_m = 4;
	    int[][] matrix = {{1,0},{1,2},{2,3},{3,0}};
		ArrayList<ArrayList<Integer>> adj_undirected_list = adj_list_undirected(input_n,input_m,matrix);
        print_undirected_adj_list(adj_undirected_list);
        //directed graph creation
        int n = 5,m = 5;
        int[][] matrix_3 = {{0,1},{1,2},{1,4},{4,3},{2,3}};
        ArrayList<ArrayList<Integer>> adj_directed_list = adj_list_directed(n,m,matrix_3);
        //directed graph creation 2
        int n1 = 8,m1 = 9;
        int[][] matrix_4 = {{0,1},{1,2},{1,4},{4,3},{2,3},{7,1},{6,7},{7,5},{5,6}};
        ArrayList<ArrayList<Integer>> adj_directed_list_1 = adj_list_directed(n1,m1,matrix_4);
        //dfs traversal
        dfs(adj_undirected_list);
        //bfs traversal
        bfs(adj_undirected_list);
        //sample of weighted undirected graph
        int[][] matrix_2 = {{1,0,1},{1,2,2},{2,3,3},{3,0,4}};
        ArrayList<ArrayList<Pair>> adj_weighted_undirected_list = adj_list_weighted_undirected(input_n,input_m,matrix_2);
        print_weighted_undirected_adj_list(adj_weighted_undirected_list);
        //detect cycle in undirected graph using dfs
        System.out.println("cycle status in undirected graph using dfs : "+detectCycleinUndirectedGraph_DFS(adj_undirected_list));
        //detect cycle in undirected graph using bfs
        System.out.println("cycle status in undirected graph using bfs : "+detectCycleinUndirectedGraph_BFS(adj_undirected_list));
        //detect cycle in directed graph using dfs 
        System.out.println("cycle status in directed graph using dfs 1: "+detectCycleInDirectedGraph_DFS(adj_directed_list));
        System.out.println("cycle status in directed graph using dfs 2: "+detectCycleInDirectedGraph_DFS(adj_directed_list_1));
	}
}

class Pair{
    int node;
    int weight;
    Pair(int node,int weight){
        this.node = node;
        this.weight = weight;
    }
    public String toString(){
        return "("+node+","+weight+")";
    }
}

class PairC{
    int node,par;
    PairC(int node,int par){
        this.node = node;
        this.par = par;
    }
}