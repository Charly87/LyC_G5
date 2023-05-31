package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Stack;

import static lyc.compiler.polaca.PolacaManager.unstackCondition;

public class BICicloOperator extends ListOperation {
    public void operation(ArrayList<String> list, Stack<Integer> stack, String item) {
        int lastPos = stack.pop() - 1;
        int nextPos = (list.size() + 3);
        list.set(lastPos,"#"+ nextPos);
        unstackCondition(lastPos + 1, nextPos);
        list.add("BI");
        list.add("#"+ stack.pop());
    }
}