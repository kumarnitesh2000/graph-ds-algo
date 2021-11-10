package Extra_Question;
import java.util.*;


public class Graph {
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
    }
}
