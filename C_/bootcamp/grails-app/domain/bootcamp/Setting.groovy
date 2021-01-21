package bootcamp

class Setting {

    String name
    String value
    String type

    static mapping = {
        table 'setting'
        version false
        cache true
        name column: 'name'
        value column: 'value'
        type column: 'type'
    }

    static getSettingType(String name) {
        def setting = Setting.findByName(name, [cache: true]);
        return setting?.type
    }

    static getSetting(String name, defaultValue) {
        def retval = getSetting(name);
        if (!retval) {
            retval = defaultValue;
        }

        return retval;
    }

    static getSetting(String name) {
        def retval = null;

        def setting = Setting.findByName(name, [cache: true]);
        if (setting) {
            switch (setting.type) {
                case "Integer": retval = Integer.parseInt(setting.value); break;
                case "Long": retval = Long.parseLong(setting.value); break;
                case "Boolean": retval = Boolean.parseBoolean(setting.value); break;
                case "Float": retval = Float.parseFloat(setting.value); break;
                case "Double": retval = Double.parseDouble(setting.value); break;
                default: retval = setting.value;
            }
        }

        return retval;
    }

    static void setValue(String name, value) {
        if (value == null) {
            deleteSetting(name)
            return
        }

        def setting = Setting.findByName(name, [cache: true]);
        if (!setting) {
            setting = new Setting(name: name);
        }
        setting.value = value.toString();
        switch (value) {
            case Integer: setting.type = "Integer"; break;
            case Long: setting.type = "Long"; break;
            case Boolean: setting.type = "Boolean"; break;
            case Float: setting.type = "Float"; break;
            case Double: setting.type = "Double"; break;
            default: setting.type = "String"; break;
        }
        setting.save(flush: true);
    }

    static void deleteSetting(String name) {
        def setting = Setting.findByName(name, [cache: true]);
        if (setting) {
            setting.delete(flush: true)
        }
    }
}
