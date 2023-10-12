These functions are dedicated to gather info about archive files and parse it.

```scala
def getInfo(archive: String, password: String = "", exec: String = "7z"): List[String]
```
This returns the stdout of 7zip packed into a list, containing all the useful information of the archive.

The list it returns is required for the rest of the functions here.

```scala
def parseArchiveInfo(info: List[String]): List[String]
```
Parses the info list as obtained from the function above and gets the general archive information.

```scala
def parseFileAttributes(info: List[String]): List[List[String]]
```
Parses the info list and returns a matrix containing the original and compressed size of each file in the archive.

```scala
def isFileinArchive(info: List[String], name: String, filesinfo: List[List[String]] = getFilesInfo(info), i: Int = 0): Boolean
```
Checks if the file whose path contains ```name``` exists in the archive respective to the info list you give.
