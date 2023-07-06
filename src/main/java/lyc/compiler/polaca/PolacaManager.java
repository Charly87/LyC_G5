package lyc.compiler.polaca;

import lyc.compiler.model.DataType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class PolacaManager {

    protected static Stack<String> conditionStack = new Stack<String>();
    protected static ArrayList<String> list = new ArrayList<String>();
    protected static Stack<Integer> stack = new Stack<Integer>();

    public static Integer Counter;

    public boolean analizar(DataType t1,DataType t2){

        if( t1.toString().contains(t2.toString()) || t2.toString().contains(t1.toString()) )
            return true;
        else {
            throw new Error(t1 + " y " + t2 + " Error de tipo");
        }
    }

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
            case "EITMStart":
                operation = new EITMStart();
                break;
            case "EITMBodyStart":
                operation = new EITMBodyStart();
                break;
            case "EITMBody":
                operation = new EITMBody();
                break;
            case "EITMBodyEnd":
                operation = new EITMBodyEnd();
                break;
            case "EITMEnd":
                operation = new EITMEnd();
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
        System.out.println("\n");
        //list.clear();
        //stack.clear();
        //conditionStack.clear();
    }
    public ArrayList<String> getList(){
        return this.list;
    }
}