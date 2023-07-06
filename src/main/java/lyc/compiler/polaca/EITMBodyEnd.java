package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class EITMBodyEnd extends ListOperation {
    public void operation(ArrayList<String> list, Stack<Integer> stack, String item) {
        list.add("@value");
        list.add(":=");

        PolacaManager.Counter++;
    }
}
