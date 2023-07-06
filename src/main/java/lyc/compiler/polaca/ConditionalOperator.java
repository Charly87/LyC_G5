package lyc.compiler.polaca;

import java.util.List;
import java.util.Stack;

import static lyc.compiler.polaca.PolacaManager.conditionStack;

public class ConditionalOperator extends ListOperation {
    public void operation(List<String> polaca, Stack<Integer> stack, String item) {
        conditionStack.add(item);
    }
}
