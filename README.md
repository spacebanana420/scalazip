# scalazip
Scalazip is a 7zip binding library written in Scala. The library uses string parsing and packs 7z's arguments into a list, for portable command execution.

This readme is a work in progress

Example use:

```scala
val options =
    setThreads(0)
    ++ setWorkingDirectory("/path/to/files")
    ++ setCompressionLevel(3)
    ++ encryptHeader()
    ++ setPassword("ilovescala")
  val files = List[String]("image.bmp", "file.txt", "vm.img")
  createArchive("archive.7z", files, options)
```

In this example, 7z will compress the 3 files in the list which are assumed to be in /path/to/files. The compression level is set to 3 (from 0 to 9), header encryption is set to true and the password is set to "ilovescala", with the default encryption being AES256.

Without the working directory option, you need to specify the absolute path to the files.

The format 7z is assumed from the file name for now.

### Requirements & how to use

## Requirements

* 7zip (if not in your PATH, then you need to specify a custom path to the executable)
* Scala 3

## How to use

Download the code files in src and add them to your project however you wish. Import ```scalazip``` to use my library.
