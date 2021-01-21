package groovy.util

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class UpdateUtil {

	private static Log log = LogFactory.getLog(this)
	
	public static boolean safeUpdateObject(Object obj, Closure updateClosure) {
        log.debug("obj is $obj" )
		String objInfo = obj?.dump()
		
		boolean retval = false
		if(obj){
			retval = obj.withTransaction { status ->
				try {
					obj.lock()
					obj.refresh()
				}
				catch (Throwable exception) {
					String msg = "safeUpdateObject: Got exception while attempting to lock an object row.\n" +
							 "99% of the time, this means the object was deleted before lock was attempted.\n" +
							 "Stacktrace is displayed below in case exception falls in other 1%.\n" +
							 "Object=${objInfo}\n"
							 "Class='${exception.getClass().getName()}'; Message='${exception.message}'\n"
					log.warn msg, exception
					return false
				}
				try {
					if(updateClosure(obj)) {
						log.trace "updateClosure returned true, saving: ${objInfo}"
						obj.save(flush:true)
						return true
					} else {
						log.debug "updateClosure returned false, not saving: ${objInfo}"
						return false
					}
				} catch (Exception e) {
					log.error "Exception updating object. Exception was: \n", e
					status.setRollbackOnly()
					return false
				}
			}
		}
		else {
			log.warn "Object was null.  Not performing operation."	
		}
		
		return retval
	}
	
	public static boolean safeDeleteObject(Object obj, Closure deleteClosure) {
		String objInfo = obj.dump()
		
		boolean retval = false
		if(obj){
			retval = obj.withTransaction { status ->
				try {
					obj.lock()
					obj.refresh()
				} 
				catch (Throwable exception) {
					String msg = "safeDeleteObject: Got exception while attempting to lock an object row.\n" +
							 "99% of the time, this means the object was deleted before lock was attempted.\n" +
							 "Stacktrace is displayed below in case exception falls in other 1%.\n" +
							 "Object=${objInfo}\n"
							 "Class='${exception.getClass().getName()}'; Message='${exception.message}'\n"
					log.warn msg, exception
					return false
				}
				try {
					if(deleteClosure(obj)) {
						log.trace "deleteClosure returned true, deleting: ${objInfo}"
						obj.delete(flush:true)
						return true
					} else {
						log.debug "deleteClosure returned false, not deleting: ${objInfo}"
						return false
					}
				} catch (Exception e) {
					log.error "Exception deleting object. Exception was: \n", e
					status.setRollbackOnly()
					return false
				}
			}
		}
		else {
			log.warn "Object was null.  Not performing operation."	
		}
		
		return retval
	}


  /**
   * Safely update object's state from oldState to newState.  If object's
   * current state isn't oldState, fail the operation.
   *
   * @param object The object to update.
   * @param oldState The object's current state.
   * @param newState The state to update the object to.
   * @return Whether object's state was updated to newState.
   */
  public static boolean safeUpdateState(object, oldState, newState)
  {
    return safeUpdateObject(object) {
      if(object.state == oldState) {
        object.state = newState;
        return true;
      } else {
        return false;
      }
    }
  }


  /**
   * Run updateClosure if object's state was safely updated.
   *
   * @param object The object to update.
   * @param oldState The object's current state.
   * @param newState The state to update the object to.
   * @param updateClosure The operation to perform if object's state was safely
   * updated.  Note that object is unlocked during this operation.
   * @return Whether object's state was updated to newState and updateClosure
   * ran.
   * 
   * @see #safeUpdateState(Object, Object, Object)
   */
  public static boolean safeUpdateState(object, oldState, newState, Closure updateClosure)
  {
    if(safeUpdateState(object, oldState, newState)) {
      updateClosure(object)
      return true;
    } else {
      return false;
    }
  }

}