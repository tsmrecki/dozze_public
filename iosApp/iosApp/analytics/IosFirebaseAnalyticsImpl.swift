import ComposeApp
import Foundation
import FirebaseAnalytics
import FirebaseCore


class IosFirebaseAnalyticsImpl: FirebaseAnalyticsWrapper {
    func logEvent(name: String, params: [String : Any]?) {
        var eventParams: [String: Any] = [:]
        params?.forEach { key, value in eventParams[key] = "\(value)" }
        Analytics.logEvent(name, parameters: eventParams)
    }
    
    func setAnalyticsEnabled(enabled: Bool) {
        Analytics.setAnalyticsCollectionEnabled(enabled)
    }
}
