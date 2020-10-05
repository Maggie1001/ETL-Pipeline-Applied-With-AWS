package by.maggie.bigdata101.hotels

import org.apache.spark.sql.SparkSession

object Main extends App with Homework {

  args match {
    case Array(filePath) => {
      val spark = SparkSession.builder
        .appName("hotels")
        .getOrCreate()

      implicit val test = spark.read
        .option("header", "true")
        .schema(FileSchema)
        .csv(filePath)

      println("Task #1: ")
      task1.show()

      println("Task #2: ")
      task2.show()

      println("Task #3: ")
      task3.show()

      spark.stop()
    }
    case _ =>
      error("Usage: spark-submit --class Main --master <master> hotels-spark.jar <file>")
  }
}
