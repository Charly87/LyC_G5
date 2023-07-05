package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

public class IntermediateCodeGenerator implements FileGenerator {
    private final ArrayList<String> polacaList;

    public IntermediateCodeGenerator(ArrayList<String> polacaList) {
        this.polacaList = polacaList;
    }
    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        try {
            if (polacaList.isEmpty())
                return;

            polacaList.forEach(symbol -> {
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
