This `exp4j`
----------
This is a **modified** version of [`exp4j`](https://github.com/fasseg/exp4j) which 
contains changes that will most likely create problems for regular 
[`exp4j`](https://github.com/fasseg/exp4j) users.

I will change this fork to fit my specific needs, consider using the original
[`exp4j`](https://github.com/fasseg/exp4j) which will always have the latest features 
and it will be more stable, updated, backwards compatible and small (this one is 
already ~55kb).

[`exp4j`](https://github.com/fasseg/exp4j)
-----
exp4j is a mathematical expression evaluator for the Java programming language.
It is a simple-to-use and small library (~40kb) without any external
dependencies.

Check out http://www.objecthunter.net/exp4j/ for documentation and examples

Building
--------
The maven script still points to the original [`exp4j`](https://github.com/fasseg/exp4j)
I will leave that behaviour for the time being in order to help those who endend
up in this repository by mistake.

Building **this** repository is as simple as:
- `git clone https://github.com/dktcoding/exp4j.git`
- `cd exp4j`
- `ant build-all` 
