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
  print("---Creating archive.7z---")
  val options =
    setThreads(0)
    ++ setCompressionLevel(3)
    ++ encryptHeader()
    ++ setPassword("ilovedoremy")
  val files = List[String]("froge.png", "Ayumudromeda 2.png", "src/archive.scala")
  createArchive("archive.7z", files, options)
  print("args: \n")
  for arg <- options ++ files do {
    print(s"$arg ")
  }
}

def renametheshit () = { //does not work, can only rename files
  print("---Attempting to rename the src folder---")
  renamePaths("archive.7z", List("src/"), List("sauce/"), setPassword("ilovedoremy"))
}

def info1 () = {
  val infoList = getInfo("archive.7z", "ilovedoremy")
  val archiveInfo = parseArchiveInfo(infoList)
  val filesInfo = parseFileAttributes(infoList)
  print("---Reading archive.7z---")
  for info <- archiveInfo do {
    println(info)
  }
  println("---Reading file attributes---")
  for file <- filesInfo do {
    for info <- file do {
      println(s"$info ")
    }
    //print("\n\n")
  }
}
