package org.rip.regex.cmd;

import org.junit.Test;


public class SingleTest {




    @Test
    public void before_context_short_m() {

        String[] args = new String[] { "-r", "-C", "2", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

}
