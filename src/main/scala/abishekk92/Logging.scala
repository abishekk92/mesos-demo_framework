package abishekk92

import org.slf4j.{ Logger, LoggerFactory }

trait Logging {
  protected lazy val log = LoggerFactory.getLogger(getClass.getName)
}
