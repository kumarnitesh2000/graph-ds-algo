package Extra_Question;

class NetworkQues {
    public int makeConnected(int n, int[][] connections) {
        if(n-1>connections.length){
            return -1;
        }
        DSU dsu = new DSU(n);
        boolean[] connected = new boolean[n];
        int extra_edges = 0;
        for(int i=0;i<connections.length;i++){
            int u = connections[i][0],v = connections[i][1];
            connected[u] = true;connected[v] = true;
            int pu = dsu.findPar(u),pv = dsu.findPar(v);
            if(pu==pv){
                extra_edges++;
            }else{
                dsu.unionBySize(u,v);
                n--;
            }
        }
        return n-1;
    }
}
class DSU{
    int n;
    int[] parent;
    int[] size;
    DSU(int n){
        this.n = n;
        parent = new int[n];
        size = new int[n];
        for(int i=0;i<n;i++){
            parent[i]=i;
            size[i] = 1;
        }
    }
    int findPar(int node){
        if(node==parent[node]){
            return node;
        }
        int u_p = findPar(parent[node]);
        parent[node] = u_p;
        return u_p;
    }
    void unionBySize(int node1,int node2){
        int p1 = findPar(node1);
        int p2 = findPar(node2);
        if(p1==p2)return;
        if(size[p1] > size[p2]){
            size[p1]+=size[p2];
            parent[p2] = p1;
        }else{
            size[p2]+=size[p1];
            parent[p1] = p2;
        }
    }
}
