package processor

import java.io.{File, PrintWriter}

import scala.io.Source
import java.nio.file._

object Utils {
  /**
    * Get a recursive listing of all files underneath the given directory.
    * from stackoverflow.com/questions/2637643/how-do-i-list-all-files-in-a-subdirectory-in-scala
    */
  def getRecursiveListOfFiles(dir: String): Array[File] = getRecursiveListOfFiles(new File(dir))
  def getRecursiveListOfFiles(dir: File): Array[File] = {
    val these = dir.listFiles
    these ++ these.filter(_.isDirectory).flatMap(file => getRecursiveListOfFiles(file))
  }

  def cleanComment(line: String): String = {
    val trimmed = line.trim
    var i = 0
    while (trimmed(i) == '/' || trimmed(i) == '*') { i += 1 }
    trimmed.drop(i).replace("**/", "").replace("*/", "")
  }

  val DELIMS = List("\\,", "\\.", "\\<\\:", "\\:", "\\(", "\\)", "\\[", "\\]", "\\*", "\\s+")

  def WITH_DELIMETER(delim: String): String = String.format("((?<=%1$s)|(?=%1$s))", delim)

  def makeRelativePath(from: String, to: String): String = {
    val start = from.split("/").dropRight(1)
    val end   = to.split("/").dropRight(1)
    println(start.mkString(" "))
    println(end.mkString(" "))
    val diff  = start.zip(end).indexWhere{case (x,y) => x != y }
    println("diff: " + diff)
    val back  = if (diff == -1) 0 else start.length - diff
    val forw  = if (diff == -1) end.length else diff
    val forward = end.drop(forw)
    val backBrk = if (back > 0) "/" else ""
    val forBrk  = if (forward.nonEmpty) "/" else ""
    List.fill(back)("..").mkString("/") + backBrk + forward.mkString("/") + forBrk + to.split("/").last
  }

  def cleanupSignature(signature: String, path: String, paths: Map[String, String]): String = {
    paths.foreach{case (k,v) => println(s"  $k: $v")}

    val trimmed = signature.trim.replaceAll("@api", "")
    if (trimmed.contains("@internal")) {
      ""
    }
    else {
      var tokens = Array(trimmed)
      DELIMS.foreach { delim =>
        tokens = tokens.flatMap { t => t.split(WITH_DELIMETER(delim)) }
      }
      println(signature)
      //println(tokens.mkString("  ", "\n  ", ""))
      val linked = tokens.map { t =>
        println(t + " [" + paths.contains(t) + "]")
        if (paths.contains(t)) {
          val otherPath = paths(t)
          val pathToOther = makeRelativePath(path, otherPath)
          s":doc:`$t <$pathToOther>`"
        }
        else t
      }.mkString("")

      var i = 0
      var eqlPos = -1
      var inParens = 0
      while (i < linked.length) {
        if (linked(i) == '=' && inParens == 0) eqlPos = i
        if (linked(i) == '(') inParens += 1
        if (linked(i) == ')') inParens -= 1
        i += 1
      }

      if (eqlPos > -1) linked.take(eqlPos) else linked
    }
  }

  val MAX_DESCRIPTION_LENGTH = 115
  val MIN_HEADING_WIDTH = 20
}
import Utils._

sealed trait Mode {
  val startToken: String
  val endToken: String
  def process(line: String)(implicit output: PrintWriter): Unit
  def start(path: String, paths: Map[String,String])(implicit output: PrintWriter): Unit
  def complete()(implicit output: PrintWriter): Unit
}
case object IdentityMode extends Mode {
  val startToken: String = ""
  val endToken: String = ""
  def process(line: String)(implicit output: PrintWriter): Unit = output.println(line)
  def start(path: String, paths: Map[String,String])(implicit output: PrintWriter): Unit = {}
  def complete()(implicit output: PrintWriter): Unit = {}
}
case object TableMode extends Mode {
  var p: TableProcessor = TableProcessor("", Map.empty)
  val startToken = "@table-start"
  val endToken = "@table-end"
  def process(line: String)(implicit output: PrintWriter): Unit = p.parseLine(line)
  def start(path: String, paths: Map[String,String])(implicit output: PrintWriter): Unit = {
    p = TableProcessor(path, paths)
  }
  def complete()(implicit output: PrintWriter): Unit = p.complete()
}


object Preprocessor {
  final val modes: List[Mode] = List(TableMode)

  var types: Map[String, String] = Map.empty  // Map from type name to filename (without .rst)
  var paths: Map[String, String] = Map.empty  // Map from type name to absolute path to filename (without .rst)

  def main(args: Array[String]): Unit = {
    val cwd = new File(".").getAbsolutePath.replace("/./", "/").replace("/.", "/")
    val dir = cwd + args.headOption.getOrElse("docs/source/")
    val out = dir.split(WITH_DELIMETER("/")).reverse.dropWhile(s => s == "/").reverse.dropRight(1).mkString("") + "prepped/"

    println("Dir: " + dir)
    println("Out: " + out)

    val (files, others) = getRecursiveListOfFiles(dir).partition(_.getName.endsWith(".rst"))

    others.filter(_.isFile).foreach{f =>
      val origPath = f.getAbsolutePath
      println(origPath)
      val path = origPath.replace(dir, out)
      val dirs = path.split(WITH_DELIMETER("/")).dropRight(1).mkString("")
      new File(dirs).mkdirs()

      Files.copy(Paths.get(origPath), Paths.get(path), StandardCopyOption.REPLACE_EXISTING)
    }

    files.foreach{file =>
      val filename = file.getName.replace(".rst","")
      val path = file.getAbsolutePath.replace(".rst", "")
      val src = Source.fromFile(file.getAbsolutePath)
      val lines = src.getLines()
      lines.foreach{line =>
        //println(line)
        if (line.contains("@alias")) {
          types += line.replace("@alias","").trim -> filename
          paths += line.replace("@alias","").trim -> path
        }
      }
      src.close()
    }

    println("Paths:")
    paths.foreach{case (k,v) =>
      println(s"  $k: $v")
    }


    var mode: Mode = IdentityMode
    files.foreach{file =>
      mode = IdentityMode
      val origPath = file.getAbsolutePath
      val path = origPath.replace(dir, out)
      val dirs = path.split(WITH_DELIMETER("/")).dropRight(1).mkString("")
      new File(dirs).mkdirs()
      implicit val pw: PrintWriter = new PrintWriter(path)

      val src = Source.fromFile(file.getAbsolutePath).reset()
      val lines = src.getLines()
      lines.foreach{line =>
        mode match {
          case IdentityMode =>
            val nextMode = modes.find{m => line.contains(m.startToken) }
            if (nextMode.isDefined) {
              mode = nextMode.get
              mode.start(origPath, paths)
            }
            else mode.process(line)

          case _ if line.contains(mode.endToken) =>
            mode.complete()
            mode = IdentityMode

          case _ =>
            mode.process(line)
        }
      }

      src.close()
      pw.close()
    }

  }
}

case class TableEntry(var signature: String, var description: String) {

  def cleanup(path: String, paths: Map[String, String]): Unit = {
    description = description.trim.replaceAll("`", "**")
    signature = cleanupSignature(signature.trim, path, paths)

  }
  def isEmpty: Boolean = signature.isEmpty
}


case class TableProcessor(path: String, paths: Map[String, String]) {
  var heading: String = ""
  var name: String = ""
  var entries: Seq[TableEntry] = Nil

  var hasHeading: Boolean = false
  var inComment: Boolean = false
  var currentEntry: TableEntry = TableEntry("","")

  def finishEntry(): Unit = {
    currentEntry.cleanup(path, paths)

    //println(currentEntry.signature)
    //println(currentEntry.description)

    if (!currentEntry.isEmpty)
      entries :+= currentEntry

    currentEntry = TableEntry("","")
  }

  def parseLine(line: String): Unit = {
    val trimmed = line.trim

    if (trimmed == "") {
      // Drop this line
    }
    else if (!hasHeading) {
      hasHeading = true
      if (trimmed.startsWith("abstract class")) {
        heading = "abstract class"
        name = trimmed.drop("abstract class".length)
      }
      else {
        val tokens = trimmed.split(" ")
        heading = tokens.head
        name = tokens.drop(1).mkString(" ")
      }
    }
    else if (inComment) {
      currentEntry.description += " " + cleanComment(line)
      if (line.contains("*/")) {
        inComment = false
      }
    }
    else {
      if (line.contains("/*")) {
        inComment = !line.contains("*/")
        if (!currentEntry.isEmpty) finishEntry()
        currentEntry.description += " " + cleanComment(line)
      }
      else {
        currentEntry.signature = line
        finishEntry()
      }
    }
  }

  def complete()(implicit output: PrintWriter): Unit = {
    finishEntry()
    val finalDescriptions = entries.map{e =>
      e.description.sliding(MAX_DESCRIPTION_LENGTH,MAX_DESCRIPTION_LENGTH).toArray
    }
    val finalSignatures = entries.map{e =>
      val sig = e.signature.replace("def ", "").trim
      val split = sig.split(WITH_DELIMETER("\\("))
      "**" + split.head + "**" + split.tail.mkString("")
    }

    val col1Width = Math.max(10, heading.length + 2)
    val col2Width = (finalDescriptions.flatMap(descs => descs.map(_.length)) ++ finalSignatures.map(_.length) ++ List(29)).max + 4

    val rb = "+" + "-"*col1Width + "+" + "-"*col2Width + "+"
    val hb = "+" + "="*col1Width + "+" + "="*col2Width + "+"

    output.println(rb)
    output.println("| " + heading + " " + " "*(col1Width-heading.length-2) + "  " + name + " "*(col2Width-name.length-1) + "|")
    output.println(hb)
    finalDescriptions.zip(finalSignatures).foreach{case (desc,sig) =>
      output.println("| |" + " "*(col1Width-6) + "def" + "   " + sig + " "*(col2Width-sig.length-1) + "|")
      desc.foreach{d =>
        output.println("|" + " "*col1Width + "    " + d + " " + " "*(col2Width-d.length-4) + "|")
      }
      output.println(rb)
    }

  }
}