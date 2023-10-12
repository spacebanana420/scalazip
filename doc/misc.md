These functions are minor functions that are not major or critical to the library but can be useful to you.

```scala
def addFiles(dir: String, exclude: List[String] = List()): List[String]
```

Returns a list containing the absolute paths to the files that are in the directory ```dir```. If you specify a list in the second argument. It will be used to exclude filenames. For example the file ```abcdef.png``` will be excluded if you introduce the exclusion list ```List("def")```.
