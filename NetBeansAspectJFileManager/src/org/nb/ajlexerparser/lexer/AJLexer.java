package org.nb.ajlexerparser.lexer;

import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.nb.ajlexerparser.jcclexer.JavaCharStream;
import org.nb.ajlexerparser.jcclexer.JavaParserTokenManager;
import org.nb.ajlexerparser.jcclexer.Token;
/**
 * @author stephanie.hammond
 */
public class AJLexer implements Lexer<AJTokenId> {
    private LexerRestartInfo<AJTokenId> info;
    private JavaParserTokenManager javaParserTokenManager;

    AJLexer(LexerRestartInfo<AJTokenId> info) {
        this.info = info;
        JavaCharStream stream = new JavaCharStream(info.input());
        javaParserTokenManager = new JavaParserTokenManager(stream);
    }

    @Override
    public org.netbeans.api.lexer.Token<AJTokenId> nextToken() {
        Token token = javaParserTokenManager.getNextToken();
        if (info.input().readLength() < 1) {
            return null;
        }
        return info.tokenFactory().createToken(AJLanguageHierarchy.getToken(token.kind));
    }

    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }
}
