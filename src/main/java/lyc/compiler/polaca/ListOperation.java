package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Stack;

public abstract class ListOperation {
    public abstract void operation(ArrayList<String> list, Stack<Integer> stack, String item);
}
