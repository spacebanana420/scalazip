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
