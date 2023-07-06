package lyc.compiler.polaca;

import java.util.List;
import java.util.Stack;

public class EITMBody extends ListOperation {
    public void operation(List<String> polaca, Stack<Integer> stack, String item) {
        polaca.add("@value");
        polaca.add(":=");

        polaca.add("@count");
        polaca.add("1");
        polaca.add("+");
        polaca.add("@count");
        polaca.add(":=");
        polaca.add("@count");
        stack.add(polaca.size());
        polaca.add("#");
        polaca.add("CMP");
        polaca.add("BNE");
        polaca.add("#" + (polaca.size() + 5));

    }
}
