package lyc.compiler.model;

import java.util.ArrayList;
import java.util.Stack;

public class PolacaManager extends ListOperation{
    @Override
    public void insert(ArrayList<String> list, Stack<Integer> stack, String item) {
    }
//            case "AND":
//                conditionStack.add(item);
//                break;
//            case "OR":
//                conditionStack.add(item);
//                break;
//            case "NOT":
//
//                break;
//            case "ElementInTheMiddle":
//                break;
//            case "ElementInTheMiddleCount":
//                counter += 2;
//                break;
//            default:
//                list.add(item);
//                break;


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