package lyc.compiler.polaca;

import lyc.compiler.model.DataType;
import lyc.compiler.model.SymbolTableManager;

import java.util.List;
import java.util.Stack;

public class EITMEnd extends ListOperation {
    public void operation(SymbolTableManager symbolManager, List<String> polaca, Stack<Integer> stack, String item) {
        polaca.add("@value");

        while(!stack.isEmpty())
            polaca.set(stack.pop(), PolacaManager.Counter.toString());

        symbolManager.addFactor(PolacaManager.Counter.toString(), DataType.CTE_INTEGER);

        PolacaManager.Counter =0;
    }
}
