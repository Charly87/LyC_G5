package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Stack;

import static lyc.compiler.polaca.PolacaManager.counter;

public class ElementInTheMiddle extends ListOperation {
    public void operation(ArrayList<String> list, Stack<Integer> stack, String item) {
        if(counter > 1) {
            String middle = list.get(list.size() - counter / 2 - 1);

            int start = list.size() - counter;
            int end = list.size() - 1;
            for (int i = end; i >= start; i--)
                list.remove(i);
            list.add(middle);
            counter = 1;
        }
    }
}
