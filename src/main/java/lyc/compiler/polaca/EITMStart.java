package lyc.compiler.polaca;

import java.util.List;
import java.util.Stack;

public class EITMStart extends ListOperation {
    public void operation(List<String> polaca, Stack<Integer> stack, String item) {
        polaca.add("@value");
        polaca.add(":=");
        polaca.add("@count");
        polaca.add("1");
        polaca.add(":=");

        PolacaManager.Counter = 1;
    }
}
