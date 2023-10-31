These functions are dedicated to gather info about archive files and parse it.

```scala
def getInfo(archive: String, password: String = "", exec: String = "7z"): List[String]
```
This returns the stdout of 7zip packed into a list, containing all the useful information of the archive.

The list it returns is required for the rest of the functions here.

```scala
def getArchiveInfo(info: List[String], newlist: List[String] = List(), i: Int = 4): List[String]
```
Parses the info list as obtained from the function above and gets the general archive information.

```scala
def getFilesInfo(info: List[String], filesinfo: List[List[String]] = List(), fileinfo: List[String] = List(), i: Int = 13): List[List[String]]
```
Parses the info list and returns a matrix containing the original and compressed size of each file in the archive.

```scala
def getArchiveValues(info: List[String]): List[String | Int | Long]
```
Same as ```getArchiveInfo``` but number strings are converted to ints or longs

```scala
def isFileinArchive(info: List[String], name: String, i: Int = 0): Boolean
```
Checks if the file whose path contains ```name``` exists in the archive respective to the info list you give.

```scala
def hashArchiveFiles(archive: String, files: List[String], mode: String, password: String = "", exec: String = "7z"): List[String]
```
Gets the hashes for the ```files``` that are in ```archive```, as well as the archive's hash. You can choose different hashing algorithms, default is crc32.

Supported algorithms:
* crc32
* crc64
* sha1
* sha256
* blake2sp
