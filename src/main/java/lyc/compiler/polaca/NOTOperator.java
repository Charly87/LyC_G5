package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Stack;

public class NOTOperator extends ListOperation {
    public void operation(ArrayList<String> list, Stack<Integer> stack, String item) {
        var branch = stack.peek() - 2;
        switch (list.get(branch)) {
            case "BLE":
                list.set(branch, "BGT");
                break;
            case "BNE":
                list.set(branch, "BEQ");
                break;
            case "BEQ":
                list.set(branch, "BNE");
                break;
            case "BGT":
                list.set(branch, "BLE");
                break;
            case "BLT":
                list.set(branch, "BGE");
                break;
            case "BGE":
                list.set(branch, "BLT");
                break;
            default:
                break;
        }
    }
}
