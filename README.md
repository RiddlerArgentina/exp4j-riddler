This `exp4j` [![](https://gitlab.com/riddler_arg/exp4j/badges/master/build.svg)]() [![coverage](https://gitlab.com/riddler_arg/exp4j/badges/master/coverage.svg)](https://docs.riddler.com.ar/exp4j/jacoco/)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ad8fa724765e4d7ba58400d070c5c868)](https://www.codacy.com/app/RiddlerArgentina/exp4j-riddler?utm_source=gitlab.com&amp;utm_medium=referral&amp;utm_content=riddler_arg/exp4j&amp;utm_campaign=Badge_Grade)

----------
> # ⚠️ Disclaimer
> This is a **modified** version of [`exp4j`](https://github.com/fasseg/exp4j) which 
> contains [changes](https://redmine.riddler.com.ar/projects/exp4j/wiki/Differences_with_the_original_exp4j) that will most likely create problems for regular 
> [`exp4j`](https://github.com/fasseg/exp4j) users.
> 
> We will change this fork to fit our specific needs, consider using the original
> [`exp4j`](https://github.com/fasseg/exp4j) which will always have the latest features 
> and it will be more stable, updated, backwards compatible and small.

[`exp4j`](https://github.com/fasseg/exp4j)
-----
[`exp4j-riddler`](https://redmine.riddler.com.ar/projects/exp4j) is a mathematical expression evaluator for
the Java programming language. It is a simple-to-use and small library (~52kb) without 
any external dependencies.

Building
--------
```bash
    $ git clone https://gitlab.com/riddler_arg/exp4j.git
    $ cd exp4j
    $ ant build-all
```

Building without extras
-----------------------
```bash
    $ git clone https://gitlab.com/riddler_arg/exp4j.git
    $ cd exp4j
    $ rm -rf src/main/java/net/objecthunter/exp4j/extras
    $ ant build-all
```

Hacking
-------
- [English Documentation](https://redmine.riddler.com.ar/projects/exp4j/wiki/Getting_Started)
- [Spanish Documentation](https://redmine.riddler.com.ar/projects/exp4j/wiki/Getting_Started_es)
- [Test Coverage Reuslts](https://docs.riddler.com.ar/exp4j/jacoco/)
- [JavaDocs](https://docs.riddler.com.ar/exp4j/apidocs/)
- [Forums](https://redmine.riddler.com.ar/projects/exp4j/)