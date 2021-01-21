package bootcamp

import com.datastax.driver.core.BoundStatement
import com.datastax.driver.core.PreparedStatement
import groovy.db.CassandraQueries
import groovy.db.SimpleClient

class CassandraService {



    /**
     *
     */
    def insertTradeByPriceRow(date, hour, price, minute, sequence, quantity) throws Exception {
        PreparedStatement insertTradeByPrice = SimpleClient.session.prepare(CassandraQueries.INSERT_TRADE_BY_PRICE)
        executeBoundQuery {
            log.debug "insertTradeByPriceRow -> Preparing statement ${CassandraQueries.INSERT_TRADE_BY_PRICE}."
            BoundStatement boundStatement = new BoundStatement(insertTradeByPrice);
            return boundStatement.bind(date, hour, price, minute, sequence, quantity)
        }
    }

    /**
     *
     */
    def insertStockNewsRow(date, title, symbol, text, id, source) throws Exception {
        PreparedStatement insertStockNews = SimpleClient.session.prepare(CassandraQueries.INSERT_STOCK_NEWS)
        executeBoundQuery {
            log.debug "insertTradeByPriceRow -> Preparing statement ${CassandraQueries.INSERT_STOCK_NEWS}."
            BoundStatement boundStatement = new BoundStatement(insertStockNews);
            return boundStatement.bind(id, date, title, symbol, text, source)
        }
    }

    def get1MinuteIntervalForDate(Date date) {
        // TODO - All tick data within a given 1-minute interval for a specified date.
    }

    def get10MinuteIntervalForDate() {
        // TODO - All tick data within a given 10-minute interval  for a specified date.
    }

    def getTotalTradedForPriceAndDate() {
        // TODO - Total quantity traded at a given price on for a specified date
        // (e.g. quantity traded at 98.01 on 1/10/2014).
    }

    def getTotalTradedForPriceAndDateAnd1MinuteInterval() {
        // TODO - Total quantity traded at a given price on for a specified 1-minute interval on specified date
        // (e.g. quantity traded at 98.01 for the 1-minute interval beginning at 10:01:00 on 1/10/2014).
    }

    def getTotalTradedForPriceAndDateAnd10MinuteInterval() {
        // TODO - Total quantity traded at a given price on for a specified 10-minute interval on specified date
        // (e.g. quantity traded at 98.01 for the 10-minute interval beginning at 13:10:00 on 1/10/2014).
    }

    ////// Helper Methods //////
    /**
     *
     * @param queryString
     * @return
     * @throws Exception
     */
    def boolean executeQuery(queryString) throws Exception {
        log.info "executeQuery -> Executing query ${queryString}"
        def currentTime = System.currentTimeMillis()
        SimpleClient.session.execute(queryString);
        log.info "executeQuery -> METRIC: Took ${System.currentTimeMillis() - currentTime} ms to execute query."
        return true
    }

    /**
     *
     * @param dataBinding
     * @return
     * @throws Exception
     */
    def boolean executeBoundQuery(Closure dataBinding) throws Exception {

        log.debug "executeBoundQuery -> Binding statement."
        BoundStatement boundQuery = dataBinding()
        if (boundQuery) {
            return executeQuery(boundQuery)
        }

        return false
    }

}