package lyc.compiler.polaca;

import java.util.List;
import java.util.Stack;

public abstract class ListOperation {
    public abstract void operation(List<String> polaca, Stack<Integer> stack, String item);
}
