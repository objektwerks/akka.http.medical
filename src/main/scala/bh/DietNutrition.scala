package bh

case class DietNutrition(patientId: Long, encounterId: Long, status: String, diet: String)

object DietNutrition {
  import upickle.default._

  implicit val nowRW: ReadWriter[DietNutrition] = macroRW
}
