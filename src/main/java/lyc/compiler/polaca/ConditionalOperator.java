package lyc.compiler.polaca;

import lyc.compiler.model.SymbolTableManager;

import java.util.List;
import java.util.Stack;

import static lyc.compiler.polaca.PolacaManager.conditionStack;

public class ConditionalOperator extends ListOperation {
    public void operation(SymbolTableManager symbolManager, List<String> polaca, Stack<Integer> stack, String item) {
        conditionStack.add(item);
    }
}
