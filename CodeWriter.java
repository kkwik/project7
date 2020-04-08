import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class CodeWriter {
    FileWriter writer;
    int uniqueID = 0;
    String fileName;

    public CodeWriter(File file) throws IOException {
        writer = new FileWriter(file);
        fileName = file.getName();
       writeInit();
    }

    public void setFileName(String fileName) throws IOException
    {
            fileName = fileName;
    }

    protected void writeArithmetic(String input) throws IOException
    {
        String result = "";
        if(input.equals("add"))
            result = "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=D+M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n";
        else if(input.equals("sub"))
            result = "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M-D\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n";
        else if(input.equals("neg"))
            result = "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "M=-M\n" +
                    "@SP\n" +
                    "M=M+1\n";
        else if(input.equals("eq"))
            result = "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M-D\n" +
                    "@FLAGTRUE" + uniqueID++ + "\n" +
                    "D;JEQ\n" +
                    "@SP\n" +
                    //"M=M-1\n" +
                    "A=M\n" +
                    "M=0\n" +
                    "@OVERTRUE" + uniqueID++ + "\n" +
                    "0;JMP\n" +
                    "(FLAGTRUE" + (uniqueID - 2) + ")\n" +
                    "@SP\n" +
                    //"M=M-1\n" +
                    "A=M\n" +
                    "M=-1\n" +
                    "(OVERTRUE" + (uniqueID - 1) + ")\n" +
                    "@SP\n" +
                    "M=M+1\n";
        else if(input.equals("gt"))
            result = "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M-D\n" +
                    "@FLAGTRUE" + uniqueID++ + "\n" +
                    "D;JGT\n" +
                    "@SP\n" +
                    //"M=M-1\n" +
                    "A=M\n" +
                    "M=0\n" +
                    "@OVERTRUE" + uniqueID++ + "\n" +
                    "0;JMP\n" +
                    "(FLAGTRUE" + (uniqueID - 2) + ")\n" +
                    "@SP\n" +
                    //"M=M-1\n" +
                    "A=M\n" +
                    "M=-1\n" +
                    "(OVERTRUE" + (uniqueID - 1) + ")\n" +
                    "@SP\n" +
                    "M=M+1\n";
        else if(input.equals("lt"))
            result = "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M-D\n" +
                    "@FLAGTRUE" + uniqueID++ + "\n" +
                    "D;JLT\n" +
                    "@SP\n" +
                    //"M=M-1\n" +
                    "A=M\n" +
                    "M=0\n" +
                    "@OVERTRUE" + uniqueID++ + "\n" +
                    "0;JMP\n" +
                    "(FLAGTRUE" + (uniqueID - 2) + ")\n" +
                    "@SP\n" +
                    //"M=M-1\n" +
                    "A=M\n" +
                    "M=-1\n" +
                    "(OVERTRUE" + (uniqueID - 1) + ")\n" +
                    "@SP\n" +
                    "M=M+1\n";
        else if(input.equals("and"))
            result = "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=D&M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n";
        else if(input.equals("or"))
            result = "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=D|M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n";
        else if(input.equals("not"))
            result = "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "M=!M\n" +
                    "@SP\n" +
                    "M=M+1\n";

        writer.append(result);
    }

    protected void writePushPop(String comm, String segment, int index) throws IOException
    {
        String result = "";
        if(comm.equals("C_PUSH")){
            if(segment.equals("constant"))
                result = "@" + index + "\n" +
                        "D=A\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        "@SP\n" +
                        "M=M+1\n";
            else if(segment.equals("local"))
                result = pushForm("LCL", index);
            else if(segment.equals("argument"))
                result = pushForm("ARG", index);
            else if(segment.equals("this"))
                result = pushForm("THIS", index);
            else if(segment.equals("that"))
                result = pushForm("THAT", index);
            else if(segment.equals("static"))
                result = pushForm("16", index);
            else if(segment.equals("temp"))
                result = pushForm("5", index);
            else if(segment.equals("pointer"))
                result = pushForm("3", index);
            }
        else {
            if (segment.equals("constant"))
                result = "@SP\n" +
                        "M=M-1\n" +
                        "A=M\n" +
                        "D=M\n";
            else if (segment.equals("local"))
                result = popForm("LCL", index);
            else if (segment.equals("argument"))
                result = popForm("ARG", index);
            else if (segment.equals("this"))
                result = popForm("THIS", index);
            else if (segment.equals("that"))
                result = popForm("THAT", index);
            else if (segment.equals("static"))
                result = popForm("16", index);
            else if (segment.equals("temp"))
                result = popForm("5", index);
            else if (segment.equals("pointer"))
                result = popForm("3", index);
        }
             writer.append(result);
    }

    protected void writeInit() throws IOException
    {
        writer.append("@256\nD=A\n@SP\nM=D\n");
        writeCall("Sys.init", 0);
    }

    protected void writeLabel(String label) throws IOException
    {
        writer.append("(" + label + ")\n");
    }

    protected void writeGoto(String label) throws IOException
    {
        writer.append("@" + label + "\n0;JMP\n");
    }

    protected void writeIf(String label) throws IOException
    {
        writer.append("@SP\nM=M-1\nA=M\nD=M\n@" + label + "\nD;JNE\n");
    }

    protected void writeCall(String functionName, int numArgs) throws IOException
    {
        writer.append("@RETURN_ADDRESS" + uniqueID + "\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
        writePushPop("C_PUSH", "LCL", 0);
        writePushPop("C_PUSH", "ARG", 0);
        writePushPop("C_PUSH", "THIS", 0);
        writePushPop("C_PUSH", "THAT", 0);
        writer.append("@SP\n" +
                "D=M\n" +
                "@" + numArgs + "\n" +
                "D=D-A\n" +
                "@5\n" +
                "D=D-A\n" +
                "@ARG\n" +
                "M=D\n" +
                "@SP\n" +
                "D=M\n" +
                "@LCL\n" +
                "M=D\n");
        writeGoto(functionName);
        writeLabel("RETURN_ADDRESS" + uniqueID++);
    }

    protected void writeReturn() throws IOException
    {
        writer.append("@LCL\n" +
                "D=M\n" +
                "@R13\n" +
                "M=D\n" +
                "@5\n" +
                "D=D-A\n" +
                "A=D\n" +
                "D=M\n" +
                "@R14\n" +
                "M=D\n");
        writePushPop("C_POP", "ARG", 0);
        writer.append("@ARG\n" +
                "D=M+1\n" +
                "@SP\n" +
                "M=D\n" +
                "@R13\n" +
                "D=M-1\n" +
                "A=D\n" +
                "D=M\n" +
                "@THAT\n" +
                "M=D\n" +
                "@R13\n" +
                "D=M-1\n" +
                "D=D-1\n" +
                "A=D\n" +
                "D=M\n" +
                "@THIS\n" +
                "M=D\n" +
                "@R13\n" +
                "D=M-1\n" +
                "D=D-1\n" +
                "D=D-1\n" +
                "A=D\n" +
                "D=M\n" +
                "@ARG\n" +
                "M=D\n" +
                "@R13\n" +
                "D=M-1\n" +
                "D=D-1\n" +
                "D=D-1\n" +
                "D=D-1\n" +
                "A=D\n" +
                "D=M\n" +
                "@LCL\n" +
                "M=D\n" +
                "@R14\n" +
                "A=M\n" +
                "0;JMP\n");
        //writeGoto("R14");
    }

    protected void writeFunction(String functionName, int numLocals) throws IOException
    {
        writer.append("(" + functionName + ")\n");
        for(int i = 0; i < numLocals; i++)
            writePushPop("C_PUSH", "constant", 0);
    }

    public void close() throws IOException{
        writer.close();
    }

    protected String pushForm(String seg, int offset)
    {

        return  "@" + offset + "\n" +
                "D=A\n" +
                "@" + seg + "\n" +
                "A=D+" + (isNumber(seg) ? "A" : "M") + "\n" +
                "D=M\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1\n";
    }

    protected String popForm(String seg, int offset)
    {

        return "@" + offset + "\n" +
                "D=A\n" +
                "@" + seg + "\n" +
                "D=D+" + (isNumber(seg) ? "A" : "M") + "\n" +
                "@R13\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "D=M\n" +
                "@R13\n" +
                "A=M\n" +
                "M=D\n";
    }
    private boolean isNumber(String in)
    {
        for(int i = 0; i < in.length(); i++)
            if(!Character.isDigit(in.charAt(i)))
                return false;
        return true;
    }
}
