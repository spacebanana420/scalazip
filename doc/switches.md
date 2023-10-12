These functions return a list with the CLI argument to configure how 7zip will work. These lists should be concatenated with your options list as seen in the examples.

```scala
def setPassword(pass: String): List[String]
```

This argument sets the password to use when creating or reading an archive. If you are creating an archive. Adding this argument will enable encryption.


```scala
def setThreads(amt: Short): List[String]
```

Sets how many threads 7zip should use. Default is 1, set to 0 to use all CPU threads.

```scala
def setCompressionLevel(level: Byte): List[String]
```

Sets the compression level, with the value ranging from 0 to 9. 1 is fastest, 9 is slowest but best. Set to 0 to disable compression.

```scala
def disableCompression(): List[String]
```

Disables compression. Under the hood it just executes ```setCompressionLevel(0)```


```scala
def setCompression(mode: String): List[String]
```

Sets the compression mode. This is only supported for zip archives. If an unsupported mode is given, it will default to ```deflate```

The supported modes are:
* copy (no compression)
* deflate
* deflate64
* bzip2
* lzma
* ppmd


```scala
def setZipEncryption(mode: String): List[String]
```
Sets the encryption algo to use. This is only supported for zip archives. If an unsupported mode is given, it will default to ```aes256```

The supported modes are:
* zipcrypto
* aes128
* aes192
* aes256

```scala
def encryptHeader(): List[String]
```
Enables header encryption, default is disabled.

```scala
def deleteOriginal(): List[String]
```
Deletes the original files/archive after finishing the 7zip task.

```scala
def setNameMode(mode: String): List[String]
```
Sets the archive name mode.

The supported modes are:
* auto
* add
* name

```auto``` is default. The archive file extension is added if it is not detected in the name.

```add``` always adds the extension to the name.

```name``` does not add or detect any extension and the archive name will be always as you intend.

```scala
def setCaseSensitive(mode: Boolean): List[String]
```
Toggles case-sensitive path detection.

Default is disabled on Windows, and enabled on other systems.

```scala
def setWorkingDirectory(dir: String): List[String]
```
Sets 7zip's working directory. This is useful if you don't want to use the absolute path on your archive name and file names.

```scala
def setOutputDirectory(dir: String): List[String]
```
Sets the output directory of your archive/files. This can also be configured on the functions in archive.scala
