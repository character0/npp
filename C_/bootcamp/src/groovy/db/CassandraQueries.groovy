package groovy.db

/**
 * Created by nickpanahi on 2/19/14.
 */
class CassandraQueries {

    def grailsApplication

    // TODO
    def keyspace = grailsApplication.config.db.keyspace.name

    static final String TRADES_CF_NAME = "trades_data"
    static final String TRADES_BY_PRICE_CF_NAME = "trade_by_price"
    static final String STOCK_NEWS_CF_NAME = "news_data"
    static final String REVIEWS_DATA_CF_NAME = "reviews_data"

    static final List All_TABLE_DEFINITIONS = [TRADES_CF, TRADES_BY_PRICE_CF, STOCK_NEWS_CF, REVIEWS_DATA_CF]

    static final String TRADES_CF = """CREATE TABLE bootcamp.$TRADES_CF_NAME (
        date timestamp,
        hour int,
        price float,
        minute int,
        sequence int,
        quantity int,
        PRIMARY KEY ((date, hour), minute, sequence, price)
    );"""

    static final String TRADES_BY_PRICE_CF = """CREATE TABLE bootcamp.$TRADES_BY_PRICE_CF_NAME (
        date timestamp,
        hour int,
        price float,
        minute int,
        sequence int,
        quantity int,
        PRIMARY KEY ((date, hour, price), minute, sequence)
    );""";

    static final String STOCK_NEWS_CF = """CREATE TABLE bootcamp.$STOCK_NEWS_CF_NAME (
        id int PRIMARY KEY,
        date text,
        title text,
        symbol text,
        news text,
        source text
    );""";

    static final String REVIEWS_DATA_CF = """CREATE TABLE bootcamp.$REVIEWS_DATA_CF_NAME (
        unique_id text,
        summary text,
        time timestamp,
        user_id text,
        score double,
        helpful int,
        out_of int,
        profile_name text,
        product_id text,
        text varchar,
        PRIMARY KEY (unique_id, time)
    );""";


    static final String INSERT_TRADE_BY_PRICE = """INSERT INTO bootcamp.$TRADES_BY_PRICE_CF_NAME
        (date, hour, price, minute, sequence, quantity) VALUES (?, ?, ?, ?, ?, ?); """

    static final String SELECT_1_MINUTE_INTERVAL_FOR_DATE = """ """; // TODO

    static final String SELECT_10_MINUTE_INTERVAL_FOR_DATE = """ """; // TODO

    static final String SELECT_PRICE_FOR_DATE = """ """; // TODO

    static final String SELECT_PRICE_FOR_DATE_AND_1_MINUTE_INTERVAL = """ """; // TODO

    static final String SELECT_PRICE_FOR_DATE_AND_10_MINUTE_INTERVAL = """ """; // TODO

    static final String INSERT_STOCK_NEWS = """INSERT INTO bootcamp.$STOCK_NEWS_CF_NAME
        (id, date, title, symbol, news, source) VALUES (?, ?, ?, ?, ?, ?); """

    static final String INSERT_REVIEWS = """INSERT INTO bootcamp.$REVIEWS_DATA_CF_NAME
        (unique_id, summary, time, user_id, score, helpful, out_of, profile_name, product_id, text) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"""

}
