package scommons.react.test.dom.util

import org.scalajs.dom.{Element, Node}

/**
  * Represents path between two `DOM` nodes.
  *
  * @param root root node, start of the path
  * @param currNode current node, end of the path
  * @see [[prettyPrint]]
  */
private[util] class TestDOMPath(val root: Element, val currNode: Element) {

  /**
    * Returns new [[TestDOMPath]] that ends at the given node.
    *
    * @param currNode new path end node
    * @return new [[TestDOMPath]]
    */
  def at(currNode: Element): TestDOMPath = TestDOMPath(root, currNode)

  /**
    * @return string representation of this path
    * @see [[prettyPrint]]
    */
  override def toString: String = prettyPrint()

  /**
    * Returns string representation of this path.
    *
    * <p>For example:
    * {{{
    * <div>
    *   <footer>
    *     <p>
    * }}}
    * @param sep nodes separator
    * @param indentSize indentation size
    * @return string representation of this path
    */
  def prettyPrint(sep: String = "\n", indentSize: Int = 2): String = {
    @scala.annotation.tailrec
    def loop(path: List[Node], indent: Int, buf: StringBuilder): String = path match {
      case Nil => buf.toString()
      case node :: tail =>
        if (buf.nonEmpty) {
          buf ++= sep
        }
        for (_ <- 0 until indent) {
          buf += ' '
        }
        buf += '<'
        buf ++= node.nodeName.toLowerCase
        buf += '>'

        loop(tail, indent + indentSize, buf)
    }

    loop(getPath, 0, new StringBuilder)
  }

  private def getPath: List[Node] = {
    @scala.annotation.tailrec
    def loop(root: Node, curr: Node, res: List[Node]): List[Node] = {
      if (root == curr) curr :: res
      else {
        loop(root, curr.parentNode, curr :: res)
      }
    }

    loop(root, currNode, Nil)
  }
}

private[util] object TestDOMPath {

  def apply(root: Element, currNode: Element): TestDOMPath = new TestDOMPath(root, currNode)
}
