package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AsmCodeGenerator implements FileGenerator {
    private final List<String> assemblerList;

    public AsmCodeGenerator(List<String> assemblerList) {
        this.assemblerList = assemblerList;
    }
    public AsmCodeGenerator() {
        this.assemblerList = new ArrayList<>();
    }

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        try {
            if (assemblerList.isEmpty())
                return;

            assemblerList.forEach(asm -> {
                try {
                    fileWriter.write(asm.toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
