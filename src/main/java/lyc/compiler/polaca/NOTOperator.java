package lyc.compiler.polaca;

import lyc.compiler.model.SymbolTableManager;

import java.util.List;
import java.util.Stack;

public class NOTOperator extends ListOperation {
    public void operation(SymbolTableManager symbolManager, List<String> polaca, Stack<Integer> stack, String item) {
        var branch = stack.peek() - 2;
        switch (polaca.get(branch)) {
            case "BLE":
                polaca.set(branch, "BGT");
                break;
            case "BNE":
                polaca.set(branch, "BEQ");
                break;
            case "BEQ":
                polaca.set(branch, "BNE");
                break;
            case "BGT":
                polaca.set(branch, "BLE");
                break;
            case "BLT":
                polaca.set(branch, "BGE");
                break;
            case "BGE":
                polaca.set(branch, "BLT");
                break;
            default:
                break;
        }
    }
}
