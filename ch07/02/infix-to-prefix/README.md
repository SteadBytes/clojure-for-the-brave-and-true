# infix-to-prefix

Convert infix mathematical expressions to prefix.

Note: No real error checking, invalid input expressions will throw exceptions.

## Usage

```bash
# build jar
$ lein uberjar
$ java -jar infix-to-prefix-[VERSION]-standalone.jar --eval EXPR1 EXPR2...
```

## Options

- `--eval`: Evaluate converted expression and output result
- `-h`: Show usage

## Examples

```bash
$ java -jar infix-to-prefix-[VERSION]-standalone.jar (1 + 2 * 3)
(+ 1 (* 2 3))

$ java -jar infix-to-prefix-[VERSION]-standalone.jar --eval
7
```
