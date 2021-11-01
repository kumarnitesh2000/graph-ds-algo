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
    public static int bfsProblem_start_end(int start,int end,int[] options){
        System.out.println("bfs problem start_end problem");
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
	    //sample undirected graph
	    int input_n = 4,input_m = 4;
	    int[][] matrix = {{1,0},{1,2},{2,3},{3,0}};
		ArrayList<ArrayList<Integer>> adj_undirected_list = adj_list_undirected(input_n,input_m,matrix);
        print_undirected_adj_list(adj_undirected_list);
        //dfs traversal
        dfs(adj_undirected_list);
        //bfs traversal
        bfs(adj_undirected_list);
        //sample of weighted undirected graph
        int[][] matrix_2 = {{1,0,1},{1,2,2},{2,3,3},{3,0,4}};
        ArrayList<ArrayList<Pair>> adj_weighted_undirected_list = adj_list_weighted_undirected(input_n,input_m,matrix_2);
        print_weighted_undirected_adj_list(adj_weighted_undirected_list);
        //bfs problem start end - description in pdf page(3)
        System.out.println(bfsProblem_start_end(2,100,new int[]{2,5,10})); 
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