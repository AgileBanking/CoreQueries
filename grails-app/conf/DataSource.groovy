dataSource {
    // h2
//    pooled = true
//    driverClassName = "org.h2.Driver"
//    username = "sa"
//    password = ""

    // MySQL
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    username = "root" //  "admin1" 
    password = "master" //  "admin1pwd"   "Master@21"
//    password = "bitnami"

}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
//    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
//            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
//            url = "jdbc:h2:coreDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            url = "jdbc:mysql://localhost:3306/coredb"   
//            url = "jdbc:mysql://commonsdb.cgol0a13k4df.us-east-1.rds.amazonaws.com:3306/commons"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
//            url = "jdbc:mysql://localhost:3306/commons" 
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
//            url = "jdbc:mysql://localhost:3306/commons" 
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=false
               validationQuery="SELECT 1"
               jdbcInterceptors="ConnectionState"
            }
        }
    }
}
