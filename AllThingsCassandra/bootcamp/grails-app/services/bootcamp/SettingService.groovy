package bootcamp

import groovy.constants.SettingNames

import grails.transaction.Transactional

@Transactional
class SettingService {

    static transactional = false

    String getStringSystemSetting(String name, String defaultValue) {
        def setting = getSettingObject(name)
        if (setting) {
            log.debug "getStringSystemSetting -> Found setting '${name}'"
            return setting.value
        } else {
            log.debug "getStringSystemSetting -> NOT Found setting '${name}'"
            Setting.withTransaction {
                setting = new Setting(name: name, value: defaultValue)
                setting.save()
            }

            return setting.value
        }
    }

    int getIntegerSystemSetting(String name, int defaultValue) {
        def settingValue = Setting.getSetting(name, defaultValue)
        if (settingValue) {
            return settingValue
        } else {
            return defaultValue
        }
    }

    boolean getBooleanSystemSetting(String name, boolean defaultValue) {
        def settingValue = Setting.getSetting(name, defaultValue)
        if (settingValue) {
            return settingValue
        } else {
            return defaultValue
        }
    }

    /**
     * Adds a setting regardless or updates if already exists
     */
    Setting addStringSetting(String name, String value) {
        return addSetting( name,  value)
    }
    /**
     * Adds a setting regardless or updates if already exists
     */
    Setting addLongSetting(String name, Long value) {
        return addSetting( name,  value)
    }
    /**
     *
     * @return
     */
    Setting addBooleanSetting(String name, boolean value) {
        return addSetting( name,  value)
    }

    def addSetting(String name, def value) {

        if (!SettingNames.values().contains(name)) {
            log.debug "addSetting -> Setting name is not a recognized SettingName."
        }

        Setting setting = getSettingObject(name)
        def valStr = value ? value : null
        if (setting) {
            log.debug "addSetting -> Found setting '${name}' Updating with value ${value}"
            setting.value = valStr
        }
        else {
            log.debug "addSetting -> NOT Found setting '${name}' Creating with value ${value}"
            setting = new Setting(name: name, value: valStr)
            Setting.setValue(name, value)
        }

        return setting
    }

    Setting getSettingObject(String name) {
        def criteria = Setting.createCriteria()
        def setting = criteria.get {
            eq("name", name)
            /*if (account)
                eq("account", account)
            else
                isNull("account")*/
        }

        return setting
    }

}