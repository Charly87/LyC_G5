package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Stack;

import static lyc.compiler.polaca.PolacaManager.conditionStack;

public class ConditionalOperator extends ListOperation {
    public void operation(ArrayList<String> list, Stack<Integer> stack, String item) {
        conditionStack.add(item);
    }
}
