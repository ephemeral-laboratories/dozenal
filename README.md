Dozenal
=======

Very preliminary dozenal locale implementation for Java.


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

Other variations might work too. Research is ongoing.
