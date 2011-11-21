package org.rip.regex.cmd;

import org.junit.Test;


public class SingleTest {




    @Test
    public void exclude_from() {

        String[] args =
            new String[] { "-r", "--exclude-from=./src/test/data/excludes.txt", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

}
