import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        Parser parse = new Parser("C:\\Users\\Kevin\\Desktop\\CPP\\Year2\\Sem 2\\CS 3650\\Tetris\\nand2tetris\\projects\\08\\ProgramFlow\\BasicLoop\\BasicLoop.vm");
        File outFile = new File("C:\\Users\\Kevin\\Desktop\\CPP\\Year2\\Sem 2\\CS 3650\\Tetris\\nand2tetris\\projects\\08\\ProgramFlow\\BasicLoop\\BasicLoop.asm");
        CodeWriter writ = new CodeWriter(outFile);
        while(parse.advance())
        {
            if(parse.commandType().equals("C_ARITHMETIC"))
                writ.writeArithmetic(parse.arg1());
            else if(parse.commandType().equals(("C_PUSH")))
                writ.writePushPop(parse.commandType(), parse.arg1(), Integer.parseInt(parse.arg2()));
            else if(parse.commandType().equals(("C_POP")))
                writ.writePushPop(parse.commandType(), parse.arg1(), Integer.parseInt(parse.arg2()));
            else if(parse.commandType().equals("C_LABEL"))
                writ.writeLabel(parse.arg1());
            else if(parse.commandType().equals("C_GOTO"))
                writ.writeGoto(parse.arg1());
            else if(parse.commandType().equals("C_IF"))
                writ.writeIf(parse.arg1());
            else if(parse.commandType().equals("C_FUNCTION"))
                writ.writeFunction(parse.arg1(), Integer.parseInt(parse.arg2()));
            else if(parse.commandType().equals("C_CALL"))
                writ.writeCall(parse.arg1(), Integer.parseInt(parse.arg2()));
            else if(parse.commandType().equals("C_RETURN"))
                writ.writeReturn();
        }
        writ.close();
        System.out.println(java.time.LocalTime.now());
    }
}
