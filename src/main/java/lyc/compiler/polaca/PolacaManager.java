package lyc.compiler.polaca;

import lyc.compiler.model.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolacaManager {

    protected static Stack<String> conditionStack = new Stack<String>();
    protected static List<String> polaca = new ArrayList<>();
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

        operation.operation(polaca, stack, item);
    }

     public void unstack()
    {
        int lastPos;
        polaca.set(lastPos = stack.pop() - 1,"#" + (polaca.size()+1));
        unstackCondition(lastPos + 1, (polaca.size() + 1));
    }

    public static void unstackCondition(int lastPos, int nextPos)
    {
        if(conditionStack.size() > 0 ){
            String operator = conditionStack.pop();
            if(operator == "AND"){
                polaca.set(stack.pop() - 1, "#" + nextPos);
            }

            if(operator == "OR" ){
                polaca.set(stack.pop() - 1, "#" + lastPos);
            }
        }
    }
    public void show(){
            System.out.println("*****Polaca Inversa*****");
        int i = 1;
        for(String item : polaca){
            System.out.println(i + ") " + item);
            i++;
        }
        System.out.println("\n");
        //list.clear();
        //stack.clear();
        //conditionStack.clear();
    }
    public List<String> getPolacaList(){
        return this.polaca;
    }
}