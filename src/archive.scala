package scalazip

import java.io.File
import scala.sys.process.*

def createArchive(name: String, files: List[String], options: List[String], exec: String = "7z") = { 
  val command =
    if hasDirs(files) == true then
      List[String](exec, "a", name, "-y", "-bb0", "-r") ++ options ++ files
    else
      List[String](exec, "a", name, "-y", "-bb0") ++ options ++ files
  command.!
}

def extractFiles(name: String, files: List[String], options: List[String], exec: String = "7z") = {
  val command =
    if hasDirs(files) == true then
      List[String](exec, "e", name, "-y", "-bb0", "-r") ++ options ++ files
    else
      List[String](exec, "e", name, "-y", "-bb0") ++ options ++ files
  command.!
}

def extractArchive(name: String, options: List[String], exec: String = "7z") = {
  val command = List[String](exec, "e", name, "-y", "-bb0", "-r") ++ options
  command.!
}

def removeFiles(name: String, files: List[String], options: List[String], exec: String = "7z") = {
  val command =
    if hasDirs(files) == true then
      List[String](exec, "d", name, "-y", "-bb0", "-r") ++ options ++ files
    else
      List[String](exec, "d", name, "-y", "-bb0") ++ options ++ files
  command.!
}

private def hasDirs(paths: List[String], i: Int = 0): Boolean = {
  if i == paths.length then
    false
  else if File(paths(i)).isDirectory == true then
    true
  else
    hasDirs(paths, i+1)
}

def checkSupport(name: String, fmts: List[String] = List(".7z", ".bz2", ".gz", ".tar", ".zip", ".xz", ".wim"), i: Int = 0): Boolean = {
 if i == fmts.length then
    false
  else if name.contains(fmts(i)) then
    true
  else
    checkSupport(name, fmts, i+1)
}

def addFile(name: String, files: List[String]): List[String] = {
  if File(name).isFile() == true then
    files :+ name
  else
    files
}

def addDir(dir: String): List[String] = { //add directories too
  if File(dir).isDirectory == true then
    File(dir).list().filter(x => File(x).isFile == true)
  else
    List()
}
