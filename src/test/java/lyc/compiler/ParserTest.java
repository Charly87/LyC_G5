package lyc.compiler;

import java_cup.runtime.Symbol;
import lyc.compiler.factories.ParserFactory;
import lyc.compiler.model.InvalidLengthException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.truth.Truth.assertThat;
import static lyc.compiler.Constants.EXAMPLES_ROOT_DIRECTORY;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ParserTest {

    @Test
    public void assignmentSumCteMismoTipo() throws Exception {
        String cadena="init { f1 : Float } f1=0.9+9.1";
        System.out.println(cadena);
        compilationSuccessful(cadena);
    }
    @Test
    public void assignmentSumaCteMismoTipoString() throws Exception {
        String cadena="init { s1 : String } s1=\"Hola \" + \"Mundo \" ";
        System.out.println(cadena);
        compilationSuccessful(cadena);
    }
    @Test
    public void assignmentSumaCteDistintoTipo() throws Exception {
        String cadena="init { i1 : Int } i1=9+9.9";
        System.out.println(cadena);
        assertThrows(Error.class, () -> {  compilationSuccessful(cadena);
        });
        System.out.println();
    }

    @Test
    public void assignmentNoDeclaradaVar() throws Exception {
        String cadena="init { i1 : Int } i55=9+18";
        System.out.println(cadena);
        assertThrows(Error.class, () -> {  compilationSuccessful(cadena);
        });
        System.out.println();
    }

    @Test
    public void assignmentSumaCteDistintoTipo2() throws Exception {
        assertThrows(Error.class, () -> {  compilationSuccessful("init { i1 : Int } i1=0.9+\"hola\"");
        });
        System.out.println();
    }
    @Test
    public void assignmentSumaIdDeclarados() throws Exception {
        compilationSuccessful("init { i1,i2,i3 : Int } i1=i2+i3");
    }
    @Test
    public void assignmentSumaIdNoDeclarados() throws Exception {
        assertThrows(Error.class, () -> {  compilationSuccessful("init { b1,b2,b3 : Int } b1=b4+b3");
        });
        System.out.println();
    }
    @Test
    public void assignmentSumaIdDeclaradosDistintoTipo() throws Exception {
        assertThrows(Error.class, () -> {  compilationSuccessful("init { b1,b2,b3 : Int a1 : Float} b1=b2+a1");
        });
        System.out.println();
    }

    @Test
    public void assignmentEntero() throws Exception {
        compilationSuccessful("init {  c : Int } c = 4+9");
    }

    @Disabled
    @Test
    public void assignmentWithExpression() throws Exception {
        compilationSuccessful("c=d*(e-21)/4");
    }//Falla ya que no se declaran los ids

    @Test
    public void syntaxError() {
        compilationError("1234");
    }
    @Disabled
    @Test
    void assignments() throws Exception {
        compilationSuccessful(readFromFile("assignments.txt"));
    }

    @Test
    void write() throws Exception {
        compilationSuccessful(readFromFile("write.txt"));
    }

    @Test
    void read() throws Exception {
        compilationSuccessful(readFromFile("read.txt"));
    }

    @Disabled
    @Test
    void comment() throws Exception {
        compilationSuccessful(readFromFile("comment.txt"));
    }

    @Test
    void init() throws Exception {
        compilationSuccessful(readFromFile("init.txt"));
    }
    @Disabled
    @Test
    void and() throws Exception {
        compilationSuccessful(readFromFile("and.txt"));
    }
    @Disabled
    @Test
    void or() throws Exception {
        compilationSuccessful(readFromFile("or.txt"));
    }
    @Disabled
    @Test
    void not() throws Exception {
        compilationSuccessful(readFromFile("not.txt"));
    }
    @Disabled
    @Test
    void ifStatement() throws Exception {
        compilationSuccessful(readFromFile("if.txt"));
    }
    @Test
    void operandosCondicionDistintoTipo() throws Exception {
        assertThrows(Error.class, () -> {
            compilationSuccessful(" init { a,b : Int  c : Float} if (a > c) { write(\"a es mas grande que b\") }");
        });
        System.out.println();
    }
    @Test
    void operandosCondicionMismoTipo() throws Exception {
            compilationSuccessful(" init { a,b : Int  c : Float} if (a > b) { write(\"a es mas grande que b\") }");
    }
    @Disabled
    @Test
    void whileStatement() throws Exception {
        compilationSuccessful(readFromFile("while.txt"));
    }

    private void compilationSuccessful(String input) throws Exception {
        assertThat(scan(input).sym).isEqualTo(ParserSym.EOF);
    }

    private void compilationError(String input){
        assertThrows(Exception.class, () -> scan(input));
    }

    private Symbol scan(String input) throws Exception {
        return ParserFactory.create(input).parse();
    }

    private String readFromFile(String fileName) throws IOException {
        URL url = new URL(EXAMPLES_ROOT_DIRECTORY + "/%s".formatted(fileName));
        assertThat(url).isNotNull();
        return IOUtils.toString(url.openStream(), StandardCharsets.UTF_8);
    }

}
