package bh

case class DietNutrition(patientId: Int, encounterId: Int, status: String, diet: String) {
  def isValid: Boolean = patientId > 0 && encounterId > 0 && status.nonEmpty && diet.nonEmpty
}

object DietNutrition {
  import upickle.default._

  implicit val dietNutritionRW: ReadWriter[DietNutrition] = macroRW
}
