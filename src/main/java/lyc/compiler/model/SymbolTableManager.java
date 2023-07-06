package lyc.compiler.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SymbolTableManager {
    private final List<Symbol> symbolList;
    private int counter;

    public SymbolTableManager() {
        this.symbolList = new ArrayList<>();
        this.counter = 0;
    }

    public void add(String name, DataType type, String value, Integer length) {
        if (!exist(name)) {
            symbolList.add(new Symbol(name, type, value, length));
        }
    }
    public List<Symbol> getSymbolList() {
        return symbolList;
    }
    public Symbol getSymbol(String name) {

        Iterator<Symbol> iterator = symbolList.iterator();

        while (iterator.hasNext()) {
            Symbol symbol = iterator.next();
            if (symbol.getName().equals(name))
                return symbol;
        }

        /*Si el name no se encuentra en la lista, devuelve Error*/
        throw new Error(name+ " no se encuentra en SymbolTable");
    }
    public void addFactor(String value,DataType tipo) {
        if(!exist("_"+value))
            this.add("_" + value, tipo, value, (tipo.equals(DataType.CTE_STRING)? value.length() : null));
    }
    public DataType addFactor(String name) {
        if(exist(name))
            return getSymbol(name).getType();
        else
            throw new Error(name+ " No ha sido declarada");
    }
    public void addIdentifiers(List<String> identifiers, DataType dataType) {
        for(String id : identifiers) {
            if (!exist(id)) {
                this.add(id, dataType, "-", null);
            } else {
                throw new Error("Error de sintaxis: la variable '" + id + "' ya habia sido declarada.");
            }
        }
        identifiers.clear();
    }

    public Boolean exist(String name) {
        return symbolList.stream().anyMatch(symbol -> symbol.getName().equals(name));
    }
}
