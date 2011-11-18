package org.rip.regex.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

public class ComandLineTest {

    @Test
    public void byte_offset_long() {

        String[] args =
            new String[] { "--byte-offset", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void byte_offset_short() {

        String[] args =
            new String[] { "-b", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void file_name_only() {

        String[] args =
            new String[] { "--files-with-matches", "9030899870", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

        args = new String[] { "-l", "9030899870", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );
    }


    public void hold_on_to_this() {

        try {
            // final String cmdString =
            // "java -cp ./target/classes org.rip.regex.grep";
            final String cmdString = "java";
            final Runtime r = Runtime.getRuntime();
            final Process child = r.exec( cmdString );
            final InputStreamReader isr = new InputStreamReader( child.getInputStream() );
            final BufferedReader br = new BufferedReader( isr );
            String line = null;
            while( ( line = br.readLine() ) != null ) {
                System.out.println( line );
            }
            final int res = child.waitFor();
        }
        catch( final InterruptedException ie ) {
            System.out.println( ie.getMessage() );
        }
        catch( final IOException ioe ) {
            System.out.println( ioe.getMessage() );
        }

    }

    @Test
    public void line_long() {

        String[] args = new String[] { "--line-regexp", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

        args = new String[] { "--line-regexp", "ACT", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void line_number() {

        String[] args =
            new String[] { "--line-number", "9030899870", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

        args = new String[] { "-n", "9030899870", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );
    }

    @Test
    public void line_short() {

        String[] args = new String[] { "-x", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

        args = new String[] { "-x", "ACT", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void max_count_long() {

        String[] args =
            new String[] { "--max-count=10", "1000", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void max_count_short() {

        String[] args =
            new String[] { "-m", "10", "1000", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    public void no_args() {

        final String[] args = new String[] {};
        org.rip.regex.grep.main( args );
    }

    @Test
    public void no_file_name_long() {

        String[] args =
            new String[] { "-n", "--no-filename", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void no_file_name_short() {

        String[] args =
            new String[] { "-n", "-h", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void only_long() {

        String[] args =
            new String[] { "--only-matching", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void only_short() {

        String[] args =
            new String[] { "-o", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void print_file_name_long() {

        String[] args =
            new String[] { "--with-filename", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void print_file_name_short() {

        String[] args =
            new String[] { "-H", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void regex_file_long() {

        String[] args = new String[] { "--file=./src/test/data/reg.txt", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );
    }

    @Test
    public void regex_file_short() {

        String[] args = new String[] { "-f", "./src/test/data/reg.txt", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );


    }

    @Test
    public void regex_option_long() {

        String[] args = new String[] { "--regexp=9030899870", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );
    }

    @Test
    public void regex_option_short() {

        String[] args = new String[] { "-e", "9030899870", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void simple_case_insensitive_search() {

        String[] args = new String[] { "CALCULATION_RULES", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

        args = new String[] { "-i", "calculation_rules", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

        args = new String[] { "--ignore-case", "calculation_rules", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );
    }

    @Test
    public void simple_pattern_and_file() {

        final String[] args = new String[] { "9030899870", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );
    }

    @Test
    public void suppress_long() {

        String[] args =
            new String[] { "-w", "-x", "--no-messages", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void suppress_short() {

        String[] args =
            new String[] { "-w", "-x", "-s", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void test_help() {

        String[] args = new String[] { "--help", "9030899870", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );
    }

    @Test
    public void test_version() {

        String[] args = new String[] { "--version", "9030899870", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

        args = new String[] { "-V", "9030899870", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );
    }

    @Test
    public void word_long() {

        String[] args = new String[] { "--word-regexp", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

        args = new String[] { "--word-regexp", "ACT", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void word_short() {

        String[] args = new String[] { "-w", "ACTIVE", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

        args = new String[] { "-w", "ACT", "./src/test/data/AnadysRecRulesTest.xml" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void directories_recurse_long() {

        String[] args =
            new String[] { "--directories=recurse", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void directories_recurse_short() {

        String[] args =
            new String[] { "-d", "recurse", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void directories_recurse_short_R() {

        String[] args =
            new String[] { "-R", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void directories_recurse_short_r() {

        String[] args =
            new String[] { "-r", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void directories_recurse_recurse() {

        String[] args =
            new String[] { "--recursive", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }



    @Test
    public void include_file() {

        String[] args =
            new String[] { "-r", "--include=*.xml", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void exclude() {

        String[] args =
            new String[] { "-r", "--exclude=A*", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void exclude_from() {

        String[] args =
            new String[] { "-r", "--exclude-from=./src/test/data/excludes.txt", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void exclude_dir() {

        String[] args =
            new String[] { "-r", "--exclude-dir=d*", "ACTIVE", "./src/test" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void print_without_match_short() {

        String[] args =
            new String[] { "-r", "-L", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void print_without_match_long() {

        String[] args =
            new String[] { "-r", "--files-without-match", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void print_count_only_short() {

        String[] args = new String[] { "-r", "-c", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void print_count_only_long() {

        String[] args = new String[] { "-r", "--count", "ACTIVE", "./src/test/data" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void before_context_short_c() {

        String[] args = new String[] { "-B", "2", "-A", "2", "ACTIVE", "./src/test/data/context.txt" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void after_context_short_c() {

        String[] args = new String[] { "-A", "2", "ACTIVE", "./src/test/data/context.txt" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void context_short_c() {

        String[] args = new String[] { "-C", "2", "ACTIVE", "./src/test/data/context.txt" };
        org.rip.regex.grep.main( args );

    }

    @Test
    public void before_short_c() {

        String[] args = new String[] { "-B", "2", "ACTIVE", "./src/test/data/context.txt" };
        org.rip.regex.grep.main( args );

    }

}