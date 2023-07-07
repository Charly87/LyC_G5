package lyc.compiler.polaca;

import lyc.compiler.model.SymbolTableManager;

import java.util.List;
import java.util.Stack;

public class BIOperator extends ListOperation {
    public void operation(SymbolTableManager symbolManager, List<String> polaca, Stack<Integer> stack, String item) {
        polaca.set(stack.pop() - 1, "#" + (polaca.size() + 3));
        polaca.add(item);
        polaca.add("#");
        stack.add(polaca.size());
    }
}