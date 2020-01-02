package OSPF;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Init {
    static int nodeNum = 0;
    static ArrayList<Integer> arr = new ArrayList();

    public Init() {
    }

    public void fileRead() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/hyunwoo/Downloads/init.txt"));
            boolean is_nodeNum = false;

            while (true) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        br.close();
                        return;
                    }

                    if (!is_nodeNum) {
                        nodeNum = Integer.parseInt(line);
                        is_nodeNum = true;
                    } else {
                        String[] tempArr = line.split("\\s");

                        for (int i = 0; i < nodeNum; ++i) {
                            arr.add(Integer.parseInt(tempArr[i]));
                        }
                    }
                }
            }
        } catch (FileNotFoundException var6) {
            System.err.println("<!FileOpen 오류>" + var6.getMessage());
            var6.printStackTrace();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }
}
