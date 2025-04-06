import ComposeApp

class SwiftLibDependencyFactoryImpl: LibrariesDependencyFactory {
    static var shared = SwiftLibDependencyFactoryImpl()
    
    func provideFirebaseAnalytics() -> any FirebaseAnalyticsWrapper {
        return IosFirebaseAnalyticsImpl()
    }
}