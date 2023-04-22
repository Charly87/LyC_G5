package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import lyc.compiler.model.*;

public class SymbolTableGenerator implements FileGenerator{
    private final List<Symbol> symbolList;

    public SymbolTableGenerator(List<Symbol> symbolList) {
        this.symbolList = symbolList;
    }

    public SymbolTableGenerator() {
        this.symbolList = Collections.emptyList();
    }

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        try {
            if (symbolList.isEmpty())
                return;

            fileWriter.write(String.format("%-30s|%-30s|%-30s|%-30s\n", "NOMBRE", "TIPO", "VALOR", "LONGITUD"));
            symbolList.forEach(symbol -> {
                try {
                    fileWriter.write(symbol.toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
