package compiler;

import java.util.ArrayList;
import java.util.Stack;

public class InfixToPostFixEvalution {

    private static boolean isOperator(String c) {
        //System.out.println(c.equals( Keyword.hmp.get("+"))+"        "+c.equals( Keyword.hmp.get("*"))+"     "+c.equals( Keyword.hmp.get("-")));
           
        if(c.equals( Keyword.hmp.get("+").toString())||c.equals( Keyword.hmp.get("*").toString())||c.equals( Keyword.hmp.get("-").toString()))
           return true;
        return false;

    }

    private static int getPrecedence(String ch) {
        int i;
        i=Integer.parseInt(ch);
        switch (i) {
        case 13:
        
            return 1;

        case 18 :
       
            return 2;

        }
        return -1;
    }

    // A utility function to check if the given character is operand
    private boolean isOperand(String ch) {
        int i;
        i=Integer.parseInt(ch);
        if(i==17||i==19)
            return true;
        return false;
        //return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    public ArrayList<StackNode> convertToPostfix(Object[][] infix,int i) {
        Stack<StackNode> stack = new Stack<StackNode>();
            ArrayList<StackNode> postfix = new  ArrayList<StackNode>();
        //i=i+2;
        StackNode c;
        StackNode temp=new StackNode((int)infix[i][0],infix[i][1].toString(),infix[i][2].toString());
        int line = temp.line;
        
        while (line==temp.line) {
            
            //System.out.println(" i  "+i);
            c=new StackNode((int)infix[i][0],infix[i][1].toString(),infix[i][2].toString());
            i++;
            //System.out.println("line "+c.line+"     "+c.name+"      "+c.type);
           // System.out.println("c.type"+c.type+"");
            if (isOperand(c.type)) {
                //System.out.println("true");
                postfix.add(c);
                //i++;
            } else if (c.type.equals( Keyword.hmp.get("(").toString())) {
                stack.push(c);
                
            }
            // If the scanned character is an ‘)’, pop and output from the stack
            // until an ‘(‘ is encountered.
            else if (c.type.equals( Keyword.hmp.get(")").toString())) {

                while (!stack.isEmpty() && !stack.peek().type.equals(Keyword.hmp.get("(").toString())) {
                    postfix.add(stack.pop());
                }
                if (!stack.isEmpty() && !stack.peek().type.equals(Keyword.hmp.get("(").toString()))
                    return null;
                else if(!stack.isEmpty()){
                    //System.out.println("found");
                    stack.pop();}
            }
            else if (isOperator(c.type)) // operator encountered
            {
                if (!stack.isEmpty() && getPrecedence(c.type) <= getPrecedence(stack.peek().type)) {
                    postfix.add(stack.pop());
                }
                stack.push(c);
                
            }
             temp=new StackNode((int)infix[i][0],infix[i][1].toString(),infix[i][2].toString());
            //i++;
        }

        while (!stack.isEmpty()) {  
            postfix.add(stack.pop());
            //System.out.println("        here        ");
        }
//         for(int j =0; j<postfix.size();j++)
//            System.out.println("line    "+postfix.get(j).line+"      type        "+postfix.get(j).type+"      name    "+postfix.get(j).name);
//        
        return postfix;
    }
   

}