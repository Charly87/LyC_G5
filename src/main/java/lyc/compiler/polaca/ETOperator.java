package lyc.compiler.polaca;

import java.util.List;
import java.util.Stack;

public class ETOperator extends ListOperation {
    public void operation(List<String> polaca, Stack<Integer> stack, String item) {
        polaca.add(item);
        stack.add(polaca.size());
    }
}