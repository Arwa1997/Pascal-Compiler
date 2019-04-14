/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.ArrayList;
import java.util.regex.Pattern;
import jdk.nashorn.internal.parser.TokenType;

/**
 *
 * @author Mariem Adel
 */
public class TokenContent {

    public TokenContent(Pattern compile, TokenType STRING) {
    }
 ArrayList tokenContents;
 String str;
 ArrayList<TokenContent> TokenContent;  

    TokenContent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

public  void Tokenizer(String str) {
    this.tokenContents = new ArrayList<TokenContent>();
    this.str = str;
    
    // Name = Initial Other*
    String initial = "[a-zA-Z] | _ | :";
    String other = initial + " | [0-9] | - | \\.";
    String name = initial + "(" + other + ")*";
    tokenContents.add(new TokenContent(Pattern.compile(name), TokenType.OBJECT));
    // String = " " (Char | ')* " | ' (Char | ")* '
    String ordinary = "(?!(< | > | \" | ' | &))";
    String special = "&lt; | &gt; | &quot; | &apos; | &amp;";
    String reference = "&#[0-9]+; | &#x([0-9] | [a-fA-F])+;";
    String character = ordinary + " | " + special + " | " + reference;
    String string = "\"(" + character + " | " + "')* \" | ' (\"" + character + " | " + "\")* '";
    tokenContents.add(new TokenContent(Pattern.compile(string), TokenType.STRING));
    // Data = Char+
    String data = character + "+"; 
    tokenContents.add(new TokenContent(Pattern.compile(data), TokenType.OBJECT)); 
    // The symbol <
    tokenContents.add(new TokenContent(Pattern.compile("<"), TokenType.OBJECT));
    // The symbol >
    tokenContents.add(new TokenContent(Pattern.compile(">"), TokenType.OBJECT));
    // The symbol </
    tokenContents.add(new TokenContent(Pattern.compile("</"), TokenType.OBJECT));
    // The symbol />
    tokenContents.add(new TokenContent(Pattern.compile("/>"), TokenType.OBJECT));  
    // The symbol = 
    tokenContents.add(new TokenContent(Pattern.compile("="), TokenType.EQ));    
}
  
}
