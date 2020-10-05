package by.artsiom.bigdata101.hotels.cluster

import com.whisk.docker.scalatest.DockerTestKit
import com.whisk.docker.{ContainerLink, DockerContainer, DockerReadyChecker, LogLineReceiver}
import org.apache.log4j.Logger
import org.scalatest.Suite

import scala.concurrent.duration
import scala.concurrent.duration.FiniteDuration

/**
  * Deploys 3 containers: one spark master and two spark workers
  */
trait DockerSparkService extends DockerTestKit  { self: Suite =>
  import DockerSparkService._

  val dockerHost: Option[String]

  val master = DockerContainer(image = MasterImage, name = Some("spark-master"), tty = true)
    .withPorts(8080 -> Some(8080), 7077 -> Some(7077))
    .withEnv("INIT_DAEMON_STEP=setup_spark")
    .withLogLineReceiver(LogLineReceiver(true, LogReceiver))
    .withReadyChecker(
      DockerReadyChecker.HttpResponseCode(port = 8080, path = "/", host = dockerHost, code = 200)
        .looped(3, FiniteDuration(15, duration.SECONDS)))

  val worker1 = DockerContainer(image = WorkerImage, name = Some("spark-worker1"))
    .withPorts(8081 -> Some(8081))
    .withEnv(s"SPARK_MASTER=spark://${dockerHost.getOrElse(AllLocal)}:7077")
    .withLinks(ContainerLink(master, "spark-master"))
    .withLogLineReceiver(LogLineReceiver(true, LogReceiver))

  val worker2 = DockerContainer(image = WorkerImage, name = Some("spark-worker2"))
    .withPorts(8081 -> Some(8082))
    .withEnv(s"SPARK_MASTER=spark://${dockerHost.getOrElse(AllLocal)}:7077")
    .withLinks(ContainerLink(master, "master"))
    .withLogLineReceiver(LogLineReceiver(true, LogReceiver))

  override def dockerContainers = master :: worker1 :: worker2 :: super.dockerContainers
}

object DockerSparkService {
  val Log = Logger.getLogger(classOf[DockerSparkService])
  val AllLocal = "0.0.0.0"
  val WorkerImage = "bde2020/spark-worker:2.3.2-hadoop2.7"
  val MasterImage = "bde2020/spark-master:2.3.2-hadoop2.7"

  lazy val LogReceiver: String => Unit = Log.info(_)
}