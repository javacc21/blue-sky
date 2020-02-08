## Initial (incomplete) parser for the QUTE template language.

You can try this by simply running 
<pre>
   ant test
</pre>

It builds the parser from the QUTE.javacc grammar file, and there is a little
test harness in QUTETest.java. So it compiles all the generated code and 
runs the test harness over the four files that I put in the testfiles directory.

So, a picture tells a thousand words and you see that the parser builds
a tree of nodes that represent the AST (abstract syntax tree) and dumps it to the 
console in an in an indented text form.

At this point, here are a few caveats:

1. In this iteration (as of today) the expression language (I call it QEL for QUTE 
Expression Language is in a separate grammar file called QEL.javacc that the main QUTE.javacc *includes*.) I 
think it clearly makes sense for the expression language to have its own separately 
useable (and testable) grammar.

2. QEL is currently kind of ad hoc and incomplete. In the last iteration, it was
even more incomplete since the only thing it could handle was a simple C-style 
identifier and that was that. Now, it can handle string and number literals as 
defined by JSON as well, and also parentheses.

3. As for sections (or instructions or directives or whatever you want to call them) the 
empty and non-empty named sections are supported. However, if/elseif/else is handled separately since, actually, it does not conform to the regular tag logic. The elseif and else tags do not close! It is likely necessary to handle other built-in sections separately in a similar manner, if their logic similarly deviates, but this is mostly just proof of concept.

The template language does not seem to be so well specified, so I just made certain assumptions.
For one thing, I assumed that the way to escape a literal '{' is just the typical Unixy sort of way,by adding a backslash in front. That is actually there in the first test file.

Given that the two files (QUTE.javacc and QEL.javacc) comprise less than 200 lines
and the most basic elements of the grammar are defined, i think it is safe to say that this would be more maintainable long-term than a hand-written
parser.

Any feedback is welcome. You can write me at revusky(at)javacc.com. If you find this useful, 
you should thank Angelo Zerr!