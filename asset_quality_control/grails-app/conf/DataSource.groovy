dataSource {
	pooled = true
  driverClassName = "org.postgresql.Driver"
  username = "clquality"
  password = "clquality"
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
            dbCreate = "update"
			url = "jdbc:postgresql://localhost/clquality"
		}
	}
	test {
        dbCreate = "update"
		dataSource {
			url = "jdbc:postgresql://localhost/clquality"
		}
	}
	production {
		dataSource {
			url = "jdbc:postgresql://localhost/clquality"
		}
	}
}