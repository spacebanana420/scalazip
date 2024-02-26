package scalazip

import scalazip.misc.*
import java.io.File
import scala.sys.process.*

//The main file that contains the major functions that call 7zip

private def getMainArgs(): List[String] = List("-y", "-bso0", "-bse0", "-bsp0", "-r")

private def combineNameLists(oldnames: List[String], newnames: List[String], combined: List[String] = List(), i: Int = 0): List[String] = {
  if i == oldnames.length then
    combined
  else
    combineNameLists(oldnames, newnames, combined :+ oldnames(i) :+ newnames(i), i+1)
}

def createArchive(name: String, files: List[String], options: List[String], exec: String = "7z") = {
  val command = List[String](exec, "a", name) ++ getMainArgs() ++ options ++ files
  command.!
}

def extractArchive(name: String, options: List[String], keepPaths: Boolean = true, exec: String = "7z") = {
  val extractMode =
    if keepPaths then "x" else "e"
  val command = List[String](exec, extractMode, name) ++ getMainArgs() ++ options
  command.!
}

def extractFiles(name: String, files: List[String], options: List[String], keepPaths: Boolean = true, exec: String = "7z") = {
  val extractMode =
    if keepPaths then "x" else "e"
  val command = List[String](exec, extractMode, name) ++ getMainArgs() ++ options ++ files
  command.!
}

def removeFiles(name: String, files: List[String], options: List[String], exec: String = "7z") = {
  val command = List[String](exec, "d", name) ++ getMainArgs() ++ options ++ files
  command.!
}

def renamePaths(name: String, files: List[String], newnames: List[String], options: List[String], exec: String = "7z") = {
  if files.length == newnames.length then
    val renameList = combineNameLists(files, newnames)
    List[String](exec, "rn", name) ++ getMainArgs() ++ options ++ renameList
}

//def validateArchive()

def checkFor7z(execpath: String = "7z"): Boolean = {
  try
    if List(execpath, "-bso0", "-bse0", "-bsp0").! == 0 then
      true
    else false
  catch
    case e: Exception => false
}

def retrieveFileData(name: String, files: List[String], options: List[String], exec: String = "7z", filedata: List[String] = List(), i: Int = 0): List[String] = {
  if i == files.length then
    filedata
  else
    val command = List[String](exec, "x", name, "-so") ++ getMainArgs() ++ options :+ files(i) //optimise
    val data = command.!!
    retrieveFileData(name, files, options, exec, filedata :+ data, i+1)
}
//probably only works with ascii and utf8
def retrieveFileBytes(name: String, files: List[String], options: List[String], exec: String = "7z"): List[List[Byte]] = {
  val filedata = retrieveFileData(name, files, options, exec)
  convertFileStrings(filedata)
}

private def convertFileStrings(files: List[String], bytes: List[List[Byte]] = List(), i: Int = 0): List[List[Byte]] = {
  if i == files.length then
    bytes
  else
    convertFileStrings(files, bytes :+ dataToBytes(files(i)), i+1)
}

private def dataToBytes(file: String, bytes: List[Byte] = List(), i: Int = 0): List[Byte] = {
  if i == file.length then
    bytes
  else
    dataToBytes(file, bytes :+ file(0).toByte, i+1)
}

// private def hasDirs(paths: List[String], i: Int = 0): Boolean = {
//   if i == paths.length then
//     false
//   else if File(paths(i)).isDirectory == true then
//     true
//   else
//     hasDirs(paths, i+1)
// }

def checkSupport(name: String, fmts: Seq[String] = Vector(".7z", ".bz2", ".gz", ".tar", ".zip", ".xz", ".wim"), i: Int = 0): Boolean = {
 if i >= fmts.length then
    false
  else if name.contains(fmts(i)) then
    true
  else
    checkSupport(name, fmts, i+1)
}


