package lyc.compiler.model;

import java.util.ArrayList;
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

    public void addConstant(String value) {
        this.add("_" + value, null, value, null);
    }
    public void addStringConstant(String value) {
        this.add("_string" + counter++, null, value, value.length());
    }

    public void addIdentifiers(ArrayList<String> identifiers, DataType dataType) {
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
