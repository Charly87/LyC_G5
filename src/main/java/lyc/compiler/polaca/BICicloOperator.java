package lyc.compiler.polaca;

import lyc.compiler.model.ListOperation;

import java.util.ArrayList;
import java.util.Stack;

public class BIOperator extends ListOperation {
    public void insert(ArrayList<String> list, Stack<Integer> stack, String item) {
        list.set(stack.pop() - 1, "#" + (list.size() + 3));
        list.add(item);
        list.add("#");
        stack.add(list.size());
    }
}