package scalazip

import java.io.File

//General functions that the library uses but can also be useful to you

def addFiles(dir: String, exclude: List[String] = List()): List[String] = {
  val files =
    if exclude.length == 0 then
      File(dir).list().filter(x => File(x).isFile() == true).map(x => File(x).getAbsolutePath())
    else
      File(dir).list().filter(x => File(x).isFile() == true && belongsToList(x, exclude) == false).map(x => File(x).getAbsolutePath())
  files.toList
}

def belongsToList(name: String, strlist: List[String], i: Int = 0): Boolean = {
  if i == strlist.length then
    false
  else if name == strlist(i) then
    true
  else
    belongsToList(name, strlist, i+1)
}

def convertListInt(strs: List[String], finallist: List[String | Int] = List(), i: Int = 0): List[String | Int] = {
  if i == strs.length then
    finallist
  else
    try
      convertListInt(strs, finallist :+ strs(i).toInt, i+1)
    catch
      case e: Exception => convertListInt(strs, finallist :+ strs(i), i+1)
}

def convertListNumbers(strs: List[String], finallist: List[String | Int | Long] = List(), i: Int = 0): List[String | Int | Long] = {
  if i == strs.length then
    finallist
  else
    try
      val numint = strs(i).toInt
      val numlong = strs(i).toLong
      if numint == numlong then
        convertListNumbers(strs, finallist :+ numint, i+1)
      else
        convertListNumbers(strs, finallist :+ numlong, i+1)
    catch
      case e: Exception => convertListNumbers(strs, finallist :+ strs(i), i+1)
}

def extractSubstring(str: String, i: Int, end: Int, substr: String = ""): String = {
  if i >= str.length || (i >= end && i > -1) then
    substr
  else
    extractSubstring(str, i+1, end, substr + str(i))
}
