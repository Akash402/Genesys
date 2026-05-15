// Maps environments to deployment regions and test groups, with validation for unrecognised environments
// Assertions below serve as lightweight tests covering happy paths and couple of negative cases
class EnvironmentConfig {
    static final testRegionMap = [
        "dev": "dev",
        "test": "test",
        "stg": "stg",
        "prod": "prod",
        "prod_euc1": "prod-euc1",
        "prod_usw2": "prod-usw2"
    ]

    static final testGroupMap = [
        "dev": "outbound.oms.*",
        "test": "outbound.oms.*",
        "prod": "outbound.oms.p1",
        "prod_euc1" : "outbound.oms.p1",
        "prod_usw2" : "outbound.oms.p1"
    ]

    static def getRegion(String environment) {
        if (!testRegionMap.containsKey(environment)) {
            throw new IllegalArgumentException("Environment does not exist: `${environment}`")
        }
        return testRegionMap[environment]
    }

    static def getTestGroups(String environment) {
        if (!testGroupMap.containsKey(environment)) {
            throw new IllegalArgumentException("No test group mapped or environment does not exist: `${environment}`")
        }
        return testGroupMap[environment]
    }
}

println("Environment config started!")

// getRegion() assertions
assert EnvironmentConfig.getRegion('dev') == 'dev'
assert EnvironmentConfig.getRegion('test') == 'test'
assert EnvironmentConfig.getRegion('prod_euc1') == 'prod-euc1'
assert EnvironmentConfig.getRegion('prod_usw2') == 'prod-usw2'
try {
    EnvironmentConfig.getRegion('yolo')
    assert false : "Expected exception was not thrown"
} catch(IllegalArgumentException e) {
    println("Expected error - ${e.message}")
}

// getTestGroups() assertions
assert EnvironmentConfig.getTestGroups("dev") == 'outbound.oms.*'
assert EnvironmentConfig.getTestGroups("prod_usw2") == 'outbound.oms.p1'
try {
    EnvironmentConfig.getTestGroups('stg')
    assert false : "Expected exception was not thrown"
} catch(IllegalArgumentException e) {
    println("Expected error - ${e.message}")
}

println("Environment config completed!")