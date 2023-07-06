package lyc.compiler.assembler;
import lyc.compiler.model.DataType;
import lyc.compiler.model.Symbol;

import java.util.*;

public class AssemblerManager {
        List<String> assembler = new ArrayList<>();
        public void generate(List<Symbol> symbolTable , List<String> polaca){
            assembler.add(".MODEL LARGE");
            assembler.add(".386");
            assembler.add(".STACK 200h\n");

            assembler.add(".DATA\n");
            assembler.addAll(this.generateData(symbolTable));

            assembler.add("\n.CODE");
            assembler.add("\nMOV AX, @DATA");
            assembler.add("MOV DS, AX");
            assembler.add("MOV ES, AX\n");

            assembler.addAll(this.generateCode(polaca));

            assembler.add("\nMOV AX, 4C00h");
            assembler.add("INT 21h");
            assembler.add("END");
        }
    private List<String> generateData(List<Symbol> symbolTableList) {
        List<String> data = new ArrayList<>();
        for(Symbol symbol : symbolTableList){
            if(symbol.getType() == DataType.CTE_STRING)
                data.add(String.format("%-20s %-5s %-30s, '$'", symbol.getName(),"dd", symbol.getValue()));
            else
                data.add(String.format("%-20s %-5s %-30s", symbol.getName(),"dd", symbol.getValue()));
        }
        return data;
    }
    private List<String> generateCode(List<String> polaca) {
        List<String> code = new ArrayList<>();
        Stack<String> operandStack  = new Stack<String>();
        Queue<String> labelQueue  = new LinkedList<String>();
        Stack<Integer> pilaNroCelda = new Stack<Integer>();

        int cantVariablesAuxiliares = 0;
        int cantEtiquetas = 0;
        int nroCelda = 1;

        for(String cell : polaca){

            if(!pilaNroCelda.isEmpty() && nroCelda == pilaNroCelda.peek()){
                code.add(labelQueue.remove() + ":");
                pilaNroCelda.pop();
            }
            switch (cell){
                case "write":
                {
                    String op = operandStack.pop();
                    code.add("FLD " + op);
                    code.add("");
                    break;
                }
                case "read":
                {
                    String op2 = operandStack.pop();
                    String op1 = operandStack.pop();
                    code.add("FLD " + op1);
                    code.add("FSTP " + op2);
                    code.add("");
                    break;
                }
                case ":=":
                {
                    String op2 = operandStack.pop();
                    String op1 = operandStack.pop();
                    code.add("FLD " + op1);
                    code.add("FSTP " + op2);
                    code.add("");
                    break;
                }
                case "+":
                {
                    String op2 = operandStack.pop();
                    String op1 = operandStack.pop();
                    String aux = "@aux" + (cantVariablesAuxiliares+1);
                    cantVariablesAuxiliares++;
                    code.add("FLD " + op1);
                    code.add("FLD " + op2);
                    code.add("FADD");
                    code.add("FSTP " + aux);
                    code.add("");
                    operandStack.add(aux);
                    break;
                }
                case "-":
                {
                    String op2 = operandStack.pop();
                    String op1 = operandStack.pop();
                    String varAux = "@aux" + (cantVariablesAuxiliares+1);
                    cantVariablesAuxiliares++;
                    code.add("FLD " + op1);
                    code.add("FLD " + op2);
                    code.add("FSUB");
                    code.add("FSTP " + varAux);
                    code.add("");
                    operandStack.add(varAux);
                    break;
                }
                case "/":
                {
                    String op2 = operandStack.pop();
                    String op1 = operandStack.pop();
                    String varAux = "@aux" + (cantVariablesAuxiliares+1);
                    cantVariablesAuxiliares++;
                    code.add("FLD " + op1);
                    code.add("FLD " + op2);
                    code.add("FDIV");
                    code.add("FSTP " + varAux);
                    code.add("");
                    operandStack.add(varAux);
                    break;
                }
                case "*":
                {
                    String op2 = operandStack.pop();
                    String op1 = operandStack.pop();
                    String varAux = "@aux" + (cantVariablesAuxiliares+1);
                    cantVariablesAuxiliares++;
                    code.add("FLD " + op1);
                    code.add("FLD " + op2);
                    code.add("FMUL");
                    code.add("FSTP " + varAux);
                    code.add("");
                    operandStack.add(varAux);
                    break;
                }
                case "CMP":
                {
                    String op2 = operandStack.pop();
                    String op1 = operandStack.pop();
                    code.add("FLD " + op1);
                    code.add("FLD " + op2);
                    code.add("FXCH");
                    code.add("FCOMP");
                    code.add("FSTSW AX");
                    code.add("SAHF");
                    break;
                }
                case "BLE":
                case "BGE":
                case "BLT":
                case "BGT":
                case "BEQ":
                case "BNE":
                {
                    String label = "label" + (cantEtiquetas+1);
                    cantEtiquetas++;
                    labelQueue.add(label);
                    code.add(cell + " " + label);
                    code.add("");
                    break;
                }
                case "BI":
                {
                    code.add("BI " + labelQueue.remove());
                    code.add("");
                    break;
                }
                case "ET":
                {
                    String label = "label" + (cantEtiquetas+1);
                    cantEtiquetas++;
                    labelQueue.add(label);
                    code.add(label + ":");
                    code.add("");
                    break;
                }
                default: {
                    if(cell.startsWith("#")){
                        int nroCeldaSalto = Integer.parseInt(cell.substring(1));
                        if(nroCeldaSalto >= nroCelda){
                            pilaNroCelda.add(nroCeldaSalto);
                        }
                    }
                    else{
                        operandStack.add(cell);
                    }
                    break;
                }
            }
            nroCelda++;
        }

        return code;
    }

    public List<String> getAssemblerList(){
            return assembler;
        }
    }
