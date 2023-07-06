package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class EITMStart extends ListOperation {
    public void operation(ArrayList<String> list, Stack<Integer> stack, String item) {
        list.add("@value");
        list.add(":=");
        list.add("@count");
        list.add("1");
        list.add(":=");

        PolacaManager.Counter = 1;
    }
}
