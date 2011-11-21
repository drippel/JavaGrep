package org.rip.regex.cmd;

import org.junit.Test;


public class SingleTest {




    @Test
    public void regexp_short_1() {

        String[] args =
            new String[] { "-e", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void regexp_short_2() {

        String[] args =
            new String[] { "-e", "ACTIVE", "dataset", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void dummy_file_1() {

        String[] args =
            new String[] { "ACTIVE", "./src/test/data/dummy", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

}
