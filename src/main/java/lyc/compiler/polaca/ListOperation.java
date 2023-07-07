package lyc.compiler.polaca;

import lyc.compiler.model.SymbolTableManager;

import java.util.List;
import java.util.Stack;

public abstract class ListOperation {
    public abstract void operation(SymbolTableManager symbolManager, List<String> polaca, Stack<Integer> stack, String item);
}
