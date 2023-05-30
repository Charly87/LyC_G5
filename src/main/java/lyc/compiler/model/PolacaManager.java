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
            case "COMPONENT":
                list.add("_aux"+ counter);
                allequal.add("_aux"+ counter);
                list.add(":=");
                counter++;
                break;
            case "ALLEQUAL":
//                System.out.println("tope de pila "+allequal.peek());
//                System.out.println("tam "+allequal.size());
                while (allequal.size()!=0){
                    list.add(allequal.pop());
                    String aux = allequal.pop();
                    if (allequal.size()!=0){
                        list.add(allequal.pop());
                        allequal.add(aux);
                    }else {
                        list.add(aux);
                    }
                    list.add("CMP");
                    list.add("BNE");
                    list.add("#");
                    stack.add(list.size());
                    conditionStack.add("AND");
                }
                counter = counter / 2;
                if (counter == 1){
                    list.set(stack.pop()-1,"#"+(list.size()+4));
                }else
                {
                    while (counter != 1){
                        list.set(stack.pop()-1,"#"+(list.size()+4));
                        if(conditionStack.pop()=="AND"){
                            list.set(stack.pop()-1,"#"+(list.size()+4));
                        }
                        counter--;
                    }
                }
                list.add("TRUE");
                list.add("BI");
                list.add("#"+(list.size()+3));
                list.add("FALSE");
                counter = 1;
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