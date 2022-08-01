import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

class Main {
    public static void main(String[] args) {
        try {

            BufferedReader read= new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer st= new StringTokenizer(read.readLine());
            int n= Integer.parseInt(st.nextToken());
            int m= Integer.parseInt(st.nextToken());

            int[][] mpref= new int[n][n];
            for (int x= 0; x < n; x++ ) {
                st= new StringTokenizer(read.readLine());
                for (int i= 0; i < n; i++ ) {
                    mpref[x][i]= Integer.parseInt(st.nextToken());
                }
            }
            int[][] wpref= new int[n][n];
            for (int x= 0; x < n; x++ ) {
                st= new StringTokenizer(read.readLine());
                for (int i= 0; i < n; i++ ) {
                    wpref[x][i]= Integer.parseInt(st.nextToken());
                }
            }
            ArrayList<List<Integer>> fb= new ArrayList<>();
            for (int x= 0; x < n; x++ ) {
                fb.add(new ArrayList<>());
            }
            for (int x= 0; x < m; x++ ) {
                st= new StringTokenizer(read.readLine());
                int man= Integer.parseInt(st.nextToken());
                fb.get(man).add(Integer.parseInt(st.nextToken()));
            }

            PriorityQueue<Integer> unmatched= new PriorityQueue<>();
            for (int x= 0; x < n; x++ )
                unmatched.add(x);

            int[] wmatched= new int[n];
            Arrays.fill(wmatched, -1);
            int[] order= new int[n];
            boolean possible= true;

            BufferedWriter writer= new BufferedWriter(new OutputStreamWriter(System.out));

            while (unmatched.peek() != null) {
                int man= unmatched.peek();
                if (order[man] >= n) {
                    writer.write("No\n");
                    possible= false;
                    break;
                }
                boolean forbidden= false;
                int girl= mpref[man][order[man]];
                for (int i : fb.get(man)) {
                    if (i == girl) {
                        forbidden= true;
                        break;
                    }
                }
                if (forbidden) {
                    order[man]++ ;
                    continue;
                }
                if (wmatched[girl] == -1) {
                    wmatched[girl]= man;
                    order[man]++ ;
                    unmatched.poll();
                } else {
                    int comp= wmatched[girl];
                    boolean winner= false;
                    for (int i : wpref[girl]) {
                        if (i == man) {
                            winner= true;
                            break;
                        }
                        if (i == comp) {
                            winner= false;
                            break;
                        }
                    }
                    if (winner) {
                        wmatched[girl]= man;
                        unmatched.poll();
                        unmatched.add(comp);
                    }
                    order[man]++ ;
                }
            }
            if (possible) {
                writer.write("Yes\n");
                for (int x : wmatched)
                    writer.write(x + "\n");
            }
            read.close();
            writer.flush();
            writer.close();
        } catch (IOException io) {}
    }
}