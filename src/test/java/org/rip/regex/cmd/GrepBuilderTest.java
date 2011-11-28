package org.rip.regex.cmd;

import org.junit.Test;
import org.rip.regex.GrepBuilder;

public class GrepBuilderTest {

    @Test
    public void byte_offset_long() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().printByteOffset().addRegexes( "ACTIVE" ).addFile( "./src/test/data/AnadysRecRulesTest.xml" )
        .build().execute();

    }


    @Test
    public void file_name_only() {
        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().printFileNameOnly().addRegexes( "9030899870" )
        .addFile( "./src/test/data/AnadysRecRulesTest.xml" ).build().execute();

    }


    @Test
    public void line_long() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().lineRegex().addRegexes( "ACTIVE" ).addFile( "./src/test/data/AnadysRecRulesTest.xml" )
        .build().execute();

    }

    @Test
    public void line_number() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().printLineNumber().addRegexes( "9030899870" )
        .addFile( "./src/test/data/AnadysRecRulesTest.xml" );

    }

    @Test
    public void line_short() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().lineRegex().addRegexes( "ACTIVE" ).addFile( "./src/test/data/AnadysRecRulesTest.xml" )
        .build().execute();

    }

    @Test
    public void max_count_long() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().maxCount( 10 ).addRegexes( "1000" ).addFile( "./src/test/data/AnadysRecRulesTest.xml" )
        .build().execute();

    }

    @Test
    public void no_file_name_long() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().noFilename().addRegexes( "ACTIVE" ).addFile( "./src/test/data/AnadysRecRulesTest.xml" )
        .build().execute();

    }

    @Test
    public void no_file_name_short() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().printLineNumber().noFilename().addRegexes( "ACTIVE" )
        .addFile( "./src/test/data/AnadysRecRulesTest.xml" )
        .build().execute();

    }

    @Test
    public void only_long() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().onlyMatching().addRegexes( "ACTIVE" ).addFile( "./src/test/data/AnadysRecRulesTest.xml" )
        .build().execute();

    }

    @Test
    public void print_file_name_long() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().withFilename().addRegexes( "ACTIVE" ).addFile( "./src/test/data/AnadysRecRulesTest.xml" )
        .build().execute();

    }

    @Test
    public void regex_file_long() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().addRegexFromFiles( "./src/test/data/reg.txt" )
        .addFile( "./src/test/data/AnadysRecRulesTest.xml" ).build().execute();

    }

    @Test
    public void simple_case_insensitive_search() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().ignoreCase().addRegexes( "calculation_rules" )
        .addFile( "./src/test/data/AnadysRecRulesTest.xml" ).build().execute();

    }

    @Test
    public void suppress_long() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().wordRegex().lineRegex().noMessages().addRegexes( "ACTIVE" )
        .addFile( "./src/test/data/AnadysRecRulesTest.xml" )
        .build().execute();

    }



    @Test
    public void word_long() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().wordRegex().addRegexes( "ACTIVE" ).addFile( "./src/test/data/AnadysRecRulesTest.xml" )
        .build().execute();

    }


    @Test
    public void directories_recurse_long() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().recurseDirectories().addRegexes( "ACTIVE" ).addFile( "./src/test/data" )
        .build().execute();

    }


    @Test
    public void include_file() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().recurseDirectories().include( "*.xml" ).addRegexes( "ACTIVE" ).addFile( "./src/test/data" )
        .build().execute();

    }

    @Test
    public void exclude() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().recurseDirectories().exclude( "--exclude=A*" ).addRegexes( "ACTIVE" )
        .addFile( "./src/test/data" ).build().execute();

    }

    @Test
    public void exclude_from() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().recurseDirectories().excludeFrom( "./src/test/data/excludes.txt" ).addRegexes( "ACTIVE" )
        .addFile( "./src/test/data" ).build().execute();

    }

    @Test
    public void exclude_dir() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().recurseDirectories().excludeDir( "d*" ).addRegexes( "ACTIVE" ).addFile( "./src/test" )
        .build().execute();

    }

    @Test
    public void print_without_match_short() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().recurseDirectories().printFilesWithoutMatch().addRegexes( "ACTIVE" )
        .addFile( "./src/test/data" ).build().execute();

    }

    @Test
    public void print_count_only_short() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().recurseDirectories().printCountOnly().addRegexes( "ACTIVE" ).addFile( "./src/test/data" )
        .build().execute();

    }

    @Test
    public void before_context_short_c() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().beforeContext( 2 ).afterContext( 2 ).addRegexes( "ACTIVE" )
        .addFile( "./src/test/data/context.txt" ).build().execute();

    }

    @Test
    public void after_context_short_c() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().afterContext( 2 ).addRegexes( "ACTIVE" )
        .addFile( "./src/test/data/context.txt" ).build().execute();

    }

    @Test
    public void context_short_c() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().context( 2 ).afterContext( 2 ).addRegexes( "ACTIVE" )
        .addFile( "./src/test/data/context.txt" ).build().execute();

    }

    @Test
    public void before_short_c() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().beforeContext( 2 ).addRegexes( "ACTIVE" )
        .addFile( "./src/test/data/context.txt" ).build().execute();

    }

    @Test
    public void before_context_short_m() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().recurseDirectories().context( 2 ).addRegexes( "ACTIVE" ).addFile( "./src/test/data" )
        .build().execute();

    }

    @Test
    public void dummy_file_1() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().addRegexes( "ACTIVE" ).addFile( "./src/test/data/dummy" )
        .addFile( "./src/test/data/AnadysRecRulesTest.xml" ).build().execute();

    }

    @Test
    public void regexp_file_multi_1() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().addRegexFromFiles( "./src/test/data/multi.txt" )
        .addFile( "./src/test/data/AnadysRecRulesTest.xml" ).build().execute();

    }

    @Test
    public void regexp_file_multi_reverse_1() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().invertMatch().addRegexFromFiles( "./src/test/data/multi.txt" )
        .addFile( "./src/test/data/AnadysRecRulesTest.xml" ).build().execute();

    }

    @Test
    public void exclude_1() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().exclude( "*.txt" ).addRegexes( "ACTIVE" ).addFile( "./src/test/data/multi.txt" ).build()
        .execute();

    }

    @Test
    public void exclude_2() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().recurseDirectories().exclude( "*.txt" ).addRegexes( "ACTIVE" ).addFile( "./src" ).build()
        .execute();

    }

    @Test
    public void exclude_3() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().recurseDirectories().exclude( "*.txt", "*.java" ).addRegexes( "ACTIVE" ).addFile( "./src" )
        .build().execute();

    }

    @Test
    public void include_recurse_1() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().recurseDirectories().include( "*.txt" ).addRegexes( "ACTIVE" ).addFile( "./src" ).build()
        .execute();

    }

    @Test
    public void exclude_dir_2() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().recurseDirectories().excludeDir( "java" ).addRegexes( "ACTIVE" ).addFile( "./src/test" )
        .build().execute();

    }

    @Test
    public void file_long_1() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().addLongRegexFromFiles( "./src/test/data/master-3.3.3.1.rgx" )
            .addFile( "./src/test/data/master-3.3.3.1.dat" ).build().execute();

    }

    @Test
    public void file_long_2() {

        System.out.println( "start:" + ( new Throwable() ).getStackTrace()[0].getMethodName() );
        GrepBuilder.start().addLongRegexFromFiles( "./src/test/data/long-regex.txt" )
            .addFile( "./src/test/data/csv.txt" ).build().execute();

    }
}
