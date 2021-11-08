import java.util.*;

public class DayFive {
    public static int index_of(int x,int y,int col){
        return x*col+y;
    }
    // number of island 2 problem: https://www.lintcode.com/problem/434/
    /**
     * @param n:         An integer
     * @param m:         An integer
     * @param operators: an array of point
     * @return: an integer array
     */
    public static List<Integer> numIslands2(int n, int m, Point[] operators) {
        List<Integer> result = new ArrayList<Integer>();
        DSU dsu = new DSU(n*m);
        int[][] ocean = new int[n][m];
        int islands = 0;
        int[] dx = {-1,1,0,0};
        int[] dy = {0,0,-1,1};
        for(Point p: operators){
            if(ocean[p.x][p.y]==1){
                result.add(islands);
                continue;
            }
            ocean[p.x][p.y] = 1;
            islands++;
            for(int i=0;i<4;i++){
                int new_x = dx[i]+p.x;
                int new_y = dy[i]+p.y;
                if(new_x>=0 && new_x<n && new_y>=0 && new_y<m && ocean[new_x][new_y]==1){
                    int index_1 = index_of(new_x,new_y,m);
                    int index_2 = index_of(p.x,p.y,m);
                    if(dsu.findPar(index_1)!=dsu.findPar(index_2)){
                        dsu.unionBySize(index_1,index_2);
                        islands--;
                    }
                }
            }
            result.add(islands);
        }
        return result;
    }
    public static void mergeAllComponents(int nodes,int[][] edges){
        DSU dsu = new DSU(nodes);
        ArrayList<Point> extraRoads = new ArrayList<Point>();
        // store the extra roads
        for(int i=0;i<edges.length;i++){
            int u = edges[i][0],v = edges[i][1];
            if(dsu.findPar(u)==dsu.findPar(v)){
                extraRoads.add(new Point(u,v));
            }else{
                dsu.unionBySize(u,v);
            }
        }
        // count the components and store the ultimate parent of all the components
        HashSet<Integer> hs = new HashSet<Integer>();
        for(int i=0;i<nodes;i++){
            hs.add(dsu.findPar(i));
        }
        int x = hs.size();
        Integer[] ultimateParents = hs.toArray(new Integer[x]);
        // outupt 
        int j=0;
        for(int i=1;i<=x-1;i++){
            System.out.print("new road: ("+ultimateParents[i-1]+","+ultimateParents[i]+") old road: ("+extraRoads.get(j).x+","+extraRoads.get(j).y+")");
            System.out.println();
            j++;
        }
    } 
    public static void main(String[] args) {
        // number of island 2 problem
        Point[] points = new Point[4];
        points[0] = new Point(0,0);points[1] = new Point(0,1);points[2] = new Point(2,2);points[3] = new Point(2,1);
        System.out.println("Number of Island2 case 1: "+numIslands2(3,3,points));
        System.out.println("Number of Island2 case 2: "+numIslands2(1,1,new Point[]{}));
        // question on codeforces for dsu https://codeforces.com/problemset/problem/25/D
        System.out.println("case 1 with 2 components");
        mergeAllComponents(7,new int[][]{{0,1},{1,2},{2,0},{3,4},{4,5},{5,6}});
        System.out.println("case 2 with 3 components");
        mergeAllComponents(9,new int[][]{{0,1},{0,2},{3,4},{3,5},{4,5},{6,7},{7,8},{6,8}});
    }
}


class Point {
    int x;
    int y;

    Point() {
        x = 0;
        y = 0;
    }

    Point(int a, int b) {
        x = a;
        y = b;
    }
}

class DSU {
    int n;
    int[] parent, size, rank;

    DSU(int n) {
        // make set
        this.n = n;
        parent = new int[n];
        size = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
            rank[i] = 0;
        }
    }

    int findPar(int node) {
        if (parent[node] == node) {
            return node;
        }
        int ultimate_parent = findPar(parent[node]);
        // path compression O(log n)
        parent[node] = ultimate_parent;
        return ultimate_parent;
    }

    void unionBySize(int u, int v) {
        int pv = parent[v];
        int pu = parent[u];
        if (pu == pv) {
            return;
        }
        if (size[pu] > size[pv]) {
            parent[pv] = pu;
            size[pu] += size[pv];
        } else {
            parent[pu] = pv;
            size[pv] += size[pu];
        }
    }

    void unionByRank(int u, int v) {
        int pv = parent[v];
        int pu = parent[u];
        if (pu == pv) {
            return;
        }
        if (rank[pu] == rank[pv]) {
            rank[pu]++;
            parent[pv] = pu;
        } else if (rank[pu] > rank[pv]) {
            parent[pv] = pu;
        } else {
            parent[pu] = pv;
        }
    }
}
