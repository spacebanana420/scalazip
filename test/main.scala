package scalaziptests
import scalazip.*

//This is where I test my library
//Additional files I use to test and add to archives are not included in the project

@main def main() = {
  create1()
  info1()
  renametheshit()
}

// def extract1() = {
// }

def create1() = {
  println("Creating archive.7z\n")
  val options =
    setThreads(0)
    ++ setCompressionLevel(3)
    ++ encryptHeader()
    ++ setPassword("ilovedoremy")
  val files = List[String]("froge.png", "Ayumudromeda 2.png", "src/archive.scala")
  createArchive("archive.7z", files, options)
  println("args: \n")
  for arg <- options ++ files do {
    print(s"$arg ")
  }
}

def renametheshit () = { //does not work, can only rename files
  println("Attempting to rename the src folder")
  renamePaths("archive.7z", List("src/"), List("sauce/"), setPassword("ilovedoremy"))
}

def info1 () = {
  println("\nReading archive.7z\n")
  val infoList = getInfo("archive.7z", "ilovedoremy")
  val archiveInfo = parseArchiveInfo(infoList)
  val filesInfo = parseFileAttributes(infoList)
  for info <- archiveInfo do {
    println(info)
  }
  println("Reading file attributes\n")
  for file <- filesInfo do {
    for info <- file do {
      print(s"$info ")
    }
    println()
  }
}
