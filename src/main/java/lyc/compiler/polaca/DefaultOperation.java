package lyc.compiler.polaca;

import java.util.List;
import java.util.Stack;

public class DefaultOperation extends ListOperation {
    public void operation(List<String> polaca, Stack<Integer> stack, String item) {
        polaca.add(item);
    }
}
