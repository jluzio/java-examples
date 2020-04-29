package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import com.example.java.playground.utils.Strings;
import org.junit.jupiter.api.Test;

public class TextBlockTest extends AbstractTest {
    @Test
    public void test_default() {
        var block1 = """
                line 1
                line              2
                line 3
                """;
        log.info("Block:\r\n{}", block1);

        var block2 = """
                  line 1   
                line              2
                line 3
                """;
        // indent is stripped (and is equal to first character in the block of any line)
        // trailing spaces are trimmed
        log.info("Block:\r\n{}", Strings.toTextBlock(block2.lines(), Strings::quote));

        var block3 = """
                  line 1
                line              2
                line 3
                """;
        log.info("Block:\r\n{}", Strings.toTextBlock(block3.lines(), Strings::quote));
    }

    @Test
    public void test_utils() {
        String htmlTextBlock = "<html>   \n"+
                "\t<body>\t\t \n"+
                "\t\t<p>Hello</p>  \t \n"+
                "\t</body> \n"+
                "</html>";
        log.info("Block:\r\n{}", htmlTextBlock.replace(" ", "*"));
        log.info("Block:\r\n{}", htmlTextBlock.stripIndent().replace(" ", "*"));
    }

}
