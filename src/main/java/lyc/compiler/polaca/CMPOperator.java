package lyc.compiler.polaca;

import java.util.List;
import java.util.Stack;

public class CMPOperator extends ListOperation {
    public void operation(List<String> polaca, Stack<Integer> stack, String item) {
        polaca.add("CMP");
        polaca.add(item);
        polaca.add("#");
        stack.add(polaca.size());
    }
}

