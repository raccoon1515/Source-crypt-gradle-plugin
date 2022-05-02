# Source crypt Gradle plugin

[![Build](https://github.com/raccoon1515/source-crypt-gradle-plugin/actions/workflows/gradle.yml/badge.svg)](https://github.com/raccoon1515/source-crypt-gradle-plugin/actions/workflows/gradle.yml)

A Gradle plugin that allows you to encrypt your source code with _AES PBE_ and decrypt it at runtime.
Plugin supports resource encryption only. This is done by gradle **encryptResources** task,
which is automatically applied before **processResources** task.

## Install

The latest plugin version is available
at [gradle plugin portal](https://plugins.gradle.org/plugin/io.github.raccoon1515.sourcecrypt).

```groovy
plugins {
    id 'io.github.raccoon1515.sourcecrypt' version '1.0'
}
```

## Usage

Encryption requires **password** and **salt**. It can be provided with plugin extension:

```groovy
crypt {
    password = 'my-verRy-secRet-passw0Rd'
    salt = 'very-salty'
}
```

or by providing gradle properties:

* **encrypt.password**,
* **encrypt.salt**.

After plugin applied, **ALL** project resource will be encrypted when **processResources** task executed.
To specify which project resources have to be encrypted provide it to plugin extension:

```groovy
crypt {
    resources {
        include = new File("src/main/resources/my_secret_resource")
    }
}
```

## Decrypt resources at runtime

Plugin provides utility class **net.raccoon.sourcecrypt.Cryptor**, that provides static methods to decrypt/encrypt
strings.
Decrypt example:

```java
import net.raccoon.sourcecrypt.Cryptor;

class ExampleDecrypt {
    
    void decryptString(String encrypted, char[] password, byte[] salt) {
        String decryptedContent = Cryptor.decryptToString(encrypted, password, salt);
    }
    
    void decryptBytes(byte[] encrypted, char[] password, byte[] salt) {
        byte[] decryptedContent = Cryptor.decrypt(encrypted, password, salt);
    }
    
}
```

Also, you can add that class to your IDE:
```groovy
idea {
    module {
        generatedSourceDirs += file('build/generated')
    }
}
```

## Edit encrypted resources

To edit encrypted resources you have to decrypt them by running gradle task **decryptResources**. 
After editing completed you can manually encrypt them back by running **encryptResources** task. 
Project resources that not encrypted will be encrypted automatically when **processResources** task executed.
