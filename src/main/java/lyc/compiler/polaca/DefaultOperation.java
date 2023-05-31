package lyc.compiler.polaca;

import lyc.compiler.model.ListOperation;

import java.util.ArrayList;
import java.util.Stack;

public class ConditionalOperator extends ListOperation {
    public void insert(ArrayList<String> list, Stack<Integer> stack, String item) {
        conditionStack.add(item);
    }
}
