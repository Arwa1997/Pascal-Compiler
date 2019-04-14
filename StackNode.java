/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

/**
 *
 * @author Mariem Adel
 */
public class StackNode {
    int line;
    String type;
    String name;

    public StackNode(int line, String type, String name) {
        this.line = line;
        this.type = type;
        this.name = name;
    } 
}
