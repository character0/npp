package clsetting

class SettingService {

    static transactional = true


  /**
   * Look for a setting with name that matches the parameter. If a setting is not found then
   * a setting is created with the defaultValue provided and then returned
   *
   * @param name the name value for the setting to find or create
   * @param defaultValue the value to use if the setting does not exist
   * @return the found or created setting
   */
  public Setting getSetting(String name, String defaultValue) {

    Setting s

    if (defaultValue == null || defaultValue.length() < 1) {
      log.info "Default value for setting cannot be null"
      thrown new Exception("Default value null while retrieving DB setting")
    } else {
      s = Setting.findByName(name);

      if (s) {
        log.debug "Found setting ${name}: ${s?.value}"
        return s
      } else {
        log.info "Did not find setting: $name, creating new setting: $name with value $defaultValue"
        s = new Setting()
        s.name = name
        s.value = defaultValue

        Setting.withTransaction {
          s.save(flush: true)
        }

        return s
      }
    }
  }

  /**
   * Look for a setting with name that matches the parameter. If a setting is not found then
   * the setting is created otherwise it is updated
   *
   * @param name the name value for the setting to find
   * @param value the value to use if the setting does not exist
   * @return whether the of the setting was updated/created
   */
  public boolean updateSetting(String name, String value) {
    Setting setting = Setting.findByName(name)
    def result;
    Setting.withTransaction {
      def valStr = value ? value : null
      if (setting) {
        setting.lock()
        setting.refresh()
        log.debug "Found setting: ${name}, updating with value ${value}"
        setting.value = valStr
      }
      else {
        log.info "Setting: ${name} not found, creating with value ${value}"
        setting = new Setting()
        setting.name = name
        setting.value = valStr
      }

      result = setting.save()
    }

    return result;
  }

  /**
   * Create a setting if it does not exist, if it does, it does not get updated
   *
   * @param name the name value for the setting to find
   * @param value the value to use if the setting does not exist
   * @return whether the of the setting was created
   */
  public boolean addSettingIfNotExists(String name, String value) {
    Setting setting = Setting.findByName(name)

    def valStr = value ? value : null
    if (!setting) {
      return updateSetting(name, value)
    } else {
      log.debug "Setting: ${name} found with value ${setting?.value}"
      return true
    }
  }
}
