package lyc.compiler.polaca;

import lyc.compiler.model.SymbolTableManager;

import java.util.List;
import java.util.Stack;

import static lyc.compiler.polaca.PolacaManager.unstackCondition;

public class BICicloOperator extends ListOperation {
    public void operation(SymbolTableManager symbolManager, List<String> polaca, Stack<Integer> stack, String item) {
        int lastPos = stack.pop() - 1;
        int nextPos = (polaca.size() + 3);
        polaca.set(lastPos,"#"+ nextPos);
        unstackCondition(lastPos + 1, nextPos);
        polaca.add("BI");
        polaca.add("#"+ stack.pop());
    }
}