0.9-riddler / 2018-08-02
========================

  * Merge branch 'funciones-adicionales-2' into 'master'
  * Bump to version 0.9-riddler
  * Add horribly superflous default labels (Codacy)
  * Declare one variable per line (Codacy)
  * Implement tests for d2r & r2d (Task #795)
  * Implement d2r & r2d (Task #795)
  * Add tests for Triangle Wave (Task #769)
  * Implement Triangle Wave (Task #769)
  * Implement tests for Sawtooth wave (Task #771)
  * Implement Sawtooth wave (Task #771)
  * Remove inline variable to appease the Codacy gods...
  * Update README.md
  * Merge branch 'add-coverage-results' into 'master'
  * Change badges paths
  * Add coverage results to GitLab CI
  * Merge branch 'master' of gitlab.com:riddler_arg/exp4j
  * Bump to version 0.8.1-riddler to check if Codacy trigger works
  * Update README.md

0.8.1-riddler / 2018-07-18
==========================

  * Improve JavaDoc
  * Make internal function u() static
  * Remove invalid html tag
  * Add warning about concatenating comparison operators
  * Merge branch 'expression-based-functions' into 'master'
  * Add missing translation
  * Improve performance tests (Task #798)
  * Add tests for expression based functions (Task #784)
  * Implement expression based Functions (Task #784)
  * Cleanup History.md
  * Merge branch 'add-some-extra-functions' into 'master'
  * Update Changelog

0.8-riddler / 2018-07-17
========================

  * Bump version number in Ant script
  * Bump to version `0.8-riddler`
  * Add more documentation to `FunctionsMisc`
  * Add some documentation for `FunctionsSignal`
  * Use other `serialVersionUID` for inner clasess
  * Add tests fot `rectangle(t, X, Y)` (Task #774)
  * Implement `rectangle(t, X, Y)` (Task #774)
  * Add tests for HeavySide functions (Task #773)
  * Fix HeavySide function (Task #773)
  * Don't validate build on Deprecated JDK7
  * Implement HeavySide functions (Task #773)
  * Refactor `sinc(x)` to `FunctionsSignal` (Task #770)
  * Add tests for `round(x)` (Task #797)
  * Implement `round(x)` (Task #797)
  * Reenable Jacoco report
  * Add distribution tests for `lcm` and `gcd` (Tasks #793& #794)
  * Add tests for `lcm(a, b)` (Task #793)
  * Implement LCM (Task #793)
  * Add tests for `GDC(x, y)` (Task #794)
  * Implement `GDC(x, y)` (Task #794)
  * Add tests for `max(x,y)` (Task #787)
  * Implement `max(x,y)` (Task #787)
  * Add tests for `min(x,y)` (Task #786)
  * Implement `min(x,y)` (Task #786)
  * Add tests for `isnan(x)` (Task #788)
  * Implement `isnan(x)` (Task #788)
  * Add tests for `inf()` (Task #789)
  * Implement `inf()` (Task #789)
  * Fix a typo in the docs
  * Use tests when pushing to master
  * Add package-info
  * Add changelog
  * Merge branch 'gitlab-ci' into 'master'
  * Remove JDK7 verification
  * Attempt to build with Gitlab/CI
  * Remove JaCoCo
  * Add .gitlab-ci.yml
  * Add missing JavaDocs Task #759 - Corregir todas las advertencias de JavaDoc
  * Add lot's of missing JavaDocs Task #759 - Corregir todas las advertencias de JavaDoc
  * Adapt tests Task #759 - Corregir todas las advertencias de JavaDoc
  * Make some fields final Task #759 - Corregir todas las advertencias de JavaDoc
  * Fix JavaDoc Task #759 - Corregir todas las advertencias de JavaDoc

0.7-riddler / 2018-07-12
========================

  * Bump Version number
  * Merge branch 'extract-strings' into 'master'
  * Add properties files in Maven
  * Add properties files in Ant
  * Add Spanish strings Task #756 - Allow translating messages
  * Extract Strings to properties file Task #756 - Allow translating messages
  * Corregir Test Task #756 - Allow translating messages
  * Extract Strings to Text
  * Add a ResourceBundle wrapper Task #756 - Allow translating messages
  * Merge branch 'serialize-expressions' into 'master'
  * Remove redundant Serialization Flag Task #749 - Garantizar que las operaciones sean serializables
  * Make ALLOWED_OPERATOR_CHARS private
  * Remove redundant Serializable flag Task #749 - Garantizar que las operaciones sean serializables
  * Squash a switch operand
  * Add Serialization Tests Task #749 - Garantizar que las operaciones sean serializables
  * Remove a bunch of trailing spaces
  * Add serialVersionUID and make some classes final Task #749 - Garantizar que las operaciones sean serializables
  * Make OperatorsComparison static inner classes instead of Anonymous Inner Clasess Task #757 - Don't use Anonymous Inner Classes
  * Re add Operators.getOperators()
  * Re add Functions.getFunctions()
  * Make FunctionsBoolean static inner classes instead of Anonymous Inner Clasess Task #757 - Don't use Anonymous Inner Classes
  * Fix typo
  * Make FunctionsMisc static inner classes instead of Anonymous Inner Clasess Task #757 - Don't use Anonymous Inner Classes
  * Remove some extra spaces
  * Don't use an array as backend for Operators Task #757 - Don't use Anonymous Inner Classes
  * Don't use an array as backend for Functions Task #757 - Don't use Anonymous Inner Classes
  * Make all classes serializable Task #749 - Garantizar que las operaciones sean serializables
  * Make Operators static inner classes instead of Anonymous Inner Clasess Task #757 - Don't use Anonymous Inner Classes
  * Make Funtions static inner classes instead of Anonymous Inner Clasess Task #757 - Don't use Anonymous Inner Classes
  * Use array for user-defined function names Task #749 - Garantizar que las operaciones sean serializables
  * Update README.md
  * Update README.md

0.6-riddler / 2018-04-03
========================

  * Merge pull request #18 from RiddlerArgentina/issue-91
  * Bump to version 0.6
  * Expose all the Functions of the extras package
  * Change the internal implementation of Functions
  * Change my mind, one change per patch
  * Adapt tests for new code
  * Add disable builtin implementations
  * Merge pull request #17 from RiddlerArgentina/issue-79
  * Move VAR_NAME_PATTERN to top (Codacy)
  * Disallow all whitespace characters in variable name
  * Fix issue 79
  * Add tests for issue 79
  * Merge pull request #16 from RiddlerArgentina/issue-88
  * Add a second test to checkout if correct expression is still valid
  * Fix issue 88
  * Add test for issue 88
  * Change factorial limit to 170
  * Remove deprecated test
  * Add upper limit for factorial
  * Merge pull request #14 from RiddlerArgentina/codacy-issues
  * Move constructor
  * Update to RiddlerArgentina
  * Update README.md
  * Update README.md
  * Make exp4j Disclamer more obvious
  * Merge pull request #13 from dktcoding/make-functions-final
  * Make functions final
  * Merge pull request #12 from dktcoding/going-for-a-hundred
  * Remove useless default (switch over ENUM)
  * Extract function
  * Remove unused variable
  * Extract some functions to improve readability
  * Make simplifier final
  * Remove useless check
  * Make utility clasess final
  * Format file and add Exception test
  * Add sanity test for operators list
  * Create private constructors for utility classes
  * Add Coverage Badge
  * Merge pull request #11 from dktcoding/remove-junit-jar
  * Remove test from ant script (testing is done with maven)
  * Remove jUnit jars from project
  * Last codacy fix... for now
  * Merge pull request #10 from dktcoding/misc-issue-fix-codacy
  * Improve some codacy warnings
  * Honor static imports
  * Remove unused variables
  * Silence some warnings
  * Reduce visibility of some methods
  * Use asserts in all tests...
  * Update email address
  * Declare attributes on top
  * One line per declaration
  * Remove unused argument
  * Add gitignore rule for target/
  * Avoid using a branching statement as the last in a loop.
  * Remove empty constructor
  * Remove unused import
  * Merge pull request #9 from dktcoding/Codacy-coverage
  * I'm getting really tired of this....
  * New try....
  * Add version number to POM
  * Test over newer version of Java
  * Use mvn for testing
  * Fix POM.... I think....
  * Add maven build
  * Added Codacy coverage
  * Merge pull request #8 from codacy-badger/codacy-badge
  * Move Codacy badge
  * Add Codacy badge
  * Add gitignore rule for DS_store
  * Merge pull request #7 from dktcoding/add-even-more-tests
  * Remove unused code
  * Missing tests for Functions (JaCoCo)
  * Missing tests for FunctionsMisc (JaCoCo)
  * Tests for FunctionBoolean (JaCoCo)
  * Add test that failed to merge
  * Merge pull request #6 from dktcoding/add-more-tests
  * Fix merge conflicts
  * Test wrong number of arguments for operator
  * Add test for signum (JaCoCo)
  * Test Expression copy with set values
  * Test Expression#toString()
  * Remove trailing spaces
  * Add missing tests (JaCoCo)
  * Add test for ExpressionBuilder#operators()
  * Add test for ExpressionBuilder#functions()
  * Test Empty expression
  * Merge pull request #4 from dktcoding/fix-performance-test
  * Merge pull request #5 from dktcoding/silence-warning
  * Suppress an unchecked warning
  * Improve test
  * Fix the performance test
  * Merge pull request #3 from dktcoding/fix-copying
  * Fix: compile in Java 7
  * Fix Expression#copy()
  * Add test for multiple variables in copy()
  * Add some paranoid factorial tests
  * Add test for issue 75 of original exp4j
  * Add Travis-CI build badge
  * Add Travis CI
  * Merge pull request #2 from dktcoding/add-factorial-operator
  * Fix: sanity test for factorial
  * Add another test... jic
  * Add factorial operator test
  * Add factorial operator
  * Remove repo warning
  * Add test for function with no arguments
  * Remove Maven scripts
  * Update .gitignore
  * Added Expression#copy() and tests
  * Rename ConcurrencyTests and "extra" test
  * Add note in ExpressionBuilder methods
  * Rename operator to operators so it's consisten with the rest
  * Tweak a bit ValidationResult
  * Add JavaDoc to all public methods of Expression
  * Fix email address
  * Merge pull request #1 from dktcoding/use-enum-as-token-type
  * Disable performance test again
  * Added tests for toString() & toTokenString()
  * Add toString() to ExpressionBuilder
  * Add missing JavaDoc for Function#isDeterministic()
  * Add toString() and toTokenString() to Expression
  * Use enum instead of constant list for tokens
  * Add links to the original exp4j and building instructions
  * Fix typo in README.md
  * Change README to README.md
  * Remove deprecated getAllowedFunctionCharacters()
  * Add test for the expression in the performance test
  * Small style improvement
  * Make ArrayStack final
  * Update ShuntingYard and it's test to use the new simplifier and TokenStack
  * Move the Simplifier to the shuntingyard package
  * Add TokenStack to replace Stack<Token>
  * Minor style corrections
  * Make Token.getType() final
  * Use String.format() to handle messages
  * Add simplifier tests for boolean operators and if()
  * Adapt ExpressionBuilderTest for e and pi as functions.
  * Added pi() and e() to Functions
  * Added a small sanity check for operators and functions
  * Add FunctionsMisc class and tests
  * Fix: JavaDoc errors
  * Added OperatorsComparison class and tests
  * Added new Ant targets (package-sources,package-docs,build-all)
  * Added FunctionsBoolean class and tests
  * Added boolean operators as built-in
  * Make Functions Abstract and replace if-else chain for switch
  * Break long lines and remove constants
  * Don't allow setting inexistent variables
  * Create just one VariableToken object per variable
  * Fix test: it should fail to set inexistent variables
  * Update and disable the performance test
  * Remove the use of variables as constants
  * Change source to 1.7
  * Add .gitignore
  * Add Ant script and junit jars
  * Add simplifier
  * Add a warning to the README file
  * Changed some minor aspects of the variable name fetching feature
  * Merge pull request #68 from risenhoover/master
  * Get a list of variables before evaluation
  * Allowed '.' character in variable names ======================================= See https://github.com/fasseg/exp4j/issues/65
  * [maven-release-plugin] prepare for next development iteration

exp4j-0.4.7 / 2016-04-22
========================

  * [maven-release-plugin] prepare release exp4j-0.4.7
  * Merge pull request #61 from sarxos/master
  * Merge pull request #63 from fasseg/issue-62
  * cleaned up test
  * collapsed multiple ifs for last operator checking
  * fix for https://github.com/fasseg/exp4j/issues/62
  * Add possibility to extract missing variable/function name from exception
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.6
  * fixed release number since it's 0.4.6
  * release preparation 0.4.7
  * Added fix for zero length arg lists in functions. See https://github.com/fasseg/exp4j/issues/59
  * Avoided autoboxing as suggested by serso ========================================  - See    https://github.com/serso/exp4j/commit/ab781d6386f62c6d156935699cc495acf7c3aa2f
  * added serso as contributor
  * Eliminating reversing of arguments array ========================================  - cherry picked this from serso's PR:    https://github.com/serso/exp4j/commit/c6e876244c2cfca7eefc4edff721f4d8a1b820ea
  * added Thread Safety test for future Expression thread safety
  * Merge pull request #43 from plison/master
  * adding final to the parameter
  * allow an expression to be copied
  * allow an expression to be copied
  * added signum function to javadoc overview
  *  Added numerical constants and signum function ============================================== - https://github.com/fasseg/exp4j/pull/33 - https://github.com/fasseg/exp4j/pull/38
  * Merge pull request #38 from alexeyegorov/master
  * space after brace
  * added braces for if else statements
  * array size corrected
  * add sign function as builInFunction
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.5
  * [maven-release-plugin] rollback the release of exp4j-0.4.5
  * [maven-release-plugin] prepare release exp4j-0.4.5
  * added changes to xml file
  * Fixing same name of functions and variables. See https://github.com/fasseg/exp4j/issues/29
  * added Leo's email addy
  * added Leo as a contributor
  * added Bartosz' url
  * Merge pull request #35 from sarxos/patch-1
  * added Bartosz as a contributor to the pom
  * Merge pull request #36 from sarxos/patch-3
  * Fix broken functions validation
  * More detailed exception message
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.5
  * [maven-release-plugin] rollback the release of exp4j-0.4.5
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.5
  * [maven-release-plugin] rollback the release of exp4j-0.4.5
  * [maven-release-plugin] prepare release exp4j-0.4.5
  * [maven-release-plugin] rollback the release of exp4j-0.4.5
  * [maven-release-plugin] prepare release exp4j-0.4.5
  * [maven-release-plugin] rollback the release of exp4j-0.4.5
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.5
  * changed angle brackets since maven-site-plugin doesn't like it
  * added *federico* as a contributor, sorry for typo ;)
  * added frederico as a contributor
  * refactored ArrayStock in right package and changed visibility of class/methods
  * cherry picked the ArrayStack from https://github.com/fasseg/exp4j/issues/28
  * deprecated Function.getAllowedFunctionCharacters() as discussed in https://github.com/fasseg/exp4j/issues/27
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.4
  * Fix for https://github.com/fasseg/exp4j/issues/23
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.3
  * updated docs
  * Merge pull request #21 from fasseg/unicode-vars
  * updated documentation to include multi symbol operators
  * updated a test
  * fixed handling of unicode characters
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.3.BETA-3
  * removed calls to String.isEmpty()
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.3.BETA-2
  * substituted calls to Character.isAlphabetic
  * updated text again
  * Re
  * fixed text
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.3.BETA-1
  * [maven-release-plugin] rollback the release of 0.4.3.BETA-1
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.4.3.BETA-1
  * added scpeexe extension
  * [maven-release-plugin] rollback the release of exp4j-0.4.3.BETA-1
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.3.BETA-1
  * changed to scpexe
  * [maven-release-plugin] rollback the release of exp4j-exp4j-0.4.3.BETA-1
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-exp4j-0.4.3.BETA-1
  * [maven-release-plugin] rollback the release of exp4j-0.4.3.BETA-1
  * [maven-release-plugin] prepare release exp4j-0.4.3.BETA-1
  * [maven-release-plugin] rollback the release of exp4j-0.4.3.BETA-1
  * [maven-release-plugin] prepare release exp4j-0.4.3.BETA-1
  * updated upload url
  * [maven-release-plugin] rollback the release of exp4j-0.4.3.BETA-1
  * [maven-release-plugin] prepare release exp4j-0.4.3.BETA-1
  * [maven-release-plugin] rollback the release of exp4j-0.4.3.BETA-1
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.3.BETA-1
  * changed target and source level to 1.6 to accomodate Android users
  * added check for spaces in between numbers
  * updated small doc
  * updated contact mail address
  * added link to marek's php port
  * bumped version
  * Merge pull request #14 from fasseg/validation
  * updated documentation/tests
  * added a comment to the validation routine
  * fixed typo
  * added a better validation mechanism
  * added some tests to improve code coverage
  * Merge pull request #13 from fasseg/futures
  * fixed bold
  * added documentation for future
  * bumped version
  * added Futures
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.0.ALPHA-3
  * fixed site
  * updated documentation
  * added some tests for implicit multiplication
  * fixed implicit multiplication for numbers
  * added variable declaration methods
  * fixed test call
  * some more cleanup and refactoring
  * some cleanup and pom reorganization for reporting
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.4.0.ALPHA-2
  * bumped some versions
  * added snapshot repo
  * fixed repo
  * fixed scp extension
  * added swagon extension
  * fixed sonatpye links
  * fixed site and distri management
  * bumped version
  * added distribution management section
  * removed bundle plugin
  * fixed execution
  * fixed version number
  * added bundle plugin
  * added bundle plugin
  * added some plugins for release
  * added log2, log10 and fixed documentation
  * merged devel branch for alpha release
  * updated documentation and fixed unary handling
  * added some documentation, and fixed unary/power precedence, but now an errror happens
  * fixed some routines to pass all the tests
  * fixed bugs in numberparsing and added missing exp and expm1 functions
  * added some fixes
  * imported old exp4j tests
  * added performance tests, and fixed an shunting yard error
  * added scientific notation parsing
  * added validation result tests
  * added custom operator handling and test
  * added custom functions and some tests
  * added ExpressionBuilder and some tests
  * added Expression and some tests
  * fixed buf in shunting yard
  * added some missing tokens and a shunting yard skeleton
  * added perf test to tokenizer
  * added variables, parantheses and functions to tokenizer
  * added function skeleton
  * fixed multiple unary operators at the beginning of an expression
  * added missing unary operator and added some tests
  * added operator skeleton
  * initial commit AGAIN!
  * removed complex and big decimal stuff, added tests
  * some formatting
  * updated to newer Version of BigDecimalMath. removed mcp2
  * Added perf test for complex numbers
  * added some more complex functions and tests
  * fixed tests fro BigDecimals
  * fixed BigDecimal argument order for func and ops
  * renamed tokenizer and added some complex math. ficed the order of function and operator arguments
  * added complex number functions log, power, arg, mod
  * added simple complex arithmetic and some tests
  * added ComplexNumber parsing and test
  * moved some things around and tried to fix BigDecimalMath for precise PI values
  * added some big decimal math functions
  * added method for calculting sine by a power series
  * added cos test
  * fixed CORDIC algo, but theree's still an error in there...
  * fixed K table
  * added failing test for sin(1/3)
  * added known values to sine function
  * added sqrt function with error calc and added tables to cordic
  * added sqrt and sin PoC for BigDecimals
  * added variable handling and perf test
  * added skeleton for BigDecimal and Complex calculations
  * added custom function and operator tests
  * added ExpressionBuilder and a text, refactored Calculable to Expression
  * renamed calculable interface to expression
  * added custom function, operator and variable handling for tokenizer and shunting yard
  * added DoubleCalculable and some tests
  * added ShuntingYard implementation and some tests
  * added shunting yard impl
  * added custom function tests and fixes for tokenizer
  * added customoperator tests and fixes
  * initial creation of tests and addition of FastTokenizer
  * added same loop to list gen
  * added method to create a List of Tokens from the FastTokenizer
  * added FastTokenizer and a small comparison
  * added complex tokenization tests and continued Complex tokenization implementation
  * updated NextGenTokenizer to parse ComplexNumber
  * implemented NextGenTokenizer for Real expressions and tests
  * bumped version to 0.4.0-SNAPSHOT
  * added some failing tests for the Complex Number tokenizer
  * updated formatting of bench putput to include a percentage of java.lang.Math
  * added static api methods to expressionbuilder
  * added Future support
  * added some complex constants
  * added some special handling in powers of complex numbers
  * added some special handling in powers of complex numbers
  * added some more complex functions and tests
  * fixed factorial custom operator for beigdecimal test
  * added sqrt and benchmark for complex numbers
  * updated Complex Number handling and added some tests and functions from Complex numbers
  * added simple complex tokenization
  * added BigDecimalTest and added UnsupportedOperationExceptions in a lot of BigDecimal builtin functions and operators
  * moved benchmarks to their own test class
  * moved calculate method to abstract class and made it generic for Double and Float and added tests for Double precision
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.3.11
  * [maven-release-plugin] rollback the release of exp4j-0.3.11
  * [maven-release-plugin] prepare release exp4j-0.3.11
  * updated version of site plugin to fix https://cwiki.apache.org/confluence/display/MAVEN/AetherClassNotFound
  * [maven-release-plugin] rollback the release of exp4j-0.3.11
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.3.11
  * reabsing branch exp-32 onto master for bugfix release preparation ================================================================= - Fix for https://www.objecthunter.net/jira/browse/EXP-32 - changed call to 'String.isEmpty()' to a length() check in order to fix a MethodNotFoundError in Android < 2.3 - updated changes.xml file for release preparation
  * updated pom config for cobertura
  * fixed various tests and added checks for unparseable expressions
  * fixed test for custom functions
  * updated some tests
  * added exceptions and checking for Unknonw names
  * added variable handling
  * fixed some issues fixed som etests
  * added custom operators/funtions copied tests from exp4j v0.3
  * added variables and benchmarking for floats
  * fixed a bug in the shunting yard algorithm
  * added support for unary minus/plus
  * typo in method name
  * first implementation of float typ exp4j
  * added some more skeleton
  * changed tokenizer to be agnostic of complex syntax
  * fixed parsing of decimal separator fomr complex part
  * added simple parsing of Complex numbers
  * initial rewrite
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.3.10
  * added changes for release prep
  * added a test case
  * removed old code
  * Fix for https://www.objecthunter.net/jira/browse/EXP-25
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.3.9
  * added change
  * fixed readme link
  * EXP-24: Fix for exception when parsing expressions with scientific notation
  * changed java version to 1.6 in maven's pom
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.3.8
  * added trim() call to check for empty expression
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.3.7
  * added Division by zero check to modulo
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release exp4j-0.3.6
  * Merge branch 'exp-23'
  * added fix for EXP-23
  * removed check from modulo operator since modulo returns NaN as exptrected
  * added a test for NAN results when processing square roots of negative values
  * Added check for Division by zero and tests
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.3.5
  * fixed upload path for releases
  * [maven-release-plugin] rollback the release of 0.3.5
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.3.5
  * [maven-release-plugin] rollback the release of 0.3.5
  * [maven-release-plugin] prepare release 0.3.5
  * fixed upload path for the website
  * [maven-release-plugin] rollback the release of 0.3.5
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.3.5
  * removed support for EXP-18 ==========================  - problem is that ambiguities arise  - e.g. declare variable 'x' and variable 'xx' then evaluate 'xx' and the interpreter will not know if x*x or the var xx is meant.
  * added support for EXP-18
  * Added check if implicit multiplication is used ==============================================  - But since i now have to check explicitly for these error condition onoe could also replace them by proper multiplications and therefore implement the feature EXP-18
  * fixed noramlization of scientific notation
  * EXP-20: No exception is thrown for unmatched parenthesis in build =================================================================  - added a check for bracket count, so that unmatched paratheses can be reported to the user
  * EXP-17: parse Exponential (scientific) eg: 1.5e+3 ->1500 ========================================================  - added handling of numbers in scientific notation
  * EXP-19: error message does not count spaces ============================================  - fix for https://www.objecthunter.net/jira/browse/EXP-19  - changed error message created when unable to parse an expression to include the expresion itself  - changed subtitueUnaryOperators() to leave Whitespace untouched instead of removing it from an expression
  * added getArgumentCount() implmentation to CustomFunction
  * Revert "EXP-18: Add support for implicit multiplication"
  * EXP-18: Add support for implicit multiplication ===============================================  - added check for implicit multiplication usage  - added test which checks that a proper exception is thrown
  * EXP-17:parse Exponential (scientific) eg: 1.5e+3 ->1500 =======================================================  - added feature to parse numbers in scientific notation  - added tests for scientific notation
  * added header to site descriptor
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.3.4
  * arg used 1.7 only method instead of Character.isLetter()
  * bumped version
  * removed some warnings from unused imports
  * [maven-release-plugin] rollback the release of 0.3.3
  * fixed EXP-16 added documentation and a parameter check for the variable name
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.3.3
  * [maven-release-plugin] rollback the release of exp4j-0.3.3
  * [maven-release-plugin] prepare release exp4j-0.3.3
  * fixed wrong version reference in comment
  * Fix for http://jira.congrace.de/jira/browse/EXP-14
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.3.2
  * [maven-release-plugin] rollback the release of 0.3.2
  * [maven-release-plugin] prepare release 0.3.2
  * [maven-release-plugin] rollback the release of 0.3.2
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.3.2
  * Added possibility to influence precedence of unary minus
  * added simple concurrency tests
  * Fixed precedence of unary minus operator ========================================  - fixed evaluation of e.g. (-3^2) to result in -9, instead of 9.
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.3.1
  * FIX for EXP-11: Modulo operator '%' had the wrong associativity ===============================================================  - updated the associatitvity of the operator  - thanks go out to ryan holliday for finding this bug.
  * Added test fro RPNConverter for Issue EXP-11
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.3.0
  * Added automated singing for sonatype deployment
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.3.0
  * [maven-release-plugin] rollback the release of 0.3.0
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.3.0
  * [maven-release-plugin] rollback the release of 0.3.0
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.3.0
  * Merge branch '1.0-DEV' from Github ==================================
  * Added test for EXP-11 =====================
  * changed visibility to portected so users can actaully implement..gnaah
  * Fixed some more doc issues
  * fixed size of the jar which grew a bit in the readme
  * Fixed some doc issues
  * Added some documentation and API change notes in the docu
  * Updated changes document
  * Added some javadocs for CustomOperator and fixed precedence problems
  * Added some tests
  *  Fixed bug with operator scanning
  * Alowed "=" in CustomOperators =============================  - changed inpiut format for functions so that "f(x)=" decalaration are no longer possible this is an API change  - see http://jira.congrace.de/jira/browse/EXP-1
  * Set constrinats on the symbols to use for CustomOperators =========================================================  - added logic to dont let the user overwrite the builtin operators
  * Added functionality to have operators symbols be represented by Strings =======================================================================  - changed the cistructor for CustomOperator to take a String as the operator symbol  - changed the strucutre and the unary opertor substitutions  - still a problem with the = character because of normalizeExpression()
  * Added reference to jHepWOrk
  * FIXED visibility of CustomOperator ==================================  - changed Consutrctors from package private to protected (see http://jira.congrace.de/jira/browse/EXP-1)
  * Updated site and refactored names =================================  - updated site and user guide for custom operators  - renamed Operation to CustomOperator for least astonishment  - added sanity check to function name
  * Added validity check for operator symbols =========================================  - only allowing use of a small set of symbols  - added sanity check for the symbols
  * minor fix for the test associativity for factorial
  * Cleaned up & added some tests =============================  - added custom operator tests
  * added test case for faculty operator
  * increased version number to reflect changes
  * Rewrite of great parts to enable custom operations ==================================================  - removed PostFixExpression  - reimplemented RPNConverter and Tokenizer  - added custom operations
  * updated scm url for github
  * Changed pom.xml to include sonatype repos for publishing on maven central
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.9
  * [maven-release-plugin] rollback the release of 0.2.9
  * [maven-release-plugin] prepare release 0.2.9
  * added scpexe extension
  * [maven-release-plugin] rollback the release of 0.2.9
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.9
  * 0.2.9 rel prep
  * [maven-release-plugin] prepare release 0.2.8
  * [maven-release-plugin] rollback the release of 0.2.8
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.8
  * fixing releases
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.7
  * fixing releases
  * [maven-release-plugin] rollback the release of 0.2.8
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.8
  * fixing releases
  * fixing releases
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.7
  * [maven-release-plugin] rollback the release of 0.2.6
  * [maven-release-plugin] prepare release 0.2.6
  * [maven-release-plugin] rollback the release of 0.2.6
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.6
  * release 0.2.6: fixed release settings
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.5
  * Release 0.2.5 preparation
  * updated size info
  * removed extra unary plus handling, updates site
  * added small docu for multi argumetn functions
  * fixed mutliargument support in custom functions
  * addd some tests -- multi argumetn still broken
  * added CustomFunction with variable arguments\nThis is a broken commit since mutliargumetn functions wont work as intended
  * [maven-release-plugin] rollback the release of 0.2.5
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.5
  * fixed version numbers
  * fixed encoding placeholder in pom
  * merged with origin
  * added check if custom function name clashes with existing one
  * added mailing list to site
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.4
  * added references to site
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.3
  * preparing 0.2.3 release
  * fixed docu typo
  * marked PostfixExpression.fromInfix deprecated
  * added some function/NaN tests
  * changed benches
  * fixed some typos
  * cleaned up bench
  * added javascript benchmark
  * removed role
  * added browseable scm url
  * [maven-release-plugin] rollback the release of 0.2.2
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.2
  * [maven-release-plugin] rollback the release of 0.2.2
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.2
  * fixed issue with variable and custom function having the same name
  * added simple benchmark
  * added simple benchmark
  * [maven-release-plugin] prepare for next development iteration

0.2.1 / 2011-07-08
==================

  * [maven-release-plugin] prepare release 0.2.1
  * [maven-release-plugin] rollback the release of 0.2.1
  * [maven-release-plugin] prepare release 0.2.1
  * [maven-release-plugin] rollback the release of 0.2.1
  * [maven-release-plugin] prepare for next development iteration
  * [maven-release-plugin] prepare release 0.2.1
  * release 0.2.1
  * release 0.2.1
  * merged with master, this time for real :/
  * merged with master
  * refactoring + fixed spelling
  *  updated minor version
  *  updated minor version
  * refactoring for reduced API visibility
  * fixed issue custom function in OperatorToken
  * added documentation to apt site
  * updated apidocs for custom functions
  * added custom functions
  * added custom functions
  * removed ExpressioNBuilder example from README
  * removed version String on README
  * fixed spelling
  * added README
  * added ExpressionBuilder, moved to github
