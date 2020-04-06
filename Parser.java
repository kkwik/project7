import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class Parser {
    protected File source;
    protected Scanner scan;
    protected ArrayList<String> arithmetic = new ArrayList<String>();
    protected String currComm = "";

    protected Parser(String fileName)
    {
        source = new File(fileName);
        try {
            scan = new Scanner(source);
        }
        catch(Exception e){
            System.out.println("File not found");
            System.exit(-1);
        }

        arithmetic.add("add");
        arithmetic.add("sub");
        arithmetic.add("neg");
        arithmetic.add("eq");
        arithmetic.add("gt");
        arithmetic.add("lt");
        arithmetic.add("and");
        arithmetic.add("or");
        arithmetic.add("not");
    }

    protected boolean hasMoreCommands()
    {
        return scan.hasNext();
    }

    protected String getCommand() { return scan.nextLine().toLowerCase().split("//")[0]; }

    protected boolean advance()
    {
        if(hasMoreCommands()) {
            currComm = getCommand();
            if(currComm.length() == 0) {
                advance();
            }
            return true;
        }
        return false;
    }
    protected String commandType()
    {
        String base = currComm.split(" ")[0];
        if(arithmetic.contains(base))
            return "C_ARITHMETIC";
        else if(base.equals("push"))
            return "C_PUSH";
        else if(base.equals("pop"))
            return "C_POP";
        else if(base.equals("label"))
            return "C_LABEL";
        else if(base.equals("goto"))
            return "C_GOTO";
        else if(base.equals("if-goto"))
            return "C_IF";
        else if(base.equals("function"))
            return "C_FUNCTION";
        else if(base.equals("return"))
            return "C_RETURN";
        else if(base.equals("call"))
            return "C_CALL";

        System.out.println("ERROR: Invalid command type");
        System.exit(-1);
        return "This line really doesn't matter but I'm missing a return statement without it so whatever";
    }

    protected String arg1()
    {
        if(commandType().equals("C_RETURN"))
            return "null";
        else if(commandType().equals("C_ARITHMETIC"))
            return currComm.split(" ")[0].trim();

        return currComm.split(" ")[1];
    }

    protected String arg2(){
        if(commandType().equals("C_PUSH") || commandType().equals("C_POP") || commandType().equals("C_FUNCTION") || commandType().equals("C_CALL"))
            return currComm.split(" ")[2].trim();
        return "That shouldn't have happened";
    }
}
