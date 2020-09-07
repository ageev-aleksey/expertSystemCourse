package expert.parser;

import com.sun.tools.javac.util.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    private TokenType before;
    private Pattern p = Pattern.compile("(SOLVE)|(RULES)|(TERMS)|(\\()|(\\))|(,)|(;)|(>)|([\\w\\s]+)");
    private Matcher m;
    private Pair<TokenType, String> beforeSymbol;
    private boolean isBefore = false;

    public Tokenizer(String str) {
        if (str != null) {
            m = p.matcher(str);
            before = TokenType.SEMICOLON;
        }
    }
    public Pair<TokenType, String> next() {
        if(isBefore) {
            isBefore = false;
            return beforeSymbol;
        }
        beforeSymbol = _next();
        return beforeSymbol;

    }

    public void back() {
        isBefore = true;
    }

    private Pair<TokenType, String> _next() {
        if(!m.find()) {
            return new Pair<>(TokenType.END, "");
        }
        String s = m.group().trim();
        if(s.length() > 1) {
            if(before.equals(TokenType.BOPEN)) {
                before = TokenType.STRING;
                return new Pair<>(TokenType.STRING, s);
            } else if (before == TokenType.SEMICOLON) {
                if(s.equals("SOLVE")) {
                    before = TokenType.SOLVE;
                    return new Pair<>(TokenType.SOLVE, "SOLVE");
                }
                if(s.equals("RULES")) {
                    before = TokenType.RULES;
                    return new Pair<>(TokenType.RULES, "RULES");
                }
                if(s.equals("TERMS")) {
                    before = TokenType.TERMS;
                    return new Pair<>(TokenType.TERMS, "TERMS");
                }
                before = TokenType.WORD;
                return new Pair<>(TokenType.WORD, s);
            } else {
                before = TokenType.WORD;
                return new Pair<>(TokenType.WORD, s);
            }
        } else {
            switch(s) {
                case "(":
                    before = TokenType.BOPEN;
                    return new Pair<>(TokenType.BOPEN, "(");
                case ")":
                    before = TokenType.BCLOSE;
                    return new Pair<>(TokenType.BCLOSE, ")");
                case ";":
                    before = TokenType.SEMICOLON;
                    return new Pair<>(TokenType.SEMICOLON, ";");
                case ">":
                    before = TokenType.INFERENCE;
                    return new Pair<>(TokenType.INFERENCE, ">");
                case ",":
                    before = TokenType.COMMA;
                    return new Pair<>(TokenType.COMMA, ",");
            }
        }
        return new Pair<>(TokenType.ERROR, s);
    }

}
