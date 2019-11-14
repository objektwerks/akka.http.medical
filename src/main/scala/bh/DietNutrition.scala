package bh

case class DietNutrition(patientId: Long, encounterId: Long, status: String, diet: String) extends Product with Serializable {
  def isValid: Boolean = patientId > 0 && encounterId > 0 && status.nonEmpty && diet.nonEmpty
}

object DietNutrition {
  import upickle.default._

  implicit val dietNutritionRW: ReadWriter[DietNutrition] = macroRW
}