# jPack

Writing network code in Java isn't nice. It lacks unsigned types and POJO can't be simple packed & unpacked.
Fear not! This handy (hopefully) set of classes let's you annotate POJOs to make it simple to read & write network protocols.

# Adding the dependency

We use [Maven](http://maven.apache.org/) for building & distributing jPack. You're welcome to use our Maven repositories, or build your own .jar.

To use our Maven repos just add:

    <repositories>
        <repository>
            <id>monits-snapshots</id>
            <url>http://nexus.monits.com/content/repositories/oss-snapshots/</url>
            <name>Monits Snapshots</name>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.monits</groupId>
            <artifactId>jpack</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

To build a .jar from source:

>
> mvn clean install
>

# Using jPack

The usage is simple, annotate your POJOs and then use `Packer.pack` or `Packer.unpack`.

## `@Encode`
jPack will only pay attention to fields annotated with `@Encode`, any other fields will be ignored. The important part is that this annotation lets jPack know the order of the fields in the byte stream, via a non-optional integer parameter. jPack expects that a getter and a setter (following the Java Bean convention) exist for a field with `@Encode`.

```java

public class FirstExample {
    @Encode(0)
    private byte first;

    @Encode(1)
    private byte second;

    public byte getFirst() {
        return first;
    }

    public void setFirst(byte first) {
        this.first = first;
    }

    public byte getSecond() {
        return second;
    }

    public void setSecond(byte second) {
        this.second = second;
    }

}
```

## Unsigned integers

Java has no unsigned types. That sucks. So of course jPack gives you a way to use unsigned integers. `@Unsigned` indicates an integer type field is unsigned. This applies to arrays of integer types too. A standard conversion is applied to fit unsigned types in Java's signed types:
 - A short represents an unsigned byte
 - An int represents an unsigned short
 - A long represents an unsigned int

```java
    @Encode(0)
    @Unsigned
    private long anUnsignedInt;
```

## Nested types

When jPack finds a type it doesn't know of in a field annotated with `@Encode` it tries to treat that type as another annotated POJO. That means the following will work as expected: 

```java

public class NestedObject {

    @Encode(0)
    @Unsigned
    private int field;

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

}

public class BigObject {

    @Encode(0)
    private NestedObject obj;

    public void setObj(NestedObject obj) {
        this.obj = obj;
    }

    public NestedObject getObj() {
        return obj;
    }
}
```

And when I say "as expected" I mean, NestedObject and BigObject will pack & unpack in the same way.

## Arrays

You can have arrays of any type, as long as jPack knows how to treat that type. You only have to indicate the length of the array in some way.

### Fixed length arrays

With `@FixedLength` you can indicate that an array will always have the same length. Say you have an array of four unsigned integers, you just write:

```java
    
    @Encode(0)
    @FixedLength(4)
    private int[] myInts;

    public int[] getMyInts() {
        return myInts;
    }

    public void setMyInts() {
        this.myInts = myInts;
    }

```

### Variable length arrays

Sadly, jPack can't do magic and guess the length of your array. If you have an array of variable length, there must be a another field that indicates it's length. To tell jPack which field it is, just use `@DependsOn` (More on this annotation on the Custom Codec section).

Here we define an array of bytes, that will have it's length determined by a field called `totalBytes` (getters and setters skipped for brevity):

```java

    @Encode(0)
    @Unsigned
    private long totalBytes;

    @Encode(1)
    @DependsOn({ "totalBytes" })
    private byte[] lotsaBytes;

```

## Custom Codec

Sometimes you need a little extra, thus Custom Codecs. Any class that implements the interface `Codec` can be used to pack & unpack fields. By annotating a field with `@UseCodec` you can force jPack to use that codec.

### `@DependsOn`

If your codec needs to know the values of other fields before doing it's magic, you can add `@DependsOn` on the field. jPack will just get those values for you and make them readibly available at coding/decoding time.


# Contributing

We encourage you to contribute to this project! 

We are also looking forward to your bug reports, feature requests and questions.

# Copyright and License

Copyright 2012 Monits.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License at: 

http://www.apache.org/licenses/LICENSE-2.0

