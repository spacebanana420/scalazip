package scalazip

import scalazip.misc.*
import java.io.File

//This part is dedicated to 7z's arguments (switches)

// def setFormat(fmt: String): List[String] = {
//
// }

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
  val supportedModes = List("copy", "deflate", "deflate64", "bzip2", "lzma", "ppmd")
  if belongsToList(mode, supportedModes) == true then
    List(s"-mm=$mode")
  else
    List(s"-mm=deflate")
}

def setZipEncryption(mode: String): List[String] = {
  val supportedModes = List("zipcrypto", "aes128", "aes192", "aes256")
  if belongsToList(mode, supportedModes) == true then
    List(s"-mem=$mode")
  else
    List(s"-mem=aes256")
}

def encryptHeader(): List[String] = List("-mhe=on")

def deleteOriginal(): List[String] = List("-sdel")

def setNameMode(mode: String): List[String] = {
  mode match {
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

def setOutputDirectory(dir: String): List[String] = { //implement * better later
  if dir != "" && dir != "*" && File(dir).isDirectory == true then
    List(s"-o$dir")
  else
    List()
}
