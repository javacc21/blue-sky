## Initial (Incomplete) parser for the QUTE template language.

You can try this by simply running 
<pre>
   ant test
</pre>

It builds the parser from the QUTE.javacc grammar file, and there is a little
test harness in QUTETest.java. So it compiles all the generated code and 
runs the test harness over the three files that I put in the testfiles directory.

So, as you see, it parses the files and builds a tree of nodes (AST, i.e. abstract 
syntax tree. The most glaring omission is that there is currently no 
expression language really. Well, the only expression that the parser recognizes is
just a C-style identifier. So, obviously, the Expression() production would have to 
be much more complex than this!

<pre>
    void Expression() {}
    {
        &lt;C_IDENTIFIER&gt;
    }
</pre>

Well, I suppose another way of putting this is that we only have here the 
instruction/directive/section (or whatever the approved terminology is) part of the grammar.

The template language does not seem to be so well specified, so I just made certain assumptions.
For one thing, I assumed that the way to escape a literal '{' is just the typical Unixy sort of way, by adding a backslash in front. That is actually there in the first test file.

Given that the JavaCC grammar (admittedly without the expression part of the grammar) is currently 137
lines long, I think it is safe to say that this would be more maintainable long-term than a hand-written
parser.

Any feedback is welcome. You can write me at revusky(at)javacc.com. If you find this useful, 
you should thank Angelo Zerr!