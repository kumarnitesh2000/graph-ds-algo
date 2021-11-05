import java.util.*;

public class DayThree{
    public static void printArr(int[] a){
        for(int i: a){
            System.out.print(i+" ");
        }
    }
    // created directed graph
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
    public static boolean dfsUtil(int node, int color_option,int[] color, ArrayList<ArrayList<Integer>> adj_list){
        color[node] = color_option;
        Iterator<Integer> itr = adj_list.get(node).listIterator();
        while(itr.hasNext()){
            int neigh = itr.next();
            if(color[neigh]==-1){
                if(!dfsUtil(neigh,1-color_option,color,adj_list)){
                    return false;
                }
            }else if(color[neigh]==color_option){
                return false; 
            }
        }
        return true;
    }
    public static boolean checkForBipartite_DFS(ArrayList<ArrayList<Integer>> adj_list){
        int V = adj_list.size();
        int[] color = new int[V];
        Arrays.fill(color,-1);
        for(int i=0;i<V;i++){
            if(color[i]==-1){
                if(!dfsUtil(i,0,color,adj_list)){
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean bfsUtil(int node, int color_option,int[] color, ArrayList<ArrayList<Integer>> adj_list){
        color[node] = color_option;
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(node);
        while(!queue.isEmpty()) {
            int poll = queue.poll();
            Iterator<Integer> iterator = adj_list.get(poll).listIterator();
            while(iterator.hasNext()) {
                int neigh = iterator.next();
                if(color[neigh]==-1){
                    color[neigh] = 1-color[poll];
                    queue.add(neigh);
                }else if(color[neigh]==color[poll]){
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean checkForBipartite_BFS(ArrayList<ArrayList<Integer>> adj_list){
        int V = adj_list.size();
        int[] color = new int[V];
        Arrays.fill(color,-1);
        for(int i=0;i<V;i++){
            if(color[i]==-1){
                if(!bfsUtil(i,0,color,adj_list)){
                    return false;
                }
            }
        }
        return true;
    }
    public static void topoHelper(int node, boolean[] vis,Stack<Integer> stack,ArrayList<ArrayList<Integer>> adj_list){
        vis[node] = true;
        Iterator<Integer> itr = adj_list.get(node).listIterator();
        while(itr.hasNext()){
            int neigh = itr.next();
            if(!vis[neigh]){
                topoHelper(neigh, vis,stack,adj_list);
            }
        }
        stack.push(node);
    }
    // topo sort using dfs
    public static int[] toposortDFS(ArrayList<ArrayList<Integer>> adj_list){
        int V = adj_list.size();
        boolean[] vis = new boolean[V];
        Stack<Integer> stack = new Stack<Integer>();
        for(int i=0; i<V; i++){
            if(!vis[i])
            topoHelper(i, vis, stack,adj_list);
        }
        int[] topo = new int[V];
        int i=0;
        while(!stack.isEmpty()){
            topo[i++] = stack.pop();
        }
        return topo;
    }
    // topo sort using bfs
    public static int[] toposortBFS(ArrayList<ArrayList<Integer>> adj_list){
        int V = adj_list.size();
        Queue<Integer> q = new LinkedList<Integer>();
        int[] res = new int[V];int[] indegree = new int[V];
        // fill the indegree array
        for(int i=0;i<V;i++){
            Iterator<Integer> itr = adj_list.get(i).listIterator();
            while(itr.hasNext()){
                int n = itr.next();
                indegree[n]++;
            }
        }
        // add init element to Queue
        for(int i=0;i<V;i++){
            if(indegree[i]==0){
                q.add(i);
            }
        }
        int index = 0;
        while(!q.isEmpty()){
            int poll = q.poll();
            res[index++] = poll;
            Iterator<Integer> iterator = adj_list.get(poll).listIterator();
            while(iterator.hasNext()){
                int neigh = iterator.next();
                if(indegree[neigh]>0)indegree[neigh]--;
                if(indegree[neigh]==0)q.add(neigh);
            }
        }
        return res;
    }
    // question course schedule 1
    public static ArrayList<ArrayList<Integer>> createGraph(int V,int[][] matrix){
        ArrayList<ArrayList<Integer>> lis = new ArrayList<>();
        for(int i=0;i<V;i++){
            lis.add(new ArrayList<Integer>());
        }
        for(int i=0;i<matrix.length;i++){
            int[] row = matrix[i];
            int u = row[0],v=row[1];
            lis.get(u).add(v);
        }
        return lis;
    }
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        System.out.println("course schedule problems");
        ArrayList<ArrayList<Integer>> adj_directed_list = createGraph(numCourses,prerequisites);
        int[] indegree = new int[numCourses];
        for(int i=0;i<numCourses;i++){
            Iterator<Integer> itr = adj_directed_list.get(i).listIterator();
            while(itr.hasNext()){
                int n = itr.next();
                indegree[n]++;
            }
        }
        int count = 0;
        int[] res = new int[numCourses];
        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<numCourses;i++){
            if(indegree[i]==0){
                q.add(i);
            }
        }
        int index=0;
        while(!q.isEmpty()){
            int poll = q.poll();count++;
            res[index++] = poll;
            Iterator<Integer> itr = adj_directed_list.get(poll).listIterator();
            while(itr.hasNext()){
                int neigh = itr.next();
                indegree[neigh]--;
                if(indegree[neigh]==0){
                    q.add(neigh);
                }
            }
        }
        if(count==numCourses){
            for(int r: res){
                System.out.print(r+" ");
            }
            System.out.println();
        }
        return count==numCourses;
    }
    // alien dictionary problem
    public static char[] uvRelation(char[] curr,char[] nex){
        char u='a',v='a';
        int len = Math.min(curr.length,nex.length);
        for(int i=0;i<len;i++){
            if(curr[i]!=nex[i]){
                u = curr[i];v = nex[i];
                break;
            }
        }
        return new char[]{u,v};
    }
    public static String findOrder(String [] dict, int N, int K)
    {
        HashMap<Character,HashSet<Character>> adj_list = new HashMap<>();
        HashMap<Character,Integer> indegree = new HashMap<>();
        StringBuilder out = new StringBuilder();
        // create a adjancy list for characters   
        for(int i=0;i<dict.length-1;i++){
            char[] curr = dict[i].toCharArray();
            char[] nex = dict[i+1].toCharArray();
            char[] uv = uvRelation(curr,nex);
            char u = uv[0],v = uv[1];
            if(u!=v){
                if(!adj_list.containsKey(u)){
                    adj_list.put(u,new HashSet<Character>());
                }
                HashSet<Character> set = adj_list.get(u);set.add(v);
                adj_list.put(u,set);
            }
        }
        // for(Map.Entry<Character,HashSet<Character>> entry: adj_list.entrySet()){
        //     Character key = entry.getKey(); 
        //     HashSet<Character> set = entry.getValue();
        //     for(Character c: set){
        //         System.out.println(key+" "+c);   
        //     }
        // }
        // create a indegree map
        for(String s: dict){
            for(char c: s.toCharArray()){
                indegree.put(c,0);
            }
        }
        for(Map.Entry<Character,HashSet<Character>> entry: adj_list.entrySet()){
            HashSet<Character> set = entry.getValue();
            for(Character c: set){
                indegree.put(c,indegree.get(c)+1);
            }
        }
        // for(Map.Entry<Character,Integer> entry: indegree.entrySet()){
        //     System.out.println(entry.getKey()+" "+entry.getValue());
        // }
        //topo sort 
        Queue<Character> q = new LinkedList<>();
        for(Map.Entry<Character,Integer> entry: indegree.entrySet()){
            if(entry.getValue()==0)
            q.add(entry.getKey());
        }
        while(!q.isEmpty()){
            Character poll = q.poll();out.append(poll);
            HashSet<Character> set = adj_list.get(poll);
            if(set!=null){
                    for(Character c: set){
                        indegree.put(c,indegree.get(c)-1);
                        if(indegree.get(c)==0){
                            q.add(c);
                        }
                    }
            }
        }
        //System.out.println(out);
        return out.toString();
    }
    public static void main(String[] args){
        // with odd length cycle
        int input_n = 7,input_m = 7;
	    int[][] matrix = {{0,1},{1,2},{2,6},{2,3},{6,5},{4,5},{3,4}};
        ArrayList<ArrayList<Integer>> adj_undirected_list = adj_list_undirected(input_n,input_m,matrix);
        System.out.println("with odd length cycle: (DFS) isBipartite-> "+checkForBipartite_DFS(adj_undirected_list));
        System.out.println("with odd length cycle: (BFS) isBipartite-> "+checkForBipartite_BFS(adj_undirected_list));
        // with even length cycle 
        int n = 5,m = 5;
	    int[][] matrix_1 = {{0,1},{1,4},{1,2},{2,3},{3,4}};
        ArrayList<ArrayList<Integer>> adj_undirected_list_1 = adj_list_undirected(n,m,matrix_1);
        System.out.println("with even length cycle: (DFS) isBipartite-> "+checkForBipartite_DFS(adj_undirected_list_1));
        System.out.println("with even length cycle: (BFS) isBipartite-> "+checkForBipartite_BFS(adj_undirected_list_1));
        // directed graph creation
        int[][] matrix_4 = {{4,0},{1,0},{4,2},{1,3},{2,3}};
        ArrayList<ArrayList<Integer>> adj_directed_list = adj_list_directed(n,m,matrix_4);
        //topo sort using dfs
        int[] res = toposortDFS(adj_directed_list);
        System.out.println("topo sort using dfs: ");printArr(res);System.out.println();
        // topo sort using bfs (KAHn algo)
        int[] ans = toposortBFS(adj_directed_list); 
        System.out.println("topo sort using bfs: ");printArr(ans);System.out.println();
        //problem solving with topo sort 
        //question 1: Course Schedule https://www.geeksforgeeks.org/find-whether-it-is-possible-to-finish-all-tasks-or-not-from-given-dependencies/
        System.out.println("course schedule 1: "+canFinish(2,new int[][] {{0,1}}));
        //question 2 alien dictionary
        System.out.println("alien dict order: "+findOrder(new String[]{"baa","abcd","abca","cab","cad"},5,4));
    }
}