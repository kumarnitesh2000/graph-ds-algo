package Extra_Question;
import java.util.*;
class KStopsQues {
    public ArrayList<ArrayList<Pr>> adj_list_weighted_directed(int n,int m,int[][] connections){
        ArrayList<ArrayList<Pr>> list = new ArrayList<>();
        // n is vertices
        for(int i=0;i<n;i++){
            list.add(new ArrayList<Pr>());   
        }
        // m is edges
        for(int i=0;i<m;i++){
            int u = connections[i][0],v = connections[i][1];
            int w = connections[i][2];
            list.get(u).add(new Pr(v,w));
        }
        return list;
    }
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        if(src==dst)return 0;
        ArrayList<ArrayList<Pr>> adj_list = adj_list_weighted_directed(n,flights.length,flights);
        PriorityQueue<Node> pq = new PriorityQueue<>((a,b) -> a.d-b.d);
        int[] dis = new int[n];Arrays.fill(dis,Integer.MAX_VALUE);dis[src] = 0;
        int[] stops = new int[n];Arrays.fill(stops,Integer.MAX_VALUE);stops[src] = 0;
        pq.add(new Node(src,0,0));
        
        //pq traverse
        while(!pq.isEmpty()){
            Node poll = pq.poll();
            int node = poll.n,stop = poll.s,distance = poll.d;
            Iterator<Pr> itr = adj_list.get(node).listIterator();
            if(node==dst)return distance;
            if(stop > k)continue;
            while(itr.hasNext()){
                Pr neigh = itr.next();
                int v = neigh.node,wt = neigh.weight;
                if((dis[v] > distance+wt)){
                    dis[v] = distance+wt;
                    //System.out.println(distance+" "+wt);
                    pq.add(new Node(v,dis[v],stop+1));
                }else if(stop < stops[v]){
                    pq.add(new Node(v,distance+wt,stop+1));
                }
                stops[v] = stop;
            }
        }
        //for(int i=0;i<dis.length;i++)System.out.println(dis[i]+",");
        return dis[dst]==Integer.MAX_VALUE ? -1 : dis[dst];
    }
}

class Node{
    int s,d,n;
    Node(int n,int d,int s){
        this.s = s;
        this.n = n;
        this.d = d;
    }
}

class Pr{
    int node;
    int weight;
    Pr(int node,int weight){
        this.node = node;
        this.weight = weight;
    }
    public String toString(){
        return "("+node+","+weight+")";
    }
}

