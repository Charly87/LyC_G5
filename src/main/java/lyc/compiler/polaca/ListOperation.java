package lyc.compiler.model;

import java.util.ArrayList;
import java.util.Stack;

public abstract class ListOperation {

    protected static Stack<String> conditionStack = new Stack<String>();
    protected static ArrayList<String> list = new ArrayList<String>();
    protected static Stack<Integer> stack = new Stack<Integer>();
    protected int counter = 1;
    public abstract void insert(ArrayList<String> list, Stack<Integer> stack, String item);
}
