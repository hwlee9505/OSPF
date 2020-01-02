package OSPF;

import java.util.ArrayList;
import java.util.Scanner;

public class Control {
    ArrayList<Integer> route = new ArrayList();
    static final int maxNum = 10000000;
    int[][] weight;
    int[] distance;
    int[] priorNode;
    int[] a;
    boolean[] visit;
    int[][] nextRouter;

    public Control() {
        this.distance = new int[Init.nodeNum];
        this.priorNode = new int[Init.nodeNum];
        this.a = new int[Init.nodeNum];
        this.visit = new boolean[Init.nodeNum];
        this.nextRouter = new int[Init.nodeNum][Init.nodeNum];
    }

    public void printAll() {
        System.out.println();
        System.out.println("[노드의 수 :" + Init.nodeNum + "]");
        System.out.println();

        for (int i = 0; i < Init.nodeNum; ++i) {
            for (int j = 0; j < Init.nodeNum; ++j) {
                System.out.print((Integer) Init.arr.get(j + i * Init.nodeNum) == -1 ? i + 1 + "번) 노드에서 " + (j + 1) + "번) 노드까지의 cost가 " + Init.arr.get(j + i * Init.nodeNum) : i + 1 + "번) 노드에서 " + (j + 1) + "번) 노드까지의 cost가  " + Init.arr.get(j + i * Init.nodeNum));
                this.printState(i, j);
            }

            System.out.println();
        }

    }

    public void printState(int a, int b) {
        if ((Integer) Init.arr.get(b + a * Init.nodeNum) == 0) {
            System.out.print("  (자기 자신)");
        } else if ((Integer) Init.arr.get(b + a * Init.nodeNum) == -1) {
            System.out.print("  (직접 연결되어 있지 않음)");
        } else {
            System.out.print("  (연결됨)");
        }

        System.out.println();
    }

    public void change() {
        this.weight = new int[Init.nodeNum][Init.nodeNum];
        System.out.print("      |");

        int i;
        for (i = 0; i < this.weight.length; ++i) {
            System.out.print("node" + (i + 1) + "|");
        }

        System.out.println();
        System.out.println("------------------------------------------------------");

        for (i = 0; i < this.weight.length; ++i) {
            System.out.print("node" + (i + 1) + " | ");

            for (int j = 0; j < this.weight[0].length; ++j) {
                if ((Integer) Init.arr.get(j + i * Init.nodeNum) == -1) {
                    this.weight[i][j] = 10000000;
                } else {
                    this.weight[i][j] = (Integer) Init.arr.get(j + i * Init.nodeNum);
                }

                System.out.printf("%2d |  ", Init.arr.get(j + i * Init.nodeNum));
            }

            System.out.println();
            System.out.println("------------------------------------------------------");
        }

    }

    public int min(int[] a) {
        int min = 10000000;
        int minIndex = 0;

        for (int i = 0; i < a.length; ++i) {
            if (!this.visit[i] && a[i] != 0 && a[i] < 10000000 && a[i] < min) {
                min = a[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    public void dijkstra(int a) {
        int index;
        for (index = 0; index < this.distance.length; ++index) {
            this.distance[index] = this.weight[a][index];
            this.priorNode[index] = a;
        }

        this.visit[a] = true;

        for (index = this.weight.length; index != 0; --index) {
            int minIndex = this.min(this.distance);
            this.visit[minIndex] = true;

            for (int i = 0; i < this.distance.length; ++i) {
                if (this.weight[minIndex][i] + this.distance[minIndex] < this.distance[i]) {
                    this.distance[i] = this.weight[minIndex][i] + this.distance[minIndex];
                    this.priorNode[i] = minIndex;
                }
            }
        }

    }

    public void printCost(int a) {
        int var10001 = this.distance[a];
        System.out.println("Cost : " + var10001);
    }

    public void pathTrace(int[] priorNode, int end) {
        int temp = priorNode[end];
        this.route.add(0, end);
        if (temp != end) {
            this.pathTrace(priorNode, temp);
        }
    }

    public void printTrace() {
        for (int i = 0; i < this.route.size(); ++i) {
            System.out.print(i == 0 ? (Integer) this.route.get(i) + 1 : " - " + ((Integer) this.route.get(i) + 1));
        }

    }

    public void io() {
        System.out.println("출발라우터 입력 : ");
        Scanner scan = new Scanner(System.in);
        OSPF.start = scan.nextInt();
        System.out.println("도착라우터 입력 : ");
        OSPF.end = scan.nextInt();
    }

    public void initVist() {
        for (int i = 0; i < this.visit.length; ++i) {
            this.visit[i] = false;
        }

    }

    public void routingTable() {
        System.out.println();

        int i;
        int j;
        for (i = 0; i < this.distance.length; ++i) {
            this.initVist();
            this.dijkstra(i);

            for (j = 0; j < this.priorNode.length; ++j) {
                this.nextRouter[j][i] = this.priorNode[j] + 1;
            }
        }

        for (i = 0; i < Init.nodeNum; ++i) {
            System.out.println();
            System.out.println("------------------------------------------------------------");
            System.out.println("|                Forwarding table for R" + (i + 1) + "                   |");
            System.out.println("|----------------------------------------------------------|");
            System.out.println("|    Destionation            Next                Cost      |");
            System.out.println("|       node                router                         |");
            System.out.println("|----------------------------------------------------------|");

            for (j = 0; j < Init.nodeNum; ++j) {
                this.initVist();
                this.dijkstra(i);
                if (this.weight[i][j] == 10000000) {
                    System.out.println("|        " + (j + 1) + "                    " + this.nextRouter[i][j] + "                   " + this.distance[j] + "        |");
                } else if (i == j) {
                    System.out.println("|        -                    -                   -        |");
                } else {
                    System.out.println("|        " + (j + 1) + "                    -                   " + this.distance[j] + "        |");
                }
            }

            System.out.println("------------------------------------------------------------");
        }

    }
}
