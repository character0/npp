package bootcamp;

class SettingController {
  def scaffold = true;
  
  def search = { 
    if(params.name) { 
      def setting = Setting.findByName(params.name, [cache: true]);
      if(setting) {
        redirect(action: show, id: setting.id);
        return;
      } 
    }
    
    flash.message = "Setting '${params.name}' not found."
    redirect(action: list);
  }


}
