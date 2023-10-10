package scalaziptests
import scalazip.*

//This is where I test my library, test files not included

@main def main() = {
  create1()
}

// def extract1() = {
// }


def create1() = {
  println("Creating archive.7z")
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
