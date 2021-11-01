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
        int index=0;
        for(ArrayList<Integer> lis: adj_list){
            System.out.print(index+" -> ");System.out.print(lis);
            System.out.println();
            index++;
        }
    }
	public static void main(String[] args) {
	    //sample undirected graph
	    int input_n = 4,input_m = 4;
	    int[][] matrix = {{1,0},{1,2},{2,3},{3,0}};
		ArrayList<ArrayList<Integer>> adj_undirected_list = adj_list_undirected(input_n,input_m,matrix);
		print_undirected_adj_list(adj_undirected_list);
	}
}
