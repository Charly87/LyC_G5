package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class EITMEnd extends ListOperation {
    public void operation(ArrayList<String> list, Stack<Integer> stack, String item) {
        list.add("@value");

        while(!stack.isEmpty())
            list.set(stack.pop(), PolacaManager.Counter.toString());

        PolacaManager.Counter =0;
    }
}
