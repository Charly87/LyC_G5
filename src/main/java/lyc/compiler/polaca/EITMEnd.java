package lyc.compiler.polaca;

import java.util.List;
import java.util.Stack;

public class EITMEnd extends ListOperation {
    public void operation(List<String> polaca, Stack<Integer> stack, String item) {
        polaca.add("@value");

        while(!stack.isEmpty())
            polaca.set(stack.pop(), PolacaManager.Counter.toString());

        PolacaManager.Counter =0;
    }
}
