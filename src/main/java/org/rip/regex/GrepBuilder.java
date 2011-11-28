package org.rip.regex;

import java.util.Arrays;


public class GrepBuilder {

    private grep theGrep;

    private GrepBuilder() {

        theGrep = new grep();
    }

    // Regex options
    public GrepBuilder addRegexes( final String... regexes ) {

        for( String r : regexes ) {
            theGrep.addRegex( r );
        }
        return this;
    }

    public GrepBuilder addRegexFromFiles( final String... files ) {
        theGrep.readRegexFromFile( files );
        return this;
    }

    public GrepBuilder addLongRegexFromFiles( final String... files ) {
        theGrep.readLongRegexFromFile( files );
        return this;
    }

    public GrepBuilder ignoreCase() {
        theGrep.ignoreCase=true;
        return this;
    }

    public GrepBuilder wordRegex() {
        theGrep.wordRegex = true;
        return this;
    }

    public GrepBuilder lineRegex() {
        theGrep.lineRegex = true;
        return this;
    }

    // Miscellaneous
    public GrepBuilder noMessages() {
        theGrep.noMessages = true;
        return this;
    }

    public GrepBuilder invertMatch() {
        theGrep.invertMatch = true;
        return this;
    }

    // Output control
    public GrepBuilder maxCount( final long l ) {
        theGrep.maxCount = l;
        return this;
    }

    public GrepBuilder printByteOffset() {
        theGrep.printByteOffset = true;
        return this;
    }

    public GrepBuilder printLineNumber() {
        theGrep.printLineNumber = true;
        return this;
    }

    public GrepBuilder withFilename() {
        theGrep.printFileName = false;
        return this;
    }

    public GrepBuilder noFilename() {
        theGrep.printFileName = false;
        return this;
    }

    public GrepBuilder onlyMatching() {
        theGrep.printMatchOnly = true;
        return this;
    }

    public GrepBuilder quiet() {
        theGrep.quiet = true;
        return this;

    }

    public GrepBuilder recurseDirectories() {
        theGrep.recurseDirectories = true;
        return this;
    }

    public GrepBuilder skipDirectories() {
        theGrep.skipDirectories = true;
        return this;
    }

    public GrepBuilder include( final String... includes ) {

        theGrep.useInclude = true;
        theGrep.includeFilePatterns.addAll( Arrays.asList( includes ) );
        return this;
    }

    public GrepBuilder includeFrom( final String... from ) {
        theGrep.useInclude = true;
        theGrep.readIncludesFrom( from );
        return this;
    }

    public GrepBuilder exclude( final String... excludes ) {
        theGrep.useExclude = true;
        theGrep.excludeFilePatterns.addAll( Arrays.asList( excludes ) ) ;
        return this;
    }

    public GrepBuilder excludeFrom( final String... from ) {

        theGrep.useExclude = true;
        theGrep.readExcludeFrom( from );
        return this;
    }

    public GrepBuilder excludeDir( final String... dirs ) {
        theGrep.excludeDirPatterns.addAll( Arrays.asList( dirs ) );
        return this;
    }

    public GrepBuilder printFilesWithoutMatch() {
        theGrep.printFilesWithoutMatch = true;
        return this;
    }

    public GrepBuilder printFileNameOnly() {
        theGrep.printFileNameOnly = true;
        return this;
    }

    public GrepBuilder printCountOnly() {
        theGrep.printCountOnly = true;
        return this;
    }

    // Context control
    public GrepBuilder context( final long lines ) {

        theGrep.afterContext = lines ;
        theGrep.beforeContext = lines ;
        return this;
    }

    public GrepBuilder afterContext( final long lines ) {

        theGrep.afterContext = lines;
        return this;
    }

    public GrepBuilder beforeContext( final long lines ) {

        theGrep.beforeContext = lines;
        return this;

    }

    public GrepBuilder addFile( final String name ) {

        theGrep.addFile( name );
        return this;
    }


    public static GrepBuilder start() {

        return new GrepBuilder();
    }

    public grep build() {

        theGrep.compilePatterns();
        theGrep.verifyFileList();
        return theGrep;
    }

    /**
     * @param args
     */
    public static void main( final String[] args ) {

        GrepBuilder.start().addRegexes( "ACTIVE" ).addFile( "./src/test/data/AnadysRecRulesTest.xml" ).build()
        .execute();

    }

}
