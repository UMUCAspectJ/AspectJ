package org.nb.ajlexerparser;

import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import org.netbeans.modules.parsing.spi.Parser;
import org.nb.ajlexerparser.lexer.AJTokenId;
import org.nb.ajlexerparser.parser.AJParser;

/**
 * @author stephanie.hammond
 */

@LanguageRegistration(mimeType = "text/x-aj")
public class AJLanguage extends DefaultLanguageConfig {

    @Override
    public Language getLexerLanguage() {
        return AJTokenId.getLanguage();
    }

    @Override
    public String getDisplayName() {
        return "AJ";
    }
    
    @Override
    public Parser getParser() {
        return new AJParser();
    }    
}
