Dozenal ![Java CI with Gradle](https://github.com/ephemeral-laboratories/dozenal/workflows/Java%20CI%20with%20Gradle/badge.svg)
=======

Very preliminary dozenal locale implementation for Java.


What is Dozenal?
----------------

Dozenal is one of the popularised names for a positional number system using twelve
as the base. Proponents of the dozenal system claim that it is superior to decimal
because many common fractions are more neatly represented in dozenal.

For example:

* 1/3 = 0.4
* 1/4 = 0.3
* 1/5 = 0.24972497...
* 1/6 = 0.2

For this project I intend to use the Unicode ↊ for ten/dec and ↋ for eleven/el.


Design Outline
--------------

`LocaleServiceProvider` allows one to register new locales.

There appears to be no particular reason that numbers have to be formatted in base ten,
therefore it should be possible to slot in a provider for a locale where people use a
different radix.


Usage
-----

Put the jar somewhere in `java.ext.dirs` or on the boot classpath
via `-Xbootclasspath/a:`.

Then, run Java with additional system properties to set the locale:

```
-Duser.language=en
-Duser.country=US
-Duser.variant=DOZ
-Djava.locale.providers=SPI,JRE
```

Other variations might work too.

Calls to `NumberFormat.getNumberInstance`, `NumberFormat.getIntegerInstance`,
`NumberFormat.getPercentInstance` and the like will now format in dozenal.


Caveats
-------

Not all Java programmers know that the best way to format numbers is via
`NumberFormat`. So if you intend to slap this on an application and get it
in dozenal, expect to encounter the following:

* Calls to `String.format` passing numbers into format strings with `%d`
  which bypass `NumberFormat` for some reason (they don't have to - JDK
  please improve this)
* Calls to `String.valueOf` passing numbers
* Calls to `Integer.toString`, `Float.toString` and friends
* Numbers being concatenated directly onto strings
* Hardcoded usages of `DecimalFormat`

All of these can be replaced with calls which go via the SPI using various
techniques, but it's a long road ahead, and a lot of education on how to
use the standard libraries will be required.
 