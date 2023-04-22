package lyc.compiler.model;

import java.util.ArrayList;
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
        if (!isInTable(name)) {
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
        Iterator<String> i = identifiers.iterator();
        while (i.hasNext()) {
            // must be called before you can call i.remove()
            String id = i.next();
            if (!(isInTable(id))) {
                this.add(id, dataType, "-", null);
            } else {
                throw new Error("Error de sintaxis: la variable '" + id + "' ya habia sido declarada.");
            }
            // Remove identifier from list
            i.remove();
        }
    }

    public Boolean isInTable(String name) {
        return symbolList.stream().anyMatch(symbol -> symbol.getName().equals(name));
    }
}
