package org.rip.regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class grep {

    public grep() {

        grepPatterns = new ArrayList< Pattern >();
        filesToProcess = new ArrayList< File >();
        excludes = new ArrayList< String >();
        regexes = new ArrayList< String >();
    }

    private List< Pattern >     grepPatterns;

    private List< String >      regexes;

    private List< File >        filesToProcess;

    private ArrayList< String > excludes;

    public Pattern compilePattern( final String pat ) {

        int flags = 0;

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

        if( commandLine.hasOption( 'i' ) || commandLine.hasOption( "ignore-case" ) ) {
            ignoreCase = true;
        }

        if( commandLine.hasOption( 'v' ) || commandLine.hasOption( "invert-match" ) ) {
            invertMatch = true;
        }

        maxCount = 0;
        if( commandLine.hasOption( 'm' ) || commandLine.hasOption( "max-count" ) ) {
            String s = commandLine.getOptionValue( 'm' );
            maxCount = Long.parseLong( s );
        }

        if( commandLine.hasOption( 'l' ) || commandLine.hasOption( "files-with-matches" ) ) {
            printFileNameOnly = true;
        }

        if( commandLine.hasOption( 'b' ) || commandLine.hasOption( "byte-offset" ) ) {
            printByteOffset = true;
        }

        if( commandLine.hasOption( 'q' ) || commandLine.hasOption( "--quiet" ) || commandLine.hasOption( "silent" ) ) {
            quiet = true;
        }

        if( commandLine.hasOption( 'c' ) || commandLine.hasOption( "count" ) ) {
            printCountOnly = true;
        }

        printFilesWithoutMatch = commandLine.hasOption( 'L' ) || commandLine.hasOption( "files-without-match" );

        wordRegex = commandLine.hasOption( 'w' ) || commandLine.hasOption( "word-regexp" );

        lineRegex = commandLine.hasOption( 'x' ) || commandLine.hasOption( "line-regexp" );

        if( commandLine.hasOption( "exclude-from" ) ) {
            readExcludeFrom( commandLine.getOptionValue( "exclude-from" ) );
        }

        // this could be specified multiple times
        if( commandLine.hasOption( 'e' ) || commandLine.hasOption( "regexp" ) ) {
            regexes.add( commandLine.getOptionValue( 'e' ) );
        }

        if( commandLine.hasOption( 'f' ) || commandLine.hasOption( "file" ) ) {
            readRegexFromFile( commandLine.getOptionValue( 'f' ) );
        }

        noMessages = commandLine.hasOption( 's' ) || commandLine.hasOption( "no-messages" );

        if( commandLine.hasOption( 'H' ) || commandLine.hasOption( "with-filename" ) ) {
            printFileName = true;
        }

        if( commandLine.hasOption( 'h' ) || commandLine.hasOption( "no-filename" ) ) {
            printFileName = false;
        }

        printMatchOnly = commandLine.hasOption( 'o' ) || commandLine.hasOption( "only-matching" );

        printLineNumber = commandLine.hasOption( 'n' ) || commandLine.hasOption( "line-number" );

        recurseDirectories = false;

        if( commandLine.hasOption( 'd' ) || commandLine.hasOption( "directories" ) ) {

            String val = commandLine.getOptionValue( 'd' );
            if( StringUtils.endsWithIgnoreCase( val, "recurse" ) ) {
                recurseDirectories = true;
            }

        }

        if( commandLine.hasOption( 'R' )
            || commandLine.hasOption( 'r' )
            || commandLine.hasOption( "recursive" ) ) {
            recurseDirectories = true;
        }

        if( commandLine.hasOption( "include" ) ) {
            includeFilePattern = commandLine.getOptionValue( "include" );
        }

        if( commandLine.hasOption( "exclude" ) ) {
            excludeFilePattern = commandLine.getOptionValue( "exclude" );
        }

        if( commandLine.hasOption( "exclude-dir" ) ) {
            excludeDirPattern = commandLine.getOptionValue( "exclude-dir" );
        }

    }

    private boolean invertMatch = false;
    private boolean ignoreCase = false;
    private long    maxCount;

    private boolean printFileNameOnly = false;

    private boolean printByteOffset   = false;

    private boolean quiet             = false;

    private boolean printCountOnly    = false;

    private boolean printFilesWithoutMatch = false;

    private boolean wordRegex              = false;

    private boolean lineRegex              = false;

    private boolean noMessages             = false;

    private boolean printFileName          = true;

    private boolean printMatchOnly         = false;
    private boolean printLineNumber=false;

    private boolean recurseDirectories     = false;

    private String  includeFilePattern     = null;

    private String  excludeFilePattern     = null;

    private String  excludeDirPattern      = null;

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

        //
        if( cmd.getCommandLine().hasOption( 'e' ) || cmd.getCommandLine().hasOption( "regexp" ) ) {
            if( ArrayUtils.isEmpty( cmd.getCommandLine().getOptionValues( 'e' ) ) ) {
                grepCommandLine.usage();
                return;
            }
        }

        if( cmd.getCommandLine().hasOption( 'f' ) || cmd.getCommandLine().hasOption( "file" ) ) {
            if( ArrayUtils.isEmpty( cmd.getCommandLine().getOptionValues( 'f' ) ) ) {
                grepCommandLine.usage();
                return;
            }
        }

        if( cmd.getCommandLine().hasOption( "exclude-from" ) ) {
            String s = cmd.getCommandLine().getOptionValue( "exclude-from" );
            if( StringUtils.isEmpty( s ) ) {
                grepCommandLine.usage();
                return;
            }
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
            theGrep.regexes.add( (String)argList.get( 0 ) );
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
        if( files.size() > 0 ) {
            theGrep.printFileName = true;
        }
        theGrep.processFileArgs( files );

        // ok lets start
        theGrep.grepFiles();

    }

    private void compilePatterns() {

        for( String s : regexes ) {
            grepPatterns.add( compilePattern( s ) );
        }
    }

    private void readExcludeFrom( final String val ) {


        try( BufferedReader br = new BufferedReader( new FileReader( new File( val ) ) ) ) {
            excludes = new ArrayList< String >();

            for( String line = br.readLine(); line != null; line = br.readLine() ) {
                excludes.add( line );
            }

        }
        catch( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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





    private void readRegexFromFile( final String fname ) {


        File file = new File( fname );
        if( file.exists() && file.isFile() ) {

            try( BufferedReader br = new BufferedReader( new FileReader( file ) ) ) {

                String line = br.readLine();
                if( StringUtils.isNotBlank( line ) ) {
                    regexes.add( line );
                }

            }
            catch( IOException e ) {
                printErrorMessage( "Cannot open file:" + fname );
            }
        }


    }

    private void reset() {

        grepPatterns = new ArrayList< Pattern >();
        regexes = new ArrayList< String >();
        filesToProcess = null;
        excludes = null;


    }





    public boolean includeFile( final File f ) {

        if( StringUtils.isNotEmpty( includeFilePattern ) ) {

            String nm = FilenameUtils.getName( f.getName() );
            return FilenameUtils.wildcardMatch( nm, includeFilePattern );

        }

        return true;
    }

    public boolean excludeFile( final File f ) {

        if( StringUtils.isNotBlank( excludeFilePattern ) ) {

            String nm = FilenameUtils.getName( f.getName() );
            return FilenameUtils.wildcardMatch( nm, excludeFilePattern );
        }

        if( CollectionUtils.isNotEmpty( excludes ) ) {
            for( String pat : excludes ) {
                String nm = FilenameUtils.getName( f.getName() );
                boolean ex = FilenameUtils.wildcardMatch( nm, pat );
                if( ex ) {
                    return true;
                }
            }
        }

        return false ;

    }

    private boolean excludeDir( final File f ) {

        if( StringUtils.isNotBlank( excludeDirPattern ) ) {
            if( FilenameUtils.wildcardMatch( FilenameUtils.getName( f.getName() ), excludeDirPattern ) ) {
                return true;
            }
        }

        return false;
    }

    private boolean printFileNamesOnly() {

        return printFilesWithoutMatch || printFileNameOnly;
    }




    private long beforeContext = 0;

    private long afterContext = 0;

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
}
