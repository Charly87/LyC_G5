package lyc.compiler.polaca;

import lyc.compiler.model.DataType;
import lyc.compiler.model.SymbolTableManager;

import java.util.List;
import java.util.Stack;

public class EITMStart extends ListOperation {
    public void operation(SymbolTableManager symbolManager, List<String> polaca, Stack<Integer> stack, String item) {
        polaca.add("@value");
        polaca.add(":=");
        polaca.add("1");
        polaca.add("@count");
        polaca.add(":=");

        symbolManager.add("@value", DataType.INTEGER, "?", null );
        symbolManager.add("@count", DataType.INTEGER, "?", null );
        symbolManager.addFactor("1", DataType.CTE_INTEGER);

        PolacaManager.Counter = 1;
    }
}
