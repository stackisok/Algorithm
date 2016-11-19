package Floyd;

import java.util.ArrayList;
import java.util.List;

/**
* Created by long on 2016/11/13.
 *
 * @author wcj
*/
public class FloydInGraph {

    private static int INF = Integer.MAX_VALUE;
    //dist[i][j]=INF<==>no edges between i and j
    private int[][] dist;
    //the distance between i and j.At first,dist[i][j] is the weight of edge [i,j]
    private int[][] path;
    private List<Integer> result = new ArrayList<Integer>();

    public static void main(String[] args) {
        FloydInGraph graph = new FloydInGraph(5);
        int[][] matrix = {
                {INF, 30, INF, 10, 50},
                {INF, INF, 60, INF, INF},
                {INF, INF, INF, INF, INF},
                {INF, INF, INF, INF, 30},
                {50, INF, 40, INF, INF},
        };
        int begin = 0;
        int end = 2;
        graph.findCheapestPath(begin, end, matrix);
        List<Integer> list = graph.result;
        System.out.println(begin + " to " + end + ",the cheapest path is:");
        System.out.println(list.toString());
        System.out.println(graph.dist[begin][end]);
    }

    public void findCheapestPath(int begin, int end, int[][] matrix) {
        floyd(matrix);
        result.add(begin);
        findPath(begin, end);
        result.add(end);
    }

    public void findPath(int i, int j) {
        int k = path[i][j];
        if (k == -1) return;
        findPath(i, k);
        result.add(k);
        findPath(k, j);
    }

    public void floyd(int[][] matrix) {
        //path存放路径
        //dist存放距离
        int size = matrix.length;
        //初始化
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                dist[i][j] = matrix[i][j];
                path[i][j] = -1;
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if(dist[j][i]!=INF&& dist[i][k]!=INF&&dist[j][i]+dist[i][k]<dist[j][k]){
                        dist[j][k]=dist[j][i]+dist[i][k];
                        path[j][k]=i;

                    }

                }
            }
        }

    }

    public FloydInGraph(int size) {
        this.path = new int[size][size];
        this.dist = new int[size][size];
    }


}
