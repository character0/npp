package clquality
class TransferManagerController {

    static scaffold = true

    def index = {
        redirect(action: "list", params: params)
    }
	
	def create = {
		flash.message = "Cannot create Transfer Managers manually. They will be automatically " +
			"created when a new QC App instance is brought online."
		redirect(action: "list")
	}
	
	def save = {
		flash.message = "Cannot create Transfer Managers manually. They will be automatically " +
			"created when a new QC App instance is brought online."
		redirect(action: "list")
	}
}
