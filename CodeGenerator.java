/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Mariem Adel
 */
public class CodeGenerator {

    private ArrayList<String> idList;
    private int idCount;
    private String RegA = null;
    private String Codee = " ";
    private String progname = "";
    private BufferedWriter bw;
    public void setProgname(String progname) {
        this.progname = progname;
    }

    public String getProgname() {
        return progname;
    }
    

    public void Addcode(String s) {
        this.Codee +=  s ;
    }

    public String getCodee() {
       // System.out.println("here");
       
        //System.out.println(Codee);
        return Codee;
    }

    public void setRegA(String RegA) {
        this.RegA = RegA;
    }

    public String getRegA() {
        return RegA;
    }

    public int getIdCount() {
        return idCount;
    }

    public void setIdCount(int idCount) {
        this.idCount = idCount;
    }

    public ArrayList<String> getIdList() {
        return idList;
    }

    public String getId(ArrayList<String> idList, int index) {
        return idList.get(index);
    }

    public void setIdList(ArrayList<String> idList) {
        // idList.clear();
        this.idList = idList;
    }

    public void progCode(String progname, Object arr[][],ArrayList<String> idList, int idsCount) {
        
        String code="";
        code += progname+ "\tSTART\t" + "0\r\n";
        code += "\tEXTREF\tXWRITE,XREAD\r\n";
        code += "\tSTL\tRETADR\r\n";
        code+="\tJ\tEXADDR\r\n";
        code+="RETADR\tRESW\t1\r\n";
        for (int j = 0; j < idsCount; j++) {
            String words[] = idList.get(j).split("\\^");
            code += words[1]+"\tRESW\t1" ;
            if (j < idsCount - 1) {
                code += "\r\n";
            }
        }
        code+="\r\nEXADDR";
        Addcode(code);
    }
    public void endCode() throws IOException {
     String codee="\r\n";
     codee+="\tLDL\tRETADR\r\n";
     codee+="\tRSUB\r\n";
     codee+="\tEND";
        
     Addcode(codee);
     bw= new BufferedWriter(new FileWriter("codewrite.txt"));
     
      bw.write(getCodee());
        
        bw.close();
     
    }
    public void readCode(ArrayList<String> idList, int idsCount) {
        String code;
        code = "\t+JSUB\tXREAD";
        code += "\r\n";
        code += "\tWORD\t" + idsCount;
        code += "\r\n";
        for (int j = 0; j < idsCount; j++) {
            String words[] = idList.get(j).split("\\^");
            code += "\tWORD\t" + words[1];
            if (j < idsCount - 1) {
                code += "\r\n";
            }
        }
        Addcode(code);
     
    }

    public void writeCode(ArrayList<String> idList, int idsCount) {
        String code="\r\n";

        code += "\t+JSUB\tXWRITE";
        code += "\r\n";
        code += "\tWORD\t" + idsCount;
        code += "\r\n";
        for (int j = 0; j < idsCount; j++) {
            String words[] = idList.get(j).split("\\^");
            code += "\tWORD\t" + words[1];
            if (j < idsCount - 1) {
                code += "\r\n";
            }
        }
        Addcode(code);
        //getCodee();
        
    }

    public void asignCode(ArrayList<StackNode> postfix, StackNode n) {
        Stack<StackNode> stack = new Stack<StackNode>();
        String codee="\r\n";
        StackNode operand=null;
        StackNode regA=null;
        StackNode temp1=null;
        StackNode temp2=null;
        String words[];
        String words2[];
        String temp="";
        if(postfix.size()>1)
        for(int i=0;i<postfix.size();i++)
        {
            if(postfix.get(i).type.equals(Keyword.hmp.get("imm").toString())||postfix.get(i).type.equals(Keyword.hmp.get("id").toString())){
                operand = postfix.get(i);
                stack.push(postfix.get(i));
                
            }
                  if(postfix.get(i).type.equals(Keyword.hmp.get("+").toString())){
                      
                      if(getRegA()==null)
                      {
                          temp1=stack.peek();  
                          words = temp1.name.split("\\#|\\^");
                          codee+="\tLDA\t"+words[1]+"\r\n";
                          setRegA(words[1]);
                          
                      }
                      if(getRegA()!=null){
                          temp1=stack.pop();
                          words = temp1.name.split("\\#|\\^");
                          temp2=stack.pop();
                          words2 = temp2.name.split("\\#|\\^");      
                          if(getRegA().equals(words[1])){
                           codee+="\tADD\t"+words2[1]+"\r\n";
                           temp="^"+getRegA();
                           temp1.name=temp;
                           stack.push(temp1);
                          }
                          else if(getRegA().equals(words2[1])){
                           codee+="\tADD\t"+words[1]+"\r\n";
                           temp="^"+getRegA();
                           temp1.name=temp;
                           stack.push(temp1);
                          }
                          else
                          {
                              codee+="\tLDA\t"+words[1]+"\r\n";
                              codee+="\tADD\t"+words2[1]+"\r\n";
                              temp="^"+getRegA();
                              temp1.name=temp;
                              stack.push(temp1);
                          }
                          
         }}
         if(postfix.get(i).type.equals(Keyword.hmp.get("*").toString())){
               if(getRegA()==null)
                      {
                          temp1=stack.peek();
                          words = temp1.name.split("\\#|\\^");
                          setRegA(words[1]);
                      }
                      if(getRegA()!=null){
                          temp1=stack.pop();
                          words = temp1.name.split("\\#|\\^");
                          temp2=stack.pop();
                          words2 = temp2.name.split("\\#|\\^");
                          if(getRegA().equals(words[1])){
                           codee+="\tMUL\t"+words2[1]+"\r\n";
                           temp="^"+getRegA();
                           temp1.name=temp;
                           stack.push(temp1);
                          }
                          else if(getRegA().equals(words2[1])){
                           codee+="\tMUL\t"+words[1]+"\r\n";
                           temp="^"+getRegA();
                           temp1.name=temp;
                           stack.push(temp1);
                          }
                          else
                          {
                              codee+="\tLDA\t"+words[1]+"\r\n";
                              codee+="\tMUL\t"+words2[1]+"\r\n";
                              temp="^"+getRegA();
                              temp1.name=temp;
                              stack.push(temp1);
                          }
                      }}}
        else
        {
            temp1= postfix.get(0);
            words = temp1.name.split("\\#|\\^");
            if(!getRegA().equals(words[1]))
            {
               codee+="\tLDA\t"+words[1]+"\r\n";
               setRegA(words[1]);
            }
            
        }
       words=n.name.split("\\^");
       setRegA(words[1]);
       codee+="\tSTA\t"+words[1];
        Addcode(codee);
    }

    public void forCode(int i, Object arr[][]) {

    }
}
