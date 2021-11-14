package Extra_Question;
import java.util.*;

public class GraphColor {
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
    public static int[] colorGraph(ArrayList<ArrayList<Integer>> adj_list){
        int V = adj_list.size();
        int[] colors = new int[V];
        boolean[] vis = new boolean[V];
        Queue<Integer> q = new LinkedList<>();q.add(0);vis[0] = true;
        while(!q.isEmpty()){
            int node = q.poll();
            Iterator<Integer> itr = adj_list.get(node).listIterator();
            while(itr.hasNext()){
                int neigh_node = itr.next();
                // check if same color
                if(colors[node]==colors[neigh_node]){
                    colors[neigh_node]++;
                }
                //if not visited put in q
                if(!vis[neigh_node]){
                    vis[neigh_node] = true;
                    q.add(neigh_node);
                }
            }
        }
        return colors;
    }
    // can we color the graph with m colors 
    public static boolean canColorWithM(int M,ArrayList<ArrayList<Integer>> adj_list){
        return false;
    }
    // backtracking m - coloring problem
    public static boolean isSafe(int node,int c,List<Integer>[] G, int[] color){
        for(Integer n: G[node]){
            if(color[n]==c){
                return false;
            }
        }
        return true;
    }
    public static boolean solve(int node,List<Integer>[] G, int[] color,int m,int n){
        if(node==n){
            return true;
        }
        for(int i=1;i<=m;i++){
            if(isSafe(node,i,G,color)){
                color[node] = i;
                if(solve(node+1,G,color,m,n))return true;
                color[node] = 0;
            }
        }
        return false;
    }
    public static boolean graphColoring(List<Integer>[] G, int[] color, int i, int C) 
    {
        return solve(i,G,color,C,G.length);
    }
    public static void main(String[] args){
        ArrayList<ArrayList<Integer>> adj = adj_list_undirected(4,5,new int[][]{{0,1},{0,2},{0,3},{1,2},{2,3}});
        // color the graph
        int[] colors = colorGraph(adj);
        for(int i=0;i<colors.length;i++){
            System.out.println("node: "+i+" color: "+colors[i]);
        }
    }    
}
