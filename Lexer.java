package compiler;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Lexer {
  private ArrayList<Token> tokens;
  public static enum TokenType {
    // Token types cannot have underscores
     Sc(";"),Comma(","),NUMBER("-?[0-9]+"),KEYWORDS("DO|IF|PROGRAM|VAR|FOR|BEGIN|END.|END|READ|WRITE|TO"),lb("\\("),rb("\\)"), BINARYOP("\\*|\\/|\\+|\\-|\\:="),COMPOP("<|>|<=|>=|=|!="), WHITESPACE("[ \t\f\r\n]+"),IDENTIFIER("[a-zA-Z0-9\\-#\\/%&\\_]+");
    

    public final String pattern;

    private TokenType(String pattern) {
      this.pattern = pattern;
    }
  }

  public static class Token {
    public TokenType type;
    public String data;

    public Token(TokenType type, String data) {
      this.type = type;
      this.data = data;
    }

    @Override
    public String toString() {
      return String.format("%s %s", type.name(), data);
    }
  }

  public  ArrayList<Token> lex(String input) {
    // The tokens to return
    
      tokens = new ArrayList<Token>();
    // Lexer logic begins here
    StringBuffer tokenPatternsBuffer = new StringBuffer();
    for (TokenType tokenType : TokenType.values())
      tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
    Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

    // Begin matching tokens
    Matcher matcher = tokenPatterns.matcher(input);
    while (matcher.find()) {
         if (matcher.group(TokenType.KEYWORDS.name()) != null) {
        tokens.add(new Token(TokenType.KEYWORDS, matcher.group(TokenType.KEYWORDS.name())));
        
      }
         else if (matcher.group(TokenType.BINARYOP.name()) != null) {
        tokens.add(new Token(TokenType.BINARYOP, matcher.group(TokenType.BINARYOP.name())));
        
      }
        else if (matcher.group(TokenType.IDENTIFIER.name()) != null) {
        tokens.add(new Token(TokenType.IDENTIFIER, matcher.group(TokenType.IDENTIFIER.name())));
        
      }
        else if (matcher.group(TokenType.NUMBER.name()) != null) {
        tokens.add(new Token(TokenType.NUMBER, matcher.group(TokenType.NUMBER.name())));
         
      }
        else if (matcher.group(TokenType.lb.name()) != null) {
        tokens.add(new Token(TokenType.lb, matcher.group(TokenType.lb.name())));
        
        continue;
         
      }else if (matcher.group(TokenType.rb.name()) != null) {
        tokens.add(new Token(TokenType.rb, matcher.group(TokenType.rb.name())));
        
        continue;
         
      }else if (matcher.group(TokenType.Comma.name()) != null) {
        tokens.add(new Token(TokenType.Comma, matcher.group(TokenType.Comma.name())));
        
        continue;
         
      }else if (matcher.group(TokenType.WHITESPACE.name()) != null)
        continue;
    }

    return tokens;
  }
}