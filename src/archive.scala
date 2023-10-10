package scalazip

import java.io.File
import scala.sys.process.*

def createArchive(name: String, files: List[String], options: List[String], exec: String = "7z") = { 
  val filesOnly = files.filter(x => File(x).isFile() == true)
  if filesOnly.length != 0 then
    val command = List[String](exec, "a", name) ++ getMainArgs() ++ options ++ filesOnly
    command.!
}

def extractFiles(name: String, files: List[String], options: List[String], outDir: String = "", exec: String = "7z") = {
  val command =
  if outDir == "" || File(outDir).isDirectory() == false then
    List[String](exec, "e", name) ++ getMainArgs() ++ options ++ files
  else
    List[String](exec, "e", name, s"-o$outDir") ++ getMainArgs() ++ options ++ files
  command.!
}

def extractArchive(name: String, options: List[String], outDir: String = "", exec: String = "7z") = {
  val command =
  if outDir == "" || File(outDir).isDirectory() == false then
    List[String](exec, "e", name) ++ getMainArgs() ++ options
  else
    List[String](exec, "e", name, s"-o$outDir") ++ getMainArgs() ++ options
  command.!
}

def removeFiles(name: String, files: List[String], options: List[String], exec: String = "7z") = {
  val command = List[String](exec, "d", name) ++ getMainArgs() ++ options ++ files
  command.!
}

def renamePaths(name: String, files: List[String], newnames: List[String], exec: String = "7z") = {
  if files.length == newnames.length then
    val renameList = combineNameLists(files, newnames)
    List[String](exec, "rn", name) ++ getMainArgs() ++ renameList
}

private def getMainArgs(): List[String] = List("-y", "-bso0", "-bse0", "-bsp0", "-r")

private def combineNameLists(oldnames: List[String], newnames: List[String], combined: List[String] = List(), i: Int = 0): List[String] = {
  if i == oldnames.length then
    combined
  else
    combineNameLists(oldnames, newnames, combined :+ oldnames(i) :+ newnames(i), i+1)
}

def addDir(dir: String): List[String] = {
    val absolute =
      if dir != "." then
        File(dir).getAbsolutePath()
      else
        File("").getAbsolutePath()
    val dirChar =
      if absolute.contains("/") == true then
        '/'
      else
        '\\'
    val absoluteFiles = File(dir).list().map(x => absolute + dirChar + x)
    absoluteFiles.toList
}

// private def hasDirs(paths: List[String], i: Int = 0): Boolean = {
//   if i == paths.length then
//     false
//   else if File(paths(i)).isDirectory == true then
//     true
//   else
//     hasDirs(paths, i+1)
// }

def checkSupport(name: String, fmts: List[String] = List(".7z", ".bz2", ".gz", ".tar", ".zip", ".xz", ".wim"), i: Int = 0): Boolean = {
 if i == fmts.length then
    false
  else if name.contains(fmts(i)) then
    true
  else
    checkSupport(name, fmts, i+1)
}

// def addFile(name: String, files: List[String]): List[String] = {
//   if File(name).isFile() == true then
//     files :+ name
//   else
//     files
// }
