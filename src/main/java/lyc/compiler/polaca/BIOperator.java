package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Stack;

public class BIOperator extends ListOperation {
    public void operation(ArrayList<String> list, Stack<Integer> stack, String item) {
        list.set(stack.pop() - 1, "#" + (list.size() + 3));
        list.add(item);
        list.add("#");
        stack.add(list.size());
    }
}