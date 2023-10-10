package scalazip

import java.io.File

def setPassword(pass: String): List[String] = {
  if pass != "" then
    List(s"-p$pass")
  else
    List()
}

def setThreads(amt: Short): List[String] = {
  List(s"-mmt$amt")
}

def setCompressionLevel(level: Byte): List[String] = {
  val filteredLevel =
    if level < 0 then
      0
    else if level > 9 then
      9
    else
      level
  List(s"-mx$filteredLevel")
}

def disableCompression(): List[String] = setCompressionLevel(0)

def setCompression(mode: String): List[String] = {
  val modesZip = List("copy", "deflate", "deflate64", "bzip2", "lzma", "ppmd")
  val equivalentZip = List("Copy", "Deflate", "Deflate64", "BZip2", "LZMA", "PPMd") //probably not necessary
  List(s"-mm=$mode") //add checking
}

def setEncryption(mode: String): List[String] = {
  val modesZip = List("zipcrypto", "aes128", "aes192", "aes256")
  val equivalentZip = List("ZipCrypto", "AES128", "AES192", "AES256") //probably not necessary
  List(s"-mem=$mode") //add checking
}

def encryptHeader(): List[String] = List("-mhe=on")

def deleteOriginal(): List[String] = List("-sdel")

def setNameMode(mode: String): List[String] = {
  mode match {
    case "auto" => List("-sas")
    case "add" => List("-saa")
    case "name" => List("-sae")
    case _ => List("-sas")
  }
}

def setCaseSensitive(mode: Boolean): List[String] = {
  if mode == true then
    List("-ssc")
  else
    List("-ssc-")
}

def setWorkingDirectory(dir: String): List[String] = {
  if dir != "" && File(dir).isDirectory == true then
    List(s"-w$dir")
  else
    List()
}
