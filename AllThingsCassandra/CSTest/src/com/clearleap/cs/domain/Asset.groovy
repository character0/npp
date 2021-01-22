package com.clearleap.cs.domain

/**
 * Created with IntelliJ IDEA.
 * User: rseshadri
 * Date: 5/10/13
 * Time: 9:09 AM
 * To change this template use File | Settings | File Templates.
 */
class Asset {

    String name;
    String guid;
    String shortTitle;
    String episodeName;
    String description;
    String ratingSchema;
    String rating;
    Integer duration = 0;         //in seconds
    String state = "Incomplete"
    String priceType
    Float price = 0
    String priceInformation
    String priceCurrency
    Date startDate
    Date endDate
    Date createDate = new Date()
    Integer newDays
    Integer lastChanceDays
    String dateFormat
    String product
    String provider
    String providerId
    String assetId
    Integer rentalLength = 30*24*60         //in minutes - default 30 days


}
