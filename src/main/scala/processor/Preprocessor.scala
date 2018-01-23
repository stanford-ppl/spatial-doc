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
    while (i < trimmed.length && (trimmed(i) == '/' || trimmed(i) == '*')) { i += 1 }
    trimmed.drop(i).replace("**/", "").replace("*/", "")
  }

  val DELIMS = List("\\,", "\\<\\:", "\\:", "\\(", "\\)", "\\[", "\\]", "\\*", "\\s+", "\"")
  val RST = List(":", "(", ")", "[", "]", "*", "\"")  // Characters which need to be escaped in restructured text

  def WITH_DELIMETER(delim: String): String = String.format("((?<=%1$s)|(?=%1$s))", delim)

  def makeRelativePath(from: String, to: String): String = {
    val verbose = false //to.contains("controllers")

    val start = from.split("/").dropRight(1)
    val end   = to.split("/").dropRight(1)
    if (verbose) println(start.mkString(" "))
    if (verbose) println(end.mkString(" "))

    val diff  = start.zip(end).indexWhere{case (x,y) => x != y }
    if (verbose) println("diff: " + diff)

    val back  = if (diff == -1) Math.max(0, start.length - end.length) else start.length - diff
    val forw  = if (diff == -1) end.length else diff
    val forward = end.drop(forw)
    val backBrk = if (back > 0) "/" else ""
    val forBrk  = if (forward.nonEmpty) "/" else ""
    val relative = List.fill(back)("..").mkString("/") + backBrk + forward.mkString("/") + forBrk + to.split("/").last

    if (verbose) println(relative)

    relative
  }

  def dropRhs(str: String): String = {
    var i = 0
    var eqlPos = -1
    var inParens = 0
    val lastColon = str.lastIndexOf("\\:")
    //println(str)
    //println(" "*lastColon + "^")
    while (i < str.length) {
      if (str(i) == '=' && inParens == 0 && i >= lastColon) eqlPos = i
      if (str(i) == '(') inParens += 1
      if (str(i) == ')') inParens -= 1
      i += 1
    }

    if (eqlPos > -1) str.take(eqlPos) else str
  }

  def tokenize(str: String, delims: List[String] = DELIMS): Array[String] = {
    var tokens = Array(str)
    delims.foreach{delim => tokens = tokens.flatMap{t => t.split(WITH_DELIMETER(delim)) }}
    tokens
  }

  def makeLink(token: String, ctx: Context): String = {
    if (ctx.paths.contains(token)) {
      val d = ctx.displ.getOrElse(token, token)
      val otherPath = ctx.paths(d)
      val pathToOther = makeRelativePath(ctx.path, otherPath)
      s":doc:`$d <$pathToOther>`"
    }
    else token
  }

  def cleanRST(tokens: Array[String], ctx: Option[Context] = None): Array[String] = {
    tokens.map { t =>
      if (ctx.isDefined && ctx.get.paths.contains(t)) makeLink(t, ctx.get)
      else if (RST.contains(t)) "\\" + t
      else t
    }
  }

  def cleanupSignature(signature: String, ctx: Context): String = {
    val trimmed = signature.trim.replaceAll("@api", "")
    if (trimmed.contains("@internal")) {
      ""
    }
    else {
      val tokens = tokenize(trimmed)
      val linked = cleanRST(tokens, Some(ctx))

      val defPos = linked.indexWhere(_.contains("def"))
      val dropDef = linked.drop(defPos + 1).dropWhile(_.matches("\\s+")).dropWhile(_ == "")

      if (dropDef.nonEmpty) {
        val combined = dropDef.mkString("")
        val tokened = tokenize(combined, List("\\(", "\\)", "\\[", "\\]", "\\s+"))
        val bolded = if (!tokened.head.contains(":doc:")) {
          if (tokened.head.endsWith("\\:")) {
          "**" + tokened.head.dropRight(2) + "**" + "\\:" + tokened.drop(1).mkString("")
        }
        else if (tokened.head.endsWith("\\")) {
          "**" + tokened.head.dropRight(1) + "**" + "\\" + tokened.drop(1).mkString("")
        }
        else {
          "**" + tokened.head + "**" + tokened.drop(1).mkString("")
        }}
        else tokened.mkString("")
        dropRhs(bolded)
      }
      else ""
    }
  }

  def subAts(line: String, ctx: Context): String = {
    val tokens = tokenize(line, List("\\.", "\\s+", "\\,", "\\;", "\\!", "\\?", "\\)", "\\(", "\\[", "\\]"))
    val ignore = List("api", "virtualize", "table-start", "table-end", "alias", "disp", "struct")

    //println(tokens.mkString("_"))

    tokens.map{t =>
      if (t.contains("@") && !ctx.paths.contains(t.drop(1)) && !ignore.contains(t.drop(1))) {
        println(s"${ctx.filename}:${ctx.line}: Messed up token $t")
      }
      if (t.startsWith("@") && ctx.paths.contains(t.drop(1))) makeLink(t.drop(1), ctx)
      else t
    }.mkString("")
  }

  val MAX_DESCRIPTION_LENGTH = 100
  val MIN_HEADING_WIDTH = 20
}
import Utils._

sealed trait Mode {
  val startToken: String
  val endToken: String
  def preprocess(line: String): String
  def process(line: String)(implicit output: PrintWriter): Unit
  def start(ctx: Context)(implicit output: PrintWriter): Unit
  def complete()(implicit output: PrintWriter): Unit
}
case object IdentityMode extends Mode {
  val startToken: String = ""
  val endToken: String = ""
  def preprocess(line: String): String = line
  def process(line: String)(implicit output: PrintWriter): Unit = output.println(line)
  def start(ctx: Context)(implicit output: PrintWriter): Unit = {}
  def complete()(implicit output: PrintWriter): Unit = {}
}
case object TableMode extends Mode {
  var p: TableProcessor = TableProcessor(Context.empty)
  val startToken = "@table-start"
  val endToken = "@table-end"
  def preprocess(line: String): String = {
    if (p.inComment || line.contains("/*")) line.replaceAll("`", "**")
    else line
  }
  def process(line: String)(implicit output: PrintWriter): Unit = p.parseLine(line)
  def start(ctx: Context)(implicit output: PrintWriter): Unit = {
    p = TableProcessor(ctx)
  }
  def complete()(implicit output: PrintWriter): Unit = p.complete()
}

case class Context(
  var filename: String,
  var path: String = "",
  var line: Int = 0,
  var displ: Map[String, String] = Map.empty,  // Map from alias to display name
  var paths: Map[String, String] = Map.empty   // Map from type name to absolute path to filename (without .rst)
)
object Context {
  def empty: Context = Context("")
}

object Preprocessor {
  final val modes: List[Mode] = List(TableMode)

  def main(args: Array[String]): Unit = {
    val cwd = new File(".").getAbsolutePath.replace("/./", "/").replace("/.", "/")
    val dir = cwd + args.headOption.getOrElse("docs/site/")
    val out = dir.split(WITH_DELIMETER("/")).reverse.dropWhile(s => s == "/").reverse.dropRight(1).mkString("") + "source/"

    println("Dir: " + dir)
    println("Out: " + out)

    val (files, others) = getRecursiveListOfFiles(dir).partition(_.getName.endsWith(".rst"))

    others.filter(_.isFile).foreach{f =>
      val origPath = f.getAbsolutePath
      val path = origPath.replace(dir, out)
      val dirs = path.split(WITH_DELIMETER("/")).dropRight(1).mkString("")
      new File(dirs).mkdirs()

      Files.copy(Paths.get(origPath), Paths.get(path), StandardCopyOption.REPLACE_EXISTING)
    }
    Files.move(Paths.get(out + "/conf"), Paths.get(out + "/conf.py"))

    var displ: Map[String, String] = Map.empty   // Map from alias to display name
    var paths: Map[String, String] = Map.empty   // Map from type name to absolute path to filename (without .rst)

    displ += "MInt" -> "Int"
    displ += "MLong" -> "Long"
    displ += "MString" -> "String"
    displ += "MUnit" -> "Unit"
    displ += "MRange" -> "Range"
    displ += "MFloat" -> "Float"
    displ += "MDouble" -> "Double"
    displ += "MArray" -> "Array"
    displ += "MHashMap" -> "HashMap"

    files.foreach{file =>
      val filename = file.getName.replace(".rst","")
      val path = file.getAbsolutePath.replace(".rst", "")
      val src = Source.fromFile(file.getAbsolutePath)
      val lines = src.getLines()
      var aliases = List[String]()
      var disp = ""
      lines.foreach{line =>
        if (line.contains("@alias")) {
          val alias = line.replace("@alias","").trim
          paths += alias -> path
          aliases +:= alias
        }
        else if (line.contains("@disp")) {
          disp = line.replace("@disp","").trim
        }
      }
      if (disp != "") aliases.foreach{a => displ += a -> disp }
      src.close()
    }

    displ.foreach{case (k,v) =>
      if (!paths.contains(k) && paths.contains(v))
        paths += k -> paths(v)
    }

    /*println("Paths:")
    paths.foreach{case (k,v) =>
      println(s"  $k: $v")
    }*/


    var mode: Mode = IdentityMode
    files.foreach{file =>
      mode = IdentityMode
      val filename = file.getName
      val origPath = file.getAbsolutePath
      val path = origPath.replace(dir, out)
      val dirs = path.split(WITH_DELIMETER("/")).dropRight(1).mkString("")

      new File(dirs).mkdirs()
      implicit val pw: PrintWriter = new PrintWriter(path)

      val src = Source.fromFile(file.getAbsolutePath).reset()
      val lines = src.getLines()
      var lineNum: Int = 1

      lines.foreach{ln =>
        val ctx = Context(filename, origPath, lineNum, displ, paths)

        var line = ln

        line = mode.preprocess(line)

        // Replace references to files using @x annotation
        if (line.contains("@")) {
          //println(line)
          line = subAts(line, ctx)
          //println(line)
        }

        mode match {
          case _ if line.trim.startsWith("//")     => // Do nothing for comments
          case _ if line.trim.startsWith("@alias") =>
          case _ if line.trim.startsWith("@disp")  =>

          case IdentityMode =>
            val nextMode = modes.find{m => line.contains(m.startToken) }
            if (nextMode.isDefined) {
              mode = nextMode.get

              mode.start(ctx)
            }
            else mode.process(line)

          case _ if line.contains(mode.endToken) =>
            mode.complete()
            mode = IdentityMode

          case _ =>
            mode.process(line)
        }

        lineNum += 1
      }

      src.close()
      pw.close()
    }

  }
}

case class TableEntry(var signature: String, var description: String) {

  def cleanup(ctx: Context): Unit = {
    description = description.trim
    signature = cleanupSignature(signature.trim, ctx)
  }

  def isEmpty: Boolean = signature.isEmpty
}


case class TableProcessor(ctx: Context) {
  var heading: String = ""
  var name: String = ""
  var entries: Seq[TableEntry] = Nil

  var hasHeading: Boolean = false
  var inComment: Boolean = false
  var currentEntry: TableEntry = TableEntry("","")

  def finishEntry(): Unit = {
    currentEntry.cleanup(ctx)

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
        name = trimmed.drop("abstract class".length).trim
      }
      else {
        val tokens = trimmed.split(" ")
        heading = tokens.head
        name = tokens.drop(1).mkString(" ").trim
      }
    }
    else if (inComment) {
      currentEntry.description += "\n" + cleanComment(line)
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
      e.description.split("\n").flatMap{line =>
        var words = line.split(WITH_DELIMETER(" ")).toList
        var lines = List[String]()
        while (words.nonEmpty) {
          var line = List[String]()
          if (words.head.length > MAX_DESCRIPTION_LENGTH) {
            line = List(words.head.take(MAX_DESCRIPTION_LENGTH - 1) + "-")
            words = List(words.head.drop(MAX_DESCRIPTION_LENGTH - 1)) ++ words.tail
          }
          while (words.nonEmpty && lines.map(_.length).sum < MAX_DESCRIPTION_LENGTH - words.head.length) {
            line = line ++ List(words.head)
            words = words.tail
          }
          lines = lines ++ List(line.mkString("").trim)
        }

        //lines.foreach(println)
        lines
      }
    }

    val finalSignatures = entries.map(_.signature)

    name = {
      val tokens = cleanRST(tokenize(name))
      "**" + tokens.head + "**" + tokens.tail.mkString("")
    }

    val col1Width = Math.max(10, heading.length + 2)
    val col2Width = (finalDescriptions.flatMap(descs => descs.map(_.length)) ++ finalSignatures.map(_.length) ++ List(29, name.length + 2)).max + 4

    val rb = "+" + "-"*col1Width + "+" + "-"*col2Width + "+"
    val hb = "+" + "="*col1Width + "+" + "="*col2Width + "+"

    output.println(rb)
    if (heading != "NoHeading") {
      output.println("| " + heading + " " + " " * (col1Width - heading.length - 2) + "  " + name + " " * (col2Width - name.length - 1) + "|")
    }
    if (finalSignatures.isEmpty) {
      output.println(rb)
    }
    else if (heading != "NoHeading") {
      output.println(hb)
    }

    finalDescriptions.zip(finalSignatures).foreach{case (desc,sig) =>
      output.println("| |" + " "*(col1Width-6) + "def" + "   " + sig + " "*(col2Width-sig.length-1) + "|")
      desc.foreach{d =>
        output.println("| |" + " "*(col1Width-2) + "    " + d + " " + " "*(col2Width-d.length-4) + "|")
      }
      output.println(rb)
    }

    output.println("")

  }
}