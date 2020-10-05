package by.artsiom.bigdata101.hotels

import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class HomeworkSpec extends FlatSpec with BeforeAndAfterAll with Matchers with Homework {
  import Columns._

  var spark: SparkSession = _

  override def beforeAll() =
    spark = SparkSession.builder
      .master("local")
      .appName("hotels-tests")
      .getOrCreate()

  "Task #1" should "return correct result" in {

    implicit val testData = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("src/test/resources/data/test.csv")

    /**
      * +---------------+-------------+------------+-----+
      * |hotel_continent|hotel_country|hotel_market|count|
      * +---------------+-------------+------------+-----+
      * |              6|          105|          29|   42|
      * |              3|          151|          69|   37|
      * |              2|           50|         675|   12|
      * +---------------+-------------+------------+-----+
      */
    val result1 = task1.collect()
    val expectedResult = Array(
      Array(6, 105, 29, 42),
      Array(3, 151, 69, 37),
      Array(2, 50, 675, 12)
    )

    assert(result1.size == 3)
    (0 until expectedResult.size) foreach { i =>
      val row = result1(i)
      val expectedRow = expectedResult(i)
      assert(row.getAs[Int](HotelContinent) == expectedRow(0))
      assert(row.getAs[Int](HotelCountry) == expectedRow(1))
      assert(row.getAs[Int](HotelMarket) == expectedRow(2))
      assert(row.getAs[Long](Count) == expectedRow(3))
    }
  }

  "Task #2" should "return correct result" in {

    implicit val testData = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("src/test/resources/data/test.csv")

    /**
      * +-------------+-----+
      * |hotel_country|count|
      * +-------------+-----+
      * |           66|    1|
      * +-------------+-----+
      */
    val result2 = task2.collect()

    assert(result2.size == 1)
    result2.foreach { row =>
      assert(row.getAs[Int](HotelCountry) == 66)
      assert(row.getAs[Long](Count) == 1)
    }
  }

  "Task #3" should "return correct result" in {

    implicit val testData = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("src/test/resources/data/test.csv")

    /**
      * +---------------+-------------+------------+-----+
      * |hotel_continent|hotel_country|hotel_market|count|
      * +---------------+-------------+------------+-----+
      * |              6|          204|        1776|    4|
      * +---------------+-------------+------------+-----+
      */
    val result3 = task3.collect()

    assert(result3.size == 1)
    result3.foreach { row =>
      assert(row.getAs[Int](HotelContinent) == 6)
      assert(row.getAs[Int](HotelCountry) == 204)
      assert(row.getAs[Int](HotelMarket) == 1776)
      assert(row.getAs[Long](Count) == 4)
    }
  }

  override def afterAll() =
    if (spark != null) {
      spark.stop()
    }
}