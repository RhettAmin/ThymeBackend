package ca.thymetodine.plugins

import ca.thymetodine.db.MongoConnection.closeConnection
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*

fun Application.appMonitoring() {
    createApplicationPlugin(name = "appMonitoring") {
        on(MonitoringEvent(ApplicationStarted)) { application ->
            application.log.info("=========== Server has started! ==============")
        }
        on(MonitoringEvent(ApplicationStopping)) { application ->
            closeConnection()
            application.log.info("=========== Mongo Client closed! ==============")
        }
        on(MonitoringEvent(ApplicationStopped)) { application ->
            application.log.info("=========== Server has stopped! ==============")
            application.environment.monitor.unsubscribe(ApplicationStarted) {}
            application.environment.monitor.unsubscribe(ApplicationStopped) {}
        }
    }
}