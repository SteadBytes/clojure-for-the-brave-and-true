# the-divine-cheese-code

FIXME: description

## Installation

Download from http://example.com/FIXME.

## Usage

FIXME: explanation

    $ java -jar the-divine-cheese-code-0.1.0-standalone.jar [args]

## Notes

Clojure has a one-to-one mapping between a _namespace_ name and the path of the file where it is declared. Conventions:

- `lein new` creates a `src` directory as the source code's root
- Dashes in namespace = underscores in the file system
  - `the-divine-cheese-code` -> `the_divine_cheese_code`
- `.` in namespace denotes a directory
- Final component of a namespace = `.clj` file
- `the-divine-cheese-code.core`:
  ```
  the_divine_cheese_code # project root
  └── src # source code root
      └── the_divine_cheese_code # directory in namespace name
          └── core.clj # final component in namespace name
  ```

`ns` cheatsheet refrence: https://gist.github.com/ghoseb/287710/