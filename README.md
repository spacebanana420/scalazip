# scalazip
Scalazip is a wrapper library for the 7zip commandline, written in Scala. The library uses string parsing and packs 7z's arguments into a list, for portable command execution.

7zip's feature set isn't fully implemented, but the most important functionality and settings are ready to use.

This readme is a work in progress

Example use:

```scala
val options =
  setThreads(0)
  ++ setWorkingDirectory("/path/to/files")
  ++ setCompressionLevel(3)
  ++ encryptHeader()
  ++ setPassword("ilovescala")
val files = List("image.bmp", "file.txt", "vm.img")
createArchive("archive.7z", files, options)
```

In this example, 7z will compress the 3 files in the list which are assumed to be in ```/path/to/files```. The compression level is set to 3 (from 0 to 9), header encryption is set to true and the password is set to ```ilovescala```, with the default encryption being AES256.

```setThreads(0)``` sets the thread count to automatic, all of your CPU threads are used.

Without the working directory option, you need to specify the absolute path to the files.

The format 7z is assumed from the file name for now.

```options``` is a string list, containing the 7zip arguments for the respective settings. Each function used in this list returns its own string list, and so they are concatenated.

## Format support

scalazip supports all archive formats that 7zip supports. This includes support for writing and reading 7zip, zip, tar, gzip, bzip2 files as well as reading rar, iso, and many more archive files.

## Requirements

* [7zip](https://www.7-zip.org/)
* [Scala 3](https://scala-lang.org/)

If 7z if not in your PATH, then you need to specify a custom path to the executable (see the archive doc).

## How to use

Download the library files in ```src``` and add them to your project however you wish. Import ```scalazip``` to use my library.

## Documentation

Documentation for the library's functions is located in the ```doc``` directory. The documentation is still a work-in-progress.

* [Archive doc](https://github.com/spacebanana420/scalazip/tree/main/doc/archive.md)
* [Switches doc](https://github.com/spacebanana420/scalazip/tree/main/doc/switches.md)
* [Reader doc](https://github.com/spacebanana420/scalazip/tree/main/doc/reader.md)
