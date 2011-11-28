package org.rip.regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class grep {

    public grep() {

        grepPatterns = new ArrayList< Pattern >();
        filesToProcess = new ArrayList< File >();

        regexes = new ArrayList< String >();
    }

    private List< Pattern >     grepPatterns;

    private List< String >  regexes;

    private List< File >        filesToProcess;



    public Pattern compilePattern( final String pat ) {

        int flags = Pattern.COMMENTS;

        String pattern = new String( pat );

        if( ignoreCase ) {
            flags |= Pattern.CASE_INSENSITIVE;
        }

        // we are ignoring line-regex if both are supplied
        if( wordRegex ) {
            // poor mans way to do it
            pattern = "\\b" + pattern + "\\b";
        }
        else {
            if( lineRegex ) {

                // poor mans way to do it
                pattern = "^" + pattern + "$";
            }
        }

        return Pattern.compile( pattern, flags );

    }


    private void printMessage( final String msg ) {

        if( !quiet ) {
            System.out.println( msg );
        }
    }

    private void grepFiles() {

        // at this point the list was expanded into file names
        for( final File f : filesToProcess ) {

            grepFile( f );
        }

    }

    private void transferOptions( final CommandLine commandLine ) {

        // Regexp selection and interpretation

        if( commandLine.hasOption( 'e' ) || commandLine.hasOption( "regexp" ) ) {
            regexes.addAll( Arrays.asList( commandLine.getOptionValues( 'e' ) ) );
        }

        if( commandLine.hasOption( 'f' ) || commandLine.hasOption( "file" ) ) {
            readRegexFromFile( commandLine.getOptionValues( 'f' ) );
        }

        if( commandLine.hasOption( "file-long" ) ) {
            readLongRegexFromFile( commandLine.getOptionValues( "file-long" ) );
        }

        ignoreCase = ( commandLine.hasOption( 'i' ) || commandLine.hasOption( "ignore-case" ) );

        wordRegex = commandLine.hasOption( 'w' ) || commandLine.hasOption( "word-regexp" );

        lineRegex = commandLine.hasOption( 'x' ) || commandLine.hasOption( "line-regexp" );

        // Miscellaneous

        noMessages = commandLine.hasOption( 's' ) || commandLine.hasOption( "no-messages" );

        invertMatch = ( commandLine.hasOption( 'v' ) || commandLine.hasOption( "invert-match" ) );

        // Output control
        maxCount = 0;
        if( commandLine.hasOption( 'm' ) || commandLine.hasOption( "max-count" ) ) {
            String s = commandLine.getOptionValue( 'm' );
            maxCount = Long.parseLong( s );
        }

        printByteOffset = ( commandLine.hasOption( 'b' ) || commandLine.hasOption( "byte-offset" ) );

        printLineNumber = commandLine.hasOption( 'n' ) || commandLine.hasOption( "line-number" );

        printFileName = false;
        if( ( commandLine.hasOption( 'H' ) || commandLine.hasOption( "with-filename" ) ) ) {
            printFileName = true;
        }

        if( ( commandLine.hasOption( 'h' ) || commandLine.hasOption( "no-filename" ) ) ) {
            printFileName = false;
        }

        printMatchOnly = commandLine.hasOption( 'o' ) || commandLine.hasOption( "only-matching" );

        quiet =
            ( commandLine.hasOption( 'q' ) || commandLine.hasOption( "--quiet" ) || commandLine.hasOption( "silent" ) );

        recurseDirectories = false;
        skipDirectories = false;
        if( commandLine.hasOption( 'd' ) || commandLine.hasOption( "directories" ) ) {
            String val = commandLine.getOptionValue( 'd' );
            if( StringUtils.endsWithIgnoreCase( val, "recurse" ) ) {
                recurseDirectories = true;
            }
            if( StringUtils.endsWithIgnoreCase( val, "skip" ) ) {
                skipDirectories = true;
            }
        }

        if( commandLine.hasOption( 'R' )
            || commandLine.hasOption( 'r' )
            || commandLine.hasOption( "recursive" ) ) {
            recurseDirectories = true;
        }

        useInclude = false;
        useExclude = false;
        if( commandLine.hasOption( "include" ) ) {
            useInclude = true;
            includeFilePatterns.addAll( Arrays.asList( commandLine.getOptionValues( "include" ) ) );
        }

        if( commandLine.hasOption( "include-from" ) ) {
            useInclude = true;
            readIncludesFrom( commandLine.getOptionValues( "include-from" ) );
        }

        if( commandLine.hasOption( "exclude" ) ) {
            useExclude = true;
            excludeFilePatterns.addAll( Arrays.asList( commandLine.getOptionValues( "exclude" ) ) );
        }

        if( commandLine.hasOption( "exclude-from" ) ) {
            useExclude = true;
            readExcludeFrom( commandLine.getOptionValues( "exclude-from" ) );
        }

        if( commandLine.hasOption( "exclude-dir" ) ) {
            excludeDirPatterns.addAll( Arrays.asList( commandLine.getOptionValues( "exclude-dir" ) ) );
        }

        printFilesWithoutMatch = commandLine.hasOption( 'L' ) || commandLine.hasOption( "files-without-match" );

        printFileNameOnly = ( commandLine.hasOption( 'l' ) || commandLine.hasOption( "files-with-matches" ) );

        printCountOnly = ( commandLine.hasOption( 'c' ) || commandLine.hasOption( "count" ) );

        // Context control

        long context = 0;

        if( commandLine.hasOption( 'C' ) || commandLine.hasOption( "context" ) ) {
            String s = commandLine.getOptionValue( 'C' );
            context = Long.parseLong( s );
        }

        if( commandLine.hasOption( 'A' ) || commandLine.hasOption( "after-context" ) ) {
            String s = commandLine.getOptionValue( 'A' );
            afterContext = Long.parseLong( s );
        }
        else {
            afterContext = context;
        }

        if( commandLine.hasOption( 'B' ) || commandLine.hasOption( "before-context" ) ) {
            String s = commandLine.getOptionValue( 'B' );
            beforeContext = Long.parseLong( s );
        }
        else {
            beforeContext = context;
        }

    }

    boolean                      invertMatch            = false;

    boolean                      ignoreCase             = false;

    long                         maxCount               = 0;

    boolean              printFileNameOnly      = false;

    boolean                      printByteOffset        = false;

    boolean                      quiet                  = false;

    boolean              printCountOnly         = false;

    boolean              printFilesWithoutMatch = false;

    boolean                      wordRegex              = false;

    boolean                      lineRegex              = false;

    boolean                      noMessages             = false;

    boolean                      printFileName          = false;

    boolean                      printMatchOnly         = false;

    boolean                      printLineNumber        = false;

    boolean                      recurseDirectories     = false;

    boolean                      skipDirectories        = false;

    final List< String >         includeFilePatterns    = new ArrayList< String >();

    List< String >               excludeFilePatterns    = new ArrayList< String >();

    final List< String > excludeDirPatterns     = new ArrayList< String >();

    boolean                      useInclude             = false;

    boolean                      useExclude             = false;

    public static void main( final String[] args ) {

        grep theGrep = new grep();
        grepCommandLine cmd = new grepCommandLine();

        if( ArrayUtils.isEmpty( args ) ) {
            grepCommandLine.usage();
            return;
        }

        // parse the command line
        try {
            final CommandLineParser parser = new PosixParser();
            cmd.setCommandLine( parser.parse( grepCommandLine.grepOptions, args ) );
        }
        catch( final ParseException pe ) {
            System.out.print( pe.getMessage() );
            grepCommandLine.usage();
            return;
        }

        theGrep.transferOptions( cmd.getCommandLine() );

        // if version or help was given then print and quit
        if( cmd.getCommandLine().hasOption( "help" ) ) {
            grepCommandLine.usage();
            return;
        }

        if( cmd.getCommandLine().hasOption( "version" ) || cmd.getCommandLine().hasOption( 'V' ) ) {
            grepCommandLine.version();
            return;
        }

        // get the unprocessed items
        final List argList = cmd.getCommandLine().getArgList();

        // we must have some args besides options
        if( CollectionUtils.isEmpty( argList ) ) {
            // TODO - regex could be passed from file or arg
            // input could be from STDIN
            grepCommandLine.usage();
            return;
        }

        // just to get things started
        // we are assuming: [OPTION]... PATTERN [FILE]...
        // options are pulled out by the command line processor
        // if the regex was not supplied via file or arg
        // we assume its the first arg
        // if the regex was set via arg or file
        // we assume the first arg is a file
        int fileOffset = 0;
        if( CollectionUtils.isEmpty( theGrep.regexes ) ) {
            theGrep.addRegex( (String)argList.get( 0 ) );
            fileOffset++;
        }

        // make sure its a good regex
        try {
            theGrep.compilePatterns();
        }
        catch( final PatternSyntaxException pse ) {
            theGrep.printErrorMessage( "invalid regex syntax\n" + pse.getMessage() );
            return;
        }

        if( CollectionUtils.isEmpty( theGrep.grepPatterns ) ) {
            grepCommandLine.usage();
            return;
        }

        // just to get things going
        List< String > files = argList.subList( fileOffset, argList.size() );
        if( files.size() > 1 ) {
            theGrep.printFileName = true;
        }
        theGrep.processFileArgs( files );

        // ok lets start
        theGrep.grepFiles();

    }

    protected void compilePatterns() {

        for( String s : regexes ) {
            grepPatterns.add( compilePattern( s ) );
        }
    }

    void readExcludeFrom( final String vals[] ) {

        for( String val : vals ) {
            try( BufferedReader br = new BufferedReader( new FileReader( new File( val ) ) ) ) {

                for( String line = br.readLine(); line != null; line = br.readLine() ) {
                    excludeFilePatterns.add( line );
                }

            }
            catch( IOException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    void readIncludesFrom( final String vals[] ) {

        for( String val : vals ) {
            try( BufferedReader br = new BufferedReader( new FileReader( new File( val ) ) ) ) {

                for( String line = br.readLine(); line != null; line = br.readLine() ) {
                    includeFilePatterns.add( line );
                }

            }
            catch( IOException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }





    public void printErrorMessage( final String msg ) {

        if( !noMessages ) {
            System.out.println(msg);
        }
    }







    private void printMatch( final File file, final String line, final long lineNumber,
        final long count, final long byteOffset, final List< String > beforeContextLines,
        final List< String > afterContextLines ) {

        if( quiet || printFilesWithoutMatch || printCountOnly ) {
            return;
        }

        /*
         * if( ( beforeContext() > 0 ) || ( afterContext() > 0 ) ) {
         * System.out.println( "--" );
         * }
         */

        if( beforeContext > 0 ) {
            for( String bc : beforeContextLines ) {
                String s = new String( bc );
                if( filesToProcess.size() > 1 ) {
                    try {
                        s = file.getCanonicalPath() + "-" + s;
                    }
                    catch( IOException ioe ) {}
                }
                System.out.println( s );
            }
        }

        String msg = "";
        if( printFileNamesOnly() ) {
            try {
                System.out.println( String.format( "%1$s", file.getCanonicalPath() ) );
            }
            catch( IOException ioe ) {
                // TODO:
            }
            return;
        }

        if( printFileName ) {
            try {
                msg += file.getCanonicalPath() + ":";
            }
            catch( IOException ioe ) {}
        }

        if( printLineNumber ) {
            msg += lineNumber + ":";
        }

        if( printByteOffset ) {
            msg += byteOffset + ":";
        }

        msg = msg.length() > 1 ? msg + "    " + line : line;

        System.out.println( msg );

        if( afterContext > 0 ) {
            for( String ac : afterContextLines ) {
                String s = new String( ac );
                if( filesToProcess.size() > 1 ) {
                    try {
                        s = file.getCanonicalPath() + "-" + s;
                    }
                    catch( IOException ioe ) {}
                }
                System.out.println( s );
            }
        }

        if( ( beforeContext > 0 ) || ( afterContext > 0 ) ) {
            System.out.println( "--" );
        }
    }



    // TODO: note that file names can contain wild cards too
    // so this method should also expand directories as well
    private void processFileArgs( final List list ) {

        filesToProcess = new ArrayList< File >();

        for( final Object o : list ) {
            final String name = (String)o;

            final File f = new File( name );

            if( f.exists() ) {
                if( f.isFile() ) {
                    if( includeFile( f ) ) {
                        if( !excludeFile( f ) ) {
                            filesToProcess.add( f );
                        }
                    }
                }
                else if( f.isDirectory() ) {
                    if( recurseDirectories ) {
                        filesToProcess.addAll( recurseDir( f ) );
                    }
                }
            }
        }

    }

    public void verifyFileList() {

        List< File > list = filesToProcess;
        filesToProcess = new ArrayList< File >();

        for( final Object o : list ) {

            final File f = (File)o;

            if( f.exists() ) {
                if( f.isFile() ) {
                    if( includeFile( f ) ) {
                        if( !excludeFile( f ) ) {
                            filesToProcess.add( f );
                        }
                    }
                }
                else if( f.isDirectory() ) {
                    if( recurseDirectories ) {
                        filesToProcess.addAll( recurseDir( f ) );
                    }
                }
            }
        }

    }

    private List< File > recurseDir( final File dir ) {

        List< File > files = new ArrayList< File >();

        for( File f : dir.listFiles() ) {

            if( f.isFile() ) {
                if( includeFile( f ) ) {
                    if( !excludeFile( f ) ) {
                        files.add( f );
                    }
                }
            }
            else if( f.isDirectory() ) {
                if( !excludeDir( f ) ) {
                    files.addAll( recurseDir( f ) );
                }
            }

        }

        return files;

    }





    void readRegexFromFile( final String... fnames ) {

        for( String fname : fnames ) {

            File file = new File( fname );
            if( file.exists() && file.isFile() ) {

                try {

                    regexes.addAll( FileUtils.readLines( file ) );
                }
                catch( IOException io ) {
                    // TODO - what to do here
                }

            }
        }


    }

    void readLongRegexFromFile( final String[] fnames ) {

        for( String fname : fnames ) {

            File file = new File( fname );
            if( file.exists() && file.isFile() ) {

                try {
                    regexes.add( FileUtils.readFileToString( file ) );
                }
                catch( IOException io ) {
                    // TODO - what to do here
                }

            }
        }

    }

    private void reset() {

        grepPatterns = new ArrayList< Pattern >();
        regexes = new ArrayList< String >();
        filesToProcess = null;
        excludeFilePatterns = new ArrayList< String >();

    }




    public static Predicate wildcardMatcher( final File file ) {

        return new Predicate() {

            @Override
            public boolean evaluate( final Object object ) {

                String pattern = (String)object;
                String nm = FilenameUtils.getName( file.getName() );
                return FilenameUtils.wildcardMatch( nm, pattern );
            }

        };
    }




    public boolean includeFile( final File f ) {

        if( !useInclude ) {
            return true;
        }

        if( CollectionUtils.exists( includeFilePatterns, wildcardMatcher( f ) ) ) {
            return true;
        }

        return false;
    }

    public boolean excludeFile( final File f ) {

        if( !useExclude ) {
            return false;
        }

        if( CollectionUtils.exists( excludeFilePatterns, wildcardMatcher( f ) ) ) {
            return true;
        }

        return false ;

    }

    private boolean excludeDir( final File f ) {

        if( CollectionUtils.exists( excludeDirPatterns, wildcardMatcher( f ) ) ) {
            return true;
        }

        return false;
    }

    private boolean printFileNamesOnly() {

        return printFilesWithoutMatch || printFileNameOnly;
    }




    long beforeContext = 0;

    long         afterContext  = 0;

    private Matcher matchAny( final String line ) {

        for( Pattern pat : grepPatterns ) {

            Matcher m = pat.matcher( line );
            if( m.find() ) {
                return m;
            }
        }

        return null;
    }

    private void grepFile( final File file ) {


        long lineNumber = 0;
        long count = 0;
        long byteOffset = 0;
        List< String > beforeContextLines = new LinkedList< String >();
        List< String > afterContextLines = new LinkedList< String >();

        // keep it simple for now
        try( BufferedReader bfr = new BufferedReader( new FileReader( file ), 16000 ) ) {

            for( String line = bfr.readLine(); line != null; line = bfr.readLine() ) {

                lineNumber++;
                Matcher m = matchAny( line );

                if( ( ( m != null ) && !invertMatch ) ) {
                    count++;
                    if( afterContext > 0 ) {
                        afterContextLines.clear();
                        bfr.mark( 4000 );
                        for( int i = 0; i < afterContext; i++ ) {
                            String s = bfr.readLine();
                            if( s != null ) {
                                afterContextLines.add( s );
                            }
                            else {
                                break;
                            }
                        }
                        bfr.reset();
                    }
                    String match = line;
                    if( printMatchOnly ) {
                        match = m.group();
                    }
                    printMatch( file, match, lineNumber, count, ( byteOffset + m.start() ), beforeContextLines,
                        afterContextLines );
                    if( printFileNameOnly ) {
                        return;
                    }
                    //
                }
                else if( ( m == null ) && invertMatch ) {
                    count++;
                    printMatch( file, line, lineNumber, count, byteOffset, beforeContextLines,
                        afterContextLines );
                    // TODO: this has a slightly different meaning
                    if( printFileNameOnly ) {
                        return;
                    }
                }

                if( ( maxCount != 0 ) && ( count >= maxCount ) ) {
                    break;
                }

                byteOffset += line.getBytes().length;
                byteOffset += 1; // TODO: end of line char - what about DOS/Win32?

                beforeContextLines.add( line );
                if( beforeContextLines.size() > beforeContext ) {
                    beforeContextLines.remove( 0 );
                }
            }
        }
        catch( IOException ioe ) {
            System.out.println( ioe.getMessage() );
        }

        if( count == 0 ) {
            // no matches in file
            if( printFilesWithoutMatch ) {
                printMessage( file.getName() );
            }
        }

        if( printCountOnly ) {
            printMessage( ( file.getName() + ":" + count ) );
        }

    }

    private boolean isRegexSet() {

        return CollectionUtils.isNotEmpty( regexes );
    }

    public void addRegex( final String r ) {

        regexes.add( r );
    }

    public void addFile( final String name ) {

        filesToProcess.add( new File( name ) );
    }

    public void execute() {

        grepFiles();
    }
}
