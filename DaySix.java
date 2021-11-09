import java.util.*;
public class DaySix {
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
    public static int spanningTree(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj) 
    {
        int cost = 0;
        ArrayList<WeightedNode> list = new ArrayList<WeightedNode>();
        int index = 0;
        for(ArrayList<ArrayList<Integer>> adj_list: adj){
            for(ArrayList<Integer> lis: adj_list){
                list.add(new WeightedNode(index,lis.get(0),lis.get(1)));
            }
            index++;
        }
        // sort according to weight 
        Collections.sort(list,(a,b)-> a.weight - b.weight);
        DSU dsu = new DSU(V);
        for(WeightedNode node: list) {
            int u = node.u,v = node.v,weight = node.weight;
            int pu = dsu.findPar(u), pv = dsu.findPar(v);
            if(pu!=pv){
                cost+=weight;
                dsu.unionBySize(u,v);
            }
        }
        return cost;
    }
    public static ArrayList<ArrayList<ArrayList<Integer>>> createGraph(int n,int m,int[][] edges){
        ArrayList<ArrayList<ArrayList<Integer>>> adj = new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<ArrayList<Integer>>());
        }
        for(int i=0;i<m;i++){
            int u = edges[i][0],v = edges[i][1], w = edges[i][2];
            adj.get(u).add(new ArrayList<Integer>(Arrays.asList(v,w)));
        }
        return adj;
    }
    public static void bridgeUtil(int node, int parent,int timer,int[] tin,int[] low,boolean[] vis,ArrayList<ArrayList<Integer>> adj_list,ArrayList<Edge> bridges){
        tin[node] = low[node] = timer++;
        vis[node] = true;
        Iterator<Integer> itr = adj_list.get(node).listIterator();
        while(itr.hasNext()){
            int neigh = itr.next();
            if(neigh==parent){
                continue;
            }
            if(!vis[neigh]){
                bridgeUtil(neigh,node,timer,tin,low,vis,adj_list,bridges);
                low[node] = Math.min(low[node],low[neigh]);
                if(low[neigh] > tin[node]){
                    bridges.add(new Edge(neigh,node));
                }
            }else{
                low[node] = Math.min(tin[neigh],low[node]);
            }
        }
    }
    // bridge in a graph
    public static ArrayList<Edge> bridgesInGraph(ArrayList<ArrayList<Integer>> adj_list){
        ArrayList<Edge> adj = new ArrayList<Edge>();
        int V = adj_list.size(),timer = 0,parent = -1;
        int[] in = new int[V];Arrays.fill(in,-1);
        boolean[] vis = new boolean[V];
        int[] low = new int[V];Arrays.fill(low,-1);
        for(int i=0;i<V;i++){
            if(!vis[i]){
                bridgeUtil(i,parent,timer,in,low,vis,adj_list,adj);
            }
        } 
        return adj;
    }
    public static void articulationPointUtil(int node, ArrayList<ArrayList<Integer>> adj_list, int[] in,int[] low,int parent,int timer,boolean[] vis,boolean[] articulation_points){
        vis[node] = true;
        in[node] = low[node] = timer++;
        int child = 0;
        Iterator<Integer> itr = adj_list.get(node).listIterator();
        while(itr.hasNext()){
            int neigh = itr.next();
            if(parent==neigh){
                continue;
            }
            if(!vis[neigh]){
                articulationPointUtil(neigh, adj_list, in, low, node, timer, vis, articulation_points);
                low[node] = Math.min(low[node],low[neigh]);
                if(low[neigh] >= in[node] && parent!=-1){
                    articulation_points[node] = true;
                }
                child++;
            }else{
                low[node] = Math.min(low[node],in[neigh]);
            }
        }
        if(child > 1 && parent==-1){
            articulation_points[node] = true;
        }
    }
    public static boolean[] articulationPoint(ArrayList<ArrayList<Integer>> adj_list){
        int V = adj_list.size(),timer = 0,parent = -1;
        boolean[] articulation_points = new boolean[V];
        boolean[] vis = new boolean[V];
        int[] in = new int[V];
        int[] low = new int[V];
        for(int i=0;i<V;i++){
            if(!vis[i])
            articulationPointUtil(i,adj_list,in,low,parent,timer,vis,articulation_points);
        }
        return articulation_points;
    }
    public static void main(String[] args) {
        ArrayList<ArrayList<ArrayList<Integer>>> adj = createGraph(5,6,new int[][]{{4,3,3},{2,3,4},{1,2,3},{0,1,1},{0,4,1},{0,3,2}});
        System.out.println("minimum spanning tree: "+spanningTree(5,adj));
        ArrayList<ArrayList<Integer>> adj_list_undirected = adj_list_undirected(12,14,new int[][]{{0,1},{1,2},{1,3},{2,3},{3,4},{4,5},{3,5},{5,6},{6,7},{6,8},{8,9},{9,10},{9,11},{10,11}});     
        System.out.println("bridges in graph: ");
        ArrayList<Edge> bridges = bridgesInGraph(adj_list_undirected);
        for(Edge edge: bridges) {
            System.out.println(edge.u+" "+edge.v);
        }
        System.out.print("articulation point testcase 1: ");
        ArrayList<ArrayList<Integer>> adj_list_undirected_1 = adj_list_undirected(9,10,new int[][]{{0,1},{1,2},{2,3},{0,3},{3,4},{4,5},{5,6},{5,8},{8,7},{6,7}});
        boolean[] out1 = articulationPoint(adj_list_undirected_1);
        int index = 0;
        for(boolean o: out1){
            if(o){
                System.out.print(index+" ");
            }
            index++;
        }        
        System.out.println();
        System.out.print("articulation point testcase 2(edge case): ");
        ArrayList<ArrayList<Integer>> adj_list_undirected_2 = adj_list_undirected(4,3,new int[][]{{0,1},{0,2},{0,3}});
        boolean[] out2 = articulationPoint(adj_list_undirected_2);
        int i = 0;
        for(boolean o: out2){
            if(o){
                System.out.print(i+" ");
            }
            i++;
        }        
        System.out.println();
    }
}

class WeightedNode{
    int u,v,weight;
    WeightedNode(int u,int v,int weight){
        this.u = u;
        this.v = v;
        this.weight = weight;
    }
}

class Edge{
    int u,v;
    Edge(int u,int v){
        this.u = u;
        this.v = v;
    }
}