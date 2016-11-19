package Dijkstra;

import java.util.Arrays;
import java.util.Scanner;

/**
* Created by long on 2016/11/12.
 *
 *
 *
 *
 * @author wcj
*/
public class Dijkstra {




        /**
         * @param args
         */
    /*
     * 求从0点到所有点的最短路径
     * 每一轮"Find shrotest"都是再找距离0点最近的点v，顾可以知道暂时从0到v的最短距离就是dist[v]，因为如果还有更近的距离，那么v就不是距离0最近的点
     * 到找到最近点v后，都要以v为过度点，来比较0->v->j和原来记录的路径哪个更近，从而以刷新dist[]
     * 当假设除了0点其他，都当过v点的所有情况后，dist[j]数组保留下来的就是从0到j的最短路径
     */
        final static int MAXN = 100;
        final static int BigNum = 10000000;
        static Scanner scan = new Scanner(System.in);
        public static void main(String[] args) {
            // TODO Auto-generated method stub
            int[][] graph = new int[MAXN][MAXN];//The Adjacency matrix of the graph
            int[] dist = new int[MAXN];//The shortest distence between 0 and N
            boolean[] vis= new boolean[MAXN];//Sign the point which is visited
            int n,m;//n stands for theamount of positions;m stands for the path
            n = scan.nextInt();
            m = scan.nextInt();
            Arrays.fill(vis, false);
            for(int i=0;i<n;i++)
                for(int j=0;j<n;j++)
                    if(i==j)
                        graph[i][j] = 0;
                    else
                        graph[i][j] = BigNum;
            int pos1,pos2;
            for(int i=0;i<m;i++)    {//Set the date
                pos1 = scan.nextInt();
                pos2 = scan.nextInt();
                graph[pos1][pos2] = scan.nextInt();
            }
            for(int i=0;i<n;i++)    //Set the dist[]
                dist[i] = graph[0][i];
            vis[0] = true;int min,v = 0;
//            for(int i=0;i<n-1;i++)    {//Check n-1 times
//                min = BigNum;
//                for(int j=0;j<n;j++)    {//Find shortest
//                    if(vis[j]!= true && dist[j]<min)    {//If the point is not visited and the distence between 0 and j is smallest mark the point j
//                        min = dist[j];
//                        v = j;
//                    }
//                    vis[v] = true;        //Sign the point v to be visited
//                }
//                for(int j=0;j<n;j++)    {//Refresh the dist[]
//                    if(vis[j] != true && dist[j]>dist[v]+graph[v][j])    {//when distence is shorter when pass the point v refresh the dist
//                        dist[j] = dist[v] + graph[v][j];
//                    }
//                }
//            }
            findShortPath(graph,dist,vis,0);

        }


    static void findShortPath( int[][] graph,int[] dest,boolean[] vis,int v0){
        //graph传入的图的信息
        //dest记录v0到点最短的距离
        //vis记录该点是否找到了最短距离
        //v0开始的点
        int size=5;
        int p[]=new int[size];
        int min=MAXN,k=0;
        for(int i=0;i<size;i++){
            dest[i]=graph[v0][i];
            vis[i]=false;
            p[i]=0;
        }
        vis[v0]=true;
        p[v0]=0;
        dest[v0]=0;
        for(int i =0;i<size;i++){
            min=MAXN;
            for(int j=0;j<size;j++){
                if(!vis[j]&&dest[j]<min){
                    k=j;
                    min=dest[j];
                }
            }
            vis[k]=true;

            for(int j=0;j<size;j++){
                //对现有的数组进行修正
                if(!vis[j]&&dest[k]+graph[k][j]<dest[j]){
                    dest[j]=dest[k]+graph[k][j];
                    p[j]=k;
                }

            }



        }
        for(int w=0;w<size;w++)    {
            System.out.println("0->"+w+":"+dest[w]+""+p[w]);
        }



    }

/*
Test Date:
5 7
0 1 3
0 3 8
1 2 5
1 4 4
2 3 4
2 4 7
3 4 2
Out put:
0->1:3
0->2:8
0->3:8
0->4:7

Test Date:
5 7
0 1 10
0 3 30
0 4 100
1 2 50
2 4 10
3 2 20
3 4 60
Out put:

0->0:00
0->1:100
0->2:503
0->3:300
0->4:602
*/
}
