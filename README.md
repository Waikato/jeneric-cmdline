# jeneric-commandline

Java library for adding command-line handling to arbitrary classes, via introspection. 

## Example

```java
import nz.ac.waikato.cms.jenericcmdline.DefaultProcessor;
import nz.ac.waikato.cms.jenericcmdline.example.Simple;
import nz.ac.waikato.cms.jenericcmdline.example.Nested;

// set up simple object values
Simple simple = new Simple();
simple.setTruth(true);
simple.setEightBit((byte) 7);
simple.setSixteenBit((short) 42);
simple.setThirtyTwoBit(314);
simple.setSixtyFourBit(31415);
simple.setFloatie(0.123f);
simple.setQuadrupleHalf(123.456);
simple.setMoreThanOneTruth(new boolean[]{true, false, true});
simple.setManyInts(new int[]{1, 2, 3});
simple.setSomeDoubles(new double[]{0.1, 0.2, 0.3});

// another simple object
Simple simple2 = new Simple();
simple2.setTruth(false);
simple2.setEightBit((byte) 1);
simple2.setFloatie(1.234f);

// object with other nested, non-primitive objects
Nested nested = new Nested();
nested.setSimple(simple);
nested.setSimpleArray(new Simple[]{new Simple(), simple2});
nested.setFloating(0.456);
nested.setIntegral(1234);

// cmdline generation and parsing
DefaultProcessor processor = new DefaultProcessor();
String cmdline;

System.out.println("\nSimple");
cmdline = processor.toCommandline(simple);
System.out.println("-initial commandline:\n" + cmdline);
Simple simpleNew = (Simple) processor.fromCommandline(cmdline);
cmdline = processor.toCommandline(simpleNew);
System.out.println("-to and from commandline:\n" + cmdline);

System.out.println("\nNested");
cmdline = processor.toCommandline(nested);
System.out.println("-initial commandline:\n" + cmdline);
Nested nestedNew = (Nested) processor.fromCommandline(cmdline);
cmdline = processor.toCommandline(nestedNew);
System.out.println("-to and from commandline:\n" + cmdline);
```

## Maven

Add the following dependency to your `pom.xml`:

```xml
    <dependency>
      <groupId>com.github.waikato</groupId>
      <artifactId>jeneric-cmdline</artifactId>
      <version>0.0.1</version>
    </dependency>
```
