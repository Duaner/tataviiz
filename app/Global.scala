import play.api._

object Global extends GlobalSettings {
	override def onStart(app: Application) {
    println("ON START")
		services.Serial.initialize
	}
}