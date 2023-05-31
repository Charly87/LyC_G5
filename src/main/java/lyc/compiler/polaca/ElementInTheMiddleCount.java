package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Stack;

public class ElementInTheMiddleCount extends ListOperation {
    public void operation(ArrayList<String> list, Stack<Integer> stack, String item) {
        PolacaManager.counter += 2;
    }
}
