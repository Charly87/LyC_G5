package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class EITMBody extends ListOperation {
    public void operation(ArrayList<String> list, Stack<Integer> stack, String item) {
        list.add("@value");
        list.add(":=");

        list.add("@count");
        list.add("1");
        list.add("+");
        list.add("@count");
        list.add(":=");
        list.add("@count");
        stack.add(list.size());
        list.add("#");
        list.add("CMP");
        list.add("BNE");
        list.add("#" + (list.size() + 5));

    }
}
