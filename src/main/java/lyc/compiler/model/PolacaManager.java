package lyc.compiler.model;

import java.util.ArrayList;
import java.util.Stack;

public class PolacaManager {
    private ArrayList<String> list = new ArrayList<String>();
    private Stack<Integer> stack = new Stack<Integer>();
    private Stack<String> conditionStack = new Stack<String>();
    private int counter = 1;
    private Stack<String> allequal = new Stack<String>();
    public void insert(String item) {
        switch (item) {
            case "BLE":
            case "BNE":
            case "BEQ":
            case "BGT":
            case "BLT":
            case "BGE":
                list.add("CMP");
                list.add(item);
                list.add("#");
                stack.add(list.size());
                break;
            case "ET":
                list.add(item);
                stack.add(list.size());
                break;
            case "BI":
                list.set(stack.pop()-1,"#" + (list.size() + 3));
                list.add(item);
                list.add("#");
                stack.add(list.size());
                break;
            case "BICICLO":
                int lastPos = stack.pop() - 1;
                int nextPos = (list.size() + 3);
                list.set(lastPos,"#"+ nextPos);
                unstackCondition(lastPos + 1, nextPos);
                list.add("BI");
                list.add("#"+ stack.pop());
                break;
            case "AND":
                conditionStack.add(item);
                break;
            case "OR":
                conditionStack.add(item);
                break;
            case "NOT":
                var branch = stack.peek() - 2;
                switch (list.get(branch)) {
                    case "BLE":
                        list.set(branch, "BGT");
                        break;
                    case "BNE":
                        list.set(branch, "BEQ");
                        break;
                    case "BEQ":
                        list.set(branch, "BNE");
                        break;
                    case "BGT":
                        list.set(branch, "BLE");
                        break;
                    case "BLT":
                        list.set(branch, "BGE");
                        break;
                    case "BGE":
                        list.set(branch, "BLT");
                        break;
                    default:
                        break;
                }
                break;
            case "ElementInTheMiddle":
                if(counter > 1) {
                    String middle = list.get(list.size() - counter / 2 - 1);

                    int start = list.size() - counter;
                    int end = list.size() - 1;
                    for (int i = end; i >= start; i--)
                        list.remove(i);
                    list.add(middle);
                    counter = 1;
                }

                break;
            case "ElementInTheMiddleCount":
                counter += 2;
                break;
            default:
                list.add(item);
                break;
        }
    }
     public void unstack()
    {
        int lastPos;
        list.set(lastPos = stack.pop() - 1,"#" + (list.size()+1));
        unstackCondition(lastPos + 1, (list.size() + 1));
    }

    public void unstackCondition(int lastPos, int nextPos)
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
            //System.out.print(+ i + "|" +item + "  ");
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