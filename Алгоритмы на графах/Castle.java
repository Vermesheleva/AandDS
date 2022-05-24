import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Castle {
    private List[] list;
    private int n;
    private int m;
    private int nv;
    private int[] is_visited;
    private Queue<Integer> queue;
    private int max_square;
    private int numOfComponents;
    private int new_max_square;
    private int prev_component_square;
    private int next_component_square;

    Castle(int a, int b){
        this.n = a;
        this.m = b;
        this.nv = a * b;
        this.is_visited = new int[nv];
        this.list = new List[n * m];
        this.queue = new LinkedList<>();
        for(int i = 0; i < nv; i++){
            list[i] = new ArrayList<Integer>();
        }
        max_square = 0;
        numOfComponents = 1;
        new_max_square = 0;
        prev_component_square = 0;
        next_component_square = 0;

    }

    void fillLists(StreamTokenizer st) throws IOException{
        int val;
        int v = 1;

        for(int i = 0; i < nv; i++){
                st.nextToken();
                val = (int) st.nval;

                if (val == 0) {
                    list[v - n - 1].add(v);
                    list[v + n - 1].add(v);
                    list[v - 2].add(v);
                    list[v].add(v);
                    v++;
                    continue;
                }
                if (val == 15) {
                    v++;
                    continue;
                }
                val = 15 - val;

                if (val % 2 == 1) {
                    list[v - 2].add(v);
                    val--;
                    if(val == 0){
                        v++;
                        continue;
                    }
                }
                if (val >= 8) {
                    val -= 8;
                    list[v + n - 1].add(v);
                    if (val == 0) {
                        v++;
                        continue;
                    }

                } if (val == 2) {
                    list[v - n - 1].add(v);
                    v++;
                    continue;
                } if (val == 4) {
                    list[v].add(v);
                    v++;
                    continue;
                } if (val == 6) {
                    list[v - n - 1].add(v);
                    list[v].add(v);
                    v++;
                    continue;
                }
                v++;
        }

    }

    public int BreadthFirstSearch(int start){
        int num = 0;
        queue.add(start);
        is_visited[start] = 1;
        int p;

        while(!queue.isEmpty()){
            p = queue.poll();
            num++;

                for (int j = 0; j < list[p].size(); j++) {
                    if (is_visited[(int) list[p].get(j) - 1] == 0) {
                        is_visited[(int) list[p].get(j) - 1] = 1;
                        queue.add((int) list[p].get(j) - 1);
                    }
                }
        }
        return num;


    }

    public void solve() throws IOException{
        FileWriter fw = new FileWriter("out.txt");

        max_square = BreadthFirstSearch(0);
        next_component_square = max_square;
        new_max_square = max_square;

        for(int i = 0; i < nv; i++){
            if(is_visited[i] == 0){
                numOfComponents++;
                prev_component_square = next_component_square;
                next_component_square = this.BreadthFirstSearch(i);
                if(next_component_square > max_square){
                    max_square = next_component_square;
                }
                if(next_component_square + prev_component_square > new_max_square){
                    new_max_square = next_component_square + prev_component_square;
                }
            }
        }
        fw.write(numOfComponents + "\n");
        fw.write(max_square + "\n");
        fw.write(new_max_square + "");
        fw.close();
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("in"));
        StreamTokenizer st = new StreamTokenizer(br);
        st.nextToken();
        int m = (int) st.nval;
        st.nextToken();
        int n = (int) st.nval;

        Castle obj = new Castle(n, m);
        obj.fillLists(st);
        obj.solve();


    }


}
