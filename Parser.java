/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Arwa
 */
public class Parser {

    // HashMap<String,Integer>  hmp = new HashMap<String,Integer>();
    String title;
    String name;
    int i;
    int j = 0;
    int index = 0;
    CodeGenerator p = new CodeGenerator();
    InfixToPostFixEvalution b = new InfixToPostFixEvalution();
    //private ArrayList<String> idList;
    //private char peak = ' '; <-- eh da?
    private Boolean errorFlag;
    private Object[][] arr = new Object[1000][3];

    private ArrayList<Lexer.Token> tokens;
    FileReaderr fr = new FileReaderr();
    Lexer lexer = new Lexer();

    public Parser() throws IOException {
        getTokens();
    }

    public void setArr(Object[][] arr) {
        this.arr = arr;
    }

    public Object[][] getArr() {
        return arr;
    }

    public void getTokens() throws IOException {

        fr.setLinecount(0);
        int x = 0;
        String str;

        while (fr.codeReader(fr.getLinecount(), "EXP.pas") != null) {

            str = fr.codeReader(fr.getLinecount(), "EXP.pas");
            x++;
            fr.setLinecount(x);
            tokens = lexer.lex(str);
//            for (Lexer.Token token : tokens) {
//                System.out.println(token);
//            }

            setArr(parseTokens(tokens, x));

        }

    }

    public void setI(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public Object[][] parseTokens(ArrayList<Lexer.Token> tokens, int line) {
        int tokenType = 0;
        String tokenSpecifier = "";
        arr[j][0] = line;

        for (int i = 0; i < tokens.size(); i++) {
            tokenSplitter(tokens.get(i));
            switch (title) {
                case "KEYWORDS":
                    tokenType = Keyword.hmp.get(name);
                    tokenSpecifier = "";

                    break;
                case "IDENTIFIER":
                    tokenType = Keyword.hmp.get("id");
                    tokenSpecifier = "^" + name;

                    break;
                case "NUMBER":
                    tokenType = Keyword.hmp.get("imm");
                    tokenSpecifier = "#" + name;

                    break;
                case "BINARYOP":
                    tokenType = Keyword.hmp.get(name);
                    tokenSpecifier = "";

                    break;
                case "lb":
                    tokenType = Keyword.hmp.get(name);
                    tokenSpecifier = "";

                    break;
                case "rb":
                    tokenType = Keyword.hmp.get(name);
                    tokenSpecifier = "";

                    break;
                case "sc":
                    tokenType = Keyword.hmp.get(name);
                    tokenSpecifier = "";
                case "Comma":
                    tokenType = Keyword.hmp.get(name);
                    tokenSpecifier = "";
                    break;
            }
            arr[j][1] = tokenType;
            arr[j][2] = tokenSpecifier;
            arr[j][0] = line;

            j++;
        }
        return arr;
    }

    public void tokenSplitter(Lexer.Token token) {
        String str[] = token.toString().split("\\s+");

        title = str[0];
        name = str[1];
    }
//<prog> ::= PROGRAM <prog-name> VAR <id-list> BEGIN <stmt-list> END.

    public Boolean prog(Object arr[][], int i) throws IOException {

        if (arr[i][1] == Keyword.hmp.get("PROGRAM")) {

            i++;
            setI(i);
            if (progName(arr, i)) {
                
                i++;
                setI(i);
                if (arr[i][1] == Keyword.hmp.get("VAR")) {
                    i++;
                    setI(i);

                    if (idList(arr, i)) {
                        i = getI();
                        p.progCode(p.getProgname(), arr,p.getIdList(), p.getIdCount());
                        if (arr[i][1] == Keyword.hmp.get("BEGIN")) {

                            i++;
                            setI(i);
                            //System.out.println("begin success");
                            if (stmtList(arr, i)) {

                                i = getI();
                                //  System.out.println("stmt success");
                                if (arr[i][1] == Keyword.hmp.get("END.")) {
                                    //System.out.println("before");
                                    p.endCode();
                                    p.getCodee();
                                    
                                    //= new BufferedWriter(new FileWriter("codewrite.txt"))
                                    System.out.println("program run succesfully");
//                                    i++;
//                                    setI(i);
                                    return true;
                                }

                            }
                        }

                    }
                }
            }
        }
        System.out.println("unsuccessful compilation");
        return false;
    }
//<prog-name> ::= id
//tmam

    public Boolean progName(Object arr[][], int i) {
           
        if (arr[i][1] == Keyword.hmp.get("id")) {
            String words[]=arr[i][2].toString().split("\\^");
            p.setProgname(words[1]);
            return true;
        }
        return false;
    }
//<id-list> ::= id | <id-list>, id
//tmam

    public Boolean idList(Object arr[][], int i) {
        int found = 0;
        int idsCount = 0;
        ArrayList<String> idList = new ArrayList<>();
        // idList.add( "mariem");
        //System.out.println("here 1");
        //p.intializeIdList();
        //System.out.println("here 2");
        p.setIdList(null);
        if (arr[i][1] == Keyword.hmp.get("id")) {

            found = 1;
            idList.add(arr[i][2].toString());
            idsCount++;
            i++;
            setI(i);
            while (arr[i][1] == Keyword.hmp.get(",") && found == 1) {

                i++;
                setI(i);
                if (arr[i][1] == Keyword.hmp.get("id")) {
                    //p.addIdList((String) arr[i][2]);
                    idList.add(arr[i][2].toString());
                    idsCount++;
                    i++;
                    setI(i);

                } else {
                    found = 0;
                }
            }

        }
        if (found == 1) {
            //p.intializeIdList();
            //System.out.println("   "+idsCount);
            p.setIdCount(idsCount);
            p.setIdList(idList);
//            for (int j = 0; j < idsCount; j++) {
//                System.out.println("   " + idsCount + "     " + p.getId(p.getIdList(), j));
//            }
            return true;
        } else {
            return false;
        }

    }

    //x,y,z
//<stmt-list> ::= <stmt> | <stmt-list> ; <stmt>
    public Boolean stmtList(Object arr[][], int i) {
        int found = 0;
            while (stmt(arr, i) && (arr[i][1] != Keyword.hmp.get("END.")&&arr[i][1] != Keyword.hmp.get("END"))) {
                
                if (write(arr, i)  ) {
                    i = getI();
                    found = 1;
                    p.writeCode(p.getIdList(), p.getIdCount());
                } else if(read(arr, i))
                {
                     i = getI();
                    found = 1;
                     p.readCode(p.getIdList(), p.getIdCount());
                }
                else if(forr(arr, i))
                {
                    i = getI();
                    found = 1;
                }
                else if (assign(arr, i)) {
                    
                   StackNode n = new StackNode((int)arr[i][0],arr[i][1].toString(),arr[i][2].toString());
                   i=i+2;
                
                   p.asignCode(b.convertToPostfix(arr, i),n);
                    i = getI();
//                    if (arr[i][1] == Keyword.hmp.get(";")) {
//                     
//                        i++;
//                        setI(i);
                        found = 1;
//                    }
                } else {
                    found = 0;
                }
                // System.out.println("arr 2: " + arr[i][0] + "   " + arr[i][1]);
           // }

        }

        if (found == 1) {
            return true;
        } else {
            return false;
        }

    }

//<stmt> ::= <assign> | <read> | <write> | <for>
    public Boolean stmt(Object arr[][], int i) {

        int found = 0; 
         if (write(arr, i)) {
            i = getI();
            found = 1;
        }
         else if (assign(arr, i)) {
            i = getI();
            found = 1;
        } else if (read(arr, i)) {
            i = getI();
            found = 1;
        } else if (forr(arr, i)) {
            i = getI();
            found = 1;
        }

        if (found == 1) {
            return true;
        } else {
            return false;
        }
    }
//<assign> ::= id := <exp>

    public Boolean assign(Object arr[][], int i) {

        int found = 0;

        if (arr[i][1] == Keyword.hmp.get("id")) {

            i++;
            setI(i);

            if (arr[i][1] == Keyword.hmp.get(":=")) {
                i++;
                setI(i);

                if (exp(arr, i) == true) {
                    i = getI();
                    found = 1;
                }

            }

        }
        if (found == 1) {

            return true;
        } else {
            return false;
        }
    }
//<exp> ::= <factor> + <factor> | <factor> * factor>

    public Boolean exp(Object arr[][], int i) {
        int found = 0;
        if (term(arr, i)) {
            i = getI();
            found = 1;
            while (arr[i][1] == Keyword.hmp.get("+") || (arr[i][1] == Keyword.hmp.get("*")) && found == 1) {
                i++;
                setI(i);
                if (!term(arr, i)) {
                    found = 0;
                } else if (term(arr, i)) {
                    i = getI();
                }
            }
        }
        if (found == 1) {
            return true;
        }

        return false;
//        if (factor(arr, i)) {
//            i = getI();
//            if (arr[i][1] == Keyword.hmp.get("+")||(arr[i][1] == Keyword.hmp.get("*"))) {
//                i++;
//                setI(i);
//                if (factor(arr, i)) {
//                    i=getI();
//                    System.out.println("line: " + arr[i][0] + " exp true ");
//           
//                    return true;
//                }
//            } 
//          
//        }
//        System.out.println("line: " + arr[i][0] + " exp false ");
//           
//        return false;

    }

//<factor> ::= id | ( <exp> )
    public Boolean factor(Object arr[][], int i) {

        int found = 0;

        if (arr[i][1] == Keyword.hmp.get("id") || arr[i][1] == Keyword.hmp.get("imm")) {
            found = 1;
            i++;
            setI(i);

        } else {

            if (arr[i][1] == Keyword.hmp.get("(")) {

                found = 1;
                i++;
                setI(i);

                if (exp(arr, i)) {
                    i = getI();
                    if (arr[i][1] == Keyword.hmp.get(")")) {

                        found = 1;
                        i++;
                        setI(i);

                    }

                }
            }

        }
        if (found == 1) {
            //System.out.println("line: " + arr[i][0] + " factor true ");
            return true;
        } else {
            //System.out.println("line: " + arr[i][0] + " factor true ");

            return false;
        }
    }

    public Boolean term(Object arr[][], int i) {
        int found = 0;
        if (factor(arr, i)) {
            i = getI();
            found = 1;
            while ((arr[i][1] == Keyword.hmp.get("+") || arr[i][1] == Keyword.hmp.get("*")) && found == 1) {
                i++;
                setI(i);
                if (factor(arr, i) == false) {
                    found = 0;
                } else if (factor(arr, i)) {
                    i = getI();
                    setI(i);
                }
            }
        }
        if (found == 1) {
            //System.out.println("term true");
            return true;
        } else {
            //System.out.println("term false");
            return false;
        }
    }

//<read> ::= READ ( <id-list> )
    public Boolean read(Object arr[][], int i) {
        if (arr[i][1] == Keyword.hmp.get("READ")) {
            //System.out.println("arr read"+ arr[i][0]+"   "+i);
            i++;
            setI(i);
            if (arr[i][1] == Keyword.hmp.get("(")) {
                i++;
                setI(i);
                if (idList(arr, i)) {
                    i = getI();
                    if (arr[i][1] == Keyword.hmp.get(")")) {

                        i++;
                        setI(i);
                        //p.readCode(p.getIdList(), p.getIdCount());
                        return true;
                    }
                }
            }
        }
        return false;
    }
//<write> ::= WRITE ( <id-list> )
//tmam

    public Boolean write(Object arr[][], int i) {
          
        if (arr[i][1] == Keyword.hmp.get("WRITE")) {
            //System.out.println("arr write"+ arr[i][0]+"   "+i);  
            i++;
            setI(i);
            if (arr[i][1] == Keyword.hmp.get("(")) {
                i++;
                setI(i);
                if (idList(arr, i)) {
                    i = getI();
                    if (arr[i][1] == Keyword.hmp.get(")")) {

                        i++;
                        setI(i);
                       // p.writeCode(p.getIdList(), p.getIdCount());
                        return true;
                    }
                }
            }
        }
        return false;
//        int found = 0;
//        //System.out.println("in write");
//        if (arr[i][1] == Keyword.hmp.get("WRITE")) {
//            // System.out.println("found write");
//            i++;
//            setI(i);
//
//            if (arr[i][1] == Keyword.hmp.get("(")) {
//                // System.out.println("write (");
//                i++;
//                setI(i);
//
//                if (idList(arr, i) == true) {
//                    i = getI();
//
//                    if (arr[i][1] == Keyword.hmp.get(")")) {
//                        // System.out.println("write )");
//                        found = 1;
//                        i++;
//                        setI(i);
//                    }
//                }
//            }
//        }
//        if (found == 1) {
//            //  System.out.println("write true");
//            p.writeCode(p.getIdList(),p.getIdCount());
//            return true;
//        } else {
//            //   System.out.println("write false");
//            return false;
//        }
    }

//<for> ::= FOR <index-exp> DO <body>
    public Boolean forr(Object arr[][], int i) {
        if (arr[i][1] == Keyword.hmp.get("FOR")) {
            i++;
            setI(i);
            if (indexExp(arr, i)) {
                if (arr[i][1] == Keyword.hmp.get("DO")) {
                    i++;
                    setI(i);
                    if (body(arr, i)) {
                        i=getI();
                        return true;
                    }
                }
            }
        }
        return false;
    }
//<index-exp> ::= id := <exp> to <exp>

    public Boolean indexExp(Object arr[][], int i) {
        if (arr[i][1] == Keyword.hmp.get("id")) {
            i++;
            setI(i);
            if (arr[i][1] == Keyword.hmp.get(":=")) {
                i++;
                setI(i);
                if (exp(arr, i)) {
                    i=getI();
                    if (arr[i][1] == Keyword.hmp.get("TO")) {
                        i++;
                        setI(i);
                        if (exp(arr, i)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
//<body> ::= <stmt> | BEGIN <stmt-list> END

    public Boolean body(Object arr[][], int i) {
        if (stmt(arr, i)) {
            i = getI();
           setI(i);
            return true;
        }
        if (arr[i][1] == Keyword.hmp.get("BEGIN")) {
            i++;
            setI(i);
            if (stmtList(arr, i)) {
                i = getI();
                if (arr[i][1] == Keyword.hmp.get("END")) {
                    i++;
                    setI(i);
                    return true;
                }
            }
        }
        return false;
    }

}
