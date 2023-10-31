These functions are minor functions that are not major or critical to the library but can be useful to you.

```scala
def addFiles(dir: String, exclude: List[String] = List()): List[String]
```

Returns a list containing the absolute paths to the files that are in the directory ```dir```. If you specify a list in the second argument. It will be used to exclude filenames. For example the file ```abcdef.png``` will be excluded if you introduce the exclusion list ```List("def")```.

```scala
def belongsToList(name: String, strlist: List[String], i: Int = 0): Boolean
```
Checks if a string belongs to a list

```scala
def convertListInt(strs: List[String], finallist: List[String | Int] = List(), i: Int = 0): List[String | Int]
```
Converts a string list into a list containing strings or ints. Any strings that represent integer numbers will be converted to ints.

```scala
def convertListNumbers(strs: List[String], finallist: List[String | Int | Long] = List(), i: Int = 0): List[String | Int | Long]
```
Converts a string list into a list containing strings, ints or longs. Any strings that represent integer numbers will be converted to ints, and strings that represent long numbers will be converted to longs.

```scala
def extractSubstring(str: String, i: Int, end: Int, substr: String = ""): String
```
Gets the substring from the string ```str``` that is located at the start of``` i``` and ends at ```end``` positions.
