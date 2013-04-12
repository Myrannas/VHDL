package com.oatau.vhdl.parser.templating

import com.intellij.psi.tree.{TokenSet, IElementType}
import com.intellij.openapi.diagnostic.Logger

/**
 * Parser template, used to intelligently handle parsing errors
 * through always operating on a parser template. The parser template
 * is able to track erroneous states, and is able to recover if necessary.
 *
 * Is designed to be used as a fluent interface.
 */
trait ParserTemplate {

  def inError: Boolean

  def recoverWith(elements: (IElementType, Position)*): ParserTemplate

  def expect(element: IElementType, error: String = ""): ParserTemplate

  def expectIn(elements: TokenSet, error: String = ""): ParserTemplate

  def expectIn(elements: IElementType*): ParserTemplate

  def expectAndMarkSingle(element: IElementType, markAs: IElementType, error: String = ""): ParserTemplate

  def expectInAndMarkSingle(element: TokenSet, markAs: IElementType, error: String = ""): ParserTemplate

  def mark(element: IElementType)(block: (ParserTemplate) => Unit): ParserTemplate

  def optional(element: IElementType): ParserTemplate

  def advance(): ParserTemplate

  def apply(): Boolean

  def rollbackToMark() : ParserTemplate

  def dropMark() : ParserTemplate

  /**
   * If condition is true, execute the block.
   *
   * Does not advance the lexer state
   * @param condition The condition to check against
   * @param block The block of code to execute
   * @return the final parser template
   */
  def when(condition: (IElementType) => Boolean)(block: (ParserTemplate) => ParserTemplate): ParserTemplate

  /**
   * Parse using a sub parsing function
   * @param block The function to perform parsing
   * @return the final parser template
   */
  def parse(block: (ParserTemplate) => ParserTemplate): ParserTemplate

  /**
   * See do while loop
   * Continues to execute the below code if the condition is true after each iteration.
   * Will cease iterating if it encounters an error.
   * The token used in the check is consumed.
   *
   * @param condition The condition to check
   * @param block The block of code to execute
   * @return The final parser template
   */
  def doWhile(condition: (IElementType) => Boolean)(block: (ParserTemplate) => ParserTemplate): ParserTemplate

  /**
   * Perform a switch on a statement, used for pattern matching.
   *
   * Does not advance the lexer state
   * @param block The code to execute, takes a tuple containing the element type and a parser template
   * @return The final parser template
   */
  def switch(block: (IElementType, ParserTemplate) => ParserTemplate): ParserTemplate

  /**
   * Raise an error
   * @param errorMsg The error message to be attached to the current token
   * @return An invalid parser template
   */
  def error(errorMsg: String): ParserTemplate

  /**
   * While loop, similar in concept to the do while loop.
   * Will execute a condition with a guarding statement check before execution.
   * Does not consume the token
   *
   * @param condition The condition to check
   * @param block The code block to execute
   * @return The final parser template
   */
  def `while`(condition: (IElementType) => Boolean)(block: (ParserTemplate) => ParserTemplate): ParserTemplate

  /**
   * Return the current token type
   * @return The current token  type
   */
  def current(): IElementType

  /**
   * The text for the current token
   * @return A string containing the text for the current token
   */
  def currentText(): String

  /**
   * Create a mark at the current position
   * Marks are tracked using a stack. If an error is encountered the entire stack is dumped.
   * @return The parser template
   */
  def beginMark(): ParserTemplate

  /**
   * Close a mark at the current position
   * @param eType The element type to mark with
   * @return The parser template
   */
  def endMark(eType: IElementType): ParserTemplate

  /**
   * A logging instance
   */
  private val log = Logger.getInstance(getClass)

  /**
   * Log a message
   * @param message The message to log
   * @return The parser template
   */
  def info(message: String) = {
    log.info(message)
    this
  }

  /**
   * Log a warning level message
   * @param message The message to log
   * @return The parser template
   */
  def warn(message: String) = {
    log.warn(message)
    this
  }

  /**
   * Log a debug level message
   * @param message The message to log
   * @return The parser template
   */
  def debug(message: String) = {
    log.debug(message)
    this
  }
}



