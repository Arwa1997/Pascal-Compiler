/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.HashMap;

/**
 *
 * @author user7
 */
public class Keyword extends Token {

    public final String lexeres;
    public static final HashMap<String, Integer> hmp = new HashMap<String, Integer>();

    public Keyword(String lexeres, int tag) {
        super(tag);
        this.lexeres = lexeres;
        hmp.put(lexeres, tag);

    }

    public HashMap<String, Integer> getHmp() {
        return hmp;
    }
    public static final Keyword PROGRAM = new Keyword("PROGRAM", Tag.PROGRAM),
            VAR = new Keyword("VAR", Tag.VAR),
            BEGIN = new Keyword("BEGIN", Tag.BEGIN),
            END = new Keyword("END", Tag.END),
            END2 = new Keyword("END.", Tag.END2),
            FOR = new Keyword("FOR", Tag.FOR),
            READ = new Keyword("READ", Tag.READ),
            WRITE = new Keyword("WRITE", Tag.WRITE),
            IF = new Keyword("IF", Tag.IF),
            TO = new Keyword("TO", Tag.TO),
            SC = new Keyword(";", Tag.SC),
            DO = new Keyword("DO", Tag.DO),
            ASSIGN = new Keyword(":=", Tag.ASSIGN),
            plus = new Keyword("+", Tag.PLUS),
            lb = new Keyword("(", Tag.LB),
            RB = new Keyword(")", Tag.RB),
            ASTR = new Keyword("*", Tag.ASTR),
            ID = new Keyword("id", Tag.ID),
            imm= new Keyword("imm",Tag.imm),
            comma=new Keyword(",",Tag.comma);

}
