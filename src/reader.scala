package scalazip

import scala.sys.process.*

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

def parseArchiveInfo(info: List[String]): List[String] = {
  getArchiveInfo(info)
}

def parseFileAttributes(info: List[String]): List[List[String]] = { //implement -slt too
  getFilesInfo(info)
}

def isFileinArchive(info: List[String], name: String, i: Int = 0): Boolean = {
  val filesinfo = getFilesInfo(info)
  findFile(filesinfo, name)
}

//def hash archive (h command)

//def find file in archive

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

private def getArchiveInfo(info: List[String], newlist: List[String] = List(), i: Int = 4): List[String] = {
  if i == 11 || i == info.length then
    newlist
  else
    getArchiveInfo(info, newlist :+ getLineValue(info(i)), i+1)
}

private def getFilesInfo(info: List[String], filesinfo: List[List[String]] = List(), fileinfo: List[String] = List(), i: Int = 13): List[List[String]] = {
  if i == info.length then
    filesinfo :+ fileinfo
  else if info(i) == "" then
    getFilesInfo(info, filesinfo :+ fileinfo, List(), i+1)
  else
    getFilesInfo(info, filesinfo, fileinfo :+ getLineValue(info(i)), i+1)
}

private def findFile(filesinfo: List[List[String]], name: String, i: Int = 0): Boolean = {
  if i == filesinfo.length then
    false
  else if filesinfo(i)(0).contains(name) == true then
    true
  else
    findFile(filesinfo, name, i+1)
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

// private def getArchiveInfo(info: List[String], newlist: List[String] = List(), i: Int = 4): List[String] = {
//   if i == 11 || i == info.length then
//     val archiveAttributes = getFileAttributes(info(info.length-1))
//     newlist ++ archiveAttributes
//   else
//     getArchiveInfo(info, newlist :+ getLineValue(info(i)), i+1)
// }


// private def getFilesInfo(info: List[String], newlist: List[List[String]] = List(List()), i: Int = 14): List[List[String]] = {
//   if i == info.length-2 then //info.length-2 is when the file list ends, and 14 is when it starts
//     newlist
//   else
//     getFilesInfo(info, newlist ++ List(getFileAttributes(info(i))), i+1)
// }

// private def getFileAttributes(line: String, size: String = "", comp: String = "", name: String = "", i: Int = 0): List[String] = {
//   if i == line.length then
//     List(name, size, comp)
//   else if i >= 26 && i <= 37 && line(i) != ' ' then
//     getFileAttributes(line, size + line(i), comp, name, i+1)
//   else if i >= 39 && i <= 51 && line(i) != ' ' then
//     getFileAttributes(line, size, comp + line(i), name, i+1)
//   else if i >= 53 && i <= 77 then
//     getFileAttributes(line, size, comp, name + line(i), i+1)
//   else
//     getFileAttributes(line, size, comp, name, i+1)
// }
