package OSPF;

public class OSPF {
    static int start = 0;
    static int end = 0;

    public OSPF() {
    }

    public static void main(String[] args) {
        (new Init()).fileRead();
        Control ctrl = new Control();
        ctrl.io();
        ctrl.printAll();
        ctrl.change();
        ctrl.dijkstra(start - 1);
        ctrl.printCost(end - 1);
        ctrl.pathTrace(ctrl.priorNode, end - 1);
        System.out.print("Path : ");
        ctrl.printTrace();
        ctrl.routingTable();
    }
}
