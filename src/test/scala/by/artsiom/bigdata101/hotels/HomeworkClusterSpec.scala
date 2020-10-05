package by.artsiom.bigdata101.hotels

import java.net.URI

import by.artsiom.bigdata101.hotels.cluster.{DockerKitSpotify, DockerSparkService}
import org.apache.spark.sql.SparkSession
import org.scalatest.{FlatSpec, Ignore, Matchers}
import org.scalatest.time.{Second, Seconds, Span}

import scala.concurrent.duration._

@Ignore
class HomeworkClusterSpec extends FlatSpec with Matchers with DockerKitSpotify with DockerSparkService {
  implicit val pc = PatienceConfig(Span(30, Seconds), Span(1, Second))

  override val PullImagesTimeout      = 120 minutes
  override val StartContainersTimeout = 120 seconds
  override val StopContainersTimeout  = 100 seconds

  override val dockerHost = Option(new URI(sys.env("DOCKER_HOST")).getHost)

  "spark cluster" should "be ready for job submitting" in {
    dockerContainers.map(_.name).foreach(println)
    dockerContainers.forall(c => isContainerReady(c).futureValue) shouldBe true
  }

  "all tasks" should "return correct result" in {

    val spark = SparkSession.builder
      .master(s"spark://${dockerHost.getOrElse(DockerSparkService.AllLocal)}:7077")
      .config("spark.submit.deployMode", "cluster")
      .config("spark.network.timeout", "300")
      .appName("hotels-test")
      .getOrCreate()

    implicit val test = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("src/test/resources/data/test.csv")

    val homework = new Homework {}

    /**
      * +---------------+-------------+------------+-----+
      * |hotel_continent|hotel_country|hotel_market|count|
      * +---------------+-------------+------------+-----+
      * |              6|          105|          29|   42|
      * |              3|          151|          69|   37|
      * |              2|           50|         675|   12|
      * +---------------+-------------+------------+-----+
      */
    val result1 = homework.task1.collect()

    /**
      * +-------------+-----+
      * |hotel_country|count|
      * +-------------+-----+
      * |           66|    1|
      * +-------------+-----+
      */
    val result2 = homework.task2.collect()

    /**
      * +---------------+-------------+------------+-----+
      * |hotel_continent|hotel_country|hotel_market|count|
      * +---------------+-------------+------------+-----+
      * |              6|          204|        1776|    4|
      * +---------------+-------------+------------+-----+
      */
    val result3 = homework.task3.collect()

    assert(result1.size == 3)
    assert(result2.size == 1)
    assert(result3.size == 1)
  }
}
