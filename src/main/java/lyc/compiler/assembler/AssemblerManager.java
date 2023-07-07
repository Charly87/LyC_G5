package lyc.compiler.assembler;
import lyc.compiler.model.DataType;
import lyc.compiler.model.Symbol;
import lyc.compiler.model.SymbolTableManager;
import lyc.compiler.polaca.PolacaManager;

import java.util.*;

public class AssemblerManager {
        List<String> assembler = new ArrayList<>();
        SymbolTableManager symbolaManager;
        PolacaManager polacaManager;
        int auxCount = 0;
        int labelCount = 0;
        int cellNumber = 1;
        public AssemblerManager(SymbolTableManager symbolaManager, PolacaManager polacaManager)
        {
            this.symbolaManager = symbolaManager;
            this.polacaManager = polacaManager;
        }
        public void generate(){

            List<String> code = this.generateCode();
            List<String> data = this.generateData();

            assembler.add("include grupo2.asm");

            assembler.add(".MODEL LARGE");
            assembler.add(".386");
            assembler.add(".STACK 200h\n");

            assembler.add(".DATA\n");
            assembler.addAll(data);

            assembler.add("\n.CODE");
            assembler.add("START:\n");
            assembler.add("\nMOV AX, @DATA");
            assembler.add("MOV DS, AX");
            assembler.add("MOV ES, AX\n");
            assembler.addAll(code);

            assembler.add("\nMOV AX, 4C00h");
            assembler.add("INT 21h");
            assembler.add("END START");
        }
    private List<String> generateData() {
        List<String> data = new ArrayList<>();
        for(Symbol symbol : this.symbolaManager.getSymbolList()){
            if(symbol.getType() == DataType.CTE_STRING)
                data.add(String.format("%-20s %-5s %-30s,'$'", symbol.getName(),"db", symbol.getValue()));
            else
                data.add(String.format("%-20s %-5s %-30s", symbol.getName(),"dd", symbol.getValue()));
        }

        for(int i=1; i <= this.auxCount ; i++)
            data.add(String.format("%-20s %-5s %-30s", "@aux" + i,"dd", "?"));

        data.add(String.format("%-20s %-5s %-30s,'$'", "_NEWLINE","db", "0DH,0AH"));
        return data;
    }
    private List<String> generateCode() {
        List<String> code = new ArrayList<>();
        Stack<String> operandStack  = new Stack<String>();
        Queue<String> labelQueue  = new LinkedList<String>();
        Stack<Integer> cellNumberStack = new Stack<Integer>();

        auxCount = 0;
        labelCount = 0;
        cellNumber = 1;

        for(String cell : this.polacaManager.getPolacaList()){

            if(!cellNumberStack.isEmpty() && cellNumber == cellNumberStack.peek()){
                code.add(labelQueue.remove() + ":");
                cellNumberStack.pop();
            }
            switch (cell){
                case "WRITE":
                {
                    String op = operandStack.pop();
                    DataType type = this.getSymbolType(op);

                    if(type == DataType.STRING || type == DataType.CTE_STRING)
                        code.add("displayString " + op );
                    if(type == DataType.INTEGER || type == DataType.CTE_INTEGER)
                        code.add("displayInteger " + op );
                    if(type == DataType.FLOAT || type == DataType.CTE_FLOAT)
                        code.add("displayFloat " + op );

                    code.add("displayString _NEWLINE");
                    break;
                }
                case "READ":
                {
                    String op = operandStack.pop();
                    DataType type = this.getSymbolType(op);

                    if(type == DataType.STRING)
                        code.add("GetString " + op );
                    if(type == DataType.INTEGER)
                        code.add("GetInteger " + op );
                    if(type == DataType.FLOAT)
                        code.add("GetFloat " + op );

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
                    String aux = "@aux" + (++auxCount);
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
                    String varAux = "@aux" + (++auxCount);
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
                    String varAux = "@aux" + (++auxCount);
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
                    String varAux = "@aux" + (++auxCount);
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
                {
                    String label = "label" + (++labelCount);
                    labelQueue.add(label);
                    code.add("JNA " + label);
                    code.add("");
                    break;
                }
                case "BGE":
                {
                    String label = "label" + (++labelCount);
                    labelQueue.add(label);
                    code.add("JAE " + label);
                    code.add("");
                    break;
                }
                case "BLT":
                {
                    String label = "label" + (++labelCount);
                    labelQueue.add(label);
                    code.add("JB " + label);
                    code.add("");
                    break;
                }
                case "BGT":
                {
                    String label = "label" + (++labelCount);
                    labelQueue.add(label);
                    code.add("JA " + label);
                    code.add("");
                    break;
                }
                case "BEQ":
                {
                    String label = "label" + (++labelCount);
                    labelQueue.add(label);
                    code.add("JE " + label);
                    code.add("");
                    break;
                }
                case "BNE":
                {
                    String label = "label" + (++labelCount);
                    labelQueue.add(label);
                    code.add("JNE " + label);
                    code.add("");
                    break;
                }
                case "BI":
                {
                    code.add("JMP " + labelQueue.remove());
                    code.add("");
                    break;
                }
                case "ET":
                {
                    String label = "label" + (++labelCount);
                    labelQueue.add(label);
                    code.add(label + ":");
                    code.add("");
                    break;
                }
                default: {
                    if(cell.startsWith("#")){
                        int cellJumpNumber = Integer.parseInt(cell.substring(1));
                        if(cellJumpNumber >= cellNumber){
                            cellNumberStack.add(cellJumpNumber);
                        }
                    }
                    else{
                        String operandName = getSymbolName(cell);
                        operandStack.add(operandName);
                    }
                    break;
                }
            }
            cellNumber++;
        }

        return code;
    }


    private String getSymbolName(String value)
    {
        for(Symbol s : this.symbolaManager.getSymbolList())
        {
            if( s.getValue().equals(value))
            {
                return s.getName();
            }
        }
        return value;
    }

    private DataType getSymbolType(String name)
    {
        for(Symbol s : this.symbolaManager.getSymbolList())
        {
            if( s.getName().equals(name))
            {
                return s.getType();
            }
        }
        return null;
    }

    public List<String> getAssemblerList(){
            return assembler;
        }
    }
