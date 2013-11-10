dataSource {
    //h2
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
    
    // MySQL
//    pooled = true
//    driverClassName = "com.mysql.jdbc.Driver"
//    username = "nick"
//    password = "master"
//    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
////    password = "bitnami"       
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate'
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
//            url = "jdbc:mysql://localhost:3306/reqdb" // or ...
//            url = "jdbc:mysql://localhost:3306/corequeries" 
            username = "nick"
            password = "master"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
//            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            url = "jdbc:mysql://localhost:3306/corequeries" 
            username = "nick"
            password = "master"
            pooled = true
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT 1"
            }
        }
    }
}
