package lyc.compiler.polaca;

import java.util.ArrayList;
import java.util.Stack;

public class PolacaManager {

    protected static Stack<String> conditionStack = new Stack<String>();
    protected static ArrayList<String> list = new ArrayList<String>();
    protected static Stack<Integer> stack = new Stack<Integer>();
    protected static int counter = 1;

    public void insert(String item) {
        ListOperation operation;
        switch(item) {
            case "BLE":
            case "BNE":
            case "BEQ":
            case "BGT":
            case "BLT":
            case "BGE":
                operation = new CMPOperator();
                break;
            case "ET":
                operation = new ETOperator();
                break;
            case "BI":
                operation = new BIOperator();
                break;
            case "BICICLO":
                operation = new BICicloOperator();
                break;
            case "AND":
            case "OR":
                operation = new ConditionalOperator();
                break;
            case "NOT":
                operation = new NOTOperator();
                break;
            case "ElementInTheMiddle":
                operation = new ElementInTheMiddle();
                break;
            case "ElementInTheMiddleCount":
                operation = new ElementInTheMiddleCount();
                break;
            default:
                operation = new DefaultOperation();
                break;
        }

        operation.operation(list, stack, item);
    }

     public void unstack()
    {
        int lastPos;
        list.set(lastPos = stack.pop() - 1,"#" + (list.size()+1));
        unstackCondition(lastPos + 1, (list.size() + 1));
    }

    public static void unstackCondition(int lastPos, int nextPos)
    {
        if(conditionStack.size() > 0 ){
            String operator = conditionStack.pop();
            if(operator == "AND"){
                list.set(stack.pop() - 1, "#" + nextPos);
            }

            if(operator == "OR" ){
                list.set(stack.pop() - 1, "#" + lastPos);
            }
        }
    }
    public void show(){
            System.out.println("*****Polaca Inversa*****");
        int i = 1;
        for(String item : list){
            System.out.println(i + ") " + item);
            i++;
        }
        list.clear();
        stack.clear();
        conditionStack.clear();
    }
    public ArrayList<String> getList(){
        return this.list;
    }
}