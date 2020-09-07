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
public class Parser {
    private Tokenizer mTok;
    private StepExecutor mExecutor;

    public void parse(Tokenizer tokenizer, StepExecutor executor) {
        mTok = tokenizer;
        mExecutor = executor;
        programme();
        mTok = null;
        mExecutor = null;
    }

    // <programme> ::= <operator_list>
    private void programme() {

    }
    // <operator_list> ::= <operator> ";" <operator_list> | EPSILON
    private void operator_list() {
        operator();
        Pair<TokenType, String> tok = mTok.next();
        if (tok.fst == TokenType.SEMICOLON) {
            operator_list();
        } else if (tok.fst == TokenType.END) {
            return;
        } else {
            // TODO(ageev) что делать при ошибке?
        }
    }

    // <operator> ::= <term><def_operators> | "RULES" | "TERMS" | "SOLVE" <term_list>
    private void operator() {
        Pair<TokenType, String> tok = mTok.next();
        if(tok.fst == TokenType.WORD) {
            def_operators(tok.snd);
        } else if (tok.fst == TokenType.RULES) {
            mExecutor.rules();
        } else if (tok.fst == TokenType.TERMS) {
            mExecutor.terms();
        } else if (tok.fst == TokenType.SOLVE) {
            List<String> terms = new LinkedList<>();
            term_list(terms);
            mExecutor.solve(terms);
        }
    }

    // <def_operators> ::= '(' <string> ')' //define term
    //                   | ',' <term_list> ">" <term> //define rule
    private void def_operators(String tokenName) {
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
                    return mExecutor.createRule(tokenNames, tok.snd);
                }
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
                }
            }
        }
        return false;
    }

    // <term_list> ::= <term> "," <term_list> | <term>
    private void term_list(List<String> terms) {
        Pair<TokenType, String> tok = mTok.next();
        if(tok.fst == TokenType.WORD) {
            terms.add(tok.snd);
            while((tok = mTok.next()).fst == TokenType.COMMA) {
                tok = mTok.next();
                if(tok.fst == TokenType.WORD) {
                    terms.add(tok.snd);
                } else {
                    return false;
                }
            }
            terms.back();
            return true;
        }
        return false;
    }


}
