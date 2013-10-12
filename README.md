logback-integrity
=================

[![Build Status](https://travis-ci.org/davidillsley/logback-integrity.png?branch=master)](https://travis-ci.org/davidillsley/logback-integrity)

Occasionally, for example, when logs need to be made available for forensic examination, it's important to be able to prove that logs haven't been tampered with. One mechanism to do this is to apply a chained hashing technique to each log line. This allows you to be confident that lines have not been inserted, removed, or modified.

This repository contains a logback encoder which appends a SHA-256 hash line to each content line in a log output. The hash is of the previous 2 lines (a content line and the preceding hash) e.g. 

```
Test
#ydBMlWX8ZlyAaB+x2CmTgCaHH2bhT1AeCFMd9mk4p4k=
Test2
#sASeDSER4/YTAQYmBihbqO1tJyyTr4qM5fjEZYT8keY=
Test3
#0Fzb2ZTdOnXVlgU7ZnBkdoNpKRkSKYGwqai5nM78V1M=
```

It can be applied with a simple configuration as shown:

```xml
<configuration>

  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder class="org.i5y.logback_integrity.SHA256Encoder">
      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
  </appender>

  <root level="debug">
    <appender-ref ref="STDOUT" />
  </root>
</configuration>
```

Current Status
===
New, unstable, and unproven. Use at your own risk.
