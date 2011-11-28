package org.rip.regex;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class grepCommandLine {

    static Options      grepOptions;

    static {
        grepCommandLine.grepOptions = createOptions();
    }

    private CommandLine commandLine;

    public grepCommandLine() {

    }

    public CommandLine getCommandLine() {

        return commandLine;
    }

    public void setCommandLine( final CommandLine commandLine ) {

        this.commandLine = commandLine;
    }

    private static Options createOptions() {

        final Options opts = new Options();

        opts.addOption( "E", "extended-regexp", false, "PATTERN is an extended regular expression (ERE)" );
        opts.addOption( "F", "fixed-strings", false, "PATTERN is a set of newline-separated fixed strings" );
        opts.addOption( "G", "basic-regexp", false, "PATTERN is a basic regular expression (BRE)" );
        opts.addOption( "P", "perl-regexp", false, "PATTERN is a Perl regular expression" );
        opts.addOption( OptionBuilder.withLongOpt( "regexp" ).hasArg().withArgName( "PATTERN" )
            .withDescription( "use PATTERN for matching" ).create( 'e' ) );
        opts.addOption( OptionBuilder.withLongOpt( "file" ).hasArg().withArgName( "FILE" )
            .withDescription( "obtain PATTERN from FILE" ).create( 'f' ) );
        opts.addOption( "i", "ignore-case", false, "ignore case distinctions" );
        opts.addOption( "w", "word-regexp", false, "force PATTERN to match only whole words" );
        opts.addOption( "x", "line-regexp", false, "force PATTERN to match only whole lines" );
        opts.addOption( "z", "null-data", false, "a data line ends in 0 byte, not newline" );
        opts.addOption( "s", "no-messages", false, "suppress error messages" );
        opts.addOption( "v", "invert-match", false, "select non-matching lines" );
        opts.addOption( "V", "version", false, "print version information and exit" );
        opts.addOption( OptionBuilder.withLongOpt( "help" ).withDescription( "display this help and exit" ).create() );
        // opts.addOption( OptionBuilder.withLongOpt( "mmap" ).withDescription(
        // "ignored for backwards compatibility" ).create() );
        opts.addOption( OptionBuilder.withLongOpt( "max-count" ).withArgName( "NUM" ).hasArg()
            .withDescription( "stop after NUM matches" ).create( 'm' ) );
        opts.addOption( "b", "byte-offset", false, "print the byte offset with output lines" );
        opts.addOption( "n", "line-number", false, "print line number with output lines" );
        opts.addOption( OptionBuilder.withLongOpt( "line-buffered" ).withDescription( "flush output on every line" )
            .create() );
        opts.addOption( "H", "with-filename", false, "print the filename for each match" );
        opts.addOption( "h", "no-filename", false, "suppress the prefixing filename on output" );
        opts.addOption( OptionBuilder.withLongOpt( "label" ).hasArg().withArgName( "LABEL" )
            .withDescription( "print LABEL as filename for standard input" ).create() );
        opts.addOption( "o", "only-matching", false, "show only the part of a line matching PATTERN" );
        opts.addOption( "q", "quiet", false, "suppress all normal output" );
        opts.addOption( OptionBuilder.withLongOpt( "silent" ).withDescription( "suppress all normal output" ).create() );
        opts.addOption( OptionBuilder.withLongOpt( "binary-files" ).hasArg().withArgName( "TYPE" )
            .withDescription( "assume that binary files are TYPE" ).create() );
        // TYPE is `binary', `text', or `without-match'
        opts.addOption( "a", "text", false, "equivalent to --binary-files=text" );
        opts.addOption( "I", false, "equivalent to --binary-files=without-match" );
        opts.addOption( OptionBuilder.withLongOpt( "directories" ).hasArg().withArgName( "ACTION" )
            .withDescription( "how to handle directories" ).create( 'd' ) );
        // ACTION is `read', `recurse', or `skip'"
        opts.addOption( OptionBuilder.withLongOpt( "devices" ).hasArg().withArgName( "ACTION" )
            .withDescription( "how to handle devices, FIFOs and sockets" ).create( 'D' ) );
        // ACTION is `read' or `skip'
        opts.addOption( "R", false, "equivalent to --directories=recurse" );
        opts.addOption( "r", "recursive", false, "equivalent to --directories=recurse" );
        opts.addOption( OptionBuilder.withLongOpt( "include" ).hasArg().withArgName( "FILE_PATTERN" )
            .withDescription( "search only files that match FILE_PATTERN" ).create() );
        opts.addOption( OptionBuilder.withLongOpt( "exclude" ).hasArg().withArgName( "FILE_PATTERN" )
            .withDescription( "skip files and directories matching FILE_PATTERN" ).create() );
        opts.addOption( OptionBuilder.withLongOpt( "exclude-from" ).hasArg().withArgName( "FILE" )
            .withDescription( "skip files matching any file pattern from FILE" ).create() );
        opts.addOption( OptionBuilder.withLongOpt( "exclude-dir" ).hasArg().withArgName( "PATTERN" )
            .withDescription( "directories that match PATTERN will be skipped" ).create() );
        opts.addOption( "L", "files-without-match", false, "print only names of FILEs containing no match" );
        opts.addOption( "l", "files-with-matches", false, "print only names of FILEs containing matches" );
        opts.addOption( "c", "count", false, "print only a count of matching lines per FILE" );
        opts.addOption( "T", "initial-tab", false, "make tabs line up (if needed)" );
        opts.addOption( "Z", "null", false, "print 0 byte after FILE name" );
        opts.addOption( OptionBuilder.withLongOpt( "before-context" ).hasArg().withArgName( "NUM" )
            .withDescription( "print NUM lines of leading context" ).create( 'B' ) );
        opts.addOption( OptionBuilder.withLongOpt( "after-context" ).hasArg().withArgName( "NUM" )
            .withDescription( "print NUM lines of trailing context" ).create( 'A' ) );
        opts.addOption( OptionBuilder.withLongOpt( "contex" ).hasArg().withArgName( "NUM" )
            .withDescription( "print NUM lines of output context" ).create( 'C' ) );
        // * + "          -NUM                      same as --context=NUM\n" +
        opts.addOption( OptionBuilder.withLongOpt( "color" ).hasOptionalArg().withArgName( "WHEN" )
            .withDescription( "use markers to highlight the matching strings" ).create() );
        opts.addOption( OptionBuilder.withLongOpt( "colour" ).hasOptionalArg().withArgName( "WHEN" )
            .withDescription( "use markers to highlight the matching strings" ).create() );
        // *
        // "                                    WHEN is `always', `never', or `auto'\n"
        opts.addOption( "U", "binary", false, "do not strip CR characters at EOL (MSDOS)" );
        opts.addOption( "u", "unix-byte-offsets", false, "report offsets as if CRs were not there (MSDOS)" );

        // non gnu options
        opts.addOption( OptionBuilder.withLongOpt( "debug" ).withDescription( "turn on debugging output" ).create() );
        opts.addOption( OptionBuilder.withLongOpt( "verbose" ).withDescription( "turn on verbose output" ).create() );
        opts.addOption( OptionBuilder.withLongOpt( "test" ).withDescription( "run in test mode" ).create() );
        opts.addOption( OptionBuilder.withLongOpt( "logging" ).withDescription( "use nice logging, Log4J/SLF4J" )
            .create() );
        opts.addOption( OptionBuilder.withLongOpt( "file-long" ).hasArg().withArgName( "FILE" )
            .withDescription( "read patterns from FILE using long format" ).create() );
        opts.addOption( OptionBuilder.withLongOpt( "include-from" ).hasArg().withArgName( "PATTERN" )
            .withDescription( "include files matching patterns from FILE" ).create() );

        return opts;
    }

    public static void usage() {

        final String msg =
            "Usage: grep [OPTION]... PATTERN [FILE]...\n"
                + "Search for PATTERN in each FILE or standard input.\n"
                + "PATTERN is, by default, a basic regular expression (BRE).\n"
                + "Example: grep -i 'hello world' menu.h main.c\n"
                + "Regexp selection and interpretation:\n"
                + "        -E, --extended-regexp     PATTERN is an extended regular expression (ERE)\n"     // no
                + "        -F, --fixed-strings       PATTERN is a set of newline-separated fixed strings\n" // no
                + "       -G, --basic-regexp        PATTERN is a basic regular expression (BRE)\n"          // no
                + "       -P, --perl-regexp         PATTERN is a Perl regular expression\n"                 // no
                + "       -e, --regexp=PATTERN      use PATTERN for matching\n"                // done
                + "       -f, --file=FILE           obtain PATTERN from FILE\n"                // done
                + "       -i, --ignore-case         ignore case distinctions\n"                // done
                + "       -w, --word-regexp         force PATTERN to match only whole words\n"     // done
                + "       -x, --line-regexp         force PATTERN to match only whole lines\n"     // done
                + "       -z, --null-data           a data line ends in 0 byte, not newline\n"     // yes?
                + "Miscellaneous:\n"
                + "       -s, --no-messages         suppress error messages\n"                     // done
                + "       -v, --invert-match        select non-matching lines\n"                   // done
                + "       -V, --version             print version information and exit\n"          // done
                + "           --help                display this help and exit\n"                  // done
                // +
                + "           --mmap                ignored for backwards compatibility\n" // no
                + "Output control:\n"
                + "       -m, --max-count=NUM       stop after NUM matches\n"                      // done - max-total
                + "       -b, --byte-offset         print the byte offset with output lines\n"     // done
                + "       -n, --line-number         print line number with output lines\n"         // done
                + "           --line-buffered       flush output on every line\n"                  // yes
                + "       -H, --with-filename       print the filename for each match\n"           // done
                + "       -h, --no-filename         suppress the prefixing filename on output\n"   // done
                + "           --label=LABEL         print LABEL as filename for standard input\n"  // yes?
                + "       -o, --only-matching       show only the part of a line matching PATTERN\n"   // done
                + "       -q, --quiet, --silent     suppress all normal output\n"                      // done
                + "           --binary-files=TYPE   assume that binary files are TYPE;\n"              // yes?
                + "                                 TYPE is `binary', `text', or `without-match'\n"
                + "       -a, --text                equivalent to --binary-files=text\n"               // yes?
                + "       -I                        equivalent to --binary-files=without-match\n"      // yes?
                + "       -d, --directories=ACTION  how to handle directories;\n"                      // done
                + "                                 ACTION is `read', `recurse', or `skip'\n"
                + "       -D, --devices=ACTION      how to handle devices, FIFOs and sockets;\n"       // no
                + "                                 ACTION is `read' or `skip'\n"
                + "       -R, -r, --recursive       equivalent to --directories=recurse\n"             // done
                + "           --include=FILE_PATTERN  search only files that match FILE_PATTERN\n"     // done
                + "           --exclude=FILE_PATTERN  skip files and directories matching FILE_PATTERN\n"  // done
                + "           --exclude-from=FILE   skip files matching any file pattern from FILE\n"      // done
                + "           --exclude-dir=PATTERN  directories that match PATTERN will be skipped.\n"    // done
                + "       -L, --files-without-match  print only names of FILEs containing no match\n"      // done
                + "       -l, --files-with-matches  print only names of FILEs containing matches\n"        // done
                + "       -c, --count               print only a count of matching lines per FILE\n"       // done
                + "       -T, --initial-tab         make tabs line up (if needed)\n"                       // no
                + "       -Z, --null                print 0 byte after FILE name\n"                        // no - use --sep=CHAR
                + "Context control:\n"
                + "       -B, --before-context=NUM  print NUM lines of leading context\n"                  // done
                + "       -A, --after-context=NUM   print NUM lines of trailing context\n"                 // done
                + "       -C, --context=NUM         print NUM lines of output context\n"                   // done
                + "       -NUM                      same as --context=NUM\n"                               // no
                + "           --color[=WHEN],\n"                                                           // no
                + "           --colour[=WHEN]       use markers to highlight the matching strings;\n"      // no
                + "                                 WHEN is `always', `never', or `auto'\n"
                + "       -U, --binary              do not strip CR characters at EOL (MSDOS)\n"           // no?
                + "       -u, --unix-byte-offsets   report offsets as if CRs were not there (MSDOS)\n"     // no?
                + "\n"
                + "     `egrep' means `grep -E'.  `fgrep' means `grep -F'.\n"
                + "     Direct invocation as either `egrep' or `fgrep' is deprecated.\n"
                + "     With no FILE, or when FILE is -, read standard input.  If less than two FILEs\n"
                + "     are given, assume -h.  Exit status is 0 if any line was selected, 1 otherwise;\n"
                + "     if any error occurs and -q was not given, the exit status is 2.\n"
                + "\n"
                + "     Report bugs to: bug-grep@gnu.org\n"
                + "     GNU Grep home page: <http://www.gnu.org/software/grep/>\n"
                + "     General help using GNU software: <http://www.gnu.org/gethelp/>\n";
        System.out.print( msg );

    }

    public static void version() {

        System.out.println( "Java Grep 0.1" );

    }

}