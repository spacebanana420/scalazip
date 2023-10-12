package scalazip

import java.io.File

def addFiles(dir: String, exclude: List[String] = List()): List[String] = {
  val files =
    if exclude.length == 0 then
      File(dir).list().filter(x => File(x).isFile() == true).map(x => File(x).getAbsolutePath())
    else
      File(dir).list().filter(x => File(x).isFile() == true && belongsToList(x, exclude) == false).map(x => File(x).getAbsolutePath())
  files.toList
}

//this is in reader.scala too, merge them later
private def belongsToList(name: String, exclude: List[String], i: Int = 0): Boolean = {
  if i == exclude.length then
    false
  else if name == exclude(i) then
    true
  else
    belongsToList(name, exclude, i+1)
}
