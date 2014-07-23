dataSource {
    maxPoolSize = 30
    minPoolSize = 10

    pooled = true
    driverClassName = "org.postgresql.Driver"
    username = "bootcamp"
    password = "bootcamp"
}
hibernate {
    cache.use_second_level_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
//    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    cache.use_query_cache=true
    cache.provider_class='org.hibernate.cache.EhCacheProvider'
    dialect='org.hibernate.dialect.PostgreSQLDialect'

}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            //url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            url = 'jdbc:postgresql://localhost/bootcamp'
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            url = 'jdbc:postgresql://localhost/bootcamp'
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            url = 'jdbc:postgresql://localhost/bootcamp'
            /*properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=false
               validationQuery="SELECT 1"
               jdbcInterceptors="ConnectionState"
            }*/
        }
    }
}
