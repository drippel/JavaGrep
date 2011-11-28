package org.rip.regex.cmd;

import org.junit.Test;


public class SingleTest {




    @Test
    public void file_long_1() {

        String[] args =
            new String[] { "--file-long=./src/test/data/master-3.3.3.1.rgx", "./src/test/data/master-3.3.3.1.dat" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void file_long_2() {

        String[] args =
            new String[] { "--file-long=./src/test/data/long-regex.txt", "./src/test/data/csv.txt" };
        org.rip.regex.grep.main( args );

    }








}
