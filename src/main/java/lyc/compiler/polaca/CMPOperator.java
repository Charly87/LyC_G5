package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Stack;

public class CMPOperator extends ListOperation {
    public void operation(ArrayList<String> list, Stack<Integer> stack, String item) {
        list.add("CMP");
        list.add(item);
        list.add("#");
        stack.add(list.size());
    }
}

