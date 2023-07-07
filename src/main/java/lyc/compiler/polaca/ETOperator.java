package lyc.compiler.polaca;

import lyc.compiler.model.SymbolTableManager;

import java.util.List;
import java.util.Stack;

public class ETOperator extends ListOperation {
    public void operation(SymbolTableManager symbolManager, List<String> polaca, Stack<Integer> stack, String item) {
        polaca.add(item);
        stack.add(polaca.size());
    }
}