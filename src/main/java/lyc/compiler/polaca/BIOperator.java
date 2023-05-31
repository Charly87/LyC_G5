package lyc.compiler.polaca;

import lyc.compiler.model.ListOperation;

import java.util.ArrayList;
import java.util.Stack;

public class ETOperator extends ListOperation {
    public void insert(ArrayList<String> list, Stack<Integer> stack, String item) {
        list.add(item);
        stack.add(list.size());
    }
}