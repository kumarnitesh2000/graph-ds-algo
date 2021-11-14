package Extra_Question;
import java.util.*;


public class Graph {
    public static ArrayList<ArrayList<Pair>> adj_list_weighted_directed(int n,int m,int[][] connections){
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
    static int timer = 0;
    public static void dfsTarjan(int node,int[] in,int[] low,boolean[] is_in_stack,ArrayList<ArrayList<Integer>> res,ArrayList<ArrayList<Integer>> adj_list,Stack<Integer> stack){
        in[node] = low[node] = timer++;
        is_in_stack[node] = true;
        stack.push(node);
        Iterator<Integer> itr = adj_list.get(node).listIterator();
        while(itr.hasNext()){
            int neigh = itr.next();
            if(in[neigh]==-1){
                dfsTarjan(neigh,in,low,is_in_stack,res,adj_list,stack);
                low[node] = Math.min(low[node],low[neigh]);
            }else if(is_in_stack[neigh]){
                low[node] = Math.min(in[neigh],low[node]);
            }
        }
        if(low[node]==in[node]){
            int w = -1;
            ArrayList<Integer> component = new ArrayList<>();
            while(w!=node){
                w = stack.pop();
                component.add(w);
                is_in_stack[w] = false;
            }
            Collections.sort(component);
            res.add(component);
        } 
    }
    public static ArrayList<ArrayList<Integer>> tarjans(int V, ArrayList<ArrayList<Integer>> adj) 
    {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        int[] in = new int[V];Arrays.fill(in,-1);
        int[] low = new int[V];Arrays.fill(low,-1);
        Stack<Integer> stack = new Stack<Integer>();
        boolean[] is_in_stack = new boolean[V];
        for(int i=0;i<V;i++){
            if(in[i]==-1){
                dfsTarjan(i,in,low,is_in_stack,res,adj,stack);
            }
        }
        //Collections.reverse(res);
        Collections.sort(res,(a,b) -> a.get(0).compareTo(b.get(0)));
        return res;
    }
    // shortest path in DAG
    public static int shortestPathDAG(int s, ArrayList<ArrayList<Pair>> adj_list,int des){
        int res = 0,V = adj_list.size();        
        int[] indegree = new int[V];
        Queue<Integer> q = new LinkedList<Integer>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        // create a indegree array
        for(int i=0;i<V;i++){
            Iterator<Pair> itr = adj_list.get(i).listIterator();
            while(itr.hasNext()){
                Pair neigh = itr.next();
                int v = neigh.v;
                indegree[v]++; 
            }
        }
        // put the start points in the queue
        for(int i=0;i<V;i++){
            if(indegree[i]==0){
                q.add(i);
            }
        }
        // queue traversal
        while(!q.isEmpty()){
            int poll = q.poll();list.add(poll);
            Iterator<Pair> itr = adj_list.get(poll).listIterator();
            while(itr.hasNext()){
                int neigh = itr.next().v;
                if(indegree[neigh]>0)indegree[neigh]--;
                if(indegree[neigh]==0)q.add(neigh);
            }
        }
        int[] dis = new int[V];Arrays.fill(dis,Integer.MAX_VALUE);dis[s] = 0;
        //topo sort in list
        for(Integer l: list){
            int node = l;
            Iterator<Pair> itr = adj_list.get(node).listIterator();
            while(itr.hasNext()){
                Pair neigh = itr.next();
                int v = neigh.v,wt = neigh.wt;
                if(dis[node]!=Integer.MAX_VALUE && dis[node]+wt < dis[v]){
                    dis[v] = dis[node]+wt;
                }
            }
        }
        return dis[des];
    }
    // minEdgesToReverse
    public static int minEdgesToReverse(int n,int m,int[][] edges,int src,int dst) {
        int[] dis = new int[n];
        Arrays.fill(dis,Integer.MAX_VALUE);dis[src] = 0;
        ArrayList<ArrayList<Pair>> adj_list = new ArrayList<ArrayList<Pair>>();
        // init adj list
        for(int i=0;i<n;i++){
            adj_list.add(new ArrayList<Pair>());
        }
        // create edges in both dir with 0/1 weight
        for(int i = 0; i < m;i++){
            int u = edges[i][0],v = edges[i][1];
            adj_list.get(u).add(new Pair(v,0));
            adj_list.get(v).add(new Pair(u,1));
        }
        Deque<Pair> dq = new LinkedList<Pair>();
        dq.add(new Pair(src,0));
        while(!dq.isEmpty()) {
            Pair poll = dq.poll();
            int u = poll.v;
            Iterator<Pair> itr = adj_list.get(u).listIterator();
            while(itr.hasNext()) {
                Pair neigh = itr.next();
                int v = neigh.v,wt = neigh.wt;
                if(dis[v] > dis[u]+wt){
                    dis[v] = dis[u]+wt;
                    if(wt==0){
                        dq.addFirst(new Pair(v,wt));
                    }else{
                        dq.addLast(new Pair(v,wt));
                    }
                }
            }
        }
        return dis[dst];
    }
    public static void main(String[] args) {
        /**
         * we use tarjan algo for strongly connected components
         */
        int n = 5,m = 5;
        int[][] matrix_3 = {{0,1},{1,2},{2,0},{1,3},{3,4}};
        ArrayList<ArrayList<Integer>> adj_directed_list = adj_list_directed(n,m,matrix_3);
        ArrayList<ArrayList<Integer>> sccs = tarjans(n,adj_directed_list);
        System.out.println("All the strongly connected components are: ");
        for(ArrayList<Integer> lis: sccs) {
            for(Integer l: lis){
                System.out.print(l+" ");
            }
            System.out.println();
        }
        /*
        topological sort problem solving
        */
        ArrayList<ArrayList<Pair>> adj_list_weighted_directed_1 = adj_list_weighted_directed(4,4,new int[][]{{0,1,1},{1,2,1},{2,3,1},{0,3,4}});
        System.out.println("shortest path in DAG: "+shortestPathDAG(0,adj_list_weighted_directed_1,3));
        /*
        minimum edges to reverse to get path from src to destination
        https://www.geeksforgeeks.org/minimum-edges-reverse-make-path-source-destination/
        */
        int src = 0,destination = 5;
        System.out.println("reverse "+minEdgesToReverse(6,6,new int[][]{{0,1},{2,1},{5,2},{5,4},{4,3},{0,3}},src,destination)+" edges for making a path from src: "+src+"to destination: "+destination);
        /*
        m-coloring problem: check whether we can color the graph with m colors without violation
        */
        
    }
}

class Pair{
    int v,wt;
    Pair(int v, int wt){
        this.v = v;
        this.wt = wt;
    }
}