package scalazip

import scalazip.misc.*
import scala.sys.process.*
import java.io.File

//This code lets the user read and parse archive info
// Some indexes "i" start/increment/etc at a unique value because the stdout structure of 7z's "l" command is fixed and predictable

def getInfo(archive: String, password: String = "", exec: String = "7z"): List[String] = {
  val cmd =
    if password == "" then
      List(exec, "l", "-bso0", "-slt", archive)
    else
      List(exec, "l", "-bso0", s"-p$password", "-slt", archive)
  addInfoToList(cmd.!!)
  //do error checks
}

def getArchiveInfo(info: List[String], newlist: List[String] = List(), i: Int = 4): List[String] = {
  if i == 11 || i == info.length then
    newlist
  else
    getArchiveInfo(info, newlist :+ getLineValue(info(i)), i+1)
}

def getFilesInfo(info: List[String], filesinfo: List[List[String]] = List(), fileinfo: List[String] = List(), i: Int = 13): List[List[String]] = {
  if i == info.length then
    if fileinfo.length != 0 then
      filesinfo :+ fileinfo
    else
      filesinfo
  else if info(i) == "" then
    getFilesInfo(info, filesinfo :+ fileinfo, List(), i+1)
  else
    getFilesInfo(info, filesinfo, fileinfo :+ getLineValue(info(i)), i+1)
}

//finish this
def getArchiveValues(info: List[String]): List[String | Int | Long] = {
  convertListNumbers(getArchiveInfo(info))
}

// def getFilesValues(info: List[String], filevalues: List[List[String | Int | Long] = List(), i: Int = 0): List[List[String | Int | Long]] = {
//   val files = getFilesInfo(info)
//
// }

def isFileinArchive(info: List[String], name: String, i: Int = 0): Boolean = {
  if info(1).contains("Listing archive:") == true then
    findFile(getFilesInfo(info), name)
  else
    findElement(info, name)
}

private def addInfoToList(info: String, line: String = "", list: List[String] = List(), i: Int = 0): List[String] = {
  if i == info.length then
    if line != "" then //is this necessary?
      list :+ line
    else
      list
  else if info(i) == '\n' then
    addInfoToList(info, "", list :+ line, i+1)
  else
    addInfoToList(info, line + info(i), list, i+1)
}

private def findFile(filesinfo: List[List[String]], name: String, i: Int = 0): Boolean = {
  if i == filesinfo.length then
    false
  else if filesinfo(i)(0).contains(name) == true then
    true
  else
    findFile(filesinfo, name, i+1)
}

private def findElement(info: List[String], name: String, i: Int = 0): Boolean = {
  if i == info.length then
    false
  else if info(i).contains(name) == true then
    true
  else
    findElement(info, name, i+1)
}

private def getLineValue(line: String, value: String = "", copy: Boolean = false, i: Int = 0): String = {
  if i == line.length then
    value
  else if copy == true then
    getLineValue(line, value + line(i), copy, i+1)
  else if line(i) == '=' then
    getLineValue(line, value, true, i+2) //i+2 because after the = comes an empty space
  else
    getLineValue(line, value, copy, i+1)
}


// def hashFile(): List[String] = {
// }

def hashArchive(archive: String, files: List[String], mode: String, password: String = "", exec: String = "7z"): List[String] = {
  val modes = List("crc32", "crc64", "sha1", "sha256", "blake2sp")
  val modearg =
    if belongsToList(mode, modes) == true then
      s"-scrc$mode"
    else
      "-scrccrc32"
  val cmd =
    if password == "" then
      List(exec, "h", "-r", modearg, archive) ++ files
    else
      List(exec, "h", "-r", s"-p$password", modearg, archive) ++ files
  val info = addInfoToList(cmd.!!)
  val trueFiles = files.filter(x => findElement(info, x) == true)
  val hashes = getInfoHashes(info, trueFiles.length)
  hashes
  //val infopositions = findInfoPositions(info(8))
  //getFilesHash(info, infopositions(0), infopositions(1))
}

def hashFiles(files: List[String], mode: String, password: String = "", exec: String = "7z"): List[String] = {
  val modes = List("crc32", "crc64", "sha1", "sha256", "blake2sp")
  val modearg =
    if belongsToList(mode, modes) == true then
      s"-scrc$mode"
    else
      "-scrccrc32"
  val cmd =
    if password == "" then
      List(exec, "h", "-r", modearg) ++ files
    else
      List(exec, "h", "-r", s"-p$password", modearg) ++ files
  val info = addInfoToList(cmd.!!)
  val trueFiles = files.filter(x => File(x).isFile() == true)
  val hashes = getInfoHashes(info, trueFiles.length)
  hashes
  //val infopositions = findInfoPositions(info(8))
  //getFilesHash(info, infopositions(0), infopositions(1))
}

private def getInfoHashes(info: List[String], amount: Int, list: List[String] = List(), i: Int = 9): List[String] = {
  if i == info.length || i-9 == amount then
    list :+ info(i)
  else
    getInfoHashes(info, amount, list :+ info(i), i+1)
}

// private def getFilesHash(info: List[String], hashend: Int, namestart: Int, filesinfo: List[List[String]] = List(), i: Int = 9): List[List[String]] = {
//   if i == info.length || info(i) == "--------" then
//     if info.length != 0 then
//       filesinfo :+ info
//     else
//       filesinfo
//   else
//     val hashinfo = List(extractSubstring(info(i), namestart, -1), extractSubstring(info(i), 0, hashend)) //test!!!!!!!
//     getFilesHash(info, hashend, namestart, filesinfo :+ hashinfo, i+1)
// }
//
// private def findInfoPositions(line: String, current: Int = 0, endhash: Int = 0, startname: Int = 0, i: Int = 0): List[Int] = {
//   if i >= line.length then
//     List(endhash, startname)
//   else if line(i) == ' ' then
//     findInfoPositions(line, current+1, endhash, startname, i+1)
//   else
//     current match
//       case 0 => findInfoPositions(line, current, endhash+1, startname, i+1)
//       case 2 => findInfoPositions(line, current, endhash, startname+1, i+1)
//       case _ => findInfoPositions(line, current, endhash, startname, i+1)
// }
