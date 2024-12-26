package com.project.tikiriCi.parser;

import java.util.List;

import com.project.tikiriCi.main.Token;

public class TokenParserUtils {
    /**
     * Pick the derivation best suited for by looking at the next token --> LLK(1)
     * @param derivations
     * @param nextToken
     * @return
     */
    public static Derivation lookAHeadDerivationPicker(List<Derivation> derivations, Token nextToken) {
        Derivation pickedDerivation = new Derivation();
        for (Derivation derivation : derivations) {
             GrammerElement firstGrammerElement = derivation.peekDerivation();
            if(firstGrammerElement.getIsTerminal()){
                if(nextToken.getTokenType() == firstGrammerElement.getTokenType()){
                    pickedDerivation = derivation;
                    break;
                }
            } else {
                //look the next token by matching token within two iteration down
                List<Derivation> firstGrammerElementDerivation = firstGrammerElement.getDerivation();
                for (Derivation deriv : firstGrammerElementDerivation) {
                    firstGrammerElement = deriv.peekDerivation();
                    if(firstGrammerElement.getIsTerminal()){
                        if(nextToken.getTokenType() == firstGrammerElement.getTokenType()){
                            pickedDerivation = derivation;
                            break;
                        }
                    } else{
                        List<Derivation> fgrammerChildren = firstGrammerElement.getDerivation();
                        for (Derivation derivationl : fgrammerChildren) {
                            GrammerElement firstElement = derivationl.peekDerivation();
                            if(nextToken.getTokenType() == firstElement.getTokenType()){
                                pickedDerivation = derivation;
                                break;      
                            }
                        }
                    }
                }
            }
        }
        return pickedDerivation;
    } 
}
