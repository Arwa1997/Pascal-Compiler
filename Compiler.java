/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Mariem Adel
 */
public class Compiler {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        Parser p = new Parser();
        InfixToPostFixEvalution in = new InfixToPostFixEvalution();
        CodeGenerator r= new CodeGenerator();
        Object arr[][] = p.getArr();
        p.prog(arr, 0);
//         try {
//            Assembler asm = new Assembler();
//
//            asm.assemble(new File("codewrite.txt"), new File("codewriteer.txt"));
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
       
    }

}
