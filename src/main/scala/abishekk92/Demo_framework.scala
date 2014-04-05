package abishekk92

import abishekk92.scheduler.Demo_frameworkScheduler
import abishekk92.executor.Demo_frameworkExecutor

import com.typesafe.config.{ Config, ConfigFactory }
import org.apache.mesos.{ MesosSchedulerDriver }
import org.apache.mesos.Protos.{ FrameworkInfo, FrameworkID }

object Demo_framework extends Logging {

  val defaultSettings = ConfigFactory.parseString("""
    mesos {
      master = "localhost:5050"
    }
  """)

  val config = ConfigFactory.load.getConfig("abishekk92").withFallback(defaultSettings)

  val normalizedName = "demo_framework"
  val failoverTimeout = 600000 // in milliseconds (10 minutes)
  val mesosMaster = config.getString("mesos.master")

  val frameworkId = FrameworkID.newBuilder.setValue(normalizedName)

  val frameworkInfo = FrameworkInfo.newBuilder()
    .setId(frameworkId)
    .setName(normalizedName)
    .setFailoverTimeout(failoverTimeout)
    .setUser("") // let Mesos assign the user
    .setCheckpoint(true)
    .build

  val scheduler = new Demo_frameworkScheduler

  val driver = new MesosSchedulerDriver(
    scheduler,
    frameworkInfo,
    mesosMaster
  )

  // Execution entry point
  def main(args: Array[String]): Unit = {
    log.info("Hello from framework [{}]!", normalizedName)
    driver.run()
  }

}