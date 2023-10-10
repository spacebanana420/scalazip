package scalazip

import scala.sys.process.*

def getInfoList(archive: String, password: String = "", exec: String = "7z"): List[String] = {
  val cmd =
    if password == "" then
      List(exec, "l", "-bso0", archive)
    else
      List(exec, "l", "-bso0", s"-p$password", archive)

  val fullInfo = cmd.!!
  val infoList = addInfoToList(fullInfo)
  val archiveInfo = getArchiveInfo(infoList)
  archiveInfo
  //get files info too
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


private def getArchiveInfo(info: List[String], newlist: List[String] = List(), i: Int = 4): List[String] = {
  if i == 11 || i == info.length then
    newlist
  else
    getArchiveInfo(info, newlist :+ getInfoValue(info(i)), i+1)
}

private def getInfoValue(line: String, value: String = "", copy: Boolean = false, i: Int = 0): String = {
  if i == line.length then
    value
  else if copy == true then
    getInfoValue(line, value + line(i), copy, i+1)
  else if line(i) == '=' then
    getInfoValue(line, value, true, i+2) //i+2 because after the = comes an empty space
  else
    getInfoValue(line, value, copy, i+1)
}
