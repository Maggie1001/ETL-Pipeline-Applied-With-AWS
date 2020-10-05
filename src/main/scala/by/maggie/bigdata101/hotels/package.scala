package by.maggie.bigdata101

package object hotels {
  lazy val Columns = new {
    val DateTime                = "date_time"
    val SiteName                = "site_name"
    val PosaContinent           = "posa_continent"
    val UserCountry             = "user_location_country"
    val UserRegion              = "user_location_region"
    val UserCity                = "user_location_city"
    val OrigDestinationDistance = "orig_destination_distance"
    val UserId                  = "user_id"
    val IsMobile                = "is_mobile"
    val IsPackage               = "is_package"
    val Channel                 = "channel"
    val CheckInDate             = "srch_ci"
    val CheckOutDate            = "srch_co"
    val AdultsCount             = "srch_adults_cnt"
    val ChildrenCount           = "srch_children_cnt"
    val RoomCount               = "srch_rm_cnt"
    val DestinationId           = "srch_destination_id"
    val DestinationTypeId       = "srch_destination_type_id"
    val IsBooking               = "is_booking"
    val Cnt                     = "cnt"
    val HotelContinent          = "hotel_continent"
    val HotelCountry            = "hotel_country"
    val HotelMarket             = "hotel_market"
    val HotelCluster            = "hotel_cluster"
    val Count                   = "count"
  }

  import Columns._
  import org.apache.spark.sql.types._

  lazy val FileSchema = new StructType(
    Array(
      StructField(DateTime, TimestampType, true),
      StructField(SiteName, IntegerType, true),
      StructField(PosaContinent, IntegerType, true),
      StructField(UserCountry, IntegerType, true),
      StructField(UserRegion, IntegerType, true),
      StructField(UserCity, IntegerType, true),
      StructField(OrigDestinationDistance, DoubleType, true),
      StructField(UserId, IntegerType, true),
      StructField(IsMobile, IntegerType, true),
      StructField(IsPackage, IntegerType, true),
      StructField(Channel, IntegerType, true),
      StructField(CheckInDate, TimestampType, true),
      StructField(CheckOutDate, TimestampType, true),
      StructField(AdultsCount, IntegerType, true),
      StructField(ChildrenCount, IntegerType, true),
      StructField(RoomCount, IntegerType, true),
      StructField(DestinationId, IntegerType, true),
      StructField(DestinationTypeId, IntegerType, true),
      StructField(IsBooking, IntegerType, true),
      StructField(Cnt, IntegerType, true),
      StructField(HotelContinent, IntegerType, true),
      StructField(HotelCountry, IntegerType, true),
      StructField(HotelMarket, IntegerType, true),
      StructField(HotelCluster, IntegerType, true)
    ))
}
