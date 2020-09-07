package expert.parser;

import com.sun.tools.javac.util.Pair;
import expert.productional.Dictionary;
import expert.productional.KnowledgeBase;
import expert.productional.Production;
import expert.productional.Term;
import expert.productional.impl.UserProduction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// TODO(ageev) Добавить откат токенайзера на 1 шаг назад
// TODO(ageev) Добавить обработку сообщения об ошибке разбора
public class Parser {
    private Tokenizer mTok;
    private StepExecutor mExecutor;
    private String error;

    public void parse(Tokenizer tokenizer, StepExecutor executor) {
        mTok = tokenizer;
        mExecutor = executor;
        error = null;
        boolean res = programme();
        if(!res) {
            executor.error(error);
        }
        mTok = null;
        mExecutor = null;
    }

    // <programme> ::= <operator_list>
    private boolean programme() {
        return operator_list();
    }
    // <operator_list> ::= <operator> ";" <operator_list> | EPSILON
    private boolean operator_list() {
        boolean res = operator();
        if(!res) return false;
        Pair<TokenType, String> tok = mTok.next();
        if (tok.fst == TokenType.SEMICOLON) {
            return operator_list();
        } else if (tok.fst == TokenType.END) {
            return true;
        } else {
            error = errorUnexpected(tok, "';' or EOF");
            return false;
        }
    }

    // <operator> ::= <term><def_operators> | "RULES" | "TERMS" | "SOLVE" <term_list>
    private boolean operator() {
        Pair<TokenType, String> tok = mTok.next();
        if(tok.fst == TokenType.WORD) {
           return def_operators(tok.snd);
        } else if (tok.fst == TokenType.RULES) {
            mExecutor.rules();
            return true;
        } else if (tok.fst == TokenType.TERMS) {
            mExecutor.terms();
            return true;
        } else if (tok.fst == TokenType.SOLVE) {
            List<String> terms = new LinkedList<>();
            term_list(terms);
            mExecutor.solve(terms);
            return true;
        } else if(tok.fst == TokenType.END) {
            return true;
        }
        error = errorUnexpected(tok, "<WORD>, 'RULES', 'TERMS', 'SOLVE'");
        return false;
    }

    // <def_operators> ::= '(' <string> ')' //define term
    //                   | ',' <term_list> ">" <term> //define rule
    private boolean def_operators(String tokenName) {
        Pair<TokenType, String> tok = mTok.next();
        //define rule
        if (tok.fst == TokenType.COMMA) {
            List<String> tokenNames = new LinkedList<>();
            tokenNames.add(tokenName);
            term_list(tokenNames);
            tok = mTok.next();
            if(tok.fst == TokenType.INFERENCE) {
                tok = mTok.next();
                if(tok.fst == TokenType.WORD) {
                    mExecutor.createRule(tokenNames, tok.snd);
                    return true;
                } else {
                    error = errorUnexpected(tok, "<WORD>");
                    return false;
                }
            } else {
                error = errorUnexpected(tok, "'>'");
                return false;
            }
        }
        // define term
        else if (tok.fst == TokenType.BOPEN) {
            tok = mTok.next();
            if (tok.fst == TokenType.STRING) {
                mExecutor.createTerm(tokenName, tok.snd);
                tok = mTok.next();
                if (tok.fst == TokenType.BCLOSE) {
                    return true;
                } else {
                    error = errorUnexpected(tok, "')'");
                    return false;
                }
            } else {
                error = errorUnexpected(tok, "<STRING>");
                return false;
            }
        }
        error = errorUnexpected(tok, "',' or '('");
        return false;
    }

    // <term_list> ::= <term> "," <term_list> | <term>
    private boolean term_list(List<String> terms) {
        Pair<TokenType, String> tok = mTok.next();
        if(tok.fst == TokenType.WORD) {
            terms.add(tok.snd);
            while((tok = mTok.next()).fst == TokenType.COMMA) {
                tok = mTok.next();
                if(tok.fst == TokenType.WORD) {
                    terms.add(tok.snd);
                } else {
                    error = errorUnexpected(tok, "<TERM>");
                    return false;
                }
            }
            mTok.back();
            return true;
        }
        error = errorUnexpected(tok, "<TERM>");
        return false;
    }

    private String errorUnexpected(Pair<TokenType, String> token, String expected) {
        return "Unexpected symbol -> {" + token.fst + "; " + token.snd + "}; Expected " + expected;
    }


}
